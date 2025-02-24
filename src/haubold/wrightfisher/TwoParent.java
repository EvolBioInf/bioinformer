package haubold.wrightfisher;

import haubold.resources.util.*;

import java.awt.*;


/**
 * Description:
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Oct 6, 2005; time: 12:19:55 PM.
 */
public class TwoParent {
  private BirkhaeuserGUIComponents bgc;
  private Node[][] pop;
  private ColorUtil colorUtil;
  private Color[][] color;
  private boolean tangled;

  public TwoParent() {
    bgc = new BirkhaeuserGUIComponents();
    colorUtil = new ColorUtil();
  }

  public void paintTwoParent(Graphics2D g2, int width) {
    int i, j, k;
    Node parent, child;

    i = 0;
    // draw circles around top & bottom row
    k = pop.length - 1;
    for(i=0; i<pop[0].length; i++) {
      if(pop[0][i].isMouseOver())
        g2.setColor(color[1	][i]);
      else
        g2.setColor(Color.lightGray);
      g2.drawOval(pop[0][i].getShape().getBounds().x-1,
                  pop[0][i].getShape().getBounds().y-1,
                  6,6);
    }
    for(i=0; i<pop[0].length; i++) {
      if(pop[k][i].isMouseOver())
        g2.setColor(color[0][i]);
      else
        g2.setColor(Color.lightGray);
      g2.drawOval(pop[k][i].getShape().getBounds().x-1,
                  pop[k][i].getShape().getBounds().y-1,
                  6,6);
    }

    for(i=0; i<pop.length; i++) {
      for(j=0; j<pop[0].length; j++) {
        if(pop[i][j].isMarkedForward()) {
          g2.setColor(pop[i][j].getColorForward());
          parent  = pop[i][j];
          for(k=0; k<pop[i][j].getChildren().size(); k++) {
            child = (Node)pop[i][j].getChildren().get(k);
            if(tangled)
              g2.drawLine(parent.getShape().getBounds().x+2,
                          parent.getShape().getBounds().y+2,
                          child.getShape().getBounds().x+2,
                          child.getShape().getBounds().y+2);
            else
              g2.drawLine(parent.getUntangledShape().getBounds().x+2,
                          parent.getUntangledShape().getBounds().y+2,
                          child.getUntangledShape().getBounds().x+2,
                          child.getUntangledShape().getBounds().y+2);
          }
        } else if(pop[i][j].isMarkedBackward() && i > 0) {
          g2.setColor(pop[i][j].getColorBackward());
          child = pop[i][j];
          if(tangled) {
            g2.drawLine(child.getShape().getBounds().x+2,
                        child.getShape().getBounds().y+2,
                        pop[i][j].getAncestor1().getShape().getBounds().x+2,
                        pop[i][j].getAncestor1().getShape().getBounds().y+2);
            g2.drawLine(child.getShape().getBounds().x+2,
                        child.getShape().getBounds().y+2,
                        pop[i][j].getAncestor2().getShape().getBounds().x+2,
                        pop[i][j].getAncestor2().getShape().getBounds().y+2);
          } else {
            g2.drawLine(child.getUntangledShape().getBounds().x+2,
                        child.getUntangledShape().getBounds().y+2,
                        pop[i][j].getAncestor1().getUntangledShape().getBounds().x+2,
                        pop[i][j].getAncestor1().getUntangledShape().getBounds().y+2);
            g2.drawLine(child.getUntangledShape().getBounds().x+2,
                        child.getShape().getBounds().y+2,
                        pop[i][j].getAncestor2().getUntangledShape().getBounds().x+2,
                        pop[i][j].getAncestor2().getUntangledShape().getBounds().y+2);
          }
          g2.setColor(Color.black);
        }

      }
    }
    g2.setColor(bgc.getColor3());
    i = 0 ;
    for (i = 0; i < pop.length; i++) {
      g2.setColor(bgc.getColor3());
      g2.drawString(String.valueOf(i + 1), 10, (i + 2) * 12 - 7);
      g2.setColor(Color.black);
      for (j = 0; j < pop[i].length; j++) {
        if(pop[i][j].getDescendants().size() == pop[0].length)
          g2.setColor(Color.red);
        else if(pop[i][j].getDescendants().size() == 0)
          g2.setColor(Color.blue);
        else
          g2.setColor(Color.black);
        if(tangled)
          g2.fill(pop[i][j].getShape());
        else
          g2.fill(pop[i][j].getUntangledShape());
      }
    }
  }

  public void markBackward(Node[][] pop) {
    int i, j, k;

    k = pop.length - 1;
    for(i=0; i<pop.length; i++)
      for(j=0; j<pop[0].length; j++)
        pop[i][j].setMarkedBackward(false);
    for(i=0; i<pop[0].length; i++) {
      if(pop[k][i].isClicked()) {
        markBackwardRecursive(pop[k][i],color[0][i]);
      }
    }
  }

  private void markBackwardRecursive(Node node, Color color) {
    if(node != null) {
      node.setMarkedBackward(true);
      node.setColorBackward(color);
      markBackwardRecursive(node.getAncestor1(), color);
      markBackwardRecursive(node.getAncestor2(), color);
    }
  }

  public void markForward(Node[][] pop) {
    int i, j;

    for(i=0; i<pop.length; i++)
      for(j=0; j<pop[0].length; j++)
        pop[i][j].setMarkedForward(false);

    for(i=0; i<pop[0].length; i++) {
      if(pop[0][i].isClicked()) {
        markForwardRecursive(pop[0][i],color[1][i]);
      }
    }
  }


  private void markForwardRecursive(Node node, Color color) {
    int i;

    if(node != null) {
      node.setMarkedForward(true);
      node.setColorForward(color);
      for(i=0; i<node.getChildren().size(); i++) {
        markForwardRecursive((Node)node.getChildren().get(i),color);
      }
    }
  }
  public Node[][] getPop() {
    return pop;
  }
  public void setPop(Node[][] pop) {
    int i, j;

    this.pop = pop;

    if(pop != null) {
      color = new Color[2][pop[0].length];
      for(i=0; i<2; i++)
        for(j=0; j<pop[0].length; j++)
          color[i][j] = colorUtil.getRandomColor();
    }
  }
  public boolean isTangled() {
    return tangled;
  }
  public void setTangled(boolean tangled) {
    this.tangled = tangled;
  }
}
