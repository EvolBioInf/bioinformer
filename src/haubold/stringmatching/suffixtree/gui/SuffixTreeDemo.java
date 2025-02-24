/*
 * Created on Feb 13, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code Template
 */
package haubold.stringmatching.suffixtree.gui;
import haubold.stringmatching.suffixtree.algorithms.*;
import haubold.resources.demo.*;
import haubold.resources.util.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

/**
 * @author haubold
 */
public class SuffixTreeDemo extends DemoFrame {
  ;
  private static final long serialVersionUID = 1L;
  JLabel textLabel;
  JTextField textField;
  JPanel textPanel,guiPanel;
  JScrollPane scrollPane;
  DrawPanel treePanel;
  UkkonenSuffixTree suffixTree;
  DrawSTLatex latex;
  Random r;
  JCheckBox edgeCheckBox, nodeCheckBox, leafCheckBox, linkCheckBox;
  DemoFileMenu fileMenu;
  String initialString = "AGAAGACCTGA";

  public SuffixTreeDemo(String titleString, String helpPath) {
    setTitle(titleString);
    setHelpPath(helpPath);
    suffixTree = new UkkonenSuffixTree();
    suffixTree.setDebug(false);
    latex = new DrawSTLatex();
    textLabel = new JLabel(" Text ");
    textLabel.setHorizontalTextPosition(JLabel.CENTER);
    textLabel.setMinimumSize(new Dimension(textLabel.getHeight(),20));
    textField = new JTextField(initialString);
    textField.setToolTipText("Enter text and press RETURN.");
    TextListener textListener = new TextListener();
    textField.addActionListener(textListener);

    JToolBar toolBar = new JToolBar();
    JButton treeButton = new JButton(Icons.get1rightArrow());
    treeButton.setToolTipText("Construct suffix tree.");
    treeButton.addActionListener(textListener);
    JButton resetButton = new JButton(Icons.getUndo());
    resetButton.setToolTipText("Reset");
    resetButton.addActionListener(new ResetListener());
    toolBar.add(treeButton);
    toolBar.add(resetButton);
    toolBar.add(textField);

    textPanel = new JPanel(new BorderLayout());
    textPanel.add(toolBar,BorderLayout.NORTH);
    JPanel optionPanel = new JPanel();
    optionPanel.setLayout(new GridLayout(1,4));
    edgeCheckBox = new JCheckBox("Edge Labels", true);
    edgeCheckBox.addActionListener(textListener);
    edgeCheckBox.setToolTipText("Labels along edges.");
    leafCheckBox = new JCheckBox("Leaf Labels", true);
    leafCheckBox.addActionListener(textListener);
    leafCheckBox.setToolTipText("Start positions of suffices.");
    nodeCheckBox = new JCheckBox("String Depth", true);
    nodeCheckBox.addActionListener(textListener);
    nodeCheckBox.setToolTipText("Repeat lengths.");
    linkCheckBox = new JCheckBox("Suffix Links",false);
    linkCheckBox.addActionListener(textListener);
    linkCheckBox.setToolTipText("Construction Aid");
    optionPanel.add(leafCheckBox);
    optionPanel.add(edgeCheckBox);
    optionPanel.add(nodeCheckBox);
    optionPanel.add(linkCheckBox);
    textPanel.add(optionPanel,BorderLayout.SOUTH);
    treePanel = new DrawPanel();
    treePanel.setBackground(Color.RED);
    scrollPane = new JScrollPane(treePanel);
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(textPanel,BorderLayout.NORTH);
    this.getContentPane().add(scrollPane,BorderLayout.CENTER);
    // Initialize random number generator
    r = new Random();
    // Add printing to file Menu
    this.getFileMenu().getPrintMenuItem().setPrintable(treePanel);
  }

  class TextListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String string = textField.getText();
      string += "\000";
      char[] c = string.toCharArray();
      suffixTree.constructSuffixTree(c);
      if(edgeCheckBox.isSelected()) {
        treePanel.setEdgeLabel(true);
      } else {
        treePanel.setEdgeLabel(false);
      }
      if(leafCheckBox.isSelected()) {
        treePanel.setLeafLabel(true);
      } else {
        treePanel.setLeafLabel(false);
      }
      if(nodeCheckBox.isSelected()) {
        treePanel.setNodeLabel(true);
      } else {
        treePanel.setNodeLabel(false);
      }
      if(linkCheckBox.isSelected()) {
        treePanel.setSuffixLink(true);
      } else {
        treePanel.setSuffixLink(false);
      }
      treePanel.setTree(suffixTree);
//			latex.setNodeType(DrawSTLatex.CIRCLE_NODE);
//			latex.draw(suffixTree,"/home/haubold/suffixTree.tex");
    }
  }

  class ResetListener implements ActionListener {

    public void actionPerformed(ActionEvent arg0) {
      textField.setText(initialString);
      treePanel.setTree(null);
    }

  }

}
