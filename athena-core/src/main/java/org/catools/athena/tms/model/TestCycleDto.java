package org.catools.athena.tms.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TestCycleDto implements Serializable {

    private Long id;
    private String code;
    private String name;
  private Instant startInstant;
  private Instant endInstant;
    private String version;

}
