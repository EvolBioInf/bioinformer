package haubold.resources.util;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;
/**
 * @author haubold
 * Date: Nov 16, 2003; time: 1:23:11 PM.
 *
 * Description:
 */
public class BasicEditMenu extends JMenu {
  private static final long serialVersionUID = 1L;
  JTextComponent textPane;

  public BasicEditMenu(String text, JTextComponent textPane) {
    this.textPane = textPane;
    this.setText(text);
    // Create menu items for edit menu
    JMenuItem cutMenuItem = new JMenuItem("Cut", KeyEvent.VK_U);
    JMenuItem copyMenuItem = new JMenuItem("Copy", KeyEvent.VK_C);
    JMenuItem pasteMenuItem = new JMenuItem("Paste", KeyEvent.VK_P);
    JMenuItem selectAllMenuItem = new JMenuItem("Select All", KeyEvent.VK_A);
    // Accelerators for edit menu
    cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
    copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
    pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK));
    selectAllMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
    // ActionListeners for edit menu
    CutActionListener xal = new CutActionListener();
    CopyActionListener copy = new CopyActionListener();
    PasteActionListener pal = new PasteActionListener();
    SelectAllActionListener select = new SelectAllActionListener();
    cutMenuItem.addActionListener(xal);
    copyMenuItem.addActionListener(copy);
    pasteMenuItem.addActionListener(pal);
    selectAllMenuItem.addActionListener(select);
    this.add(cutMenuItem);
    this.add(copyMenuItem);
    this.add(pasteMenuItem);
    this.add(selectAllMenuItem);
    this.add(new JSeparator());
  }

  class CutActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      textPane.cut();
    }
  }

  class CopyActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      textPane.copy();
    }
  }

  class SelectAllActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      textPane.selectAll();
    }
  }

  class PasteActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      textPane.paste();;
    }
  }

}
