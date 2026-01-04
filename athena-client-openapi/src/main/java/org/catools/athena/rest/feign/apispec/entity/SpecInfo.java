package org.catools.athena.rest.feign.apispec.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecInfo {
  private String name;

  private String url;
}
