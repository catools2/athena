package org.catools.athena.kube.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
public class ContainerStateDto {

  private Long id;
  private LocalDateTime syncTime;
  private String type;
  private String message;
  private String value;

}
