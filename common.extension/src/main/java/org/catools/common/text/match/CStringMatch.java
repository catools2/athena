package org.catools.common.text.match;

import org.apache.commons.lang3.StringUtils;
import org.catools.common.collections.CList;

import java.util.List;
import java.util.function.Function;

import static org.catools.common.text.match.CStringMatchUtil.calculateMatchValue;

public class CStringMatch {
  public static <T> CStringMatchInfo getBestMatch(
      T actual, List<T> matches, Function<T, String> toStrFunc, int acceptableMatchValue) {
    return getMatches(actual, matches, toStrFunc, acceptableMatchValue).stream()
        .sorted((o1, o2) -> (int) ((o2.getPercent() - o1.getPercent()) * 1000))
        .findFirst()
        .orElse(null);
  }

  public static <T> CList<CStringMatchInfo<T>> getMatches(
      T actual, List<T> matches, Function<T, String> toStrFunc, int acceptableMatchValue) {
    CList<CStringMatchInfo<T>> matchInfo = new CList<>();
    for (T match : matches) {
      matchInfo.add(getMatch(actual, match, toStrFunc, acceptableMatchValue));
    }
    return matchInfo.getAll(m -> m.getPercent() >= acceptableMatchValue);
  }

  public static <T> CStringMatchInfo<T> getMatch(
      T actual, T match, Function<T, String> toStrFunc, int acceptableMatchValue) {
    if (StringUtils.equals(toStrFunc.apply(actual), toStrFunc.apply(match))) {
      return new CStringMatchInfo<T>(actual, match, 100d);
    }
    double calcResult = calculateMatchValue(toStrFunc.apply(actual), toStrFunc.apply(match));
    return new CStringMatchInfo<T>(
        actual, match, calcResult >= acceptableMatchValue ? calcResult : 0);
  }
}
