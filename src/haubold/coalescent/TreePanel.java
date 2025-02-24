package haubold.coalescent;

/**
 * @author Bernhard Haubold
 * Date: May 1, 2003; time: 3:56:09 PM.
 *
 * Description:
 */

import javax.swing.*;

import haubold.resources.util.BirkhaeuserGUIComponents;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.*;
import java.util.ArrayList;

public class TreePanel extends JPanel implements MouseMotionListener {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  double[][] treeCoords;
  double[][] mutCoords;
  int[] leafId;
  boolean tree = false;
  boolean mutations = false;
  int w = 400;
  int h = 200;
  int border = w/10;
  int nodes;
  BirkhaeuserGUIComponents bgc = new BirkhaeuserGUIComponents();
  ArrayList<Rectangle2D> mutationList;
  Rectangle hitRect;
  double scale, xTranslate, yTranslate;
  Mutation[] mutationArray;

  public TreePanel() {
    mutationList = new ArrayList<Rectangle2D>();
    hitRect = new Rectangle(0,0,3,3);
  }

  public void reset() {
    repaint();
  }

  public void drawTree(double[][] tc, int[] li) {
    treeCoords = tc;
    leafId = li;

    tree = true;
    repaint();
  }

  public void drawMutations(double[][] mc) {
    mutCoords = mc;
    mutations = true;
    repaint();
  }

  public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON);
    int i, mut;
    int sampleSize;
    double unitX = 1.0;
    double unitY = 1.0;
    double unitLeaves = 1.0;
    Shape rectangle = new Rectangle(0, 0, getWidth(), getHeight());
    double highestP, xScale, yScale;
    g2.setPaint(bgc.getColor1());
    g2.fill(rectangle);
    g2.setPaint(Color.blue);
    highestP = 0.0;
    AffineTransform transform = g2.getTransform();

    xScale = (double)getWidth()/(double)w;
    yScale = (double)getHeight()/(double)h;
    double scale = Math.min(xScale,yScale);
    xTranslate = ((double) getWidth() - (double) w * scale) / 2.0;
    yTranslate = ((double) getHeight() - (double) h * scale) / 2.0;
    g2.translate(xTranslate,yTranslate);
    g2.scale(scale,scale);
    if (tree) {
      nodes = treeCoords.length;
      sampleSize = (nodes + 1) / 2;
      for (i = 0; i < nodes; i++) {
        if (treeCoords[i][3] > highestP) {
          highestP = treeCoords[i][3];
        }
      }
      unitX = (double) (w - 4.0 * border) / (double) (nodes - 1);
      unitLeaves =
        (double) (w - 4 * border) / (double) ((nodes + 1) / 2 - 1);
      unitY = (double) (h - 2.0 * border) / 3.0;
      if (highestP * unitY > h - border) {
        yScale = (double) (h - 2 * border) / (highestP * unitY);
      } else {
        yScale = 1.0;
      }
      g2.setPaint(Color.black);
      g2.setFont(new Font("Times", Font.PLAIN, 10));

      g2.drawLine(10,h-border,10,(int)(h-border-unitY*yScale));
      g2.drawLine(8,h-border,12,h-border);
      g2.drawLine(8,(int)(h-border-unitY*yScale),12,(int)(h-border-unitY*yScale));
      g2.drawString("2N generations", 14,(int)(h-border-unitY*yScale/2.0+3));

      g2.translate(100, 0);
      g2.setPaint(Color.black);
      if (sampleSize < 16) {
        for (i = 0; i < sampleSize; i++) {
          g2.drawString(
            String.valueOf(leafId[i] + 1),
            (int) (border / 2 + i * unitLeaves) - 2,
            h - 30);
        }
      }
      g2.setPaint(Color.blue);
      for (i = 0; i < nodes; i++) {
        g2.drawLine(
          (int) (border / 2 + treeCoords[i][0] * unitX),
          (int) (h - border - treeCoords[i][1] * unitY * yScale),
          (int) (border / 2 + treeCoords[i][2] * unitX),
          (int) (h - border - treeCoords[i][3] * unitY * yScale));
      }
    }
    if (mutations) {
      mutationList.clear();
      g2.setPaint(Color.red);
      mut = mutCoords.length;
      for (i = 0; i < mut; i++) {
//				g2.drawString("+",
//				(float)border/2.0f+(float)mutCoords[i][0]*(float)unitX,
//				(float)h-(float)border-(float)mutCoords[i][1]*(float)unitY*(float)yScale);
        Rectangle2D dot =
          new Rectangle2D.Float(
          (int) (border / 2 + mutCoords[i][0] * unitX),
          (int) (h - border - mutCoords[i][1] * unitY * yScale),
          2,
          2);
        mutationList.add(dot);
        g2.fill(dot);
      }
    }
    g2.setTransform(transform);
  }
  public void setTree(boolean t) {
    tree = t;
  }
  public void setMutations(boolean m) {
    mutations = m;
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
   */
  public void mouseDragged(MouseEvent e) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
   */
  public void mouseMoved(MouseEvent e) {
    int i, n;

    hitRect.x = (int) ((double) (e.getX() - xTranslate) / scale);
    hitRect.y = (int) ((double) (e.getY() - yTranslate) / scale);

    n = mutationList.size();
    for(i=0; i<n; i++) {
      if(((Shape)mutationList.get(i)).intersects(hitRect))
        ;
    }

  }

  public void setMutationArray(Mutation[] mutationArray) {
    this.mutationArray = mutationArray;
  }

}
