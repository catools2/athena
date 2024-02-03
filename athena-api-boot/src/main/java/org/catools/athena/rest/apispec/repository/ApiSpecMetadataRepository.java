package org.catools.athena.rest.apispec.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.apispec.entity.ApiSpecMetadata;
import org.catools.athena.rest.core.repository.MetadataRepository;
import org.springframework.transaction.annotation.Transactional;

@Hidden
@Transactional
public interface ApiSpecMetadataRepository extends MetadataRepository<ApiSpecMetadata> {
}
