package org.catools.athena.rest.git.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.git.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Transactional
public interface TagRepository extends JpaRepository<Tag, Long> {

  Optional<Tag> findByNameOrHash(String name, String hash);

}
