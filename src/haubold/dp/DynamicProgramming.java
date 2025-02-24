package haubold.dp;
import java.awt.*;
import java.awt.geom.*;
import java.awt.Graphics2D;
import javax.swing.*;
import java.util.*;
/**
 * A <code>DynamicProgramming</code> object provides a dynamic programming matrix
 * as central data structure. This contains entries f(i,j) of
 * maximum scores of the alignment of sequence1[1..i] and
 * sequence2[1..j]. From this data structure an optimal alignment
 * can be obtained. This <code>DynamicProgramming</code> class
 * implements the local, global, and overlap alignment algorithms.
 *
 * @version 0.1 September 21, 2002
 * @since 0.0 July 12, 2002
 * @author Bernhard Haubold
 */

import haubold.resources.util.*;

class DynamicProgramming extends JPanel {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  public double numCooptimal;
  public double optimalScore;

  public int SETUP_MATRIX = 0;
  public int INITIALIZE_MATRIX = 1;
  public int FILL_MATRIX = 2;
  public int TRACE_BACK = 3;
  public int CLEAR_MATRIX = 4;

  private int alignmentMode;

  private Arrow arrow;
  private double scaleX, scaleY;
  private boolean traceBack;
  String sequence1, sequence2;
  HashMap substitutionMap;
  double gapOpening;
  double gapExtension;
  boolean gapOpen = false;
  int gapLength;
  JScrollPane scrollPane;
  DpmCell[][] dpmMatrix;
  BirkhaeuserGUIComponents bgc = new BirkhaeuserGUIComponents();
  double matchScore;
  double mismatchScore;

  DynamicProgramming() {
    this.setLayout(new BorderLayout());
    scrollPane = new JScrollPane();
    arrow = new Arrow();
    arrow.setArrowSize((float)5.0);
//		this.add(scrollPane);

  }

  public DynamicProgramming(String sequence1, String sequence2) {
    this.setLayout(new BorderLayout());
    scrollPane = new JScrollPane();
    arrow = new Arrow();
    arrow.setArrowSize((float)5.0);
  }

  public void setSequences(String seq1, String seq2) {
    sequence1 = seq1;
    sequence2 = seq2;
    int m = sequence2.length();
    int n = sequence1.length();
    Object[] headers = new Object[n + 2];
    Object[][] rows = new Object[m + 2][n + 2];
    int i, j;

    // set top row
    headers[0] = "";
    rows[0][0] = "";
    headers[1] = "";
    rows[0][1] = "-";
    rows[1][0] = "-";
    for (j = 0; j < n; j++) {
      headers[j + 2] = "";
      rows[0][j + 2] = sequence1.substring(j, j + 1);
    }
    // set first column
    for (j = 0; j < m; j++) {
      rows[j + 2][0] = sequence2.substring(j, j + 1);
    }
    m = sequence2.length();
    n = sequence1.length();
    dpmMatrix = new DpmCell[m + 1][n + 1];
    for (i = 0; i < m + 1; i++) {
      for (j = 0; j < n + 1; j++) {
        dpmMatrix[i][j] = new DpmCell(i,j);
      }
    }
  }

  public void setParameters(HashMap sMap, double gapOp, double gapExt) {
    substitutionMap = new HashMap();
    substitutionMap = sMap;
    gapOpening = gapOp;
    gapExtension = gapExt;
  }

