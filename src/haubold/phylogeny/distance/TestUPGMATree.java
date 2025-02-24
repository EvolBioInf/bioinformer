package haubold.phylogeny.distance;

/**
 * @author Bernhard Haubold
 * Date: Jun 8, 2003; time: 2:14:28 PM.
 *
 * Description:
 */


import java.awt.BorderLayout;
import javax.swing.*;

public class TestUPGMATree {

  public static void main(String[] args) {
    JFrame frame = new JFrame();
//		treePanel.setTree(tree.getTree());
//		DistanceTreeGUI distanceDemo = new DistanceTreeGUI();
    DistanceTreeDemo dtd = new DistanceTreeDemo("Distance Tree", "resources/help/phylogeny/index.html");
    frame.getContentPane().setLayout(new BorderLayout());
//		frame.getContentPane().add(distanceDemo,BorderLayout.CENTER);
    frame.getContentPane().add(dtd,BorderLayout.CENTER);
//		frame.getContentPane().add(treePanel,BorderLayout.CENTER);
    frame.setSize(520,400);
    frame.setVisible(true);

  }

}
