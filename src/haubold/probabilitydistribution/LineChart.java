package haubold.probabilitydistribution;

import java.awt.BorderLayout;
import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.xy.*;

public class LineChart extends JPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  public LineChart(XYDataset dataset) {
    this.setLayout(new BorderLayout());
    JFreeChart chart = createChart(dataset);
    ChartPanel chartPanel = new ChartPanel(chart);
    this.add(chartPanel,BorderLayout.CENTER);
  }

  private static JFreeChart createChart(XYDataset dataset) {
    // create the chart...
    JFreeChart chart = ChartFactory.createXYLineChart(
                         "Line Chart Demo 4",      // chart title
                         "X",                      // x axis label
                         "Y",                      // y axis label
                         dataset,                  // data
                         PlotOrientation.VERTICAL,
                         true,                     // include legend
                         true,                     // tooltips
                         false                     // urls
                       );

    XYPlot plot = chart.getXYPlot();
    plot.getDomainAxis().setLowerMargin(0.0);
    plot.getDomainAxis().setUpperMargin(0.0);
    return chart;
  }

}