  void numberOfCooptimalAlignments(
    int startRow,
    int startColumn,
    int endRow,
    int endColumn) {
    int i = 0;
    int j = 0;
    // initialize last column
    dpmMatrix[startRow][startColumn].numCooptimal = 1.0;
    for (i = startRow - 1; i >= endRow; i--) {
      dpmMatrix[i][startColumn].numCooptimal = 0.0;
      if (dpmMatrix[i + 1][startColumn].backVertical) {
        dpmMatrix[i][startColumn].numCooptimal =
          dpmMatrix[i][startColumn].numCooptimal
          + dpmMatrix[i
                      + 1][startColumn].numCooptimal;
      } else {
        dpmMatrix[i][startColumn].numCooptimal = 0.0;
      }
    }
    // initialize last row
    for (i = startColumn - 1; i >= endColumn; i--) {
      dpmMatrix[startRow][i].numCooptimal = 0.0;
      if (dpmMatrix[startRow][i + 1].backHorizontal) {
        dpmMatrix[startRow][i].numCooptimal =
          dpmMatrix[startRow][i].numCooptimal
          + dpmMatrix[startRow][i
                                + 1].numCooptimal;
      } else {
        dpmMatrix[startRow][i].numCooptimal = 0.0;
      }
    }
    // fill rest of matrix
    for (i = startRow - 1; i >= endRow; i--) {
      for (j = startColumn - 1; j >= endColumn; j--) {
        dpmMatrix[i][j].numCooptimal = 0.0;
        // add contribution of cells that point to current cell
        if (dpmMatrix[i + 1][j].backVertical) {
          dpmMatrix[i][j].numCooptimal =
            dpmMatrix[i][j].numCooptimal
            + dpmMatrix[i
                        + 1][j].numCooptimal;
        }
        if (dpmMatrix[i + 1][j + 1].backDiagonal) {
          dpmMatrix[i][j].numCooptimal =
            dpmMatrix[i][j].numCooptimal
            + dpmMatrix[i
                        + 1][j
                             + 1].numCooptimal;
        }
        if (dpmMatrix[i][j + 1].backHorizontal) {
          dpmMatrix[i][j].numCooptimal =
            dpmMatrix[i][j].numCooptimal
            + dpmMatrix[i][j
                           + 1].numCooptimal;
        }
      }
    }
    numCooptimal = dpmMatrix[i + 1][j + 1].numCooptimal;
  }

  ////////////////// Global Alignment Methods ///////////////////////////
  public void initializeGlobalAlignment() {
    int i;
    int m = sequence2.length();
    int n = sequence1.length();

    dpmMatrix[0][0].score = 0.0;
    // initialize first row
    for (i = 1; i <= n; i++) {
      dpmMatrix[0][i].score = dpmMatrix[0][i - 1].score + gapExtension;
      dpmMatrix[0][i].vertical = dpmMatrix[0][i].score;
      dpmMatrix[0][i].backHorizontal = true;
    }
    // initialize first column
    for (i = 1; i <= m; i++) {
      dpmMatrix[i][0].score = dpmMatrix[i - 1][0].score + gapExtension;
      dpmMatrix[i][0].horizontal = dpmMatrix[i][0].score;
      dpmMatrix[i][0].backVertical = true;
    }
  }

  public void fillGlobalAlignment() {
    double h, v, d;
    String result;
    int i, j;
    int n = sequence1.length();
    int m = sequence2.length();
    for (i = 1; i <= m; i++) {
      for (j = 1; j <= n; j++) {
        if(sequence2.substring(i - 1, i).equals(sequence1.substring(j - 1, j))) {
          d = dpmMatrix[i-1][j-1].score + matchScore;
        } else {
          d = dpmMatrix[i-1][j-1].score + mismatchScore;
        }
        h =
          Math.max(
            dpmMatrix[i][j - 1].horizontal,
            dpmMatrix[i][j - 1].score + gapOpening)
          + gapExtension;
        v =
          Math.max(
            dpmMatrix[i - 1][j].vertical,
            dpmMatrix[i - 1][j].score + gapOpening)
          + gapExtension;
        dpmMatrix[i][j].score = Math.max(Math.max(h, d), v);
        dpmMatrix[i][j].vertical = v;
        dpmMatrix[i][j].horizontal = h;
        result = String.valueOf(dpmMatrix[i][j].score);
        result.trim();
        if (v == dpmMatrix[i][j].score) {
          dpmMatrix[i][j].backVertical = true;
          result = "| " + result;
        } else {
          result = "  " + result;
        }
        if (h == dpmMatrix[i][j].score) {
          dpmMatrix[i][j].backHorizontal = true;
          result = "-" + result;
        } else {
          result = " " + result;
        }
        if (d == dpmMatrix[i][j].score) {
          dpmMatrix[i][j].backDiagonal = true;
          result = "\\" + result;
        } else {
          result = "  " + result;

        }
      }
    }
  }

