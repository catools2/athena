package org.catools.pipeline.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.catools.pipeline.configs.CPipelineConfigs.PIPELINE_SCHEMA;

@Entity
@NamedQuery(name = "getLastByName", query = "FROM CPipeline where id = (Select max(id) FROM CPipeline where name=:name)")
@Table(name = "pipeline", schema = PIPELINE_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "pipeline")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CPipeline implements Serializable {

  private static final long serialVersionUID = 6051874043285613707L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "type", length = 100, nullable = false)
  private String type;

  @Column(name = "name", length = 100, nullable = false)
  private String name;

  @Column(name = "description", length = 300, nullable = false)
  private String description;

  @Column(name = "number", length = 100, nullable = false)
  private String number;

  @Column(name = "start_date", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date startDate;

  @Column(name = "end_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date endDate;

  @ManyToOne(
      cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      targetEntity = CPipelineProject.class,
      fetch = FetchType.LAZY)
  @JoinColumn(name = "project_code",
      referencedColumnName = "code",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_PIPELINE_PROJECT"))
  private CPipelineProject project;

  @ManyToOne(
      cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      targetEntity = CPipelineEnvironment.class,
      fetch = FetchType.LAZY)
  @JoinColumn(
      name = "environment_code",
      referencedColumnName = "code",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_PIPELINE_ENVIRONMENT"))
  private CPipelineEnvironment environment;

  @ManyToMany(
      cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      fetch = FetchType.LAZY,
      targetEntity = CPipelineMetaData.class)
  @JoinTable(
      schema = PIPELINE_SCHEMA,
      name = "pipeline_metadata_mid",
      joinColumns = {@JoinColumn(name = "pipeline_id")},
      inverseJoinColumns = {@JoinColumn(name = "metadata_id")}
  )
  private List<CPipelineMetaData> metadata = new ArrayList<>();
}
