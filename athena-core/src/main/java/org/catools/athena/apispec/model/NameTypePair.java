package org.catools.athena.apispec.model;

public interface NameTypePair {
  String getName();

  String getType();

  <T extends NameTypePair> T setName(String name);

  <T extends NameTypePair> T setType(String value);
}
