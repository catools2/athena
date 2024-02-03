package org.catools.athena.rest.git.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.git.model.CommitDto;
import org.catools.athena.rest.git.mapper.GitMapper;
import org.catools.athena.rest.git.model.Commit;
import org.catools.athena.rest.git.repository.CommitMetadataRepository;
import org.catools.athena.rest.git.repository.CommitRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.catools.athena.rest.core.utils.MetadataPersistentHelper.normalizeMetadata;

@Service
@RequiredArgsConstructor
public class CommitServiceImpl implements CommitService {
  private final CommitMetadataRepository commitMetadataRepository;
  private final CommitRepository commitRepository;
  private final GitMapper gitMapper;

  @Override
  public CommitDto save(CommitDto entity) {
    final Commit entityToSave = gitMapper.commitDtoToCommit(entity);
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
