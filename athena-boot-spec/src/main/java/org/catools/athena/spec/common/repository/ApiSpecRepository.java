package org.catools.athena.spec.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.spec.common.entity.ApiSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Hidden
public interface ApiSpecRepository extends JpaRepository<ApiSpec, Long> {

  String INVENTORY_FILTERS = """
      where (:projectId is null or s.projectId = :projectId)
        and (:name = '' or lower(s.name) like concat('%', :name, '%'))
        and (:title = '' or lower(s.title) like concat('%', :title, '%'))
        and (:version = '' or lower(s.version) like concat('%', :version, '%'))
      """;

  Optional<ApiSpec> findByProjectIdAndName(Long projectId, String name);

  @Query("""
      select
        s.id as id,
        s.projectId as projectId,
        s.name as name,
        s.title as title,
        s.version as version,
        s.firstTimeSeen as firstTimeSeen,
        s.lastSyncTime as lastSyncTime,
        size(s.paths) as pathCount
      from ApiSpec s
      """ + INVENTORY_FILTERS)
  Page<ApiSpecInventoryProjection> findInventory(
      @Param("projectId") Long projectId,
      @Param("name") String name,
      @Param("title") String title,
      @Param("version") String version,
      Pageable pageable
  );

  @Query("""
      select
        count(distinct s.id) as specCount,
        count(distinct s.projectId) as projectCount,
        count(p.id) as pathCount,
        max(s.lastSyncTime) as latestSyncTime
      from ApiSpec s
      left join s.paths p
      """ + INVENTORY_FILTERS)
  ApiSpecSummaryProjection summarizeInventory(
      @Param("projectId") Long projectId,
      @Param("name") String name,
      @Param("title") String title,
      @Param("version") String version
  );

  @Query("""
      select count(s.id)
      from ApiSpec s
      """ + INVENTORY_FILTERS + """
        and (s.lastSyncTime is null or s.lastSyncTime < :staleBefore)
      """)
  Long countStaleSpecs(
      @Param("projectId") Long projectId,
      @Param("name") String name,
      @Param("title") String title,
      @Param("version") String version,
      @Param("staleBefore") java.time.Instant staleBefore
  );
}