  public String[] traceBackGlobalAlignment() {
    String[] alignment = new String[3];
    alignment[0] = new String();
    alignment[1] = new String();
    alignment[2] = new String();
    int j = sequence1.length();
    int i = sequence2.length();
    int startColumn, endColumn, startRow, endRow;
    dpmMatrix[i][j].optimal = true;
    optimalScore = dpmMatrix[i][j].score;
    startColumn = j;
    startRow = i;
    while (i > 0 || j > 0) {
      if (dpmMatrix[i][j].backDiagonal) {
        dpmMatrix[i - 1][j - 1].optimal = true;
        alignment[0] = alignment[0] + sequence1.substring(j - 1, j);
        alignment[2] = alignment[2] + sequence2.substring(i - 1, i);
        if (sequence1
            .substring(j - 1, j)
            .equals(sequence2.substring(i - 1, i))) {
          alignment[1] = alignment[1] + ":";
        } else {
          alignment[1] = alignment[1] + " ";
        }
        i = i - 1;
        j = j - 1;
      } else if (dpmMatrix[i][j].backHorizontal) {
        dpmMatrix[i][j - 1].optimal = true;
        alignment[0] = alignment[0] + sequence1.substring(j - 1, j);
        alignment[2] = alignment[2] + "-";
        alignment[1] = alignment[1] + " ";
        j = j - 1;
      } else if (dpmMatrix[i][j].backVertical) {
        dpmMatrix[i - 1][j].optimal = true;
        alignment[0] = alignment[0] + "-";
        alignment[2] = alignment[2] + sequence2.substring(i - 1, i);
        alignment[1] = alignment[1] + " ";
        i = i - 1;
      } else {
        System.out.println("Error in reconstruction of Alignment");
      }
    }
    endColumn = j;
    endRow = i;
    numberOfCooptimalAlignments(startRow, startColumn, endRow, endColumn);
    numCooptimal = dpmMatrix[endRow][endColumn].numCooptimal;
    StringBuffer str1 = new StringBuffer(alignment[0]);
    StringBuffer str2 = new StringBuffer(alignment[1]);
    StringBuffer str3 = new StringBuffer(alignment[2]);
    alignment[0] = str1.reverse().toString();
    alignment[1] = str2.reverse().toString();
    alignment[2] = str3.reverse().toString();
    return alignment;
  }
  ////////////////// Overlap Alignment Methods ///////////////////////////
  public void initializeOverlapAlignment() {
    int i;
    int m = sequence2.length();
    int n = sequence1.length();

    dpmMatrix[0][0].score = 0.0;
    // initialize first row
    for (i = 1; i <= n; i++) {
      dpmMatrix[0][i].score = 0.0;
      dpmMatrix[0][i].vertical = dpmMatrix[0][i].score;
      dpmMatrix[0][i].backHorizontal = true;
    }
    // initialize first column
    for (i = 1; i <= m; i++) {
      dpmMatrix[i][0].score = 0.0;
      dpmMatrix[i][0].horizontal = dpmMatrix[i][0].score;
      dpmMatrix[i][0].backVertical = true;
    }
  }

  public void fillOverlapAlignment() {
    double h, v, d;
    String result;
    int i, j;
    int n = sequence1.length();
    int m = sequence2.length();
    for (i = 1; i <= m; i++) {
      for (j = 1; j <= n; j++) {
        if(sequence2.substring(i - 1, i).equals(sequence1.substring(j - 1, j))) {
          d = dpmMatrix[i-1][j-1].score + matchScore;
        } else {
          d = dpmMatrix[i-1][j-1].score + mismatchScore;
        }
        h =
          Math.max(
            dpmMatrix[i][j - 1].horizontal,
            dpmMatrix[i][j - 1].score + gapOpening)
          + gapExtension;
        v =
          Math.max(
            dpmMatrix[i - 1][j].vertical,
            dpmMatrix[i - 1][j].score + gapOpening)
          + gapExtension;
        dpmMatrix[i][j].score = Math.max(Math.max(h, d), v);
        dpmMatrix[i][j].vertical = v;
        dpmMatrix[i][j].horizontal = h;
        result = String.valueOf(dpmMatrix[i][j].score);
        result.trim();
        if (v == dpmMatrix[i][j].score) {
          dpmMatrix[i][j].backVertical = true;
          result = "| " + result;
        } else {
          result = "  " + result;
        }
        if (h == dpmMatrix[i][j].score) {
          dpmMatrix[i][j].backHorizontal = true;
          result = "-" + result;
        } else {
          result = " " + result;
        }
        if (d == dpmMatrix[i][j].score) {
          dpmMatrix[i][j].backDiagonal = true;
          result = "\\" + result;
        } else {
          result = "  " + result;

        }
      }
    }
  }

