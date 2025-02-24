package haubold.birkdesktop;

import haubold.resources.util.*;
import java.awt.event.*;
import javax.swing.*;


public class EvolutionMenu extends JMenu {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public EvolutionMenu(BirkDesktop parentFrame) {

    JMenuItem menuItem;
    FrameCreationListener fcl = null;
    Object args[];
    BirkhaeuserGUIComponents bgc;

    bgc = new BirkhaeuserGUIComponents();
    this.setText("Evolution");
    this.setBackground(bgc.getColor2());
    this.setMnemonic('E');

    // Phylogeny
    menuItem = new JMenuItem("Phylogeny");
    menuItem.setMnemonic('y');
    args = new Object[] {"Phylogeny",
                         parentFrame.getHelpPath() + "defaultHelp" + "/index.html"
                        };
    try {
      fcl = new FrameCreationListener(
        Class.forName("haubold.phylogeny.distance.DistanceTreeDemo"),
        args,parentFrame);
    } catch(Exception e) {
      e.printStackTrace();
    }
    menuItem.addActionListener(fcl);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,ActionEvent.CTRL_MASK));
    this.add(menuItem);
    // Drift
    menuItem = new JMenuItem("Drift");
    menuItem.setMnemonic('f');
    args = new Object[] {"Drift",parentFrame.getHelpPath() + "defaultHelp" + "/index.html"};
    try {
      fcl = new FrameCreationListener(
        Class.forName("haubold.drift.DriftDemo"),
        args,parentFrame);
    } catch(Exception e) {
      e.printStackTrace();
    }
    menuItem.addActionListener(fcl);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,ActionEvent.CTRL_MASK));
    this.add(menuItem);
    // Wright-Fisher
    menuItem = new JMenuItem("Wright-Fisher");
    menuItem.setMnemonic('w');
    args = new Object[] {"Wright-Fisher",parentFrame.getHelpPath() + "defaultHelp" + "/index.html"};
    try {
      fcl = new FrameCreationListener(
        Class.forName("haubold.wrightfisher.WrightFisherDemo"),
        args,parentFrame);
    } catch(Exception e) {
      e.printStackTrace();
    }
    menuItem.addActionListener(fcl);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,ActionEvent.CTRL_MASK));
    this.add(menuItem);
    // Coalescent
    menuItem = new JMenuItem("Coalescent");
    menuItem.setMnemonic('C');
    args = new Object[] {"Coalescent",parentFrame.getHelpPath() + "defaultHelp" + "/index.html"};
    try {
      fcl = new FrameCreationListener(
        Class.forName("haubold.coalescent.CoalescentDemo"),
        args,parentFrame);
    } catch(Exception e) {
      e.printStackTrace();
    }
    menuItem.addActionListener(fcl);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
    this.add(menuItem);
  }
}
