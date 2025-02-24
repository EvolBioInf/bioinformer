package haubold.dp;
import java.awt.*;
import java.awt.Graphics2D;
import javax.swing.*;

public class CellPanel extends JPanel {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  boolean horizontal, diagonal, vertical;
  double value;

  public void constructCell(boolean h, boolean d, boolean v, double val) {
    horizontal = h;
    diagonal = d;
    vertical = v;
    value = val;
    repaint();
  }

  public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON);
    System.out.println("value = " + String.valueOf(value));
    g2.drawString(String.valueOf(value), 5, 5);
  }
}
