package org.catools.athena.kube.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;


@Data
@Accessors(chain = true)
public class ContainerStateDto {

  private Long id;

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

}
