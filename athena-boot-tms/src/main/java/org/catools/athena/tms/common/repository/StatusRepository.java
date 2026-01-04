package org.catools.athena.tms.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.tms.common.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Hidden
public interface StatusRepository extends JpaRepository<Status, Long> {

  Optional<Status> findByCodeOrName(String code, String name);
}
