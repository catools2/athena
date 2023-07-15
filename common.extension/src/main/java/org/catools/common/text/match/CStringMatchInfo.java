package org.catools.common.text.match;

public class CStringMatchInfo<T> {
  private T actual;
  private T match;
  private double percent;

  public CStringMatchInfo(T actual, T match, double percent) {
    this.actual = actual;
    this.match = match;
    this.percent = percent;
  }

  public T getActual() {
    return actual;
  }

  public T getMatch() {
    return match;
  }

  public double getPercent() {
    return percent;
  }

  @Override
  public String toString() {
    return "CStringMatchInfo{"
        + "actual="
        + actual
        + ", match="
        + match
        + ", percent="
        + percent
        + '}';
  }
}
