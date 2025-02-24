package haubold.coalescent;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

/**
 * Description: Contains coordinates for one mutation.
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Oct 11, 2005; time: 6:02:22 AM.
 */
public class Mutation implements MouseMotionListener {

  private Graphics2D g2;
  private Shape treeShape, haplotypeShapes[], hitRect;
  private boolean treeMouseOver, haplotypeMouseOver;
  private Color mouseColor, treeColor;

  public Mutation() {
    hitRect = new Rectangle2D.Double(0,0,3,3);
    treeMouseOver = false;
    haplotypeMouseOver = false;
  }
  /* (non-Javadoc)
   * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
   */
  public void mouseMoved(MouseEvent e) {
    int i;

    if(hitRect.intersects(treeShape.getBounds2D())) {
      if(!treeMouseOver) {
        treeMouseOver = true;
        g2.setColor(mouseColor);
        g2.fill(treeShape);
      }
      if(!haplotypeMouseOver) {
        haplotypeMouseOver = true;
        g2.setColor(treeColor);
        for(i=0; i<haplotypeShapes.length; i++)
          g2.fill(haplotypeShapes[i]);
      }
    } else {
      if(treeMouseOver) {
        treeMouseOver = false;
        g2.setColor(treeColor);
        g2.fill(treeShape);
      }
      if(haplotypeMouseOver) {
        haplotypeMouseOver = false;
        g2.setColor(treeColor);
        for(i=0; i<haplotypeShapes.length; i++)
          g2.fill(haplotypeShapes[i]);
      }
    }
  }

  public Shape[] getHaplotypeShape() {
    return haplotypeShapes;
  }
  public void setHaplotypeShape(Shape[] haplotypeShapes) {
    this.haplotypeShapes = haplotypeShapes;
  }
  public Shape getTreeShape() {
    return treeShape;
  }
  public void setTreeShape(Shape treeShape) {
    this.treeShape = treeShape;
  }
  public Graphics2D getG2() {
    return g2;
  }
  public void setG2(Graphics2D g2) {
    this.g2 = g2;
  }
  public Color getMouseColor() {
    return mouseColor;
  }
  public void setMouseColor(Color mouseColor) {
    this.mouseColor = mouseColor;
  }
  public Color getTreeColor() {
    return treeColor;
  }
  public void setTreeColor(Color treeColor) {
    this.treeColor = treeColor;
  }
  public void mouseDragged(MouseEvent e) {}
}
