package haubold.hmm.gui;
import java.awt.*;
/**
 * @author haubold
 * Date: Nov 26, 2003; time: 11:13:55 PM.
 *
 * Description:
 */
public class TimerThread extends Thread {

  ModelPanel panel;
  boolean interrupted;
  Graphics2D g2;
  int h, w;
  RenderingHints qualityHints;
  boolean up;
  Thread thread;

  public TimerThread(ModelPanel panel, Thread thread) {
    this.panel = panel;
    this.thread = thread;
    h = panel.getHeight();
    w = panel.getWidth();
    g2 = (Graphics2D) panel.getGraphics();
    qualityHints =
      new RenderingHints(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON);
    qualityHints.put(
      RenderingHints.KEY_RENDERING,
      RenderingHints.VALUE_RENDER_QUALITY);
    g2.setRenderingHints(qualityHints);
  }

  public void run() {
    int i;
    while (thread.isAlive()) {
      for (i = 0; i <= 100; i++) {
        try {
          sleep(20);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
        panel.setProgress(i);
      }
      for (i = 100; i >= 0; i--) {
        try {
          sleep(20);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
        panel.setProgress(i);
      }
    }
    panel.setProgress(0);
  }
  /**
   * @return
   */
  public boolean isInterrupted() {
    return interrupted;
  }

  /**
   * @param b
   */
  public void setInterrupted(boolean b) {
    interrupted = b;
  }

}
