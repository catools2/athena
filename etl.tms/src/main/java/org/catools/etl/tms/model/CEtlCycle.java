package org.catools.etl.tms.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.common.utils.CStringUtil;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "cycle", schema = "tms")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "cycle")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CEtlCycle implements Serializable {

  public static final CEtlCycle UNSET = new CEtlCycle("UNSET", "UNSET", CEtlVersion.UNSET, null, null);
  @Serial
  private static final long serialVersionUID = 6051874018185613707L;
  @Id
  @Column(name = "id", length = 20, unique = true, nullable = false)
  private String id;

  @Column(name = "name", length = 250, nullable = false)
  private String name;

  @Column(name = "end_date")
  private Date endDate;

  @Column(name = "start_date")
  private Date startDate;

  @ManyToOne(
      cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      fetch = FetchType.LAZY)
  @JoinColumn(
      name = "version_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "FK_CYCLE_VERSION"))
  private CEtlVersion version;

  public CEtlCycle(
      String id,
      String name,
      CEtlVersion version,
      Date endDate,
      Date startDate) {
    this.id = id;
    this.name = CStringUtil.substring(name, 0, 250);
    this.version = version;
    this.endDate = endDate;
    this.startDate = startDate;
  }
}
