package org.catools.athena.rest.common.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class ResponseEntityUtils {
    public static <T> ResponseEntity<T> notFound() {
        return ResponseEntity.notFound().build();
    }

    public static <T> ResponseEntity<T> noContent() {
        return ResponseEntity.noContent().build();
    }

    public static <T> ResponseEntity<T> created(final Long id) {
        return ResponseEntity.created(getLocation(id)).build();
    }

    public static <T> ResponseEntity<T> ok(final T t) {
        return ResponseEntity.ok().body(t);
    }

    public static <T> ResponseEntity<T> alreadyReported(final Long id) {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).location(getLocation(id)).build();
    }

    public static Long getId(final URI location) {
        return Long.valueOf(location.getFragment());
    }

    private static URI getLocation(final Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
