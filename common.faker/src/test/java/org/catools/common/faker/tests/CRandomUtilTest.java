package org.catools.common.faker.tests;

import org.assertj.core.api.Assertions;
import org.catools.common.exception.CInvalidRangeException;
import org.catools.common.faker.CRandom;
import org.testng.annotations.Test;

public class CRandomUtilTest {

  @Test
  public void testDouble_next() {
    int i = 1000;

    while (i-- > 0) {
      Assertions.assertThat(CRandom.Double.next()).isBetween(0D, Double.MAX_VALUE);
    }
  }

  @Test
  public void testDouble_nextSameRange() {
    int i = 1000;

    while (i-- > 0) {
      Assertions.assertThat(CRandom.Double.next(10, 10)).isEqualTo(10D);
    }
  }

  @Test
  public void testDouble_nextWithRange() {
    int i = 1000;

    while (i-- > 0) {
      Assertions.assertThat(CRandom.Double.next(0, 10)).isBetween(0D, 10D);
    }
  }

  @Test(expectedExceptions = CInvalidRangeException.class)
  public void testDouble_nextWithRange_LowerBoundGreaterHigherBound() {
    CRandom.Double.next(11, 10);
  }

  @Test
  public void testDouble_nextWithRange_LowerBoundNegative() {
    Assertions.assertThat(CRandom.Double.next(-1, 10)).isBetween(-1D, 10D);
  }

  @Test
  public void testFloat_next() {
    int i = 1000;

    while (i-- > 0) {
      Assertions.assertThat(CRandom.Float.next()).isBetween(0F, Float.MAX_VALUE);
    }
  }

  @Test
  public void testFloat_nextSameRange() {
    int i = 1000;

    while (i-- > 0) {
      Assertions.assertThat(CRandom.Float.next(10, 10)).isEqualTo(10F);
    }
  }

  @Test
  public void testFloat_nextWithRange() {
    int i = 1000;

    while (i-- > 0) {
      Assertions.assertThat(CRandom.Float.next(0, 10)).isBetween(0F, 10F);
    }
  }

  @Test(expectedExceptions = CInvalidRangeException.class)
  public void testFloat_nextWithRange_LowerBoundGreaterHigherBound() {
    CRandom.Float.next(11, 10);
  }

  @Test
  public void testFloat_nextWithRange_LowerBoundNegative() {
    Assertions.assertThat(CRandom.Float.next(-1, 10)).isBetween(-1F, 10F);
  }

  @Test
  public void testInt_next() {
    int i = 1000;

    while (i-- > 0) {
      Assertions.assertThat(CRandom.Int.next()).isBetween(0, Integer.MAX_VALUE);
    }
  }

  @Test
  public void testInt_nextSameRange() {
    int i = 1000;

    while (i-- > 0) {
      Assertions.assertThat(CRandom.Int.next(10, 10)).isEqualTo(10);
    }
  }

  @Test
  public void testInt_nextWithRange() {
    int i = 1000;

    while (i-- > 0) {
      Assertions.assertThat(CRandom.Int.next(0, 10)).isBetween(0, 10);
    }
  }

  @Test(expectedExceptions = CInvalidRangeException.class)
  public void testInt_nextWithRange_LowerBoundGreaterHigherBound() {
    CRandom.Int.next(11, 10);
  }

  @Test
  public void testInt_nextWithRange_LowerBoundNegative() {
    Assertions.assertThat(CRandom.Int.next(-1, 10)).isBetween(-1, 10);
  }

  @Test
  public void testLong_next() {
    int i = 1000;

    while (i-- > 0) {
      Assertions.assertThat(CRandom.Long.next()).isBetween(0L, Long.MAX_VALUE);
    }
  }

  @Test
  public void testLong_nextSameRange() {
    int i = 1000;

    while (i-- > 0) {
      Assertions.assertThat(CRandom.Long.next(10, 10)).isEqualTo(10L);
    }
  }

  @Test
  public void testLong_nextWithRange() {
    int i = 1000;

    while (i-- > 0) {
      Assertions.assertThat(CRandom.Long.next(0, 10)).isBetween(0L, 10L);
    }
  }

  @Test(expectedExceptions = CInvalidRangeException.class)
  public void testLong_nextWithRange_LowerBoundGreaterHigherBound() {
    CRandom.Long.next(11, 10);
  }

  @Test
  public void testLong_nextWithRange_LowerBoundNegative() {
    Assertions.assertThat(CRandom.Long.next(-1, 10)).isBetween(-1L, 10L);
  }
}
