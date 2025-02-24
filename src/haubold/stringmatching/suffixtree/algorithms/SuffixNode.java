/*
 * Created on Feb 13, 2003
 *
 */
package haubold.stringmatching.suffixtree.algorithms;

import java.util.*;

/**
 * @author haubold
 */
public class SuffixNode {
  private SuffixNode parent = null;
  private SuffixNode suffixLink = null;
  private TreeMap<Character,SuffixNode> children = null;
  private int leafLabel = -1;
  private int endLabel = -1;
  private int startLabel = -1;
  private int matchEnd = -1;
  private int nodeDepth = 0;
  private int id = 0;
  private float xPos = 0f;
  private float yPos = 0f;
  private boolean leaf = true;
  private boolean masked = false;
  private int stringId = 0;

  private static int nextId = 1;

  public SuffixNode() {
    id = nextId++;
  }

  public SuffixNode(SuffixNode parent, int startLabel, int endLabel) {
    id = nextId++;
    this.parent = parent;
    this.startLabel = startLabel;
    this.endLabel = endLabel;
  }

  public SuffixNode(SuffixNode parent, int startLabel) {
    id = nextId++;
    this.parent = parent;
    this.startLabel = startLabel;
  }

  public SuffixNode getChild(char c) {
    if(children != null) {
      return (SuffixNode)children.get(c);
    } else {
      return null;
    }
  }
  /**
   * Adds a child node using the first character of the edge label
   * leading to the child node as key.
   * @param c
   * @param node
   */
  public void addChild(char c, SuffixNode node) {
    if(children == null) {
      children = new TreeMap<Character,SuffixNode>();
      leaf = false;
    }
    children.put(c, node);
  }
  /**
   * Removes the node whose edge label begins with c.
   * @param c
   */
  public void removeChild(char c) {
    children.remove(c);
  }

  /**
   * @return int
   */
  public static int getNextId() {
    return nextId;
  }

  /**
   * @return TreeMap
   */
  public SuffixNode[] getChildren() {
    SuffixNode[] nodes;
    Collection collection = children.values();
    nodes = new SuffixNode[collection.size()];
    Iterator iterator = collection.iterator();
    int i = 0;
    while(iterator.hasNext()) {
      nodes[i++] = (SuffixNode)iterator.next();
    }
    return nodes;
  }

  /**
   * @return int
   */
  public int getEndLabel() {
    return endLabel;
  }

  /**
   * @return int
   */
  public int getId() {
    return id;
  }

  /**
   * @return boolean
   */
  public boolean isLeaf() {
    return leaf;
  }

  /**
   * @return int
   */
  public int getMatchEnd() {
    return matchEnd;
  }

  /**
   * @return SuffixNode
   */
  public SuffixNode getParent() {
    return parent;
  }

  /**
   * @return int
   */
  public int getStartLabel() {
    return startLabel;
  }

  /**
   * @return float
   */
  public float getXPos() {
    return xPos;
  }

  /**
   * @return float
   */
  public float getYPos() {
    return yPos;
  }

  /**
   * Sets the nextId.
   * @param nextId The nextId to set
   */
  public static void setNextId(int nextId) {
    SuffixNode.nextId = nextId;
  }

  /**
   * Sets the endLabel.
   * @param endLabel The endLabel to set
   */
  public void setEndLabel(int endLabel) {
    this.endLabel = endLabel;
  }

  /**
   * Sets the id.
   * @param id The id to set
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Sets the leaf.
   * @param leaf The leaf to set
   */
  public void setLeaf(boolean leaf) {
    this.leaf = leaf;
  }
  /**
   * Sets the matchEnd.
   * @param matchEnd The matchEnd to set
   */
  public void setMatchEnd(int matchEnd) {
    this.matchEnd = matchEnd;
  }

  /**
   * Sets the parent.
   * @param parent The parent to set
   */
  public void setParent(SuffixNode parent) {
    this.parent = parent;
  }

  /**
   * Sets the startLabel.
   * @param startLabel The startLabel to set
   */
  public void setStartLabel(int startLabel) {
    this.startLabel = startLabel;
  }

  /**
   * Sets the xPos.
   * @param xPos The xPos to set
   */
  public void setXPos(float xPos) {
    this.xPos = xPos;
  }

  /**
   * Sets the yPos.
   * @param yPos The yPos to set
   */
  public void setYPos(float yPos) {
    this.yPos = yPos;
  }

  /**
   * @return int
   */
  public int getLeafLabel() {
    return leafLabel;
  }

  /**
   * Sets the leafLabel.
   * @param leafLabel The leafLabel to set
   */
  public void setLeafLabel(int leafLabel) {
    this.leafLabel = leafLabel;
  }

  /**
   * Returns the suffixLink.
   * @return SuffixNode
   */
  public SuffixNode getSuffixLink() {
    return suffixLink;
  }

  /**
   * Sets the suffixLink.
   * @param suffixLink The suffixLink to set
   */
  public void setSuffixLink(SuffixNode suffixLink) {
    this.suffixLink = suffixLink;
  }

  /**
   * Returns the nodeDepth.
   * @return int
   */
  public int getNodeDepth() {
    return nodeDepth;
  }

  /**
   * Sets the nodeDepth.
   * @param nodeDepth The nodeDepth to set
   */
  public void setNodeDepth(int nodeDepth) {
    this.nodeDepth = nodeDepth;
  }

  /**
   * Returns the masked.
   * @return boolean
   */
  public boolean isMasked() {
    return masked;
  }

  /**
   * Sets the masked.
   * @param masked The masked to set
   */
  public void setMasked(boolean masked) {
    this.masked = masked;
  }

  /**
   * Returns the stringId.
   * @return int
   */
  public int getStringId() {
    return stringId;
  }

  /**
   * Sets the stringId.
   * @param stringId The stringId to set
   */
  public void setStringId(int stringId) {
    this.stringId = stringId;
  }

}
