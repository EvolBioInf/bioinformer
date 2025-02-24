package haubold.resources.util;
import javax.swing.*;
import java.net.*;
import java.awt.*;
/**
 * @author Bernhard Haubold
 * Date: Jun 12, 2003; time: 11:54:22 AM.
 *
 * Description:
 */
public class BioinformerPanel extends JPanel {
  private static final long serialVersionUID = 1L;
  JLabel imageLabel;
  JTextPane textPane;
  BirkhaeuserGUIComponents bgc;
  public BioinformerPanel(String version) {
    this.setLayout(new BorderLayout());
    String imageName = "haubold/resources/images/bioinformer.gif";
    URL iconURL = ClassLoader.getSystemResource(imageName);

    ImageIcon icon = new ImageIcon(iconURL);
    imageLabel = new JLabel(icon);
    this.add(imageLabel,BorderLayout.CENTER);
    bgc = new BirkhaeuserGUIComponents();
    textPane = new JTextPane();
    textPane.setBackground(bgc.getColor2());
    textPane.setText("Version " + version+ "\nWritten by Bernhard Haubold, " +
                     "University of Applied Sciences Weihenstephan, " +
                     "Germany.");
    this.add(textPane,BorderLayout.SOUTH);
  }

}
