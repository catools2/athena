package org.catools.athena.git.common.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.git.common.mapper.GitMapper;
import org.catools.athena.git.common.model.GitRepository;
import org.catools.athena.git.common.repository.GitRepositoryRepository;
import org.catools.athena.git.model.GitRepositoryDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GitRepositoryServiceImpl implements GitRepositoryService {
  private final GitRepositoryRepository gitRepositoryRepository;
  private final GitMapper gitMapper;

  @Override
  public Optional<GitRepositoryDto> getById(Long id) {
    return gitRepositoryRepository.findById(id).map(gitMapper::gitRepositoryToGitRepositoryDto);
  }

  @Override
  public Optional<GitRepositoryDto> search(String keyword) {
    return gitRepositoryRepository.findByNameOrUrl(keyword, keyword).map(gitMapper::gitRepositoryToGitRepositoryDto);
  }

  @Override
  public GitRepositoryDto saveOrUpdate(GitRepositoryDto entity) {
    final GitRepository entityToSave = gitRepositoryRepository.findByNameOrUrl(entity.getName(), entity.getUrl()).map(repo -> {
      repo.setName(entity.getName());
      repo.setUrl(entity.getUrl());
      repo.setLastSync(entity.getLastSync());
      return repo;
    }).orElseGet(() -> gitMapper.gitRepositoryDtoToGitRepository(entity));

    final GitRepository savedEntity = gitRepositoryRepository.saveAndFlush(entityToSave);
    return gitMapper.gitRepositoryToGitRepositoryDto(savedEntity);
  }
}
