package org.catools.athena.tms.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TestCycleDto implements Serializable {

    private Long id;
    private String code;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String version;

}
