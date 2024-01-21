package org.catools.athena.rest.apispec.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.apispec.entity.ApiPathMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Transactional
public interface ApiPathMetadataRepository extends JpaRepository<ApiPathMetadata, Long> {

    Optional<ApiPathMetadata> findByNameAndValue(String name, String value);
}
