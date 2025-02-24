package haubold.hmm.gui;
import java.awt.BorderLayout;

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
    HmmDemo hd = new HmmDemo("HMM", "resources/help/hmm/index.html");
    System.out.print("tf  - 4");
    testFrame.getContentPane().add(hd,BorderLayout.CENTER);
    testFrame.setSize(600,600);
    testFrame.setVisible(true);
  }

}
