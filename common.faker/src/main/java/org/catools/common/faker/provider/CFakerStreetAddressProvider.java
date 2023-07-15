package org.catools.common.faker.provider;

import com.mifmif.common.regex.Generex;
import lombok.AllArgsConstructor;
import org.catools.common.faker.model.CRandomStreetInfo;
import org.catools.common.utils.CIterableUtil;

import java.util.List;

@AllArgsConstructor
public class CFakerStreetAddressProvider {
  private final List<String> streetNames;
  private final List<String> streetSuffixes;
  private final List<String> streetPrefixes;
  private final List<String> streetNumberPatterns;
  private final List<String> buildingNumberPatterns;

  public CRandomStreetInfo getAny() {
    return new CRandomStreetInfo(
        CIterableUtil.getRandom(streetNames),
        CIterableUtil.getRandom(streetSuffixes),
        CIterableUtil.getRandom(streetPrefixes),
        new Generex(CIterableUtil.getRandom(streetNumberPatterns)).random(),
        new Generex(CIterableUtil.getRandom(buildingNumberPatterns)).random());
  }
}
