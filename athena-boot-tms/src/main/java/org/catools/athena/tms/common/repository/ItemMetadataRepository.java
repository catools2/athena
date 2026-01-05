package org.catools.athena.tms.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.tms.common.entity.ItemMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Hidden
public interface ItemMetadataRepository extends JpaRepository<ItemMetadata, Long> {
  Optional<ItemMetadata> findByNameAndValue(String name, String value);
}
