package haubold.phylogeny.distance;

import haubold.resources.util.*;
import haubold.phylogeny.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
/**
 * @author Bernhard Haubold
 * Date: Jun 22, 2003; time: 8:23:19 AM.
 *
 * Description: Class for demonstrating the bootstrap procedure
 * in phylogenetic reconstruction. The bootstrap was introduced
 * into phylogenetic analysis by Felsenstein.
 *
 * Reference: Felsenstein, J. (1985). Confidence limits on phylogenies:
 * an approach using the bootstrap. Evolution 39: 783-891.
 */
public class BootstrapGUI extends PrintableJPanel {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  JToolBar toolBar;
  JButton bootstrapButton, resetButton, animateButton;
  JPanel dataPanel;
  JSplitPane splitPane;
  JScrollPane alignmentScroll, distanceScroll, bootstrapScroll;
  BirkhaeuserGUIComponents bgc;
  TreePanel treePanel;
  JTextPane bootstrapPane;
  JSlider stepSlider;
  Random ran;
  StringBuffer sb;
  boolean animate = false;

  DistanceComputationGUI distanceComputationGUI;
  DistanceTreeGUI distanceTreeGUI;
  DataContainer dataContainer;

  public BootstrapGUI() {
    bgc = new BirkhaeuserGUIComponents();
    distanceComputationGUI = new DistanceComputationGUI();
    distanceTreeGUI = new DistanceTreeGUI();
    // Construct toolbar
    toolBar = new JToolBar();
    bootstrapButton = new JButton(Icons.get2rightArrow());
    bootstrapButton.setToolTipText("Bootstrap");
    BootstrapActionListener bal = new BootstrapActionListener();
    bootstrapButton.addActionListener(bal);

    animateButton = new JButton(Icons.getRun());
    animateButton.setToolTipText("Animate");
    AnimateActionListener aal = new AnimateActionListener();
    animateButton.addActionListener(aal);

    resetButton = new JButton(Icons.getUndo());
    resetButton.setToolTipText("Reset");
    ResetActionListener ral = new ResetActionListener();
    resetButton.addActionListener(ral);

    stepSlider = bgc.getSlider(1,10,4,"Step Time");
    stepSlider.setEnabled(false);
    stepSlider.setSnapToTicks(true);

    toolBar.add(bootstrapButton);
    toolBar.add(animateButton);
    toolBar.add(resetButton);
    toolBar.add(stepSlider);


    // Generate the scroll panes
    distanceScroll = distanceComputationGUI.getDistanceScroll();
    distanceTreeGUI.setDistancePane(
      distanceComputationGUI.getDistancePane());
    System.out.println(distanceTreeGUI.getDistancePane().getText());
    alignmentScroll = distanceComputationGUI.getAlignmentScroll();

    //		alignmentScroll.setBackground(bgc.getColor2());
    treePanel = new TreePanel();
    distanceTreeGUI.setTreePanel(treePanel);

    bootstrapPane = new JTextPane();
    Font font = new Font("Courier", Font.PLAIN, 12);
    bootstrapPane.setFont(font);
    bootstrapPane.setBackground(bgc.getColor1());
    //		distanceScroll.setBackground(bgc.getColor1());
    bootstrapScroll = new JScrollPane(bootstrapPane);
    bootstrapPane.setBackground(bgc.getColor1());
    distanceComputationGUI.setAlignmentPane2(bootstrapPane);

    // Add Borders
    alignmentScroll.setBorder(new TitledBorder("Original Data"));
    bootstrapScroll.setBorder(new TitledBorder("Bootstrapped Data"));
    distanceScroll.setBorder(new TitledBorder("Distance Matrix"));
    // Construct data panes
    dataPanel = new JPanel();
    dataPanel.setLayout(new GridLayout(3, 1));
    dataPanel.add(alignmentScroll);
    dataPanel.add(bootstrapScroll);
    dataPanel.add(distanceScroll);

    // Put everything together
    this.setLayout(new BorderLayout());
    this.add(toolBar, BorderLayout.NORTH);
    treePanel = new TreePanel();
    splitPane =
      new JSplitPane(JSplitPane.VERTICAL_SPLIT, dataPanel, treePanel);
    dataPanel.setPreferredSize(new Dimension(100,200));
    splitPane.setOneTouchExpandable(true);
    this.add(splitPane, BorderLayout.CENTER);
    splitPane.setDividerLocation(-1);

    // Initialize random number generator;
    ran = new Random();

    // Initialize string buffer
    sb = new StringBuffer();

    // Generate data container and default alignment
    dataContainer = new DataContainer();
  }

