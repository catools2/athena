package org.catools.athena.rest.kube.model;

import jakarta.persistence.*;
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

  @Column(name = "name", length = 200)
  private String name;

  @Column(name = "phase", length = 200)
  private String phase;

  @Column(name = "message", length = 1000)
  private String message;

  @Column(name = "reason", length = 1000)
  private String reason;
}
