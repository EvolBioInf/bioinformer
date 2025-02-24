package haubold.wrightfisher;

import java.awt.*;

import javax.swing.*;

/**
 * Description:
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Sep 11, 2005; time: 2:33:15 PM.
 */
public class TestWrightFisher extends JFrame {
  private static final long serialVersionUID = 1L;

  public static void main(String[] args) {
    WrightFisherGui wfg = new WrightFisherGui();
    JFrame frame1 = new TestWrightFisher();
    frame1.getContentPane().setLayout(new BorderLayout());
    frame1.getContentPane().add(wfg,BorderLayout.CENTER);
    frame1.setSize(new Dimension(500,700));
    frame1.setVisible(true);
  }

}
