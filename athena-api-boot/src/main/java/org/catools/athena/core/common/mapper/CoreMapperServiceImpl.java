package org.catools.athena.core.common.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.core.common.entity.*;
import org.catools.athena.core.common.repository.*;
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
  public Project getProjectByCode(String code) {
    if (StringUtils.isBlank(code)) return null;
    return projectRepository.findByCode(code).orElse(null);
  }

  @Override
  public Environment getEnvironmentByCode(String code) {
    if (StringUtils.isBlank(code)) return null;
    return environmentRepository.findByCode(code).orElse(null);
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
    return userRepository.findByUsername(keyword).orElseGet(() -> userAliasRepository.findByAlias(keyword).map(UserAlias::getUser).orElse(null));
  }

  @Override
  public Version getVersionByCode(String code) {
    if (StringUtils.isBlank(code)) return null;
    return versionRepository.findByCode(code).orElse(null);
  }

  @Override
  public Set<Version> getVersionsByCode(Set<String> codes) {
    return codes.stream().map(this::getVersionByCode).collect(Collectors.toSet());
  }

  @Override
  public Set<String> getVersionCodesFromVersions(Set<Version> versions) {
    return versions.stream().map(Version::getCode).collect(Collectors.toSet());
  }

  @CacheEvict(value = {"userCache"}, allEntries = true)
  @Scheduled(fixedRate = 60 * 1000)
  @SuppressWarnings("unused")
  public void emptyCache() {
    log.debug("Emptying Core mapper cache...");
  }

}
