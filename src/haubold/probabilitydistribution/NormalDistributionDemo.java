/* ---------------------------
 * NormalDistributionDemo.java
 * ---------------------------
 * (C) Copyright 2004, by Object Refinery Limited.
 *
 */

package haubold.probabilitydistribution;
import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.*;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.plot.*;
import org.jfree.data.function.*;
import org.jfree.data.general.*;
import org.jfree.data.xy.*;
import org.jfree.ui.*;

/**
 * This demo shows a normal distribution function.
 */
public class NormalDistributionDemo extends ApplicationFrame {
  private static final long serialVersionUID = 1L;
  /**
   * A demonstration application showing a normal distribution.
   *
   * @param title  the frame title.
   */
  JPanel distributionPanel;
  JSlider sigmaSlider, muSlider;
  Function2D normal;
  XYDataset dataset;
  JFreeChart chart;
  AxisSpace as;
  public NormalDistributionDemo(String title) {

    super(title);
    JToolBar toolBar;
    SliderListener sl;
    int i;
    normal = new NormalPDF(0.0, 1.0);
    dataset = DatasetUtilities.sampleFunction2D(normal, -15.0, 15.0, 200, "Normal");
    chart = ChartFactory.createXYLineChart(
              "",
              "X",
              "Y",
              dataset,
              PlotOrientation.VERTICAL,
              true,
              true,
              false
            );
    chart.getXYPlot().getRangeAxis().setAutoRange(false);
    chart.getXYPlot().getRangeAxis().setRange(0,1);
    distributionPanel = new JPanel();
    toolBar = new JToolBar();
    sigmaSlider = new JSlider(0, 500, 100);
    sigmaSlider.setPaintTicks(true);
    sigmaSlider.setMajorTickSpacing(100);
    sigmaSlider.setMinorTickSpacing(20);
    sigmaSlider.setBorder(new TitledBorder("sigma"));
    sigmaSlider.setPaintLabels(true);
    sigmaSlider.getLabelTable().put(Integer.valueOf(100),new JLabel("1"));
    sigmaSlider.getLabelTable().put(Integer.valueOf(200),new JLabel("2"));
    sigmaSlider.getLabelTable().put(Integer.valueOf(300),new JLabel("3"));
    sigmaSlider.getLabelTable().put(Integer.valueOf(400),new JLabel("4"));
    sigmaSlider.getLabelTable().put(Integer.valueOf(500),new JLabel("5"));
    sigmaSlider.setLabelTable(sigmaSlider.getLabelTable());

    muSlider = new JSlider(-1500, 1500, 0);
    muSlider.setPaintTicks(true);
    muSlider.setPaintLabels(true);
    muSlider.setMajorTickSpacing(500);
    muSlider.setMinorTickSpacing(100);
    muSlider.setBorder(new TitledBorder("mu"));
    for(i=-15; i<=15; i+=5) {
      muSlider.getLabelTable().put(Integer.valueOf(i*100),new JLabel(String.valueOf(i)));
    }
    muSlider.setLabelTable(muSlider.getLabelTable());
    sl = new SliderListener();
    sigmaSlider.addChangeListener(sl);
    muSlider.addChangeListener(sl);
    toolBar.add(sigmaSlider);
    toolBar.add(muSlider);
    distributionPanel.setLayout(new BorderLayout());
    ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
    distributionPanel.add(chartPanel,BorderLayout.CENTER);
    distributionPanel.add(toolBar,BorderLayout.NORTH);
    setContentPane(distributionPanel);

  }

  /**
   * Starting point for the demonstration application.
   *
   * @param args  ignored.
   */
  public static void main(String[] args) {

    NormalDistributionDemo demo = new NormalDistributionDemo("Normal Distribution Demo");
    demo.pack();
    RefineryUtilities.centerFrameOnScreen(demo);
    demo.setVisible(true);

  }

  class SliderListener implements ChangeListener {

    public void stateChanged(ChangeEvent e) {
      normal = new NormalPDF(muSlider.getValue()/100.0, sigmaSlider.getValue()/100.0);
      dataset = DatasetUtilities.sampleFunction2D(normal, -15.0, 15.0, 200, "Normal");
      chart.getXYPlot().setDataset(dataset);
//			chart.getXYPlot().setFixedDomainAxisSpace(as);
      chart.fireChartChanged();
    }

  }

}



