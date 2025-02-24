package haubold.phylogeny.distance;

/**
 * @author Bernhard Haubold
 * Date: Oct 4, 2003; time: 12:03:38 PM.
 *
 * Description:
 */
import java.awt.*;
import javax.swing.*;

public class TestTree extends JFrame {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public TestTree() {
    DistanceTreeDemo dtd = new DistanceTreeDemo("Distance Tree", "resources/help/phylogeny/index.html");
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(dtd,BorderLayout.CENTER);
  }

  public static void main(String[] args) {
    TestTree testTree = new TestTree();
    testTree.setSize(500,500);
    testTree.setVisible(true);
  }

}
