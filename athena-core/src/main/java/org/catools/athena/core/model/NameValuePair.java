package org.catools.athena.core.model;

public interface NameValuePair {
    Long getId();
    String getName();
    String getValue();

    <T extends NameValuePair> T setId(Long id);
    <T extends NameValuePair> T setName(String name);
    <T extends NameValuePair> T setValue(String value);
}
