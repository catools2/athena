package org.catools.athena.rest.git.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.git.model.CommitDto;
import org.catools.athena.rest.git.mapper.GitMapper;
import org.catools.athena.rest.git.model.Commit;
import org.catools.athena.rest.git.repository.CommitRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommitServiceImpl implements CommitService {
  private final CommitRepository commitRepository;
  private final GitMapper gitMapper;

  @Override
  public CommitDto save(CommitDto entity) {
    final Commit entityToSave = gitMapper.commitDtoToCommit(entity);
    final Commit savedEntity = commitRepository.saveAndFlush(entityToSave);
    return gitMapper.commitToCommitDto(savedEntity);
  }

  @Override
  public Set<CommitDto> getAll() {
    return commitRepository.findAll().stream().map(gitMapper::commitToCommitDto).collect(Collectors.toSet());
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
