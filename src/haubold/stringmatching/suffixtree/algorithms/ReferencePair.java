package haubold.stringmatching.suffixtree.algorithms;


/**
 * @author haubold
 * Date: Feb 23, 2003; time: 10:50:17 AM.
 *
 * Description:
 */
public class ReferencePair {
  SuffixNode node;
  int leftPointer;
  int rightPointer;
  boolean endPoint;
  /**
   * Returns the leftPointer.
   * @return int
   */
  public int getLeftPointer() {
    return leftPointer;
  }

  /**
   * Returns the node.
   * @return SuffixNode
   */
  public SuffixNode getNode() {
    return node;
  }

  /**
   * Returns the rightPointer.
   * @return int
   */
  public int getRightPointer() {
    return rightPointer;
  }

  /**
   * Sets the leftPointer.
   * @param leftPointer The leftPointer to set
   */
  public void setLeftPointer(int leftPointer) {
    this.leftPointer = leftPointer;
  }

  /**
   * Sets the node.
   * @param node The node to set
   */
  public void setNode(SuffixNode node) {
    this.node = node;
  }

  /**
   * Sets the rightPointer.
   * @param rightPointer The rightPointer to set
   */
  public void setRightPointer(int rightPointer) {
    this.rightPointer = rightPointer;
  }

  /**
   * Returns the endPoint.
   * @return boolean
   */
  public boolean isEndPoint() {
    return endPoint;
  }

  /**
   * Sets the endPoint.
   * @param endPoint The endPoint to set
   */
  public void setEndPoint(boolean endPoint) {
    this.endPoint = endPoint;
  }

}
