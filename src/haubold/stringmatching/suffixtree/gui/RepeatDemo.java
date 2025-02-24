package haubold.stringmatching.suffixtree.gui;

/**
 * @author Bernhard Haubold
 * Date: Feb 28, 2003; time: 9:56:34 AM.
 *
 * Description: Graphical interface for repeat finding. The
 * algorithmic core of the applet is a suffix tree.
 */

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.*;
import javax.swing.border.*;
import javax.swing.event.*;

import haubold.resources.util.*;
import haubold.resources.demo.*;
import haubold.stringmatching.suffixtree.algorithms.*;

public class RepeatDemo extends DemoFrame {
  private static final long serialVersionUID = 1L;
  BirkhaeuserGUIComponents bgc;
  JButton runButton;
  JCheckBox longestRepeatBox;
  JComboBox textCombo;
  JSlider lengthSlider;
  JLabel lengthLabel, maxLabel;
  JTextPane textPane;
  int initialLength = 5;
  StyledDocument document;
  SimpleAttributeSet defaultAttributes;
  SimpleAttributeSet selectedAttributes;
  DataContainer container;
  UkkonenSuffixTree suffixTree;
  String PHI = "Phi-X174 Genome";
  String ADH = "Drosophila Adh";
  String DNA = "Double Helix Paper";
  String BLANK = "Blank Page";
  Random random = new Random();

  public RepeatDemo(String titleString, String helpPath) {
    setTitle(titleString);
    setHelpPath(helpPath);
    // Construct RUN button.
    runButton = new JButton(Icons.get1rightArrow());
    runButton.setToolTipText("Search Repeats");
    RunListener runListener = new RunListener();
    runButton.addActionListener(runListener);
    // Construct check box.
    longestRepeatBox = new JCheckBox("Longest Repeat", false);
    CheckBoxListener checkBoxListener = new CheckBoxListener();
    longestRepeatBox.addActionListener(checkBoxListener);
    // Construct slider.
    bgc = new BirkhaeuserGUIComponents();
    lengthSlider = bgc.getSlider(2,20,initialLength,"Repeat Length");
    lengthLabel = new JLabel(String.valueOf(initialLength));
    lengthLabel.setPreferredSize(new Dimension(20, 10));
    lengthLabel.setHorizontalAlignment(JLabel.RIGHT);
    lengthLabel.setFont(new Font("monospaced",Font.BOLD,12));
    LengthListener lengthListener = new LengthListener();
    lengthSlider.addChangeListener(lengthListener);
    TitledBorder border = new TitledBorder("Repeat Length");
    lengthSlider.setBorder(border);
    // Construct combo box
    String[] textItems = { PHI, ADH, DNA, BLANK };
    textCombo = new JComboBox(textItems);
    textCombo.setMaximumSize(new Dimension(150,25));
    textCombo.setToolTipText("Text for searching");
    TextBoxListener textBoxListener = new TextBoxListener();
    textCombo.addActionListener(textBoxListener);
    maxLabel = new JLabel("; Length: ");
    maxLabel.setPreferredSize(new Dimension(80, 10));
    maxLabel.setEnabled(false);
    // Construct toolbar.
    JToolBar toolBar = new JToolBar();
    toolBar.add(runButton);
    toolBar.add(textCombo);
    toolBar.add(longestRepeatBox);
    toolBar.add(maxLabel);
    toolBar.add(lengthSlider);
    toolBar.add(lengthLabel);
    // Construct textPane and place it into scroll pane.
    StyleContext context = new StyleContext();
    document = new DefaultStyledDocument(context);
    defaultAttributes = new SimpleAttributeSet();
    selectedAttributes = new SimpleAttributeSet();
    StyleConstants.setFontFamily(defaultAttributes, "monospaced");
    StyleConstants.setFontSize(defaultAttributes, 12);
    StyleConstants.setFontFamily(selectedAttributes, "monospaced");
    StyleConstants.setFontSize(selectedAttributes, 12);
    StyleConstants.setBackground(
      selectedAttributes,
      new Color(255, 204, 51));
    textPane = new JTextPane(document);
    textPane.setEditable(true);
    textPane.setDoubleBuffered(true);
    JScrollPane scrollPane = new JScrollPane(textPane);
    // Extend file Menu
    OpenSequenceMenuItem osmi = new OpenSequenceMenuItem("Open Sequence", textPane);
    osmi.setMnemonic(KeyEvent.VK_O);
    osmi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
    this.getFileMenu().add(osmi,0);
    // Add textPane and toolbar to applet.
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(toolBar, BorderLayout.NORTH);
    this.getContentPane().add(scrollPane, BorderLayout.CENTER);
    // Create suffix tree.
    suffixTree = new UkkonenSuffixTree();
    // Create data container.
    container = new DataContainer();
    // Put text into text window.
    setText();
    // Set length slider value to longest repeat in text.
    String text = textPane.getText();
    suffixTree.constructSuffixTree((text + "\000").toCharArray());
    int length = suffixTree.getLongestRepeatLength();
    if(length > lengthSlider.getMaximum()) {
      lengthSlider.setMaximum(length+2);
    }
    lengthSlider.setValue(length);
  }

//	public static void main(String arg[]) {
//		JFrame frame = new JFrame("Repeats");
//		JApplet applet = new RepeatPanel();
//		applet.init();
//		frame.setSize(750, 500);
//		frame.getContentPane().add(applet, BorderLayout.CENTER);
//		frame.setVisible(true);
//	}

