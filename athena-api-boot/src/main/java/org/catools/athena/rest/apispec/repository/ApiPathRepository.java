package org.catools.athena.rest.apispec.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.apispec.entity.ApiPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Hidden
public interface ApiPathRepository extends JpaRepository<ApiPath, Long> {

    Optional<ApiPath> findByApiSpecIdAndMethodAndUrl(Long apiSpecId, String method, String url);
}
