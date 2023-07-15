package org.catools.common.faker.tests;

import org.assertj.core.api.Assertions;
import org.catools.common.faker.CRandom;
import org.catools.common.faker.model.CRandomAddress;
import org.catools.common.faker.model.CRandomCompany;
import org.catools.common.faker.model.CRandomName;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class CRandomTest {

  @Test
  public void testCRandomInt() {
    int i = 10000;
    while (i-- > 0) {
      Assertions.assertThat(CRandom.Int.next(1, 10)).isBetween(1, 10);
      Assertions.assertThat(CRandom.Int.next(-20, 10)).isBetween(-20, 10);
      Assertions.assertThat(CRandom.Int.next(-10, -1)).isBetween(-10, -1);
    }
  }

  @Test
  public void testCRandomLong() {
    int i = 10000;
    while (i-- > 0) {
      Assertions.assertThat(CRandom.Long.next(1, 10)).isBetween(1L, 10L);
      Assertions.assertThat(CRandom.Long.next(-20, 10)).isBetween(-20L, 10L);
      Assertions.assertThat(CRandom.Long.next(-10, -1)).isBetween(-10L, -1L);
    }
  }

  @Test
  public void testCRandomFloat() {
    int i = 10000;
    while (i-- > 0) {
      Assertions.assertThat(CRandom.Float.next(1, 10)).isBetween(1F, 10F);
      Assertions.assertThat(CRandom.Float.next(-20, 10)).isBetween(-20F, 10F);
      Assertions.assertThat(CRandom.Float.next(-10, -1)).isBetween(-10F, -1F);
    }
  }

  @Test
  public void testCRandomBigDecimal() {
    int i = 10000;
    while (i-- > 0) {
      Assertions.assertThat(CRandom.BigDecimal.next(BigDecimal.ONE, BigDecimal.TEN))
          .isBetween(BigDecimal.ONE, BigDecimal.TEN);
      Assertions.assertThat(
              CRandom.BigDecimal.next(BigDecimal.valueOf(-20), BigDecimal.valueOf(10)))
          .isBetween(BigDecimal.valueOf(-20), BigDecimal.valueOf(10));
      Assertions.assertThat(
              CRandom.BigDecimal.next(BigDecimal.valueOf(-10), BigDecimal.valueOf(-1)))
          .isBetween(BigDecimal.valueOf(-10), BigDecimal.valueOf(-1));
    }
  }

  @Test
  public void testCRandomDouble() {
    int i = 10000;
    while (i-- > 0) {
      Assertions.assertThat(CRandom.Double.next(1d, 10d)).isBetween(1d, 10d);
      Assertions.assertThat(CRandom.Double.next(-20d, 10d)).isBetween(-20d, 10d);
      Assertions.assertThat(CRandom.Double.next(-10d, -1d)).isBetween(-10d, -1d);
    }
  }

  @Test
  public void testCRandomPerson() {
    int i = 10000;
    Set<CRandomName> values = new HashSet<>();
    while (i-- > 0) {
      values.add(CRandom.PersonName.next());
      values.add(CRandom.PersonName.nextFemale("USA"));
      values.add(CRandom.PersonName.nextMale("usa"));
    }
    Assertions.assertThat(values.size()).isGreaterThan(29990);
  }

  @Test
  public void testCRandomAddress() {
    int i = 10000;
    Set<CRandomAddress> values = new HashSet<>();
    while (i-- > 0) {
      values.add(CRandom.Address.next());
    }
    Assertions.assertThat(values.size()).isGreaterThan(9990);
  }

  @Test
  public void testCRandomCompany() {
    int i = 10000;
    Set<CRandomCompany> values = new HashSet<>();
    while (i-- > 0) {
      values.add(CRandom.Company.next());
    }
    Assertions.assertThat(values.size()).isGreaterThan(9990);
  }
}