  private void setText() {
    String item = (String) textCombo.getSelectedItem();
    String text;
    maxLabel.setText("length: ");
    if (item.equals(ADH)) {
      text = container.getAdh2();
    } else if (item.equals(PHI)) {
      text = container.getPhi();
    } else if(item.equals(DNA)) {
      text = container.getWatsonCrick();
    } else {
      text = "";
    }
    try {
      document.remove(0, document.getLength());
      document.insertString(0, text, defaultAttributes);
      textPane.setCaretPosition(0);
    } catch (Exception e) {
      e.printStackTrace();
    }
    // Make sure slider is not out of range.
    suffixTree.constructSuffixTree((text + "\000").toCharArray());
    int length = suffixTree.getLongestRepeatLength();
    if(length > lengthSlider.getMaximum()) {
      lengthSlider.setMaximum(length+2);
    }
  }

  private void repeat() {
    String text = "";
    Repeat repeat;
    ArrayList repeatList = null;
    try {
      document.setCharacterAttributes(
        0,
        document.getLength(),
        defaultAttributes,
        true);
      text = document.getText(0, document.getLength());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    suffixTree.constructSuffixTree((text + "\000").toCharArray());
    if (longestRepeatBox.isSelected()) {
      repeatList = suffixTree.getLongestRepeat();
    } else {
      int length = lengthSlider.getValue();
      repeatList = suffixTree.findRepeat(length);
    }
    if (repeatList != null) {
      for (int i = 0; i < repeatList.size(); i++) {
        repeat = (Repeat) repeatList.get(i);
        if(longestRepeatBox.isSelected()) {
          maxLabel.setText("length: "
                           + String.valueOf(repeat.getLength()));
        }
        StyleConstants.setBackground(
          selectedAttributes,
          new Color(
            random.nextInt(235) + 20,
            random.nextInt(235) + 20,
            random.nextInt(235) + 20));
        for (int j = 0; j < repeat.size(); j++) {
          document.setCharacterAttributes(
            ((Integer) repeat.get(j)).intValue(),
            repeat.getLength(),
            selectedAttributes,
            true);
        }
      }
    }
  }

  class LengthListener implements ChangeListener {
    public void stateChanged(ChangeEvent e) {
      int l = lengthSlider.getValue();
      lengthLabel.setText(String.valueOf(l));
    }
  }

  class TextBoxListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      setText();
    }
  }

  class CheckBoxListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (longestRepeatBox.isSelected()) {
        lengthSlider.setEnabled(false);
        lengthLabel.setEnabled(false);
        maxLabel.setEnabled(true);
      } else {
        lengthSlider.setEnabled(true);
        lengthLabel.setEnabled(true);
        maxLabel.setText("length: ");
        maxLabel.setEnabled(false);
      }
    }
  }

  class RunListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      repeat();
    }
  }

}
