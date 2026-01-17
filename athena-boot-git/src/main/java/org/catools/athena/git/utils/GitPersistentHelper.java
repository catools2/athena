package org.catools.athena.git.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.git.common.entity.Tag;
import org.catools.athena.git.common.repository.TagRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@UtilityClass
public class GitPersistentHelper {

  private static final int BATCH_SIZE = 100;

  public static synchronized Set<Tag> normalizeTags(Set<Tag> tags, TagRepository tagRepository) {
    final Set<Tag> output = new HashSet<>();

    if (tags.isEmpty()) {
      return output;
    }

    log.debug("Normalizing {} tags", tags.size());

    // Process tags in batches to reduce database round trips
    List<Tag> tagList = new ArrayList<>(tags);
    for (int i = 0; i < tagList.size(); i += BATCH_SIZE) {
      int end = Math.min(i + BATCH_SIZE, tagList.size());
      List<Tag> batch = tagList.subList(i, end);

      for (Tag tag : batch) {
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

      // Flush after each batch to free up memory
      if (end < tagList.size()) {
        tagRepository.flush();
      }
    }

    tagRepository.flush();
    log.debug("Normalized {} tags successfully", output.size());
    return output;
  }

}
