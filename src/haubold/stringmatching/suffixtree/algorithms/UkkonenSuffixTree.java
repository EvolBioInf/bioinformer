package haubold.stringmatching.suffixtree.algorithms;

/**
 * @author haubold
 * Date: Feb 23, 2003; time: 10:44:43 AM.
 *
 * Description:
 */

import java.util.*;

public class UkkonenSuffixTree implements SuffixTree {
  private char[] text1;
  private char[] text2;
  SuffixNode root;
  boolean debug;
  TreeMonitor monitor;
  int leafEnd;
  int leafLabel;
  int longestRepeatLength;
  ArrayList<Integer> matchList;
  boolean masked = false;
  ReferencePair referencePair;
  private int stringCount = 1;

  public void constructSuffixTree(char[] text) {
    this.text1 = text;
    this.text2 = text;
    SourceNode source = new SourceNode();
    root = new SuffixNode(null, -1, -1);
    source.setStartLabel(0);
    source.setEndLabel(1);
    source.setRoot(root);
    root.setSuffixLink(source);
    referencePair = new ReferencePair();
    referencePair.setNode(root);
    referencePair.setLeftPointer(0);
    int i = -1;
    leafLabel = -1;
    while (i < text.length - 1) {
      i++;
      leafEnd = i;
      referencePair.setRightPointer(i);
      referencePair = update(referencePair);
      referencePair.setRightPointer(i);
      referencePair = canonize(referencePair);
    }
    fixLeafEndLabels(root);
    if (debug) {
      monitor.writeTree(root);
    }
  }


  private ReferencePair update(ReferencePair referencePair) {
    SuffixNode s = referencePair.getNode();
    int i = referencePair.getRightPointer();

    SuffixNode oldR = root;
    referencePair.setRightPointer(i - 1);
    referencePair = testAndSplit(referencePair, text2[i]);

    SuffixNode r = referencePair.getNode();
    while (!referencePair.isEndPoint()) {
      SuffixNode rDash = new SuffixNode(r, i, Integer.MAX_VALUE);
      r.addChild(text2[i], rDash);
      rDash.setLeafLabel(++leafLabel);
      rDash.setStringId(stringCount);
      if (oldR.getParent() != null) {
        oldR.setSuffixLink(r);
      }
      oldR = r;
      SuffixNode sl = s.getSuffixLink();
      referencePair.setNode(sl);
      referencePair = canonize(referencePair);
      s = referencePair.getNode();
      referencePair = testAndSplit(referencePair, text2[i]);
      r = referencePair.getNode();
    }
    if (oldR.getParent() != null) {
      oldR.setSuffixLink(s);
    }
    referencePair.setNode(s);
    return referencePair;
  }

  private ReferencePair testAndSplit(ReferencePair referencePair, char t) {
    SuffixNode s = referencePair.getNode();
    int k = referencePair.getLeftPointer();
    int p = referencePair.getRightPointer();
    if (k <= p) {
      SuffixNode sDash = s.getChild(text2[k]);
      if (t == text1[sDash.getStartLabel() + p - k + 1]) {
        referencePair.setEndPoint(true);
        return referencePair;
      } else {
        SuffixNode r =
          new SuffixNode(
          s,
          sDash.getStartLabel(),
          +sDash.getStartLabel() + p - k);
        s.removeChild(text2[k]);
        s.addChild(text2[r.getStartLabel()], r);
        sDash.setParent(r);
        sDash.setStartLabel(sDash.getStartLabel() + p - k + 1);
        r.addChild(text1[sDash.getStartLabel()], sDash);
        referencePair.setNode(r);
        referencePair.setEndPoint(false);
        if (this.isMasked()) {
          if (r.getParent().isMasked()) {
            r.setNodeDepth(0);
          } else {
            // Check whether string contains X.
            maskLoop : for (
              int i = r.getStartLabel();
              i <= r.getEndLabel();
              i++) {
              if (text1[i] == 'x'
                  || text1[i] == 'X'
                  || text1[i] == 'n'
                  || text1[i] == 'N') {
                r.setNodeDepth(0);
                r.setMasked(true);
                break maskLoop;
              }
            }
            if (r.isMasked()) {
              r.setNodeDepth(0);
            } else {
              r.setNodeDepth(
                r.getParent().getNodeDepth()
                + r.getEndLabel()
                - r.getStartLabel()
                + 1);
            }
          }
        } else {
          r.setNodeDepth(
            r.getParent().getNodeDepth()
            + r.getEndLabel()
            - r.getStartLabel()
            + 1);
        }
        return referencePair;
      }
    } else {
      if (s.getChild(t) == null) {
        referencePair.setEndPoint(false);
        return referencePair;
      } else {
        referencePair.setEndPoint(true);
        return referencePair;
      }
    }
  }

