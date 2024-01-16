package org.catools.athena.rest.pipeline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Pipeline with provided id does not exists!")
public class PipelineNotExistsException extends RuntimeException {
    public PipelineNotExistsException() {
        super();
    }
}
