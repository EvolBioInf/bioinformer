package haubold.phylogeny.util;

/**
 * @author Bernhard Haubold
 * Date: Jun 9, 2003; time: 4:41:28 PM.
 *
 * Description: Data transfer object for distance matrices.
 */
public class DistanceMatrix {

  double[][] distanceMatrix;
  String[] taxa;

  /**
   * Returns the distanceMatrix.
   * @return double[][]
   */
  public double[][] getDistanceMatrix() {
    return distanceMatrix;
  }

  /**
   * Returns the taxa.
   * @return String[]
   */
  public String[] getTaxa() {
    return taxa;
  }

  /**
   * Sets the distanceMatrix.
   * @param distanceMatrix The distanceMatrix to set
   */
  public void setDistanceMatrix(double[][] distanceMatrix) {
    this.distanceMatrix = distanceMatrix;
  }

  /**
   * Sets the taxa.
   * @param taxa The taxa to set
   */
  public void setTaxa(String[] taxa) {
    this.taxa = taxa;
  }

}