  private Alignment bootstrap(Alignment alignment) {
    char[][] al1 = alignment.getAlignment();
    char[][] al2 = new char[al1.length][al1[0].length];
    int r;
    for (int i = 0; i < al1[0].length; i++) {
      r = ran.nextInt(al1[0].length);
      for (int j = 0; j < al1.length; j++) {
        al2[j][i] = al1[j][r];
      }
    }
    alignment.setAlignment(al2);
    return alignment;
  }

  class ResetActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      distanceComputationGUI.getAlignmentPane().setText(dataContainer.getAlignment());
      distanceComputationGUI.getAlignmentPane().setCaretPosition(0);
      distanceComputationGUI.getDistancePane().setText("");
      treePanel.setTree(null);
      bootstrapPane.setText("");

    }
  }

  private void step0() {
    Alignment alignment;
    String[] taxa;
    char[][] al;

    alignment = distanceComputationGUI.getAlignment();
    distanceComputationGUI.getAlignmentPane().setCaretPosition(0);
    alignment = bootstrap(alignment);
    taxa = alignment.getTaxa();
    al = alignment.getAlignment();
    if (sb.length() > 0) {
      sb.delete(0, sb.length());
    }
    for (int i = 0; i < taxa.length; i++) {
      sb.append(">" + taxa[i] + "\n");
      for (int j = 0; j < al[i].length; j++) {
        sb.append(al[i][j]);
      }
      sb.append("\n");
    }
    bootstrapPane.setText(sb.toString());
    bootstrapPane.setCaretPosition(0);
    if(!animate) {
      distanceComputationGUI.getDistancePane().setText("");
      treePanel.setTree(null);
    }
  }

  private void step1() {
    distanceComputationGUI.runAction();
  }

  private void step2() {
    distanceTreeGUI.runAction();
    Node[] tree = distanceTreeGUI.getTree();
    treePanel.setTree(tree);
  }

  class BootstrapActionListener implements ActionListener {
    Alignment alignment;
    String[] taxa;
    char[][] al;
    int step = 0;
    public void actionPerformed(ActionEvent e) {
      if (step == 0) {
        bootstrapButton.setToolTipText("Distance");
        step0();
        step++;
      } else if (step == 1) {
        bootstrapButton.setToolTipText("Tree");
        step1();
        step++;
      } else if (step == 2) {
        bootstrapButton.setToolTipText("Bootstrap");
        step2();
        step = 0;
      }
    }
  }

  class AnimateActionListener implements ActionListener {
    Animation animation;
    public void actionPerformed(ActionEvent arg0) {
      if (animate == false) {
        animation = new Animation();
        animate = true;
        animation.start();
        animateButton.setToolTipText("Stop");
        animateButton.setIcon(Icons.getStop());
        bootstrapButton.setEnabled(false);
        resetButton.setEnabled(false);
        stepSlider.setEnabled(true);
      } else {
        animate = false;
        animateButton.setToolTipText("Animate");
        animateButton.setIcon(Icons.getRun());
        bootstrapButton.setEnabled(true);
        resetButton.setEnabled(true);
        stepSlider.setEnabled(false);
      }

    }

  }

  /**
   * @return
   */
  public DistanceComputationGUI getDistanceComputationGUI() {
    return distanceComputationGUI;
  }

  /**
   * @param computationGUI
   */
  public void setDistanceComputationGUI(DistanceComputationGUI computationGUI) {
    distanceComputationGUI = computationGUI;
  }

  class Animation extends Thread {
    long sleepTime = 200;
    public void run() {
      while (animate) {
        sleepTime = stepSlider.getValue()*50;
        step0();
        if(!animate)
          break;
        try {
          sleep(sleepTime);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
        if(!animate)
          break;
        step1();
        if(!animate)
          break;
        try {
          sleep(sleepTime);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
        if(!animate)
          break;
        step2();
        if(!animate)
          break;
        try {
          sleep(sleepTime);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }
  }

}
