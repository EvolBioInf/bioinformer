package haubold.wrightfisher;

import java.awt.*;
import java.awt.event.*;

import haubold.resources.util.*;

/**
 * Description:
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 *         Date: Sep 11, 2005; time: 2:35:14 PM.
 */
public class WrightFisherPanel extends PrintableJPanel implements
  MouseListener, MouseMotionListener {
  private static final long serialVersionUID = 1L;

  Node[][] population, untangledPop;

  Arrow arrow;
  Rectangle hitRect, enteredRect;
  double scale, translateX, translateY;
  private boolean tangled, showMrca = false, showAncestral = false;
  private boolean oneParent = false;
  private boolean forward, backward;
  BirkhaeuserGUIComponents bgc;
  Graphics2D graphicsObject;
  TwoParent twoParent;

  public WrightFisherPanel() {
    bgc = new BirkhaeuserGUIComponents();
    arrow = new Arrow();
    this.setToolTipText("Click dots in top or bottom row");
    hitRect = new Rectangle(0, 0, 3, 3);
    enteredRect = new Rectangle(0, 0, 3, 3);

    twoParent = new TwoParent();
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    graphicsObject = g2;
    g2.setColor(bgc.getColor1());
    g2.fillRect(0, 0, getWidth(), getHeight());

    if (population != null && population.length > 0
        && population[0].length > 0)
      paintPanel(g2);
  }

  private void paintPanel(Graphics2D g2) {
    double xScale, yScale, w, h;

    if (population.length > 4)
      w = ((population[0].length + 10) * 10.0);
    else
      w = ((population[0].length + 6) * 10.0);
    h = ((population.length + 2) * 12.0);
    xScale = (double) this.getWidth() / w;
    yScale = (double) this.getHeight() / h;
    scale = Math.min(xScale, yScale);
    translateX = ((double) this.getWidth() - w * scale) / 2.0;
    translateY = ((double) this.getHeight() - h * scale) / 2.0;

    g2.translate(translateX, translateY);
    g2.scale(scale, scale);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

    if (oneParent)
      paintOneParent(g2);
    else
      twoParent.paintTwoParent(g2,this.getWidth());
    g2.setColor(Color.white);
    g2.setColor(bgc.getColor3());
    if (population.length > 4) {
      g2.drawString("present", (population[0].length + 5) * 10,
                    (population.length + 1) * 12 - 7);
      g2.drawString("past", (population[0].length + 5) * 10, 2 * 12 - 7);
      arrow.drawArrow(g2, (population[0].length + 6) * 10,
                      (population.length - 1) * 12,
                      (population[0].length + 6) * 10, 3 * 12 - 7);
    }
    // draw box around bottom row
//		g2.setColor(Color.blue);
//		g2.drawRect(30,(population.length)*12-4,(population[0].length+1)*10+3,9);
  }

  private void paintOneParent(Graphics2D g2) {
    int i, j, k, offset;
    Node parent, child;
    Shape parentShape, childShape;

    i = 0;
    g2.setColor(bgc.getColor3());
    g2.drawString(String.valueOf(i + 1), 10, (i + 2) * 12 - 7);
    for (i = 1; i < population.length; i++) {
      g2.setColor(bgc.getColor3());
      g2.drawString(String.valueOf(i + 1), 10, (i + 2) * 12 - 7);
      g2.setColor(Color.black);
      for (j = 0; j < population[i].length; j++) {
        parent = population[i][j].getAncestor1();
        child = population[i][j];
        if(forward) {
          g2.setColor(child.getColorForward());
        } else {
          g2.setColor(child.getColorBackward());
        }
        if (tangled) {
          parentShape = parent.getShape();
          childShape = child.getShape();
        } else {
          parentShape = parent.getUntangledShape();
          childShape = child.getUntangledShape();
        }
        g2.drawLine(parentShape.getBounds().x + 2, parentShape
                    .getBounds().y + 2, childShape.getBounds().x + 2,
                    childShape.getBounds().y + 2);
      }

    }
    g2.setColor(Color.black);
    k = population.length - 1;
    for(i=0; i<population[0].length; i++) {
      if(population[0][i].isMouseOver())
        g2.setColor(Color.green);
      else
        g2.setColor(Color.lightGray);
      g2.drawOval(population[0][i].getShape().getBounds().x-1,
                  population[0][i].getShape().getBounds().y-1,
                  6,6);
    }
    for(i=0; i<population[0].length; i++) {
      if(population[k][i].isMouseOver())
        g2.setColor(Color.blue);
      else
        g2.setColor(Color.lightGray);
      g2.drawOval(population[k][i].getShape().getBounds().x-1,
                  population[k][i].getShape().getBounds().y-1,
                  6,6);
      g2.setColor(Color.black);
      if(String.valueOf(population[0][i].getId()).length() == 1)
        offset = 0;
      else
        offset = 2;
      if(tangled) {
        g2.translate(population[k][i].getShape().getBounds().x-offset,
                     population[k][i].getShape().getBounds().y+12);
        g2.scale(0.5,0.5);
        g2.drawString(String.valueOf(population[0][i].getId()),0,0);
        g2.scale(2,2);
        g2.translate(-(population[k][i].getShape().getBounds().x-offset),
                     -(population[k][i].getShape().getBounds().y+12));
      }
      else {
        g2.translate(population[k][i].getUntangledShape().getBounds().x-offset,
                     population[k][i].getShape().getBounds().y+12);
        g2.scale(0.5,0.5);
        g2.drawString(String.valueOf(population[0][i].getId()),0,0);
        g2.scale(2,2);
        g2.translate(-(population[k][i].getUntangledShape().getBounds().x-offset),
                     -(population[k][i].getShape().getBounds().y+12));

      }

    }

    for (i = 0; i < population.length; i++) {
      for (j = 0; j < population[0].length; j++) {
        if(forward)
          g2.setColor(population[i][j].getColorForward());
        else if(population[i][j].isGeneMrca())
          g2.setColor(Color.red);
        else
          g2.setColor(population[i][j].getColorBackward());
        if (tangled)
          g2.fill(population[i][j].getShape());
        else
          g2.fill(population[i][j].getUntangledShape());
      }
    }
    g2.setColor(Color.black);
  }

  /**
   * @param population
   *            The population to set.
   */
  public void setPopulation(Node[][] population) {
    this.population = population;
    twoParent.setPop(population);
  }

  /**
   * @param tangled
   *            The tangled to set.
   */
  public void setTangled(boolean tangled) {
    this.tangled = tangled;
    twoParent.setTangled(tangled);
  }

  /**
   * @return Returns the showMrca.
   */
  public boolean isShowMrca() {
    return showMrca;
  }

  /**
   * @param showMrca
   *            The showMrca to set.
   */
  public void setShowMrca(boolean showMrca) {
    this.showMrca = showMrca;
  }

  /**
   * @return Returns the population.
   */
  public Node[][] getPopulation() {
    return population;
  }

  /**
   * @return Returns the showAncestral.
   */
  public boolean isShowAncestral() {
    return showAncestral;
  }

  /**
   * @param showAncestral
   *            The showAncestral to set.
   */
  public void setShowAncestral(boolean showAncestral) {
    this.showAncestral = showAncestral;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
   */
  public void mouseClicked(MouseEvent e) {
    int i;
    int popSizeMinusOne;

    popSizeMinusOne = population.length - 1;
    hitRect.x = (int) ((double) (e.getX() - translateX) / scale);
    hitRect.y = (int) ((double) (e.getY() - translateY) / scale);
    for (i = 0; i < population[0].length; i++) {
      if ((tangled && population[0][i].getShape().intersects(hitRect))
          || (!tangled && population[0][i].getUntangledShape()
              .intersects(hitRect))) {
        forward = true;
        backward = false;
        if (population[0][i].isClicked()) {
          population[0][i].setClicked(false);
          population[0][i].setColorForward(Color.black);
        } else {
          population[0][i].setClicked(true);
          population[0][i].setColorForward(Color.green);
        }
      }
    }
    for (i = 0; i < population[0].length; i++) {
      if ((tangled && population[popSizeMinusOne][i].getShape()
           .intersects(hitRect))
          || (!tangled && population[popSizeMinusOne][i]
              .getUntangledShape().intersects(hitRect))) {
        backward = true;
        forward = false;
        if (population[popSizeMinusOne][i].isClicked()) {
          population[popSizeMinusOne][i].setClicked(false);
          population[popSizeMinusOne][i].setColorForward(Color.black);
        } else {
          population[popSizeMinusOne][i].setClicked(true);
          population[popSizeMinusOne][i].setColorForward(Color.blue);
        }
      }
    }
    if (forward) {
      if(oneParent)
        markForward(population);
      else
        twoParent.markForward(population);
      repaint();
    } else if (backward) {
      if(oneParent)
        markBackward(population);
      else
        twoParent.markBackward(population);
      repaint();
    }
  }

  void markForward(Node[][] pop) {
    int i;

    for (i = 0; i < pop[0].length; i++) {
      if(pop[pop.length-1][i].isClicked()) {
        unmarkBackwardRecursive(pop[pop.length-1][i]);
        pop[pop.length-1][i].setClicked(false);
      }
      if (pop[0][i].isClicked())
        markForwardRecursive(pop[0][i]);
      else
        unmarkForwardRecursive(pop[0][i]);
    }
  }

  void markForwardRecursive(Node node) {
    int i;

    node.setColorForward(Color.green);
    for (i = 0; i < node.getOffspring().size(); i++)
      markForwardRecursive((Node) node.getOffspring().get(i));
  }

  void unmarkForwardRecursive(Node node) {
    int i;

    node.setColorForward(Color.black);
    for (i = 0; i < node.getOffspring().size(); i++)
      unmarkForwardRecursive((Node) node.getOffspring().get(i));
  }

  private void markBackwardRecursive(Node node) {
    if(node != null) {
      node.setAncestral(true);
      node.setColorBackward(Color.blue);
      markBackwardRecursive(node.getAncestor1());
    }
  }

  private void unmarkBackwardRecursive(Node node) {
    if(node != null) {
      node.setGeneMrca(false);
      node.setAncestral(false);
      node.setColorBackward(Color.black);
      unmarkBackwardRecursive(node.getAncestor1());
    }
  }

  void markBackward(Node[][] pop) {
    int i, j, indAnc = 0;
    int k, numAnc;

    k = pop.length - 1;
    for(i=0; i<pop.length; i++)
      for(j=0; j<pop[0].length; j++)
        pop[i][j].setAncestral(false);
    for (i = 0; i < pop[0].length; i++) {
      unmarkBackwardRecursive(pop[k][i]);
      if(pop[0][i].isClicked()) {
        unmarkForwardRecursive(pop[0][i]);
        pop[0][i].setClicked(false);
      }
    }
    for (i = 0; i < pop[0].length; i++) {
      if (pop[k][i].isClicked())
        markBackwardRecursive(pop[k][i]);
      else
        pop[k][i].setAncestral(false);
    }
    for(i=k; i>=0; i--) {
      numAnc = 0;
      for(j=0; j<pop[0].length; j++) {
        if(pop[i][j].isAncestral()) {
          numAnc++;
          indAnc = j;
        }
      }
      if(numAnc == 1) {
        pop[i][indAnc].setGeneMrca(true);
        return;
      }
    }


  }

  public void mouseMoved(MouseEvent e) {
    int i;
    int numGenMinusOne, popSize;
    boolean change;

    if (population != null) {
      popSize = population[0].length;
      numGenMinusOne = population.length - 1;
    } else {
      popSize = 0;
      numGenMinusOne = -1;
    }
    change = false;
    hitRect.x = (int) ((double) (e.getX() - translateX) / scale);
    hitRect.y = (int) ((double) (e.getY() - translateY) / scale);
    for (i = 0; i < popSize; i++) {
      if (population[0][i].getShape().intersects(hitRect)) {
        if(!population[0][i].isMouseOver()) {
          change = true;
          population[0][i].setMouseOver(true);
        }
      } else {
        if(population[0][i].isMouseOver()) {
          change = true;
          population[0][i].setMouseOver(false);
        }
      }
    }
    for (i = 0; i < popSize; i++) {
      if (population[numGenMinusOne][i].getShape().intersects(hitRect)) {
        if(!population[numGenMinusOne][i].isMouseOver()) {
          change = true;
          population[numGenMinusOne][i].setMouseOver(true);
        }
      } else {
        if(population[numGenMinusOne][i].isMouseOver()) {
          change = true;
          population[numGenMinusOne][i].setMouseOver(false);
        }
      }
    }
    if (change) {
      repaint();
    }
  }

  public boolean isOneParent() {
    return oneParent;
  }

  public void setOneParent(boolean oneParent) {
    int i,j;

    this.oneParent = oneParent;
    if(population != null) {
      for(i=0; i<population.length; i++) {
        for(j=0; j<population[0].length; j++) {
          population[i][j].setColorBackward(Color.black);
          population[i][j].setColorForward(Color.black);
          population[i][j].setClicked(false);
          population[i][j].setGeneMrca(false);
        }
      }
      repaint();
    }
  }

  public Node[][] getUntangledPop() {
    return untangledPop;
  }

  public void setUntangledPop(Node[][] untangledPop) {
    this.untangledPop = untangledPop;
  }

  public void mousePressed(MouseEvent e) {}
  public void mouseReleased(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  public void mouseDragged(MouseEvent e) {}
}
