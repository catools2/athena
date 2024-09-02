package org.catools.athena.git.utils;

import lombok.experimental.UtilityClass;
import org.catools.athena.git.common.model.Tag;
import org.catools.athena.git.common.repository.TagRepository;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class GitPersistentHelper {

  public static synchronized Set<Tag> normalizeTags(Set<Tag> tags, TagRepository tagRepository) {
    final Set<Tag> output = new HashSet<>();

    for (Tag tag : tags) {
      // Read md from DB and if MD does not exist we create one and assign it to the pipeline
      Tag pipelineMD = tagRepository.findByNameOrHash(tag.getName(), tag.getHash())
          .orElseGet(() -> tagRepository.save(tag));
      output.add(pipelineMD);
    }

    tagRepository.flush();
    return output;
  }

}
