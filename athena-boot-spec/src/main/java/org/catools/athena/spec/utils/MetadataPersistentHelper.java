package org.catools.athena.spec.utils;

import lombok.experimental.UtilityClass;
import org.catools.athena.model.core.NameValuePair;
import org.catools.athena.spec.common.repository.MetadataRepository;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class MetadataPersistentHelper {

  public static synchronized <T extends NameValuePair> Set<T> normalizeMetadata(Set<T> metadataSet, MetadataRepository<T> metadataRepository) {
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
