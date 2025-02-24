package haubold.phylogeny.distance;

import javax.swing.*;

/**
 * @author Bernhard Haubold
 * Date: Jun 22, 2003; time: 8:38:48 AM.
 *
 * Description: A JTextPane wrapped into a JScrollPane ready
 * for the representation of equally spaced character data.
 */
public class DataTextPane extends JTextPane {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  JTextPane textPane;

  /**
   * Returns the textPane.
   * @return JTextPane
   */
  public JTextPane getTextPane() {
    return textPane;
  }

}
