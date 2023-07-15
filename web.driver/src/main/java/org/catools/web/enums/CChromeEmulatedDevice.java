package org.catools.web.enums;

import org.apache.commons.lang3.StringUtils;
import org.catools.common.collections.CList;
import org.catools.common.utils.CStringUtil;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.catools.common.utils.CStringUtil.scrunch;

public enum CChromeEmulatedDevice {
  IPHONE_4("iPhone 4"),
  IPHONE_5_SE("iPhone 5/SE"),
  IPHONE_6_7_8("iPhone 6/7/8"),
  IPHONE_6_7_8_PLUS("iPhone 6/7/8 Plus"),
  IPHONE_X("iPhone X"),
  BLACKBERRY_Z30("BlackBerry Z30"),
  NEXUS_4("Nexus 4"),
  NEXUS_5("Nexus 5"),
  NEXUS_5X("Nexus 5X"),
  NEXUS_6("Nexus 6"),
  NEXUS_6P("Nexus 6P"),
  PIXEL_2("Pixel 2"),
  PIXEL_2_XL("Pixel 2 XL"),
  LG_OPTIMUS_L70("LG Optimus L70"),
  NOKIA_N9("Nokia N9"),
  NOKIA_LUMIA_520("Nokia Lumia 520"),
  MICROSOFT_LUMIA_550("Microsoft Lumia 550"),
  MICROSOFT_LUMIA_950("Microsoft Lumia 950"),
  GALAXY_S_III("Galaxy S III"),
  GALAXY_S5("Galaxy S5"),
  KINDLE_FIRE_HDX("Kindle Fire HDX"),
  IPAD_MINI("iPad Mini"),
  IPAD("iPad"),
  IPAD_PRO("iPad Pro"),
  BLACKBERRY_PLAYBOOK("Blackberry PlayBook"),
  NEXUS_10("Nexus 10"),
  NEXUS_7("Nexus 7"),
  GALAXY_NOTE_3("Galaxy Note 3"),
  GALAXY_NOTE_II("Galaxy Note II"),
  LAPTOP_WITH_TOUCH("Laptop with touch"),
  LAPTOP_WITH_HIDPI_SCREEN("Laptop with HiDPI screen"),
  LAPTOP_WITH_MDPI_SCREEN("Laptop with MDPI screen");

  private String deviceName;

  CChromeEmulatedDevice(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public static CChromeEmulatedDevice findBy(String deviceName) {
    if (CStringUtil.isBlank(deviceName)) {
      return null;
    }
    return new CList<>(values())
        .getFirstOrThrow(
            v ->
                scrunch(v.name()).equals(scrunch(deviceName))
                    || scrunch(v.deviceName).equals(scrunch(deviceName)),
            () ->
                new InvalidParameterException(
                    String.format(
                        "Could not evaluate provided device name %s, valid values are [%s]",
                        deviceName, StringUtils.join(getDeviceNames(), ", "))));
  }

  public static List<String> getDeviceNames() {
    return Arrays.asList(values()).stream().map(v -> v.deviceName).collect(Collectors.toList());
  }
}
