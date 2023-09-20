package org.catools.etl.tms.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;


@NamedQuery(name = "getItemTypeById", query = "FROM CEtlItemType where id=:id")
@NamedQuery(name = "getItemTypeByName", query = "FROM CEtlItemType where name=:name")
@Entity
@Table(name = "item_type", schema = "tms")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "itemtype")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CEtlItemType implements Serializable {

  @Serial
  private static final long serialVersionUID = 6067871108185613707L;

  public static final CEtlItemType UNSET = new CEtlItemType("UNSET");

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 250, unique = true, nullable = false)
  private String name;

  public CEtlItemType(String name) {
    this.name = name;
  }
}
