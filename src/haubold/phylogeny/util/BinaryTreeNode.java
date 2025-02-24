package haubold.phylogeny.util;

/**
 * @author Bernhard Haubold
 * Date: Jun 7, 2003; time: 1:29:20 PM.
 *
 * Description:
 */
public abstract class BinaryTreeNode implements Node {

  int id;
  Node leftChild;
  Node rightChild;
  Node parent;
  double xPosition;
  double yPosition;
  boolean leaf;
  int index;
  double nodeHeight;
  double branchLength;

  private static int nextId = 0;

  // Constructor
  BinaryTreeNode() {
    id = nextId++;
  }

  /**
   * @see haubold.phylogeny.util.BinaryTreeNode#getLeftChild()
   */
  public Node getLeftChild() {
    return leftChild;
  }

  /**
   * @see haubold.phylogeny.util.BinaryTreeNode#getRightChild()
   */
  public Node getRightChild() {
    return rightChild;
  }

  /**
   * @see haubold.phylogeny.util.BinaryTreeNode#getParent()
   */
  public Node getParent() {
    return parent;
  }

  /**
   * @see haubold.phylogeny.util.BinaryTreeNode#setLeftChild(BinaryTreeNode)
   */
  public void setLeftChild(Node leftChild) {
    this.leftChild = leftChild;
  }

  /**
   * @see haubold.phylogeny.util.BinaryTreeNode#setRightChild(BinaryTreeNode)
   */
  public void setRightChild(Node rightChild) {
    this.rightChild = rightChild;
  }

  /**
   * @see haubold.phylogeny.util.BinaryTreeNode#setParent()
   */
  public void setParent(Node parent) {
    this.parent = parent;
  }

  /**
   * @see haubold.phylogeny.util.BinaryTreeNode#getXPosition()
   */
  public double getXPosition() {
    return xPosition;
  }

  /**
   * @see haubold.phylogeny.util.BinaryTreeNode#getYPosition()
   */
  public double getYPosition() {
    return yPosition;
  }

  /**
   * @see haubold.phylogeny.util.BinaryTreeNode#setXPosition(double)
   */
  public void setXPosition(double xPosition) {
    this.xPosition = xPosition;
  }

  /**
   * @see haubold.phylogeny.util.BinaryTreeNode#setYPosition(double)
   */
  public void setYPosition(double yPosition) {
    this.yPosition = yPosition;
  }

  /**
   * @see haubold.phylogeny.util.BinaryTreeNode#isLeaf()
   */
  public abstract boolean isLeaf();

  /**
   * @see haubold.phylogeny.util.BinaryTreeNode#getId()
   */
  public int getId() {
    return id;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public int getIndex() {
    return index;
  }

  /**
   * Returns the nodeHeight.
   * @return double
   */
  public double getNodeHeight() {
    return nodeHeight;
  }

  /**
   * Sets the nodeHeight.
   * @param nodeHeight The nodeHeight to set
   */
  public void setNodeHeight(double nodeHeight) {
    this.nodeHeight = nodeHeight;
  }

  /**
   * Returns the branchLength.
   * @return double
   */
  public double getBranchLength() {
    return branchLength;
  }

  /**
   * Sets the branchLength.
   * @param branchLength The branchLength to set
   */
  public void setBranchLength(double branchLength) {
    this.branchLength = branchLength;
  }

}
