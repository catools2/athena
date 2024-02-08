package org.catools.athena.tms.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.core.common.entity.User;
import org.catools.athena.core.common.entity.Version;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.catools.athena.apispec.common.config.ApiSpecConstant.ATHENA_OPENAPI_SCHEMA;
import static org.catools.athena.tms.common.config.TmsConstant.ATHENA_TMS_SCHEMA;


@Entity
@Table(name = "item", schema = ATHENA_TMS_SCHEMA)
@Data
@Accessors(chain = true)
public class Item implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "code", length = 15, nullable = false)
  private String code;

  @Column(name = "name", length = 300, nullable = false)
  private String name;

  @Column(name = "created_on", columnDefinition = "TIMESTAMPTZ", nullable = false)
  private Instant createdOn;

  @ManyToOne
  @JoinColumn(name = "created_by", nullable = false, referencedColumnName = "id")
  private User createdBy;

  @Column(name = "updated_on", columnDefinition = "TIMESTAMPTZ")
  private Instant updatedOn;

  @ManyToOne
  @JoinColumn(name = "updated_by", nullable = false, referencedColumnName = "id")
  private User updatedBy;

  @ManyToOne
  @JoinColumn(name = "type_id", nullable = false, referencedColumnName = "id")
  private ItemType type;

  @ManyToOne
  @JoinColumn(name = "status_id", nullable = false, referencedColumnName = "id")
  private Status status;

  @ManyToOne
  @JoinColumn(name = "priority_id", nullable = false, referencedColumnName = "id")
  private Priority priority;

  @ManyToOne
  @JoinColumn(name = "project_id", nullable = false, referencedColumnName = "id")
  private Project project;

  @ManyToMany(
      cascade = CascadeType.MERGE,
      fetch = FetchType.EAGER)
  @JoinTable(
      schema = ATHENA_OPENAPI_SCHEMA,
      name = "item_version_mid",
      joinColumns = {@JoinColumn(name = "item_id")},
      inverseJoinColumns = {@JoinColumn(name = "version_id")}
  )
  private Set<Version> versions = new HashSet<>();

  @ManyToMany(
      cascade = CascadeType.MERGE,
      fetch = FetchType.EAGER,
      targetEntity = ItemMetadata.class)
  @JoinTable(
      schema = ATHENA_OPENAPI_SCHEMA,
      name = "item_metadata_mid",
      joinColumns = {@JoinColumn(name = "item_id")},
      inverseJoinColumns = {@JoinColumn(name = "metadata_id")}
  )
  private Set<ItemMetadata> metadata = new HashSet<>();
}
