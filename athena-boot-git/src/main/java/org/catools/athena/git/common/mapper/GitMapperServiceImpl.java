package org.catools.athena.git.common.mapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.common.exception.RecordNotFoundException;
import org.catools.athena.core.feign.service.CachedUserFeignService;
import org.catools.athena.git.common.entity.GitRepository;
import org.catools.athena.git.common.repository.GitRepositoryRepository;
import org.catools.athena.model.core.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@SuppressWarnings("unused")
public class GitMapperServiceImpl implements GitMapperService {

  @Autowired
  private CachedUserFeignService userFeignService;

  @Autowired
  private GitRepositoryRepository gitRepositoryRepository;

  /**
   * Get user id by username
   *
   * @param username
   */
  @Override
  @Transactional(readOnly = true)
  public Long getUserId(String username) {
    if (StringUtils.isBlank(username)) return null;
    return Optional.ofNullable(userFeignService.search(username).body())
        .map(UserDto::getId)
        .orElseThrow(() -> new RecordNotFoundException("user", "username", username));
  }

  /**
   * Get username by Id
   *
   * @param id
   */
  @Override
  @Transactional(readOnly = true)
  public String getUsername(Long id) {
    if (id == null) return null;
    return Optional.ofNullable(userFeignService.getById(id).body())
        .map(UserDto::getUsername)
        .orElseThrow(() -> new RecordNotFoundException("user", "id", id));
  }

  /**
   * Get GitRepository by name
   *
   * @param name
   */
  @Override
  @Transactional(readOnly = true)
  public GitRepository findRepositoryByName(String name) {
    if (StringUtils.isBlank(name)) return null;
    return gitRepositoryRepository.findByName(name).orElse(null);
  }
}
