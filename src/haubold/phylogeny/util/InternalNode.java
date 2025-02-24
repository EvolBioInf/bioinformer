package haubold.phylogeny.util;

/**
 * @author Bernhard Haubold
 * Date: Jun 7, 2003; time: 1:42:18 PM.
 *
 * Description:
 */
public class InternalNode extends BinaryTreeNode {

  /**
   * No args constructor.
   */
  public InternalNode() {

  }

  /**
   * Constructor for root node
   */
  public InternalNode(Node leftChild, Node rightChild) {
    this.leftChild = leftChild;
    this.rightChild = rightChild;
    this.parent = null;
  }

  /**
   * Constructor for non-root internal node.
   */
  public InternalNode(Node leftChild, Node rightChild, Node parent) {
    this.leftChild = leftChild;
    this.rightChild = rightChild;
    this.parent = parent;
  }

  public boolean isLeaf() {
    return false;
  }

}
