package org.catools.athena.core.common.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.core.common.entity.AppVersion;
import org.catools.athena.core.common.entity.Environment;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.core.common.entity.User;
import org.catools.athena.core.common.entity.UserAlias;
import org.catools.athena.core.common.repository.EnvironmentRepository;
import org.catools.athena.core.common.repository.ProjectRepository;
import org.catools.athena.core.common.repository.UserAliasRepository;
import org.catools.athena.core.common.repository.UserRepository;
import org.catools.athena.core.common.repository.VersionRepository;
import org.catools.athena.pipeline.common.entity.Pipeline;
import org.catools.athena.pipeline.common.repository.PipelineRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoreMapperServiceImpl implements CoreMapperService {

  private final PipelineRepository pipelineRepository;
  private final EnvironmentRepository environmentRepository;
  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;
  private final UserAliasRepository userAliasRepository;
  private final VersionRepository versionRepository;

  @SuppressWarnings("notused")
  @Override
  public Project getProject(String keyword) {
    if (StringUtils.isBlank(keyword)) return null;
    return projectRepository.findByCode(keyword).orElse(null);
  }

  @Override
  public Environment getEnvironment(String keyword) {
    if (StringUtils.isBlank(keyword)) return null;
    return environmentRepository.findByCodeOrName(keyword, keyword).orElse(null);
  }

  @Override
  public Pipeline getPipelineById(Long pipelineId) {
    if (pipelineId == null) return null;
    return pipelineRepository.findById(pipelineId).orElse(null);
  }

  @Cacheable(value = "userCache", key = "#p0", condition = "#p0!=null", unless = "#result==null")
  @Override
  public User search(String keyword) {
    if (StringUtils.isBlank(keyword)) return null;
    return userRepository.findByUsernameIgnoreCase(keyword).orElseGet(() -> userAliasRepository.findByAliasIgnoreCase(keyword).map(UserAlias::getUser).orElse(null));
  }

  @Override
  public AppVersion getVersion(String keyword) {
    if (StringUtils.isBlank(keyword)) return null;
    return versionRepository.findByCodeOrName(keyword, keyword).orElse(null);
  }


  @Override
  public Set<String> getVersionCodesFromVersions(Set<AppVersion> appVersions) {
    return appVersions.stream().map(AppVersion::getCode).collect(Collectors.toSet());
  }

  @CacheEvict(value = {"userCache"}, allEntries = true)
  @Scheduled(fixedRate = 60 * 1000)
  public void emptyCache() {
    // this is a scheduled timer to clean up caches
  }

}
