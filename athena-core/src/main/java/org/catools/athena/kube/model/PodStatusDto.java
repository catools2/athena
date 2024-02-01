package org.catools.athena.kube.model;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PodStatusDto {
  private Long id;

  @Size(max = 200, message = "The pod status name can be at most 200 character.")
  private String name;

  @Size(max = 200, message = "The pod status phase can be at most 200 character.")
  private String phase;

  @Size(max = 1000, message = "The pod status message can be at most 1000 character.")
  private String message;

  @Size(max = 1000, message = "The pod status reason can be at most 1000 character.")
  private String reason;

}