  private ReferencePair canonize(ReferencePair referencePair) {
    SuffixNode s = referencePair.getNode();
    int k = referencePair.getLeftPointer();
    int p = referencePair.getRightPointer();
    if (p < k) {
      return referencePair;
    } else {
      SuffixNode testNode = s.getChild(text2[k]);
      while (testNode.getEndLabel() - testNode.getStartLabel()
             <= p - k) {
        s = testNode;
        k += s.getEndLabel() - s.getStartLabel() + 1;
        if (k <= p) {
          testNode = s.getChild(text2[k]);
        }
      }
      referencePair.setNode(s);
      referencePair.setLeftPointer(k);
      return referencePair;
    }
  }

  @SuppressWarnings("unused")
  private ReferencePair findReferencePair(char[] pattern) {
    int pi = 0;
    SuffixNode node = root;
    //		Find match along edge.
    if (node.getChild(pattern[0]) == null) {
      return null;
    } else {
      node = node.getChild(pattern[0]);
    }
    int i = 0;
    nodeHopping : while (
      pi < pattern.length
      && text1[node.getStartLabel() + i] == pattern[pi]) {
      i++;
      pi++;
      if (node.getStartLabel() + i > node.getEndLabel()) {
        if (pi < pattern.length
            && node.getChild(pattern[pi]) != null) {
          node = node.getChild(pattern[pi]);
          i = 0;
        } else {
          break nodeHopping;
        }
      }
    }
    referencePair.setNode(node.getParent());
    referencePair.setLeftPointer(pi-1);
    return referencePair;
  }

  public ArrayList match(char[] pattern) {
    int pi = 0;
    if (matchList == null) {
      matchList = new ArrayList<Integer>();
    }
    SuffixNode node = root;
    //		Find match along edge.
    if (node.getChild(pattern[0]) == null) {
      matchList.clear();
      return matchList;
    } else {
      node = node.getChild(pattern[0]);
    }
    int i = 0;
    nodeHopping : while (
      pi < pattern.length
      && text1[node.getStartLabel() + i] == pattern[pi]) {
      i++;
      pi++;
      if (node.getStartLabel() + i > node.getEndLabel()) {
        if (pi < pattern.length
            && node.getChild(pattern[pi]) != null) {
          node = node.getChild(pattern[pi]);
          i = 0;
        } else {
          break nodeHopping;
        }
      }
    }

    i--;
    pi--;
    matchList.clear();
    if (pi == pattern.length - 1) {
      searchLeaves(node);
    }
    return matchList;
  }

  public ArrayList findRepeat(int length) {
    ArrayList<Repeat> repeatList = new ArrayList<Repeat>();
    repeatList = findRepeat(root, length, repeatList);
    return repeatList;
  }

  private ArrayList<Repeat> findRepeat(
    SuffixNode node,
    int length,
    ArrayList<Repeat> repeatList) {

    if (!node.isLeaf()) {
      if (node.getNodeDepth() == length) {
        Repeat repeat = new Repeat(length, node);
        repeatList.add(findRepeatPositions(node, repeat, length));
      }
      SuffixNode[] children = node.getChildren();
      for (int i = 0; i < children.length; i++) {
        findRepeat(children[i], length, repeatList);
      }
    }
    return repeatList;
  }

