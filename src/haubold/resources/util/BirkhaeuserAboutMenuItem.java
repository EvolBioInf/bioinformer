package haubold.resources.util;

/**
 * @author Bernhard Haubold
 * Date: May 3, 2003; time: 11:04:29 AM.
 *
 * Description:
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BirkhaeuserAboutMenuItem extends JMenuItem {
  private static final long serialVersionUID = 1L;
  JDialog aboutDialog;
  JOptionPane pane;
  String titleString = "About";
  BirkhaeuserGUIComponents bgc;

  public BirkhaeuserAboutMenuItem(String version) {
    AboutItemListener aboutItemListener = new AboutItemListener();
    this.addActionListener(aboutItemListener);
    this.setText("About");
    bgc = new BirkhaeuserGUIComponents();
    pane = new JOptionPane(new BirkhaeuserPane(version));
    aboutDialog = pane.createDialog(this, titleString);
  }

  class AboutItemListener implements ActionListener {
    Dimension dim = new Dimension(400,700);
    public void actionPerformed(ActionEvent e) {
      aboutDialog.setSize(dim);
      aboutDialog.setLocationRelativeTo((Component)e.getSource());
      aboutDialog.setVisible(true);
    }
  }
  /**
   * Returns the titleString.
   * @return String
   */
  public String getTitleString() {
    return titleString;
  }
  /**
   * Sets the titleString.
   * @param titleString The titleString to set
   */
  public void setTitleString(String titleString) {
    this.titleString = titleString;
    aboutDialog = pane.createDialog(this, titleString);
  }
}
