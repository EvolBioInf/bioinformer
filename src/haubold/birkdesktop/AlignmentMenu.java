package haubold.birkdesktop;

import haubold.resources.util.BirkhaeuserGUIComponents;
import java.awt.event.*;
import javax.swing.*;


public class AlignmentMenu extends JMenu {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public AlignmentMenu(BirkDesktop parentFrame) {

    JMenuItem menuItem;
    FrameCreationListener fcl = null;
    Object args[];
    BirkhaeuserGUIComponents bgc;

    bgc = new BirkhaeuserGUIComponents();
    this.setText("Alignment");
    this.setBackground(bgc.getColor2());
    this.setMnemonic('A');
    // protein substitution matrices
    menuItem = new JMenuItem("Protein Substitution Matrices");
    menuItem.setMnemonic('t');
    args = new Object[] {"Protein Substitution Matrices",
                         parentFrame.getHelpPath() + "defaultHelp" + "/index.html"
                        };
    try {
      fcl = new FrameCreationListener(
        Class.forName("haubold.sm.SubstitutionMatrixDemo"),
        args,parentFrame);
    } catch(Exception e) {
      e.printStackTrace();
    }
    menuItem.addActionListener(fcl);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,ActionEvent.CTRL_MASK));
    this.add(menuItem);
    // number of alignments
    menuItem = new JMenuItem("Number of Alignments");
    menuItem.setMnemonic('n');
    args = new Object[] {"Number of Alignments",parentFrame.getHelpPath() + "defaultHelp" + "/index.html"};
    try {
      fcl = new FrameCreationListener(
        Class.forName("haubold.numAlignments.NumAlDemo"),
        args,parentFrame);
    } catch(Exception e) {
      e.printStackTrace();
    }
    menuItem.addActionListener(fcl);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
    this.add(menuItem);
    // pairwise alignment
    menuItem = new JMenuItem("Pairwise Alignment");
    menuItem.setMnemonic('a');
    args = new Object[] {"Pairwise Alignment",parentFrame.getHelpPath() + "defaultHelp" + "/index.html"};
    try {
      fcl = new FrameCreationListener(
        Class.forName("haubold.dp.PairwiseAlignmentDemo"),
        args,parentFrame);
    } catch(Exception e) {
      e.printStackTrace();
    }
    menuItem.addActionListener(fcl);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
    this.add(menuItem);
  }

}
