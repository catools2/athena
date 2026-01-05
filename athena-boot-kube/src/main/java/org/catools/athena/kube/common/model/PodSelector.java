package org.catools.athena.kube.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.catools.athena.model.core.NameValuePair;


@Entity
@Table(name = "pod_selector",
    uniqueConstraints = {
        @UniqueConstraint(name = "UniquePodSelectorNameValue", columnNames = {"name", "value"})
    },
    indexes = {
        @Index(name = "idx_pod_selector_name_value", columnList = "name, value")
    }
)
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Accessors(chain = true)
public class PodSelector implements NameValuePair {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 300, nullable = false)
  private String name;

  @Column(name = "value", length = 1000, nullable = false)
  private String value;
}
