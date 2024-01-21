package org.catools.athena.rest.apispec.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.apispec.entity.ApiSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Transactional
public interface ApiSpecRepository extends JpaRepository<ApiSpec, Long> {

    Optional<ApiSpec> findByProjectCodeAndName(String projectCode, String name);
}
