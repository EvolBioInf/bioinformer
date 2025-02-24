/*
 * Created on Feb 13, 2003
 *
 */
package haubold.stringmatching.suffixtree.algorithms;


import java.io.*;

/**
 * This class allows the user to generate a LaTeX file corresponding
 * to a suffix tree.
 * @author haubold
 */
public class DrawSTLatex {
  int xPos = 0;
  PrintWriter pw;
  OutputStream os;
  char[] text;
  SuffixNode root;
  int levels;
  String outFile;
  float levelScale = 2.5f;
  public static int DOT_NODE = 0;
  public static int CIRCLE_NODE = 1;
  public static int COMPLETE_GRAPHIC = 0;
  public static int INSERTION_GRAPHIC = 1;
  private int nodeType;
  private int graphicType;

  public void draw(SuffixTree st) {
    this.root = st.getRoot();
    text = st.getText();
    draw(st,"suffixTree.tex");
  }

  public void draw(SuffixTree st, String outFile) {
    this.root = st.getRoot();
    text = st.getText();
    try {
      os = new FileOutputStream(outFile);
      pw = new PrintWriter(os);
    } catch(Exception e) {
      e.printStackTrace();
    }
    if(graphicType == COMPLETE_GRAPHIC) {
      pw.print(getHeader());
    }
    xPos = 0;
    levels = 0;
    root.setYPos(0);
    initializePositions(root);
    root.setYPos(root.getYPos()*(levelScale+1));
//		Traverse tree to find box coordinates.
    traverseTree(root,false);
    pw.print("\\begin{pspicture}("
             + xPos
             + ","
             +(root.getYPos())
             + ")\n\\psset{linecolor=lightgray}\n");
//		Traverse tree to draw nodes.
    traverseTree(root,true);
    pw.print("\\end{pspicture}");
    if(graphicType == COMPLETE_GRAPHIC) {
      pw.print(getFooter());
    }
    try {
      pw.close();
      os.close();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void initializePositions(SuffixNode node) {
    node.setXPos(0f);
    node.setYPos(0f);
    if(!node.isLeaf()) {
      SuffixNode[] children = node.getChildren();
      for(int i=0; i<children.length; i++) {
        localizeLeaves(children[i]);
      }
      node.setYPos(children[0].getYPos()+1);
    } else {
      node.setXPos(xPos++);
    }
  }

  private void traverseTree(SuffixNode node, boolean output) {
    if(node.getParent() != null) {
      node.setYPos(node.getParent().getYPos()-levelScale);
    }
    if(!node.isLeaf()) {
      SuffixNode[] children = node.getChildren();
//			Place parent in the middle of its children.
      for(int i=0; i<children.length; i++) {
        traverseTree(children[i],output);
      }
      node.setXPos(children[0].getXPos()
                   + (children[children.length-1].getXPos()-children[0].getXPos())/2f);
    }
    if(output) {
      drawNode(node);
    }
  }

  private void drawNode(SuffixNode node) {
    if(nodeType == CIRCLE_NODE) {
      pw.print("\\rput("
               + node.getXPos()
               + ","
               + node.getYPos()
               + "){\\circlenode{"
               + node.getId()
               + "}{"
               + node.getId()
               + "}}\n");
    } else {
      pw.print("\\dotnode("
               + node.getXPos()
               + ","
               + node.getYPos()
               + "){"
               + node.getId()
               + "}\n");
    }
    if(!node.isLeaf()) {
      SuffixNode children[]  = node.getChildren();
      for(int i=0; i<children.length; i++) {
        pw.print("\\pstextpath[c]{\\psline("
                 + node.getXPos()
                 + ","
                 + node.getYPos()
                 + ")("
                 + children[i].getXPos()
                 + ","
                 + children[i].getYPos()
                 + ")}{\\texttt{");
        for(int j=children[i].getStartLabel(); j<=children[i].getEndLabel(); j++) {
          if(text[j] != '$') {
            pw.print(text[j]);
          } else {
            pw.print("\\" + text[j]);
          }
        }
        pw.print("}}\n");
      }
    } else {
      pw.print("\\nput{-90}{"
               + node.getId()
               + "}{"
               + (node.getLeafLabel()+1)
               + "}\n");
    }
  }

  private String getHeader() {
    String header = "\\documentclass{article}\n"
                    + "\\usepackage{times}\n"
                    + "\\usepackage{pstricks,pst-node,pst-tree,pst-text,pstcol}\n"
                    + "\\usepackage{graphics}\n"
                    + "\\oddsidemargin=0cm\n"
                    + "\\evensidemargin=0cm\n"
                    + "\\textwidth=16cm\n"
                    + "\\textheight=22cm\n"
                    + "\\begin{document}\n";
    return header;
  }

  private String getFooter() {
    String footer = "\\end{document}";
    return footer;
  }

  private void localizeLeaves(SuffixNode node) {
    if(!node.isLeaf()) {
      SuffixNode[] children = node.getChildren();
      for(int i=0; i<children.length; i++) {
        localizeLeaves(children[i]);
      }
      node.setYPos(children[0].getYPos()+1);
    } else {
      node.setXPos(xPos++);
    }
  }

  /**
   * Returns the graphicType.
   * @return int
   */
  public int getGraphicType() {
    return graphicType;
  }

  /**
   * Returns the nodeType.
   * @return int
   */
  public int getNodeType() {
    return nodeType;
  }

  /**
   * Sets the graphicType.
   * @param graphicType The graphicType to set
   */
  public void setGraphicType(int graphicType) {
    this.graphicType = graphicType;
  }

  /**
   * Sets the nodeType.
   * @param nodeType The nodeType to set
   */
  public void setNodeType(int nodeType) {
    this.nodeType = nodeType;
  }

}
