package org.catools.athena.kube.common.repository;

import org.catools.athena.model.core.NameValuePair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface MetadataRepository<T extends NameValuePair> extends JpaRepository<T, Long> {
  Optional<T> findByNameAndValue(String name, String value);

  /**
   * Declared here so the shared normalisation helper can call it generically; every concrete
   * sub-interface must supply the table-specific {@code ON CONFLICT DO NOTHING} insert via
   * {@code @Query}. A sub-interface that forgets fails at context startup rather than at the first
   * concurrent write, which is the failure mode this method exists to remove.
   */
  int insertIfAbsent(String name, String value);
}
