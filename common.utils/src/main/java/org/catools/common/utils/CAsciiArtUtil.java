package org.catools.common.utils;

import com.github.lalyos.jfiglet.FigletFont;
import lombok.experimental.UtilityClass;
import org.catools.common.exception.CConvertFigletFontException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class CAsciiArtUtil {
  public static List<String> convert(String resourceName, ClassLoader loader, String input) {
    List<String> lines;
    try {
      InputStream resourceAsStream = loader.getResourceAsStream(resourceName);
      lines = new ArrayList<>(Arrays.asList(FigletFont.convertOneLine(resourceAsStream, input).split("\n")));
    } catch (IOException e) {
      throw new CConvertFigletFontException(e);
    }
    lines.removeIf(l -> l == null || l.replaceAll("\\s+", "").isEmpty());
    int length = lines.stream().findFirst().orElse("").length();
    lines.add("".repeat(length));
    return lines;
  }

  public static List<String> convertWithDoh(String input) {
    return convert("fonts/doh.flf", CAsciiArtUtil.class.getClassLoader(), input);
  }
}
