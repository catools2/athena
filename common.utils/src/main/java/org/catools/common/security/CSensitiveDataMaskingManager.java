package org.catools.common.security;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

public class CSensitiveDataMaskingManager {
  private static final List<String> maskList = new ArrayList<>();
  private static final List<String> ignoreList = new ArrayList<>();
  private static final String DEFAULT_MASK = "*".repeat(6);

  public static void clear() {
    doAction(
        (maskList, ignoreList) -> {
          maskList.clear();
          ignoreList.clear();
          return true;
        });
  }

  public static void addMask(final String... masks) {
    doAction(
        (maskList, ignoreList) -> {
          for (String mask : masks) {
            if (StringUtils.isNotBlank(mask)) {
              if (!ignoreList.contains(mask) && !maskList.contains(mask)) {
                maskList.add(mask);
              }
              // Sort by longest pattern first to not chop the mask in case if they are partially match
              Collections.sort(maskList, (o1, o2) -> o2.length() - o1.length());
            }
          }
          return true;
        });
  }

  public static void ignore(final String... inputs) {
    doAction(
        (maskList, ignoreList) -> {
          for (String input : inputs) {
            if (StringUtils.isNotBlank(input)) {
              if (!ignoreList.contains(input)) {
                ignoreList.add(input);
              }
              maskList.removeIf(s -> ignoreList.contains(s));
            }
          }
          return true;
        });
  }

  public static String mask(final Object input) {
    final AtomicReference<String> message =
        new AtomicReference<>(input == null ? StringUtils.EMPTY : input.toString());
    if (CSecurityConfigs.maskSensitiveData()) {
      return doAction(
          (maskList, ignoreList) -> {
            for (String securityMask : maskList) {
              while (message.get().contains(securityMask)) {
                message.set(message.get().replace(securityMask, DEFAULT_MASK));
              }
            }
            return message.get();
          });
    }
    return message.get();
  }

  private static synchronized <T> T doAction(BiFunction<List<String>, List<String>, T> func) {
    return func.apply(maskList, ignoreList);
  }
}
