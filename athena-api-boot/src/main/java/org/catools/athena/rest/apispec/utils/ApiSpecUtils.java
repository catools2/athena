package org.catools.athena.rest.apispec.utils;

import lombok.RequiredArgsConstructor;
import org.catools.athena.rest.apispec.entity.ApiPath;
import org.catools.athena.rest.apispec.entity.ApiPathMetadata;
import org.catools.athena.rest.apispec.entity.ApiSpec;
import org.catools.athena.rest.apispec.entity.ApiSpecMetadata;
import org.catools.athena.rest.apispec.repository.ApiPathMetadataRepository;
import org.catools.athena.rest.apispec.repository.ApiSpecMetadataRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ApiSpecUtils {
  private final ApiSpecMetadataRepository apiSpecMetadataRepository;
  private final ApiPathMetadataRepository apiPathMetadataRepository;

  public void normalizeApiSpecMetadata(ApiSpec apiSpec) {
    final Set<ApiSpecMetadata> metadata = new HashSet<>();
    for (ApiSpecMetadata md : apiSpec.getMetadata()) {
      // Read md from DB and if MD does not exist we create one and assign it to the pipeline
      ApiSpecMetadata apiSpecMetadata =
          apiSpecMetadataRepository.findByNameAndValue(md.getName(), md.getValue())
              .orElseGet(() -> apiSpecMetadataRepository.saveAndFlush(md));
      metadata.add(apiSpecMetadata);
    }
    apiSpec.setMetadata(metadata);
  }

  public void normalizeApiPathMetadata(ApiPath apiPath) {
    final Set<ApiPathMetadata> metadata = new HashSet<>();
    for (ApiPathMetadata md : apiPath.getMetadata()) {
      // Read md from DB and if MD does not exist we create one and assign it to the pipeline
      ApiPathMetadata apiPathMetadata =
          apiPathMetadataRepository.findByNameAndValue(md.getName(), md.getValue())
              .orElseGet(() -> apiPathMetadataRepository.saveAndFlush(md));
      metadata.add(apiPathMetadata);
    }
    apiPath.setMetadata(metadata);
  }
}
