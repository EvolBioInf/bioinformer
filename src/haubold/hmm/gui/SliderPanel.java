package haubold.hmm.gui;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

import haubold.hmm.algorithm.HiddenMarkovModel;
import haubold.resources.util.*;
/**
 * @author haubold
 * Date: Nov 1, 2003; time: 12:11:50 PM.
 *
 * Description: Packages the sliders for manipulating the model's probabilities.
 */
public class SliderPanel extends JPanel {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private BirkhaeuserGUIComponents bgc = new BirkhaeuserGUIComponents();
  private JSlider emission1, transition1;
  private JSlider emission2, transition2;
  private int factor = 100; // Factor of proportionality
  private JSlider initial;
  private ModelPanel modelPanel;
  private HiddenMarkovModel hmm, originalHmm;
  private Color colorE1, colorE2, colorH1, colorH2;
  double[][] tp1 = {{0.99,0.01},{0.05,0.95}};
  double[][] ep1 = {{0.5,0.5},{0.8,0.2}};
  double[] ip1 = {0.5,0.5};
  double[][] tp2 = {{0.99,0.01},{0.05,0.95}};
  double[][] ep2 = {{0.5,0.5},{0.8,0.2}};
  double[] ip2 = {0.5,0.5};

  public SliderPanel(HiddenMarkovModel hmm) {
    this.hmm = hmm;
    initialize();

  }

  public SliderPanel() {
    hmm = new HiddenMarkovModel();


    hmm.setInitialProbabilities(ip1);
    hmm.setTransitionProbabilities(tp1);
    hmm.setObservationProbabilities(ep1);
    originalHmm = new HiddenMarkovModel();
    originalHmm.setInitialProbabilities(ip2);
    originalHmm.setObservationProbabilities(ep2);
    originalHmm.setTransitionProbabilities(tp2);
    colorE1 = bgc.getColor3();
    colorE2 = bgc.getColor5();
    colorH1 = bgc.getColor2();
    colorH2 = bgc.getColor4();
    initialize();
  }

  private void initialize() {
    SliderListener sl;
    emission1 = bgc.getSlider(0,factor,(int)(ep1[0][0]*factor),"% Emission Probability 1");
    emission2 = bgc.getSlider(0,factor,(int)(ep1[1][0]*factor),"% Emission Probability 2");
    transition1 = bgc.getSlider(0,factor,(int)(tp1[0][0]*factor),"% Transition Probability 1");
    transition2 = bgc.getSlider(0,factor,(int)(tp1[1][1]*factor),"% Transition Probability 2");
    initial = bgc.getSlider(0,factor,(int)(ip1[0]*factor),"% Initial Probability");
    sl = new SliderListener();
    emission1.addChangeListener(sl);
    emission2.addChangeListener(sl);
    transition1.addChangeListener(sl);
    transition2.addChangeListener(sl);
    initial.addChangeListener(sl);
    // Set Colors
    setColors();
    this.setLayout(new GridLayout(5,5));
    this.add(emission1);
    this.add(emission2);
    this.add(transition1);
    this.add(transition2);
    this.add(initial);
  }

  private void setColors() {
//		paintLabels(emission1,colorE1);
//		paintLabels(emission2,colorE2);
//		paintLabels(transition1,colorH1);
//		paintLabels(transition2,colorH2);
//		paintLabels(initial,Color.black);
  }

  public void reset() {
    transition1.setValue((int)(originalHmm.getTransitionProbabilities()[0][0]*(double)factor));
    transition2.setValue((int)(originalHmm.getTransitionProbabilities()[1][1]*(double)factor));
    emission1.setValue((int)(originalHmm.getObservationProbabilities()[0][0]*(double)factor));
    emission2.setValue((int)(originalHmm.getObservationProbabilities()[1][0]*(double)factor));
    initial.setValue((int)(originalHmm.getInitialProbabilities()[0]*(double)factor));
  }

  /**
   * @param panel
   */
  public void setModelPanel(ModelPanel panel) {
    modelPanel = panel;
  }

  class SliderListener implements ChangeListener {
    public void stateChanged(ChangeEvent arg0) {
      double[][] tp = hmm.getTransitionProbabilities();
      double[][] ep = hmm.getObservationProbabilities();
      double[] ip = hmm.getInitialProbabilities();
      tp[0][0] = transition1.getValue()/(double)factor;
      tp[0][1] = 1.0-tp[0][0];
      tp[1][1] = transition2.getValue()/(double)factor;
      tp[1][0] = 1.0 - tp[1][1];
      ep[0][0] = emission1.getValue()/(double)factor;
      ep[0][1] = 1.0-ep[0][0];
      ep[1][0] = emission2.getValue()/(double)factor;
      ep[1][1] = 1.0-ep[1][0];
      ip[0] = initial.getValue()/(double)factor;
      ip[1] = 1.0 - ip[0];
//			System.out.println("ip[0]: " + ip[0]);
      hmm.setInitialProbabilities(ip);
      hmm.setObservationProbabilities(ep);
      hmm.setTransitionProbabilities(tp);
      modelPanel.setHmm(hmm);
    }
  }

  /**
   * @return
   */
  public HiddenMarkovModel getHmm() {
    return hmm;
  }

  /**
   * @param model
   */
  public void setHmm(HiddenMarkovModel model) {
    hmm = model;
  }

  /**
   * Get color of first emitted state.
   * @return
   */
  public Color getColorE1() {
    return colorE1;
  }

  /**
   * Get color of second emitted state.
   * @return
   */
  public Color getColorE2() {
    return colorE2;
  }

  /**
   * Get color of first hidden state.
   * @return
   */
  public Color getColorH1() {
    return colorH1;
  }

  /**
   * Get color of second hidden state.
   * @return
   */
  public Color getColorH2() {
    return colorH2;
  }

  /**
   * Set color of first emitted state.
   * @param color
   */
  public void setColorE1(Color color) {
    colorE1 = color;
    setColors();
  }

  /**
   * Set color of second emitted state.
   * @param color
   */
  public void setColorE2(Color color) {
    colorE2 = color;
    setColors();
  }

  /**
   * Set color of first hidden state.
   * @param color
   */
  public void setColorH1(Color color) {
    colorH1 = color;
    setColors();
  }

  /**
   * Set color of second hidden state.
   * @param color
   */
  public void setColorH2(Color color) {
    colorH2 = color;
    setColors();
  }

}
