package org.catools.common.tests.serialization;

import org.catools.common.configs.CPathConfigs;
import org.catools.common.exception.CResourceNotFoundException;
import org.catools.common.exception.CRuntimeException;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.serialization.CXmlSerializationUtil;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.catools.common.utils.CJsonUtil;
import org.testng.annotations.Test;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.Serializable;
import java.util.Objects;

public class CXmlSerializationUtilTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWrite() {
    A a = new A();
    a.field1 = "FIELD1";
    File file = CPathConfigs.getTempChildFile("CXmlSerializationUtilTest");
    CXmlSerializationUtil.write(a, file);
    CVerify.Object.equals(CXmlSerializationUtil.read(file, A.class), a, "Deserialization worked.");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CRuntimeException.class)
  public void testWrite_N() {
    File file = CPathConfigs.getTempChildFile("CXmlSerializationUtilTest");
    CXmlSerializationUtil.write(null, file);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRead() {
    A a = new A();
    a.field1 = "FIELD1";
    CVerify.Object.equals(
        CXmlSerializationUtil.read("testData/CResourceTest/A.xml", CBaseUnitTest.class, A.class),
        a,
        "Deserialization worked.");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CResourceNotFoundException.class)
  public void testRead_N1() {
    CVerify.Object.isNull(
        CXmlSerializationUtil.read(
            "testData/CResourceTest/BaD_A.xml", CBaseUnitTest.class, A.class),
        "Deserialization worked.");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CRuntimeException.class)
  public void testRead_N() {
    CXmlSerializationUtil.read((File) null, A.class);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToXml() {
    A a = new A();
    a.field1 = "FIELD1";
    CVerify.String.equals(
        CXmlSerializationUtil.toXml(a),
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><a><field1>FIELD1</field1></a>",
        "To Xml worked.");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CRuntimeException.class)
  public void testToXml_N() {
    CXmlSerializationUtil.toXml(new B());
  }

  @XmlRootElement
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
