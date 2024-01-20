package org.catools.athena.rest.kube.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

import static org.catools.athena.rest.kube.config.KubeConstant.ATHENA_KUBE_SCHEMA;


@Entity
@Table(name = "container_state", schema = ATHENA_KUBE_SCHEMA)
@Data
@Accessors(chain = true)
public class ContainerState implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "The container status sync time must be provided.")
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "sync_time")
  private LocalDateTime syncTime;

  @NotBlank(message = "The container state type must be provided.")
  @Size(max = 100, message = "The container state type can be at most 100 character.")
  @Column(name = "type", length = 100, nullable = false)
  private String type;

  @NotBlank(message = "The container state message must be provided.")
  @Size(max = 1000, message = "The container state message can be at most 1000 character.")
  @Column(name = "name", length = 1000, nullable = false)
  private String message;

  @NotBlank(message = "The container state value must be provided.")
  @Size(max = 1000, message = "The container state value can be at most 1000 character.")
  @Column(name = "value", length = 1000, nullable = false)
  private String value;

  @NotNull(message = "The state container must be provided.")
  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinColumn(name = "container_id", nullable = false, referencedColumnName = "id")
  private Container container;

}
