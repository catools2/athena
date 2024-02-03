package org.catools.athena.rest.kube.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.core.repository.MetadataRepository;
import org.catools.athena.rest.kube.model.PodMetadata;
import org.springframework.transaction.annotation.Transactional;

@Hidden
@Transactional
public interface PodMetadataRepository extends MetadataRepository<PodMetadata> {
}
