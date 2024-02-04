package org.catools.athena.git.utils;

import lombok.experimental.UtilityClass;
import org.catools.athena.git.common.model.DiffEntry;
import org.catools.athena.git.common.model.Tag;
import org.catools.athena.git.model.DiffEntryDto;
import org.catools.athena.git.model.TagDto;

import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

@UtilityClass
public class GitTestUtils {

  public static void verifyTagsDtoHasCorrectValue(Set<TagDto> tags1, Set<TagDto> tags2) {
    assertThat(tags1, notNullValue());
    assertThat(tags2, notNullValue());

    for (TagDto t2 : tags2) {
      Optional<TagDto> t1 = tags1.stream().filter(b -> b.getName().equals(t2.getName())).findFirst();
      assertThat(t1.isPresent(), equalTo(true));
      assertThat(t1.get().getName(), equalTo(t2.getName()));
      assertThat(t1.get().getHash(), equalTo(t2.getHash()));
    }
  }

  public static void verifyTagsHasCorrectValue(Set<Tag> tags1, Set<TagDto> tags2) {
    assertThat(tags1, notNullValue());
    assertThat(tags2, notNullValue());

    for (TagDto t2 : tags2) {
      Optional<Tag> t1 = tags1.stream().filter(b -> b.getName().equals(t2.getName())).findFirst();
      assertThat(t1.isPresent(), equalTo(true));
      assertThat(t1.get().getName(), equalTo(t2.getName()));
      assertThat(t1.get().getHash(), equalTo(t2.getHash()));
    }
  }

  public static void verifyDiffDtoEntriesHaveCorrectValue(Set<DiffEntryDto> diffEntry, Set<DiffEntryDto> diffEntryDto) {
    assertThat(diffEntry.isEmpty(), equalTo(diffEntryDto.isEmpty()));

    for (DiffEntryDto entryDto : diffEntryDto) {
      Optional<DiffEntryDto> actual = diffEntry.stream().filter(d -> d.getOldPath().equals(entryDto.getOldPath())).findFirst();
      assertThat(actual.isPresent(), equalTo(true));
      verifyDiffEntryHasCorrectValue(actual.get(), entryDto);
    }
  }

  public static void verifyDiffEntriesHaveCorrectValue(Set<DiffEntry> diffEntry, Set<DiffEntryDto> diffEntryDto) {
    assertThat(diffEntry.isEmpty(), equalTo(diffEntryDto.isEmpty()));

    for (DiffEntryDto entryDto : diffEntryDto) {
      Optional<DiffEntry> actual = diffEntry.stream().filter(d -> d.getOldPath().equals(entryDto.getOldPath())).findFirst();
      assertThat(actual.isPresent(), equalTo(true));
      verifyDiffEntryHasCorrectValue(actual.get(), entryDto);
    }
  }

  public static void verifyDiffEntryHasCorrectValue(DiffEntry diffEntry, DiffEntryDto diffEntryDto) {
    assertThat(diffEntry.getCommit(), notNullValue());
    assertThat(diffEntry.getDeleted(), equalTo(diffEntryDto.getDeleted()));
    assertThat(diffEntry.getInserted(), equalTo(diffEntryDto.getInserted()));
    assertThat(diffEntry.getOldPath(), equalTo(diffEntryDto.getOldPath()));
    assertThat(diffEntry.getNewPath(), equalTo(diffEntryDto.getNewPath()));
  }

  public static void verifyDiffEntryHasCorrectValue(DiffEntryDto diffEntry, DiffEntryDto diffEntryDto) {
    assertThat(diffEntry.getDeleted(), equalTo(diffEntryDto.getDeleted()));
    assertThat(diffEntry.getInserted(), equalTo(diffEntryDto.getInserted()));
    assertThat(diffEntry.getOldPath(), equalTo(diffEntryDto.getOldPath()));
    assertThat(diffEntry.getNewPath(), equalTo(diffEntryDto.getNewPath()));
  }
}
