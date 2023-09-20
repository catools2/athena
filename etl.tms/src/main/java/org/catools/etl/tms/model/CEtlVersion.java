package org.catools.etl.tms.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;


@NamedQuery(
    name = "getVersionForProjectNameAndName",
    query = "FROM CEtlVersion v join fetch v.project p where v.name=:name and p.name=:projectName")
@Entity
@Table(name = "version", schema = "tms", uniqueConstraints = {
    @UniqueConstraint(name = "VERSION_PROJECT_UC", columnNames = {"name", "project_id"})
})
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CEtlVersion implements Serializable {

  @Serial
  private static final long serialVersionUID = 6067874018185683707L;

  public static final CEtlVersion UNSET = new CEtlVersion("UNSET", CEtlProject.UNSET);

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 100, nullable = false)
  private String name;

  @ManyToOne(
      cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      fetch = FetchType.LAZY)
  @JoinColumn(
      name = "project_id",
      referencedColumnName = "id",
      nullable = false)
  private CEtlProject project;

  public CEtlVersion(String name, CEtlProject project) {
    this.name = name;
    this.project = project;
  }
}
