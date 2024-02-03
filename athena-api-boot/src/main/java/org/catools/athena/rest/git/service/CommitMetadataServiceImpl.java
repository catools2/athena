package org.catools.athena.rest.git.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.rest.git.mapper.GitMapper;
import org.catools.athena.rest.git.model.CommitMetadata;
import org.catools.athena.rest.git.repository.CommitMetadataRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommitMetadataServiceImpl implements CommitMetadataService {
  private final CommitMetadataRepository commitMetadataRepository;
  private final GitMapper gitMapper;

  @Override
  public MetadataDto save(MetadataDto entity) {
    final CommitMetadata entityToSave = gitMapper.metadataDtoToCommitMetadata(entity);
    final CommitMetadata savedEntity = commitMetadataRepository.saveAndFlush(entityToSave);
    return gitMapper.commitMetadataToMetadataDto(savedEntity);
  }

  @Override
  public Optional<MetadataDto> getById(Long id) {
    return commitMetadataRepository.findById(id).map(gitMapper::commitMetadataToMetadataDto);
  }

  @Override
  public Optional<MetadataDto> search(String name, String value) {
    return commitMetadataRepository.findByNameAndValue(name, value).map(gitMapper::commitMetadataToMetadataDto);
  }
}
