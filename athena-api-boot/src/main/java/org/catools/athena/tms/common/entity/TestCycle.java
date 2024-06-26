package org.catools.athena.tms.common.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.catools.athena.core.common.entity.AppVersion;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.catools.athena.tms.common.config.TmsConstant.ATHENA_TMS_SCHEMA;


@Entity
@Table(name = "cycle", indexes = @Index(columnList = "code"), schema = ATHENA_TMS_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Accessors(chain = true)
public class TestCycle implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "code", length = 10, unique = true, nullable = false)
  private String code;

  @Column(name = "unique_hash", unique = true, nullable = false)
  private Integer uniqueHash;

  @Column(name = "name", length = 300, nullable = false)
  private String name;

  @Column(name = "start_date", columnDefinition = "TIMESTAMPTZ", nullable = false)
  private Instant startDate;

  @Column(name = "end_date", columnDefinition = "TIMESTAMPTZ")
  private Instant endDate;

  @ManyToOne
  @JoinColumn(name = "version_id", referencedColumnName = "id")
  private AppVersion version;

  @OneToMany(mappedBy = "cycle", orphanRemoval = true, cascade = CascadeType.ALL)
  private Set<TestExecution> testExecutions = new HashSet<>();

  public void setTestExecutions(Set<TestExecution> testExecutions) {
    if (testExecutions == null) {
      this.testExecutions.forEach(this::removeTestExecution);
      return;
    }
    this.testExecutions.clear();
    testExecutions.forEach(this::addTestExecution);
  }

  public void removeTestExecution(TestExecution testExecution) {
    if (testExecution == null) return;
    this.testExecutions.remove(testExecution.setItem(null));
  }

  public void addTestExecution(TestExecution testExecution) {
    if (testExecution == null) return;
    this.testExecutions.add(testExecution.setCycle(this));
  }
}
