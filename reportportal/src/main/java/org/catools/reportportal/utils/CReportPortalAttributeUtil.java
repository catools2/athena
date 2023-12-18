package org.catools.reportportal.utils;

import com.epam.ta.reportportal.ws.model.attribute.ItemAttributesRQ;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.utils.CStringUtil;

@UtilityClass
@Slf4j
public class CReportPortalAttributeUtil {
  public static CSet<ItemAttributesRQ> getAttributes(String attributes) {
    CSet<ItemAttributesRQ> output = new CSet<>();
    if (CStringUtil.isNotBlank(attributes)) {
      CList<String> keyValues = CList.of(attributes.split(";"));
      for (String keyValue : keyValues) {
        String[] groups = keyValue.split(":", 2);
        if (groups.length == 1) {
          output.add(new ItemAttributesRQ(groups[0]));
        } else if (groups.length == 2) {
          if (CStringUtil.isBlank(groups[1])) {
            log.warn("Skip Annotation, value cannot be empty/null! key:{}, value:{}", groups[0], groups[1]);
          } else {
            output.add(new ItemAttributesRQ(groups[0], groups[1]));
          }
        } else {
          log.warn("Annotation parser failed to identify valid parameters in {}", keyValue);
        }
      }
    }
    return output;
  }
}
