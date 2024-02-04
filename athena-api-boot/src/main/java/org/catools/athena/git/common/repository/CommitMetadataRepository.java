package org.catools.athena.git.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.core.common.repository.MetadataRepository;
import org.catools.athena.git.common.model.CommitMetadata;
import org.springframework.transaction.annotation.Transactional;

@Hidden
@Transactional
public interface CommitMetadataRepository extends MetadataRepository<CommitMetadata> {

}
