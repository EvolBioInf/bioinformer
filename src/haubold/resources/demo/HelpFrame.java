package haubold.resources.demo;
import haubold.resources.util.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
/**
 * Description: Internal frame for presenting help content.
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Mar 28, 2004; time: 9:02:22 AM.
 */
public class HelpFrame extends JInternalFrame implements Printable {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  JComponent component;
  JDesktopPane desktop;
  JMenuBar menuBar;
  JToolBar toolBar;
  JButton toolButton;
  Dimension dimension;
  DemoFileMenu fileMenu;
  DemoFrameListener dfl;


  public HelpFrame(String title, JComponent component, JDesktopPane desktop, Dimension dimension) {
    super(title, true, true, true, true);
    this.component = component;
    this.desktop = desktop;
    this.dimension = dimension;
    initialize();
  }

  private void initialize() {
    ImageIcon icon = Icons.getHelpIcon();
    setFrameIcon(icon);
    setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    getContentPane().setLayout(new BorderLayout());
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    getContentPane().add(panel,BorderLayout.SOUTH);
    getContentPane().add(component, BorderLayout.CENTER);
    desktop.add(this);
    menuBar = new JMenuBar();
    fileMenu = new DemoFileMenu(this);
    fileMenu.getPrintMenuItem().setPrintable(this);
    menuBar.add(fileMenu);
    this.setJMenuBar(menuBar);
    this.setSize(dimension);
    this.setVisible(true);
    this.setBounds(30,40,350,500);
    dfl = new DemoFrameListener();
    this.addInternalFrameListener(dfl);
  }

  public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
    if (pageIndex >= 1) {
      return Printable.NO_SUCH_PAGE;
    }
    g.translate((int)pageFormat.getImageableX(),(int)pageFormat.getImageableY());
    paint(g);
    return Printable.PAGE_EXISTS;
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
    fileMenu.setToolBar(toolBar);
    dfl.setTaskBar(toolBar);
  }

  /**
   * @param button
   */
  public void setToolButton(JButton button) {
    toolButton = button;
    fileMenu.setToolButton(toolButton);
    dfl.setTaskButton(toolButton);
  }

}
