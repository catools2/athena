package org.catools.athena.git.common.mapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.common.exception.RecordNotFoundException;
import org.catools.athena.core.feign.UserFeignClient;
import org.catools.athena.core.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@SuppressWarnings("unused")
public class GitMapperServiceImpl implements GitMapperService {

  @Autowired
  private UserFeignClient userFeignClient;

  /**
   * Get user id by username
   *
   * @param username
   */
  @Override
  @Cacheable(value = "userIdByUsername", key = "#p0", condition = "#p0!=null", unless = "#result==null")
  public Long getUserId(String username) {
    if (StringUtils.isBlank(username)) return null;
    return Optional.ofNullable(userFeignClient.search(username).body())
        .map(UserDto::getId)
        .orElseThrow(() -> new RecordNotFoundException("user", "username", username));
  }

  /**
   * Get username by Id
   *
   * @param id
   */
  @Override
  @Cacheable(value = "userNameById", key = "#p0", condition = "#p0!=null", unless = "#result==null")
  public String getUsername(Long id) {
    if (id == null) return null;
    return Optional.ofNullable(userFeignClient.getById(id).body())
        .map(UserDto::getUsername)
        .orElseThrow(() -> new RecordNotFoundException("user", "id", id));
  }


  @CacheEvict(value = {"userIdByUsername", "userIdByUsername"}, allEntries = true)
  @Scheduled(fixedRate = 10 * 60 * 1000) // 10 minutes
  public void emptyCache() {
    // this is a scheduled timer to clean up caches
  }
}
