package haubold.stringmatching.suffixtree.gui;

import java.awt.BorderLayout;
import javax.swing.*;

import haubold.stringmatching.classical.StringMatchingDemo;

/**
 * @author Bernhard Haubold
 * Date: Sep 1, 2003; time: 9:06:28 AM.
 *
 * Description:
 */
public class StringMatchingTest extends JFrame {
  private static final long serialVersionUID = 1L;

  public static void main(String[] args) {
    StringMatchingTest smt = new StringMatchingTest();
    smt.getContentPane().setLayout(new BorderLayout());
    StringMatchingDemo smd = new StringMatchingDemo("String Matching", "resources/help/stringMatching/index.html");
    smt.getContentPane().add(smd,BorderLayout.CENTER);
    smt.setSize(100,100);
    smt.setVisible(true);
  }

}
