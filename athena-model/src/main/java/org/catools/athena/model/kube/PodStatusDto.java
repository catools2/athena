package org.catools.athena.model.kube;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.common.markers.IdRequired;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PodStatusDto {
  @NotNull(groups = IdRequired.class, message = "The id must be provided.")
  private Long id;

  @Size(max = 200, message = "The pod status name can be at most 200 character.")
  private String name;

  @Size(max = 200, message = "The pod status phase can be at most 200 character.")
  private String phase;

  @Size(max = 1000, message = "The pod status message can be at most 1000 character.")
  private String message;

  @Size(max = 1000, message = "The pod status reason can be at most 1000 character.")
  private String reason;

  public PodStatusDto(String name, String phase, String message, String reason) {
    this.name = name;
    this.phase = phase;
    this.message = message;
    this.reason = reason;
  }
}
