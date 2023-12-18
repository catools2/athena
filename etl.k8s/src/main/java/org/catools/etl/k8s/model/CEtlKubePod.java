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


@NamedQuery(name = "getEtlKubePodByName", query = "FROM CEtlKubePod where name=:name")
@Entity
@Table(name = "pod", schema = K8S_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CEtlKubePod implements Serializable {

  @Serial
  private static final long serialVersionUID = 6052874018185613707L;

  @Id
  @Column(name = "name", length = 200)
  private String name;

  @Column(name = "uid", length = 36)
  private String uid;

  @Column(name = "hostname", length = 200)
  private String hostname;

  @Column(name = "nodeName", length = 200)
  private String nodeName;

  @Column(name = "created_at")
  private Date createdAt;

  @Column(name = "deleted_at")
  private Date deletedAt;

  @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(
      name = "status_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_POD_STATUS"))
  private CEtlKubePodStatus status;

  @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(
      name = "project_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_POD_PROJECT"))
  private CEtlKubeProject project;

  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinTable(
      schema = K8S_SCHEMA,
      name = "pod_metadata_mid",
      joinColumns = {@JoinColumn(name = "pod_name", referencedColumnName = "name")},
      inverseJoinColumns = {@JoinColumn(name = "metadata_id")})
  private Set<CEtlKubePodMetaData> metadata = new HashSet<>();

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      schema = K8S_SCHEMA,
      name = "pod_container_mid",
      joinColumns = {@JoinColumn(name = "pod_name", referencedColumnName = "name")},
      inverseJoinColumns = {@JoinColumn(name = "container_id")})
  private Set<CEtlKubeContainer> containers = new HashSet<>();

  public CEtlKubePod(
      String name,
      String uid,
      String hostname,
      String nodeName,
      Date createdAt,
      Date deletedAt,
      CEtlKubeProject project,
      CEtlKubePodStatus status) {
    this.name = CStringUtil.substring(name, 0, 200);
    this.uid = CStringUtil.substring(uid, 0, 36);
    this.hostname = CStringUtil.substring(hostname, 0, 200);
    this.nodeName = CStringUtil.substring(nodeName, 0, 200);
    this.createdAt = createdAt;
    this.deletedAt = deletedAt;
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
