package org.catools.athena.rest.git.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.core.repository.MetadataRepository;
import org.catools.athena.rest.git.model.CommitMetadata;
import org.springframework.transaction.annotation.Transactional;

@Hidden
@Transactional
public interface CommitMetadataRepository extends MetadataRepository<CommitMetadata> {

}
