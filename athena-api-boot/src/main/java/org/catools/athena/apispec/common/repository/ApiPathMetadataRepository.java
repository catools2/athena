package org.catools.athena.apispec.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.apispec.common.entity.ApiPathMetadata;
import org.catools.athena.core.common.repository.MetadataRepository;
import org.springframework.transaction.annotation.Transactional;

@Hidden
@Transactional
public interface ApiPathMetadataRepository extends MetadataRepository<ApiPathMetadata> {
}
