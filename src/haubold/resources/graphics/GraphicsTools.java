package haubold.resources.graphics;
import java.awt.*;

/**
 * Description: Collection of methods for
 * manipulating the position of graphical opjects.
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Dec 18, 2003; time: 10:21:38 PM.
 */
public class GraphicsTools {

  public Graphics2D centerScale(
    Graphics2D g,
    Dimension panelDimension,
    Dimension objectDimension) {
    double xScale =
      (double) panelDimension.width / (double) objectDimension.width;
    double yScale =
      (double) panelDimension.height / (double) objectDimension.height;
    double scale = Math.min(xScale, yScale);
    int tx =
      (int) (((double) panelDimension.width - (double) objectDimension.width * scale) / 2.0);
    int ty =
      (int) (((double) panelDimension.height - (double) objectDimension.width * scale) / 2.0);
    g.translate(tx, ty);
    g.scale(scale, scale);
    return g;
  }

  /**
   * Set standard rendering hints.
   * @param g2
   * @return
   */
  public Graphics2D setRenderingHints(Graphics2D g2) {
    RenderingHints qualityHints =
      new RenderingHints(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON);
    qualityHints.put(
      RenderingHints.KEY_RENDERING,
      RenderingHints.VALUE_RENDER_QUALITY);
    g2.setRenderingHints(qualityHints);
    return g2;
  }

}
