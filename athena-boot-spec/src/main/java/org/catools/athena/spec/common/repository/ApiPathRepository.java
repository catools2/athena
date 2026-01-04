package org.catools.athena.spec.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.spec.common.entity.ApiPath;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Hidden
public interface ApiPathRepository extends JpaRepository<ApiPath, Long> {

  Optional<ApiPath> findBySpecIdAndUrlAndMethodAndTitle(Long specId, String url, String method, String title);
}
