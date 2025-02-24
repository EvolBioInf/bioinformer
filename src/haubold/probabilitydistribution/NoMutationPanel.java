package haubold.probabilitydistribution;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import haubold.resources.util.*;

import javax.swing.*;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.data.xy.*;

/**
 * Description: Simulate evolution by drift alone in a two-allele system.
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Sep 7, 2005; time: 2:57:30 PM.
 */
public class NoMutationPanel extends PrintableJPanel {
  private static final long serialVersionUID = 1L;
  BirkhaeuserGUIComponents bgc;
  JToolBar toolBar;
  JSlider popSizeSlider, pSlider;
  JButton runButton, stopButton, resetButton;
  boolean stop;
  Random ran;
  int numPop, initialPopSize, initialP, liveCount;
  ArrayList hList, fList;

  XYSeriesCollection fDataset, hDataset;
  JFreeChart chart;
  ChartPanel chartPanel;

  public NoMutationPanel() {
    XYPlot plot;
    NumberAxis domainAxis, rangeAxis;
    this.setLayout(new BorderLayout());
    // construct toolBar
    toolBar = new JToolBar();
    initialPopSize = 10;
    initialP = 50;
    bgc = new BirkhaeuserGUIComponents();
    popSizeSlider = bgc.getSlider(0,100,initialPopSize,"Population Size");
    pSlider = bgc.getSlider(0,100,initialP,"Initial frequency(A) x 100");
    runButton = new JButton(Icons.get1rightArrow());
    stopButton = new JButton(Icons.getStop());
    resetButton = new JButton(Icons.getUndo());
    runButton.addActionListener(new RunActionListener());
    stopButton.addActionListener(new StopActionListener());
    resetButton.addActionListener(new ResetActionListener());
    runButton.setToolTipText("Run");
    stopButton.setToolTipText("Stop");
    resetButton.setToolTipText("Reset");
    toolBar.add(popSizeSlider);
    toolBar.add(pSlider);
    toolBar.add(runButton);
    toolBar.add(stopButton);
    toolBar.add(resetButton);
    this.add(toolBar,BorderLayout.NORTH);
    // construct chart
    fDataset = new XYSeriesCollection();
    hDataset = new XYSeriesCollection();
    chart = ChartFactory.createXYLineChart(
              "",
              "Generation",
              "Frequency(A)",
              fDataset,
              PlotOrientation.VERTICAL,
              true,
              true,
              false
            );
    plot = (XYPlot)chart.getPlot();
    rangeAxis = (NumberAxis)plot.getRangeAxis();
    rangeAxis.setAutoRange(false);
    rangeAxis.setRange(0,1);
    domainAxis = (NumberAxis)plot.getDomainAxis();
    domainAxis.setRange(1,100);
    XYItemRenderer renderer1 = new StandardXYItemRenderer();
    renderer1.setSeriesPaint(0, Color.black);
    plot.setRenderer(0, renderer1);

    // AXIS 2
    NumberAxis axis2 = new NumberAxis("Genetic diversity, h");
    axis2.setFixedDimension(10.0);
    axis2.setAutoRangeIncludesZero(false);
    axis2.setAutoRange(false);
    axis2.setLabelPaint(Color.red);
    axis2.setTickLabelPaint(Color.red);
    plot.setRangeAxis(1, axis2);
    plot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_RIGHT);
    plot.setDataset(1, hDataset);
    plot.mapDatasetToRangeAxis(1, 1);
    XYItemRenderer renderer2 = new StandardXYItemRenderer();
    renderer2.setSeriesPaint(0, Color.red);
    plot.setRenderer(1, renderer2);


    chartPanel = new ChartPanel(chart);
    this.add(chartPanel,BorderLayout.CENTER);

//		ChartFrame frame = new ChartFrame("Test",chart);
//		frame.pack();
//		frame.setVisible(true);

    stop = true;
    liveCount = 0;
    ran = new Random();
  }

  class StopActionListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      stop = true;
      stopButton.setEnabled(false);
      resetButton.setEnabled(true);
    }

  }

  class ResetActionListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      hDataset.removeAllSeries();
      fDataset.removeAllSeries();
      numPop = 0;
      resetButton.setEnabled(false);
      popSizeSlider.setValue(initialPopSize);
      pSlider.setValue(initialP);
    }

  }

  class RunActionListener implements ActionListener {
    XYSeries hSeries, fSeries;
    String hIdentifier, fIdentifier;
    DataGenerator generator;

    public void actionPerformed(ActionEvent e) {
      if(stop) {
        stop = false;
        resetButton.setEnabled(false);
        stopButton.setEnabled(true);
      }
      if(popSizeSlider.getValue() > 1) {
        hIdentifier = new String();
        numPop++;
        hIdentifier = "Pop_" + numPop + ", N=" + popSizeSlider.getValue() + ", h";
        fIdentifier = new String();
        fIdentifier = "Pop_" + numPop + ", N=" + popSizeSlider.getValue() + ", f";
        hSeries = new XYSeries(hIdentifier);
        fSeries = new XYSeries(fIdentifier);
        fDataset.addSeries(fSeries);
        hDataset.addSeries(hSeries);
        generator = new DataGenerator(10,fSeries,hSeries);
        generator.start();
        liveCount++;
      }
    }

  }

  class DataGenerator extends Timer implements ActionListener {
    private static final long serialVersionUID = 1L;
    XYSeries fSeries, hSeries;
    short pop1[], pop2[];
    short indexFrom, indexTo;
    int popSize, popSizeMinusOne;
    double generationCounter;
    boolean first = true;

    DataGenerator(int interval, XYSeries fSeries, XYSeries hSeries) {
      super(interval, null);
      addActionListener(this);
      this.fSeries = fSeries;
      this.hSeries = hSeries;
      int i, n;

      popSize = popSizeSlider.getValue();
      popSizeMinusOne = popSize - 1;
      pop1 = new short[popSize];
      pop2 = new short[popSize];
      n = Math.round((float)popSize*(float)pSlider.getValue()/100);
      for(i=0; i<n; i++) {
        pop1[i]=1;
      }
      generationCounter = 0;
    }

    public void actionPerformed(ActionEvent event) {
      int i, j, r;
      double f, h;

      if(!stop) {
        f = 0;
        for(i=0; i<popSize; i++)
          f += pop1[i];
        f /= (double)popSize;
        // measure genetic diversity
        h = 0;
        for(i=0; i<popSizeMinusOne; i++)
          for(j=i+1; j<popSize; j++)
            if(pop1[i] != pop1[j])
              h++;
        h /= (double)popSize*(double)(popSize-1)/2.0;
        if(f != 0 && f != 1) {
          for(i=0; i<popSize; i++) {
            r = ran.nextInt(popSize);
            pop2[i] = pop1[r];
          }
          for(i=0; i<popSize; i++) {
            pop1[i] = pop2[i];
          }
          fSeries.add(++generationCounter,f);
          hSeries.add(generationCounter,h);
        } else if(first) {
          fSeries.add(++generationCounter,f);
          hSeries.add(generationCounter,h);
          first = false;
          this.stop();
          liveCount--;
          if(liveCount == 0) {
            stopButton.setEnabled(false);
            resetButton.setEnabled(true);
            stop = true;
          }
        }
      }
    }
  }
}
