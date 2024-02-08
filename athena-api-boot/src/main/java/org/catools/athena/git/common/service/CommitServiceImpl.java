package org.catools.athena.git.common.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.git.common.mapper.GitMapper;
import org.catools.athena.git.common.model.Commit;
import org.catools.athena.git.common.model.DiffEntry;
import org.catools.athena.git.common.repository.CommitMetadataRepository;
import org.catools.athena.git.common.repository.CommitRepository;
import org.catools.athena.git.common.repository.DiffEntryRepository;
import org.catools.athena.git.common.repository.TagRepository;
import org.catools.athena.git.model.CommitDto;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.catools.athena.core.utils.MetadataPersistentHelper.normalizeMetadata;
import static org.catools.athena.git.utils.GitPersistentHelper.normalizeTags;

@Service
@RequiredArgsConstructor
public class CommitServiceImpl implements CommitService {
  private final CommitMetadataRepository commitMetadataRepository;
  private final DiffEntryRepository diffEntryRepository;
  private final CommitRepository commitRepository;
  private final TagRepository tagRepository;
  private final GitMapper gitMapper;

  @Override
  public CommitDto save(CommitDto entity) {
    final Commit commit = gitMapper.commitDtoToCommit(entity);

    final Commit commitToSave = commitRepository.findByHash(commit.getHash()).map(c -> {
      c.setParentHash(commit.getParentHash());
      c.setShortMessage(commit.getShortMessage());
      c.setCommitTime(commit.getCommitTime());
      c.setParentCount(commit.getParentCount());
      c.setAuthor(commit.getAuthor());
      c.setCommitter(commit.getCommitter());
      c.setRepository(commit.getRepository());
      c.setInserted(commit.getInserted());
      c.setDeleted(commit.getDeleted());

      c.getDiffEntries().removeIf(d1 -> commit.getDiffEntries().stream().noneMatch(d2 -> d1.getOldPath().equals(d2.getOldPath())));
      c.getMetadata().removeIf(d1 -> commit.getMetadata().stream().noneMatch(d2 -> d1.getName().equals(d2.getName()) && d1.getValue().equals(d2.getValue())));
      c.getTags().removeIf(d1 -> commit.getTags().stream().noneMatch(d2 -> d1.getName().equals(d2.getName())));

      c.getDiffEntries().addAll(commit.getDiffEntries().stream().filter(d1 -> c.getDiffEntries().stream().noneMatch(d2 -> d1.getOldPath().equals(d2.getOldPath()))).collect(Collectors.toSet()));
      c.getMetadata().addAll(commit.getMetadata().stream().filter(d1 -> c.getMetadata().stream().noneMatch(d2 -> d1.getName().equals(d2.getName()) && d1.getValue().equals(d2.getValue()))).collect(Collectors.toSet()));
      c.getTags().addAll(commit.getTags().stream().filter(d1 -> c.getTags().stream().noneMatch(d2 -> d1.getName().equals(d2.getName()))).collect(Collectors.toSet()));
      return c;
    }).orElse(commit);

    commitToSave.setTags(normalizeTags(commitToSave.getTags(), tagRepository));
    commitToSave.setMetadata(normalizeMetadata(commitToSave.getMetadata(), commitMetadataRepository));
    commitToSave.setDiffEntries(normalizeDiffEntries(commitToSave));
    final Commit savedEntity = commitRepository.saveAndFlush(commitToSave);
    return gitMapper.commitToCommitDto(savedEntity);
  }


  @Override
  public Optional<CommitDto> getById(Long id) {
    return commitRepository.findById(id).map(gitMapper::commitToCommitDto);
  }

  @Override
  public Optional<CommitDto> search(String keyword) {
    return commitRepository.findByHash(keyword).map(gitMapper::commitToCommitDto);
  }

  public Set<DiffEntry> normalizeDiffEntries(Commit commit) {
    if (commit.getId() == null) return commit.getDiffEntries();

    final Set<DiffEntry> diffEntries = new HashSet<>();
    for (DiffEntry diff : commit.getDiffEntries()) {
      // Read md from DB and if MD does not exist we create one and assign it to the pipeline
      DiffEntry diffEntry =
          diffEntryRepository.findAllByCommitIdAndOldPathAndNewPathAndChangeTypeAndInsertedAndDeleted(
                  commit.getId(), diff.getOldPath(), diff.getNewPath(), diff.getChangeType(), diff.getInserted(), diff.getDeleted())
              .orElse(diff.setCommit(commit));
      diffEntries.add(diffEntry);
    }
    return diffEntries;
  }
}
