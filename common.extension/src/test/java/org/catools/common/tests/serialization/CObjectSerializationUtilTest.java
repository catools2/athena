package org.catools.common.tests.serialization;

import org.catools.common.configs.CPathConfigs;
import org.catools.common.exception.CRuntimeException;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.serialization.CObjectSerializationUtil;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.catools.common.utils.CJsonUtil;
import org.testng.annotations.Test;

import java.io.File;
import java.io.Serializable;
import java.util.Objects;

public class CObjectSerializationUtilTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWrite() {
    A a = new A();
    a.field1 = "FIELD1";
    File file = CPathConfigs.getTempChildFile("CObjectSerializationUtilTest");
    CObjectSerializationUtil.write(file, a);

    CVerify.Object.equals(CObjectSerializationUtil.read(file), a, "Deserialization worked.");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CRuntimeException.class)
  public void testRead_N() {
    CObjectSerializationUtil.read(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CRuntimeException.class)
  public void testWrite_N() {
    B a = new B();
    a.field1 = "FIELD1";
    File file = CPathConfigs.getTempChildFile("CObjectSerializationUtilTest.b");
    CObjectSerializationUtil.write(file, a);
  }

  public static class A implements Serializable {
    public String field1;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof A)) return false;
      A a = (A) o;
      return Objects.equals(field1, a.field1);
    }

    @Override
    public int hashCode() {
      return Objects.hash(field1);
    }

    @Override
    public String toString() {
      return CJsonUtil.toString(this);
    }
  }

  public static class B {
    public String field1;

    @Override
    public String toString() {
      return CJsonUtil.toString(this);
    }
  }
}
