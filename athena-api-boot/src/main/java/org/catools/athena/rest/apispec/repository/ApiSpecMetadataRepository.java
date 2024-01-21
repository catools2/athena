package org.catools.athena.rest.apispec.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.apispec.entity.ApiSpecMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Transactional
public interface ApiSpecMetadataRepository extends JpaRepository<ApiSpecMetadata, Long> {

    Optional<ApiSpecMetadata> findByNameAndValue(String name, String value);
}
