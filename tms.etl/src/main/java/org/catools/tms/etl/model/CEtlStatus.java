package org.catools.tms.etl.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;


@NamedQuery(name = "getStatusByName", query = "FROM CEtlStatus where name=:name")
@Entity
@Table(name = "status", schema = "tms")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "status")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CEtlStatus implements Serializable {

  private static final long serialVersionUID = 6867874018185613707L;

  public static final CEtlStatus UNSET = new CEtlStatus("UNSET");

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 50, unique = true, nullable = false)
  private String name;

  public CEtlStatus(String name) {
    this.name = name;
  }
}
