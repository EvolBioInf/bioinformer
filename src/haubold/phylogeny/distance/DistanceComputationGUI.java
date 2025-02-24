package haubold.phylogeny.distance;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.swing.*;
import haubold.resources.util.*;
/**
 * @author Bernhard Haubold
 * Date: Jun 10, 2003; time: 10:02:57 AM.
 *
 * Description:
 */
public class DistanceComputationGUI extends PrintableJPanel {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  JToolBar toolBar;
  JButton runButton, resetButton;
  JComboBox comboBox;
  JTextPane alignmentPane1, alignmentPane2, distancePane;
  JScrollPane alignmentScroll, distanceScroll;
  JSplitPane splitPane;
  String alignment;
  DistanceComputation distanceComputation;
  String[] taxa;
  StringBuffer stringBuffer;
  DecimalFormat decimalFormat;
  DecimalFormatSymbols dfs;
  BirkhaeuserGUIComponents bgc = new BirkhaeuserGUIComponents();
  RunActionListener runActionListener;
  boolean mismatches;

  DistanceComputationGUI() {
    this.setLayout(new BorderLayout());
    // Construct toolbar
    toolBar = new JToolBar();
    runButton = new JButton(Icons.get1rightArrow());
    runButton.setToolTipText("Compute Distances");
    runActionListener = new RunActionListener();
    runButton.addActionListener(runActionListener);
    String[] comboItems =
    { "Mismatches", "Normalized Mismatches", "Jukes-Cantor" };
    comboBox = new JComboBox(comboItems);
    resetButton = new JButton(Icons.getUndo());
    resetButton.setToolTipText("Reset");
    ResetActionListener resetActionListener = new ResetActionListener();
    resetButton.addActionListener(resetActionListener);
    toolBar.add(runButton);
    toolBar.add(resetButton);
    toolBar.add(comboBox);
    this.add(toolBar, BorderLayout.NORTH);
    // Construct splitpane
    DataContainer dataContainer = new DataContainer();
    alignment = dataContainer.getAlignment();
    alignmentPane1 = new JTextPane();
    Font font = new Font("Courier", Font.PLAIN, 12);
    alignmentPane1.setFont(font);
    alignmentPane1.setText(alignment);
    alignmentPane1.setCaretPosition(0);
    alignmentScroll = new JScrollPane(alignmentPane1);
    distancePane = new JTextPane();
    distancePane.setFont(font);
    distancePane.setToolTipText("Distance matrix in PHYLIP format");
    alignmentPane1.setToolTipText("Alignment in FASTA format");
    distancePane.setBackground(bgc.getColor1());
    alignmentPane1.setBackground(bgc.getColor2());
    alignmentPane2 = alignmentPane1;  // Do NOT remove this line!
    alignmentPane2.setCaretPosition(0);
    distanceScroll = new JScrollPane(distancePane);
    splitPane =
      new JSplitPane(
      JSplitPane.VERTICAL_SPLIT,
      alignmentScroll,
      distanceScroll);
    splitPane.setOneTouchExpandable(true);
    this.add(splitPane, BorderLayout.CENTER);
    decimalFormat = new DecimalFormat("0.000");
    dfs = decimalFormat.getDecimalFormatSymbols();
    dfs.setDecimalSeparator('.');
    decimalFormat.setDecimalFormatSymbols(dfs);
  }

  private Alignment extractAlignment(String input) {
    if (stringBuffer == null) {
      stringBuffer = new StringBuffer();
    } else {
      stringBuffer.delete(0, stringBuffer.length());
    }
    String[] elements;
    String[] units = input.split(">");
    taxa = new String[units.length - 1];
    char[][] ac = new char[units.length - 1][];

    for (int i = 1; i < taxa.length + 1; i++) {
      elements = units[i].split("\n");
      taxa[i - 1] = elements[0];
      stringBuffer.delete(0, stringBuffer.length());
      for (int j = 1; j < elements.length; j++) {
        stringBuffer.append(elements[j]);
      }
      ac[i - 1] = (stringBuffer.toString()).toCharArray();
    }
    Alignment al = new Alignment(ac, taxa);
    return al;
  }

  public Alignment getAlignment() {
    String input = alignmentPane1.getText();
    return extractAlignment(input);
  }

