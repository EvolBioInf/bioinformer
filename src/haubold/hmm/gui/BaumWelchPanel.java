package haubold.hmm.gui;
import java.awt.*;
import java.awt.event.*;

import haubold.hmm.algorithm.*;
import haubold.resources.util.*;
import javax.swing.*;
/**
 * @author haubold
 * Date: Nov 24, 2003; time: 5:27:36 PM.
 *
 * Description: Panel to demonstrate the estimation of
 * a hidden Markov model using the Baum-Welch algorithm.
 * Reference: Rabiner (1998). A tutorial on hidden Markov models and
 * selected applications in speech recognition. Proceedings of the IEEE,
 * 77:257-286.
 */
public class BaumWelchPanel extends PrintableJPanel {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private JScrollPane scrollPane;
  private JSplitPane splitPane;
  private JPanel modelPanel;
  private HiddenMarkovModel trueModel, initialModel, newModel;
  private ModelPanel trueModelPanel, initialModelPanel, newModelPanel;
  private HmmPane hmmPane;
  private BirkhaeuserGUIComponents bgc;
  private JToolBar toolBar;
  private JButton generateDataButton, estimateModelButton, initialModelButton;
  private JSlider numObsSlider;
  private int[] obsSeq;
  private BaumWelchThread baumWelchTread;
  private TimerThread timerThread;

  BaumWelchPanel(HiddenMarkovModel trueModel) {
    this.trueModel = trueModel;
    bgc = new BirkhaeuserGUIComponents();
    // Generate the two missing hidden Markov models
    initialModel = new RandomHmm(trueModel.getNumStates(),
                                 trueModel.getNumObservationSymbols());
    newModel = initialModel.copy();
    // Generate the three panels representing the hidden Markov models
    trueModelPanel = new ModelPanel(trueModel);
    initialModelPanel = new ModelPanel(initialModel);
    newModelPanel = new ModelPanel(newModel);
    trueModelPanel.setBackground(bgc.getColor1());
    initialModelPanel.setBackground(bgc.getColor3().brighter());
    newModelPanel.setBackground(Color.WHITE);
    // Add ToolTips to the hidden Markov panels
    trueModelPanel.setToolTipText("True Model");
    initialModelPanel.setToolTipText("Initial Model");
    newModelPanel.setToolTipText("New Model");
    // Add the three panels representing hidden Markov Models to the modelPanel
    modelPanel = new JPanel(new GridLayout(1, 3));
    modelPanel.add(trueModelPanel);
    modelPanel.add(initialModelPanel);
    modelPanel.add(newModelPanel);
    modelPanel.setMinimumSize(new Dimension(100,100));
    modelPanel.repaint();
    // Construct splitPane
    hmmPane = new HmmPane();
    scrollPane = new JScrollPane(hmmPane);
    splitPane =
      new JSplitPane(JSplitPane.VERTICAL_SPLIT, modelPanel, scrollPane);
    splitPane.setOneTouchExpandable(true);
    splitPane.setDividerLocation(-1);
    this.setLayout(new BorderLayout());
    this.add(splitPane, BorderLayout.CENTER);
    // Construct Toolbar
    toolBar = new JToolBar();
    generateDataButton = new JButton(Icons.get1rightArrow());
    generateDataButton.setToolTipText("Generate Data");
    initialModelButton = new JButton(Icons.getDie());
    initialModelButton.setToolTipText("Generate Random Initial Model");
    estimateModelButton = new JButton(Icons.getQuestionMark());
    estimateModelButton.setToolTipText("Estimate Hiden Markov Model");
    estimateModelButton.setEnabled(false);
    GenerateActionListener gal = new GenerateActionListener();
    EstimateActionListener eal = new EstimateActionListener();
    InitializeActionListener ial = new InitializeActionListener();
    generateDataButton.addActionListener(gal);
    estimateModelButton.addActionListener(eal);
    initialModelButton.addActionListener(ial);
    numObsSlider = bgc.getSlider(0,5000,2000,"Length Of Observation Sequence");
    toolBar.add(generateDataButton);
    toolBar.add(initialModelButton);
    toolBar.add(estimateModelButton);
    toolBar.add(numObsSlider);
    this.add(toolBar,BorderLayout.NORTH);
  }

  class GenerateActionListener implements ActionListener {
    HmmSequence hmmSequence = new HmmSequence();
    public void actionPerformed(ActionEvent e) {
      int i;
      int it = numObsSlider.getValue();
      hmmSequence.setHmm(trueModelPanel.getHmm());
      SequenceUnit[] sequenceData = hmmSequence.generateHmmSequence(it);
      obsSeq = new int[sequenceData.length];
      for(i=0; i<sequenceData.length; i++) {
        obsSeq[i]=sequenceData[i].getObservedState();
      }
      hmmPane.setSequenceData(sequenceData);
      estimateModelButton.setEnabled(true);
    }
  }

  class EstimateActionListener implements ActionListener {
    BaumWelch bw = new BaumWelch();
    HiddenMarkovModel im;
    public void actionPerformed(ActionEvent arg0) {
      HiddenMarkovModel im = initialModelPanel.getHmm();
      bw.setMaxIt(10000);
      bw.setObsSeq(obsSeq);
      bw.setHmm(im.copy());
      baumWelchTread = new BaumWelchThread(bw,newModelPanel,estimateModelButton);
      timerThread = new TimerThread(newModelPanel,baumWelchTread);
      baumWelchTread.start();
      timerThread.start();
    }
  }

  class InitializeActionListener implements ActionListener {
    HiddenMarkovModel im;
    public void actionPerformed(ActionEvent arg0) {
      im = new RandomHmm(trueModel.getNumStates(),
                         trueModel.getNumObservationSymbols());
      initialModelPanel.setHmm(im);
      newModelPanel.setHmm(im.copy());
    }

  }

  public JSplitPane getSplitPane() {
    return splitPane;
  }

  public JPanel getModelPanel() {
    return modelPanel;
  }
}
