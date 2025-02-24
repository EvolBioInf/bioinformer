package haubold.sm;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

import haubold.resources.util.*;

public class PamPanel extends PrintableJPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  MatrixDisplayPanel lodPanel;
  static JSlider nSlider, sSlider;
  JLabel nLabel, sLabel;
  JButton resetButton, aboutButton;
  ResetActionListener resetAction;
  JToolBar toolBar;
  JDialog aboutDialog;
  BirkhaeuserAboutButton birkButton;
  PamComputation pamComputation;
  int initN = 70; // initial value of PAM-matrix
  int initS = 2; //  initial value for denominator of scale factor
  String[] aminoAcids;
  int[][] values;
  int diff;

  public PamPanel() {
    int i;
    // Construct matrix display panel
    pamComputation = new PamComputation();
    values = pamComputation.getLod(initN, initS);
    aminoAcids = pamComputation.getAminoAcids();
    diff = pamComputation.getPercentDifference();
    lodPanel = new MatrixDisplayPanel(values,aminoAcids,diff);
    lodPanel.setAminoAcids(pamComputation.getAminoAcids());
    lodPanel.setValues(pamComputation.getLod(initN,initS));
    lodPanel.setPercentDifference(pamComputation.getPercentDifference());
    lodPanel.repaint();
    nLabel = new JLabel();
    nLabel.setPreferredSize(new Dimension(40,10));
    nLabel.setHorizontalAlignment(JLabel.CENTER);
    nLabel.setText(String.valueOf(initN));
    sLabel = new JLabel();
    sLabel.setPreferredSize(new Dimension(40,10));
    sLabel.setHorizontalAlignment(SwingConstants.CENTER);
    sLabel.setText("1/"+initS);
    DefaultBoundedRangeModel brm = new DefaultBoundedRangeModel(10,2,2,502);
    nSlider = new JSlider(brm);
    nSlider.setMajorTickSpacing(100);
    nSlider.setPaintTicks(true);
    nSlider.setPaintLabels(true);
    nSlider.setSnapToTicks(false);
    for(i=1; i<=5; i++) {
      nSlider.getLabelTable().remove(Integer.valueOf(i*100+2));
      nSlider.getLabelTable().put(Integer.valueOf(i*100),new JLabel(Integer.valueOf(i*100).toString(),JLabel.CENTER));
    }
    nSlider.setLabelTable(nSlider.getLabelTable());
    nSlider.setValue(initN);

    nSlider.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        nSliderChanged(e);
      }
    });
    TitledBorder nBorder = new TitledBorder("PAM");
    nSlider.setBorder(nBorder);

    // Crate custom labels for scale slider.
    sSlider = new JSlider(1, 5, initS);
    Hashtable labelTable = new Hashtable();
    labelTable.put(Integer.valueOf( 1 ), new JLabel("1/1") );
    labelTable.put(Integer.valueOf( 2 ), new JLabel("1/2") );
    labelTable.put(Integer.valueOf( 3 ), new JLabel("1/3") );
    labelTable.put(Integer.valueOf( 4 ), new JLabel("1/4") );
    labelTable.put(Integer.valueOf( 5 ), new JLabel("1/5") );
    sSlider.setLabelTable( labelTable );
    sSlider.setMajorTickSpacing(1);
    sSlider.setPaintTicks(true);
    sSlider.setPaintLabels(true);
    sSlider.setSnapToTicks(true);
    sSlider.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        sSliderChanged(e);
      }
    });
    TitledBorder sBorder = new TitledBorder("Bits");
    sSlider.setBorder(sBorder);
    resetButton = new JButton(Icons.getUndo());
    resetButton.setToolTipText("Reset");
    resetAction = new ResetActionListener();
    resetButton.addActionListener(resetAction);
    birkButton = new BirkhaeuserAboutButton();
    birkButton.setTitleString("About \"PAM\"");
    // Construct toolBar.
    toolBar = new JToolBar();
    toolBar.add(nSlider);
    toolBar.add(nLabel);
    toolBar.add(sSlider);
    toolBar.add(resetButton);
    this.setLayout(new BorderLayout());
    this.add(toolBar, BorderLayout.NORTH);
    this.add(lodPanel, BorderLayout.CENTER);
  }

  void resetButtonAction(ActionEvent e) {
    nSlider.setValue(initN);
    sSlider.setValue(initS);
  }

  public static int getN() {
    return nSlider.getValue();
  }

  public static int getScale() {
    return sSlider.getValue();
  }

  void nSliderChanged(ChangeEvent e) {
    int n = nSlider.getValue();
    int s = sSlider.getValue();
    nLabel.setText(String.valueOf(n));
    lodPanel.setValues(pamComputation.getLod(n,s));
    lodPanel.setPercentDifference(pamComputation.getPercentDifference());
    lodPanel.repaint();
  }

  void sSliderChanged(ChangeEvent e) {
    int n = nSlider.getValue();
    int s = sSlider.getValue();
    sLabel.setText("1/" + s);
    lodPanel.setValues(pamComputation.getLod(n,s));
    lodPanel.setPercentDifference(pamComputation.getPercentDifference());
    lodPanel.repaint();
  }

  class ResetActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      nSlider.setValue(initN);
      sSlider.setValue(initS);
    }
  }
}
