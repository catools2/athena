package org.catools.athena.tms.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.tms.common.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Hidden
public interface PriorityRepository extends JpaRepository<Priority, Long> {

  Optional<Priority> findByCodeOrName(String code, String name);
}
