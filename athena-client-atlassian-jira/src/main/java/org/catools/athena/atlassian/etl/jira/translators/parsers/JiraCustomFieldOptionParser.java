package org.catools.athena.atlassian.etl.jira.translators.parsers;

import com.atlassian.jira.rest.client.api.domain.IssueField;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.rest.feign.common.utils.JsonUtils;
import org.codehaus.jettison.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class JiraCustomFieldOptionParser implements JiraFieldParser {
  private final IssueField field;

  public JiraCustomFieldOptionParser(IssueField field) {
    this.field = field;
  }

  @Override
  public int rank() {
    return 0;
  }

  @Override
  public boolean isRightParser() {
    return field.getValue() instanceof JSONArray && StringUtils.containsIgnoreCase(field.getValue().toString(), "customFieldOption");
  }

  @Override
  public HashMap<String, String> getNameValuePairs() {
    HashMap<String, String> output = new HashMap<>();

    try {
      List<LinkedHashMap<String, String>> parse = JsonUtils.readValue(field.getValue().toString(), ArrayList.class);
      for (LinkedHashMap<String, String> map : parse) {
        map.remove("self");
        map.remove("id");
        if (map.size() == 1) {
          String value = new ArrayList<>(map.values()).get(0);
          if (!isDefaultValue(value)) {
            output.put(field.getName(), value);
          }
        } else if (!map.isEmpty()) {
          output.put(field.getName(), new ArrayList<>(map.values()).get(0));
        } else {
          field.getName();
        }
      }
      return output;
    } catch (Throwable t) {
      throw new RuntimeException(t);
    }

  }
}
