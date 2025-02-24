package haubold.stringmatching.suffixtree.algorithms;

import javax.swing.*;

import haubold.resources.util.*;

import java.awt.*;
import java.awt.geom.*;

/**
 * @author haubold
 *
 */
public class DrawPanel extends PrintableJPanel {
  private static final long serialVersionUID = 1L;
  SuffixTree tree;
  float xPos;
  int maxLabel;
  int maxLevel;
  int tableHeight;
  SuffixNode root;
  JPanel panel;
  float xScale, yScale;
  float borderWidth = 20;
  int labelLength;
  float xIncrement, yIncrement;
  float arrowSize = 10;
  float arrowAngle = (float) Math.toRadians(15);
  float xPositionString, yPositionString;
  AffineTransform originalTransform, transform;
  char[] text;
  boolean first = true;
  Color green = new Color(0, 153, 0);
  Color darkBlue = new Color(0, 0, 153);
  Color lightBlue = new Color(150,150,255);
  boolean edgeLabel = true;
  boolean nodeLabel = true;
  boolean leafLabel = true;
  boolean suffixLink = false;
  QuadCurve2D.Float quad = new QuadCurve2D.Float();
  BirkhaeuserGUIComponents bgc = new BirkhaeuserGUIComponents();

  public DrawPanel() {
    transform = new AffineTransform();
  }

  public DrawPanel(SuffixTree tree) {
    this.tree = tree;
    transform = new AffineTransform();
  }

  private void positionNodes() {
    root = tree.getRoot();
    text = tree.getText();
    text[text.length - 1] = '$';
    maxLabel = 0;
    maxLevel = 0;
    if (root != null) {
      initializeTree(root);
      root.setYPos(0.5f);
      xPos = 2.0f;
      traverseTree(root);
    }
  }

  private void initializeTree(SuffixNode node) {
    node.setYPos(0.0f);
    node.setXPos(0.0f);
    if (!node.isLeaf()) {
      SuffixNode children[] = node.getChildren();
      for (int i = 0; i < children.length; i++) {
        initializeTree(children[i]);
      }
    }
  }

  private void traverseTree(SuffixNode node) {
    if (node.getParent() != null && node.getYPos() == 0.0) {
      node.setYPos(node.getParent().getYPos() + 1f);
      if (node.getYPos() > maxLevel) {
        maxLevel = (int) node.getYPos();
      }
      if (node.getEndLabel() - node.getStartLabel() > maxLabel) {
        maxLabel = node.getEndLabel() - node.getStartLabel();
      }
    }
    if (!node.isLeaf()) {
      SuffixNode children[] = node.getChildren();
      for (int i = 0; i < children.length; i++) {
        traverseTree(children[i]);
      }
      children = node.getChildren();
      //			Assign x-positions to internal nodes.
      node.setXPos(
        children[0].getXPos()
        + (children[children.length
                    - 1].getXPos()
           - children[0].getXPos())
        / 2f);
    } else {
      //			Assign x-positions to leaves.
      node.setXPos(xPos++);
    }
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
    g2.setColor(bgc.getColor1());
    g2.fillRect(0, 0, this.getWidth(), this.getHeight());
    g2.setColor(Color.BLACK);
    AffineTransform t = g2.getTransform();
    if (root != null) {
      g2 = drawTable(g2);
      xScale = (this.getWidth() - 25) / ((float) (xPos + 1));
      yScale =
        (this.getHeight() - 2 * tableHeight) / ((float) (maxLevel + 1));
      g2.translate(0, 1.5 * tableHeight);
      g2 = drawTree(root, g2);
      g2.setTransform(t);
    }
  }

  private Graphics2D drawTree(SuffixNode root, Graphics2D g2) {
    g2 = drawEdges(root, g2);
    g2 = drawNodes(root, g2);
    if (edgeLabel) {
      g2 = drawEdgeLabels(root, g2);
    }
    if (suffixLink) {
      g2 = drawSuffixLinks(root, g2);
    }
    if (nodeLabel) {
      g2 = drawNodeDepth(root, g2);
    }
    return g2;
  }

