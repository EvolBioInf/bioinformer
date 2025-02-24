package haubold.sm;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Description: Panel for displaying Protein substitution matrices.
 * In this case only BLOSUM matrices are shown, but PAM matrices
 * could be displayed just as easily.
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Apr 25, 2004; time: 12:09:28 PM.
 */
public class BlosumPanel extends JPanel {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  JToolBar toolBar;
  JComboBox comboBox;
  String[] matrices = {"blosum45", "blosum62", "blosum80"
                      };
  MatrixDisplayPanel displayPanel;
  SubstitutionMatrixReader substitutionMatrixReader;
  StringBuffer sb;
  String[] aminoAcids;
  int[][] values;

  public BlosumPanel() {
    aminoAcids = new String[20];
    values = new int[20][20];
    substitutionMatrixReader = new SubstitutionMatrixReader();
    constructGui();
    sb = new StringBuffer();
  }

  private void constructGui() {
    SubstitutionMatrix sm = null;
    // Set layout
    setLayout(new BorderLayout());
    // Construct tool bar for controlling matrix
    toolBar = new JToolBar();
    comboBox = new JComboBox(matrices);
    comboBox.setMaximumSize(new Dimension(100,20));
    comboBox.addActionListener(new ComboBoxListener());
    toolBar.add(comboBox);
    add(toolBar,BorderLayout.NORTH);
    // Construct display panel
    try {
      sm = substitutionMatrixReader.getSubstitutionMatrix(matrices[0]);
    } catch(Exception ex) {
      ex.printStackTrace();
    }
    displayPanel = new MatrixDisplayPanel(sm.getValues(),sm.getAminoAcids(),-1);
    add(displayPanel,BorderLayout.CENTER);
  }

  class ComboBoxListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      SubstitutionMatrix sm = null;
      String matrix = (String) comboBox.getSelectedItem();
      try {
        sm = substitutionMatrixReader.getSubstitutionMatrix(matrix);
      } catch(Exception ex) {
        ex.printStackTrace();
      }
      for(int i=0; i<20; i++) {
        aminoAcids[i]=sm.getAminoAcids()[i];
        for(int j=0; j<20; j++) {
          values[i][j]=sm.getValues()[i][j];
        }
      }
      displayPanel.setAminoAcids(aminoAcids);
      displayPanel.setValues(values);
      displayPanel.setPercentDifference(-1);
      displayPanel.repaint();
    }
  }

}
