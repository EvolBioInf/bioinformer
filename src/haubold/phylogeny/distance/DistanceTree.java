package haubold.phylogeny.distance;

import haubold.phylogeny.util.Node;
import haubold.phylogeny.util.Tree;

/**
 * @author Bernhard Haubold
 * Date: Jun 8, 2003; time: 10:01:26 AM.
 *
 * Description:
 */
public abstract class DistanceTree implements Tree {

  double[][] distanceMatrix;
  Node root;
  String label;
  String[] taxa;

  public DistanceTree() {
  }

  public DistanceTree(double[][] distanceMatrix, String taxa[]) {
    this.distanceMatrix = distanceMatrix;
    this.taxa = taxa;
  }


  /**
   * @see haubold.phylogeny.util.Tree#getRoot()
   */
  public Node getRoot() {
    return root;
  }

  /**
   * @see haubold.phylogeny.util.Tree#getLabel()
   */
  public String getLabel() {
    return label;
  }

  /**
   * @see haubold.phylogeny.util.Tree#setLabel(String)
   */
  public void setLabel(String label) {
    this.label = label;
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

  /**
   * Returns the taxa.
   * @return String[]
   */
  public String[] getTaxa() {
    return taxa;
  }

  /**
   * Sets the taxa.
   * @param taxa The taxa to set
   */
  public void setTaxa(String[] taxa) {
    this.taxa = taxa;
  }

}
