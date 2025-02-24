package haubold.wrightfisher;

import haubold.resources.util.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Description:
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Sep 12, 2005; time: 1:52:40 PM.
 */
public class WrightFisherGui extends PrintableJPanel {
  private static final long serialVersionUID = 1L;
  JToolBar toolBar;
  JSlider genSlider, popSlider;
  JButton runButton, resetButton;
  JRadioButton oneParentButton, twoParentButton;
  ButtonGroup parentGroup;
  JCheckBox disentangleCheck;
  BirkhaeuserGUIComponents bgc;
  WrightFisher wf;
  WrightFisherPanel wfp;
  int iniPopSize = 10;
  int iniGenNum = 20;
  JOptionPane optionPane;
  JDialog dialog;
  JPanel checkPanel, controlPanel;
  boolean oneParent = true;

  public WrightFisherGui() {
    bgc = new BirkhaeuserGUIComponents();
    wf = new WrightFisher();
    wfp = new WrightFisherPanel();
    wfp.addMouseListener(wfp);
    wfp.addMouseMotionListener(wfp);
    wfp.setPopulation(null);
    optionPane = new JOptionPane("No MRCA present.");
    dialog = optionPane.createDialog(this,"MRCA");
    // construct toolbar
    this.setLayout(new BorderLayout());
    toolBar = new JToolBar();
    genSlider = bgc.getSlider(0,200,iniGenNum,"Generations");
    genSlider.setToolTipText("Number of generations simulated");
    popSlider = bgc.getSlider(0,50,iniPopSize,"Population size");
    popSlider.setToolTipText("Number of evolving entities");
    parentGroup = new ButtonGroup();
    oneParentButton = new JRadioButton("One-Parent Genealogy");
    twoParentButton = new JRadioButton("Two-Parent Genealogy");
    parentGroup.add(oneParentButton);
    parentGroup.add(twoParentButton);
    oneParentButton.setSelected(true);
    disentangleCheck = new JCheckBox("Disentangle");
    disentangleCheck.setToolTipText("Disentangle lines of descent");
    disentangleCheck.setBackground(bgc.getColor2());
    oneParentButton.setBackground(bgc.getColor2());
    twoParentButton.setBackground(bgc.getColor2());
    ActionListener al = new ButtonActionListener();
    disentangleCheck.addActionListener(al);
    oneParentButton.addActionListener(al);
    twoParentButton.addActionListener(al);
    checkPanel = new JPanel();
    checkPanel.add(oneParentButton);
    checkPanel.add(twoParentButton);
    checkPanel.add(disentangleCheck);
    checkPanel.setBackground(bgc.getColor2());
    runButton = new JButton();
    runButton.setToolTipText("Run");
    runButton.setIcon(Icons.get1rightArrow());
    runButton.addActionListener(new RunActionListener());
    resetButton = new JButton();
    resetButton.setToolTipText("Reset");
    resetButton.setIcon(Icons.getUndo());
    resetButton.addActionListener(new ResetActionListener());
    toolBar.add(genSlider);
    toolBar.add(popSlider);
    toolBar.add(runButton);
    toolBar.add(resetButton);
    controlPanel = new JPanel();
    controlPanel.setLayout(new GridLayout(1,3));
    controlPanel.setLayout(new BorderLayout());
    controlPanel.add(toolBar,BorderLayout.NORTH);
    controlPanel.add(checkPanel,BorderLayout.SOUTH);
    this.add(controlPanel,BorderLayout.NORTH);
    this.add(wfp,BorderLayout.CENTER);
  }

  class RunActionListener implements ActionListener {
    Node[][] pop, untangledPop;
    public void actionPerformed(ActionEvent e) {
      if(genSlider.getValue() > 0 && popSlider.getValue() > 0) {
        pop = wf.constructPopulation(genSlider.getValue(),popSlider.getValue());
      } else {
        pop = null;
      }
      wfp.setPopulation(pop);
      if(disentangleCheck.isSelected())
        wfp.setTangled(false);
      else
        wfp.setTangled(true);
      if(oneParentButton.isSelected())
        wfp.setOneParent(true);
      else
        wfp.setOneParent(false);
      wfp.repaint();
    }

  }

  class ResetActionListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      popSlider.setValue(iniPopSize);
      genSlider.setValue(iniGenNum);
      oneParentButton.setSelected(true);
      disentangleCheck.setSelected(false);
      disentangleCheck.setEnabled(true);
      oneParent = true;
      wfp.setPopulation(null);
      wfp.setOneParent(true);
      wfp.repaint();
    }

  }

  class ButtonActionListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      if(disentangleCheck.isSelected())
        wfp.setTangled(false);
      else
        wfp.setTangled(true);
      if(oneParentButton.isSelected() && !oneParent) {
        oneParent = true;
        wfp.setOneParent(true);
        disentangleCheck.setEnabled(true);
      } else if(twoParentButton.isSelected() && oneParent) {
        oneParent = false;
        wfp.setOneParent(false);
        disentangleCheck.setEnabled(false);
      }
      wfp.repaint();

    }

  }

}
