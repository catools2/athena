package org.catools.athena.git.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.git.common.mapper.GitMapper;
import org.catools.athena.git.common.model.Commit;
import org.catools.athena.git.common.model.DiffEntry;
import org.catools.athena.git.common.model.Tag;
import org.catools.athena.git.common.repository.CommitMetadataRepository;
import org.catools.athena.git.common.repository.CommitRepository;
import org.catools.athena.git.common.repository.TagRepository;
import org.catools.athena.git.model.CommitDto;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.catools.athena.core.utils.MetadataPersistentHelper.normalizeMetadata;

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

    return saveAndFlush(entity);
  }


  @Override
  public Optional<CommitDto> getById(Long id) {
    return commitRepository.findById(id).map(gitMapper::commitToCommitDto);
  }

  @Override
  public Optional<CommitDto> search(String keyword) {
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

      return commitRepository.saveAndFlush(commit);
    });

    return gitMapper.commitToCommitDto(savedEntity);
  }

  public static synchronized Set<Tag> normalizeTags(Set<Tag> tags, TagRepository tagRepository) {
    final Set<Tag> output = new HashSet<>();

    for (Tag tag : tags) {
      // Read md from DB and if MD does not exist we create one and assign it to the pipeline
      Tag pipelineMD =
          tagRepository.findByNameOrHash(tag.getName(), tag.getHash())
              .orElseGet(() -> tagRepository.save(tag));
      output.add(pipelineMD);
    }

    tagRepository.flush();
    return output;
  }
}
