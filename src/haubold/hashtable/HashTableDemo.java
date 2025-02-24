package haubold.hashtable;

/**
 * @author Bernhard Haubold
 * Date: Mar 7, 2003; time: 1:47:59 PM.
 *
 * Description:
 */
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import haubold.resources.util.*;
import haubold.resources.demo.*;

public class HashTableDemo extends DemoFrame {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  JSlider slider;
  JLabel lengthLabel;
  JButton hashButton, resetButton;
  JPanel controlPanel;
  JLabel textLabel;
  BirkhaeuserAboutButton aboutButton;
  JTextField textField;
  JToolBar toolBar;
  JTable table;
  JScrollPane scrollPane;
  String initialText = "AAGACGTAGATAGACCGT";
  SequenceHash hash;
  DefaultTableModel tableModel;
  String[] tableHeader;
  BirkhaeuserGUIComponents bgc;
  StringTablePanel tablePanel;

  public HashTableDemo(String titleString, String helpPath) {
    setTitle(titleString);
    setHelpPath(helpPath);
    hash = new SequenceHash();
    // Ceate controlPanel.
    controlPanel = new JPanel();
    controlPanel.setLayout(new BorderLayout());
    toolBar = new JToolBar();
    bgc = new BirkhaeuserGUIComponents();
    slider = bgc.getSlider(1, 20, 3, "Word Length");
    slider.setMinorTickSpacing(1);
    slider.setMajorTickSpacing(5);
    LengthListener lengthListener = new LengthListener();
    slider.addChangeListener(lengthListener);
    lengthLabel = new JLabel();
    lengthLabel.setPreferredSize(new Dimension(30, 30));
    lengthLabel.setHorizontalTextPosition(JLabel.RIGHT);
    lengthLabel.setText(String.valueOf(slider.getValue()));
    hashButton = new JButton(Icons.get1rightArrow());
    hashButton.setToolTipText("Hash Sequence");
    HashActionListener hashActionListener = new HashActionListener();
    hashButton.addActionListener(hashActionListener);
    resetButton = new JButton(Icons.getUndo());
    resetButton.setToolTipText("Reset");
    ResetActionListener resetActionListener = new ResetActionListener();
    resetButton.addActionListener(resetActionListener);
    toolBar.add(slider);
    toolBar.add(lengthLabel);
    toolBar.add(hashButton);
    toolBar.add(resetButton);
    textLabel = new JLabel(" Text: ");
    textField = new JTextField();
    textField.addActionListener(hashActionListener);
    textField.setText(initialText);
    // Table Panel
    tablePanel = new StringTablePanel();
    tablePanel.setPreferredSize(new Dimension(200, 50));
    tablePanel.setText("".toCharArray());
    controlPanel.add(toolBar, BorderLayout.NORTH);
    controlPanel.add(textLabel, BorderLayout.WEST);
    controlPanel.add(textField, BorderLayout.CENTER);
    controlPanel.add(tablePanel, BorderLayout.SOUTH);
    // Create JTable
    tableModel = new DefaultTableModel();
    tableHeader = new String[2];
    tableHeader[0] = "Word";
    tableHeader[1] = "Position";
    tableModel.setColumnIdentifiers(tableHeader);
    table = new JTable();
    table.setModel(tableModel);
    scrollPane = new JScrollPane(table);
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(controlPanel, BorderLayout.NORTH);
    this.getContentPane().add(scrollPane, BorderLayout.CENTER);
  }

//	public static void main(String[] arg) {
//		JApplet applet = new HashTablePanel();
//		applet.init();
//		JFrame frame = new JFrame("Hash Table");
//		frame.setSize(450, 290);
//		frame.getContentPane().setLayout(new BorderLayout());
//		frame.getContentPane().add(applet, BorderLayout.CENTER);
//		frame.setVisible(true);
//	}

  private void fillTable() {
    ArrayList list;
    String key;
    String position = "";
    int p;
    String text = textField.getText();
    int l = slider.getValue();
    hash.hashSequence(text.toCharArray(), l);
    Set keys = hash.keySet();
    Object[][] tableData = new Object[keys.size()][2];
    Iterator keyIterator = keys.iterator();
    int i = 0;
    while (keyIterator.hasNext()) {
      key = (String) keyIterator.next();
      tableData[i][0] = key;
      list = (ArrayList) hash.get(key);
      position = "";
      for (int j = 0; j < list.size(); j++) {
        p = ((Integer) list.get(j)).intValue() + 1;
        position += p + " ";
      }
      tableData[i][1] = position;
      i++;
    }
    tableModel.setDataVector(tableData, tableHeader);
    tablePanel.setText(text.toCharArray());
  }

  class ResetActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      textField.setText(initialText);
      Object[][] o = new Object[0][0];
      tableModel.setDataVector(o, tableHeader);
      slider.setValue(3);
      tablePanel.setText("".toCharArray());
    }
  }

  class HashActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      fillTable();
    }
  }

  class LengthListener implements ChangeListener {
    public void stateChanged(ChangeEvent e) {
      int l = slider.getValue();
      lengthLabel.setText(String.valueOf(l));
    }
  }
}
