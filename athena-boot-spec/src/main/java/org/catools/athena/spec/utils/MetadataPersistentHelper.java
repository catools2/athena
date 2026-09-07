package org.catools.athena.spec.utils;

import lombok.experimental.UtilityClass;
import org.catools.athena.model.core.NameValuePair;
import org.catools.athena.spec.common.repository.MetadataRepository;

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
              .orElseGet(() -> {
                // Let the database absorb the race. A plain insert here would abort the surrounding
                // transaction on conflict, and a recovery lookup issued afterwards could never run --
                // PostgreSQL refuses every statement in an aborted transaction, which surfaced as an
                // opaque Hibernate "null identifier" assertion rather than the 23505 that caused it.
                metadataRepository.insertIfAbsent(md.getName(), md.getValue());
                return metadataRepository.findByNameAndValue(md.getName(), md.getValue())
                    .orElseThrow(() -> new IllegalStateException(
                        "Metadata (" + md.getName() + ") could not be read back after insert"));
              });

      metadata.add(pipelineMD);
    }

    return metadata;
  }
}
