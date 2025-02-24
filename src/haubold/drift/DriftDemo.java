package haubold.drift;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;

import javax.swing.*;
import javax.swing.event.*;

import haubold.resources.demo.*;
import haubold.resources.util.*;

/**
 * Description:
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Sep 7, 2005; time: 2:50:43 PM.
 */
public class DriftDemo extends DemoFrame {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  JTabbedPane tp;
  PrintMenuItem pmi;

  public DriftDemo(String titleString, String helpPath) {
    setTitle(titleString);
    setHelpPath(helpPath);
    this.getContentPane().setLayout(new BorderLayout());
    tp = new JTabbedPane();
    this.getContentPane().add(tp,BorderLayout.CENTER);
    MutationPanel mp = new MutationPanel();
    NoMutationPanel np = new NoMutationPanel();
    tp.add("Drift without Mutation", np);
    tp.add("Drift with Mutation", mp);
    // Add change listener
    TabChangeListener tcl = new TabChangeListener();
    tp.addChangeListener(tcl);
    // Add printing to file Menu
    pmi = this.getFileMenu().getPrintMenuItem();
    pmi.setPrintable(np);
    DemoFileMenu fileMenu = getFileMenu();
    JMenuItem exitMenuItem = fileMenu.getExitMenuItem();
    pmi = new PrintMenuItem("Print", KeyEvent.VK_P, np);
    pmi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
    fileMenu.add(exitMenuItem);
    setFileMenu(fileMenu);
  }

  class TabChangeListener implements ChangeListener {
    public void stateChanged(ChangeEvent e) {
      JTabbedPane tabPane = (JTabbedPane)e.getSource();
      Printable printable = (Printable)tabPane.getSelectedComponent();
      pmi.setPrintable(printable);
    }
  }
}
