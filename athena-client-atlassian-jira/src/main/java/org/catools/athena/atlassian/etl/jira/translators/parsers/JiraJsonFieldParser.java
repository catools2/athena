package org.catools.athena.atlassian.etl.jira.translators.parsers;

import com.atlassian.jira.rest.client.api.domain.IssueField;
import org.codehaus.jettison.json.JSONObject;

import java.util.HashMap;

public class JiraJsonFieldParser implements JiraFieldParser {
  private final IssueField field;
  private final String valueAttribute;

  public JiraJsonFieldParser(IssueField field, String valueAttribute) {
    this.field = field;
    this.valueAttribute = valueAttribute;
  }

  @Override
  public int rank() {
    return 100;
  }

  @Override
  public boolean isRightParser() {
    return field.getValue() instanceof JSONObject &&
        ((JSONObject) field.getValue()).has(valueAttribute) &&
        ((JSONObject) field.getValue()).opt(valueAttribute) instanceof String;
  }

  @Override
  public HashMap<String, String> getNameValuePairs() {
    HashMap<String, String> output = new HashMap<>();
    try {
      output.put(field.getName(), ((JSONObject) field.getValue()).getString(valueAttribute));
      return output;
    } catch (Throwable t) {
      throw new RuntimeException(t);
    }
  }
}
