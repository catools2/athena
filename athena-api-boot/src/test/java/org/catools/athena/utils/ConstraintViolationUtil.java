package org.catools.athena.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.experimental.UtilityClass;
import org.junit.jupiter.api.function.Executable;

import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Utilities to facilitate validation of Constraint Violation exceptions during unit testing
 */
@UtilityClass
public class ConstraintViolationUtil {
    public static void assertThrowsConstraintViolation(Executable executable, String propertyPath, String message) {
        final ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, executable);
        ConstraintViolationUtil.assertViolation(exception, propertyPath, message);
    }

    private static void assertViolation(ConstraintViolationException exception, String propertyPath, String message) {
        assertThat(exception.getConstraintViolations()
                .stream()
                .filter(ex -> hasPath(ex, propertyPath))
                .map(ConstraintViolation::getMessage).collect(Collectors.toSet()), contains(message));
    }

    private static boolean hasPath(ConstraintViolation<?> violation, String propertyPath) {
        return violation.getPropertyPath() != null && violation.getPropertyPath().toString().equals(propertyPath);
    }
}
