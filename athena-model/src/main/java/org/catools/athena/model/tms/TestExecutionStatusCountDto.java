package org.catools.athena.model.tms;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class TestExecutionStatusCountDto implements Serializable {

  private String status;

  private Long count;
}