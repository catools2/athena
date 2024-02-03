package org.catools.athena.rest.core.utils;

import lombok.experimental.UtilityClass;
import org.catools.athena.core.model.NameValuePair;
import org.catools.athena.rest.core.repository.MetadataRepository;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class MetadataPersistentHelper {

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
