package org.catools.athena.git.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.utils.RetryUtils;
import org.catools.athena.git.common.entity.GitRepository;
import org.catools.athena.git.common.mapper.GitMapper;
import org.catools.athena.git.common.repository.GitRepositoryRepository;
import org.catools.athena.model.git.GitRepositoryDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitRepositoryServiceImpl implements GitRepositoryService {
  private final GitRepositoryRepository gitRepositoryRepository;
  private final GitMapper gitMapper;

  @Override
  @Transactional(readOnly = true)
  public Optional<GitRepositoryDto> getById(Long id) {
    return gitRepositoryRepository.findById(id).map(gitMapper::gitRepositoryToGitRepositoryDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<GitRepositoryDto> findByNameOrUrl(final String keyword) {
    return gitRepositoryRepository.findByNameOrUrl(keyword, keyword).map(gitMapper::gitRepositoryToGitRepositoryDto);
  }

  @Override
  @Transactional
  public GitRepositoryDto saveOrUpdate(GitRepositoryDto entity) {
    log.debug("Saving entity: {}", entity);
    final GitRepository entityToSave = gitRepositoryRepository.findByNameOrUrl(entity.getName(), entity.getUrl()).map(repo -> {
      repo.setName(entity.getName());
      repo.setUrl(entity.getUrl());
      repo.setLastSync(entity.getLastSync());
      return repo;
    }).orElseGet(() -> gitMapper.gitRepositoryDtoToGitRepository(entity));

    final GitRepository savedEntity = RetryUtils.retry(3, 1000, integer -> gitRepositoryRepository.saveAndFlush(entityToSave));
    return gitMapper.gitRepositoryToGitRepositoryDto(savedEntity);
  }
}
