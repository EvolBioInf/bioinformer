package haubold.phylogeny.distance;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.Printable;

import javax.swing.*;
import javax.swing.event.*;

import haubold.resources.demo.*;
import haubold.resources.util.OpenSequenceMenuItem;
import haubold.resources.util.PrintMenuItem;

/**
 * @author Bernhard Haubold
 * Date: Jun 10, 2003; time: 2:59:34 PM.
 *
 * Description:
 */
public class DistanceTreeDemo extends DemoFrame {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  JTabbedPane tp;
  DistanceComputationGUI dcg;
  DistanceTreeGUI dtg;
  BootstrapGUI bg;
  PrintMenuItem pmi;
  OpenSequenceMenuItem osmi;
  DemoFrame frame;

  public DistanceTreeDemo(String titleString, String helpPath) {
    frame = this;
    setTitle(titleString);
    setHelpPath(helpPath);
    this.getContentPane().setLayout(new BorderLayout());
    tp = new JTabbedPane();
    this.getContentPane().add(tp,BorderLayout.CENTER);
    dcg = new DistanceComputationGUI();
    dtg = new DistanceTreeGUI();
    bg = new BootstrapGUI();
    tp.addTab("Distance Matrix", dcg);
    tp.addTab("Phylogeny", dtg);
    tp.addTab("Bootstrap", bg);
    // Add change listener
    TabChangeListener tcl = new TabChangeListener();
    tp.addChangeListener(tcl);
    // Add sequence opening file menu
    osmi = new OpenSequenceMenuItem("Open Sequence Alignment", KeyEvent.VK_O, dcg.getAlignmentPane());;
    osmi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
    this.getFileMenu().add(osmi,0);
    // Add printing to file Menu
    pmi = this.getFileMenu().getPrintMenuItem();
    pmi.setPrintable(dcg);
  }

  class TabChangeListener implements ChangeListener {
    String text;
    public void stateChanged(ChangeEvent e) {
      dtg.setDistanceText(dcg.getDistancePane().getText());
      text = dcg.getAlignmentPane().getText();
      bg.getDistanceComputationGUI().getAlignmentPane().setText(text);
      bg.getDistanceComputationGUI().getAlignmentPane().setCaretPosition(0);
      JTabbedPane tabPane = (JTabbedPane)e.getSource();
      Printable printable = (Printable)tabPane.getSelectedComponent();
      pmi.setPrintable(printable);
      Object object = tabPane.getSelectedComponent();
      if(object.getClass() == dtg.getClass()) {
        osmi.setEnabled(false);
      } else if(object.getClass() == bg.getClass()) {
        osmi.setEnabled(true);
        osmi.setTextPane(bg.getDistanceComputationGUI().getAlignmentPane());
      } else if(object.getClass() == dcg.getClass()) {
        osmi.setEnabled(true);
        osmi.setTextPane(dcg.getAlignmentPane());
      }

    }
  }

}
