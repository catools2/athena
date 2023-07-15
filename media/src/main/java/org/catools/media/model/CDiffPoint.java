package org.catools.media.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.awt.*;

@Data
@Accessors(chain = true)
public class CDiffPoint extends Point {
  private int originalColor;
  private int diffColor;

  public CDiffPoint(int x, int y, int originalColor, int diffColor) {
    super(x, y);
    this.originalColor = originalColor;
    this.diffColor = diffColor;
  }
}
