package org.catools.tms.etl.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;


@NamedQueries({@NamedQuery(name = "getEtlUserByName", query = "FROM CEtlUser where name=:name")})
@Entity
@Table(name = "user", schema = "tms")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "user")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CEtlUser implements Serializable {
  private static final long serialVersionUID = 6267874018185613707L;

  public static final CEtlUser UNSET = new CEtlUser("UNSET");

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 200)
  private String name;

  public CEtlUser(String name) {
    this.name = name;
  }
}
