package haubold.coalescent;

/**
 * @author Bernhard Haubold
 * Date: May 1, 2003; time: 3:49:23 PM.
 *
 * Description:
 */

import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.*;

public class TestCoalescent {

  public static void main(String[] args) {
    JFrame frame = new JFrame("Test Coalescent");
    frame.getContentPane().setLayout(new BorderLayout());


    JDesktopPane desktop = new JDesktopPane();

    CoalescentDemo cd = new CoalescentDemo("Coalescent","resources/help/coalescent/index.html");
    cd.setSize(new Dimension(100,100));
//		cd.constructHelpMenu(desktop);
    cd.setVisible(true);
    desktop.add(cd);

    frame.getContentPane().add(desktop,BorderLayout.CENTER);
    frame.setSize(new Dimension(400,400));
    frame.setVisible(true);
  }

}
