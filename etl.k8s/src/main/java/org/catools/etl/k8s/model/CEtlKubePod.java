package org.catools.etl.k8s.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.common.utils.CStringUtil;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static org.catools.etl.k8s.configs.CEtlKubeConfigs.K8S_SCHEMA;


@Entity
@Table(name = "pod", schema = K8S_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CEtlKubePod implements Serializable {

  @Serial
  private static final long serialVersionUID = 6052874018185613707L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "hostname", length = 200)
  private String hostname;

  @Column(name = "nodeName", length = 200)
  private String nodeName;

  @ManyToOne(
      cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      fetch = FetchType.LAZY)
  @JoinColumn(
      name = "status_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_POD_STATUS"))
  private CEtlKubePodStatus status;

  @ManyToOne(
      cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      fetch = FetchType.LAZY)
  @JoinColumn(
      name = "project_id",
      referencedColumnName = "id",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_POD_PROJECT"))
  private CEtlKubeProject project;

  @ManyToMany(
      cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      fetch = FetchType.LAZY)
  @JoinTable(
      schema = K8S_SCHEMA,
      name = "pod_metadata_mid",
      joinColumns = {@JoinColumn(name = "pod_id")},
      inverseJoinColumns = {@JoinColumn(name = "metadata_id")})
  private Set<CEtlKubePodMetaData> metadata = new HashSet<>();

  @OneToMany(
      cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      fetch = FetchType.LAZY)
  @JoinColumn(name = "container_id", referencedColumnName = "id")
  private Set<CEtlKubeContainer> containers = new HashSet<>();

  public CEtlKubePod(
      String hostname,
      String nodeName,
      CEtlKubeProject project,
      CEtlKubePodStatus status) {
    this.hostname = CStringUtil.substring(hostname, 0, 200);
    this.nodeName = CStringUtil.substring(nodeName, 0, 200);
    this.project = project;
    this.status = status;
  }

  public void addContainer(CEtlKubeContainer container) {
    this.containers.add(container);
  }

  public void addMetaData(CEtlKubePodMetaData metadata) {
    this.metadata.add(metadata);
  }
}
