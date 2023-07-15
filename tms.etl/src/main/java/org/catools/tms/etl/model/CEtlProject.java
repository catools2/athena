package org.catools.tms.etl.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@NamedQueries({
    @NamedQuery(name = "getProjectByName", query = "FROM CEtlProject where name=:name"),
})
@Entity
@Table(name = "project", schema = "tms")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "project")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CEtlProject implements Serializable {

  private static final long serialVersionUID = 6069874018185613707L;

  public static final CEtlProject UNSET = new CEtlProject("UNSET");

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 100, unique = true, nullable = false)
  private String name;

  public CEtlProject(String name) {
    this.name = name;
  }
}
