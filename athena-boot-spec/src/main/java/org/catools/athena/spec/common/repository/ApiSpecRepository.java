package org.catools.athena.spec.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.spec.common.entity.ApiSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Transactional
public interface ApiSpecRepository extends JpaRepository<ApiSpec, Long> {

  Optional<ApiSpec> findByProjectIdAndName(Long projectId, String name);
}
