package org.catools.athena.rest.common.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.catools.athena.rest.core.config.CorePathDefinitions.ROOT_API;

@UtilityClass
public class ResponseEntityUtils {

  public static final String ENTITY_ID = "entity_id";

  public static <T> ResponseEntity<Set<T>> okOrNoContent(final Set<T> t) {
    return t.isEmpty() ? noContent() : ok(t);
  }

  public static <T> ResponseEntity<T> okOrNoContent(final Optional<T> t) {
    return t.map(ResponseEntityUtils::ok).orElseGet(ResponseEntityUtils::noContent);
  }

  public static <T> ResponseEntity<T> noContent() {
    return ResponseEntity.noContent().build();
  }

  public static <T> ResponseEntity<T> created(final String path, final Long id) {
    return ResponseEntity.created(getLocation(path, id))
        .header(ENTITY_ID, String.valueOf(id))
        .build();
  }

  public static <T> ResponseEntity<T> ok(final T t) {
    return ResponseEntity.ok().body(t);
  }

  public static <T> ResponseEntity<T> alreadyReported(final String path, final Long id) {
    return ResponseEntity
        .status(HttpStatus.ALREADY_REPORTED)
        .location(getLocation(path, id))
        .header(ENTITY_ID, String.valueOf(id))
        .build();
  }

  public static Long getId(final URI location) {
    String[] parts = location.getPath().split("/");
    return Long.valueOf(parts[parts.length - 1]);
  }

  public static Long getEntityId(final ResponseEntity<?> response) {
    List<String> header = response.getHeaders().get(ENTITY_ID);
    if (header == null || header.isEmpty())
      return null;
    return Long.valueOf(header.get(0));
  }

  private static URI getLocation(final String path, final Long id) {
    return UriComponentsBuilder
        .fromPath(ROOT_API)
        .pathSegment(StringUtils.stripStart(path, "/"), String.valueOf(id))
        .build()
        .toUri();
  }
}
