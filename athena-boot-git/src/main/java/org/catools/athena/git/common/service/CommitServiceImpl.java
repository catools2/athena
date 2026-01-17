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
  public CommitDto saveOrUpdate(CommitDto entity) {
    Runtime runtime = Runtime.getRuntime();
    long usedMemoryBefore = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);

    log.info("Saving commit, hash: {}, diffs: {}, tags: {}, metadata: {}, author: {}, committer: {}, heapUsedMB: {}",
        entity.getHash(),
        entity.getDiffEntries().size(),
        entity.getTags().size(),
        entity.getMetadata().size(),
        entity.getAuthor(),
        entity.getCommitter(),
        usedMemoryBefore);

    // Sometimes commits has the same hash and due to intensive parallel execution it is hard to control the flow
    // Using synchronized can slow everything so it is better to just give the save another chance in the case of issues
    try {
      CommitDto result = saveAndFlush(entity);
      long usedMemoryAfter = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
      log.info("Saved commit, hash: {}, heapUsedMB: {}, memoryDeltaMB: {}",
          entity.getHash(), usedMemoryAfter, (usedMemoryAfter - usedMemoryBefore));
      return result;
    } catch (Exception e) {
      log.warn("First attempt of saving commit {} failed with {}, retrying...", entity.getHash(), e.getMessage());
      return saveAndFlush(entity);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<CommitDto> getById(Long id) {
    return commitRepository.findByIdWithRelations(id).map(commit -> {
      // Load tags and metadata separately to avoid cartesian product
      loadCommitRelationships(commit, id, null);
      return gitMapper.commitToCommitDto(commit);
    });
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<CommitDto> findByHash(final String keyword) {
    return commitRepository.findByHashWithRelations(keyword).map(commit -> {
      // Load tags and metadata separately to avoid cartesian product
      loadCommitRelationships(commit, null, keyword);
      return gitMapper.commitToCommitDto(commit);
    });
  }

  /**
   * Load tags and metadata for a commit using separate queries to avoid cartesian product explosion.
   * This prevents memory issues when dealing with large collections (e.g., 2000 tags).
   *
   * @param commit the commit entity to load relationships for
   * @param id     the commit ID (if loading by ID)
   * @param hash   the commit hash (if loading by hash)
   */
  private void loadCommitRelationships(Commit commit, Long id, String hash) {
    // Load tags separately
    if (id != null) {
      commitRepository.findByIdWithTags(id).ifPresent(c -> commit.setTags(c.getTags()));
      commitRepository.findByIdWithMetadata(id).ifPresent(c -> commit.setMetadata(c.getMetadata()));
    } else if (hash != null) {
      commitRepository.findByHashWithTags(hash).ifPresent(c -> commit.setTags(c.getTags()));
      commitRepository.findByHashWithMetadata(hash).ifPresent(c -> commit.setMetadata(c.getMetadata()));
    }

    log.debug("Loaded commit {} with {} tags and {} metadata entries",
        commit.getHash(), commit.getTags().size(), commit.getMetadata().size());
  }

  @Transactional
  protected CommitDto saveAndFlush(CommitDto entity) {
    final Commit commit = gitMapper.commitDtoToCommit(entity);

    // Check if commit already exists
    Optional<Commit> existingCommit = commitRepository.findByHashWithRelations(commit.getHash());

    if (existingCommit.isPresent()) {
      // Load existing tags and metadata separately
      Commit existing = existingCommit.get();
      loadCommitRelationships(existing, null, commit.getHash());
      return gitMapper.commitToCommitDto(existing);
    }

    // New commit - normalize tags and metadata
    if (commit.getTags().size() > 1000) {
      log.debug("Commit {} has {} tags, which is unusually high and may cause performance issues",
          commit.getHash(), commit.getTags().size());
    }

    commit.setTags(normalizeTags(commit.getTags(), tagRepository));

    final Set<CommitMetadata> normalizedMetadata = normalizeMetadata(commit.getMetadata());
    commit.setMetadata(normalizedMetadata);

    commit.getDiffEntries().forEach(de -> de.setCommit(commit));

    // Set commit dynamic fields
    commit.setTotalImpactedFiles(commit.getDiffEntries().stream().map(DiffEntry::getOldPath).collect(Collectors.toSet()).size());
    commit.setTotalInsertedLine(commit.getDiffEntries().stream().map(DiffEntry::getInserted).reduce(Integer::sum).orElse(0));
    commit.setTotalDeletedLines(commit.getDiffEntries().stream().map(DiffEntry::getDeleted).reduce(Integer::sum).orElse(0));

    Commit savedEntity = commitRepository.saveAndFlush(commit);
    return gitMapper.commitToCommitDto(savedEntity);
  }

  private synchronized Set<CommitMetadata> normalizeMetadata(Set<CommitMetadata> metadataSet) {
    final Set<CommitMetadata> metadata = new HashSet<>();

    for (CommitMetadata md : metadataSet) {
      // Create a detached instance to avoid issues with the original entity
      // Read md from DB and if MD does not exist we create one and assign it to the commit
      CommitMetadata commitMD =
          commitMetadataRepository.findByNameAndValue(md.getName(), md.getValue())
              .orElseGet(() -> {
                try {
                  return commitMetadataRepository.saveAndFlush(md);
                } catch (org.springframework.dao.DataIntegrityViolationException e) {
                  // Another thread inserted it between our check and save - retry lookup
                  return commitMetadataRepository.findByNameAndValue(md.getName(), md.getValue())
                      .orElseThrow(() -> new RuntimeException("Failed to find or create metadata after retry", e));
                }
              });

      metadata.add(commitMD);
    }

    return metadata;
  }
}
