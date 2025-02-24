package haubold.resources.util;

/**
 * @author Bernhard Haubold
 * Date: May 7, 2003; time: 1:37:51 PM.
 *
 * Description:
 */
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class BirkhaeuserSplashWindow extends JWindow {
  private static final long serialVersionUID = 1L;
  Thread closeThread;
  boolean running = false;

  public BirkhaeuserSplashWindow(String imageFileName, Frame f, int waitTime, String version) {
    super(f);
    this.getContentPane().setLayout(new BorderLayout());
    BioinformerPanel bp = new BioinformerPanel(version);
    getContentPane().add(bp);

    pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension labelSize = bp.getPreferredSize();
    setLocation(screenSize.width/2-(labelSize.width/2),
                screenSize.height/2-(labelSize.height/2));
    MyMouseListener mml = new MyMouseListener();
    bp.addMouseListener(mml);
    final int pause = waitTime;
    final Runnable closerRunner = new Runnable() {
      public void run() {
        try {
          Thread.sleep(pause);
        } catch(Exception ex) {
          ex.printStackTrace();
        }
        setVisible(false);
        dispose();
      }
    };
    Runnable waitRunner = new Runnable() {
      public void run() {
        setVisible(true);
        try {
          Thread.sleep(pause);
          if(running)
            closeThread.start();
        } catch(Exception e) {
          e.printStackTrace();
        }
      }
    };
    Thread splashThread = new Thread(waitRunner,"SplashThread");
    closeThread = new Thread(closerRunner, "CloseThread");
    splashThread.start();
    running = true;
  }

  class MyMouseListener implements MouseListener {
    public void mouseExited(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {
    }
    public void mouseClicked(MouseEvent e) {
//			closeThread.start();
    }
    public void mousePressed(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
  }

  public void close() {
    closeThread.start();
    running = false;
  }

}