  int[] findMaxOverlap() {
    int n = sequence1.length();
    int m = sequence2.length();
    int i, column, row;
    double maxScore;
    int[] coordinates = new int[2];
    maxScore = dpmMatrix[m][0].score;
    row = m;
    column = 0;
    // check last row
    for (i = 1; i <= n; i++) {
      if (dpmMatrix[m][i].score > maxScore) {
        maxScore = dpmMatrix[m][i].score;
        column = i;
      }
    }
    // check last column
    for (i = 0; i <= m; i++) {
      if (dpmMatrix[i][n].score > maxScore) {
        maxScore = dpmMatrix[i][n].score;
        column = n;
        row = i;
      }
    }
    coordinates[0] = row;
    coordinates[1] = column;
    return coordinates;
  }

  public String[] traceBackOverlapAlignment() {
    String[] alignment = new String[3];
    alignment[0] = new String();
    alignment[1] = new String();
    alignment[2] = new String();
    int[] coordinates;
    int row, column, i, j, startColumn, startRow, endColumn, endRow;

    // find starting point for trace back
    coordinates = findMaxOverlap();
    row = coordinates[0];
    column = coordinates[1];
    startColumn = column;
    startRow = row;
    optimalScore = dpmMatrix[row][column].score;

    // add substring from sequence1 to alignment (if necessary)
    if (column < dpmMatrix[0].length) {
      for (i = sequence1.length() - 1; i >= column; i--) {
        alignment[0] = alignment[0] + sequence1.substring(i, i + 1);
        alignment[2] = alignment[2] + "-";
      }
    }

    // add substring from sequence2 to alignmnet (if necessary)
    if (row < dpmMatrix.length) {
      for (i = sequence2.length() - 1; i >= row; i--) {
        alignment[2] = alignment[2] + sequence2.substring(i, i + 1);
        alignment[0] = alignment[0] + "-";
      }
    }

    // do remainder of traceback
    i = row;
    j = column;
    dpmMatrix[i][j].optimal = true;
    while (i > 0 || j > 0) {
      if (dpmMatrix[i][j].backDiagonal) {
        dpmMatrix[i - 1][j - 1].optimal = true;
        alignment[0] = alignment[0] + sequence1.substring(j - 1, j);
        alignment[2] = alignment[2] + sequence2.substring(i - 1, i);
        if (sequence1
            .substring(j - 1, j)
            .equals(sequence2.substring(i - 1, i))) {
          alignment[1] = alignment[1] + ":";
        } else {
          alignment[1] = alignment[1] + " ";
        }
        i = i - 1;
        j = j - 1;
      } else if (dpmMatrix[i][j].backHorizontal) {
        dpmMatrix[i][j - 1].optimal = true;
        alignment[0] = alignment[0] + sequence1.substring(j - 1, j);
        alignment[2] = alignment[2] + "-";
        alignment[1] = alignment[1] + " ";
        j = j - 1;
      } else if (dpmMatrix[i][j].backVertical) {
        dpmMatrix[i - 1][j].optimal = true;
        alignment[0] = alignment[0] + "-";
        alignment[2] = alignment[2] + sequence2.substring(i - 1, i);
        alignment[1] = alignment[1] + " ";
        i = i - 1;
      } else {
        System.out.println("Error in reconstruction of Alignment");
      }
    }
    endColumn = j;
    endRow = i;
    numberOfCooptimalAlignments(startRow, startColumn, endRow, endColumn);
    numCooptimal = dpmMatrix[endRow][endColumn].numCooptimal;
    StringBuffer str1 = new StringBuffer(alignment[0]);
    StringBuffer str2 = new StringBuffer(alignment[1]);
    StringBuffer str3 = new StringBuffer(alignment[2]);
    alignment[0] = str1.reverse().toString();
    alignment[1] = str2.reverse().toString();
    alignment[2] = str3.reverse().toString();
    return alignment;
  }
  ////////////////// Local Alignment Methods ///////////////////////////
  public void initializeLocalAlignment() {
    int i;
    int m = sequence2.length();
    int n = sequence1.length();

    dpmMatrix[0][0].score = 0.0;
    // initialize first row
    for (i = 1; i <= n; i++) {
      dpmMatrix[0][i].score = 0.0;
      dpmMatrix[0][i].vertical = dpmMatrix[0][i].score;
      dpmMatrix[0][i].backHorizontal = false;
    }
    // initialize first column
    for (i = 1; i <= m; i++) {
      dpmMatrix[i][0].score = 0.0;
      dpmMatrix[i][0].horizontal = dpmMatrix[i][0].score;
      dpmMatrix[i][0].backVertical = false;
    }
  }

