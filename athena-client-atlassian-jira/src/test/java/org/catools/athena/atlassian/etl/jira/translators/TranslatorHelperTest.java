package org.catools.athena.atlassian.etl.jira.translators;

import com.atlassian.jira.rest.client.api.domain.BasicUser;
import org.catools.athena.model.core.UserAliasDto;
import org.catools.athena.model.core.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TranslatorHelperTest {

  @Test
  void toUserDto_shouldPreferTheDisplayNameJiraReports() {
    BasicUser issueUser = new BasicUser(null, "jirauser10001", "jirauser10001");
    BasicUser jiraUser = new BasicUser(null, "jirauser10001", "Jane Doe");

    UserDto coreUser = TranslatorHelper.toUserDto(issueUser, jiraUser);

    assertThat(coreUser.getUsername()).isEqualTo("Jane Doe");
    assertThat(coreUser.getAliases())
        .extracting(UserAliasDto::getAlias)
        .containsExactlyInAnyOrder("jirauser10001", "Jane Doe");
  }

  @Test
  void toUserDto_shouldFallBackToTheIssuePayloadWhenJiraNoLongerKnowsTheAccount() {
    // A deleted account (404 from Jira) resolves to null, and getUser then passes the issue's own
    // BasicUser as both arguments. The transition must still be attributable.
    BasicUser issueUser = new BasicUser(null, "asmith", "Alex Smith");

    UserDto coreUser = TranslatorHelper.toUserDto(issueUser, issueUser);

    assertThat(coreUser.getUsername()).isEqualTo("Alex Smith");
    assertThat(coreUser.getAliases())
        .extracting(UserAliasDto::getAlias)
        .containsExactlyInAnyOrder("asmith", "Alex Smith");
  }

  @Test
  void toUserDto_shouldStillProduceAUsernameWhenTheDeletedAccountHasNoDisplayName() {
    BasicUser issueUser = new BasicUser(null, "asmith", null);

    UserDto coreUser = TranslatorHelper.toUserDto(issueUser, issueUser);

    assertThat(coreUser.getUsername()).isNotBlank();
  }

  @ParameterizedTest
  @CsvSource({
      "AIGenerated,      aigenerated",
      "AI-Generated,     aigenerated",
      "AI_Generated,     aigenerated",
      "aigenerated,      aigenerated",
      "GenAI-Assisted,   genaiassisted",
      "aireviewd,        aireviewd",
  })
  void normalizeLabel_shouldCollapseSpellingVariants(String raw, String expected) {
    assertThat(TranslatorHelper.normalizeLabel(raw)).isEqualTo(expected);
  }

  @Test
  void normalizeLabel_shouldNotConflateUnrelatedLabelsThatMerelyStartWithAi() {
    // A prefix match would score an unrelated label such as 'Airstream' as AI work.
    assertThat(TranslatorHelper.normalizeLabel("Airstream")).isEqualTo("airstream");
    assertThat(TranslatorHelper.normalizeLabel("Airstream"))
        .isNotEqualTo(TranslatorHelper.normalizeLabel("AIReviewed"));
  }

  @Test
  void normalizeLabels_shouldSplitJiraSpaceSeparatedLabelStrings() {
    Set<String> labels = TranslatorHelper.normalizeLabels("AIReviewed  AI-Generated Airstream");

    assertThat(labels).containsExactlyInAnyOrder("aireviewed", "aigenerated", "airstream");
  }

  @Test
  void normalizeLabels_shouldReturnEmptySetForBlankOrNullChangelogSides() {
    // A label first being set has fromString == null, which must not blow up.
    assertThat(TranslatorHelper.normalizeLabels(null)).isEmpty();
    assertThat(TranslatorHelper.normalizeLabels("   ")).isEmpty();
  }
}
