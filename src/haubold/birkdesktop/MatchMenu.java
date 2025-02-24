package haubold.birkdesktop;

import haubold.resources.util.*;
import java.awt.event.*;

import javax.swing.*;


public class MatchMenu extends JMenu {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public MatchMenu(BirkDesktop parentFrame) {

    JMenuItem menuItem;
    FrameCreationListener fcl = null;
    Object args[];
    BirkhaeuserGUIComponents bgc;

    bgc = new BirkhaeuserGUIComponents();
    this.setText("Match");
    this.setBackground(bgc.getColor2());
    this.setMnemonic('M');

    // string matching
    menuItem = new JMenuItem("String Matching");
    menuItem.setMnemonic('s');
    args = new Object[] {"String Matching",
                         parentFrame.getHelpPath() + "defaultHelp" + "/index.html"
                        };
    try {
      fcl = new FrameCreationListener(
        Class.forName("haubold.stringmatching.classical.StringMatchingDemo"),
        args,parentFrame);
    } catch(Exception e) {
      e.printStackTrace();
    }
    menuItem.addActionListener(fcl);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
    this.add(menuItem);
    // Suffix Tree
    menuItem = new JMenuItem("Suffix Tree");
    menuItem.setMnemonic('u');
    args = new Object[] {"Suffix Tree",parentFrame.getHelpPath() + "defaultHelp" + "/index.html"};
    try {
      fcl = new FrameCreationListener(
        Class.forName("haubold.stringmatching.suffixtree.gui.SuffixTreeDemo"),
        args,parentFrame);
    } catch(Exception e) {
      e.printStackTrace();
    }
    menuItem.addActionListener(fcl);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,ActionEvent.CTRL_MASK));
    this.add(menuItem);
    // Repeat Searching
    menuItem = new JMenuItem("Repeat Searching");
    menuItem.setMnemonic('r');
    args = new Object[] {"Repeat Searching",parentFrame.getHelpPath() + "defaultHelp" + "/index.html"};
    try {
      fcl = new FrameCreationListener(
        Class.forName("haubold.stringmatching.suffixtree.gui.RepeatDemo"),
        args,parentFrame);
    } catch(Exception e) {
      e.printStackTrace();
    }
    menuItem.addActionListener(fcl);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,ActionEvent.CTRL_MASK));
    this.add(menuItem);
    // Hash Table
    menuItem = new JMenuItem("Hash Table");
    menuItem.setMnemonic('h');
    args = new Object[] {"Hash Table",parentFrame.getHelpPath() + "defaultHelp" + "/index.html"};
    try {
      fcl = new FrameCreationListener(
        Class.forName("haubold.hashtable.HashTableDemo"),
        args,parentFrame);
    } catch(Exception e) {
      e.printStackTrace();
    }
    menuItem.addActionListener(fcl);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.CTRL_MASK));
    this.add(menuItem);
    // Dotplot
    menuItem = new JMenuItem("Dotplot");
    menuItem.setMnemonic('d');
    args = new Object[] {"Dotplot",parentFrame.getHelpPath() + "defaultHelp" + "/index.html"};
    try {
      fcl = new FrameCreationListener(
        Class.forName("haubold.hashtable.DotPlotDemo"),
        args,parentFrame);
    } catch(Exception e) {
      e.printStackTrace();
    }
    menuItem.addActionListener(fcl);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,ActionEvent.CTRL_MASK));
    this.add(menuItem);
  }

}
