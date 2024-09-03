package org.catools.athena.spec.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.spec.common.entity.ApiSpecMetadata;
import org.springframework.transaction.annotation.Transactional;

@Hidden
@Transactional
public interface ApiSpecMetadataRepository extends MetadataRepository<ApiSpecMetadata> {
}
