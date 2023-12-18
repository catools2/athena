package org.catools.etl.tms.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;


@NamedQuery(name = "getEtlExecutionStatusByName", query = "FROM CEtlExecutionStatus where name=:name")
@Entity
@Table(name = "execution_status", schema = "tms")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "execution_status")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CEtlExecutionStatus implements Serializable {

  public static final CEtlExecutionStatus UNSET = new CEtlExecutionStatus("UNSET");
  @Serial
  private static final long serialVersionUID = 6867874018185613707L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 100, unique = true, nullable = false)
  private String name;

  public CEtlExecutionStatus(String name) {
    this.name = name;
  }
}
