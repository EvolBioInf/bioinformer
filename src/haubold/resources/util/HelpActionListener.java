package haubold.resources.util;

/**
 * @author haubold
 * Date: Nov 15, 2003; time: 9:32:53 AM.
 *
 * Description:
 */
import haubold.resources.demo.HelpFrame;
import haubold.resources.demo.TaskButtonListener;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;

public class HelpActionListener implements ActionListener {
  String helpFrameTitleString;
  JComponent component;
  Dimension helpFrameDimension;
  JDesktopPane desktop;
  HelpFrame helpFrame = null;
  JToolBar toolBar;
  JButton toolButton;

  public HelpActionListener(
    JDesktopPane desktop,
    JComponent component,
    Dimension dimension,
    String titleString) {
    this.desktop = desktop;
    this.component = component;
    this.helpFrameDimension = dimension;
    this.helpFrameTitleString = "Help: " + titleString;
  }

  public void actionPerformed(ActionEvent e) {
    if (helpFrame == null) {
      helpFrame =
        new HelpFrame(
        helpFrameTitleString,
        component,
        desktop,
        helpFrameDimension);
    }
    toolButton = new JButton(helpFrameTitleString);
    toolButton.addActionListener(
      new TaskButtonListener(helpFrame, desktop));
    toolBar.add(toolButton);
    if(toolBar.getComponentCount()>0) {
      toolBar.doLayout();
    }
    toolBar.repaint();
    helpFrame.setToolBar(toolBar);
    helpFrame.setToolButton(toolButton);
    helpFrame.setVisible(true);
  }
  /**
   * @return
   */
  public JToolBar getToolBar() {
    return toolBar;
  }

  /**
   * @param bar
   */
  public void setToolBar(JToolBar bar) {
    toolBar = bar;
  }

  /**
   * @return
   */
  public JButton getToolButton() {
    return toolButton;
  }

  /**
   * @param button
   */
  public void setToolButton(JButton button) {
    toolButton = button;
  }

}
