package org.catools.athena.tms.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.catools.athena.core.common.entity.AppVersion;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.core.common.entity.User;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.catools.athena.tms.common.config.TmsConstant.ATHENA_TMS_SCHEMA;


@Entity
@Table(name = "item", indexes = @Index(columnList = "code"), schema = ATHENA_TMS_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Accessors(chain = true)
public class Item implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "code", length = 15, unique = true, nullable = false)
  private String code;

  @Column(name = "name", length = 300, nullable = false)
  private String name;

  @Column(name = "created_on", columnDefinition = "TIMESTAMPTZ", nullable = false)
  private Instant createdOn;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "created_by", nullable = false, referencedColumnName = "id")
  private User createdBy;

  @Column(name = "updated_on", columnDefinition = "TIMESTAMPTZ")
  private Instant updatedOn;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "updated_by", referencedColumnName = "id")
  private User updatedBy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "type_id", nullable = false, referencedColumnName = "id")
  private ItemType type;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "status_id", nullable = false, referencedColumnName = "id")
  private Status status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "priority_id", nullable = false, referencedColumnName = "id")
  private Priority priority;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id", nullable = false, referencedColumnName = "id")
  private Project project;

  @OneToMany(mappedBy = "item", orphanRemoval = true, cascade = CascadeType.ALL)
  private Set<StatusTransition> statusTransitions = new HashSet<>();

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(
      schema = ATHENA_TMS_SCHEMA,
      name = "item_version_mid",
      joinColumns = {@JoinColumn(name = "item_id")},
      inverseJoinColumns = {@JoinColumn(name = "version_id")}
  )
  private Set<AppVersion> versions = new HashSet<>();

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(
      schema = ATHENA_TMS_SCHEMA,
      name = "item_metadata_mid",
      joinColumns = {@JoinColumn(name = "item_id")},
      inverseJoinColumns = {@JoinColumn(name = "metadata_id")}
  )
  private Set<ItemMetadata> metadata = new HashSet<>();

  public void setStatusTransitions(Set<StatusTransition> statusTransitions) {
    if (statusTransitions == null) {
      this.statusTransitions.forEach(this::removeStatusTransition);
      return;
    }
    this.statusTransitions.clear();
    statusTransitions.forEach(this::addStatusTransition);
  }

  public void removeStatusTransition(StatusTransition statusTransition) {
    if (statusTransition == null) return;
    this.statusTransitions.remove(statusTransition.setItem(null));
  }

  public void addStatusTransition(StatusTransition statusTransition) {
    if (statusTransition == null) return;
    this.statusTransitions.add(statusTransition.setItem(this));
  }
}
