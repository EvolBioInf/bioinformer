package haubold.resources.graphics;
import javax.swing.*;
import java.awt.*;
/**
 * @author Bernhard Haubold
 * Date: Jun 14, 2003; time: 11:07:09 AM.
 *
 * Description:
 */
public class TestDrawing {

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    Drawing gp = new Drawing();
    gp.setLayout(new BorderLayout());
    Gene gene = new Gene(100,50,150);
    gp.addGene(gene);
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(gp,BorderLayout.CENTER);
    frame.setSize(500,500);
    frame.setVisible(true);
  }
}
