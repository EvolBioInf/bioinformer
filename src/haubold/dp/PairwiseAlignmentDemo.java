package haubold.dp;
import haubold.resources.util.*;
import haubold.resources.demo.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
/**
 * This applet demonstrates the principle of paiwise alignment by dynamic programming.
 *
 * @version 0.1, September 21, 2002
 * @since 0.0, July 12, 2002
 * @author Bernhard Haubold
 */

public class PairwiseAlignmentDemo extends DemoFrame {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  JTextField seqField1, seqField2;
  JTextField gapOpeningField, gapExtensionField;
  JTextField alignmentField1, alignmentField2;
  JToolBar toolBar;
  JComboBox alignmentBox;
  JButton resetButton, stepButton;
  GapModel gapModel;
  JLabel gapCostLabel, plusLabel, gapLengthLabel, seqLabel1, seqLabel2;
  JLabel scoreLabel;
  SubstitutionMatrix sMatrix;
  static JFrame appletFrame;
  DynamicProgramming dp;
  String[] alignment = new String[3];
  String initialSeq1, initialSeq2;
  JTextArea textArea;
  JScrollPane textPane;
  JSlider matchSlider, mismatchSlider, gapSlider;
  JPanel textPanel,
         sequencePanel1,
         sequencePanel2,
         sequencePanel,
         controlPanel,
         sliderPanel,
         alignmentPanel;
  PrintableJPanel displayPanel;
  int step;
  Font font10, font12;
  boolean global = false;
  boolean local = false;
  boolean overlap = false;
  JLabel[][] alignmentField;
  Border border;
  int initialMismatch = -3;
  int initialMatch = 4;
  int initialGap = -5;
  public PairwiseAlignmentDemo(String titleString, String helpPath) {
    setTitle(titleString);
    setHelpPath(helpPath);
    // Set up elements of GUI
    this.setTitle("Pairwise Alignment");
    // Construct Panel for entering sequences
    font12 = new Font("CourierNew", Font.PLAIN, 12);
    font10 = new Font("CourierNew", Font.PLAIN, 10);
    seqLabel1 = new JLabel(" Sequence 1 ");
    seqLabel2 = new JLabel(" Sequence 2 ");
    initialSeq1 = "ACCGTT";
    initialSeq2 = "AGTTAC";
    seqField1 = new JTextField(initialSeq1, 40);
    seqField2 = new JTextField(initialSeq2, 40);
    seqField1.setFont(font12);
    seqField2.setFont(font12);
    sequencePanel1 = new JPanel(new BorderLayout());
    sequencePanel2 = new JPanel(new BorderLayout());
    sequencePanel = new JPanel(new BorderLayout());
    sequencePanel1.add(seqLabel1, BorderLayout.WEST);
    sequencePanel1.add(seqField1, BorderLayout.CENTER);
    sequencePanel2.add(seqLabel2, BorderLayout.WEST);
    sequencePanel2.add(seqField2, BorderLayout.CENTER);
    sequencePanel.add(sequencePanel1, BorderLayout.NORTH);
    sequencePanel.add(sequencePanel2, BorderLayout.SOUTH);
    // Panel for entering afine gap cost
    gapModel = new GapModel();
    // ComboBox
    String[] str1 = {"Global Alignment Algorithm",
                     "Local Alignment Algorithm",
                     "Overlap Alignment Algorithm"
                    };
    alignmentBox = new JComboBox(str1);
    alignmentBox.setForeground(Color.black);
    // Buttons
    stepButton = new JButton(Icons.get2rightArrow());
    stepButton.setToolTipText("Step 1: setup matrix");
    stepButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        stepAction();
      }
    });
    resetButton = new JButton(Icons.getUndo());
    resetButton.setToolTipText("Reset");
    resetButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        resetAction();
      }
    });
    // Substitution matrix
    sMatrix = new SubstitutionMatrix();
    sMatrix.setToolTipText("Substitution Matrix");
    // Tool bar
    BirkhaeuserGUIComponents bgc = new BirkhaeuserGUIComponents();
    toolBar = new JToolBar();
    toolBar.add(stepButton);
    toolBar.add(resetButton);
    toolBar.add(alignmentBox);
    // Slider panel
    sliderPanel = new JPanel(new GridLayout(1,3));
    matchSlider = bgc.getSlider(-5,5,initialMatch,"Match Score");
    matchSlider.setMinorTickSpacing(1);
    matchSlider.setSnapToTicks(true);
    mismatchSlider = bgc.getSlider(-5,5,initialMismatch,"Mismatch Score");
    mismatchSlider.setMinorTickSpacing(1);
    mismatchSlider.setSnapToTicks(true);
    gapSlider = bgc.getSlider(-5,5,initialGap,"Gap Extension");
    gapSlider.setMinorTickSpacing(1);
    gapSlider.setSnapToTicks(true);
    sliderPanel.add(matchSlider);
    sliderPanel.add(mismatchSlider);
    sliderPanel.add(gapSlider);
    // Put slider panel and sequence panel into input panel
    JPanel inputPanel = new JPanel(new BorderLayout());
    inputPanel.add(toolBar,BorderLayout.NORTH);
    inputPanel.add(sliderPanel,BorderLayout.CENTER);
    inputPanel.add(sequencePanel,BorderLayout.SOUTH);
    alignmentPanel = new JPanel(new BorderLayout());
    scoreLabel = new JLabel(" Score = ");
    scoreLabel.setFont(font12);
    scoreLabel.setOpaque(true);
    scoreLabel.setBackground(bgc.getColor1());
    scoreLabel.setForeground(Color.black);
    // Construct text panel, which contains the alignment in textual form
    JPanel textPanel = new JPanel();
    textPanel.setLayout(new GridLayout(3, 100));
    textPanel.setBackground(bgc.getColor2());
    textPanel.setForeground(Color.blue);
    alignmentField = new JLabel[3][50];
    for (int j = 0; j < 3; j++) {
      for (int i = 0; i < alignmentField[0].length; i++) {
        alignmentField[j][i] = new JLabel(" ");
        alignmentField[j][i].setHorizontalAlignment(
          SwingConstants.CENTER);
        textPanel.add(alignmentField[j][i]);
        textPanel.setBorder(border);
      }
    }
    alignmentPanel.add(textPanel, BorderLayout.NORTH);
    alignmentPanel.add(scoreLabel, BorderLayout.SOUTH);
    dp = new DynamicProgramming(initialSeq1, initialSeq2);
    dp.setPreferredSize(new Dimension(200,200));
    step = 0;
    stepAction();
    displayPanel = new PrintableJPanel();
    displayPanel.setLayout(new BorderLayout());
    this.getContentPane().setLayout(new BorderLayout());
    displayPanel.add(inputPanel,BorderLayout.NORTH);
    displayPanel.add(dp,BorderLayout.CENTER);
    displayPanel.add(alignmentPanel,BorderLayout.SOUTH);
    this.getContentPane().add(displayPanel,BorderLayout.CENTER);
    // Set printable for print menu item
    this.getFileMenu().getPrintMenuItem().setPrintable(displayPanel);
  }

  private void resetAction() {
    seqField1.setText(initialSeq1);
    seqField2.setText(initialSeq2);
    gapModel.setGapOpening(0.0);
    gapModel.setGapExtension(-1.0);
    alignmentBox.setSelectedIndex(0);
    matchSlider.setValue(initialMatch);
    mismatchSlider.setValue(initialMismatch);
    gapSlider.setValue(initialGap);
    sMatrix.reset();
    step = 0;
    stepAction();
  }

  private void stepAction() {
    if (step == 0) {
      dp.setSequences(seqField1.getText(), seqField2.getText());
      stepButton.setToolTipText("Step 1: setup matrix");
      scoreLabel.setText(" ");
      for (int j = 0; j < 3; j++) {
        for (int i = 0; i < alignmentField[0].length; i++) {
          alignmentField[j][i].setText(" ");
        }
      }
      seqField1.setEditable(true);
      seqField2.setEditable(true);
      alignmentBox.setEnabled(true);
      matchSlider.setEnabled(true);
      mismatchSlider.setEnabled(true);
      gapSlider.setEnabled(true);
      step++;
      dp.setAlignmentMode(dp.CLEAR_MATRIX);
      dp.repaint();
    } else if (step == 1) {
      global = false;
      local = false;
      overlap = false;
      String message = "ok";
      if (message.equals("ok")) {
        dp.setSequences(seqField1.getText(), seqField2.getText());
      } else {
        JOptionPane optionPane =
          new JOptionPane(message, JOptionPane.ERROR_MESSAGE);
        JDialog dialog =
          optionPane.createDialog(optionPane, "Error in Sequence");
//				dialog.show();
        dialog.setVisible(true);
        return;
      }
      dp.setMatchScore(matchSlider.getValue());
      dp.setMismatchScore(mismatchSlider.getValue());
      dp.setGapExtension(gapSlider.getValue());
      stepButton.setToolTipText("Step 2: initialize matrix");
      step++;
      matchSlider.setEnabled(false);
      mismatchSlider.setEnabled(false);
      gapSlider.setEnabled(false);
      seqField1.setEditable(false);
      seqField2.setEditable(false);
      alignmentBox.setEnabled(false);
      if (alignmentBox.getSelectedItem().toString().equals("Global Alignment Algorithm")) {
        global = true;
      } else if (
        alignmentBox.getSelectedItem().toString().equals("Overlap Alignment Algorithm")) {
        overlap = true;
      } else if (
        alignmentBox.getSelectedItem().toString().equals("Local Alignment Algorithm")) {
        local = true;
      }
      if (global) {
        dp.initializeGlobalAlignment();
        dp.fillGlobalAlignment();
        alignment = dp.traceBackGlobalAlignment();
      } else if (overlap) {
        dp.initializeOverlapAlignment();
        dp.fillOverlapAlignment();
        alignment = dp.traceBackOverlapAlignment();
      } else if (local) {
        dp.initializeLocalAlignment();
        dp.fillLocalAlignment();
        alignment = dp.traceBackLocalAlignment();
      }
      dp.setAlignmentMode(dp.SETUP_MATRIX);
      dp.repaint();
    } else if (step == 2) {
      dp.setAlignmentMode(dp.INITIALIZE_MATRIX);
      dp.repaint();
      stepButton.setToolTipText("Step 3: fill matrix      ");
      step++;
    } else if (step == 3) {
      stepButton.setToolTipText("Step 4: trace back       ");
      dp.setAlignmentMode(dp.FILL_MATRIX);
      dp.repaint();
      step++;
    } else if (step == 4) {
      for (int i = 0; i < 3; i++) {
        for (int j = 0;
             j
             < Math.min(
               alignment[i].length(),
               alignmentField[0].length);
             j++) {
          alignmentField[i][j].setText(
            alignment[i].substring(j, j + 1));
        }
      }
      stepButton.setToolTipText("Step 5: clear matrix     ");
      if (global) {
        scoreLabel.setText(
          "Score = "
          + String.valueOf(dp.optimalScore)
          + "; Number of cooptimal alignments = "
          + String.valueOf(dp.numCooptimal));
      } else {
        scoreLabel.setText(
          "Score = " + String.valueOf(dp.optimalScore));
      }
      dp.setAlignmentMode(dp.TRACE_BACK);
      dp.repaint();
      step = 0;
    } else {
      global = false;
      ;
      local = false;
      overlap = false;
      step = 0;
    }
  }

  String checkSequences() {
    String seq1 = seqField1.getText();
    String seq2 = seqField2.getText();
    String s, message;
    int i;

    seq1 = seq1.toUpperCase();
    seq2 = seq2.toUpperCase();

    // check sequence 1
    for (i = 0; i < seq1.length(); i++) {
      s = seq1.substring(i, i + 1);
      if (!(s.equals("A")
            || s.equals("C")
            || s.equals("T")
            || s.equals("G"))) {
        i++;
        message =
          "Sequence 1 contains an error at position "
          + String.valueOf(i);
        return message;
      }
    }

    // check sequence 2
    for (i = 0; i < seq2.length(); i++) {
      s = seq2.substring(i, i + 1);
      if (!(s.equals("A")
            || s.equals("C")
            || s.equals("T")
            || s.equals("G"))) {
        i++;
        message =
          "Sequence 2 contains an error at position "
          + String.valueOf(i);
        return message;
      }
    }
    message = "ok";
    return message;
  }
}
