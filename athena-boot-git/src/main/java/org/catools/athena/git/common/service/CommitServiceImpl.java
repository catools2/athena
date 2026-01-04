package org.catools.athena.git.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.git.common.entity.Commit;
import org.catools.athena.git.common.entity.CommitMetadata;
import org.catools.athena.git.common.entity.DiffEntry;
import org.catools.athena.git.common.mapper.GitMapper;
import org.catools.athena.git.common.repository.CommitMetadataRepository;
import org.catools.athena.git.common.repository.CommitRepository;
import org.catools.athena.git.common.repository.TagRepository;
import org.catools.athena.model.git.CommitDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.catools.athena.git.utils.GitPersistentHelper.normalizeTags;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommitServiceImpl implements CommitService {
  private final CommitMetadataRepository commitMetadataRepository;
  private final CommitRepository commitRepository;
  private final TagRepository tagRepository;
  private final GitMapper gitMapper;

  @Override
  @Transactional
  public CommitDto saveOrUpdate(CommitDto entity) {
    log.info("Saving entity, hash: {}, diffs: {}, author: {}, committer: {}, tags: {}, metadata: {}.",
        entity.getHash(),
        entity.getDiffEntries().size(),
        entity.getAuthor(),
        entity.getCommitter(),
        entity.getTags().size(),
        entity.getMetadata().size());

    // Sometimes commits has the same hash and due to intensive parallel execution it is hard to control the flow
    // Using synchronized can slow everything so it is better to just give the save another chance in the case of issues
    try {
      return saveAndFlush(entity);
    } catch (Exception e) {
      log.warn("First attempt of saving commit failed with {}", e.getMessage());
      commitRepository.flush();
      return saveAndFlush(entity);
    }
  }


  @Override
  @Transactional(readOnly = true)
  public Optional<CommitDto> getById(Long id) {
    return commitRepository.findByIdWithRelations(id).map(gitMapper::commitToCommitDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<CommitDto> findByHash(final String keyword) {
    return commitRepository.findByHashWithRelations(keyword).map(gitMapper::commitToCommitDto);
  }

  private CommitDto saveAndFlush(CommitDto entity) {
    final Commit commit = gitMapper.commitDtoToCommit(entity);

    final Commit savedEntity = commitRepository.findByHashWithRelations(commit.getHash()).orElseGet(() -> {
      commit.setTags(normalizeTags(commit.getTags(), tagRepository));

      final Set<CommitMetadata> normalizedMetadata = normalizeMetadata(commit.getMetadata());
      commit.setMetadata(normalizedMetadata);

      commit.getDiffEntries().forEach(de -> de.setCommit(commit));

      // Set commit dynamic fields
      commit.setTotalImpactedFiles(commit.getDiffEntries().stream().map(DiffEntry::getOldPath).collect(Collectors.toSet()).size());
      commit.setTotalInsertedLine(commit.getDiffEntries().stream().map(DiffEntry::getInserted).reduce(Integer::sum).orElse(0));
      commit.setTotalDeletedLines(commit.getDiffEntries().stream().map(DiffEntry::getDeleted).reduce(Integer::sum).orElse(0));

      return commitRepository.saveAndFlush(commit);
    });

    return gitMapper.commitToCommitDto(savedEntity);
  }

  private synchronized Set<CommitMetadata> normalizeMetadata(Set<CommitMetadata> metadataSet) {
    final Set<CommitMetadata> metadata = new HashSet<>();

    for (CommitMetadata md : metadataSet) {
      // Create a detached instance to avoid issues with the original entity
      CommitMetadata detachedMd = new CommitMetadata();
      detachedMd.setName(md.getName());
      detachedMd.setValue(md.getValue());

      // Read md from DB and if MD does not exist we create one and assign it to the commit
      CommitMetadata commitMD =
          commitMetadataRepository.findByNameAndValue(detachedMd.getName(), detachedMd.getValue())
              .orElseGet(() -> {
                try {
                  return commitMetadataRepository.saveAndFlush(detachedMd);
                } catch (org.springframework.dao.DataIntegrityViolationException e) {
                  // Another thread inserted it between our check and save - retry lookup
                  return commitMetadataRepository.findByNameAndValue(detachedMd.getName(), detachedMd.getValue())
                      .orElseThrow(() -> new RuntimeException("Failed to find or create metadata after retry", e));
                }
              });

      metadata.add(commitMD);
    }

    return metadata;
  }
}
