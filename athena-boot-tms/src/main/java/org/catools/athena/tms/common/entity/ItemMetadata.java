package org.catools.athena.tms.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.catools.athena.model.core.NameValuePair;


/**
 * Uniqueness of (name, value) is enforced by the expression index
 * {@code uk_metadata_name_value_md5} created in V12__widen_tms_metadata_value.sql, not by a table
 * constraint. It indexes {@code md5(value)} rather than {@code value} because {@code value} is now
 * unbounded text and a btree tuple has a hard size ceiling that a large value would breach. That is
 * not expressible as a JPA {@code @UniqueConstraint} or {@code @Index}, so both are omitted here
 * rather than declared in a form that would not match the real schema and fail
 * {@code ddl-auto=validate} in the integration tests.
 */
@Entity
@Table(name = "metadata")
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Accessors(chain = true)
public class ItemMetadata implements NameValuePair {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "name", length = 100, nullable = false)
  private String name;

  // text, not varchar(n): ingested metadata values regularly exceeded the previous 2000-char cap
  // and the overflow aborted the whole item save. See V12__widen_tms_metadata_value.sql.
  @Column(name = "value", columnDefinition = "text", nullable = false)
  private String value;
}
