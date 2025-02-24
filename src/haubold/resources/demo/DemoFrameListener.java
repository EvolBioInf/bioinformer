package haubold.resources.demo;

import javax.swing.event.*;
import javax.swing.*;

/**
 * Description: Listen to internal frame events.
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: May 29, 2004; time: 3:40:29 PM.
 */
public class DemoFrameListener implements InternalFrameListener {
  JToolBar taskBar;
  JButton taskButton;

  public DemoFrameListener() {

  }

  public DemoFrameListener(JToolBar taskBar, JButton taskButton) {
    this.taskBar = taskBar;
    this.taskButton = taskButton;
  }

  /* (non-Javadoc)
   * @see javax.swing.event.InternalFrameListener#internalFrameOpened(javax.swing.event.InternalFrameEvent)
   */
  public void internalFrameOpened(InternalFrameEvent arg0) {
//		System.out.println("DemoFrame - internalFrame opened");

  }

  /* (non-Javadoc)
   * @see javax.swing.event.InternalFrameListener#internalFrameClosing(javax.swing.event.InternalFrameEvent)
   */
  public void internalFrameClosing(InternalFrameEvent arg0) {
    if(taskBar != null && taskButton != null) {
      taskBar.remove(taskButton);
      if(taskBar.getComponentCount()>0) {
        taskBar.doLayout();
      }
      taskBar.repaint();
    }
  }

  /* (non-Javadoc)
   * @see javax.swing.event.InternalFrameListener#internalFrameClosed(javax.swing.event.InternalFrameEvent)
   */
  public void internalFrameClosed(InternalFrameEvent arg0) {


  }

  /* (non-Javadoc)
   * @see javax.swing.event.InternalFrameListener#internalFrameIconified(javax.swing.event.InternalFrameEvent)
   */
  public void internalFrameIconified(InternalFrameEvent arg0) {
//		System.out.println("DemoFrame - internalFrame iconified");

  }

  /* (non-Javadoc)
   * @see javax.swing.event.InternalFrameListener#internalFrameDeiconified(javax.swing.event.InternalFrameEvent)
   */
  public void internalFrameDeiconified(InternalFrameEvent arg0) {
//		System.out.println("DemoFrame - internalFrame deiconified");

  }

  /* (non-Javadoc)
   * @see javax.swing.event.InternalFrameListener#internalFrameActivated(javax.swing.event.InternalFrameEvent)
   */
  public void internalFrameActivated(InternalFrameEvent arg0) {
//		System.out.println("DemoFrame - internalFrame activated");

  }

  /* (non-Javadoc)
   * @see javax.swing.event.InternalFrameListener#internalFrameDeactivated(javax.swing.event.InternalFrameEvent)
   */
  public void internalFrameDeactivated(InternalFrameEvent arg0) {
//		System.out.println("DemoFrame - internalFrame deactivated");

  }

  /**
   * @return
   */
  public JToolBar getTaskBar() {
    return taskBar;
  }

  /**
   * @return
   */
  public JButton getTaskButton() {
    return taskButton;
  }

  /**
   * @param bar
   */
  public void setTaskBar(JToolBar bar) {
    taskBar = bar;
  }

  /**
   * @param button
   */
  public void setTaskButton(JButton button) {
    taskButton = button;
  }

}
