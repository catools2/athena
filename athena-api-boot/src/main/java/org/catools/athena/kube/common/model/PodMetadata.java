package org.catools.athena.kube.common.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.NameValuePair;

import java.io.Serializable;

import static org.catools.athena.kube.common.config.KubeConstant.ATHENA_KUBE_SCHEMA;


@Entity
@Table(name = "pod_metadata", schema = ATHENA_KUBE_SCHEMA)
@Setter
@Getter
@Accessors(chain = true)
public class PodMetadata implements NameValuePair, Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 300, nullable = false)
  private String name;

  @Column(name = "value", length = 1000, nullable = false)
  private String value;
}
