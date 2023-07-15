package org.catools.atlassian.etl.jira.translators.parsers;

import com.atlassian.jira.rest.client.api.domain.IssueField;
import org.catools.common.collections.CHashMap;
import org.codehaus.jettison.json.JSONObject;

public class CEtlJiraJsonFieldParser implements CEtlJiraFieldParser {
  private final IssueField field;
  private final String valueAttribute;

  public CEtlJiraJsonFieldParser(IssueField field, String valueAttribute) {
    this.field = field;
    this.valueAttribute = valueAttribute;
  }

  @Override
  public int rank() {
    return 100;
  }

  @Override
  public boolean isRightParser() {
    return field.getValue() instanceof JSONObject
        && ((JSONObject) field.getValue()).has(valueAttribute)
        && ((JSONObject) field.getValue()).opt(valueAttribute) instanceof String;
  }

  @Override
  public CHashMap<String, String> getNameValuePairs() {
    CHashMap<String, String> output = new CHashMap<>();
    try {
      output.put(field.getName(), ((JSONObject) field.getValue()).getString(valueAttribute));
      return output;
    } catch (Throwable t) {
      throw new RuntimeException(t);
    }
  }
}
