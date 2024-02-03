package org.catools.athena.rest.apispec.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.apispec.entity.ApiPathMetadata;
import org.catools.athena.rest.core.repository.MetadataRepository;
import org.springframework.transaction.annotation.Transactional;

@Hidden
@Transactional
public interface ApiPathMetadataRepository extends MetadataRepository<ApiPathMetadata> {
}
