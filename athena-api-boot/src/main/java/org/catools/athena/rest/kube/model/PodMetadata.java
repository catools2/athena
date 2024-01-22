package org.catools.athena.rest.kube.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.NameValuePair;

import java.io.Serializable;

import static org.catools.athena.rest.kube.config.KubeConstant.ATHENA_KUBE_SCHEMA;


@Entity
@Table(name = "pod_metadata", schema = ATHENA_KUBE_SCHEMA)
@Data
@Accessors(chain = true)
public class PodMetadata implements NameValuePair, Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "The pod metadata name must be provided.")
  @Size(max = 300, message = "The pod metadata name can be at most 300 character.")
  @Column(name = "name", length = 300, nullable = false)
  private String name;

  @NotBlank(message = "The pod metadata value must be provided.")
  @Size(max = 1000, message = "The pod metadata value can be at most 1000 character.")
  @Column(name = "value", length = 1000, nullable = false)
  private String value;
}
