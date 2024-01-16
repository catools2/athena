package org.catools.athena.rest.common.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String projectCode) {
        super("Project not found!, project code: " + projectCode);
    }
}
