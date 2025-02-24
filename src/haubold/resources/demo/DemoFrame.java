package haubold.resources.demo;
import java.awt.print.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Nov 28, 2003; time: 8:56:45 PM.
 *
 * Description:
 */
public class DemoFrame extends JInternalFrame implements Printable {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private DemoHelpMenu helpMenu;
  private DemoFileMenu fileMenu;
  private JToolBar taskBar;
  private JButton taskButton;
  private String helpPath;
  ArrayList <DemoFrame>activeFrames;


  /**
   * NB: Only the no-args constructor works for this class.
   *
   */
  public DemoFrame() {
    super("", true, true, true, true);
    initialize();
  }


  private void initialize() {
    JMenuBar menuBar = new JMenuBar();
    this.setJMenuBar(menuBar);
    fileMenu = new DemoFileMenu(this);
    fileMenu.getPrintMenuItem().setPrintable(this);
    this.getJMenuBar().add(fileMenu);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
  }

  public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
    if (pageIndex >= 1) {
      return Printable.NO_SUCH_PAGE;
    }
    g.translate((int)pageFormat.getImageableX(),(int)pageFormat.getImageableY());
    paint(g);
    return Printable.PAGE_EXISTS;
  }

  public void constructHelpMenu(JDesktopPane desktop, String title) {
    helpMenu = new DemoHelpMenu(helpPath, desktop, title);
    helpMenu.setToolBar(taskBar);
    this.getJMenuBar().add(helpMenu);
  }

  /**
   * @return
   */
  public String getHelpPath() {
    return helpPath;
  }

  /**
   * @param string
   */
  public void setHelpPath(String string) {
    helpPath = string;
  }

  /**
   * @return
   */
  public DemoFileMenu getFileMenu() {
    return fileMenu;
  }

  /**
   * @return
   */
  public DemoHelpMenu getHelpMenu() {
    return helpMenu;
  }

  /**
   * @param menu
   */
  public void setFileMenu(DemoFileMenu menu) {
    fileMenu = menu;
  }

  /**
   * @param menu
   */
  public void setHelpMenu(DemoHelpMenu menu) {
    helpMenu = menu;
  }

  /**
   * @return
   */
  public JToolBar getToolBar() {
    return taskBar;
  }

  /**
   * @return
   */
  public JButton getToolButton() {
    return taskButton;
  }

  /**
   * @param bar
   */
  public void setToolBar(JToolBar bar) {
    taskBar = bar;
    fileMenu.setToolBar(taskBar);
  }

  /**
   * @param button
   */
  public void setToolButton(JButton button) {
    taskButton = button;
    fileMenu.setToolButton(taskButton);
  }


  public ArrayList getActiveFrames() {
    return activeFrames;
  }


  public void setActiveFrames(ArrayList <DemoFrame>activeFrames) {
    this.activeFrames = activeFrames;
    fileMenu.setActiveFrames(activeFrames);
  }


}
