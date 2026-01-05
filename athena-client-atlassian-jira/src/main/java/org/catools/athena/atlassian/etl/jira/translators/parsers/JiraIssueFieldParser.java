package org.catools.athena.atlassian.etl.jira.translators.parsers;

import com.atlassian.jira.rest.client.api.domain.IssueField;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.util.HashMap;

public class JiraIssueFieldParser implements JiraFieldParser {
  private final IssueField field;

  public JiraIssueFieldParser(IssueField field) {
    this.field = field;
  }

  @Override
  public int rank() {
    return 10;
  }

  @Override
  public boolean isRightParser() {
    return !(field.getValue() instanceof JSONObject || field.getValue() instanceof JSONArray);
  }

  @Override
  public HashMap<String, String> getNameValuePairs() {
    HashMap<String, String> output = new HashMap<>();
    try {
      output.put(field.getName(), field.getValue().toString());
      return output;
    } catch (Throwable t) {
      throw new RuntimeException(t);
    }
  }
}
