package haubold.probabilitydistribution;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import haubold.resources.util.*;
import org.jfree.data.xy.*;

public class ExtremeValuePanel extends PrintableJPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  JSlider lambdaSlider, muSlider;
  XYDataset dataSet;
  public ExtremeValuePanel() {
    BirkhaeuserGUIComponents bgc;

    bgc = new BirkhaeuserGUIComponents();
    lambdaSlider = bgc.getSlider(0,5,1,"lambda");
    muSlider = bgc.getSlider(-5,5,0,"mu");
    dataSet = new ExtremeValueDistribution(lambdaSlider.getValue(), muSlider.getValue());

  }

  class SliderChangeListener implements ChangeListener {

    public void stateChanged(ChangeEvent e) {


    }

  }

}
