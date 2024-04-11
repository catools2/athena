package org.catools.athena.kube.common.model;

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

import java.io.Serializable;

import static org.catools.athena.kube.common.config.KubeConstant.ATHENA_KUBE_SCHEMA;


@Entity
@Table(name = "pod_status",
    schema = ATHENA_KUBE_SCHEMA,
    uniqueConstraints = {
        @UniqueConstraint(name = "UniquePodStatus", columnNames = {"name", "phase", "message", "reason"})
    }
)
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
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
