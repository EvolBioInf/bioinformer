package haubold.stringmatching.classical;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.*;

import haubold.resources.util.*;
import haubold.resources.demo.*;
import haubold.stringmatching.suffixtree.algorithms.*;

public class StringMatchingDemo extends DemoFrame {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  JLabel timeLabel, preprocessLabel, patternLabel;
  JTextPane textPane;
  JTextField patternField;
  JComboBox textBox;
  JComboBox  algorithmBox;
  StyledDocument document;
  DataContainer container;
  SimpleAttributeSet defaultAttributes;
  SimpleAttributeSet selectedAttributes;
  NaiveAlgorithm naiveAlgorithm;
  ZAlgorithm zAlgorithm;
  UkkonenSuffixTree suffixTree;
  String initPattern = "ACGT";
  String NAIVE = "Naive";
  String Z = "Z";
  String SUFFIX = "Suffix Tree";
  String HAEM = "Haemophilus Genome";
  String PHI = "Phi-X174 Genome";
  String ADH = "Drosophila Adh";
  String DNA = "Double Helix Paper";
  String BLANK = "Blank Page";
  String firstTextBoxItem;
  String firstAlgorithmBoxItem;
  public StringMatchingDemo(String titleString, String helpPath) {
    setTitle(titleString);
    setHelpPath(helpPath);
    container = new DataContainer();
    JPanel stringPanel = new JPanel(new BorderLayout());
    JPanel patternPanel = new JPanel(new BorderLayout());
    JToolBar toolBar = new JToolBar();
    JButton matchButton = new JButton(Icons.get1rightArrow());
    matchButton.setToolTipText("Match");
    MatchButtonListener buttonListener = new MatchButtonListener();
    JButton resetButton = new JButton(Icons.getUndo());
    resetButton.setToolTipText("Reset");
    ResetActionListener resetActionListener = new ResetActionListener();
    resetButton.addActionListener(resetActionListener);
    matchButton.addActionListener(buttonListener);
    String algorithmElements[] = {NAIVE,Z,SUFFIX};
    String textElements[] = {PHI,ADH,DNA,BLANK};
    firstTextBoxItem = PHI;
    firstAlgorithmBoxItem = NAIVE;
    algorithmBox = new JComboBox(algorithmElements);
    algorithmBox.setToolTipText("Algorithm");
    textBox = new JComboBox(textElements);
    textBox.setToolTipText("Text for searching");
    TextBoxListener textBoxListener = new TextBoxListener();
    textBox.addActionListener(textBoxListener);
    timeLabel = new JLabel(" Search:        ");
    timeLabel.setToolTipText("Time (in milliseconds) "
                             + "for searching.");
    preprocessLabel = new JLabel(" Preprocess:     ");
    preprocessLabel.setToolTipText("Time (in milliseconds) "
                                   + "for preprocessing");
    timeLabel.setEnabled(false);
    preprocessLabel.setEnabled(false);
    toolBar.add(matchButton);
    toolBar.add(resetButton);
    toolBar.add(algorithmBox);
    toolBar.add(textBox);
    toolBar.add(timeLabel);
    toolBar.add(preprocessLabel);
    patternLabel = new JLabel(" Pattern ");
    patternField = new JTextField(initPattern);
    patternField.setToolTipText("Enter pattern and press RETURN");
    PatternFieldListener patternFieldListener = new PatternFieldListener();
    MatchButtonListener matchButtonListener = new MatchButtonListener();
    patternField.addActionListener(patternFieldListener);
    matchButton.addActionListener(matchButtonListener);
    patternPanel.add(patternLabel,BorderLayout.WEST);
    patternPanel.add(patternField,BorderLayout.CENTER);
//        patternLabel.setPreferredSize(labelDimension);
    stringPanel.add(toolBar,BorderLayout.NORTH);
    stringPanel.add(patternPanel,BorderLayout.SOUTH);

    // construct textPane
    StyleContext context = new StyleContext();
    document = new DefaultStyledDocument(context);
    defaultAttributes = new SimpleAttributeSet();
    selectedAttributes = new SimpleAttributeSet();
    StyleConstants.setFontFamily(defaultAttributes,"monospaced");
    StyleConstants.setFontSize(defaultAttributes,12);
    StyleConstants.setFontFamily(selectedAttributes,"monospaced");
    StyleConstants.setFontSize(selectedAttributes,12);
    StyleConstants.setBackground(selectedAttributes, new Color(255,204,51));
    textPane = new JTextPane(document);
    textPane.setEditable(true);
    textPane.setDoubleBuffered(true);
    textPane.setFont(new Font("monospaced",Font.PLAIN,12));
    // Add open item to file Menu
    OpenSequenceMenuItem osmi = new OpenSequenceMenuItem("Open Sequence", textPane);
    osmi.setMnemonic(KeyEvent.VK_O);
    osmi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
    this.getFileMenu().add(osmi,0);
    // create algorithm objects
    suffixTree = new UkkonenSuffixTree();
    naiveAlgorithm = new NaiveAlgorithm();
    zAlgorithm = new ZAlgorithm();

    // put textPane into scrollPane
    JScrollPane scrollPane = new JScrollPane(textPane);
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(stringPanel,BorderLayout.NORTH);
    this.getContentPane().add(scrollPane,BorderLayout.CENTER);
    timeLabel.setPreferredSize(new Dimension(100,20));
    preprocessLabel.setPreferredSize(new Dimension(140,20));
    setText();
  }

