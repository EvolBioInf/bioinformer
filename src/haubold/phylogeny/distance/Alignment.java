package haubold.phylogeny.distance;

/**
 * @author Bernhard Haubold
 * Date: Jun 10, 2003; time: 11:00:14 AM.
 *
 * Description: Data transport object for alignments.
 */
public class Alignment {

  char[][] alignment;
  String[] taxa;
  double[][] distanceMatrix;

  public Alignment() {
  }

  public Alignment(char[][] alignment, String[] taxa) {
    this.alignment = alignment;
    this.taxa = taxa;
  }

  /**
   * Returns the alignment.
   * @return char[][]
   */
  public char[][] getAlignment() {
    return alignment;
  }

  /**
   * Returns the taxa.
   * @return String[]
   */
  public String[] getTaxa() {
    return taxa;
  }

  /**
   * Sets the alignment.
   * @param alignment The alignment to set
   */
  public void setAlignment(char[][] alignment) {
    this.alignment = alignment;
  }

  /**
   * Sets the taxa.
   * @param taxa The taxa to set
   */
  public void setTaxa(String[] taxa) {
    this.taxa = taxa;
  }

  /**
   * Returns the distanceMatrix.
   * @return double[][]
   */
  public double[][] getDistanceMatrix() {
    return distanceMatrix;
  }

  /**
   * Sets the distanceMatrix.
   * @param distanceMatrix The distanceMatrix to set
   */
  public void setDistanceMatrix(double[][] distanceMatrix) {
    this.distanceMatrix = distanceMatrix;
  }

}
