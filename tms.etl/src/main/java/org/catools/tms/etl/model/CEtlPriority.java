package org.catools.tms.etl.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "getPriorityById", query = "FROM CEtlPriority where id=:id")
@NamedQuery(name = "getPriorityByName", query = "FROM CEtlPriority where name=:name")
@Entity
@Table(name = "priority", schema = "tms")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "priority")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CEtlPriority implements Serializable {

  private static final long serialVersionUID = 6067874018185613747L;

  public static final CEtlPriority UNSET = new CEtlPriority("UNSET");

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  public CEtlPriority(String name) {
    this.name = name;
  }
}
