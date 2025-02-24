package haubold.phylogeny.util;

/**
 * @author Bernhard Haubold
 * Date: Jun 7, 2003; time: 2:17:02 PM.
 *
 * Description:
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;

import haubold.resources.util.*;

public class TreePanel extends JPanel {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  Node[] tree = null;
  BirkhaeuserGUIComponents bgc = new BirkhaeuserGUIComponents();
  double treeXDim, treeYDim;
  double border = 0.1;
  int maximumLabelLength = 0;
  double defaultSize = 200.0;
  double prescale;
  DecimalFormat decimalFormat = new DecimalFormat("0.000");
  boolean branchLengths = false;
  boolean leafLabels = true;
  Color backgroundColor = bgc.getColor4();
  Color taxonColor = bgc.getColor3();
  Color distanceColor = bgc.getColor5();

  public TreePanel() {
  }

  public TreePanel(Node[] tree) {
    this.tree = tree;
  }

  public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    double xScale, yScale, scale;
    double tx, ty;
    AffineTransform transform;
    g2.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(backgroundColor);
    g2.fillRect(0, 0, getWidth(), getHeight());
    g2.setColor(Color.black);
    if (tree != null) {
      // Compute scale factor
      xScale =
        ((double) (getWidth() - (double) getWidth() * border))
        / (treeXDim + maximumLabelLength * 6.0);
      yScale =
        ((double) getHeight() - (double) getHeight() * border)
        / treeYDim;
      scale = Math.min(xScale, yScale);
      // Compute translation factor to ensure that tree is always centered
      tx =
        (getWidth() - (treeXDim + maximumLabelLength * 6.0) * scale)
        / 2.0;
      ty = (getHeight() - treeYDim * scale) / 2.0;
      transform = g2.getTransform();
      g2.translate(tx, ty);
      g2.scale(scale, scale);
      drawTree(g2);
      g2.setTransform(transform);
    }
  }

  public void drawPDF(Graphics2D g2) {
    double xScale, yScale, scale;
    double tx, ty;
    AffineTransform transform;
    int width = 400;
    int height = 150;
    if (tree != null) {
      // Compute scale factor
      xScale =
        ((double) (width - (double) width * border))
        / (treeXDim + maximumLabelLength * 6.0);
      yScale = ((double) height - (double) height * border) / treeYDim;
      scale = Math.min(xScale, yScale);
      // Compute translation factor to ensure that tree is always centered
      tx = (width - (treeXDim + maximumLabelLength * 6.0) * scale) / 2.0;
      ty = (height - treeYDim * scale) / 2.0;
      transform = g2.getTransform();
      g2.translate(tx, ty);
      g2.scale(scale, scale);
      drawTree(g2);
      g2.setTransform(transform);
    }
  }

  private void drawTree(Graphics2D g2) {
    int i;
    for (i = 0; i < tree.length; i++) {
      if (tree[i].getParent() != null) {
        g2.drawLine(
          (int) treeXDim - (int) tree[i].getYPosition(),
          (int) tree[i].getXPosition(),
          (int) treeXDim - (int) tree[i].getParent().getYPosition(),
          (int) tree[i].getParent().getXPosition());
        if (branchLengths) {
          double bl =
            (tree[i].getParent().getNodeHeight()
             - tree[i].getNodeHeight());
          double sx =
            treeXDim
            - (tree[i].getYPosition()
               + (tree[i].getParent().getYPosition()
                  - tree[i].getYPosition())
               / 2.0);
          double sy =
            tree[i].getXPosition()
            + (tree[i].getParent().getXPosition()
               - tree[i].getXPosition())
            / 2.0;
          g2.setColor(distanceColor);
          g2.drawString(decimalFormat.format(bl), (int) sx, (int) sy);
          g2.setColor(Color.black);
        }
      }
      if (tree[i].isLeaf()) {
        if (leafLabels) {
          g2.setColor(taxonColor);
          g2.drawString(
            ((LeafNode) tree[i]).getLabel(),
            (int) treeXDim - (int) tree[i].getYPosition() + 2,
            (int) tree[i].getXPosition() + 5);
          g2.setColor(Color.BLACK);
        }
      }
    }
    // Add scale bar
    double scaleLength = treeXDim / 10.0;
    g2.drawLine(0, 5, (int) scaleLength, 5);
    g2.drawString(
      decimalFormat.format(tree[tree.length - 1].getNodeHeight() / 10.0),
      0,
      4);
  }

  /**
   * Returns the tree.
   * @return Node[]
   */
  public Node[] getTree() {
    return tree;
  }

  /**
   * Sets the tree.
   * @param tree The tree to set
   */
  public void setTree(Node[] tree) {
    this.tree = tree;
    if (tree != null) {
      treeXDim = computeXDim(tree);
      treeYDim = computeYDim(tree);
      prescaleTree(tree);
      maximumLabelLength = computeMaximumLabelLength(tree);
    }
    repaint();
  }

  private double computeXDim(Node[] tree) {
    double xDim = Double.MIN_VALUE;
    for (int i = 0; i < tree.length; i++) {
      if (xDim < tree[i].getYPosition()) {
        xDim = tree[i].getYPosition();
      }
    }
    return xDim;
  }

  private double computeYDim(Node[] tree) {
    double yDim = Double.MIN_VALUE;
    for (int i = 0; i < tree.length; i++) {
      if (yDim < tree[i].getXPosition()) {
        yDim = tree[i].getXPosition();
      }
    }
    return yDim;
  }

  private void prescaleTree(Node[] tree) {
    prescale = defaultSize / Math.min(treeXDim, treeYDim);
    for (int i = 0; i < tree.length; i++) {
      tree[i].setXPosition(tree[i].getXPosition() * prescale);
      tree[i].setYPosition(tree[i].getYPosition() * prescale);
    }
    treeXDim *= prescale;
    treeYDim *= prescale;
  }

  private int computeMaximumLabelLength(Node[] tree) {
    int maximumLabelLength = Integer.MIN_VALUE;

    for (int i = 0; i < tree.length; i++) {
      if (tree[i].isLeaf()) {
        if (maximumLabelLength
            < ((LeafNode) tree[i]).getLabel().length()) {
          maximumLabelLength =
            ((LeafNode) tree[i]).getLabel().length();
        }
      }
    }

    return maximumLabelLength;
  }

  /**
   * Returns the branchLengths.
   * @return boolean
   */
  public boolean isBranchLengths() {
    return branchLengths;
  }

  /**
   * Sets the branchLengths.
   * @param branchLengths The branchLengths to set
   */
  public void setBranchLengths(boolean branchLengths) {
    this.branchLengths = branchLengths;
  }

  /**
   * Returns the color.
   * @return Color
   */
  public Color getBackgroundColor() {
    return backgroundColor;
  }

  /**
   * Sets the color.
   * @param color The color to set
   */
  public void setBackgroundColor(Color backgroundColor) {
    this.backgroundColor = backgroundColor;
    System.out.println("Set background color");
    this.repaint();
  }

  /**
   * Returns the taxonColor.
   * @return Color
   */
  public Color getTaxonColor() {
    return taxonColor;
  }

  /**
   * Sets the taxonColor.
   * @param taxonColor The taxonColor to set
   */
  public void setTaxonColor(Color taxonColor) {
    this.taxonColor = taxonColor;
  }

  /**
   * Returns the distanceColor.
   * @return Color
   */
  public Color getDistanceColor() {
    return distanceColor;
  }

  /**
   * Sets the distanceColor.
   * @param distanceColor The distanceColor to set
   */
  public void setDistanceColor(Color distanceColor) {
    this.distanceColor = distanceColor;
  }

  /**
   * Returns the leafLabels.
   * @return boolean
   */
  public boolean isLeafLabels() {
    return leafLabels;
  }

  /**
   * Sets the leafLabels.
   * @param leafLabels The leafLabels to set
   */
  public void setLeafLabels(boolean leafLabels) {
    this.leafLabels = leafLabels;
  }

}
