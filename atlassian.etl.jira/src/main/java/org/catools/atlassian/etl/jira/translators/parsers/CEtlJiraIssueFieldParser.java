package org.catools.atlassian.etl.jira.translators.parsers;

import com.atlassian.jira.rest.client.api.domain.IssueField;
import org.catools.common.collections.CHashMap;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public class CEtlJiraIssueFieldParser implements CEtlJiraFieldParser {
  private final IssueField field;

  public CEtlJiraIssueFieldParser(IssueField field) {
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
  public CHashMap<String, String> getNameValuePairs() {
    CHashMap<String, String> output = new CHashMap<>();
    try {
      output.put(field.getName(), field.getValue().toString());
      return output;
    } catch (Throwable t) {
      throw new RuntimeException(t);
    }
  }
}