  public void fillLocalAlignment() {
    double h, v, d;
    String result;
    int i, j;
    int n = sequence1.length();
    int m = sequence2.length();
    for (i = 1; i <= m; i++) {
      for (j = 1; j <= n; j++) {
        if(sequence2.substring(i - 1, i).equals(sequence1.substring(j - 1, j))) {
          d = dpmMatrix[i-1][j-1].score + matchScore;
        } else {
          d = dpmMatrix[i-1][j-1].score + mismatchScore;
        }
        h =
          Math.max(
            dpmMatrix[i][j - 1].horizontal,
            dpmMatrix[i][j - 1].score + gapOpening)
          + gapExtension;
        v =
          Math.max(
            dpmMatrix[i - 1][j].vertical,
            dpmMatrix[i - 1][j].score + gapOpening)
          + gapExtension;
        dpmMatrix[i][j].score =
          Math.max(Math.max(Math.max(h, d), v), 0);
        dpmMatrix[i][j].vertical = v;
        dpmMatrix[i][j].horizontal = h;
        result = String.valueOf(dpmMatrix[i][j].score);
        result.trim();
        if (v == dpmMatrix[i][j].score && v != 0) {
          dpmMatrix[i][j].backVertical = true;
          result = "| " + result;
        } else {
          result = "  " + result;
        }
        if (h == dpmMatrix[i][j].score && h != 0) {
          dpmMatrix[i][j].backHorizontal = true;
          result = "-" + result;
        } else {
          result = " " + result;
        }
        if (d == dpmMatrix[i][j].score && d != 0) {
          dpmMatrix[i][j].backDiagonal = true;
          result = "\\" + result;
        } else {
          result = "  " + result;

        }
      }
    }
  }

  int[] findMaxLocal() {
    int n = sequence1.length();
    int m = sequence2.length();
    int i, j, column, row;
    double maxScore;
    int[] coordinates = new int[2];
    maxScore = dpmMatrix[0][0].score;
    row = 0;
    column = 0;
    for (i = 0; i <= m; i++) {
      for (j = 0; j <= n; j++) {
        if (dpmMatrix[i][j].score > maxScore) {
          maxScore = dpmMatrix[i][j].score;
          column = j;
          row = i;
        }
      }
    }
    coordinates[0] = row;
    coordinates[1] = column;
    return coordinates;
  }

