package org.catools.athena.rest.tms.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.core.repository.MetadataRepository;
import org.catools.athena.rest.tms.entity.ItemMetadata;
import org.springframework.transaction.annotation.Transactional;

@Hidden
@Transactional
public interface ItemMetadataRepository extends MetadataRepository<ItemMetadata> {
}
