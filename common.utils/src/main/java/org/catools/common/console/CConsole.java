package org.catools.common.console;

import org.apache.commons.lang3.StringUtils;
import org.catools.common.security.CSensitiveDataMaskingManager;
import org.catools.common.utils.CAnsiUtil;
import org.fusesource.jansi.AnsiConsole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * {@code CConsole} uses to handle console interaction
 */
public class CConsole {
  public static synchronized String print(String input, final Object... params) {
    return print(String.format(input, params));
  }

  public static synchronized String println(String input, final Object... params) {
    return print(String.format(input, params) + System.lineSeparator());
  }

  public static synchronized String print(String input) {
    String output = CSensitiveDataMaskingManager.mask(input);
    if (org.catools.common.utils.CAnsiUtil.isConsoleAvailable()) {
      AnsiConsole.out().print(output);
      AnsiConsole.out().flush();
    } else {
      AnsiConsole.system_out.print(output);
      AnsiConsole.system_out.flush();
    }
    return output;
  }

  public static void printBlue(String input, final Object... params) {
    print(CAnsiUtil.toBlue(input, params));
  }

  public static void printCyan(String input, final Object... params) {
    print(CAnsiUtil.toCyan(input, params));
  }

  public static void printGreen(String input, final Object... params) {
    print(CAnsiUtil.toGreen(input, params));
  }

  public static void printMagenta(String input, final Object... params) {
    print(CAnsiUtil.toMagenta(input, params));
  }

  public static void printRed(String input, final Object... params) {
    print(CAnsiUtil.toRed(input, params));
  }

  public static void printYellow(String input, final Object... params) {
    print(CAnsiUtil.toYellow(input, params));
  }

  public static String prompt(String message, String defaultReturnValue, boolean isSensitive) {
    if (StringUtils.isBlank(defaultReturnValue)) {
      print(message + ": ");
    } else {
      print("%s [%s]: ", message, defaultReturnValue);
    }

    if (isSensitive && org.catools.common.utils.CAnsiUtil.isConsoleAvailable()) {
      return StringUtils.defaultIfBlank(readPassword(), defaultReturnValue);
    }
    return StringUtils.defaultIfBlank(readLine(), defaultReturnValue);
  }

  public static String prompt(String input, final Object... params) {
    return prompt(String.format(input, params), StringUtils.EMPTY, false);
  }

  public static void pressEnterToContinue(String message) {
    println(message);
    pressEnterToContinue();
  }

  public static void pressEnterToContinue() {
    readKey("Press enter to continue...");
  }

  public static String readKey(String message) {
    return prompt(message, "", false);
  }

  // Privet Methods
  private static synchronized String readLine() {
    try {
      return new BufferedReader(new InputStreamReader(System.in)).readLine();
    } catch (IOException e) {
      throw new RuntimeException("Failed to read console input.", e);
    }
  }

  private static synchronized String readPassword() {
    return new String(System.console().readPassword(StringUtils.EMPTY));
  }
}