  public void setAlignment(Alignment al) {
    if (distanceComputation == null) {
      distanceComputation = new DistanceComputation(al.getAlignment());
    } else {
      distanceComputation.setAlignment(al.getAlignment());
    }
    String command = (String) comboBox.getSelectedItem();
    mismatches = false;
    if (command.equals("Jukes-Cantor")) {
      al.setDistanceMatrix(distanceComputation.getJukesCantor());
    } else if (command.equals("Normalized Mismatches")) {
      al.setDistanceMatrix(distanceComputation.getNormalizedMismatches());
    } else if (command.equals("Mismatches")) {
      mismatches = true;
      al.setDistanceMatrix(distanceComputation.getMismatches());
    } else {
      System.out.println("ERROR: Combobox commands not synchronized.");
      System.exit(0);
    }
    showDistanceMatrix(distancePane, al);
  }

  public void runAction() {
    String input;
    input = alignmentPane2.getText();
    Alignment al = extractAlignment(input);
    if (distanceComputation == null) {
      distanceComputation = new DistanceComputation(al.getAlignment());
    } else {
      distanceComputation.setAlignment(al.getAlignment());
    }
    String command = (String) comboBox.getSelectedItem();
    mismatches = false;
    if (command.equals("Jukes-Cantor")) {
      al.setDistanceMatrix(distanceComputation.getJukesCantor());
    } else if (command.equals("Normalized Mismatches")) {
      al.setDistanceMatrix(distanceComputation.getNormalizedMismatches());
    } else if (command.equals("Mismatches")) {
      mismatches = true;
      al.setDistanceMatrix(distanceComputation.getMismatches());
    } else {
      System.out.println("ERROR: Combobox commands not synchronized.");
      System.exit(0);
    }
    showDistanceMatrix(distancePane, al);
  }

  private void showDistanceMatrix(JTextPane distancePane, Alignment al) {
    if(al.getDistanceMatrix() == null) {
      distancePane.setText("ERROR: have you submitted unaligned sequences?");
      return;
    }
    double[][] dm = al.getDistanceMatrix();
    int n = al.getDistanceMatrix().length;
    String[] taxa = al.getTaxa();
    if (stringBuffer == null) {
      stringBuffer = new StringBuffer();
    } else {
      stringBuffer.delete(0, stringBuffer.length());
    }
    stringBuffer.append(n);
    stringBuffer.append("\n");
    for (int i = 0; i < n; i++) {
      stringBuffer.append(taxa[i] + "\t");
      for (int j = 0; j < n; j++) {
        if (j < i + 1) {
          stringBuffer.append("-\t");
        } else {
          if(mismatches)
            stringBuffer.append((int)dm[i][j] + "\t");
          else
            stringBuffer.append(decimalFormat.format(dm[i][j]) + "\t");
        }
      }
      stringBuffer.append("\n");
    }
    distancePane.setText(stringBuffer.toString());
    distancePane.setCaretPosition(0);
  }

  class RunActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      runAction();
    }
  }

  class ResetActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      alignmentPane1.setText(alignment);
      alignmentPane1.setCaretPosition(0);
      comboBox.setSelectedIndex(0);
      distancePane.setText("");
    }
  }
  /**
   * Returns the alignmentScroll.
   * @return JScrollPane
   */
  public JScrollPane getAlignmentScroll() {
    return alignmentScroll;
  }

  /**
   * Returns the distanceScroll.
   * @return JScrollPane
   */
  public JScrollPane getDistanceScroll() {
    return distanceScroll;
  }

  /**
   * Returns the alignmentPane.
   * @return JTextPane
   */
  public JTextPane getAlignmentPane() {
    return alignmentPane1;
  }

  /**
   * Returns the distancePane.
   * @return JTextPane
   */
  public JTextPane getDistancePane() {
    return distancePane;
  }

  /**
   * Returns the runActionListener.
   * @return RunActionListener
   */
  public RunActionListener getRunActionListener() {
    return runActionListener;
  }

  /**
   * Sets the alignmentPane.
   * @param alignmentPane The alignmentPane to set
   */
  public void setAlignmentPane(JTextPane alignmentPane) {
    this.alignmentPane1 = alignmentPane;
  }

  /**
   * Returns the alignmentPane2.
   * @return JTextPane
   */
  public JTextPane getAlignmentPane2() {
    return alignmentPane2;
  }

  /**
   * Sets the alignmentPane2.
   * @param alignmentPane2 The alignmentPane2 to set
   */
  public void setAlignmentPane2(JTextPane alignmentPane2) {
    this.alignmentPane2 = alignmentPane2;
  }

}
