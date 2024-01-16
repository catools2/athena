package org.catools.athena.tms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TestExecutionDto implements Serializable {

    private Long id;
    private LocalDateTime createdOn;
    private LocalDateTime executedOn;
    private String cycle;
    private String item;
    private String status;
    private String executor;
}
