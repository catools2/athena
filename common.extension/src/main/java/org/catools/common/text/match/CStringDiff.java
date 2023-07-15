package org.catools.common.text.match;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.catools.common.text.CStringDiffConfigs;
import org.catools.common.utils.CAnsiUtil;
import org.fusesource.jansi.Ansi;

import java.util.LinkedList;
import java.util.List;

public class CStringDiff {
  private static final int DIFF_EDIT_COST = CStringDiffConfigs.getDiffEditCost();

  public static String prettyDiff(String original, String text2) {
    return prettyDiff(original, text2, DIFF_EDIT_COST);
  }

  public static String prettyDiff(String original, String text2, int diffEditCost) {
    LinkedList<DiffMatchPatch.Diff> diff = diff(original, text2, diffEditCost);
    if (diff.isEmpty()) {
      return "";
    }
    return diffPrettyFormat(
        diff,
        CStringDiffConfigs.getEqualFormat(),
        CStringDiffConfigs.getDeleteFormat(),
        CStringDiffConfigs.getInsertFormat());
  }

  public static String coloredDiff(String original, String text2) {
    return coloredDiff(original, text2, DIFF_EDIT_COST);
  }

  public static String coloredDiff(String original, String text2, int diffEditCost) {
    LinkedList<DiffMatchPatch.Diff> diffs = diff(original, text2, diffEditCost);
    if (diffs.isEmpty()) {
      return "";
    }
    return coloredDiff(
        diffs,
        CStringDiffConfigs.getEqualColor(),
        CStringDiffConfigs.getDeleteColor(),
        CStringDiffConfigs.getInsertColor());
  }

  public static LinkedList<DiffMatchPatch.Diff> diff(String original, String text2) {
    return diff(original, text2, DIFF_EDIT_COST);
  }

  public static LinkedList<DiffMatchPatch.Diff> diff(
      String original, String text2, int diffEditCost) {
    DiffMatchPatch diffMatchPatch = new DiffMatchPatch();
    diffMatchPatch.diffEditCost = (short) diffEditCost;
    LinkedList<DiffMatchPatch.Diff> diffs = diffMatchPatch.diffMain(original, text2);
    diffMatchPatch.diffCleanupEfficiency(diffs);
    return diffs;
  }

  public static String diffPrettyFormat(
      List<DiffMatchPatch.Diff> diffs,
      String equalFormat,
      String deleteFormat,
      String insertFormat) {
    StringBuilder output = new StringBuilder();
    for (DiffMatchPatch.Diff diff : diffs) {
      switch (diff.operation) {
        case INSERT:
          output.append(String.format(insertFormat, diff.text));
          break;
        case DELETE:
          output.append(String.format(deleteFormat, diff.text));
          break;
        case EQUAL:
          output.append(String.format(equalFormat, diff.text));
          break;
      }
    }
    return output.toString();
  }

  public static String coloredDiff(
      List<DiffMatchPatch.Diff> diffs,
      Ansi.Color equalForeground,
      Ansi.Color deleteForeground,
      Ansi.Color insertForeground) {
    return coloredDiff(
        diffs,
        Ansi.Color.DEFAULT,
        equalForeground,
        Ansi.Color.DEFAULT,
        deleteForeground,
        Ansi.Color.DEFAULT,
        insertForeground);
  }

  public static String coloredDiff(
      List<DiffMatchPatch.Diff> diffs,
      Ansi.Color equalBackground,
      Ansi.Color equalForeground,
      Ansi.Color deleteBackground,
      Ansi.Color deleteForeground,
      Ansi.Color insertBackground,
      Ansi.Color insertForeground) {
    StringBuilder output = new StringBuilder();
    output.append(CAnsiUtil.RESET);
    for (DiffMatchPatch.Diff diff : diffs) {
      switch (diff.operation) {
        case INSERT: output.append(
          CAnsiUtil.encode(diff.text, Ansi.Attribute.INTENSITY_BOLD, insertForeground, insertBackground));
          break;
        case DELETE: output.append(
          CAnsiUtil.encode(diff.text, Ansi.Attribute.INTENSITY_BOLD, deleteForeground, deleteBackground));
          break;
        case EQUAL: output.append(
          CAnsiUtil.encode(diff.text, Ansi.Attribute.INTENSITY_BOLD, equalForeground, equalBackground));
          break;
      }
    }
    output.append(CAnsiUtil.RESET);
    return output.toString();
  }
}
