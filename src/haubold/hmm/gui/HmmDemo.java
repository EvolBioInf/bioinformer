package haubold.hmm.gui;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;

import haubold.resources.demo.*;
import haubold.resources.util.*;

/**
 * @author haubold
 * Date: Nov 10, 2003; time: 4:49:19 PM.
 *
 * Description:
 */
public class HmmDemo extends DemoFrame {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  JTabbedPane tp;
  PrintMenuItem pmi;
  SequencePanel sequencePanel;
  BaumWelchPanel baumWelchPanel;
  HmmDemo demo;
  public HmmDemo(String titleString, String helpPath) {
    demo = this;
    setTitle(titleString);
    setHelpPath(helpPath);
    this.getContentPane().setLayout(new BorderLayout());
    tp = new JTabbedPane();
    this.getContentPane().add(tp,BorderLayout.CENTER);
    sequencePanel  = new SequencePanel();
    baumWelchPanel = new BaumWelchPanel(sequencePanel.getHmm());
    tp.add("Detect Hidden States", sequencePanel);
    tp.add("Estimate HMM", baumWelchPanel);
    // Add change listener
    TabChangeListener tcl = new TabChangeListener();
    tp.addChangeListener(tcl);
    // Add printing to file Menu
    pmi = this.getFileMenu().getPrintMenuItem();
    pmi.setPrintable(sequencePanel);
    DemoFileMenu fileMenu = getFileMenu();
    JMenuItem exitMenuItem = fileMenu.getExitMenuItem();
    pmi = new PrintMenuItem("Print", KeyEvent.VK_P, sequencePanel);
    pmi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
    fileMenu.add(exitMenuItem);
    setFileMenu(fileMenu);
  }

  class TabChangeListener implements ChangeListener {
    int i;
    public void stateChanged(ChangeEvent e) {
      JTabbedPane tabPane = (JTabbedPane)e.getSource();
      demo.doLayout();
      demo.repaint();
      baumWelchPanel.getModelPanel().doLayout();
      baumWelchPanel.getModelPanel().repaint();
      Printable printable = (Printable)tabPane.getSelectedComponent();
      pmi.setPrintable(printable);
    }
  }

}
