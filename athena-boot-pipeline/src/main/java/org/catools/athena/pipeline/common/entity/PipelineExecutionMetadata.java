package org.catools.athena.pipeline.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.NameValuePair;


@Entity
@Table(name = "execution_metadata", uniqueConstraints = {
    @UniqueConstraint(name = "uk_execution_metadata_name_value", columnNames = {"name", "value"})
})
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Accessors(chain = true)
public class PipelineExecutionMetadata implements NameValuePair {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "name", length = 100, nullable = false)
  private String name;

  @Column(name = "value", length = 2000, nullable = false)
  private String value;
}
