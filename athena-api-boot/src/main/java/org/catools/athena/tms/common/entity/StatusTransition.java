package org.catools.athena.tms.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.catools.athena.core.common.entity.User;

import java.io.Serializable;
import java.time.Instant;

import static org.catools.athena.tms.common.config.TmsConstant.ATHENA_TMS_SCHEMA;


@Entity
@Table(name = "status_transition", schema = ATHENA_TMS_SCHEMA)
@Getter
@Setter
@Accessors(chain = true)
public class StatusTransition implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "occurred", columnDefinition = "TIMESTAMPTZ")
  private Instant occurred;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "from_status", nullable = false, referencedColumnName = "id")
  private Status from;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "to_status", nullable = false, referencedColumnName = "id")
  private Status to;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id", nullable = false, referencedColumnName = "id")
  private Item item;

  @ManyToOne
  @JoinColumn(name = "author", nullable = false, referencedColumnName = "id")
  private User author;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    StatusTransition that = (StatusTransition) o;
    EqualsBuilder equalsBuilder = new EqualsBuilder();

    equalsBuilder.append(item != null ? item.getCode() : null, that.item != null ? that.item.getCode() : null);


    return equalsBuilder
        .append(occurred, that.occurred)
        .append(from, that.from)
        .append(to, that.to)
        .append(author, that.author)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(occurred)
        .append(from)
        .append(to)
        .append(item != null ? item.getCode() : null)
        .append(author)
        .toHashCode();
  }
}
