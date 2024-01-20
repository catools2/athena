package org.catools.athena.rest.kube.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.kube.model.PodLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Transactional
public interface PodLabelRepository extends JpaRepository<PodLabel, Long> {
    Optional<PodLabel> findByNameAndValue(String name, String value);
}
