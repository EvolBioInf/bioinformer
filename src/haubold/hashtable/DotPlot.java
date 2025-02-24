package haubold.hashtable;

/**
 * @author Bernhard Haubold
 * Date: Mar 7, 2003; time: 6:00:11 PM.
 *
 * Description:
 */
import haubold.resources.util.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.*;

public class DotPlot extends PrintableJPanel {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  SequenceHash hash;
  char[] seq1, seq2;
  int wordLength;
  StringBuffer key;
  ArrayList list;
  BirkhaeuserGUIComponents bgc;
  boolean reset = false;

  public DotPlot() {
    hash = new SequenceHash();
    key = new StringBuffer();
    bgc = new BirkhaeuserGUIComponents();
  }

  public void paint(Graphics g) {
    int i, j;
    double x1, x2, y1, y2;
    double sx = 1.0;
    double sy = 1.0;
    int tx, ty;
    double scale;
    BufferedImage bi = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
    Graphics2D big = bi.createGraphics();
    Graphics2D g2 = (Graphics2D) g;
    RenderingHints qualityHints =
      new RenderingHints(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON);
    qualityHints.put(
      RenderingHints.KEY_RENDERING,
      RenderingHints.VALUE_RENDER_QUALITY);
    big.setRenderingHints(qualityHints);
    big.setColor(bgc.getColor1());
    big.fillRect(0, 0, this.getWidth(), this.getHeight());
    big.setColor(Color.black);
    // Hash first sequence.
    if (seq1 != null) {
      AffineTransform transform = big.getTransform();
      sx = (double) this.getWidth() / (double) seq1.length * 0.90;
      sy = (double) this.getHeight() / (double) seq2.length * 0.90;
      scale = Math.min(sx, sy);
      tx =
        (int) (((double) getWidth() - (double) seq1.length * scale)
               / 2.0);
      ty =
        (int) (((double) getHeight() - (double) seq2.length * scale)
               / 2.0);
      big.translate(tx, ty);
      big.scale(scale, scale);
      big.setColor(Color.red);
      big.drawRect(0, 0, seq1.length, seq2.length);
      big.scale(1 / scale, 1 / scale);
      big.drawString(
        "Sequence 1 ->",
        0,
        (int) ((double) seq2.length * scale) + 10);
      big.rotate(Math.PI / 2.0);
      big.drawString("Sequence 2 ->", 0, 10);
      big.rotate(-Math.PI / 2.0);
      big.scale(scale, scale);
      double tickX =
        Math.pow(
          10,
          Math.floor((Math.log(seq1.length) / Math.log(10))));
      double tickY =
        Math.pow(
          10,
          Math.floor((Math.log(seq2.length) / Math.log(10))));
      big.setColor(Color.black);
      i = 0;
      if (seq1.length > 99 && seq2.length > 99) {
        while (i < seq1.length) {
          big.drawLine(
            i,
            - (int) (Math.min(ty, tx) / 5.0 * 4.0),
            i,
            0);
          big.scale(1.0 / scale, 1.0 / scale);
          big.drawString(
            String.valueOf(i),
            (int) ((double) i * scale),
            - (int) (ty / 5.0 * 4.0 * scale));
          big.scale(scale, scale);
          i += tickX;
        }
        i = 0;
        while (i < seq2.length) {
          big.drawLine(
            (int) (seq1.length),
            i,
            (int) ((double) seq1.length
                   + Math.min(tx, ty) / 5.0 * 4.0),
            i);
          big.scale(1.0 / scale, 1.0 / scale);
          big.drawString(
            String.valueOf(i),
            (int) ((seq1.length + Math.min(tx, ty) / 5.0 * 4.0)
                   * scale),
            (int) ((double) i * scale));
          big.scale(scale, scale);
          i += tickY;
        }
        g = (Graphics)big;
      }
      hash.hashSequence(seq1, wordLength);
      int max = 0;
      Integer pos;
      // Run though second sequence and draw lines.
      for (i = 0; i < seq2.length - wordLength + 1; i++) {
        key.delete(0, key.length());
        for (j = 0; j < wordLength; j++) {
          key.append(seq2[i + j]);
        }
        if (hash.containsKey(key.toString())) {
          list = (ArrayList) hash.get(key.toString());
          for (j = 0; j < list.size(); j++) {
            pos = (Integer) list.get(j);
            if (max < pos.intValue()) {
              max = pos.intValue();
            }
            x1 = (((Integer) list.get(j)).doubleValue());
            y1 = ((double) i);
            x2 = (x1 + (double) wordLength);
            y2 = (y1 + (double) wordLength);
            big.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
          }
        }
      }
      big.setTransform(transform);
    }
    g2.drawImage(bi,0,0,this);
  }

  public void setSequences(String sequence1, String sequence2) {
    this.seq1 = sequence1.toCharArray();
    this.seq2 = sequence2.toCharArray();
    reset = false;
    repaint();
  }

  public void setWordLength(int wordLength) {
    this.wordLength = wordLength;
    repaint();
  }

  public void reset() {
    seq1 = null;
    repaint();
  }

}
