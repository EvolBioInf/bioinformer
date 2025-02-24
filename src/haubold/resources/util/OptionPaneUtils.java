package haubold.resources.util;

/**
 * @author Bernhard Haubold
 * Date: Apr 15, 2003; time: 7:26:48 AM.
 *
 * Description:
 */

import javax.swing.*;

public class OptionPaneUtils {

  public static JOptionPane getNarrowOptionPane(int lineLength) {
    class NarrowOptionPane extends JOptionPane {
      private static final long serialVersionUID = 1L;
      int lineLength;
      NarrowOptionPane(int lineLength) {
        this.lineLength = lineLength;
      }
      @SuppressWarnings("unused")
      public int getLineLength() {
        return lineLength;
      }
    }
    return new NarrowOptionPane(lineLength);
  }

}
