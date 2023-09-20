package org.catools.media.utils;

import boofcv.alg.misc.ImageStatistics;
import boofcv.alg.misc.PixelMath;
import boofcv.alg.template.TemplateMatching;
import boofcv.alg.template.TemplateMatchingIntensity;
import boofcv.factory.template.FactoryTemplateMatching;
import boofcv.factory.template.TemplateScoreType;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.struct.feature.Match;
import boofcv.struct.image.GrayF32;
import lombok.experimental.UtilityClass;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

@UtilityClass
public class CTemplateMatchingUtil {
  public static BufferedImage getMatch(GrayF32 image, GrayF32 template, GrayF32 mask, TemplateScoreType scoreType, int maxMatches) {
    // create output image to show results
    List<Match> found = findMatches(image, template, mask, scoreType, maxMatches);

    // draw rectangle around matches on the base image
    BufferedImage output = drawRectanglesForMatches(image, template, found);

    return output;
  }

  /**
   * Demonstrates how to search for matches of a template inside an image
   *
   * @param image           Image being searched
   * @param template        Template being looked for
   * @param mask            Mask which determines the weight of each template pixel in the match score
   * @param expectedMatches Number of expected matches it hopes to find
   * @return List of match location and scores
   */
  public static List<Match> findMatches(GrayF32 image, GrayF32 template, GrayF32 mask, TemplateScoreType scoreType, int expectedMatches) {
    // create template matcher.
    TemplateMatching<GrayF32> matcher = FactoryTemplateMatching.createMatcher(scoreType, GrayF32.class);

    // Find the points which match the template the best
    matcher.setImage(image);
    matcher.setTemplate(template, mask, expectedMatches);
    matcher.process();

    return matcher.getResults().toList();
  }

  /**
   * Helper function will is finds matches and displays the results as colored rectangles
   */
  private static BufferedImage drawRectanglesForMatches(GrayF32 image, GrayF32 template, List<Match> found) {
    BufferedImage output = new BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_BGR);

    ConvertBufferedImage.convertTo(image, output);
    Graphics2D g2 = output.createGraphics();

    // Search for the cursor in the image. For demonstration purposes it has been pasted 3 times
    g2.setColor(Color.RED);
    g2.setStroke(new BasicStroke(5));

    int r = 2;
    int w = template.width + 2 * r;
    int h = template.height + 2 * r;

    for (Match m : found) {
      int x0 = m.x - r;
      int y0 = m.y - r;
      int x1 = x0 + w;
      int y1 = y0 + h;

      g2.drawLine(x0, y0, x1, y0);
      g2.drawLine(x1, y0, x1, y1);
      g2.drawLine(x1, y1, x0, y1);
      g2.drawLine(x0, y1, x0, y0);
    }
    return output;
  }

  /**
   * Computes the template match intensity image and displays the results. Brighter intensity indicates
   * a better match to the template.
   */
  public static BufferedImage getMatchIntensity(GrayF32 image, GrayF32 template, GrayF32 mask, TemplateScoreType scoreType) {
    // create algorithm for computing intensity image
    TemplateMatchingIntensity<GrayF32> matchIntensity = FactoryTemplateMatching.createIntensity(scoreType, GrayF32.class);

    // apply the template to the image
    matchIntensity.setInputImage(image);
    matchIntensity.process(template, mask);

    // get the results
    GrayF32 intensity = matchIntensity.getIntensity();

    // White will indicate a good match and black a bad match, or the reverse
    // depending on the cost function used.
    float min = ImageStatistics.min(intensity);
    float max = ImageStatistics.max(intensity);
    float range = max - min;
    PixelMath.plus(intensity, -min, intensity);
    PixelMath.divide(intensity, range, intensity);
    PixelMath.multiply(intensity, 255.0f, intensity);

    return new BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_BGR);
  }
}
