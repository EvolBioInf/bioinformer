package haubold.sm;
import java.awt.BorderLayout;

import haubold.resources.demo.*;
import javax.swing.*;
import javax.swing.event.*;
/**
 * Description: Top container of the program demonstrating PAM and
 * BLOSUM substitution matrices.
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Apr 24, 2004; time: 3:30:39 PM.
 */
public class SubstitutionMatrixDemo extends DemoFrame {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  JTabbedPane tabbedPane;
  PamPanel pamPanel;
  BlosumPanel blosumPanel;
  SubstitutionMatrixReader smr;
  public SubstitutionMatrixDemo(String titleString, String helpPath) {
    setTitle(titleString);
    setHelpPath(helpPath);
    // Construct tabbedPane
    tabbedPane = new JTabbedPane();
    pamPanel = new PamPanel();
    blosumPanel = new BlosumPanel();
    tabbedPane.addTab("PAM",pamPanel);
    tabbedPane.addTab("BLOSUM",blosumPanel);
    TabChangeListener tcl = new TabChangeListener();
    tabbedPane.addChangeListener(tcl);
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(tabbedPane);
    smr = new SubstitutionMatrixReader();
    // Add printing to file Menu
//		this.getFileMenu().getPrintMenuItem().setPrintable(lodPanel);
  }

  class TabChangeListener implements ChangeListener {
    public void stateChanged(ChangeEvent e) {

    }
  }

}
