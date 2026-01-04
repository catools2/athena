package org.catools.athena.metric.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.metric.common.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Hidden
public interface ActionRepository extends JpaRepository<Action, Long> {
  Optional<Action> findByNameAndTypeAndTargetAndCommand(String name, String type, String target, String command);

}
