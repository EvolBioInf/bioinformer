package haubold.sm;

/**
 * @author Bernhard Haubold
 * Date: Apr 5, 2003; time: 12:48:55 PM.
 *
 * Description:
 */
import haubold.resources.util.*;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class MatrixDisplayPanel extends PrintableJPanel {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  int[][] values;
  int percentDifference;
  String aminoAcids[];
  BirkhaeuserGUIComponents bgc;

  Color backgroundColor;;

  public MatrixDisplayPanel(int[][] values, String[] aminoAcids, int percentDifference) {
    this.values = values;
    this.aminoAcids = aminoAcids;
    this.percentDifference = percentDifference;
    bgc = new BirkhaeuserGUIComponents();
    backgroundColor = bgc.getColor1();

  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    int i, j, strLen;
    // Step sizes in the x and y dimensions.
    int stepX = 40;
    int stepY = 20;
    // Points of the rectangle containing the PAM matrix.
    int rx1 = 0;
    int ry1 = 0;
    int rx2 = 21 * stepX -  stepX/2;
    int ry2 = 22 * stepY - stepY/2;
    String str = new String();
    Font courier = new Font("courier", Font.BOLD, 18);
    Font times = new Font("times", Font.PLAIN, 18);
    g2.setFont(courier);
    g2.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON);
    Dimension dim = getSize();
    int w = (int) dim.getWidth();
    int h = (int) dim.getHeight();
    g2.setColor(backgroundColor);
    g2.fillRect(0, 0, w, h);
    AffineTransform transform = g2.getTransform();
    // Check whether or not percent difference is printed to screen.
    if(percentDifference < 0) {
//			g2.translate(0,stepY);
    }

    // Scaling & centering
    double scaleX = (double)getWidth() / 23.0 / (double)stepX;
    double scaleY = (double)getHeight() / 24.0 / (double)stepY;
    double scale = Math.min(scaleX,scaleY);
    int tx =
      (int) (((double) getWidth() - (double) (rx2-rx1) * scale)
             / 2.0);
    // Check whether program prints out percent difference
    int ty;
    if(percentDifference>0) {
      ty =
        (int) (((double) getHeight() - (double) (ry2-ry1+stepY) * scale)
               / 2.0);
    } else {
      ty =
        (int) (((double) getHeight() - (double) (ry2-ry1) * scale)
               / 2.0);
    }
    g2.translate(tx,ty);
    g2.scale(scale, scale);
    g2.setColor(Color.black);
    g2.drawRoundRect(rx1,ry1,rx2,ry2,30,30);

    // Draw amino acid designations along x & y axis.
    g2.setColor(Color.red);
    for (i = 0; i < 20; i++) {
      g2.drawString(
        aminoAcids[i],
        (i+1) * stepX - 2,
        stepY);
      g2.drawString(aminoAcids[i], 4, (i+2) * stepY);
    }
    // Draw entries of PAM matrix.
    for (i = 0; i < 20; i++) {
      for (j = 0; j < 20; j++) {
        if (i == j) {
          g2.setColor(Color.blue);
        } else {
          g2.setColor(Color.black);
        }
        str = String.valueOf(values[j][i]);
        strLen = str.length();
        g2.drawString(
          str,
          (i) * stepX + (stepX + 3 - strLen * 7),
          (j+2) * stepY);
      }
    }
    g2.setFont(times);
    // Draw expected percent difference.
    if(percentDifference > 0.0) {
      g2.drawString(
        " Expected difference = " + String.valueOf(percentDifference) + "%",
        8*stepX,
        23 * stepY);
    }
    g2.setTransform(transform);
  }
  /**
   * @return
   */
  public String[] getAminoAcids() {
    return aminoAcids;
  }

  /**
   * @return
   */
  public int getPercentDifference() {
    return percentDifference;
  }

  /**
   * @return
   */
  public int[][] getValues() {
    return values;
  }

  /**
   * @param strings
   */
  public void setAminoAcids(String[] strings) {
    aminoAcids = strings;
  }

  /**
   * @param i
   */
  public void setPercentDifference(int i) {
    percentDifference = i;
  }

  /**
   * @param is
   */
  public void setValues(int[][] is) {
    values = is;
  }

  /**
   * @return
   */
  public Color getBackgroundColor() {
    return backgroundColor;
  }

  /**
   * @param color
   */
  public void setBackgroundColor(Color color) {
    backgroundColor = color;
  }

}
