package haubold.sm;

/**
 * @author Bernhard Haubold
 * Date: Apr 5, 2003; time: 12:51:00 PM.
 *
 * Description: Compute full series of PAM protein substitution matrices.
 * Input data taken from Dayhoff, Schwartz, and Orcutt (1978).
 */

import haubold.maths.*;

public class PamComputation {

  int[][] lod = new int[20][20];
  double[][] pamN = new double[20][20];

  // Taken from Figure 82 in Dayhoff et al. (1978); Data checked agains original
  // publication & corrected on April 7, 2003.
  double[][] pam1 = {
    {9867,   2,    9,   10,    3,    8,   17,   21,    2,    6, 4, 2, 6, 2, 22, 35, 32, 0, 2, 18},
    {1,   9913,    1,    0,    1,   10,    0,    0,   10,    3, 1, 19, 4, 1,  4,  6,  1, 8, 0,  1},
    {4,      1, 9822,   36,    0,    4,    6,    6,   21,    3, 1, 13,  0, 1, 2, 20, 9, 1, 4, 1},
    {6,      0,   42, 9859,    0,    6,   53,    6,    4,    1, 0, 3, 0, 0, 1, 5, 3, 0, 0, 1},
    {1,      1,    0,    0, 9973,    0,    0,    0,    1,    1, 0, 0, 0, 0, 1, 5, 1, 0, 3, 2},
    {3,      9,    4,    5,    0, 9876,   27,    1,   23,    1, 3, 6, 4, 0, 6, 2, 2, 0, 0, 1},
    {10,     0,    7,   56,    0,   35, 9865,    4,    2,    3, 1, 4, 1, 0, 3, 4, 2, 0, 1, 2},
    {21,     1,   12,   11,    1,    3,    7, 9935,    1,    0, 1, 2, 1, 1, 3, 21, 3, 0, 0, 5},
    {1,      8,   18,    3,    1,   20,    1,    0, 9912,    0, 1, 1, 0, 2, 3, 1, 1, 1, 4, 1},
    {2,      2,    3,    1,    2,    1,    2,    0,    0, 9872, 9, 2, 12, 7, 0, 1, 7, 0, 1, 33},
    {3,      1,    3,    0,    0,    6,    1,    1,    4,   22, 9947, 2, 45, 13, 3, 1, 3, 4, 2, 15},
    {2,     37,   25,    6,    0,   12,    7,    2,    2,    4, 1, 9926, 20, 0, 3, 8, 11, 0, 1, 1},
    {1,      1,    0,    0,    0,    2,    0,    0,    0,    5, 8, 4, 9874, 1, 0, 1, 2, 0, 0, 4},
    {1,      1,    1,    0,    0,    0,    0,    1,    2,    8, 6, 0, 4, 9946, 0, 2, 1, 3, 28, 0},
    {13,     5,    2,    1,    1,    8,    3,    2,    5,    1, 2, 2, 1, 1, 9926, 12, 4, 0, 0, 2},
    {28,    11,   34,    7,   11,    4,    6,   16,    2,    2, 1, 7, 4, 3, 17, 9840, 38, 5, 2, 2},
    {22,     2,   13,    4,    1,    3,    2,    2,    1,   11, 2, 8, 6, 1, 5, 32, 9871, 0, 2, 9},
    {0,      2,    0,    0,    0,    0,    0,    0,    0,    0, 0, 0, 0, 1, 0, 1, 0, 9976, 1, 0},
    {1,      0,    3,    0,    3,    0,    1,    0,    4,    1, 1, 0, 0, 21, 0, 1, 1, 2, 9945, 1},
    {13,     2,    1,    1,    3,    2,    2,    3,    3,    57, 11, 1, 17, 1, 3, 2, 10, 0, 2, 9901}
  };
  // Taken from Table 22 in Dayhoff et al. (1978). Frequencies refer to amino acids
  // in the same order as in pam1.
  double[] frequency = {
    0.087, 0.041, 0.040, 0.047, 0.033,
    0.038, 0.050, 0.089, 0.034, 0.037,
    0.085, 0.081, 0.015, 0.040, 0.051,
    0.070, 0.058, 0.010, 0.030, 0.065
  };
  // Same order as in the frequency array and pam1.
  String[] aminoAcids = {"A", "R", "N", "D", "C", "Q", "E", "G", "H", "I",
                         "L", "K", "M", "F", "P", "S", "T", "W", "Y", "V"
                        };

  public PamComputation() {
    int i, j;
    for (i = 0; i < 20; i++) {
      for (j = 0; j < 20; j++) {
        pam1[i][j] = pam1[i][j] / 10000.0;
      }
    }
  }

  /**
   * Generate PAMn matrix by matrix multiplication.
   */
  private double[][] computePamN(int n) {
    int i, j;
    // initialize pamN to identity matrix
    for (i = 0; i < 20; i++) {
      pamN[i][i] = 1.0;
      for (j = 0; j < 20; j++) {
        if (i != j) {
          pamN[i][j] = 0.0;
        }
      }
    }
    // do matrix multiplication
    for (i = 1; i <= n; i++) {
      pamN = Algebra.matrixMultiplication(pamN, pam1);
    }
    return pamN;
  }

  /**
   * Calculate evolutionary distance implied by a given PAM matrix.
   */
  public int getPercentDifference() {
    int i;
    double s = 0.0;
    int difference;
    for (i = 0; i < 20; i++) {
      s = s + frequency[i] * 1.0 * pamN[i][i];
    }
    difference = (int)Math.round(100.0 * (1.0 - s));
    return difference;
  }

  /**
   * Transform PAM-matrix to its log-odds format.
   */
  public int[][] getLod(int n, double scale) {
    computePamN(n);
    int i, j;

    for (i = 0; i < 20; i++) {
      for (j = 0; j < 20; j++) {
        lod[i][j] =
          (int)Math.round(Math.log(pamN[i][j] / frequency[i]) / (Math.log(2.0) / scale));
      }
    }
    return lod;
  }
  /**
   * @return
   */
  public String[] getAminoAcids() {
    return aminoAcids;
  }

}
