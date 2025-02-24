package haubold.resources.util;

/**
 * @author Bernhard Haubold
 * Date: Apr 24, 2003; time: 8:45:58 PM.
 *
 * Description:
 */

import java.awt.*;
import java.awt.geom.AffineTransform;

import javax.swing.*;

public class StringTablePanel extends JPanel {
  private static final long serialVersionUID = 1L;
  char[] text;
  BirkhaeuserGUIComponents bgc;

  public StringTablePanel() {
    bgc = new BirkhaeuserGUIComponents();
  }

  public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    RenderingHints qualityHints =
      new RenderingHints(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON);
    qualityHints.put(
      RenderingHints.KEY_RENDERING,
      RenderingHints.VALUE_RENDER_QUALITY);
    g2.setRenderingHints(qualityHints);
    drawTable(g2, text);
  }

  /**
   * Generate the table depiction of the text.
   */
  private Graphics2D drawTable(Graphics2D g2, char[] text) {
    int cellWidth = 17;
    int cellHeight = 17;
    int tableY = 6;
    int tableX = cellWidth;
    int tableWidth = tableX + cellWidth * (text.length);
    int tableHeight = tableY + 2 * cellHeight;
    double xScale =
      (double) this.getWidth() / (double) (tableWidth + cellWidth);
    double yScale =
      (double) this.getHeight() / (double) (tableHeight + tableY);
    g2.setColor(bgc.getColor1());
    g2.fillRect(0, 0, this.getWidth(), this.getHeight());
    g2.setColor(Color.black);
    AffineTransform transform = g2.getTransform();
    double scale = Math.min(xScale,yScale);
    int tx =
      (int) (((double) getWidth() - (double) tableWidth * scale)
             / 2.0);
    int ty =
      (int) (((double) getHeight() - (double) tableHeight * scale)
             / 2.0);
    g2.translate(tx,ty);
    g2.scale(scale, scale);
    if (text.length > 0) {
      g2.drawLine(tableX, tableY, tableWidth, tableY);
      g2.drawLine(
        tableX,
        tableY + cellHeight,
        tableWidth,
        tableY + cellHeight);
      g2.drawLine(tableX, tableHeight, tableWidth, tableHeight);
      int i;
      for (i = 0; i < text.length; i++) {
        g2.setColor(Color.blue);
        g2.drawString(
          String.valueOf(i + 1),
          tableX + cellWidth * i + 1,
          tableY + cellHeight - 1);
        g2.setColor(Color.black);
        g2.drawString(
          String.valueOf(text[i]),
          tableX + cellWidth * i + 1,
          tableY + 2 * cellHeight - 1);
        g2.drawLine(
          tableX + cellWidth * i,
          tableY,
          tableX + cellWidth * i,
          tableHeight);
      }
      g2.drawLine(
        tableX + cellWidth * i,
        tableY,
        tableX + cellWidth * i,
        tableHeight);
    }
    g2.setTransform(transform);
    return g2;
  }

  /**
   * Returns the text.
   * @return char[]
   */
  public char[] getText() {
    return text;
  }

  /**
   * Sets the text.
   * @param text The text to set
   */
  public void setText(char[] text) {
    this.text = text;
    repaint();
  }

}
