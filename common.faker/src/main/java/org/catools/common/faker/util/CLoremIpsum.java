package org.catools.common.faker.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.catools.common.exception.CInvalidRangeException;
import org.catools.common.faker.CRandom;
import org.catools.common.utils.CIterableUtil;
import org.catools.common.utils.CStringUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

public class CLoremIpsum {
  private static final Set<String> specialChars =
      new HashSet<>(List.of("!?,;......".split(CStringUtil.EMPTY)));

  public static String getParagraph(
      int minWordLength,
      int maxWordLength,
      int minStatementLength,
      int maxStatementLength,
      int minParagraphLength,
      int maxParagraphLength) {
    if (minParagraphLength > maxParagraphLength) {
      throw new CInvalidRangeException(
          "Min paragraph length value must be smaller or equal to max paragraph length.");
    }
    if (minParagraphLength < 0) {
      throw new CInvalidRangeException("Both paragraph length range values must be non-negative.");
    }
    StringBuilder sb = new StringBuilder();
    int length = CRandom.Int.next(minParagraphLength, maxParagraphLength);
    while (sb.toString().trim().length() < length) {
      sb.append(
          getStatement(minWordLength, maxWordLength, minStatementLength, maxStatementLength).trim()
              + " ");
    }
    return sb.toString().trim().substring(0, length - 1) + CIterableUtil.getRandom(specialChars);
  }

  public static String getParagraphs(
      int minWordLength,
      int maxWordLength,
      int minStatementLength,
      int maxStatementLength,
      int minParagraphLength,
      int maxParagraphLength,
      int count) {
    List<String> sb = new ArrayList<>();
    while (count-- > 0) {
      sb.add(
          getParagraph(
              minWordLength,
              maxWordLength,
              minStatementLength,
              maxStatementLength,
              minParagraphLength,
              maxParagraphLength));
    }
    return StringUtils.join(sb, " ");
  }

  public static String getStatement(
      int minWordLength, int maxWordLength, int minStatementLength, int maxStatementLength) {
    if (minStatementLength > maxStatementLength) {
      throw new CInvalidRangeException(
          "Min statement length value must be smaller or equal to max statement length.");
    }
    if (minStatementLength < 0) {
      throw new CInvalidRangeException("Both statement length range values must be non-negative.");
    }
    StringBuilder sb = new StringBuilder();
    sb.append(CIterableUtil.getRandom("ABCDEFGHIJKLMNOPQRSTUVWXYZ".split(CStringUtil.EMPTY)));
    int length = CRandom.Int.next(minStatementLength, maxStatementLength);
    while (sb.toString().trim().length() < length) {
      sb.append(getWord(minWordLength, maxWordLength) + " ");
    }
    return sb.toString().trim().substring(0, length - 1) + CIterableUtil.getRandom(specialChars);
  }

  public static String getStatements(
      int minWordLength,
      int maxWordLength,
      int minStatementLength,
      int maxStatementLength,
      int count) {
    List<String> sb = new ArrayList<>();
    while (count-- > 0) {
      sb.add(getStatement(minWordLength, maxWordLength, minStatementLength, maxStatementLength));
    }
    return StringUtils.join(sb, " ");
  }

  public static String getWord(int minWordLength, int maxWordLength) {
    if (minWordLength > maxWordLength) {
      throw new CInvalidRangeException(
          "Min word length value must be smaller or equal to max word length.");
    }
    if (minWordLength < 0) {
      throw new CInvalidRangeException("Both word length range values must be non-negative.");
    }
    return new RandomStringGenerator.Builder()
        .withinRange('a', 'z')
        .filteredBy(LETTERS, DIGITS)
        .build()
        .generate(minWordLength, maxWordLength);
  }

  public static String getWords(int minWordLength, int maxWordLength, int count) {
    List<String> sb = new ArrayList<>();
    while (count-- > 0) {
      sb.add(getWord(minWordLength, maxWordLength));
    }
    return StringUtils.join(sb, " ");
  }
}
