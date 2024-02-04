package org.catools.athena.kube.common.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

import static org.catools.athena.kube.common.config.KubeConstant.ATHENA_KUBE_SCHEMA;


@Entity
@Table(name = "container_state", schema = ATHENA_KUBE_SCHEMA)
@Data
@Accessors(chain = true)
public class ContainerState implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "sync_time", columnDefinition = "TIMESTAMPTZ")
  private Instant syncTime;

  @Column(name = "type", length = 100, nullable = false)
  private String type;

  @Column(name = "name", length = 1000, nullable = false)
  private String message;

  @Column(name = "value", length = 1000, nullable = false)
  private String value;

  @ManyToOne
  @JoinColumn(name = "container_id", nullable = false, referencedColumnName = "id")
  private Container container;

}
