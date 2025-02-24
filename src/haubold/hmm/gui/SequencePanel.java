package haubold.hmm.gui;

import haubold.hmm.algorithm.*;
import haubold.resources.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author haubold
 * Date: Nov 9, 2003; time: 2:59:42 PM.
 *
 * Description: GUI for generating a sequence of observations given
 * a hidden Markov model.
 */
public class SequencePanel extends PrintableJPanel {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private JToolBar toolBar;
  private JSlider itSlider;
  private JButton generateButton, viterbiButton, resetButton;
  private JSplitPane modelPane;
  private JSplitPane combinedPane;
  private ModelPanel modelPanel;
  private SliderPanel sliderPanel;
  private HmmPane hmmPane, viterbiPane;
  private JScrollPane hmmScrollPane, viterbiScrollPane;
  private HmmSequence hmmSequence;
  private Viterbi viterbi;
  private BirkhaeuserGUIComponents bgc;
  private SequenceUnit[] sequenceData;
  private JLabel lengthLabel;

  public SequencePanel() {
    LineNumber lineNumber1, lineNumber2;
    JPanel displayPanel;

    // Make the pane displaying the observed sequence
    bgc = new BirkhaeuserGUIComponents();
    hmmPane = new HmmPane();
    hmmPane.setToolTipText("True Hidden States");
    hmmScrollPane = new JScrollPane(hmmPane);
    lineNumber1 = new LineNumber(hmmPane);
    lineNumber1.setBackground(bgc.getColor1());
    hmmScrollPane.setRowHeaderView(lineNumber1);
    // Make the pane displaying the viterbi sequence
    viterbiPane = new HmmPane();
    viterbiPane.setPreferredSize(new Dimension());
    viterbiPane.setToolTipText("Reconstructed Hidden States");
    viterbiScrollPane = new JScrollPane(viterbiPane);
    lineNumber2 = new LineNumber(viterbiPane);
    lineNumber2.setBackground(bgc.getColor1());
    viterbiScrollPane.setRowHeaderView(lineNumber2);
    displayPanel = new JPanel();
    displayPanel.setLayout(new GridLayout(1, 2));
    displayPanel.add(hmmScrollPane);
    displayPanel.add(viterbiScrollPane);

    // Make model & corresponding control sliders
    sliderPanel = new SliderPanel();
    modelPanel = new ModelPanel(sliderPanel.getHmm());
    modelPanel.setBackground(bgc.getColor1());
    sliderPanel.setModelPanel(modelPanel);
    modelPane =
      new JSplitPane(
      JSplitPane.HORIZONTAL_SPLIT,
      modelPanel,
      sliderPanel);
    modelPane.setOneTouchExpandable(true);
    modelPane.setDividerLocation(300);
    // Include the text pane
    combinedPane =
      new JSplitPane(JSplitPane.VERTICAL_SPLIT, modelPane, displayPanel);
    combinedPane.setOneTouchExpandable(true);
    combinedPane.setDividerLocation(200);
    // Make toolbar
    toolBar = new JToolBar();
    generateButton = new JButton(Icons.get1rightArrow());
    generateButton.setToolTipText("Generate Sequence");
    generateButton.addActionListener(new GenerateActionListener());
    toolBar.add(generateButton);
    viterbiButton = new JButton(Icons.getQuestionMark());
    viterbiButton.setToolTipText("Estimate Hidden States");
    viterbiButton.addActionListener(new ViterbiActionListener());
    viterbiButton.setEnabled(false);
    toolBar.add(viterbiButton);
    resetButton = new JButton(Icons.getUndo());
    resetButton.setToolTipText("Reset");
    resetButton.setEnabled(true);
    ResetActionListener ral = new ResetActionListener();
    resetButton.addActionListener(ral);
    toolBar.add(resetButton);
    itSlider = bgc.getSlider(0, 5000, 2000, "Length of Observation Sequence");
    itSlider.addChangeListener(new LengthActionListener());
    toolBar.add(itSlider);
    lengthLabel = new JLabel(" " + itSlider.getValue());
    lengthLabel.setPreferredSize(new Dimension(40,15));
    toolBar.add(lengthLabel);
    // Put GUI together
    this.setLayout(new BorderLayout());
    this.add(toolBar, BorderLayout.NORTH);
    this.add(combinedPane, BorderLayout.CENTER);
    // Initialize algorithm objects
    hmmSequence = new HmmSequence();
    viterbi = new Viterbi();
  }

  public HiddenMarkovModel getHmm() {
    return sliderPanel.getHmm();
  }

  class GenerateActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      int it = itSlider.getValue();
      hmmSequence.setHmm(sliderPanel.getHmm());
      sequenceData = hmmSequence.generateHmmSequence(it);
      hmmPane.setSequenceData(sequenceData);
      viterbiPane.setText("");
      viterbiButton.setEnabled(true);
      resetButton.setEnabled(true);
    }
  }

  class ViterbiActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      viterbi.setHmm(sliderPanel.getHmm());
      viterbi.setSequence(sequenceData);
      sequenceData = viterbi.getViterbiSequence();
      viterbiPane.setSequenceData(sequenceData);
    }
  }

  class ResetActionListener implements ActionListener {
    public void actionPerformed(ActionEvent arg0) {
      sliderPanel.reset();
//			resetButton.setEnabled(false);
      viterbiButton.setEnabled(false);
      hmmPane.setText("");
      viterbiPane.setText("");
    }
  }

  class LengthActionListener implements ChangeListener {

    public void stateChanged(ChangeEvent e) {
      lengthLabel.setText(" " + itSlider.getValue());
    }

  }
}
