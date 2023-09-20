package org.catools.common.tests.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.exception.CInvalidJsonFormatException;
import org.catools.common.exception.CJsonGenerationException;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.io.CFile;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.catools.common.utils.CJsonUtil;
import org.catools.common.utils.CStringUtil;
import org.testng.annotations.Test;

import java.util.stream.Stream;

@Test(singleThreaded = true, priority = 100)
public class CJsonUtilTest extends CBaseUnitTest {
  private final ASet info = new ASet();
  private final String jsonInfo =
      "[ {\n"
          + "  \"a1\" : \"A1\",\n"
          + "  \"a2\" : {\n"
          + "    \"b1\" : [ \"L1\", \"L2\" ],\n"
          + "    \"b2\" : [ \"S1\", \"S2\" ],\n"
          + "    \"b3\" : 2\n"
          + "  },\n"
          + "  \"a3\" : 1\n"
          + "}, {\n"
          + "  \"a1\" : \"A2\",\n"
          + "  \"a2\" : {\n"
          + "    \"b1\" : [ \"L1\", \"L2\" ],\n"
          + "    \"b2\" : [ \"S1\", \"S2\" ],\n"
          + "    \"b3\" : 2\n"
          + "  },\n"
          + "  \"a3\" : 1\n"
          + "}, {\n"
          + "  \"a1\" : \"A3\",\n"
          + "  \"a2\" : {\n"
          + "    \"b1\" : [ \"L1\", \"L2\" ],\n"
          + "    \"b2\" : [ \"S1\", \"S2\" ],\n"
          + "    \"b3\" : 2\n"
          + "  },\n"
          + "  \"a3\" : 1\n"
          + "} ]";

  private final String badJsonInfo =
      "[ {\n"
          + "  \"a1\" : \"A1\",\n"
          + "  \"a2\" : {\n"
          + "    \"b1\" : [ \"L1\", \"L2\" ],\n"
          + "    \"b2\" : [ \"S1\", \"S2\" ],\n"
          + "    \"b3\" : 2\n"
          + "  },\n"
          + "  \"a3\" : 1\n"
          + "}, {\n"
          + "  \"a1\" : \"A2\",\n"
          + "    \"b1\" : [ \"L1\", \"L2\" ],\n"
          + "    \"b2\" : [ \"S1\", \"S2\" ],\n"
          + "    \"b3\" : 2\n"
          + "  },\n"
          + "  \"a3\" : 1\n"
          + "}, {\n"
          + "  \"a1\" : \"A3\",\n"
          + "  \"a2\" : {\n"
          + "    \"b1\" : [ \"L1\", \"L2\" ],\n"
          + "    \"b2\" : [ \"S1\", \"S2\" ],\n"
          + "    \"b3\" : 2\n"
          + "  },\n"
          + "  \"a3\" : 1\n"
          + "} ]";

  public CJsonUtilTest() {
    info.add(new A("A1", new B(new CList<>("L1", "L2"), new CSet<>("S1", "S2"), 2), 1));
    info.add(new A("A2", new B(new CList<>("L1", "L2"), new CSet<>("S1", "S2"), 2), 1));
    info.add(new A("A3", new B(new CList<>("L1", "L2"), new CSet<>("S1", "S2"), 2), 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testClone() {
    ASet clone = CJsonUtil.clone(info);
    CVerify.String.equals(
        CJsonUtil.toString(info).replaceAll("\r", CStringUtil.EMPTY),
        jsonInfo,
        "Clone generate same object");
    clone.remove(0);
    CVerify.Int.equals(clone.size(), 2, "Clone object has 2 records after remove");
    CVerify.Int.equals(info.size(), 3, "Original object has 3 records after remove get on clone");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReadFile() {
    CFile file = CFile.fromTmp(RandomStringUtils.randomAlphabetic(10) + ".file").write(jsonInfo);
    ASet read = CJsonUtil.read(file, ASet.class);
    CVerify.String.equals(
        read.mapToList(i -> i.getA1()).join(), "A1A2A3", "write method add correct value to file");
  }

  @Test(
      retryAnalyzer = CTestRetryAnalyzer.class,
      expectedExceptions = CInvalidJsonFormatException.class)
  public void testReadFile_BadFormat() {
    CFile file = CFile.fromTmp(RandomStringUtils.randomAlphabetic(10) + ".file").write(badJsonInfo);
    CJsonUtil.read(file, ASet.class);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReadString() {
    ASet read = CJsonUtil.read(jsonInfo, ASet.class);
    CVerify.String.equals(
        read.mapToList(i -> i.getA1()).join(), "A1A2A3", "write method add correct value to file");
  }

  @Test(
      retryAnalyzer = CTestRetryAnalyzer.class,
      expectedExceptions = CInvalidJsonFormatException.class)
  public void testReadString_BadFormat() {
    CJsonUtil.read(badJsonInfo, ASet.class);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToString() {
    CVerify.String.equals(
        CJsonUtil.toString(info).replaceAll("\r", CStringUtil.EMPTY),
        jsonInfo,
        "toString returns correct value");
  }

  @Test(
      retryAnalyzer = CTestRetryAnalyzer.class,
      expectedExceptions = CJsonGenerationException.class)
  public void testToString_BadFormat() {
    CJsonUtil.toString(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWriteToFile() {
    CFile file = CFile.fromTmp(RandomStringUtils.randomAlphabetic(10) + ".file");
    CJsonUtil.write(file, info);
    CVerify.String.equals(
        file.readString().replaceAll("\r", CStringUtil.EMPTY),
        jsonInfo,
        "write method add correct value to file");
  }

  @Test(
      retryAnalyzer = CTestRetryAnalyzer.class,
      expectedExceptions = CJsonGenerationException.class)
  public void testWriteToFile_BadFormat() {
    CFile file =
        CFile.fromTmp(
            RandomStringUtils.randomAlphabetic(10) + '\u0000' + ".file");
    CJsonUtil.write(file, badJsonInfo);
  }

  public static class ASet extends CList<A> {
    public ASet() {
    }

    public ASet(A... c) {
      super(c);
    }

    public ASet(Stream<A> stream) {
      super(stream);
    }

    public ASet(Iterable<A> iterable) {
      super(iterable);
    }
  }

  public static class A {
    private String a1;
    private B a2;
    private int a3;

    public A() {
    }

    public A(String a1, B a2, int a3) {
      this.a1 = a1;
      this.a2 = a2;
      this.a3 = a3;
    }

    public String getA1() {
      return a1;
    }

    public A setA1(String a1) {
      this.a1 = a1;
      return this;
    }

    public B getA2() {
      return a2;
    }

    public A setA2(B a2) {
      this.a2 = a2;
      return this;
    }

    public int getA3() {
      return a3;
    }

    public A setA3(int a3) {
      this.a3 = a3;
      return this;
    }
  }

  public static class B {
    private CList<String> b1;
    private CSet<String> b2;
    private int b3;

    public B() {
    }

    public B(CList<String> b1, CSet<String> b2, int b3) {
      this.b1 = b1;
      this.b2 = b2;
      this.b3 = b3;
    }

    public CList<String> getB1() {
      return b1;
    }

    public B setB1(CList<String> b1) {
      this.b1 = b1;
      return this;
    }

    public CSet<String> getB2() {
      return b2;
    }

    public B setB2(CSet<String> b2) {
      this.b2 = b2;
      return this;
    }

    public int getB3() {
      return b3;
    }

    public B setB3(int b3) {
      this.b3 = b3;
      return this;
    }
  }
}
