package haubold.phylogeny.util;

/**
 * @author Bernhard Haubold
 * Date: Jun 7, 2003; time: 2:06:12 PM.
 *
 * Description:
 */

public interface Tree {
  /**
   * Returns the node of the tree.
   */
  Node getRoot();

  /**
   * Returns the tree.
   */
  Node[] getTree();

  /**
   * Returns the label of the tree
   */
  String getLabel();

  /**
   * Sets the tree's label.
   */
  void setLabel(String label);
}

