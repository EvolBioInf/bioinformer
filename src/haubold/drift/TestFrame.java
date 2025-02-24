package haubold.drift;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

/**
 * @author haubold
 * Date: Nov 1, 2003; time: 1:26:47 PM.
 *
 * Description:
 */
public class TestFrame extends JFrame {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public static void main(String[] args) {
    System.out.print("tf  - 1");
    TestFrame testFrame = new TestFrame();
    System.out.print("tf  - 2");
    testFrame.getContentPane().setLayout(new BorderLayout());
    System.out.print("tf  - 3");
    MutationPanel mp = new MutationPanel();
    System.out.print("tf  - 4");
    testFrame.getContentPane().add(mp,BorderLayout.CENTER);
    testFrame.setSize(600,600);
    testFrame.setVisible(true);

    testFrame.addWindowListener(
    new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

}
