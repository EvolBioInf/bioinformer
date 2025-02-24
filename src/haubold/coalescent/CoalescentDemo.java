package haubold.coalescent;

import haubold.resources.util.*;
import haubold.resources.demo.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class CoalescentDemo extends DemoFrame {
  private static final long serialVersionUID = 1L;
  JPanel controlP;
  TreePanel treeP;
  HaplotypePanel haplotypeP;
  JButton stepB, resetB, animateB;
  JLabel sampleSizeL, thetaL;
  JSlider sampleSizeS, thetaS, stepSlider;
  SliderListener mySlideListener = new SliderListener();
  JTextField sampleSizeF, thetaF;
  PrintableSplitPane splitPane;
  JToolBar toolBar;
  BirkhaeuserGUIComponents bgc;
  int step = 0;
  int thetaMax = 500; // Maximum value of theta * 10
  int[] leafId;
  String[] stepBstr = { "Step 1", "Step 2", "Step 3" };
  String[] stepLstr =
  { "Generate genealogy", "Generate mutations", "Generate sample" };
  double[][] treeCoords;
  Tree tree;
  MutationObject mo = new MutationObject();
  double theta;
  boolean animate = false;
  private double dividerLocation;

  public CoalescentDemo(String title, String helpPath) {
    setTitle(title);
    setHelpPath(helpPath);
    tree = new Tree();
    treeP = new TreePanel();
    haplotypeP = new HaplotypePanel();
    // Construct buttons and corresponding event listeners
    stepB = new JButton(Icons.get2rightArrow());
    stepB.setToolTipText("Step1:generate genealogy");
    resetB = new JButton(Icons.getUndo());
    resetB.setToolTipText("Reset");
    stepB.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        stepAction(e);
      }
    });
    resetB.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        resetAction(e);
      }
    });
    animateB = new JButton(Icons.getRun());
    animateB.setToolTipText("Animate Display");
    animateB.addActionListener(new AnimateActionListener());

    // Construct sliders and the corresponding labels
    bgc = new BirkhaeuserGUIComponents();
    sampleSizeS = bgc.getSlider(2,50,10,"Sample Size");
    sampleSizeS.addChangeListener(mySlideListener);
    thetaS = bgc.getSlider(0,thetaMax,20,"theta");
    // Make custom labels for theta slider
    Hashtable<Integer,JLabel> hashtable = new Hashtable<Integer,JLabel>();
    for(int i=0; i<=thetaMax; i += 100) {
      hashtable.put(Integer.valueOf(i),new JLabel(String.valueOf(i/10)));
    }
    thetaS.setLabelTable(hashtable);
    thetaS.addChangeListener(mySlideListener);
    thetaL = new JLabel(String.valueOf(thetaS.getValue()/10.0));
    Dimension dimension = new Dimension(30,50);
    thetaL.setMinimumSize(dimension);
    thetaL.setMaximumSize(dimension);
    thetaL.setPreferredSize(dimension);
    thetaL.setHorizontalAlignment(JLabel.RIGHT);
    sampleSizeL = new JLabel(String.valueOf(sampleSizeS.getValue()));
    dimension = new Dimension(20,20);
    sampleSizeL.setMinimumSize(dimension);
    sampleSizeL.setMaximumSize(dimension);
    sampleSizeL.setPreferredSize(dimension);
    sampleSizeL.setHorizontalAlignment(JLabel.RIGHT);

    stepSlider = bgc.getSlider(1,10,4,"Step Time");
    stepSlider.setEnabled(false);
    stepSlider.setSnapToTicks(true);

    // Construct toolBar
    toolBar = new JToolBar();
    toolBar.add(stepB);
    toolBar.add(animateB);
    toolBar.add(resetB);
    toolBar.add(stepSlider);
    toolBar.add(sampleSizeS);
    toolBar.add(sampleSizeL);
    toolBar.add(thetaS);
    toolBar.add(thetaL);
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(toolBar, BorderLayout.NORTH);
    // Construct splitPane
    splitPane = new PrintableSplitPane(JSplitPane.VERTICAL_SPLIT,true);
    splitPane.setOneTouchExpandable(true);
    splitPane.add(treeP);
    splitPane.add(haplotypeP);
    this.getContentPane().add(splitPane,BorderLayout.CENTER);
    splitPane.setDividerLocation(200);
    // Set printable Item
    this.getFileMenu().getPrintMenuItem().setPrintable(splitPane);
  }

  private void step0() {
    int sampleSize;

    sampleSize = sampleSizeS.getValue();
    tree.makeTree(sampleSize);
    leafId = tree.getLeafId();
    treeCoords = tree.getTreeCoordinates();
    treeP.setMutations(false);
    treeP.drawTree(treeCoords, leafId);
    haplotypeP.setDrawing(false);
    haplotypeP.repaint();
  }

  private void step1() {
    theta = (double) thetaS.getValue() / 10.0;
    tree.mutate(theta);
    mo = tree.getMutationCoordinates();
    if (mo.mutCoord.length > 0.0) {
      treeP.drawMutations(mo.mutCoord);
    }
  }

  private void step2() {
    haplotypeP.drawHaplotypes(mo.haplotypes);
  }

  private void step3() {
    treeP.setTree(false);
    treeP.setMutations(false);
    treeP.repaint();
    haplotypeP.setDrawing(false);
    haplotypeP.repaint();
  }

  private void stepAction(ActionEvent e) {
    if (step == 0) {
      step0();
      stepB.setToolTipText("Step2: generate mutations");
      step++;
    } else if (step == 1) {
      step1();
      stepB.setToolTipText("Step3: generate haplotypes");
      step++;
    } else if (step == 2) {
      step2();
      stepB.setToolTipText("Step1: generate genealogy");
      step = 0;
    }
  }
  private void resetAction(ActionEvent e) {
    treeCoords = null;
    stepB.setToolTipText("Step1: generate genealogy");
    sampleSizeS.setValue(10);
    thetaS.setValue(20);
    step3();
    step = 0;
  }

  class SliderListener implements ChangeListener {
    public void stateChanged(ChangeEvent e) {
      sampleSizeL.setText(String.valueOf(sampleSizeS.getValue()));
      thetaL.setText(String.valueOf((double) thetaS.getValue() / 10.0));
    }
  }
  /**
   * Returns the sliderPosition.
   * @return double
   */
  public double getDividerLocation() {
    return dividerLocation;
  }

  /**
   * Sets the sliderPosition.
   * @param sliderPosition The sliderPosition to set
   */
  public void setDividerLocation(double dividerLocation) {
    this.dividerLocation = dividerLocation;
    splitPane.setDividerLocation(dividerLocation);
  }


  class AnimateActionListener implements ActionListener {
    Animation animation;
    public void actionPerformed(ActionEvent arg0) {
      if (animate == false) {
        animation = new Animation();
        animate = true;
        animation.start();
        animateB.setIcon(Icons.getStop());
        animateB.setToolTipText("Stop");
        stepB.setEnabled(false);
        resetB.setEnabled(false);
        stepSlider.setEnabled(true);
      } else {
        animate = false;
        animateB.setIcon(Icons.getRun());
        animateB.setToolTipText("Animate Display");
        stepB.setEnabled(true);
        resetB.setEnabled(true);
//				step3();
        stepB.setToolTipText("Step1:generate genealogy");
        step = 0;
        stepSlider.setEnabled(false);
      }

    }

  }

  class Animation extends Thread {
    long sleepTime = 200;
    public void run() {
      while (animate) {
        sleepTime = stepSlider.getValue()*100;
        step0();
        if(!animate)
          break;
        try {
          sleep(sleepTime);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
        if(!animate)
          break;
        step1();
        if(!animate)
          break;
        try {
          sleep(sleepTime);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
        if(!animate)
          break;
        step2();
        if(!animate)
          break;
        try {
          sleep(sleepTime);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }
  }
}