  public String[] traceBackLocalAlignment() {
    String[] alignment = new String[3];
    alignment[0] = new String();
    alignment[1] = new String();
    alignment[2] = new String();
    int[] coordinates;
    int i, j, startColumn, startRow, endColumn, endRow;

    // find starting point for trace back
    coordinates = findMaxLocal();
    i = coordinates[0];
    j = coordinates[1];
    startColumn = j;
    startRow = i;
    optimalScore = dpmMatrix[i][j].score;

    // do traceback
    dpmMatrix[i][j].optimal = true;
    while (dpmMatrix[i][j].score > 0) {
      if (dpmMatrix[i][j].backDiagonal) {
        dpmMatrix[i - 1][j - 1].optimal = true;
        alignment[0] = alignment[0] + sequence1.substring(j - 1, j);
        alignment[2] = alignment[2] + sequence2.substring(i - 1, i);
        if (sequence1
            .substring(j - 1, j)
            .equals(sequence2.substring(i - 1, i))) {
          alignment[1] = alignment[1] + ":";
        } else {
          alignment[1] = alignment[1] + " ";
        }
        i = i - 1;
        j = j - 1;
      } else if (dpmMatrix[i][j].backHorizontal) {
        dpmMatrix[i][j - 1].optimal = true;
        alignment[0] = alignment[0] + sequence1.substring(j - 1, j);
        alignment[2] = alignment[2] + "-";
        alignment[1] = alignment[1] + " ";
        j = j - 1;
      } else if (dpmMatrix[i][j].backVertical) {
        dpmMatrix[i - 1][j].optimal = true;
        alignment[0] = alignment[0] + "-";
        alignment[2] = alignment[2] + sequence2.substring(i - 1, i);
        alignment[1] = alignment[1] + " ";
        i = i - 1;
      } else {
        System.out.println("Error in reconstruction of Alignment");
      }
    }
    endRow = i;
    endColumn = j;
    numberOfCooptimalAlignments(startRow, startColumn, endRow, endColumn);
    numCooptimal = dpmMatrix[endRow][endColumn].numCooptimal;
    StringBuffer str1 = new StringBuffer(alignment[0]);
    StringBuffer str2 = new StringBuffer(alignment[1]);
    StringBuffer str3 = new StringBuffer(alignment[2]);
    alignment[0] = str1.reverse().toString();
    alignment[1] = str2.reverse().toString();
    alignment[2] = str3.reverse().toString();
    return alignment;
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    int i, j;
    int stepX, stepY;
    // Clear visible part of panel
    Dimension dim = getSize();
    int w = (int) dim.getWidth();
    int h = (int) dim.getHeight();
    g2.setColor(bgc.getColor1());
    g2.fillRect(0, 0, w, h);
    // Work out the width of the colums.
    int maxLen = 0;
    for(i=0; i<dpmMatrix.length; i++) {
      for(j=0; j<dpmMatrix[i].length; j++) {
        if(String.valueOf((int)dpmMatrix[i][j].score).length() > maxLen)
          maxLen = String.valueOf((int)dpmMatrix[i][j].score).length();
      }
    }
    stepX = (int)Math.max((maxLen * 14.0),26);
    stepY = 24;
    scaleX = (double)getWidth() / (double)(sequence1.length()+3) / (double)stepX;
    scaleY = (double)getHeight() / (double)(sequence2.length()+4) / (double)stepY;
    double scale = Math.min(scaleX,scaleY);
    int tx = (int)(((double) getWidth() - (double) (sequence1.length()+3)*stepX * scale)
                   / 2.0);
    int ty = (int)(((double) getHeight() - (double) (sequence2.length()+3)*stepY * scale)
                   / 2.0);
    AffineTransform transform = g2.getTransform();
    g2.translate(tx,ty);
    g2.scale(scale,scale);
    // Step sizes in the x and y dimensions.
    Font courier = new Font("courier", Font.BOLD, 10);
    g2.setFont(courier);
    g2.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON);
    // Draw first sequence along x axis
    traceBack = false;
    if(alignmentMode == SETUP_MATRIX) {
      setupMatrix(g2, stepX, stepY);
    } else if(alignmentMode == INITIALIZE_MATRIX) {
      setupMatrix(g2, stepX, stepY);
      initializeMatrix(g2, stepX, stepY);
    } else if(alignmentMode == FILL_MATRIX) {
      setupMatrix(g2, stepX,  stepY);
      initializeMatrix(g2, stepX, stepY);
      fillMatrix(g2, stepX, stepY);
    } else if(alignmentMode == TRACE_BACK) {
      traceBack = true;
      setupMatrix(g2, stepX,  stepY);
      initializeMatrix(g2, stepX, stepY);
      fillMatrix(g2, stepX, stepY);
    }
    g2.setTransform(transform);
  }

  /**
   * Draw the two sequences in the two dimensions of the matrix.
   */
  private void setupMatrix(Graphics2D g2, int stepX, int stepY) {
    int i;
    // Points of the rectangle containing the DP matrix.
    int rx1 = stepX - 2;
    int ry1 = stepY / 2 - 3;
    int rx2 = (sequence1.length()+2) * stepX - stepX / 3;
    int ry2 = (sequence2.length()+3) * stepY - stepY;
    g2.setColor(Color.black);
    g2.drawRoundRect(rx1,ry1,rx2,ry2,30,30);
    // Draw horizontal line
    g2.drawLine(rx1,ry1+stepY,rx2+stepX-2,ry1+stepY);
    // Draw vertical line
    g2.drawLine(rx1+stepX,ry1,rx1+stepX,ry2+stepY/2-3);

    g2.setColor(Color.blue);
    g2.drawString("-",2*stepX,stepY);
    g2.drawString("-",stepX+stepX/3, 2*stepY);
    for (i = 0; i < sequence1.length(); i++) {
      g2.drawString(
        sequence1.substring(i,i+1),
        (i + 3) * stepX - 2,
        stepY);
    }
    for (i=0; i < sequence2.length(); i++) {
      g2.drawString(sequence2.substring(i,i+1), stepX+stepX/3, (i + 3) * stepY);
    }
  }

  private void initializeMatrix(Graphics2D g2, int stepX, int stepY) {
    int i;
    g2.setColor(Color.black);
    for (i = 0; i < sequence1.length()+1; i++) {
      g2.drawString(
        String.valueOf((int)dpmMatrix[0][i].score),
        (i + 2) * stepX,
        2*stepY);
      if(dpmMatrix[0][i].backHorizontal) {
        arrow.drawArrow(g2,
                        (i + 2) * stepX - 4,
                        2*stepY - stepY/4 + 3,
                        (i + 2) * stepX - 2*stepX/4,
                        2*stepY - stepY/4 + 3);
      }
    }
    for (i=1; i < sequence2.length()+1; i++) {
      g2.drawString(String.valueOf((int)dpmMatrix[i][0].score), 2*stepX, (i + 2) * stepY);
      if(dpmMatrix[i][0].backVertical) {
        arrow.drawArrow(g2,
                        2 * stepX + stepX/3 - 5,
                        (i+2)*stepY - stepY/2,
                        2 * stepX + stepX/3 - 5,
                        (i+2)*stepY - stepY + 2);
      }
    }
  }

  private void fillMatrix(Graphics2D g2, int stepX, int stepY) {
    int i, j;
    for(i=0; i<sequence1.length()+1; i++) {
      for(j=0; j<sequence2.length()+1; j++) {
        if(traceBack && dpmMatrix[j][i].optimal) {
          g2.setColor(Color.red);
        } else {
          g2.setColor(Color.black);
        }
        g2.drawString(
          String.valueOf((int)dpmMatrix[j][i].score),
          (i + 2) * stepX,
          (j + 2) * stepY);
        if(dpmMatrix[j][i].backDiagonal) {
          arrow.drawArrow(g2,
                          (i + 2) * stepX - 4,
                          (j + 2) * stepY - stepY/2,
                          (i + 2) * stepX - stepX/2,
                          (j + 2) * stepY - stepY + 2);
          g2.setColor(Color.black);
        }
        if(dpmMatrix[j][i].backHorizontal) {
          arrow.drawArrow(g2,
                          (i + 2) * stepX - 4,
                          (j + 2) * stepY - stepY/4 + 3,
                          (i + 2) * stepX - 2*stepX/4,
                          (j + 2) * stepY - stepY/4 + 3);
        }
        if(dpmMatrix[j][i].backVertical) {
          arrow.drawArrow(g2,
                          (i + 2) * stepX + stepX/3 - 5,
                          (j + 2)*stepY - stepY/2,
                          (i + 2) * stepX + stepX/3 - 5,
                          (j + 2)*stepY - stepY + 2);
        }
      }
    }
  }


  /**
   * Sets the alignmentMode.
   * @param alignmentMode The alignmentMode to set
   */
  public void setAlignmentMode(int alignmentMode) {
    this.alignmentMode = alignmentMode;
  }

  /**
   * Returns the matchScore.
   * @return double
   */
  public double getMatchScore() {
    return matchScore;
  }

  /**
   * Sets the matchScore.
   * @param matchScore The matchScore to set
   */
  public void setMatchScore(double matchScore) {
    this.matchScore = matchScore;
  }

  /**
   * Returns the mismatchScore.
   * @return double
   */
  public double getMismatchScore() {
    return mismatchScore;
  }

  /**
   * Sets the mismatchScore.
   * @param mismatchScore The mismatchScore to set
   */
  public void setMismatchScore(double mismatchScore) {
    this.mismatchScore = mismatchScore;
  }

  /**
   * Returns the gapExtension.
   * @return double
   */
  public double getGapExtension() {
    return gapExtension;
  }

  /**
   * Sets the gapExtension.
   * @param gapExtension The gapExtension to set
   */
  public void setGapExtension(double gapExtension) {
    this.gapExtension = gapExtension;
  }

  /**
   * Returns the gapOpening.
   * @return double
   */
  public double getGapOpening() {
    return gapOpening;
  }

  /**
   * Sets the gapOpening.
   * @param gapOpening The gapOpening to set
   */
  public void setGapOpening(double gapOpening) {
    this.gapOpening = gapOpening;
  }

}
