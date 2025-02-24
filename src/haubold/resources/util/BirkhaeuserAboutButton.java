package haubold.resources.util;

/**
 * @author Bernhard Haubold
 * Date: Apr 19, 2003; time: 1:14:58 PM.
 *
 * Description:
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BirkhaeuserAboutButton extends JButton {
  private static final long serialVersionUID = 1L;
  JDialog aboutDialog;
  JOptionPane pane;
  String titleString = "About";
  JComponent component;
  BirkhaeuserGUIComponents bgc;
  int xDim = 100;
  int yDim = 160;
  /*
   * Supply a component for display.
   */
  public BirkhaeuserAboutButton(JComponent component) {
    init();
    pane = new JOptionPane(component);
    pane.setSize(new Dimension(xDim,yDim));
    pane.setMaximumSize(new Dimension(xDim,yDim));
    pane.setBackground(bgc.getColor1());
    aboutDialog.setForeground(bgc.getColor2());
  }

  private void init() {
    bgc = new BirkhaeuserGUIComponents();
    AboutButtonListener aboutButtonListener = new AboutButtonListener();
    this.addActionListener(aboutButtonListener);
    this.setText("About");
  }

  /*
   * Display the Birkhaeuser page.
   */
  public BirkhaeuserAboutButton() {
    init();
    pane = new JOptionPane(new BirkhaeuserPane());
    pane.setBackground(bgc.getColor1());
    aboutDialog = pane.createDialog(this, titleString);
  }

  class AboutButtonListener implements ActionListener {
    Dimension dim = new Dimension(xDim,yDim);
    public void actionPerformed(ActionEvent e) {
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
