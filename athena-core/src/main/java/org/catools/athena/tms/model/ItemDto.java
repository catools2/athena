package org.catools.athena.tms.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.MetadataDto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ItemDto implements Serializable {

  private Long id;
  private String code;
  private String name;
  private Instant createdOn;
  private String createdBy;
  private Instant updatedOn;
  private String updatedBy;
  private String type;
  private String status;
  private String priority;
  private String project;
  private Set<String> versions = new HashSet<>();
  private Set<MetadataDto> metadata = new HashSet<>();

}
