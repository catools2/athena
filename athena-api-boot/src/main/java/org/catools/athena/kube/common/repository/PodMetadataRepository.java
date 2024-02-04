package org.catools.athena.kube.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.core.common.repository.MetadataRepository;
import org.catools.athena.kube.common.model.PodMetadata;
import org.springframework.transaction.annotation.Transactional;

@Hidden
@Transactional
public interface PodMetadataRepository extends MetadataRepository<PodMetadata> {
}
