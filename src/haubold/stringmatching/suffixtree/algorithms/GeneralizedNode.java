package haubold.stringmatching.suffixtree.algorithms;

/**
 * @author Bernhard Haubold
 * Date: Apr 11, 2003; time: 2:00:55 PM.
 *
 * Description: This node can be used by <code>GeneralizedSuffixTree</code>.
 */

import java.util.*;

public class GeneralizedNode {
  private GeneralizedNode parent = null;
  private GeneralizedNode suffixLink = null;
  private TreeMap<Character,GeneralizedNode> children = null;
  private int leafLabel = -1;
  private int endLabel = -1;
  private int startLabel = -1;
  private int matchEnd = -1;
  private int nodeDepth = 0;
  private int id = 0;
  private boolean leaf = true;
  private int stringId = 0;



  private static int nextId = 1;

  public GeneralizedNode() {
    id = nextId++;
  }

  public GeneralizedNode(GeneralizedNode parent, int startLabel, int endLabel) {
    id = nextId++;
    this.parent = parent;
    this.startLabel = startLabel;
    this.endLabel = endLabel;
  }

  public GeneralizedNode(GeneralizedNode parent, int startLabel) {
    id = nextId++;
    this.parent = parent;
    this.startLabel = startLabel;
  }

  public GeneralizedNode getChild(char c) {
    if (children != null) {
      return (GeneralizedNode) children.get(c);
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
  public void addChild(char c, GeneralizedNode node) {
    if (children == null) {
      children = new TreeMap<Character,GeneralizedNode>();
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
  public GeneralizedNode[] getChildren() {
    GeneralizedNode[] nodes;
    Collection collection = children.values();
    nodes = new GeneralizedNode[collection.size()];
    Iterator iterator = collection.iterator();
    int i = 0;
    while (iterator.hasNext()) {
      nodes[i++] = (GeneralizedNode) iterator.next();
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
   * @return LeanNode
   */
  public GeneralizedNode getParent() {
    return parent;
  }

  /**
   * @return int
   */
  public int getStartLabel() {
    return startLabel;
  }

  /**
   * Sets the endLabel.
   * @param endLabel The endLabel to set
   */
  public void setEndLabel(int endLabel) {
    this.endLabel = endLabel;
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
  public void setParent(GeneralizedNode parent) {
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
   * @return LeanNode
   */
  public GeneralizedNode getSuffixLink() {
    return suffixLink;
  }

  /**
   * Sets the suffixLink.
   * @param suffixLink The suffixLink to set
   */
  public void setSuffixLink(GeneralizedNode suffixLink) {
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
