package org.catools.athena.rest.kube.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static org.catools.athena.rest.kube.config.KubeConstant.ATHENA_KUBE_SCHEMA;


@Entity
@Table(name = "pod_status", schema = ATHENA_KUBE_SCHEMA)
@Data
@Accessors(chain = true)
public class PodStatus implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(max = 200, message = "The pod status name can be at most 200 character.")
  @Column(name = "name", length = 200)
  private String name;

  @Size(max = 200, message = "The pod status phase can be at most 200 character.")
  @Column(name = "phase", length = 200)
  private String phase;

  @Size(max = 1000, message = "The pod status message can be at most 1000 character.")
  @Column(name = "message", length = 1000)
  private String message;

  @Size(max = 1000, message = "The pod status reason can be at most 1000 character.")
  @Column(name = "reason", length = 1000)
  private String reason;
}
