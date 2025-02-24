package haubold.wrightfisher;

/**
 * Description: Represents one gene in a Wright-Fisher population.
 *              The children-array contains the indices of the
 *              descendants. An ancestral gene is one that has
 *              descendants in the present.
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Sep 11, 2005; time: 1:38:02 PM.
 */
public class Gene {
  private boolean ancestral;
  private int[] children;
  private boolean mrca, visited;
  int numDesc, parent1, parent2, generation, gene;
  /**
   * @return Returns the ancestral.
   */
  public boolean isAncestral() {
    return ancestral;
  }
  /**
   * @param ancestral The ancestral to set.
   */
  public void setAncestral(boolean ancestral) {
    this.ancestral = ancestral;
  }
  /**
   * @return Returns the children.
   */
  public int[] getChildren() {
    return children;
  }
  /**
   * @param children The children to set.
   */
  public void setChildren(int[] children) {
    this.children = children;
  }
  /**
   * @return Returns the mrca.
   */
  public boolean isMrca() {
    return mrca;
  }
  /**
   * @param mrca The mrca to set.
   */
  public void setMrca(boolean mrca) {
    this.mrca = mrca;
  }
  /**
   * @return Returns the numDesc.
   */
  public int getNumDesc() {
    return numDesc;
  }
  /**
   * @param numDesc The numDesc to set.
   */
  public void setNumDesc(int numDesc) {
    this.numDesc = numDesc;
  }
  /**
   * @return Returns the parent.
   */
  public int getParent1() {
    return parent1;
  }
  public int getParent2() {
    return parent2;
  }
  /**
   * @param parent The parent to set.
   */
  public void setParent1(int parent1) {
    this.parent1 = parent1;
  }
  /**
   * @param parent The parent to set.
   */
  public void setParent2(int parent2) {
    this.parent2 = parent2;
  }
  /**
   * @return Returns the gene.
   */
  public int getGene() {
    return gene;
  }
  /**
   * @param gene The gene to set.
   */
  public void setGene(int gene) {
    this.gene = gene;
  }
  /**
   * @return Returns the generation.
   */
  public int getGeneration() {
    return generation;
  }
  /**
   * @param generation The generation to set.
   */
  public void setGeneration(int generation) {
    this.generation = generation;
  }
  /**
   * @return Returns the visited.
   */
  public boolean isVisited() {
    return visited;
  }
  /**
   * @param visited The visited to set.
   */
  public void setVisited(boolean visited) {
    this.visited = visited;
  }
}
