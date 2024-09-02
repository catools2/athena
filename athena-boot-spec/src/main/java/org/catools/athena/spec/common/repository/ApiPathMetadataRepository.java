package org.catools.athena.spec.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.spec.common.entity.ApiPathMetadata;
import org.springframework.transaction.annotation.Transactional;

@Hidden
@Transactional
public interface ApiPathMetadataRepository extends MetadataRepository<ApiPathMetadata> {
}
