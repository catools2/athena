package org.catools.athena.tms.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.tms.common.entity.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Hidden
public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {

  Optional<ItemType> findByCodeOrName(String code, String name);
}