  private Repeat findRepeatPositions(
    SuffixNode node,
    Repeat repeat,
    int length) {
    if (!node.isLeaf()) {
      SuffixNode[] children = node.getChildren();
      for (int i = 0; i < children.length; i++) {
        findRepeatPositions(children[i], repeat, length);
      }
    } else {
      repeat.add(Integer.valueOf(node.getLeafLabel()));
    }
    return repeat;
  }

  private void searchLeaves(SuffixNode node) {
    if (!node.isLeaf()) {
      SuffixNode[] children = node.getChildren();
      for (int i = 0; i < children.length; i++) {
        searchLeaves(children[i]);
      }
    } else {
      matchList.add(Integer.valueOf(node.getLeafLabel()));
    }
  }



  private void fixLeafEndLabels(SuffixNode node) {
    if (node.isLeaf()) {
      node.setEndLabel(leafEnd);
    } else {
      SuffixNode[] children = node.getChildren();
      for (int i = 0; i < children.length; i++) {
        fixLeafEndLabels(children[i]);
      }
    }
  }

  private Repeat findLongestRepeat(SuffixNode node, Repeat longestRepeat) {
    if (!node.isLeaf()) {
      if (node.getNodeDepth() > longestRepeat.getLength()) {
        longestRepeat.setLength(node.getNodeDepth());
        longestRepeat.setNode(node);
      }
      SuffixNode children[] = node.getChildren();
      for (int i = 0; i < children.length; i++) {
        findLongestRepeat(children[i], longestRepeat);
      }
    }
    return longestRepeat;
  }

  @SuppressWarnings("unused")
  private Repeat findLongestRepeatPositions(SuffixNode node, Repeat repeat) {
    if (node.isLeaf()) {
      repeat.add(Integer.valueOf(node.getLeafLabel()));
    } else {
      SuffixNode[] children = node.getChildren();
      for (int i = 0; i < children.length; i++) {
        findLongestRepeatPositions(children[i], repeat);
      }
    }
    return repeat;
  }

  /**
   * Returns the root.
   * @return SuffixNode
   */
  public SuffixNode getRoot() {
    return root;
  }

  /**
   * Returns the text.
   * @return char[]
   */
  public char[] getText() {
    return text1;
  }

  /**
   * Sets the root.
   * @param root The root to set
   */
  public void setRoot(SuffixNode root) {
    this.root = root;
  }

  /**
   * Sets the text.
   * @param text The text to set
   */
  public void setText(char[] text) {
    this.text1 = text;
  }

  /**
   * Returns the debug.
   * @return boolean
   */
  public boolean isDebug() {
    return debug;
  }

  /**
   * Sets the debug.
   * @param debug The debug to set
   */
  public void setDebug(boolean debug) {
    this.debug = debug;
    if (debug && monitor == null) {
      monitor = new TreeMonitor();
    }
  }

  /**
   * Returns the longestRepeat.
   * @return Repeat
   */
  public ArrayList getLongestRepeat() {
    Repeat longestRepeat = new Repeat();
    ArrayList repeatList = new ArrayList();
    longestRepeat.setLength(0);
    longestRepeat.clear();
    longestRepeat = findLongestRepeat(root, longestRepeat);
    repeatList = findRepeat(longestRepeat.getLength());
    return repeatList;
  }
  /**
   * Returns the longestRepeatLength.
   * @return int
   */
  public int getLongestRepeatLength() {
    Repeat longestRepeat = new Repeat();
    longestRepeat.setLength(0);
    longestRepeat.clear();
    longestRepeat = findLongestRepeat(root, longestRepeat);
    return longestRepeat.getLength();
  }
  /**
   * Evaluates to true if the string on which the suffix tree is based
   * contains masked repeat regions.
   * @return boolean
   */
  public boolean isMasked() {
    return masked;
  }

  /**
   * Should be set "true" if the string on which the suffix tree is based
   * contains masked repeat regions.
   * @param masked The masked to set
   */
  public void setMasked(boolean masked) {
    this.masked = masked;
  }

}
