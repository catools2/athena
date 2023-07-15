package org.catools.reportportal.configs;

import com.epam.ta.reportportal.ws.model.attribute.ItemAttributesRQ;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;
import org.catools.common.utils.CStringUtil;
import org.catools.reportportal.utils.CReportPortalAttributeUtil;

import java.util.Set;

public class CRPConfigs {
  public static boolean addPackageNameToMethodDescription() {
    return CHocon.asBoolean(Configs.CATOOLS_REPORT_PORTAL_ADD_PACKAGE_NAME_TO_METHOD_DESCRIPTION);
  }

  public static boolean addClassNameToMethodDescription() {
    return CHocon.asBoolean(Configs.CATOOLS_REPORT_PORTAL_ADD_CLASS_NAME_TO_METHOD_DESCRIPTION);
  }

  public static boolean isReportPortalEnable() {
    return CStringUtil.isNotBlank(getLaunchId());
  }

  public static String getLaunchId() {
    return System.getProperty("rp.launch.id");
  }

  public static Set<ItemAttributesRQ> getAttributes() {
    return CReportPortalAttributeUtil.getAttributes(System.getProperty("rp.attributes", ""));
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_REPORT_PORTAL_ADD_PACKAGE_NAME_TO_METHOD_DESCRIPTION("catools.report_portal.add_package_name_to_method_description"),
    CATOOLS_REPORT_PORTAL_ADD_CLASS_NAME_TO_METHOD_DESCRIPTION("catools.report_portal.add_class_name_to_method_description");

    private final String path;
  }
}
