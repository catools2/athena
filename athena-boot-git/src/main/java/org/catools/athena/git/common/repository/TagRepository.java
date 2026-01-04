package org.catools.athena.git.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.git.common.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Hidden
public interface TagRepository extends JpaRepository<Tag, Long> {

  Optional<Tag> findByNameAndHash(String name, String hash);

}
