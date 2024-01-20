package org.catools.athena.rest.kube.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.kube.model.PodAnnotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Transactional
public interface PodAnnotationRepository extends JpaRepository<PodAnnotation, Long> {
    Optional<PodAnnotation> findByNameAndValue(String name, String value);
}
