package org.catools.web.axe;

import com.deque.html.axecore.selenium.AxeBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.catools.common.utils.CJsonUtil;
import org.catools.common.utils.CResourceUtil;
import org.catools.web.axe.entities.CAxePage;
import org.openqa.selenium.WebDriver;

import java.net.URL;

@Data
@NoArgsConstructor
public class CAxeAnalyzer {
  private static final URL AXE_MIN_JS = CResourceUtil.getResource("axe.min.js", CAxeAnalyzer.class);

  public static CAxePage analyzePage(final WebDriver driver) {
    CAxePage page = CJsonUtil.read(new AxeBuilder().analyze(driver).toString(), CAxePage.class);
    page.setTitle(driver.getTitle());
    return page;
  }
}
