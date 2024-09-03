package org.catools.athena.kube.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.kube.common.model.PodLabel;
import org.springframework.transaction.annotation.Transactional;

@Hidden
@Transactional
public interface PodLabelRepository extends MetadataRepository<PodLabel> {
}
