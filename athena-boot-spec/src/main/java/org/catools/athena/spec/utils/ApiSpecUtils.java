package org.catools.athena.spec.utils;

import lombok.RequiredArgsConstructor;
import org.catools.athena.spec.common.entity.ApiPath;
import org.catools.athena.spec.common.entity.ApiSpecMetadata;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ApiSpecUtils {

  public boolean notContains(Set<ApiSpecMetadata> specMetadata, ApiSpecMetadata metadata) {
    return specMetadata.stream().noneMatch(m2 ->
        m2.getName().equals(metadata.getName()) && m2.getValue().equals(metadata.getValue()));
  }

  public boolean notContains(Set<ApiPath> paths, ApiPath path) {
    return paths.stream().noneMatch(p2 -> p2.getMethod().equals(path.getMethod()) && p2.getUrl().equals(path.getUrl()));
  }
}
