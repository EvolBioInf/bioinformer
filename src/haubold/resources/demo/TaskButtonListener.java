package haubold.resources.demo;
import javax.swing.*;
import java.awt.event.*;
/**
 * Description: Listen to the Buttons in the task menu.
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: May 29, 2004; time: 2:55:36 PM.
 */
public class TaskButtonListener implements ActionListener {
  JInternalFrame internalFrame;
  JDesktopPane desktop;
  public TaskButtonListener(JInternalFrame internalFrame, JDesktopPane desktop) {
    this.internalFrame = internalFrame;
    this.desktop = desktop;
  }
  public void actionPerformed(ActionEvent arg0) {
    desktop.moveToFront(internalFrame);
    desktop.getDesktopManager().activateFrame(internalFrame);
  }
}
