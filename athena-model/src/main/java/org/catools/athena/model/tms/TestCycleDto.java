package org.catools.athena.model.tms;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.common.markers.IdRequired;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Data
@Accessors(chain = true)
public class TestCycleDto implements Serializable {

  @NotNull(groups = IdRequired.class, message = "The id must be provided.")
  private Long id;

  @NotBlank(message = "The cycle name must be provided.")
  @Size(max = 300, message = "The cycle name can be at most 300 character.")
  private String code;

  @NotNull(message = "The cycle start date/time must be provided.")
  private String name;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant startDate;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant endDate;

  @NotNull(message = "The pipeline project must be provided.")
  private String project;

  private String version;

  private Set<TestExecutionDto> testExecutions = new HashSet<>();

}
