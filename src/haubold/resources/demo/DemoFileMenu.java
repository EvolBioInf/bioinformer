package haubold.resources.demo;
import javax.swing.*;
import java.awt.event.*;
import java.awt.print.*;
import java.util.ArrayList;
import haubold.resources.util.*;
/**
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Nov 28, 2003; time: 8:50:28 PM.
 *
 * Description:
 */
public class DemoFileMenu extends JMenu {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  JInternalFrame frame;
  JMenuItem exitMenuItem;
  PrintMenuItem printMenuItem;
  JToolBar toolBar;
  JButton toolButton;
  ArrayList activeFrames;

  public DemoFileMenu(JInternalFrame frame) {
    Printable printable = null;
    this.frame = frame;
    this.setText("File");
    this.setMnemonic('F');
    // File->Print, P - Memonic
    printMenuItem = new PrintMenuItem("Print", KeyEvent.VK_P, printable);
    printMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
    this.add(printMenuItem);
    // File->Exit, X - Memonic
    exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
    exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
    FileActionListener fileActionListener = new FileActionListener();
    exitMenuItem.addActionListener(fileActionListener);
    this.add(exitMenuItem);
  }


  class FileActionListener implements ActionListener {
    int copynumber;
    public void actionPerformed(ActionEvent e) {
      if (e.getActionCommand() == "Exit") {
        toolBar.remove(toolButton);
        toolBar.doLayout();
        toolBar.repaint();
        activeFrames.remove(frame);
        frame.dispose();
      }
    }
  }

  /**
   * @return
   */
  public JMenuItem getExitMenuItem() {
    return exitMenuItem;
  }

  /**
   * @param item
   */
  public void setExitMenuItem(JMenuItem item) {
    exitMenuItem = item;
  }

  /**
   * @return
   */
  public PrintMenuItem getPrintMenuItem() {
    return printMenuItem;
  }

  /**
   * @param item
   */
  public void setPrintMenuItem(PrintMenuItem item) {
    printMenuItem = item;
  }

  /**
   * @return
   */
  public JToolBar getToolBar() {
    return toolBar;
  }

  /**
   * @return
   */
  public JButton getToolButton() {
    return toolButton;
  }

  /**
   * @param bar
   */
  public void setToolBar(JToolBar bar) {
    toolBar = bar;
  }

  /**
   * @param button
   */
  public void setToolButton(JButton button) {
    toolButton = button;
  }

  public ArrayList getActiveFrames() {
    return activeFrames;
  }

  public void setActiveFrames(ArrayList activeFrames) {
    this.activeFrames = activeFrames;
  }
}
