package haubold.probabilitydistribution;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.Random;

import haubold.resources.util.*;

import javax.swing.*;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.data.xy.*;

/**
 * Description: Simulate evolution under the infinite alleles model.
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Sep 7, 2005; time: 2:57:30 PM.
 */
public class MutationPanel extends PrintableJPanel {
  private static final long serialVersionUID = 1L;
  BirkhaeuserGUIComponents bgc;
  JToolBar toolBar;
  JSlider popSizeSlider, pSlider;
  JButton runButton, stopButton, resetButton;
  boolean stop;
  Random ran;
  int numPop, initialPopSize, initialP, liveCount;
  double allele;

  XYSeriesCollection dataset;
  JFreeChart chart;
  ChartPanel chartPanel;

  public MutationPanel() {
    XYPlot plot;
    NumberAxis domainAxis, rangeAxis;
    this.setLayout(new BorderLayout());
    // construct toolBar
    toolBar = new JToolBar();
    initialPopSize = 100;
    initialP = 5;
    bgc = new BirkhaeuserGUIComponents();
    popSizeSlider = bgc.getSlider(0,200,initialPopSize,"Population Size");
    pSlider = bgc.getSlider(0,10,initialP,"Mutation Rate x 1000");
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
    dataset = new XYSeriesCollection();
    chart = ChartFactory.createXYLineChart(
              "",
              "Generation",
              "Genetic diversity, h",
              dataset,
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
    domainAxis.setRange(1,1000);

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
      dataset.removeAllSeries();
      numPop = 0;
      resetButton.setEnabled(false);
      popSizeSlider.setValue(initialPopSize);
      pSlider.setValue(initialP);
    }

  }

  class RunActionListener implements ActionListener {
    XYSeries xysObs, xysExp;
    String identifierObs, identifierExp;
    DataGenerator generator;

    public void actionPerformed(ActionEvent e) {
      if(stop) {
        stop = false;
        resetButton.setEnabled(false);
        stopButton.setEnabled(true);
      }
      if(popSizeSlider.getValue() > 1) {
        identifierObs = new String();
        identifierObs = "Pop_" + ++numPop + ", N=" + popSizeSlider.getValue();
        identifierExp = "Pop_" + numPop + ", exp";
        xysObs = new XYSeries(identifierObs);
        xysExp = new XYSeries(identifierExp);
        dataset.addSeries(xysObs);
        dataset.addSeries(xysExp);
        generator = new DataGenerator(10,xysObs,xysExp);
        generator.start();
        liveCount++;
      }
    }

  }

  class DataGenerator extends Timer implements ActionListener {
    private static final long serialVersionUID = 1L;
    XYSeries xysObs, xysExp;
    double pop1[], pop2[];
    short indexFrom, indexTo;
    int popSize, popSizeMinusOne;
    double generationCounter;
    boolean first = true;
    double mu, hExp;

    DataGenerator(int interval, XYSeries xysObs, XYSeries xysExp) {
      super(interval, null);
      addActionListener(this);
      this.xysObs = xysObs;
      this.xysExp = xysExp;
      int i;

      popSize = popSizeSlider.getValue();
      popSizeMinusOne = popSize - 1;
      mu = pSlider.getValue()/1000.0;
      hExp = (2*popSize*mu)/(2.0*popSize*mu+1.0);
      pop1 = new double[popSize];
      pop2 = new double[popSize];
      for(i=0; i<popSize; i++) {
        pop1[i]=allele;
      }
      generationCounter = 0;
    }

    public void actionPerformed(ActionEvent event) {
      int i, j, r;
      double h;

      if(!stop) {
        // measure genetic diversity
        h = 0;
        for(i=0; i<popSizeMinusOne; i++)
          for(j=i+1; j<popSize; j++)
            if(pop1[i] != pop1[j])
              h++;
        h /= (double)popSize*(double)(popSize-1)/2.0;
        xysObs.add(++generationCounter,h);
        xysExp.add(generationCounter,hExp);
        // mutate
        for(i=0; i<popSize; i++) {
          if(ran.nextDouble() < mu)
            pop1[i] = ++allele;
        }
        // resample with replacement
        for(i=0; i<popSize; i++) {
          r = ran.nextInt(popSize);
          pop2[i] = pop1[r];
        }
        for(i=0; i<popSize; i++) {
          pop1[i] = pop2[i];
        }
      }
    }
  }
}
