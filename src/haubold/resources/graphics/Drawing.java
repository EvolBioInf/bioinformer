package haubold.resources.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.*;
/**
 * @author Bernhard Haubold
 * Date: Jun 14, 2003; time: 11:13:29 AM.
 *
 * Description:
 */
public class Drawing extends JPanel {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  ArrayList<Gene> geneList = new ArrayList<Gene>();
  Gene gene;
  MouseListener[] ml;

  public void paint(Graphics g) {
    for (int i = 0; i < geneList.size(); i++) {
      gene = (Gene) geneList.get(i);
//			gene.repaint();
      gene.paint(g);
    }
  }

  public void addGene(Gene g) {
    g.setDrawing(this);
    GeneListener geneListener = new GeneListener();
    g.addMouseListener(geneListener);
    geneList.add(g);

//		ml = g.getMouseListeners();
//		for (int j = 0; j < ml.length; j++) {
//			this.addMouseListener(ml[j]);
//		}
    repaint();
  }
  public void removeGene(Gene g) {
//		ml = g.getMouseListeners();
//		for (int j = 0; j < ml.length; j++) {
//			this.removeMouseListener(ml[j]);
//		}
    geneList.remove(g);
    repaint();
  }
  public void removeAllGenes() {
    for (int i = 0; i < geneList.size(); i++) {
      geneList.remove(i);
//			ml = g.getMouseListeners();
//			for (int j = 0; j < ml.length; j++) {
//				this.removeMouseListener(ml[j]);
//			}
    }
    repaint();
  }


  class GeneListener implements MouseListener {
    Rectangle hitRect = new Rectangle(0, 0, 3, 3);
    public void mouseReleased(MouseEvent evt) {
    }
    public void mouseExited(MouseEvent evt) {
    }
    public void mouseEntered(MouseEvent evt) {
    }
    public void mousePressed(MouseEvent evt) {
      System.out.println("Mouse pressed");
      if(geneHit(evt)) {
        Gene gene = (Gene) evt.getSource();
        Graphics2D g = (Graphics2D) gene.getGraphics();
        Rectangle geneRect = gene.getGeneRect();
        AffineTransform transform = g.getTransform();
        g.setColor(Color.green);
        g.fill(geneRect);
        g.setTransform(transform);
      }
    }
    public void mouseClicked(MouseEvent evt) {
      System.out.println("Mouse clicked");

    }
    private boolean geneHit(MouseEvent evt) {
      hitRect.x = evt.getX() - 1;
      hitRect.y = evt.getY() - 1;
      Gene gene = (Gene)evt.getSource();
      Graphics2D g = (Graphics2D) gene.getGraphics();
      Rectangle geneRect = gene.getGeneRect();
      if (g.hit(hitRect, geneRect, false)) {
        return true;
      } else {
        return false;
      }
    }
  }

}