  /**
   * Draws edges for subtree of <code>node</code>.
   */
  private Graphics2D drawEdges(SuffixNode node, Graphics2D g2) {
    // Draw edges.
    if (node.getParent() != null) {
      g2.drawLine(
        (int) (node.getXPos() * xScale),
        (int) (node.getYPos() * yScale),
        (int) (node.getParent().getXPos() * xScale),
        (int) (node.getParent().getYPos() * yScale));
    }
    if (!node.isLeaf()) {
      SuffixNode[] children = node.getChildren();
      for (int i = 0; i < children.length; i++) {
        drawEdges(children[i], g2);
      }
    }
    return g2;
  }
  /**
   * Draws edge labels for subtree of <code>node</code>.
   */
  private Graphics2D drawEdgeLabels(SuffixNode node, Graphics2D g2) {
    AffineTransform aT;

    // Draw edge labels.
    if (node.getParent() != null) {
      labelLength = node.getEndLabel() - node.getStartLabel();
      //			 Find start positions of strings.
      float a = (node.getParent().getXPos() - node.getXPos()) * xScale;
      float b = (node.getYPos() - node.getParent().getYPos()) * yScale;
      float c = (float) Math.sqrt(Math.pow(a, 2.0) + Math.pow(b, 2.0));
      //			Find rotation angle of strings.
      double theta = Math.PI / 2.0 + Math.asin((double) a / (double) c);
      double sc = 1.0;
      if (labelLength * 10 > c / 2.0f) {
        xPositionString = node.getXPos() * xScale + a / 1.20f;
        yPositionString = node.getYPos() * yScale - b / 1.2f;
        AffineTransform rotate =
          AffineTransform.getRotateInstance(
            theta,
            xPositionString,
            yPositionString);
        aT = g2.getTransform();
        g2.transform(rotate);
        sc = Math.min(c / 1.2 / (double) labelLength / 10.0,1.0);
        AffineTransform scale =
          AffineTransform.getScaleInstance(sc, sc);
        g2.transform(scale);
      } else {
        xPositionString = node.getXPos() * xScale + a / 2.0f;
        yPositionString = node.getYPos() * yScale - b / 2.0f;
        AffineTransform rotate =
          AffineTransform.getRotateInstance(
            theta,
            xPositionString,
            yPositionString);
        aT = g2.getTransform();
        g2.transform(rotate);
      }
      if (sc > 0.1 && edgeLabel) {
        g2.setColor(Color.red);
        g2.drawChars(
          text,
          node.getStartLabel(),
          node.getEndLabel() - node.getStartLabel() + 1,
          (int) (xPositionString / sc),
          (int) (yPositionString / sc));
        g2.setColor(Color.black);
      }
      g2.setTransform(aT);
    }
    if (!node.isLeaf()) {
      SuffixNode[] children = node.getChildren();
      for (int i = 0; i < children.length; i++) {
        drawEdgeLabels(children[i], g2);
      }
    }
    return g2;
  }

  private Graphics2D drawNodes(SuffixNode node, Graphics2D g2) {
    if (node.isLeaf()) {
      g2.setColor(green);
      g2.fillRect(
        (int) (node.getXPos() * xScale - 2),
        (int) (node.getYPos() * yScale),
        5,
        5);
      if (leafLabel) {
        g2.drawString(
          String.valueOf(node.getLeafLabel() + 1),
          node.getXPos() * xScale - 5,
          node.getYPos() * yScale + 15);
      }
      g2.setColor(Color.black);
    } else if (node.getParent() != null) {
      g2.fillOval(
        (int) (node.getXPos() * xScale - 2),
        (int) (node.getYPos() * yScale),
        5,
        5);
    } else {
      g2.setColor(lightBlue);
      g2.fillOval(
        (int) (node.getXPos() * xScale - 2),
        (int) (node.getYPos() * yScale),
        5,
        5);
      g2.setColor(Color.black);
    }
    if (!node.isLeaf()) {
      SuffixNode[] children = node.getChildren();
      for (int i = 0; i < children.length; i++) {
        drawNodes(children[i], g2);
      }
    }
    return g2;
  }

  private Graphics2D drawNodeDepth(SuffixNode node, Graphics2D g2) {
    if (node.getParent() == null) {
      g2.setColor(lightBlue);
      g2.drawString(
        String.valueOf(node.getNodeDepth()),
        node.getXPos() * xScale - 4,
        node.getYPos() * yScale - 2);
      g2.setColor(Color.black);
    } else if (!node.isLeaf()) {
      g2.drawString(
        String.valueOf(node.getNodeDepth()),
        node.getXPos() * xScale + 5,
        node.getYPos() * yScale + 5);
    }
    if(!node.isLeaf()) {
      SuffixNode[] children = node.getChildren();
      for (int i = 0; i < children.length; i++) {
        drawNodeDepth(children[i], g2);
      }
    }
    return g2;
  }

