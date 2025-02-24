package haubold.numAlignments;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import java.text.*;

import haubold.resources.util.*;
import haubold.resources.demo.*;

/**
 * Calculation of the number of alignments that can be formed
 * from two sequences of lengths m & n.
 *
 * @version 0.0 July 10, 2002.
 * @author Bernhard Haubold
 */
public class NumAlDemo extends DemoFrame {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  JSlider mSlider, nSlider;
  JButton resetButton;
  JLabel titleLabel, mLabel, nLabel, resultLabel;
  JPanel mPanel, nPanel;
  ResultPanel resultPanel;
  DecimalFormat form;
  JToolBar toolBar = new JToolBar();
  BirkhaeuserGUIComponents bgc;
  static double[][] dpm = null;
  JRadioButton localButton, globalButton;
  int maxM = 400;
  int maxN = 400;
  static double[][] pascalsTriangle;

  public NumAlDemo(String titleString, String helpPath) {
    setTitle(titleString);
    setHelpPath(helpPath);
    bgc = new BirkhaeuserGUIComponents();
    mSlider = getSlider(0,maxM,20);
    nSlider = getSlider(0,maxN,20);
    mSlider.setBorder(new TitledBorder("Length of Sequence 1"));
    nSlider.setBorder(new TitledBorder("Length of Sequence 2"));
    initializeDp(maxM, maxN);
    mSlider.setPreferredSize(new Dimension(170,65));
    nSlider.setPreferredSize(new Dimension(170,65));
    mLabel = new JLabel();
    nLabel = new JLabel();
    Dimension size = new Dimension(70,20);
    mLabel.setPreferredSize(size);
    mLabel.setMaximumSize(size);
    mLabel.setMinimumSize(size);
    nLabel.setPreferredSize(size);
    nLabel.setMaximumSize(size);
    nLabel.setMinimumSize(size);

    mLabel.setHorizontalAlignment(JLabel.CENTER);
    nLabel.setHorizontalAlignment(JLabel.CENTER);
    mLabel.setText(String.valueOf(mSlider.getValue()));
    nLabel.setText(String.valueOf(nSlider.getValue()));
    mLabel.setOpaque(true);
    nLabel.setOpaque(true);
    mLabel.setForeground((Color.red).brighter());
    nLabel.setForeground((Color.blue).brighter());
    resultPanel = new ResultPanel();
    resultPanel.setResult(numAl(mSlider.getValue(),nSlider.getValue(),false));
    // Construct Buttons
    globalButton = new JRadioButton("Global");
    globalButton.setSelected(true);
    localButton = new JRadioButton("Local");
    ButtonGroup bg = new ButtonGroup();
    JPanel buttonPanel = new JPanel(new GridLayout(2,1));
    bg.add(globalButton);
    bg.add(localButton);
    buttonPanel.add(globalButton);
    buttonPanel.add(localButton);
    // Construct change listener
    SliderListener myListener = new SliderListener();
    nSlider.addChangeListener(myListener);
    mSlider.addChangeListener(myListener);
    globalButton.addChangeListener(myListener);
    localButton.addChangeListener(myListener);


    toolBar = new JToolBar();
    toolBar.add(mSlider);
    toolBar.add(mLabel);
    toolBar.add(nSlider);
    toolBar.add(nLabel);
    toolBar.add(buttonPanel);

    // Add GUI elements to contentPane
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(toolBar, BorderLayout.NORTH);
    this.getContentPane().add(resultPanel, BorderLayout.CENTER);

//		constructPascalsTriangle(maxM);
  }

  class SliderListener implements ChangeListener {
    boolean local;
    public void stateChanged(ChangeEvent e) {
      mLabel.setText(String.valueOf(mSlider.getValue()));
      nLabel.setText(String.valueOf(nSlider.getValue()));
      if(localButton.isSelected()) {
        local=true;
      } else {
        local=false;
      }
      resultPanel.setResult(numAl(mSlider.getValue(),nSlider.getValue(),local));
    }
  }


  static double numAl(int m, int n, boolean local) {
    int i, j;
    double s;
    if(m==0 || n ==0) {
      return 1;
    }
    if(!local) {
      return dpm[m][n];
    } else {
      s=0.0;
      for(i=1; i<=n; i++) {
        for(j=1; j<=m; j++) {
          s+=dpm[j][i]*(n-i+1)*(m-j+1);
        }
      }
    }
    return s;
  }

//	private void constructPascalsTriangle(int max){
//		pascalsTriangle = new double[max+1][max+1];
//		int i, j;
//		// Initialize
//		for(i=0;i<=max;i++){
//			pascalsTriangle[0][i]=1.0;
//			pascalsTriangle[i][i]=1.0;
//		}
//		// Fill triangle
//		for(i=1;i<=max;i++){
//			for(j=2;j<=max;j++){
//				pascalsTriangle[i][j] = pascalsTriangle[i][j-1]+pascalsTriangle[i-1][j-1];
//			}
//		}
//	}

  private void initializeDp(int m, int n) {
    int i, j;
    dpm = new double[m + 1][n + 1];
    // set first row
    for (i = 0; i <= n; i++) {
      dpm[0][i] = 1.0;
    }
    // set first column
    for (i = 1; i <= m; i++) {
      dpm[i][0] = 1.0;
    }
    // fill in rest of matrix
    for (i = 1; i <= m; i++) {
      for (j = 1; j <= n; j++) {
        dpm[i][j] = dpm[i - 1][j] + dpm[i - 1][j - 1] + dpm[i][j - 1];
      }
    }
  }

  private JSlider getSlider(int min, int max, int init) {
    JSlider slider = new JSlider(min,max,init);
    slider.setMajorTickSpacing(100);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    slider.setSnapToTicks(false);
    return slider;
  }

}
