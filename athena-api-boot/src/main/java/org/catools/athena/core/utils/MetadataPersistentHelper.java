package org.catools.athena.core.utils;

import lombok.experimental.UtilityClass;
import org.catools.athena.core.common.repository.MetadataRepository;
import org.catools.athena.core.model.NameValuePair;
import org.catools.athena.git.common.model.Tag;
import org.catools.athena.git.common.repository.TagRepository;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class MetadataPersistentHelper {

  public static Set<Tag> normalizeTags(Set<Tag> tags, TagRepository tagRepository) {
    final Set<Tag> output = new HashSet<>();
    for (Tag tag : tags) {
      // Read md from DB and if MD does not exist we create one and assign it to the pipeline
      Tag pipelineMD =
          tagRepository.findByNameOrHash(tag.getName(), tag.getHash())
              .orElseGet(() -> tagRepository.saveAndFlush(tag));
      output.add(pipelineMD);
    }
    return output;
  }

  public static <T extends NameValuePair> Set<T> normalizeMetadata(Set<T> metadataSet, MetadataRepository<T> metadataRepository) {
    final Set<T> metadata = new HashSet<>();
    for (T md : metadataSet) {
      // Read md from DB and if MD does not exist we create one and assign it to the pipeline
      T pipelineMD =
          metadataRepository.findByNameAndValue(md.getName(), md.getValue())
              .orElseGet(() -> metadataRepository.saveAndFlush(md));
      metadata.add(pipelineMD);
    }
    return metadata;
  }
}
