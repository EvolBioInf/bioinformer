package haubold.resources.util;

/**
 * @author Bernhard Haubold
 * Date: May 2, 2003; time: 1:04:21 PM.
 *
 * Description:
 */

import java.awt.Graphics2D;
import java.awt.geom.*;

public class Arrow {

  float arrowSize = 10;
  float arrowAngle = (float) Math.toRadians(15);

  // calculate points for arrowhead
  public void drawArrow(Graphics2D g2, float x1, float y1, float x2, float y2) {
    float angle =
      (float) Math.atan2(y2 - y1, x2 - x1) + (float) Math.PI;
    float x3 = (int) (x2 + Math.cos(angle - arrowAngle) * arrowSize);
    float y3 = (int) (y2 + Math.sin(angle - arrowAngle) * arrowSize);
    float x4 = (int) (x2 + Math.cos(angle + arrowAngle) * arrowSize);
    float y4 = (int) (y2 + Math.sin(angle + arrowAngle) * arrowSize);
    // draw arrowhead
    g2.drawLine((int) x2, (int) y2, (int) x3, (int) y3);
    g2.drawLine((int) x2, (int) y2, (int) x4, (int) y4);
    g2.drawLine((int) x3, (int) y3, (int) x4, (int) y4);
    // draw line
    g2.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
  }

  /**
   * Draw quadratic curve with arrow head at destination point.
   * @param g2 Graphics2D object
   * @param x1 X-coordinate of source
   * @param y1 Y-coordinate of source
   * @param ctrlX X-coordinate of control point
   * @param ctrlY Y-coordinate of control point
   * @param x2 X-coordinate of sink
   * @param y2 Y-coordinate of sink
   */
  public void drawQuadArrow(Graphics2D g2, float x1, float y1,
                            float ctrlX, float ctrlY,
                            float x2, float y2) {
    QuadCurve2D.Float quad = new QuadCurve2D.Float();
    quad.setCurve(x1, y1, ctrlX, ctrlY, x2, y2);
    g2.draw(quad);
    // calculate points for arrowhead
    float angle =
      (float) Math.atan2(y2 - ctrlY, x2 - ctrlX) + (float) Math.PI;
    float x3 = (int) (x2 + Math.cos(angle - arrowAngle) * arrowSize);
    float y3 = (int) (y2 + Math.sin(angle - arrowAngle) * arrowSize);
    float x4 = (int) (x2 + Math.cos(angle + arrowAngle) * arrowSize);
    float y4 = (int) (y2 + Math.sin(angle + arrowAngle) * arrowSize);
    // draw arrowhead
    g2.drawLine((int) x2, (int) y2, (int) x3, (int) y3);
    g2.drawLine((int) x2, (int) y2, (int) x4, (int) y4);
    g2.drawLine((int) x3, (int) y3, (int) x4, (int) y4);
  }

  /**
   * Draw arrow head at arbitrary angles.
   * @param g2 Graphics2D object
   * @param x X-coordinate pointed to by arrow head
   * @param y Y-coordinate pointed to by arrow head
   * @param ctrlX X-coordinate of control point, i.e. a position connected by a straight
   * line to the arrow's point
   * @param ctrlY Y-coordinate of control point, i.e.a position connected by a straight
   * line to the arrow's point
   */
  public void drawArrowHead(Graphics2D g2, float x, float y, float ctrlX, float ctrlY) {
    // calculate points for arrowhead
    float angle =
      (float) Math.atan2(y - ctrlY, x - ctrlX) + (float) Math.PI;
    float x3 = (int) (x + Math.cos(angle - arrowAngle) * arrowSize);
    float y3 = (int) (y + Math.sin(angle - arrowAngle) * arrowSize);
    float x4 = (int) (x + Math.cos(angle + arrowAngle) * arrowSize);
    float y4 = (int) (y + Math.sin(angle + arrowAngle) * arrowSize);
    // draw arrowhead
    g2.drawLine((int) x, (int) y, (int) x3, (int) y3);
    g2.drawLine((int) x, (int) y, (int) x4, (int) y4);
    g2.drawLine((int) x3, (int) y3, (int) x4, (int) y4);
  }

  /**
   * Returns the arrowSize.
   * @return float
   */
  public float getArrowSize() {
    return arrowSize;
  }

  /**
   * Sets the arrowSize.
   * @param arrowSize The arrowSize to set
   */
  public void setArrowSize(float arrowSize) {
    this.arrowSize = arrowSize;
  }

  /**
   * Returns the arrowAngle.
   * @return float
   */
  public float getArrowAngle() {
    return arrowAngle;
  }

  /**
   * Sets the arrowAngle.
   * @param arrowAngle The arrowAngle to set
   */
  public void setArrowAngle(float arrowAngle) {
    this.arrowAngle = arrowAngle;
  }

}
