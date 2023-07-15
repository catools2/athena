package org.catools.common.console;

import org.catools.common.utils.CStringUtil;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

/**
 * {@code CAnsiColor} uses to coloring text for better console UX
 */
public class CAnsiColor {
  public static final String RESET = "\u001B[1;39;49;0m\u001B[m";

  static {
    AnsiConsole.systemInstall();
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
    return Ansi.ansi().bold().fg(fg).bg(bg).a(attr).a(input).reset().toString();
  }
}