  class StringListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      System.out.println("Action performed");
    }
  }

  class ResetActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      timeLabel.setText(" Search:        ");
      timeLabel.setEnabled(false);
      preprocessLabel.setText(" Preprocess:     ");
      preprocessLabel.setEnabled(false);
      textBox.setSelectedIndex(0);
      algorithmBox.setSelectedIndex(0);
      setText();
      patternField.setText(initPattern);
    }
  }

  private void setText() {
    String item  = (String) textBox.getSelectedItem();
    String text;
    if(item.equals(HAEM)) {
      text = "Not available.";
    } else if(item.equals(ADH)) {
      text = container.getAdh2();
    } else if(item.equals(PHI)) {
      text = container.getPhi();
    } else if (item.equals(DNA)) {
      text = container.getWatsonCrick();
    } else {
      text = "";
    }
    try {
      document.remove(0,document.getLength());
      document.insertString(0,text,defaultAttributes);
      textPane.setCaretPosition(0);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void search() {
    ArrayList list = null;
    String algorithm = (String)algorithmBox.getSelectedItem();
    String pattern = patternField.getText();
    String text = "";
    long t1 = 0;
    long t2 = 0;
    try {
      document.setCharacterAttributes(0,document.getLength(),defaultAttributes,true);
      text = document.getText(0,document.getLength());
    } catch(Exception ex) {
      ex.printStackTrace();
    }
    if(algorithm.equals(NAIVE) && pattern.length() > 0 && text.length() > 0) {
      t1 = System.currentTimeMillis();
      list = naiveAlgorithm.match(pattern,text);
      t2 = System.currentTimeMillis();
      preprocessLabel.setEnabled(false);
      preprocessLabel.setText(" Preprocess: ");
    } else if(algorithm.equals(Z) && pattern.length() > 0 && text.length() > 0) {
      t1 = System.currentTimeMillis();
      zAlgorithm.preprocess(pattern,text);
      t2 = System.currentTimeMillis();
      preprocessLabel.setEnabled(true);
      preprocessLabel.setText(" Preprocess: " + (t2-t1) + " ms.");
      t1 = System.currentTimeMillis();
      list = zAlgorithm.match(pattern,text);
      t2 = System.currentTimeMillis();
    } else if (algorithm.equals(SUFFIX) && pattern.length() > 0 && text.length() > 0) {
      t1 = System.currentTimeMillis();
      suffixTree.constructSuffixTree((text+"\000").toCharArray());
      t2 = System.currentTimeMillis();
      preprocessLabel.setEnabled(true);
      preprocessLabel.setText(" Preprocess: " + (t2-t1) + " ms.");
      t1 = System.currentTimeMillis();
      list = suffixTree.match(pattern.toCharArray());
      t2 = System.currentTimeMillis();

    }
    timeLabel.setEnabled(true);
    timeLabel.setText(" Search: " + (t2-t1) + " ms.");
    if(list != null) {
      for(int i=0; i<list.size(); i++) {
        document.setCharacterAttributes(((Integer)list.get(i)).intValue(),pattern.length(),selectedAttributes,true);
      }
    } else {
      System.out.println("List is null");
    }
  }

  class TextBoxListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      setText();
    }
  }

  class MatchButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      search();
    }
  }

  class PatternFieldListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      search();
    }
  }
}