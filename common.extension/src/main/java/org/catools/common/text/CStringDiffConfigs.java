package org.catools.common.text;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;
import org.fusesource.jansi.Ansi;

@UtilityClass
public class CStringDiffConfigs {
  public static int getDiffEditCost() {
    return CHocon.asInteger(Configs.CATOOLS_DIFF_TEXT_EDIT_COST);
  }

  public static Ansi.Color getInsertColor() {
    return CHocon.asEnum(Configs.CATOOLS_DIFF_TEXT_INSERT_COLOR, Ansi.Color.class);
  }

  public static Ansi.Color getDeleteColor() {
    return CHocon.asEnum(Configs.CATOOLS_DIFF_TEXT_DELETE_COLOR, Ansi.Color.class);
  }

  public static Ansi.Color getEqualColor() {
    return CHocon.asEnum(Configs.CATOOLS_DIFF_TEXT_EQUAL_COLOR, Ansi.Color.class);
  }

  public static String getInsertFormat() {
    return CHocon.asString(Configs.CATOOLS_DIFF_TEXT_INSERT_FORMAT);
  }

  public static String getDeleteFormat() {
    return CHocon.asString(Configs.CATOOLS_DIFF_TEXT_DELETE_FORMAT);
  }

  public static String getEqualFormat() {
    return CHocon.asString(Configs.CATOOLS_DIFF_TEXT_EQUAL_FORMAT);
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_DIFF_TEXT_EDIT_COST("catools.diff_text.edit_cost"),
    CATOOLS_DIFF_TEXT_INSERT_FORMAT("catools.diff_text.insert_format"),
    CATOOLS_DIFF_TEXT_DELETE_FORMAT("catools.diff_text.delete_format"),
    CATOOLS_DIFF_TEXT_EQUAL_FORMAT("catools.diff_text.equal_format"),
    CATOOLS_DIFF_TEXT_INSERT_COLOR("catools.diff_text.insert_color"),
    CATOOLS_DIFF_TEXT_DELETE_COLOR("catools.diff_text.delete_color"),
    CATOOLS_DIFF_TEXT_EQUAL_COLOR("catools.diff_text.equal_color");

    private final String path;
  }
}
