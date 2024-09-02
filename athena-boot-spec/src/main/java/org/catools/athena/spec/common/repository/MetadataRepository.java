package org.catools.athena.spec.common.repository;

import org.catools.athena.core.model.NameValuePair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface MetadataRepository<T extends NameValuePair> extends JpaRepository<T, Long> {
  Optional<T> findByNameAndValue(String name, String value);
}
