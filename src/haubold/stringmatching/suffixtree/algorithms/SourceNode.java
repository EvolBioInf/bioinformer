package haubold.stringmatching.suffixtree.algorithms;


/**
 * @author haubold
 * Date: Feb 23, 2003; time: 1:29:09 PM.
 *
 * Description:
 */
public class SourceNode extends SuffixNode {
  SuffixNode root;
  public SuffixNode getChild(char c) {
    return root;
  }
  /**
   * Returns the root.
   * @return SuffixNode
   */
  public SuffixNode getRoot() {
    return root;
  }

  /**
   * Sets the root.
   * @param root The root to set
   */
  public void setRoot(SuffixNode root) {
    this.root = root;
  }

}
