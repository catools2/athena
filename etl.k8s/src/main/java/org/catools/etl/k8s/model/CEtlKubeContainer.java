package org.catools.etl.k8s.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.common.utils.CStringUtil;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.catools.etl.k8s.configs.CEtlKubeConfigs.K8S_SCHEMA;


@Entity
@Table(name = "container", schema = K8S_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CEtlKubeContainer implements Serializable {

  @Serial
  private static final long serialVersionUID = 6052874018185613707L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "type", length = 50, nullable = false)
  private String type;

  @Column(name = "name", length = 300, nullable = false)
  private String name;

  @Column(name = "image", length = 300, nullable = false)
  private String image;

  @Column(name = "imageId", length = 300, nullable = false)
  private String imageId;

  @Column(name = "ready")
  private Boolean ready;

  @Column(name = "started")
  private Boolean started;

  @Column(name = "restartCount")
  private Integer restartCount;

  @Column(name = "startedAt")
  private Date startedAt;

  @ManyToMany(
      cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      fetch = FetchType.LAZY)
  @JoinTable(
      schema = K8S_SCHEMA,
      name = "container_metadata_mid",
      joinColumns = {@JoinColumn(name = "container_id")},
      inverseJoinColumns = {@JoinColumn(name = "metadata_id")})
  private Set<CEtlKubeContainerMetaData> metadata = new HashSet<>();

  public CEtlKubeContainer(
      String type,
      String name,
      String image,
      String imageId,
      Boolean ready,
      Boolean started,
      Integer restartCount,
      Date startedAt) {
    this.type = type;
    this.name = CStringUtil.substring(name, 0, 300);
    this.image = CStringUtil.substring(image, 0, 300);
    this.imageId = CStringUtil.substring(imageId, 0, 300);
    this.ready = ready;
    this.started = started;
    this.startedAt = startedAt;
    this.restartCount = restartCount;
  }

  public void addMetaData(CEtlKubeContainerMetaData metadata) {
    this.metadata.add(metadata);
  }
}
