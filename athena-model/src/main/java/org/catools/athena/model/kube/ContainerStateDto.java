package org.catools.athena.model.kube;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.common.markers.IdRequired;

import java.time.Instant;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ContainerStateDto {

  @NotNull(groups = IdRequired.class, message = "The id must be provided.")
  private Long id;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant syncTime;

  @NotBlank(message = "The container state type must be provided.")
  @Size(max = 100, message = "The container state type can be at most 100 character.")
  private String type;

  @NotBlank(message = "The container state message must be provided.")
  @Size(max = 1000, message = "The container state message can be at most 1000 character.")
  private String message;

  @NotBlank(message = "The container state value must be provided.")
  @Size(max = 1000, message = "The container state value can be at most 1000 character.")
  private String value;

  public ContainerStateDto(Instant syncTime, String type, String message, String value) {
    this.syncTime = syncTime;
    this.type = type;
    this.message = message;
    this.value = value;
  }
}
