package org.catools.athena.git.common.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.git.common.mapper.GitMapper;
import org.catools.athena.git.common.model.Commit;
import org.catools.athena.git.common.repository.CommitMetadataRepository;
import org.catools.athena.git.common.repository.CommitRepository;
import org.catools.athena.git.common.repository.TagRepository;
import org.catools.athena.git.model.CommitDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.catools.athena.core.utils.MetadataPersistentHelper.normalizeMetadata;
import static org.catools.athena.core.utils.MetadataPersistentHelper.normalizeTags;

@Service
@RequiredArgsConstructor
public class CommitServiceImpl implements CommitService {
  private final CommitMetadataRepository commitMetadataRepository;
  private final CommitRepository commitRepository;
  private final TagRepository tagRepository;
  private final GitMapper gitMapper;

  @Override
  public CommitDto save(CommitDto entity) {
    final Commit entityToSave = gitMapper.commitDtoToCommit(entity);
    entityToSave.setTags(normalizeTags(entityToSave.getTags(), tagRepository));
    entityToSave.setMetadata(normalizeMetadata(entityToSave.getMetadata(), commitMetadataRepository));
    final Commit savedEntity = commitRepository.saveAndFlush(entityToSave);
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
}
