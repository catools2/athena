package org.catools.athena.git.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.utils.RetryUtils;
import org.catools.athena.git.common.mapper.GitMapper;
import org.catools.athena.git.common.model.Commit;
import org.catools.athena.git.common.model.DiffEntry;
import org.catools.athena.git.common.repository.CommitMetadataRepository;
import org.catools.athena.git.common.repository.CommitRepository;
import org.catools.athena.git.common.repository.TagRepository;
import org.catools.athena.git.model.CommitDto;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.catools.athena.core.utils.MetadataPersistentHelper.normalizeMetadata;
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
  public Optional<CommitDto> getById(Long id) {
    return commitRepository.findById(id).map(gitMapper::commitToCommitDto);
  }

  @Override
  public Optional<CommitDto> findByHash(final String keyword) {
    return commitRepository.findByHash(keyword).map(gitMapper::commitToCommitDto);
  }

  private CommitDto saveAndFlush(CommitDto entity) {
    final Commit commit = gitMapper.commitDtoToCommit(entity);

    final Commit savedEntity = commitRepository.findByHash(commit.getHash()).orElseGet(() -> {
      commit.setTags(normalizeTags(commit.getTags(), tagRepository));
      commit.setMetadata(normalizeMetadata(commit.getMetadata(), commitMetadataRepository));

      commit.getDiffEntries().forEach(de -> de.setCommit(commit));

      // Set commit dynamic fields
      commit.setTotalImpactedFiles(commit.getDiffEntries().stream().map(DiffEntry::getOldPath).collect(Collectors.toSet()).size());
      commit.setTotalInsertedLine(commit.getDiffEntries().stream().map(DiffEntry::getInserted).reduce(Integer::sum).orElse(0));
      commit.setTotalDeletedLines(commit.getDiffEntries().stream().map(DiffEntry::getDeleted).reduce(Integer::sum).orElse(0));

      return RetryUtils.retry(3, 1000, integer -> commitRepository.saveAndFlush(commit));
    });

    return gitMapper.commitToCommitDto(savedEntity);
  }
}
