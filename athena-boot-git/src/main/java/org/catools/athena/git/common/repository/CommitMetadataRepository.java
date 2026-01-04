package org.catools.athena.git.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.git.common.entity.CommitMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Hidden
public interface CommitMetadataRepository extends JpaRepository<CommitMetadata, Long> {

  Optional<CommitMetadata> findByNameAndValue(String name, String value);

}
