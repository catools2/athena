package org.catools.common.faker.provider;

import lombok.AllArgsConstructor;
import org.catools.common.faker.CRandom;
import org.catools.common.faker.model.CRandomName;
import org.catools.common.utils.CIterableUtil;

import java.util.List;

@AllArgsConstructor
public class CFakerNameProvider {
  private final List<String> maleFirstNames;
  private final List<String> femaleFirstNames;
  private final List<String> maleLastNames;
  private final List<String> femaleLastNames;
  private final List<String> maleMiddleNames;
  private final List<String> femaleMiddleNames;
  private final List<String> malePrefixes;
  private final List<String> femalePrefixes;
  private final List<String> maleSuffixes;
  private final List<String> femaleSuffixes;

  public CRandomName getAny() {
    if (CRandom.Int.next() % 3 == 0) {
      return getAnyMale();
    }
    return getAnyFemale();
  }

  public CRandomName getAnyMale() {
    return new CRandomName(
        CIterableUtil.getRandom(maleFirstNames),
        CIterableUtil.getRandom(maleMiddleNames),
        CIterableUtil.getRandom(maleLastNames),
        CIterableUtil.getRandom(malePrefixes),
        CIterableUtil.getRandom(maleSuffixes));
  }

  public CRandomName getAnyFemale() {
    return new CRandomName(
        CIterableUtil.getRandom(femaleFirstNames),
        CIterableUtil.getRandom(femaleMiddleNames),
        CIterableUtil.getRandom(femaleLastNames),
        CIterableUtil.getRandom(femalePrefixes),
        CIterableUtil.getRandom(femaleSuffixes));
  }
}
