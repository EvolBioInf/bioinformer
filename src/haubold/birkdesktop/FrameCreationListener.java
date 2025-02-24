package haubold.birkdesktop;

/**
 * @author Bernhard Haubold
 * Date: May 1, 2003; time: 2:44:03 PM.
 *
 * Description:
 */

import javax.swing.*;

import java.awt.Rectangle;
import java.awt.event.*;
import java.lang.reflect.*;

import haubold.resources.demo.*;

public class FrameCreationListener implements ActionListener {
  private JDesktopPane desktop;
  private DemoFrame demo = null;
  private JToolBar toolBar;
  private JButton toolButton;
  Class demoDefinition;
  Class[] argsClass = new Class[] { String.class, String.class };
  Object[] args;
  Constructor demoConstructor;
  Rectangle bounds;
  Icon frameIcon;
  int numComponents;
  BirkDesktop parentFrame;

  public FrameCreationListener(Class demoDefinition, Object[] args,
                               BirkDesktop parentFrame) {

    this.args = args;
    this.parentFrame = parentFrame;

    this.bounds = parentFrame.getDemoBounds();
    this.desktop = parentFrame.getDesktop();
    this.toolBar = parentFrame.getTaskBar();

    Class[] argsClass;

    argsClass = new Class[] { String.class, String.class };
    try {
      demoConstructor = demoDefinition.getConstructor(argsClass);
    } catch (Exception e) {
      e.printStackTrace();
    }
    numComponents = desktop.getComponents().length;
  }

  public void actionPerformed(ActionEvent e) {
    DemoFrameListener dfl = null;
    int location;

    location = (desktop.getComponents().length - numComponents) * 50;
    location = location
               % Math.min(desktop.getHeight(), desktop.getWidth() - 10);
    try {
      demo = (DemoFrame) demoConstructor.newInstance(args);
      desktop.add(demo);
      bounds.setLocation(location, location);
      demo.setBounds(bounds);
      demo.setFrameIcon(frameIcon);
      demo.setToolBar(toolBar);
      demo.addInternalFrameListener(dfl);
      toolButton = new JButton(demo.getTitle());
      toolButton.addActionListener(new TaskButtonListener(demo, desktop));
      demo.setToolButton(toolButton);
      demo.setFrameIcon(parentFrame.getFrameIcon());
      dfl = new DemoFrameListener(toolBar, toolButton);
      demo.addInternalFrameListener(dfl);
      demo.setActiveFrames(parentFrame.getActiveFrames());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    toolBar.add(toolButton);
    toolBar.doLayout();
    toolBar.repaint();
    demo.setVisible(true);
    parentFrame.getActiveFrames().add(demo);
    demo.doLayout();
    demo.repaint();
  }
}
