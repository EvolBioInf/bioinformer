package haubold.coalescent;

/**
 * @author Bernhard Haubold
 * Date: May 1, 2003; time: 4:00:45 PM.
 *
 * Description:
 */

import javax.swing.*;

import haubold.resources.util.BirkhaeuserGUIComponents;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.geom.*;

public class HaplotypePanel extends JPanel {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  BirkhaeuserGUIComponents bgc = new BirkhaeuserGUIComponents();
  int h = 150;
  int w = 503;
  boolean draw = false;
  double[][] haplotypes;
  public void setHeight(int h) {
  }

  public void setWidth(int w) {
  }

  public void drawHaplotypes(double[][] ha) {
    haplotypes = ha;
    draw = true;
    repaint();
  }

  public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON);
    int border = 10;
    Shape rectangle = new Rectangle(0, 0, getWidth(), getHeight());
    g2.setPaint(bgc.getColor2());
    AffineTransform transform = g2.getTransform();
    g2.fill(rectangle);
    double xScale = (double)getWidth()/(double)w;
    double yScale = (double)getHeight()/(double)h;
    double scale = Math.min(xScale,yScale);
    int	tx =
      (int) (((double) getWidth() - (double) w * scale)
             / 2.0);
    int	ty =
      (int) (((double) getHeight() - (double) h * scale)
             / 2.0);
    g2.translate(tx,ty);
    g2.scale(scale,scale);
    if (draw) {
      int sampleSize = haplotypes.length;
      int positions = haplotypes[0].length;
      int i, j;
      int step = (int) ((double) (h - border) / (double) sampleSize);
      if (step < 1) {
        step = 1;
      }
      double yPos = border;
      for (i = 0; i < sampleSize; i++) {
        g2.setPaint(Color.black);
        if (sampleSize < 16) {
          g2.drawString(String.valueOf(i + 1), 2, (int) yPos + 2);
        }
        g2.drawLine(2 * border, (int) yPos, w - border, (int) yPos);
        g2.setPaint(Color.red);
        for (j = 0; j < positions; j++) {
          if (haplotypes[i][j] > 0.0) {
            Rectangle2D dot =
              new Rectangle2D.Float(
              (int) (2 * border
                     + haplotypes[i][j]
                     * (double) (w - 3 * border)),
              (int) yPos - 2,
              4,
              4);
            g2.fill(dot);
          }
        }
        yPos = yPos + step;
      }
    }
    g2.setTransform(transform);
  }
  public void setDrawing(boolean d) {
    draw = d;
  }

}
