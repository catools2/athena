package org.catools.athena.core.utils;

import lombok.experimental.UtilityClass;
import org.catools.athena.common.utils.RetryUtil;
import org.catools.athena.core.common.repository.MetadataRepository;
import org.catools.athena.core.model.NameValuePair;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class MetadataPersistentHelper {

  public synchronized static <T extends NameValuePair> Set<T> normalizeMetadata(Set<T> metadataSet, MetadataRepository<T> metadataRepository) {
    final Set<T> metadata = new HashSet<>();

    for (T md : metadataSet) {
      // Read md from DB and if MD does not exist we create one and assign it to the pipeline
      T pipelineMD =
          metadataRepository.findByNameAndValue(md.getName(), md.getValue())
              .orElseGet(() -> RetryUtil.retry(3, 1000, integer -> metadataRepository.saveAndFlush(md)));

      metadata.add(pipelineMD);
    }

    return metadata;
  }
}
