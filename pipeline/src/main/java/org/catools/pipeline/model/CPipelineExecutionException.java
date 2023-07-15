package org.catools.pipeline.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.common.utils.CStringUtil;

import javax.persistence.*;
import java.io.Serializable;

import static org.catools.pipeline.configs.CPipelineConfigs.PIPELINE_SCHEMA;


@Entity
@Table(name = "execution_exception", schema = PIPELINE_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CPipelineExecutionException implements Serializable {

  private static final long serialVersionUID = 606744019185613707L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "type", length = 200, nullable = false)
  private String type;

  @Column(name = "message", length = 2000, nullable = false)
  private String message;

  @Column(name = "stack_trace", length = 5000, nullable = false)
  private String stackTrace;

  public CPipelineExecutionException(String type, String message, String stackTrace) {
    this.type = getSubstring(type, 200);
    this.message = getSubstring(message, 2000);
    this.stackTrace = getSubstring(stackTrace, 5000);
  }

  private static String getSubstring(String input, int b) {
    if (CStringUtil.isBlank(input)) return "";
    return input.substring(0, Math.min(input.length(), b));
  }
}
