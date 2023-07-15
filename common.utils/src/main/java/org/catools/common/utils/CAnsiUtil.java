package org.catools.common.utils;

import lombok.experimental.UtilityClass;
import org.catools.common.configs.CAnsiConfigs;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.FilterOutputStream;
import java.lang.reflect.Field;

@UtilityClass
public class CAnsiUtil {
  public static final String RESET = "\u001B[1;39;49;0m\u001B[m";
  private static Boolean consoleIsAvailable = null;
  private static Boolean systemOutSupportAnsi = null;

  static {
    AnsiConsole.systemInstall();
  }

  public static boolean isOutputSupportAnsi() {
    return isConsoleAvailable() || isSystemOutSupportAnsi() || isXterm();
  }

  public static boolean isXterm() {
    String term = System.getenv("TERM");
    return term != null && term.toLowerCase().startsWith("xterm");
  }

  public static boolean isSystemOutSupportAnsi() {
    if (systemOutSupportAnsi == null) {
      try {
        Field field = FilterOutputStream.class.getDeclaredField("out");
        field.setAccessible(true);
        systemOutSupportAnsi =
            field.get(System.out).getClass().getName().toLowerCase().contains("ansi");
        field.setAccessible(false);
      } catch (Throwable t) {
        systemOutSupportAnsi = false;
      }
    }
    return systemOutSupportAnsi.booleanValue();
  }

  public static boolean isConsoleAvailable() {
    if (consoleIsAvailable == null) {
      consoleIsAvailable = java.lang.System.console() != null;
    }
    return consoleIsAvailable.booleanValue();
  }

  public static String toBlue(String input, final Object... params) {
    return encode(
        CStringUtil.format(input, params),
        Ansi.Attribute.INTENSITY_BOLD,
        Ansi.Color.BLUE,
        Ansi.Color.DEFAULT);
  }

  public static String toCyan(String input, final Object... params) {
    return encode(
        CStringUtil.format(input, params),
        Ansi.Attribute.INTENSITY_BOLD,
        Ansi.Color.CYAN,
        Ansi.Color.DEFAULT);
  }

  public static String toGreen(String input, final Object... params) {
    return encode(
        CStringUtil.format(input, params),
        Ansi.Attribute.INTENSITY_BOLD,
        Ansi.Color.GREEN,
        Ansi.Color.DEFAULT);
  }

  public static String toMagenta(String input, final Object... params) {
    return encode(
        CStringUtil.format(input, params),
        Ansi.Attribute.INTENSITY_BOLD,
        Ansi.Color.MAGENTA,
        Ansi.Color.DEFAULT);
  }

  public static String toRed(String input, final Object... params) {
    return encode(
        CStringUtil.format(input, params),
        Ansi.Attribute.INTENSITY_BOLD,
        Ansi.Color.RED,
        Ansi.Color.DEFAULT);
  }

  public static String toYellow(String input, final Object... params) {
    return encode(
        CStringUtil.format(input, params),
        Ansi.Attribute.INTENSITY_BOLD,
        Ansi.Color.YELLOW,
        Ansi.Color.DEFAULT);
  }

  public static String toDefault(String input, final Object... params) {
    return encode(
        CStringUtil.format(input, params),
        Ansi.Attribute.RESET,
        Ansi.Color.DEFAULT,
        Ansi.Color.DEFAULT);
  }

  public static String encode(Object input, Ansi.Attribute attr, Ansi.Color fg, Ansi.Color bg) {
    if (CAnsiConfigs.isPrintInColorAvailable()) {
      return Ansi.ansi().bold().fg(fg).bg(bg).a(attr).a(input).reset().toString();
    } else {
      return input == null ? "null" : input.toString();
    }
  }
}
