package org.catools.athena.git.utils;

import lombok.experimental.UtilityClass;
import org.catools.athena.git.common.entity.Tag;
import org.catools.athena.git.common.repository.TagRepository;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class GitPersistentHelper {

  public static synchronized Set<Tag> normalizeTags(Set<Tag> tags, TagRepository tagRepository) {
    final Set<Tag> output = new HashSet<>();

    for (Tag tag : tags) {
      // Read tag from DB and if it does not exist we create one
      Tag normalizedTag = tagRepository.findByNameAndHash(tag.getName(), tag.getHash())
          .orElseGet(() -> {
            try {
              return tagRepository.saveAndFlush(tag);
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
              // Another thread inserted it between our check and save - retry lookup
              return tagRepository.findByNameAndHash(tag.getName(), tag.getHash())
                  .orElseThrow(() -> new RuntimeException("Failed to find or create tag after retry", e));
            }
          });
      output.add(normalizedTag);
    }

    tagRepository.flush();
    return output;
  }

}
