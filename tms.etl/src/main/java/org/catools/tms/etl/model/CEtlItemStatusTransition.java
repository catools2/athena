package org.catools.tms.etl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@NamedQueries({
    @NamedQuery(
        name = "getItemStatusTransitionByStatusesAndDate",
        query = "FROM CEtlItemStatusTransition st join fetch st.from f join fetch st.to t " +
            "where f.name=:fromName and t.name=:toName and st.occurred=:occurred and st.item.id=:itemId")
})
@Entity
@Table(name = "status_transition", schema = "tms")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class CEtlItemStatusTransition implements Serializable {

  private static final long serialVersionUID = 6087874018185613707L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "occurred")
  @Temporal(TemporalType.TIMESTAMP)
  private Date occurred;

  @ManyToOne(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY)
  @JoinColumn(name = "from_status", referencedColumnName = "id")
  private CEtlStatus from;

  @ManyToOne(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY)
  @JoinColumn(name = "to_status", referencedColumnName = "id")
  private CEtlStatus to;

  @ManyToOne(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id", referencedColumnName = "id")
  private CEtlItem item;

  public CEtlItemStatusTransition(Date occurred, CEtlStatus from, CEtlStatus to, CEtlItem item) {
    this.occurred = occurred;
    this.from = from;
    this.to = to;
    this.item = item;
  }

  @JsonIgnore
  public CEtlItem getItem() {
    return item;
  }

  public String getItemId() {
    return item == null ? null : item.getId();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    CEtlItemStatusTransition that = (CEtlItemStatusTransition) o;

    return new EqualsBuilder().append(id, that.id)
        .append(occurred, that.occurred)
        .append(from, that.from)
        .append(to, that.to)
        .append(getItemId(), that.getItemId()).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(id)
        .append(occurred)
        .append(from)
        .append(to)
        .append(getItemId()).toHashCode();
  }

  @Override
  public String toString() {
    return "CEtlItemStatusTransition{" +
        "id=" + id +
        ", occurred=" + occurred +
        ", from=" + from +
        ", to=" + to +
        ", itemId=" + item.getId() +
        '}';
  }
}
