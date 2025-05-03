package org.catools.athena.metric.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Entity
@Table(name = "action", indexes = @Index(columnList = "name, type, target, command"))
@Data
@Accessors(chain = true)
public class Action implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "category", length = 100, nullable = false)
  private String category;

  @Column(name = "name", length = 100, nullable = false)
  private String name;

  @Column(name = "type", length = 100, nullable = false)
  private String type;

  @Column(name = "target", length = 1000, nullable = false)
  private String target;

  @Column(name = "command", length = 5000, nullable = false)
  private String command;

  @Column(name = "parameter", length = 5000)
  private String parameter;

}
