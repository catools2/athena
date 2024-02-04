package org.catools.athena.apispec.utils;

import lombok.RequiredArgsConstructor;
import org.catools.athena.apispec.common.entity.ApiPath;
import org.catools.athena.apispec.common.entity.ApiSpec;
import org.catools.athena.apispec.common.entity.ApiSpecMetadata;
import org.catools.athena.apispec.common.repository.ApiPathRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ApiSpecUtils {

  private final ApiPathRepository apiPathRepository;

  public boolean notContains(Set<ApiSpecMetadata> specMetadata, ApiSpecMetadata metadata) {
    return specMetadata.stream().noneMatch(m2 ->
        m2.getName().equals(metadata.getName()) &&
            m2.getValue().equals(metadata.getValue()));
  }

  public boolean notContains(Set<ApiPath> paths, ApiPath path) {
    return paths.stream().noneMatch(p2 -> p2.getMethod().equals(path.getMethod()) &&
        p2.getUrl().equals(path.getUrl()) &&
        p2.getDescription().equals(path.getDescription()));
  }

  public void normalizeApiPaths(ApiSpec apiSpec) {
    if (apiSpec.getId() == null) return;

    final Set<ApiPath> paths = new HashSet<>();
    for (ApiPath path : apiSpec.getPaths()) {
      // Read md from DB and if MD does not exist we create one and assign it to the pipeline
      ApiPath apiPath =
          apiPathRepository.findBySpecIdAndUrlAndMethodAndTitle(apiSpec.getId(), path.getUrl(), path.getMethod(), path.getTitle())
              .orElse(path);
      paths.add(apiPath);
    }
    apiSpec.setPaths(paths);
  }
}
