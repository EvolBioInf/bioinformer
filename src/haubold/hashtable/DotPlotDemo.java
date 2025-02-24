package haubold.hashtable;

/**
 * @author Bernhard Haubold
 * Date: Mar 7, 2003; time: 5:59:46 PM.
 *
 * Description:
 */
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import haubold.resources.util.*;
import haubold.resources.demo.*;
import haubold.sequencetools.*;

public class DotPlotDemo extends DemoFrame {
  private static final long serialVersionUID = 1L;
  JTextPane textPane1, textPane2;
  JScrollPane scrollPane1, scrollPane2;
  JSlider slider;
  JLabel lengthLabel;
  JButton dotButton, resetButton, randomizeButton;
  JToolBar toolBar;
  JPanel controlPanel, textPanel;
  DotPlot plotPanel;
  DataContainer dataContainer;
  String initialText1, initialText2;
  JSplitPane splitPane = new JSplitPane();
  int initialWordLength = 11;
  BirkhaeuserGUIComponents bgc;
  Random random;
  SequenceTools sequenceTools;
  PrintMenuItem pmi;
  StringBuffer header1, header2;

  public DotPlotDemo(String titleString, String helpPath) {
    setTitle(titleString);
    setHelpPath(helpPath);
    DefaultBoundedRangeModel brm;
    int i;
    // Construct controlPanel
    controlPanel = new JPanel();
    controlPanel.setLayout(new BorderLayout());
    textPanel = new JPanel();
    textPanel.setLayout(new GridLayout(2,1));
    // Construct toolBar
    toolBar = new JToolBar();
    bgc = new BirkhaeuserGUIComponents();

    brm = new DefaultBoundedRangeModel(initialWordLength,0,1,40);
    slider = new JSlider(brm);
    slider.setMajorTickSpacing(10);
//		slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    slider.setSnapToTicks(false);
    for(i=1; i<=4; i++) {
      slider.getLabelTable().remove(Integer.valueOf(i*10+1));
      slider.getLabelTable().put(Integer.valueOf(i*10),new JLabel(Integer.valueOf(i*10).toString(),JLabel.CENTER));
    }
    slider.setLabelTable(slider.getLabelTable());
    slider.setBorder(new TitledBorder("Match Length"));

    LengthListener lengthListener = new LengthListener();
    slider.addChangeListener(lengthListener);
    lengthLabel = new JLabel();
    lengthLabel.setPreferredSize(new Dimension(30,30));
    lengthLabel.setHorizontalTextPosition(JLabel.RIGHT);
    lengthLabel.setText(" " + String.valueOf(slider.getValue()));
    dotButton = new JButton(Icons.get1rightArrow());
    dotButton.setToolTipText("Dot");
    HashActionListener hashActionListener = new HashActionListener();
    dotButton.addActionListener(hashActionListener);
    resetButton = new JButton(Icons.getUndo());
    resetButton.setToolTipText("Reset");
    ResetActionListener resetActionListener = new ResetActionListener();
    resetButton.addActionListener(resetActionListener);
    randomizeButton = new JButton(Icons.getDie());
    randomizeButton.setToolTipText("Randomize Sequence");
    RandomActionListener randomActionListener = new RandomActionListener();
    randomizeButton.addActionListener(randomActionListener);
    toolBar.add(dotButton);
    toolBar.add(randomizeButton);
    toolBar.add(resetButton);
    toolBar.add(slider);
    toolBar.add(lengthLabel);
    // Construct textPanes.
    // Get initial sequences.
    dataContainer = new DataContainer();
    initialText1 = dataContainer.getAdh();
    initialText2 = dataContainer.getAdh2();
    textPane1 = new JTextPane();
    textPane2 = new JTextPane();
    scrollPane1 = new JScrollPane(textPane1);
    scrollPane2 = new JScrollPane(textPane2);
    TitledBorder scrollBorder1 = new TitledBorder("Sequence 1");
    TitledBorder scrollBorder2 = new TitledBorder("Sequence 2");
    scrollPane1.setBorder(scrollBorder1);
    scrollPane2.setBorder(scrollBorder2);
    scrollPane1.setPreferredSize(new Dimension(200,100));
    scrollPane2.setPreferredSize(new Dimension(200,100));
    Font font = new Font("monospaced",Font.PLAIN,12);
    textPane1.setFont(font);
    textPane2.setFont(font);
    textPane1.setText(initialText1);
    textPane2.setText(initialText2);
    textPane1.setCaretPosition(0);
    textPane2.setCaretPosition(0);
    // Create plotPanel.
    plotPanel = new DotPlot();
    // Extend file Menu
    DemoFileMenu fileMenu = getFileMenu();
    JMenuItem exitMenuItem = fileMenu.getExitMenuItem();
    OpenSequenceMenuItem osmi1 = new OpenSequenceMenuItem("Open Sequence 1", textPane1);
    osmi1.setMnemonic(KeyEvent.VK_1);
    osmi1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,ActionEvent.CTRL_MASK));
    fileMenu.add(osmi1);
    OpenSequenceMenuItem osmi2 = new OpenSequenceMenuItem("Open Sequence 2", textPane2);
    osmi2.setMnemonic(KeyEvent.VK_2);
    osmi2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,ActionEvent.CTRL_MASK));
    fileMenu.add(osmi2);
    pmi = this.getFileMenu().getPrintMenuItem();
    pmi.setPrintable(plotPanel);
    fileMenu.add(pmi);
    fileMenu.add(exitMenuItem);
    setFileMenu(fileMenu);
    // Put sequences into textPanes and construct textPanel.
    textPanel.add(scrollPane1,BorderLayout.NORTH);
    textPanel.add(scrollPane2,BorderLayout.SOUTH);
    // Put the controlPanel together
    controlPanel.add(toolBar,BorderLayout.NORTH);
    controlPanel.add(textPanel,BorderLayout.CENTER);
    // Put GUI together
    splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    splitPane.setOneTouchExpandable(true);
    splitPane.add(controlPanel);
    splitPane.add(plotPanel);
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(splitPane,BorderLayout.CENTER);
    // Initialize random number generator.
    random = new Random();
    sequenceTools = new SequenceTools();
    header1 = new StringBuffer();
    header2 = new StringBuffer();
  }

  class ResetActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      textPane1.setText(initialText1);
      textPane2.setText(initialText2);
      textPane1.setCaretPosition(0);
      textPane2.setCaretPosition(0);
      slider.setValue(initialWordLength);
      plotPanel.reset();
    }
  }

  class HashActionListener implements ActionListener {
    ArrayList list;
    String key;
    String position = "";
    public void actionPerformed(ActionEvent e) {
      int i;
      String seq1 = textPane1.getText();
      String seq2 = textPane2.getText();
      if(seq1.charAt(0) == '>')
        for(i=1; i<seq1.length(); i++)
          if(seq1.charAt(i) == '\n') {
            seq1 = seq1.substring(i+1);
            break;
          }

      if(seq2.charAt(0) == '>')
        for(i=1; i<seq2.length(); i++)
          if(seq2.charAt(i) == '\n') {
            seq2 = seq2.substring(i+1);
            break;
          }
      int l = slider.getValue();
      plotPanel.setWordLength(l);
      plotPanel.setSequences(seq1.toUpperCase(),seq2.toUpperCase());
    }
  }

  class RandomActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String seq1,seq2;
      int i;

      seq1 = textPane1.getText();
      seq2 = textPane2.getText();
      header1.replace(0, header1.length(), "");
      header2.replace(0, header2.length(), "");
      if(seq1.charAt(0) == '>')
        for(i=0; i<seq1.length(); i++)
          if(seq1.charAt(i) == '\n') {
            seq1 = seq1.substring(i+1);
            break;
          } else
            header1.append(seq1.charAt(i));

      if(seq2.charAt(0) == '>')
        for(i=0; i<seq2.length(); i++)
          if(seq2.charAt(i) == '\n') {
            seq2 = seq2.substring(i+1);
            break;
          } else
            header2.append(seq2.charAt(i));
      header1.append("\n");
      header1.append(sequenceTools.randomizeSequence(seq1));
      header2.append("\n");
      header2.append(sequenceTools.randomizeSequence(seq2));
      textPane1.setText(header1.toString());
      textPane1.setCaretPosition(0);
      textPane2.setText(header2.toString());
      textPane2.setCaretPosition(0);
    }
  }

  class LengthListener implements ChangeListener {
    public void stateChanged(ChangeEvent e) {
      int l = slider.getValue();
      lengthLabel.setText(" " + String.valueOf(l));
    }
  }
}
