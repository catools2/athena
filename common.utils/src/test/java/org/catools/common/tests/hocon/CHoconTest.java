package org.catools.common.tests.hocon;

import org.catools.common.enums.CPlatform;
import org.catools.common.hocon.CHocon;
import org.testng.annotations.Test;

public class CHoconTest {

  @Test
  public void testReload() {
    CHocon.asObject("catools");
  }

  @Test
  public void testValid() {
    assert CHocon.asString("valid.str").equals("str");
    assert CHocon.asBoolean("valid.bool");
    assert CHocon.asNumber("valid.num").doubleValue() == 1.1;
    assert CHocon.asInteger("valid.int") == 1;
    assert CHocon.asLong("valid.long") == 11111;
    assert CHocon.asDouble("valid.double") == 1.1;
    assert CHocon.asEnum("valid.enum", CPlatform.class) == CPlatform.MAC;
  }

  @Test
  public void testValidList() {
    assert CHocon.asStrings("validList.str").get(0).equals("str");
    assert CHocon.asBooleans("validList.bool").get(0);
    assert CHocon.asNumbers("validList.num").get(0).doubleValue() == 1.1;
    assert CHocon.asIntegers("validList.int").get(0) == 1;
    assert CHocon.asLongs("validList.long").get(0) == 11111;
    assert CHocon.asDoubles("validList.double").get(0) == 1.1;
    assert CHocon.asEnums("validList.enum", CPlatform.class).get(0) == CPlatform.MAC;
  }


  @Test
  public void testStringValid() {
    assert CHocon.asString("stringValid.str").equals("str");
    assert CHocon.asBoolean("stringValid.bool");
    assert CHocon.asNumber("stringValid.num").doubleValue() == 1.1;
    assert CHocon.asInteger("stringValid.int") == 1;
    assert CHocon.asLong("stringValid.long") == 11111;
    assert CHocon.asDouble("stringValid.double") == 1.1;
    assert CHocon.asEnum("stringValid.enum", CPlatform.class) == CPlatform.MAC;
  }

  @Test
  public void testStringValidList1() {
    assert CHocon.asStrings("stringValidList1.str").get(0).equals("str");
    assert CHocon.asBooleans("stringValidList1.bool").get(0);
    assert CHocon.asNumbers("stringValidList1.num").get(0).doubleValue() == 1.1;
    assert CHocon.asIntegers("stringValidList1.int").get(0) == 1;
    assert CHocon.asLongs("stringValidList1.long").get(0) == 11111;
    assert CHocon.asDoubles("stringValidList1.double").get(0) == 1.1;
    assert CHocon.asEnums("stringValidList1.enum", CPlatform.class).get(0) == CPlatform.MAC;
  }

  @Test
  public void testStringValidList2() {
    assert CHocon.asStrings("stringValidList1.str").get(0).equals("str");
    assert CHocon.asBooleans("stringValidList1.bool").get(0);
    assert CHocon.asNumbers("stringValidList1.num").get(0).doubleValue() == 1.1;
    assert CHocon.asIntegers("stringValidList1.int").get(0) == 1;
    assert CHocon.asLongs("stringValidList1.long").get(0) == 11111;
    assert CHocon.asDoubles("stringValidList1.double").get(0) == 1.1;
    assert CHocon.asEnums("stringValidList1.enum", CPlatform.class).get(0) == CPlatform.MAC;
  }
}