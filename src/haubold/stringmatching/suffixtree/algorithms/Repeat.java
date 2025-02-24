package haubold.stringmatching.suffixtree.algorithms;

/**
 * @author haubold
 * Date: Feb 25, 2003; time: 11:04:59 AM.
 *
 * Description: Transport class for repeats.
 */

import java.util.*;
public class Repeat extends ArrayList<Integer> {
  private static final long serialVersionUID = 1L;
  private int length;
  private SuffixNode node;
  private int sourceString;

  public Repeat() {
  }

  public Repeat(int length, SuffixNode node) {
    this.length = length;
    this.node = node;
  }

  /**
   * Returns the length.
   * @return int
   */
  public int getLength() {
    return length;
  }

  /**
   * Sets the length.
   * @param length The length to set
   */
  public void setLength(int length) {
    this.length = length;
  }

  /**
   * Returns the node.
   * @return SuffixNode
   */
  public SuffixNode getNode() {
    return node;
  }

  /**
   * Sets the node.
   * @param node The node to set
   */
  public void setNode(SuffixNode node) {
    this.node = node;
  }

  /**
   * Returns the sourceString.
   * @return int
   */
  public int getSourceString() {
    return sourceString;
  }

  /**
   * Sets the sourceString.
   * @param sourceString The sourceString to set
   */
  public void setSourceString(int sourceString) {
    this.sourceString = sourceString;
  }

}
