/*
 * Created on Feb 13, 2003
 *
 */
package haubold.stringmatching.suffixtree.algorithms;

import java.util.*;
/**
 * @author haubold
 */
public class NaiveSuffixTree implements SuffixTree {
  SuffixNode root;
  char[] text;
  int textIndex = 0;
  TreeMonitor monitor;
  boolean debug = false;
  ArrayList<Integer> matchList;

  public NaiveSuffixTree() {
    if(debug) {
      monitor = new TreeMonitor();
    }
    matchList = new ArrayList<Integer>();
  }

  public SuffixNode constructSuffixTree(char[] text) {
    this.text = text;
    initialize();
    generateDebugOutput();
    for(textIndex=1; textIndex<text.length-1; textIndex++) {
      root = insertSuffix(root,textIndex);
      generateDebugOutput();
    }
//		generateDebugOutput();
    return root;
  }

  private SuffixNode initialize() {
    root = new SuffixNode(null,0,-1);
    SuffixNode leaf = new SuffixNode(root, 0, text.length-1);
    leaf.setLeafLabel(0);
    leaf.setParent(root);
    root.addChild(text[0],leaf);
    return root;
  }

  private SuffixNode insertSuffix(SuffixNode node, int suffixIndex) {
//		System.out.println("Insert suffix");
//		Try moving one level down from incoming ROOT.
    if(node.getChild(text[suffixIndex]) != null) {
      node = node.getChild(text[suffixIndex]);
//			If this is not possible,
    } else {
//			insert new edge and return root.
//			System.out.println("A");
      insertNode(node,suffixIndex,-1);
      return node;
    }
//		Move down from node.
    int i=0;
    nodeHopping: while(text[node.getStartLabel()+i] == text[suffixIndex]) {
      i++;
      suffixIndex++;
      if(node.getStartLabel()+i > node.getEndLabel()) {
        if(node.getChild(text[suffixIndex]) != null) {
          node = node.getChild(text[suffixIndex]);
          i = 0;
        } else {
          break nodeHopping;
        }
      }
    }

    i--;
    suffixIndex--;

//		Insert node with label beginning at suffixIndex+i+1;
//		notice also the end of the match in the edge,
//		indicated by node.getStartLabel()+i
//		System.out.println("B");
    String edgeLabel = new String();
    for(int j=node.getStartLabel(); j<= node.getEndLabel(); j++) {
      edgeLabel += text[j];
    }
//		System.out.println("Match node: " + node.getId()
//		+ " Edge label: " + edgeLabel
//		+ " suffixIndex+i: " + (suffixIndex+1));
    insertNode(node,suffixIndex,node.getStartLabel()+i);
    return root;
  }

  private void insertNode(SuffixNode node, int suffixIndex, int edgeEnd) {
//		Check if node is ROOT.
    if(node.getParent() == null) {
      SuffixNode leaf = new SuffixNode(node, suffixIndex, text.length-1);
      leaf.setLeafLabel(textIndex);
      node.addChild(text[suffixIndex],leaf);
      leaf.setParent(node);
      return;
    }
    if(node.getEndLabel() == edgeEnd) {
      SuffixNode leaf = new SuffixNode(node,suffixIndex+1,text.length-1);
      leaf.setLeafLabel(textIndex);
      node.addChild(text[suffixIndex+1],leaf);
      leaf.setParent(node);
      return;
    }
    if(node.getEndLabel() > edgeEnd) {
      SuffixNode internalNode = new SuffixNode(node.getParent(),node.getStartLabel(), edgeEnd);
      node.getParent().removeChild(text[node.getStartLabel()]);
      node.setStartLabel(edgeEnd+1);
      internalNode.addChild(text[node.getStartLabel()],node);
      node.getParent().addChild(text[internalNode.getStartLabel()],internalNode);
      node.setParent(internalNode);
      SuffixNode leaf = new SuffixNode(internalNode,suffixIndex+1,text.length-1);
      internalNode.addChild(text[suffixIndex+1],leaf);
      leaf.setLeafLabel(textIndex);
      leaf.setParent(internalNode);
      return;
    } else {
      System.out.println("ERROR: insertion of node "
                         + node.getId()
                         + " failed."
                         + " node.getEndLabel(): "
                         + node.getEndLabel()
                         + " edgeEnd: "
                         + edgeEnd
                         + " suffixIndex: "
                         + suffixIndex);
    }
  }

  private void generateDebugOutput() {
    if(debug) {
      System.out.println("*********** Tree"
                         + textIndex
                         + "****************");
      monitor.writeTree(root);
      System.out.println("************************");
    }
  }

  /**
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
    if(debug && monitor == null) {
      monitor = new TreeMonitor();
    }
  }
  /**
   * @return char[]
   */
  public char[] getText() {
    return text;
  }

  /**
   * @return SuffixNode
   */
  public SuffixNode getRoot() {
    return root;
  }

  public ArrayList match(char[] pattern) {
    int pi = 0;
    @SuppressWarnings("unused")
    int m[];
    SuffixNode node = root;
//		Find match along edge.
    if(node.getChild(pattern[0]) == null) {
      m = new int[0];
      return matchList;
    } else {
      node = node.getChild(pattern[0]);
    }
    int i=0;
    nodeHopping: while(pi<pattern.length
                       && text[node.getStartLabel()+i] == pattern[pi]) {
      i++;
      pi++;
      if(node.getStartLabel()+i > node.getEndLabel()) {
        if(pi<pattern.length && node.getChild(pattern[pi]) != null) {
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
    if(pi == pattern.length-1) {
      searchLeaves(node);
    }
//		m = new int[matchList.size()];
//		for(i=0;i<matchList.size();i++){
//			m[i] = ((Integer)matchList.get(i)).intValue();
//		}
    return matchList;
  }

  private void searchLeaves(SuffixNode node) {
    if(!node.isLeaf()) {
      SuffixNode[] children = node.getChildren();
      for(int i=0; i<children.length; i++) {
        searchLeaves(children[i]);
      }
    } else {
      matchList.add(Integer.valueOf(node.getLeafLabel()));
    }
  }

}

