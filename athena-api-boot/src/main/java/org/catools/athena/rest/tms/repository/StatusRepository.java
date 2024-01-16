package org.catools.athena.rest.tms.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.tms.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Hidden
public interface StatusRepository extends JpaRepository<Status, Long> {

    Optional<Status> findByCode(String code);
}
