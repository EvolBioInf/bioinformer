package haubold.wrightfisher;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import haubold.resources.demo.DemoFileMenu;
import haubold.resources.demo.DemoFrame;
import haubold.resources.util.PrintMenuItem;

/**
 * Description:
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Sep 12, 2005; time: 3:25:53 PM.
 */
public class WrightFisherDemo extends DemoFrame {
  private static final long serialVersionUID = 1L;
  PrintMenuItem pmi;
  public WrightFisherDemo(String titleString, String helpPath) {
    setTitle(titleString);
    setHelpPath(helpPath);
    this.getContentPane().setLayout(new BorderLayout());
    WrightFisherGui wfg = new WrightFisherGui();
    this.getContentPane().add(wfg);
    // Add printing to file Menu
    pmi = this.getFileMenu().getPrintMenuItem();
    pmi.setPrintable(wfg);
    DemoFileMenu fileMenu = getFileMenu();
    JMenuItem exitMenuItem = fileMenu.getExitMenuItem();
    pmi = new PrintMenuItem("Print", KeyEvent.VK_P, wfg);
    pmi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
    fileMenu.add(exitMenuItem);
    setFileMenu(fileMenu);
  }
}
