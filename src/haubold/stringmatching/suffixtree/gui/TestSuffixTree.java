package haubold.stringmatching.suffixtree.gui;

/**
 * @author haubold
 *
 */

import haubold.stringmatching.suffixtree.algorithms.*;
import java.util.*;

public class TestSuffixTree {

  TestSuffixTree() {
  }

  public static void main(String[] arg) {
    UkkonenSuffixTree st = new UkkonenSuffixTree();
    DrawSTLatex latex = new DrawSTLatex();
    st.setDebug(false);
//		st.setDebug(false);
    ArrayList<Integer> list = new ArrayList<Integer>();
//		String text = data.getPhi()+'\000';
    String text = "ACCGCTCA$";
    String pattern = "GTTCGT";
    char[] textChar = text.toCharArray();
    char[] patternChar = pattern.toCharArray();
    long t1 = System.currentTimeMillis();
    st.constructSuffixTree(textChar);
    latex.draw(st,"/home/haubold/Publications/IntroBioinf/Text/ExactMatching/Figures/accgctca.tex");
    long t2 = System.currentTimeMillis();
    System.out.println("Suffix tree constructed in "
                       + (t2-t1)
                       + " ms.");
    t1 = System.currentTimeMillis();
    ArrayList matchList = st.match(patternChar);
    t2 =  System.currentTimeMillis();
    System.out.println("Matching in suffix tree: " + (t2-t1) + " ms");
    for(int i=0; i<matchList.size(); i++) {
      System.out.println("match at: " + matchList.get(i));
    }
    int i;
    t1 = System.currentTimeMillis();
    for(i=0; i<text.length() - patternChar.length +1; i++) {
      int j=0;
      while(j<patternChar.length && textChar[i+j] == patternChar[j]) {
        j++;
      }
      if(j==pattern.length()) {
        list.add(Integer.valueOf(i));
      }
    }
    t2 = System.currentTimeMillis();
    System.out.println("Matching with naive algorithm: " + (t2-t1) + " ms");
    System.out.println("CLASSICAL RESULTS:");
    for(i=0; i<list.size(); i++) {
      System.out.println("Classical match at: " + list.get(i));
    }
  }

}
