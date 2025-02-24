package haubold.dp;
import javax.swing.*;
import java.util.*;
import haubold.resources.util.BirkhaeuserGUIComponents;
import java.awt.*;

/**
 * A <code>SubstitutionModel</code> object defines a DNA substitution matrix.
 * This is provided as a table, which can be edited to allow the user to
 * experiment with alternative settings.
 *
 * @version 0.1, September 21, 2002
 * @since 0.0, July 12, 2002
 * @author Bernhard Haubold
 */
public class SubstitutionMatrix extends JPanel {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private JTextField[][] matrixField;
  private HashMap<String,Double> matrixHash = null;
  private String alphabet = "ACGT";
  BirkhaeuserGUIComponents bgc = new BirkhaeuserGUIComponents();
  private double matchScore, mismatchScore;

  SubstitutionMatrix() {
    int i, j;
    matrixField =
      new JTextField[alphabet.length() + 1][alphabet.length() + 1];
    this.setLayout(
      new GridLayout(alphabet.length() + 1, alphabet.length() + 1));
    for (i = 0; i < alphabet.length() + 1; i++) {
      for (j = 0; j < alphabet.length() + 1; j++) {
        matrixField[i][j] = new JTextField();
        matrixField[i][j].setEditable(true);
        matrixField[i][j].setHorizontalAlignment(JTextField.RIGHT);
        //		matrixField[i][j].setBackground(Color.white);
        this.add(matrixField[i][j]);
      }
    }
    initializeMatrix();
  }

  public void initializeMatrix() {
    int i, j;

    if (matrixHash == null) {
      matrixHash = new HashMap<String,Double>();
      for (i = 0; i < alphabet.length(); i++) {
        for (j = 0; j < alphabet.length(); j++) {
          if (i == j) {
            matrixHash.put(
              alphabet.substring(i, i + 1)
              + alphabet.substring(j, j + 1),
              Double.valueOf(matchScore));
          } else {
            matrixHash.put(
              alphabet.substring(i, i + 1)
              + alphabet.substring(j, j + 1),
              Double.valueOf(mismatchScore));
          }
        }
      }
    }

    for (i = 0; i < alphabet.length(); i++) {
      matrixField[i + 1][0].setText(alphabet.substring(i, i + 1));
      matrixField[0][i + 1].setText(alphabet.substring(i, i + 1));
      matrixField[i + 1][0].setEditable(false);
      matrixField[0][i + 1].setEditable(false);
      matrixField[i + 1][0].setBackground(bgc.getColor2());
      matrixField[0][i + 1].setBackground(bgc.getColor2());
      for (j = 0; j < alphabet.length(); j++) {
        matrixField[i
                    + 1][j
                         + 1].setText(
                           matrixHash
                           .get(
                             alphabet.substring(i, i + 1)
                             + alphabet.substring(j, j + 1))
                           .toString());
        matrixField[i + 1][j + 1].setEditable(true);
        //		matrixField[i+1][j+1].setBackground(Color.white);
      }
    }
    matrixField[0][0].setBackground(bgc.getColor2());
    matrixField[0][0].setEditable(false);
  }

  public HashMap getValues() {
    for (int i = 1; i < matrixField.length; i++) {
      for (int j = 1; j < matrixField.length; j++) {
        matrixHash.put(
          matrixField[0][j].getText() + matrixField[i][0].getText(),
          Double.valueOf(
            Double.parseDouble(matrixField[i][j].getText())));
      }
    }
    return matrixHash;
  }

  public void setValues(HashMap<String,Double> hm) {
    matrixHash = hm;
    initializeMatrix();
  }

  public String getAlphabet() {
    return alphabet;
  }

  public void setAlphabet(String al) {
    alphabet = al;
  }

  public void reset() {
    initializeMatrix();
  }

  public void disableEdit() {
    int i, j;
    for (i = 1; i < alphabet.length() + 1; i++) {
      for (j = 1; j < alphabet.length() + 1; j++) {
        matrixField[i][j].setEditable(false);
      }
    }
  }

  public void enableEdit() {
    int i, j;
    for (i = 1; i < alphabet.length() + 1; i++) {
      for (j = 1; j < alphabet.length() + 1; j++) {
        matrixField[i][j].setEditable(true);
      }
    }
  }

  /**
   * Returns the matchScore.
   * @return double
   */
  public double getMatchScore() {
    return matchScore;
  }

  /**
   * Returns the mismatchScore.
   * @return double
   */
  public double getMismatchScore() {
    return mismatchScore;
  }

  /**
   * Sets the matchScore.
   * @param matchScore The matchScore to set
   */
  public void setMatchScore(double matchScore) {
    this.matchScore = matchScore;
    initializeMatrix();
  }

  /**
   * Sets the mismatchScore.
   * @param mismatchScore The mismatchScore to set
   */
  public void setMismatchScore(double mismatchScore) {
    this.mismatchScore = mismatchScore;
    initializeMatrix();
  }

}
