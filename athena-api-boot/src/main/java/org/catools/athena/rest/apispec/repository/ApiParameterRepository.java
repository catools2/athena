package org.catools.athena.rest.apispec.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.apispec.entity.ApiParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Hidden
public interface ApiParameterRepository extends JpaRepository<ApiParameter, Long> {

    Optional<ApiParameter> findByNameAndType(String name, String type);
}
