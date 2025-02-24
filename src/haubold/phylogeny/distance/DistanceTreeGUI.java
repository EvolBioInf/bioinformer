package haubold.phylogeny.distance;

import javax.swing.*;

import java.awt.Font;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
//import gnu.jpdf.*;
//import com.lowagie.text.pdf.*;
import haubold.phylogeny.util.*;
import haubold.resources.util.*;

/**
 * @author Bernhard Haubold
 * Date: Jun 9, 2003; time: 4:03:39 PM.
 *
 * Description:
 */
public class DistanceTreeGUI extends PrintableJPanel {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  JToolBar toolBar;
  JButton runButton, resetButton, printButton;
  JTextPane distancePane;
  JSplitPane splitPane;
  JComboBox algorithmBox;
  JScrollPane distanceScroll;
  TreePanel treePanel;
  UPGMATree upgmaTree;
  PrimateDistanceData distanceData;
  String initialDataString;
  String distanceText;
  JCheckBox branchLengthsCheck;
  JCheckBox leafLabelCheck;
  DecimalFormat decimalFormat = new DecimalFormat("0.000");
  BirkhaeuserGUIComponents bgc = new BirkhaeuserGUIComponents();
  RunActionListener runActionListener;
  Node[] tree;
  FileOutputStream fos;
//	PDFJob pdfJob;
  JFrame fileChooserFrame;
  boolean mismatch;

  public DistanceTreeGUI() {
    this.setLayout(new BorderLayout());
    // Construct toolbar
    toolBar = new JToolBar();
    runButton = new JButton(Icons.get1rightArrow());
    runButton.setToolTipText("Compute Tree");
    runActionListener = new RunActionListener();
    runButton.addActionListener(runActionListener);
    resetButton = new JButton(Icons.getUndo());
    resetButton.setToolTipText("Reset");
    ResetActionListener resetActionListener = new ResetActionListener();
    resetButton.addActionListener(resetActionListener);
    String[] algorithms =
    { "UPGMA", "Neighbor Joining", "Fitch-Margoliash" };
    algorithmBox = new JComboBox(algorithms);
    algorithmBox.setEnabled(false);
    branchLengthsCheck = new JCheckBox("Show Branch Lengths");
    branchLengthsCheck.setSelected(false);
    BranchCheckActionListener bca = new BranchCheckActionListener();
    branchLengthsCheck.addActionListener(bca);
    leafLabelCheck = new JCheckBox("Show Names of Taxa");
    leafLabelCheck.setSelected(true);
    leafLabelCheck.addActionListener(bca);
    toolBar.add(runButton);
    toolBar.add(resetButton);
//		toolBar.add(algorithmBox);
    toolBar.add(branchLengthsCheck);
    toolBar.add(leafLabelCheck);
    this.add(toolBar, BorderLayout.NORTH);
    // Construct SplitPane
    distancePane = new JTextPane();
    Font font = new Font("Courier", Font.PLAIN, 12);
    distancePane.setFont(font);
    distancePane.setToolTipText("Distance matrix in PHYLIP format");
    distancePane.setBackground(bgc.getColor1());
    distanceScroll = new JScrollPane(distancePane);
    treePanel = new TreePanel();
    splitPane =
      new JSplitPane(
      JSplitPane.VERTICAL_SPLIT,
      distanceScroll,
      treePanel);
    splitPane.setOneTouchExpandable(true);
    splitPane.setDividerLocation(150);
    this.add(splitPane, BorderLayout.CENTER);
  }

  private DistanceMatrix extractDistanceMatrix(String text) {
    DistanceMatrix distanceMatrix = new DistanceMatrix();
    String[] lines = text.split("\n");
    String[] components;
    int n = 0;

    try {
      n = Integer.parseInt(lines[0]);
    } catch (Exception e) {
      JOptionPane optionPane =
        new JOptionPane(
        "Please enter or generate a distance matrix first.",
        JOptionPane.ERROR_MESSAGE);
      JDialog dialog = optionPane.createDialog(this, "Error Message");
      dialog.setVisible(true);
//			dialog.show();
      distanceMatrix = null;
      return distanceMatrix;
    }
    double[][] dm = new double[n][n];
    String taxa[] = new String[n];
    try {
      for (int i = 1; i < lines.length; i++) {
        components = lines[i].split("\\s+");
        taxa[i - 1] = components[0];
        for (int j = 1; j < components.length; j++) {
          if (j > i) {
            dm[i - 1][j - 1] = Double.parseDouble(components[j]);
            dm[j - 1][i - 1] = dm[i - 1][j - 1];
          }
        }
      }
    } catch (Exception e) {
      JOptionPane optionPane =
        new JOptionPane(
        "There is an error in your distance matrix.",
        JOptionPane.ERROR_MESSAGE);
      JDialog dialog = optionPane.createDialog(this, "Error Message");
//			dialog.show();
      dialog.setVisible(true);
      distanceMatrix = null;
      return distanceMatrix;
    }
    distanceMatrix.setDistanceMatrix(dm);
    distanceMatrix.setTaxa(taxa);
    return distanceMatrix;
  }

