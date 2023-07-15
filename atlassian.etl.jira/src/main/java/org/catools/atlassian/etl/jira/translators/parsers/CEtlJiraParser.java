package org.catools.atlassian.etl.jira.translators.parsers;

import com.atlassian.jira.rest.client.api.domain.IssueField;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CList;

import java.util.Comparator;
import java.util.function.Function;

@Slf4j
@UtilityClass
public class CEtlJiraParser {
  private static final CList<IssueField> skippedFields = new CList<>();
  private static final CList<Function<IssueField, CEtlJiraFieldParser>> fieldParsers = new CList<>();

  static {
    fieldParsers.add(CEtlJiraCustomFieldOptionParser::new);
    fieldParsers.add(field -> new CEtlJiraJsonFieldParser(field, "name"));
    fieldParsers.add(field -> new CEtlJiraJsonFieldParser(field, "value"));
    fieldParsers.add(CEtlJiraIssueFieldParser::new);
  }

  public static void addFieldParser(Function<IssueField, CEtlJiraFieldParser> parserFunction) {
    fieldParsers.add(parserFunction);
  }

  public static CHashMap<String, String> parserJiraField(IssueField field) {
    CList<Function<IssueField, CEtlJiraFieldParser>> list =
        fieldParsers.getAll(p -> p.apply(field).isRightParser());
    // we skip any fields which does not have parser or if it is jira plug in at this point
    // in future we should implement parser for all fields
    if (list.isEmpty()
        || (field.getValue() != null
        && field.getValue().toString().contains("com.atlassian.jira.plugin"))) {
      skippedFields.add(field);
      log.trace(
          "Could not find parser for field {} with value {}.\n record will be skipped",
          field.getName(),
          field.getValue());
      return new CHashMap<>();
    }

    list.sort(Comparator.comparingInt(o -> o.apply(field).rank()));
    Function<IssueField, CEtlJiraFieldParser> bestMatch = list.getFirstOrNull();
    return bestMatch.apply(field).getNameValuePairs();
  }
}
