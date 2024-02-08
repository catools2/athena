package org.catools.athena.tms.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.core.common.repository.MetadataRepository;
import org.catools.athena.tms.common.entity.ItemMetadata;
import org.springframework.transaction.annotation.Transactional;

@Hidden
@Transactional
public interface ItemMetadataRepository extends MetadataRepository<ItemMetadata> {
}
