package org.catools.athena.tms.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.MetadataDto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Data
@Accessors(chain = true)
public class ItemDto implements Serializable {

  private Long id;

  @NotBlank(message = "The item code must be provided.")
  @Size(max = 15, message = "The item code can be at most 15 character.")
  private String code;

  @NotBlank(message = "The item name must be provided.")
  @Size(max = 300, message = "The item name can be at most 300 character.")
  private String name;

  @NotNull(message = "The item created date/time must be provided.")
  private Instant createdOn;

  @NotNull(message = "The item created by must be provided.")
  private String createdBy;

  private Instant updatedOn;

  private String updatedBy;

  @NotNull(message = "The item type must be provided.")
  private String type;

  @NotNull(message = "The item status must be provided.")
  private String status;

  @NotNull(message = "The item priority must be provided.")
  private String priority;

  @NotNull(message = "The item project must be provided.")
  private String project;

  private Set<String> versions = new HashSet<>();
  private Set<MetadataDto> metadata = new HashSet<>();

}
