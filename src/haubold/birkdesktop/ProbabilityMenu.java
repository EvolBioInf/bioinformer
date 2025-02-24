package haubold.birkdesktop;

import haubold.resources.util.BirkhaeuserGUIComponents;
import java.awt.event.*;

import javax.swing.*;


public class ProbabilityMenu extends JMenu {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public ProbabilityMenu(BirkDesktop parentFrame) {

    JMenuItem menuItem;
    FrameCreationListener fcl = null;
    Object args[];
    BirkhaeuserGUIComponents bgc;

    bgc = new BirkhaeuserGUIComponents();
    this.setText("Probability");
    this.setBackground(bgc.getColor2());
    this.setMnemonic('P');
    // Hidden Markov Model
    menuItem = new JMenuItem("Hidden Markov Model");
    menuItem.setMnemonic('i');
    args = new Object[] {"Hidden Markov Model",
                         parentFrame.getHelpPath() + "defaultHelp" + "/index.html"
                        };
    try {
      fcl = new FrameCreationListener(
        Class.forName("haubold.hmm.gui.HmmDemo"),
        args,parentFrame);
    } catch(Exception e) {
      e.printStackTrace();
    }
    menuItem.addActionListener(fcl);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,ActionEvent.CTRL_MASK));
    this.add(menuItem);
  }

}
