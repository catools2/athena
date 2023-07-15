package org.catools.tms.etl.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.common.utils.CStringUtil;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "item", schema = "tms")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "item")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CEtlItem implements Serializable {

  private static final long serialVersionUID = 6052874018185613707L;

  @Id
  @Column(name = "id", length = 10, unique = true, nullable = false)
  private String id;

  @Column(name = "name", length = 1000, nullable = false)
  private String name;

  @Column(name = "created", nullable = false)
  private Date created;

  @Column(name = "updated")
  private Date updated;

  @ManyToOne(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY)
  @JoinColumn(
      name = "type_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_ITEM_TYPE"))
  private CEtlItemType type;

  @ManyToOne(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY)
  @JoinColumn(
      name = "status_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_ITEM_STATUS"))
  private CEtlStatus status;

  @ManyToOne(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY)
  @JoinColumn(
      name = "priority_id",
      referencedColumnName = "id",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_ITEM_PRIORITY"))
  private CEtlPriority priority;

  @ManyToOne(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY)
  @JoinColumn(
      name = "project_id",
      referencedColumnName = "id",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_ITEM_PROJECT"))
  private CEtlProject project;

  @ManyToMany(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY)
  @JoinTable(
      schema = "tms",
      name = "item_version",
      joinColumns = {@JoinColumn(name = "item_id")},
      inverseJoinColumns = {@JoinColumn(name = "version_id")})
  private Set<CEtlVersion> versions = new HashSet<>();

  @ManyToMany(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY)
  @JoinTable(
      schema = "tms",
      name = "item_metadata",
      joinColumns = {@JoinColumn(name = "item_id")},
      inverseJoinColumns = {@JoinColumn(name = "metadata_id")})
  private Set<CEtlItemMetaData> metadata = new HashSet<>();

  @OneToMany(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id", referencedColumnName = "id")
  private Set<CEtlItemStatusTransition> statusTransitions = new HashSet<>();

  public CEtlItem(
      String id,
      String name,
      Date created,
      Date updated,
      CEtlProject project,
      CEtlItemType type,
      CEtlVersions versions,
      CEtlStatus status,
      CEtlPriority priority) {
    this.id = id;
    this.name = CStringUtil.substring(name, 0, 1000);
    this.created = created;
    this.updated = updated;
    this.project = project;
    this.type = type;
    this.versions = versions;
    this.status = status;
    this.priority = priority;
  }

  public void addStatusTransition(CEtlItemStatusTransition statusTransition) {
    this.statusTransitions.add(statusTransition);
  }

  public void addItemMetaData(CEtlItemMetaData metadata) {
    this.metadata.add(metadata);
  }
}
