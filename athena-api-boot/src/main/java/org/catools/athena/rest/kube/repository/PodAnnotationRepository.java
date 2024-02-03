package org.catools.athena.rest.kube.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.core.repository.MetadataRepository;
import org.catools.athena.rest.kube.model.PodAnnotation;
import org.springframework.transaction.annotation.Transactional;

@Hidden
@Transactional
public interface PodAnnotationRepository extends MetadataRepository<PodAnnotation> {
}
