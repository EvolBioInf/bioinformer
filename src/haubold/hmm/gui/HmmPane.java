package haubold.hmm.gui;

import haubold.hmm.algorithm.*;
import haubold.resources.util.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.text.*;

/**
 * @author haubold
 * Date: Nov 9, 2003; time: 3:10:25 PM.
 *
 * Description: JTextPane for simultaneously displaying
 * hidden and observed states. The hidden states are marked
 * by background colors, the observed states by letters.
 */
public class HmmPane extends JTextPane {
  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private BirkhaeuserGUIComponents bgc;

  private Color state1Color, state2Color;

  private Color emission1Color, emission2Color;

  private Style[][] styleQuartet;

  private String[][] styleNameQuartet;

  public HmmPane() {
    bgc = new BirkhaeuserGUIComponents();
    state1Color = bgc.getColor2();
    state2Color = bgc.getColor4();
    emission1Color = bgc.getColor3();
    emission2Color = bgc.getColor5();
    getStyleNameQuartet();
    this.setEditable(false);
  }

  /**
   * Create a two by two matrix of document styles, where style[i][j] should
   * be applied to emission j from state i.
   * @return
   */
  public String[][] getStyleNameQuartet() {
    // Create four styles
    styleQuartet = new Style[2][2];
    styleNameQuartet = new String[2][2];
    styleNameQuartet[0][0] = "1";
    styleNameQuartet[0][1] = "2";
    styleNameQuartet[1][0] = "3";
    styleNameQuartet[1][1] = "4";
    Style def = StyleContext.getDefaultStyleContext().getStyle(
                  StyleContext.DEFAULT_STYLE);
    Style base = this.addStyle("base", def);
    StyleConstants.setFontFamily(base, "monospaced");
    StyleConstants.setFontSize(base, 12);
    styleQuartet[0][0] = this.addStyle(styleNameQuartet[0][0], base);
    styleQuartet[0][1] = this.addStyle(styleNameQuartet[0][1], base);
    styleQuartet[1][0] = this.addStyle(styleNameQuartet[1][0], base);
    styleQuartet[1][1] = this.addStyle(styleNameQuartet[1][1], base);
    // Set foreground
    StyleConstants.setForeground(styleQuartet[0][0], emission1Color);
    StyleConstants.setForeground(styleQuartet[0][1], emission2Color);
    StyleConstants.setForeground(styleQuartet[1][0], emission1Color);
    StyleConstants.setForeground(styleQuartet[1][1], emission2Color);
    // Set background
    StyleConstants.setBackground(styleQuartet[0][0], state1Color);
    StyleConstants.setBackground(styleQuartet[0][1], state1Color);
    StyleConstants.setBackground(styleQuartet[1][0], state2Color);
    StyleConstants.setBackground(styleQuartet[1][1], state2Color);
    return styleNameQuartet;
  }

  public void setSequenceData(SequenceUnit[] sequenceData) {
    Document doc = this.getDocument();
    int i;
    try {
      doc.remove(0, doc.getLength());
      for (i = 0; i < sequenceData.length; i++) {
        if (sequenceData[i].getNucleotide().equals("A")
            || sequenceData[i].getNucleotide().equals("T")) {
          doc.insertString(doc.getLength(), sequenceData[i]
                           .getNucleotide(), this
                           .getStyle(styleNameQuartet[sequenceData[i]
                                     .getHiddenState()][0]));
        } else {
          doc.insertString(doc.getLength(), sequenceData[i]
                           .getNucleotide(), this
                           .getStyle(styleNameQuartet[sequenceData[i]
                                     .getHiddenState()][1]));
        }
      }
      this.setCaretPosition(0);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * @param color
   */
  public void setState1Color(Color color) {
    state1Color = color;
  }

  /**
   * @param color
   */
  public void setState2Color(Color color) {
    state2Color = color;
  }

  /**
   * @param color
   */
  public void setEmission1Color(Color color) {
    emission1Color = color;
  }

  /**
   * @param color
   */
  public void setEmission2Color(Color color) {
    emission2Color = color;
  }

}
