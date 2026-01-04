package org.catools.athena.atlassian.etl.jira.translators.parsers;

import com.atlassian.jira.rest.client.api.domain.IssueField;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@UtilityClass
public class JiraParser {
  private static final List<Function<IssueField, JiraFieldParser>> FIELD_PARSERS = new ArrayList<>();

  static {
    FIELD_PARSERS.add(JiraCustomFieldOptionParser::new);
    FIELD_PARSERS.add(field -> new JiraJsonFieldParser(field, "key"));
    FIELD_PARSERS.add(field -> new JiraJsonFieldParser(field, "name"));
    FIELD_PARSERS.add(field -> new JiraJsonFieldParser(field, "value"));
    FIELD_PARSERS.add(JiraIssueFieldParser::new);
  }

  public static HashMap<String, String> parserJiraField(IssueField field) {
    List<Function<IssueField, JiraFieldParser>> list = FIELD_PARSERS.stream()
        .filter(p -> p.apply(field).isRightParser())
        .collect(Collectors.toList());

    // we skip any fields which does not have parser or if it is jira plug in at this point
    // in future we should implement parser for all fields
    if (list.isEmpty() || (field.getValue() != null && field.getValue().toString().contains("com.atlassian.jira.plugin"))) {
      log.trace("Could not find parser for field {} with value {}.\n record will be skipped", field.getName(), field.getValue());
      return new HashMap<>();
    }

    list.sort(Comparator.comparingInt(o -> o.apply(field).rank()));
    return list.stream().findFirst().map(b -> b.apply(field).getNameValuePairs()).orElse(null);
  }
}
