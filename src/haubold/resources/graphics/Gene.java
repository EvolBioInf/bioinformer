package haubold.resources.graphics;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

/**
 * @author Bernhard Haubold
 * Date: Jun 14, 2003; time: 10:47:00 AM.
 *
 * Description:
 */
public class Gene extends JComponent {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  int xPosition;
  int yPosition;
  int height = 10;
  int length = 100;
  Color defaultColor = Color.red;
  Color mouseOverColor = Color.blue;
  Color clickColor = Color.green;
  Rectangle geneRect;
  JPanel drawing;

  public Gene(int xPosition, int yPosition, int length) {
    this.xPosition = xPosition;
    this.length = length;
    geneRect = new Rectangle(xPosition,yPosition,length,height);
  }

  public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;
    AffineTransform transform = g2.getTransform();
    g2.setColor(defaultColor);
    g2.fill(geneRect);
    g2.setTransform(transform);
  }


  class GeneMouseAdapter extends MouseAdapter {
    public void mousePressed(MouseEvent evt) {
      System.out.println("Mouse pressed");
    }
  }
  /**
   * Returns the parent.
   * @return JPanel
   */
  public JPanel getDrawing() {
    return drawing;
  }

  /**
   * Sets the parent.
   * @param parent The parent to set
   */
  public void setDrawing(JPanel drawing) {
    this.drawing = drawing;
  }

  /**
   * Returns the geneRect.
   * @return Rectangle
   */
  public Rectangle getGeneRect() {
    return geneRect;
  }

  /**
   * Sets the geneRect.
   * @param geneRect The geneRect to set
   */
  public void setGeneRect(Rectangle geneRect) {
    this.geneRect = geneRect;
  }

}
