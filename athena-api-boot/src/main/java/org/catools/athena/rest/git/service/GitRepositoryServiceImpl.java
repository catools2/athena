package org.catools.athena.rest.git.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.git.model.GitRepositoryDto;
import org.catools.athena.rest.git.mapper.GitMapper;
import org.catools.athena.rest.git.model.GitRepository;
import org.catools.athena.rest.git.repository.GitRepositoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GitRepositoryServiceImpl implements GitRepositoryService {
  private final GitRepositoryRepository gitRepositoryRepository;
  private final GitMapper gitMapper;

  @Override
  public GitRepositoryDto save(GitRepositoryDto entity) {
    final GitRepository entityToSave = gitMapper.gitRepositoryDtoToGitRepository(entity);
    final GitRepository savedEntity = gitRepositoryRepository.saveAndFlush(entityToSave);
    return gitMapper.gitRepositoryToGitRepositoryDto(savedEntity);
  }

  @Override
  public Set<GitRepositoryDto> getAll() {
    return gitRepositoryRepository.findAll().stream().map(gitMapper::gitRepositoryToGitRepositoryDto).collect(Collectors.toSet());
  }

  @Override
  public Optional<GitRepositoryDto> getById(Long id) {
    return gitRepositoryRepository.findById(id).map(gitMapper::gitRepositoryToGitRepositoryDto);
  }

  @Override
  public Optional<GitRepositoryDto> search(String keyword) {
    return gitRepositoryRepository.findByNameOrUrl(keyword, keyword).map(gitMapper::gitRepositoryToGitRepositoryDto);
  }
}