  private Graphics2D drawSuffixLinks(SuffixNode node, Graphics2D g2) {
    if (node.getSuffixLink() != null && node.getParent() != null) {
      float cx, cy, sx, sy, nx, ny;
      nx = node.getXPos() * xScale;
      ny = node.getYPos() * yScale;
      sx = node.getSuffixLink().getXPos() * xScale;
      sy = node.getSuffixLink().getYPos() * yScale;
      if (node.getYPos() != node.getSuffixLink().getYPos()) {
        cx = node.getSuffixLink().getXPos() * xScale;
        cy = node.getYPos() * yScale;
      } else {
        cx =
          Math.min(node.getXPos(), node.getSuffixLink().getXPos())
          + (Math
             .max(node.getXPos(), node.getSuffixLink().getXPos())
             - Math.min(
               node.getXPos(),
               node.getSuffixLink().getXPos()))
          / 2.0f;
        cx *= xScale;
        cy = (node.getYPos() + 0.5f) * yScale;
      }
      if(node.getXPos() - node.getSuffixLink().getXPos() < 0.1) {
        cx += + 0.5f * xScale;
      }
      quad.setCurve(nx, ny, cx, cy, sx, sy);
      g2.setColor(darkBlue);
      g2.draw(quad);
      g2.drawLine(
        (int) (node.getSuffixLink().getXPos() * xScale),
        (int) (node.getSuffixLink().getYPos() * yScale),
        (int) (node.getSuffixLink().getXPos() * xScale),
        (int) (node.getSuffixLink().getYPos() * yScale + 5));
      // calculate points for arrowhead
      float angle =
        (float) Math.atan2(sy - cy, sx - cx) + (float) Math.PI;
      float x3 = (int) (sx + Math.cos(angle - arrowAngle) * arrowSize);
      float y3 = (int) (sy + Math.sin(angle - arrowAngle) * arrowSize);
      float x4 = (int) (sx + Math.cos(angle + arrowAngle) * arrowSize);
      float y4 = (int) (sy + Math.sin(angle + arrowAngle) * arrowSize);
      // draw arrowhead
      g2.drawLine((int) sx, (int) sy, (int) x3, (int) y3);
      g2.drawLine((int) sx, (int) sy, (int) x4, (int) y4);
      g2.drawLine((int) x3, (int) y3, (int) x4, (int) y4);
      g2.setColor(Color.black);
    }
    if (!node.isLeaf()) {
      SuffixNode[] children = node.getChildren();
      for (int i = 0; i < children.length; i++) {
        drawSuffixLinks(children[i], g2);
      }
    }
    return g2;
  }
  /**
   * Generate the table depiction of the text.
   */
  private Graphics2D drawTable(Graphics2D g2) {
    int cellWidth = 17;
    int cellHeight = 17;
    int tableY = 6;
    int tableX = cellWidth;
    int tableWidth = tableX + cellWidth * (text.length);
    tableHeight = tableY + 2 * cellHeight;
    g2.drawLine(tableX, tableY, tableWidth, tableY);
    g2.drawLine(
      tableX,
      tableY + cellHeight,
      tableWidth,
      tableY + cellHeight);
    g2.drawLine(tableX, tableHeight, tableWidth, tableHeight);
    int i;
    for (i = 0; i < text.length; i++) {
      g2.setColor(green);
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
    return g2;
  }

  /**
   * Compute the length of the edge label for each node.
   */
  @SuppressWarnings("unused")
  private int computeNodeDepth(SuffixNode node) {
    int nd = 0;
    while (node.getParent() != null) {
      nd += node.getEndLabel() - node.getStartLabel() + 1;
      node = node.getParent();
    }
    return nd;
  }

  /**
   * Returns the tree.
   * @return SuffixTree
   */
  public SuffixTree getTree() {
    return tree;
  }

  /**
   * Sets the tree.
   * @param tree The tree to set
   */
  public void setTree(SuffixTree tree) {
    this.tree = tree;
    if(tree != null) {
      positionNodes();
    } else {
      root = null;
    }
    repaint();
  }

  /**
   * Returns the edgeLabel.
   * @return boolean
   */
  public boolean isEdgeLabel() {
    return edgeLabel;
  }

  /**
   * Returns the leafLabel.
   * @return boolean
   */
  public boolean isLeafLabel() {
    return leafLabel;
  }

  /**
   * Returns the nodeLabel.
   * @return boolean
   */
  public boolean isNodeLabel() {
    return nodeLabel;
  }

  /**
   * Sets the edgeLabel.
   * @param edgeLabel The edgeLabel to set
   */
  public void setEdgeLabel(boolean edgeLabel) {
    this.edgeLabel = edgeLabel;
  }

  /**
   * Sets the leafLabel.
   * @param leafLabel The leafLabel to set
   */
  public void setLeafLabel(boolean leafLabel) {
    this.leafLabel = leafLabel;
  }

  /**
   * Sets the nodeLabel.
   * @param nodeLabel The nodeLabel to set
   */
  public void setNodeLabel(boolean nodeLabel) {
    this.nodeLabel = nodeLabel;
  }
  /**
   * Returns the arrowAngle.
   * @return float
   */
  public float getArrowAngle() {
    return arrowAngle;
  }

  /**
   * Returns the arrowSize.
   * @return float
   */
  public float getArrowSize() {
    return arrowSize;
  }

  /**
   * Sets the arrowAngle.
   * @param arrowAngle The arrowAngle to set
   */
  public void setArrowAngle(float arrowAngle) {
    this.arrowAngle = arrowAngle;
  }

  /**
   * Sets the arrowSize.
   * @param arrowSize The arrowSize to set
   */
  public void setArrowSize(float arrowSize) {
    this.arrowSize = arrowSize;
  }

  /**
   * Returns the suffixLink.
   * @return boolean
   */
  public boolean isSuffixLink() {
    return suffixLink;
  }

  /**
   * Sets the suffixLink.
   * @param suffixLink The suffixLink to set
   */
  public void setSuffixLink(boolean suffixLink) {
    this.suffixLink = suffixLink;
  }

}