  public void runAction() {
    DistanceMatrix distanceMatrix;
    boolean branchLengths, leafLabels;
    String text = distancePane.getText();
    distanceMatrix = extractDistanceMatrix(text);
    String algorithm = algorithmBox.getSelectedItem().toString();
    if (branchLengthsCheck.isSelected()) {
      branchLengths = true;
    } else {
      branchLengths = false;
    }
    if (leafLabelCheck.isSelected()) {
      leafLabels = true;
    } else {
      leafLabels = false;
    }
    treePanel.setBranchLengths(branchLengths);
    treePanel.setLeafLabels(leafLabels);
    if (distanceMatrix != null) {
      if (algorithm == "UPGMA") {
        if (upgmaTree == null) {
          upgmaTree =
            new UPGMATree(
            distanceMatrix.getDistanceMatrix(),
            distanceMatrix.getTaxa());
        } else {
          upgmaTree.setTaxa(distanceMatrix.getTaxa());
          upgmaTree.setDistanceMatrix(
            distanceMatrix.getDistanceMatrix());
        }
        tree = upgmaTree.getTree();
        treePanel.setTree(tree);
      }
    }
  }

  class RunActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      runAction();
    }
  }

  class ResetActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      distancePane.setText(initialDataString);
      distancePane.setCaretPosition(0);
      treePanel.setTree(null);
      branchLengthsCheck.setSelected(false);
      leafLabelCheck.setSelected(true);
    }
  }
  class BranchCheckActionListener implements ActionListener {
    boolean branchLengths, leafLabels;
    public void actionPerformed(ActionEvent e) {
      if (branchLengthsCheck.isSelected()) {
        branchLengths = true;
      } else {
        branchLengths = false;
      }
      if (leafLabelCheck.isSelected()) {
        leafLabels = true;
      } else {
        leafLabels = false;
      }
      treePanel.setBranchLengths(branchLengths);
      treePanel.setLeafLabels(leafLabels);
      treePanel.repaint();
    }
  }
  /**
   * Returns the distanceText.
   * @return String
   */
  public String getDistanceText() {
    return distancePane.getText();
  }

  /**
   * Sets the distanceText.
   * @param distanceText The distanceText to set
   */
  public void setDistanceText(String distanceText) {
    this.distanceText = distanceText;
    if (distanceText.length() > 0) {
      distancePane.setText(distanceText);
      distancePane.setCaretPosition(0);
      initialDataString = distancePane.getText();
      treePanel.setTree(null);
    }
  }

  /**
   * Returns the distancePane.
   * @return JTextPane
   */
  public JTextPane getDistancePane() {
    return distancePane;
  }

  /**
   * Returns the treePanel.
   * @return TreePanel
   */
  public TreePanel getTreePanel() {
    return treePanel;
  }

  /**
   * Sets the distancePane.
   * @param distancePane The distancePane to set
   */
  public void setDistancePane(JTextPane distancePane) {
    this.distancePane = distancePane;
  }

  /**
   * Returns the runActionListener.
   * @return RunActionListener
   */
  public RunActionListener getRunActionListener() {
    return runActionListener;
  }

  /**
   * Returns the distanceScroll.
   * @return JScrollPane
   */
  public JScrollPane getDistanceScroll() {
    return distanceScroll;
  }

  /**
   * Sets the treePanel.
   * @param treePanel The treePanel to set
   */
  public void setTreePanel(TreePanel treePanel) {
    this.treePanel = treePanel;
  }

  /**
   * Returns the tree.
   * @return Node[]
   */
  public Node[] getTree() {
    return tree;
  }

  public boolean isMismatch() {
    return mismatch;
  }

  public void setMismatch(boolean mismatch) {
    this.mismatch = mismatch;
  }

}
