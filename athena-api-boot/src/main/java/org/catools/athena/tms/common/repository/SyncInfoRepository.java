package org.catools.athena.tms.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.tms.common.entity.SyncInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Transactional
public interface SyncInfoRepository extends JpaRepository<SyncInfo, Long> {

  Optional<SyncInfo> findByActionAndComponentAndProjectId(String action, String component, Long projectId);

}
