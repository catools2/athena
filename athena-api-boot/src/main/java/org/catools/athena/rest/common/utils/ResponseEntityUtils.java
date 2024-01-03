package org.catools.athena.rest.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.catools.athena.rest.core.config.AthenaCoreConstant.ROOT_API;

public class ResponseEntityUtils {
    public static <T> ResponseEntity<T> notFound() {
        return ResponseEntity.notFound().build();
    }

    public static <T> ResponseEntity<T> noContent() {
        return ResponseEntity.noContent().build();
    }

    public static <T> ResponseEntity<T> created(final String path, final Long id) {
        return ResponseEntity.created(getLocation(path, id)).build();
    }

    public static <T> ResponseEntity<T> ok(final T t) {
        return ResponseEntity.ok().body(t);
    }

    public static <T> ResponseEntity<T> alreadyReported(final String path, final Long id) {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).location(getLocation(path, id)).build();
    }

    public static Long getId(final URI location) {
        String[] parts = location.getPath().split("/");
        return Long.valueOf(parts[parts.length - 1]);
    }

    private static URI getLocation(final String path, final Long id) {
        return UriComponentsBuilder.fromPath(ROOT_API).pathSegment(StringUtils.stripStart(path, "/"), String.valueOf(id)).build().toUri();
    }
}
