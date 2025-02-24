package haubold.numAlignments;

/**
 * @author Bernhard Haubold
 * Date: May 2, 2003; time: 9:48:58 PM.
 *
 * Description:
 */

import haubold.resources.util.BirkhaeuserGUIComponents;
import java.awt.*;
import java.awt.Graphics2D;
import javax.swing.*;

import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.*;
import java.text.*;

public class ResultPanel extends JPanel {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  DecimalFormat form;
  double result;
  BirkhaeuserGUIComponents bgc;
  Font font12;

  public ResultPanel() {
    form = new DecimalFormat("#0.##E0");
    bgc = new BirkhaeuserGUIComponents();
    font12 = new Font("Helvetica", Font.BOLD, 12);
  }

  public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(bgc.getColor1());
    g2.setFont(font12);
    g2.fillRect(0, 0, getWidth(), getHeight());
    String resultString1 =
      "Number of Possible Alignments ";
    String resultString2 = form.format(result);
    AffineTransform transform = g2.getTransform();
    FontRenderContext frc = g2.getFontRenderContext();
    TextLayout tl1 = new TextLayout(resultString1,font12, frc);
    TextLayout tl2 = new TextLayout(resultString2, font12, frc);
    Rectangle2D stringRect1 = tl1.getBounds();
    Rectangle2D stringRect2 = tl2.getBounds();
    double stringLength1 = stringRect1.getWidth();
    double stringLength2 = stringRect2.getWidth();
    double stringHeight = stringRect1.getHeight();
    double scaleX =
      (double) (getWidth()) / stringLength1 * 0.9;
    double scaleY = (double) getHeight() / stringHeight * 0.9;
    double scale = Math.min(scaleX, scaleY);
    int tx =
      (int) (((double) getWidth()
              - (double) stringLength1 * scale)
             / 2.0);
    int ty = (int) (((double) getHeight() - stringHeight*scale) / 2.0);
    g2.translate(tx, ty);
    g2.scale(scale, scale);
    g2.setColor(Color.black);
    tl1.draw(g2,0,0);
    g2.setTransform(transform);
//		scaleX =
//			(double) (getWidth()) / stringLength2 * 0.9;
//		scaleY = (double) getHeight() / stringHeight * 0.9;
//		scale = Math.min(scaleX, scaleY);
    tx =
      (int) (((double) getWidth()
              - (double) stringLength2 * scale)
             / 2.0);
    ty = (int) (((double) getHeight() - stringHeight*scale) / 2.0);
    g2.translate(tx, ty);
    g2.scale(scale, scale);
    g2.setColor(bgc.getColor3());
    tl2.draw(g2,0,20);
  }

  /**
   * Returns the result.
   * @return double
   */
  public double getResult() {
    return result;
  }

  /**
   * Sets the result.
   * @param result The result to set
   */
  public void setResult(double result) {
    this.result = result;
    repaint();
  }

}
