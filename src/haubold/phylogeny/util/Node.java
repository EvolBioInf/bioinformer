package haubold.phylogeny.util;

/**
 * @author Bernhard Haubold
 * Date: Jun 7, 2003; time: 1:16:37 PM.
 *
 * Description: Interface for node of binary phylogenetic tree.
 */
public interface Node {

  // Pointers to parent node & child nodes
  Node getLeftChild();
  Node getRightChild();
  Node getParent();
  void setLeftChild(Node leftChild);
  void setRightChild(Node rightChild);
  void setParent(Node node);

  // X & y coodinates of node
  double getXPosition();
  double getYPosition();
  void setXPosition(double xPosition);
  void setYPosition(double yPosition);

  // Height of node
  double getNodeHeight();
  void setNodeHeight(double height);

  // Length of branch leading to parent of node
  double getBranchLength();
  void setBranchLength(double branchLength);

  // Whether or not node is a leaf
  boolean isLeaf();

  // Identifier
  int getId();

  int getIndex();
  void setIndex(int index);

}
