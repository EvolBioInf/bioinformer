/*
 * Created on Feb 13, 2003
 *
 */
package haubold.stringmatching.suffixtree.algorithms;

/**
 * The purpose of <code>TreeMonitor</code> is to provide debugging
 * information about suffix trees.
 * @author haubold
 */
public class TreeMonitor {
  public TreeMonitor() {
  }

  public void writeTree(SuffixNode tree) {
    traverseTree(tree);
  }

  private void traverseTree(SuffixNode node) {
    if(!node.isLeaf()) {
      SuffixNode[] children = node.getChildren();
      for(int i=0; i<children.length; i++) {
        traverseTree(children[i]);
      }
    }
    debugOutput(node);
  }

  private void debugOutput(SuffixNode node) {
    String output = "Id: "
                    + node.getId()
                    + " start "
                    + node.getStartLabel()
                    + " end "
                    + node.getEndLabel();
    if(node.getParent() != null) {
      output += " parentId: "
                + node.getParent().getId();
    } else {
      output += " node is ROOT ";
    }
    if(node.isLeaf()) {
      output += " leaf-label: "
                + node.getLeafLabel()
                + " stringId: "
                + node.getStringId();
    }
    System.out.println(output);
  }
}
