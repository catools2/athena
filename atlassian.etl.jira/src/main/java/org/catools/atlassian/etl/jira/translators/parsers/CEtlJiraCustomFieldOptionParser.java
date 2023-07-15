package org.catools.atlassian.etl.jira.translators.parsers;

import com.atlassian.jira.rest.client.api.domain.IssueField;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CList;
import org.catools.common.utils.CJsonUtil;
import org.catools.common.utils.CStringUtil;
import org.codehaus.jettison.json.JSONArray;

import java.util.LinkedHashMap;

public class CEtlJiraCustomFieldOptionParser implements CEtlJiraFieldParser {
  private final IssueField field;

  public CEtlJiraCustomFieldOptionParser(IssueField field) {
    this.field = field;
  }

  @Override
  public int rank() {
    return 0;
  }

  @Override
  public boolean isRightParser() {
    return field.getValue() instanceof JSONArray
        && CStringUtil.containsIgnoreCase(field.getValue().toString(), "customFieldOption");
  }

  @Override
  public CHashMap<String, String> getNameValuePairs() {
    CHashMap<String, String> output = new CHashMap<>();
    try {
      CList<LinkedHashMap<String, String>> parse = CJsonUtil.read(field.getValue().toString(), CList.class);
      for (LinkedHashMap<String, String> map : parse) {
        map.remove("self");
        map.remove("id");
        if (map.size() == 1) {
          String value = new CList<>(map.values()).get(0);
          if (!isDefaultValue(value)) {
            output.put(field.getName(), value);
          }
        } else if (!map.isEmpty()) {
          output.put(field.getName(), new CList<>(map.values()).get(0));
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
