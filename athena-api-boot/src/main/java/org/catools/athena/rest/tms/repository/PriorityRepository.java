package org.catools.athena.rest.tms.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.tms.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Transactional
public interface PriorityRepository extends JpaRepository<Priority, Long> {

    Optional<Priority> findByCode(String code);
}
