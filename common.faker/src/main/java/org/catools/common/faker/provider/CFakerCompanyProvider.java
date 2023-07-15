package org.catools.common.faker.provider;

import lombok.AllArgsConstructor;
import org.catools.common.faker.model.CRandomCompany;
import org.catools.common.utils.CIterableUtil;

import java.util.List;

@AllArgsConstructor
public class CFakerCompanyProvider {
  private final List<String> companyNames;
  private final List<String> companyPrefixes;
  private final List<String> companySuffixes;

  public CRandomCompany getAny() {
    return new CRandomCompany(
        CIterableUtil.getRandom(companyNames),
        CIterableUtil.getRandom(companyPrefixes),
        CIterableUtil.getRandom(companySuffixes));
  }
}
