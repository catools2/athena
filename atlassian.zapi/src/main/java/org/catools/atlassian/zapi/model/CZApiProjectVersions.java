package org.catools.atlassian.zapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.catools.common.utils.CStringUtil;

import java.util.Objects;

public class CZApiProjectVersions {
  private CZApiVersions unreleasedVersions;
  private CZApiVersions releasedVersions;

  public CZApiVersions getUnreleasedVersions() {
    return unreleasedVersions;
  }

  public CZApiProjectVersions setUnreleasedVersions(CZApiVersions unreleasedVersions) {
    this.unreleasedVersions = unreleasedVersions;
    return this;
  }

  public CZApiVersions getReleasedVersions() {
    return releasedVersions;
  }

  public CZApiProjectVersions setReleasedVersions(CZApiVersions releasedVersions) {
    this.releasedVersions = releasedVersions;
    return this;
  }

  @JsonIgnore
  public CZApiVersion getByName(String name) {
    return getAllVersions().getFirst(v -> CStringUtil.equalsIgnoreCase(name, v.getName()));
  }

  @JsonIgnore
  public CZApiVersions getAllVersions() {
    CZApiVersions czApiVersions = new CZApiVersions(releasedVersions);
    czApiVersions.addAll(unreleasedVersions);
    return czApiVersions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CZApiProjectVersions that = (CZApiProjectVersions) o;
    return Objects.equals(unreleasedVersions, that.unreleasedVersions)
        && Objects.equals(releasedVersions, that.releasedVersions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(unreleasedVersions, releasedVersions);
  }

  @Override
  public String toString() {
    return "CZApiProjectVersions{"
        + "unreleasedVersions="
        + unreleasedVersions
        + ", releasedVersions="
        + releasedVersions
        + '}';
  }
}
