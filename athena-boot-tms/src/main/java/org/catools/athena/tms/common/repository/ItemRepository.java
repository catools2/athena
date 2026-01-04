package org.catools.athena.tms.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.tms.common.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Hidden
public interface ItemRepository extends JpaRepository<Item, Long> {

  Optional<Item> findByCode(String code);

  /**
   * Fetch Item with all related entities eagerly loaded to avoid N+1 queries.
   * This query uses LEFT JOIN FETCH to load Item, ItemType, Status, Priority,
   * and StatusTransitions in a single database roundtrip.
   *
   * @param code the item code
   * @return Optional containing Item with all relations loaded, or empty if not found
   */
  @Query("SELECT DISTINCT i FROM Item i " +
         "LEFT JOIN FETCH i.type " +
         "LEFT JOIN FETCH i.status " +
         "LEFT JOIN FETCH i.priority " +
         "LEFT JOIN FETCH i.statusTransitions " +
         "WHERE i.code = ?1")
  Optional<Item> findByCodeWithRelations(String code);

  /**
   * Fetch Item by ID with all related entities eagerly loaded to avoid N+1 queries.
   * This query uses LEFT JOIN FETCH to load Item, ItemType, Status, Priority,
   * and StatusTransitions in a single database roundtrip.
   *
   * @param id the item ID
   * @return Optional containing Item with all relations loaded, or empty if not found
   */
  @Query("SELECT DISTINCT i FROM Item i " +
         "LEFT JOIN FETCH i.type " +
         "LEFT JOIN FETCH i.status " +
         "LEFT JOIN FETCH i.priority " +
         "LEFT JOIN FETCH i.statusTransitions " +
         "WHERE i.id = ?1")
  Optional<Item> findByIdWithRelations(Long id);

}
