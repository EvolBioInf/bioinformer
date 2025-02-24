package haubold.phylogeny.distance;

/**
 * @author Bernhard Haubold
 * Date: Jun 10, 2003; time: 9:35:52 AM.
 *
 * Description: Calculate distances between pairs of
 * sequences or other discrete character data.
 */
public class DistanceComputation {
  char[][] alignment;
  double[][] jukesCantor,mismatches,normalizedMismatches;

  public DistanceComputation(char[][] alignment) {
    this.alignment = filterAlignment(alignment);
  }

  private double[][] computeJCDistances(char[][] alignment) {
    // start with sanity check
    if(alignment == null)
      return null;
    int n = alignment.length;
    int i,j;
    double[][] dm = computeNormalizedMismatches(alignment);

    for(i=0; i<n-1; i++) {
      for(j=i+1; j<n; j++) {
        dm[i][j]=3.0/4.0*Math.log(3.0/(4.0*(1.0-dm[i][j])-1));
        dm[j][i]=dm[i][j];
      }
    }

    return dm;
  }

  private double[][] computeNormalizedMismatches(char[][] alignment) {
    // start with sanity check
    if(alignment == null)
      return null;
    int n = alignment.length;
    double l = alignment[0].length;
    double[][] dm = computeMismatches(alignment);
    int i,j;
    for(i=0; i<n; i++) {
      for(j=0; j<n; j++) {
        dm[i][j] = dm[i][j]/l;
      }
    }
    return dm;
  }

  private double[][] computeMismatches(char[][] alignment) {
    // sanity check
    if(alignment == null)
      return null;
    int n = alignment.length;
    int l = alignment[0].length;
    double[][] dm = new double[n][n];
    int i,j,k;
    // Initialize diagonal of matrix to zero
    for(i=0; i<n; i++) {
      dm[i][i]=0.0;
    }

    // Fill in remainder of matrix
    for(i=0; i<n-1; i++) {
      for(j=i+1; j<n; j++) {
        dm[i][j]=0.0;
        for(k=0; k<l; k++) {
          if(alignment[i][k] != alignment [j][k]) {
            dm[i][j]++;
          }
        }
        dm[j][i]=dm[i][j];
      }
    }

    return dm;
  }

  /**
   * Remove indels from alignment.
   */
  private char[][] filterAlignment(char[][] alignment) {
    char[][] alignment2 = new char[alignment.length][alignment[0].length];
    char[][] alignment3;
    boolean indel = false;
    int i, j, c;

    // check that all sequences have same length
    for(i=1; i<alignment.length; i++)
      if(alignment[0].length != alignment[i].length)
        return null;
    c = 0;
    for(j=0; j<alignment[0].length; j++) {
      indel = false;
      innerLoop: for(i=0; i<alignment.length; i++) {
        if(alignment[i][j] == '-') {
          indel = true;
          break innerLoop;
        }
      }
      if(!indel) {
        for(i=0; i<alignment.length; i++) {
          alignment2[i][c] = alignment[i][j];
        }
        c++;
      }
    }
    alignment3 = new char[alignment.length][c];
    for(j=0; j<c; j++) {
      for(i=0; i<alignment.length; i++) {
        alignment3[i][j] = alignment2[i][j];
      }
    }
    return alignment3;
  }

  /**
   * Returns the alignment.
   * @return char[][]
   */
  public char[][] getAlignment() {
    return alignment;
  }

  /**
   * Returns the jukesCantor.
   * @return double[][]
   */
  public double[][] getJukesCantor() {
    return computeJCDistances(alignment);
  }

  /**
   * Returns the mismatches.
   * @return double[][]
   */
  public double[][] getMismatches() {
    return computeMismatches(alignment);
  }

  /**
   * Returns the normalizedMismatches.
   * @return double[][]
   */
  public double[][] getNormalizedMismatches() {
    return computeNormalizedMismatches(alignment);
  }

  /**
   * Sets the alignment.
   * @param alignment The alignment to set
   */
  public void setAlignment(char[][] alignment) {
    this.alignment = filterAlignment(alignment);
  }

}
