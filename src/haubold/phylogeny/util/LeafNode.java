package haubold.phylogeny.util;

/**
 * @author Bernhard Haubold
 * Date: Jun 7, 2003; time: 1:55:14 PM.
 *
 * Description:
 */
public class LeafNode extends BinaryTreeNode {

  String label = "leafLabel";

  /**
   * No args constructor.
   */
  public LeafNode() {
    leftChild = null;
    rightChild = null;
  }

  public LeafNode(Node parent) {
    this.parent = parent;
    leftChild = null;
    rightChild = null;
  }

  public LeafNode(Node parent, String label) {
    this.parent = parent;
    this.label = label;
    leftChild = null;
    rightChild = null;
  }

  /**
   * @see haubold.phylogeny.util.Node#isLeaf()
   */
  public boolean isLeaf() {
    return true;
  }

  /**
   * Returns the label.
   * @return String
   */
  public String getLabel() {
    return label;
  }

  /**
   * Sets the label.
   * @param label The label to set
   */
  public void setLabel(String label) {
    this.label = label;
  }

}
