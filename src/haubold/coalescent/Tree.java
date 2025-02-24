package haubold.coalescent;

import java.util.*;

/**
 * A <code>Tree</code> object is an array of nodes that defines a binary tree.
 * This tree represents a random genealogy of a sample of genes drawn from
 * a Fisher-Wright population and evolving without recombination.
 *
 * @version April 2, 2002
 * @author Bernhard Haubold
 * @since April 2, 2002
 *
 */

/**
 * Provides the building blocks of the coalescent tree.
 */
class Node {
  public double xPos;
  public double time;
  public double mutations;
  public int id;
  public int numMut;
  public Node desc1;
  public Node desc2;
  public Node ancestor;
}

public class Tree {
  private Node[] tree;
  int sampleSize;
  double[][] treeCoord;
  double[][] haplotypes;
  double pos;
  double ranPos;
  double theta;
  double totalMutations;
  Mutation[] mutations;
  NumericalTools nt = new NumericalTools();
  Random randomNumber = new Random();

  //-------------------------------------------------------------------------------------
  public void makeTree(int n) {
    sampleSize = n;
    double t = 0.0; // time variable
    int i; // loop variable
    int doubleSampleSize = 2 * sampleSize; // avoid repeated multiplication
    // of sampleSize
    tree = new Node[doubleSampleSize - 1];
    int pick; // index od randomly piced node
    int list[] = new int[sampleSize]; // list of node indices
    for (i = 0; i < 2 * sampleSize - 1; i++) {
      tree[i] = new Node();
    }
    // Set times of leaves to zero, nullify their descendants, set number of
    // mutations to zero
    // and given them an identifyer
    for (i = 0; i < sampleSize; i++) {
      tree[i].time = 0.0;
      tree[i].desc1 = null;
      tree[i].desc2 = null;
      tree[i].numMut = 0;
      tree[i].id = i;
    }
    // Nullify ancestor of root
    tree[doubleSampleSize - 2].ancestor = null;
    // Set times of nodes
    for (i = sampleSize; i > 1; i--) {
      t = t
          + (-2.0 * Math.log(1.0 - randomNumber.nextDouble()) / (double) (i * (i - 1)));
      tree[doubleSampleSize - i].time = t;
    }
    // Create tree topology
    for (i = 0; i < sampleSize; i++) {
      list[i] = i;
    }
    for (i = sampleSize; i > 1; i--) {
      pick = (int) (randomNumber.nextDouble() * (double) i);
      tree[list[pick]].ancestor = tree[doubleSampleSize - i];
      tree[doubleSampleSize - i].desc1 = tree[list[pick]];
      list[pick] = list[i - 1];
      pick = (int) (randomNumber.nextDouble() * (double) (i - 1.0));
      tree[list[pick]].ancestor = tree[doubleSampleSize - i];
      tree[doubleSampleSize - i].desc2 = tree[list[pick]];
      list[pick] = doubleSampleSize - i;
    }
    pos = 0.0;
    totalMutations = 0.0;
    detX(tree[2 * sampleSize - 2]);
  }

  //-------------------------------------------------------------------------------------
  public double[][] getTreeCoordinates() {
    int i;
    treeCoord = new double[2 * sampleSize - 1][4];
    for (i = 0; i < 2 * sampleSize - 1; i++) {
      if (tree[i].ancestor != null) {
        treeCoord[i][0] = tree[i].xPos;
        treeCoord[i][1] = tree[i].time;
        treeCoord[i][2] = tree[i].ancestor.xPos;
        treeCoord[i][3] = tree[i].ancestor.time;
      }
    }
    return treeCoord;
  }

  //-------------------------------------------------------------------------------------
  public void mutate(double tc) {
    theta = tc;
    detMut(tree[2 * sampleSize - 2]);
  }

  //-------------------------------------------------------------------------------------
  private void detX(Node localRoot) { // in order traversal of tree
    if (localRoot != null) {
      detX(localRoot.desc1);
      localRoot.xPos = pos;
      pos++;
      detX(localRoot.desc2);
    }
  }

  //-------------------------------------------------------------------------------------
  private void detMut(Node localRoot) {
    if (localRoot != null) {
      detMut(localRoot.desc1);
      if (localRoot.ancestor != null) {
        localRoot.mutations = NumericalTools.nextPoisson(theta / 2.0
                              * (localRoot.ancestor.time - localRoot.time));
        totalMutations = totalMutations + localRoot.mutations;
      }
      detMut(localRoot.desc2);
    }
  }

  //-------------------------------------------------------------------------------------
  public MutationObject getMutationCoordinates() {
    int i, j, m;
    MutationObject mo = new MutationObject();
    int mut = 0;
    double deltaX, deltaY;
    double[][] mutCoord = new double[(int) totalMutations][2];
    double[][] haplotypes = new double[sampleSize][(int) totalMutations];
    for (i = 0; i < 2 * sampleSize - 1; i++) {
      if (tree[i].mutations > 0.0) {
        m = (int) tree[i].mutations;
        deltaX = tree[i].xPos - tree[i].ancestor.xPos;
        deltaY = tree[i].ancestor.time - tree[i].time;
        for (j = 0; j < m; j++) {
          ranPos = randomNumber.nextDouble();
          makeHaplotypes(tree[i], haplotypes, mut);
          ranPos = randomNumber.nextDouble();
          mutCoord[mut][0] = (tree[i].xPos - deltaX * ranPos);
          if (deltaX < 0) {
            mutCoord[mut][0] = mutCoord[mut][0] * 0.99;
          }
          mutCoord[mut][1] = tree[i].time + deltaY * ranPos;
          mut++;
        }
      }
    }
    mo.mutCoord = mutCoord;
    mo.haplotypes = haplotypes;
    return mo;
  }

  //-------------------------------------------------------------------------------------
  private void makeHaplotypes(Node localRoot, double[][] haplotypes, int mut) {
    if (localRoot != null) {
      makeHaplotypes(localRoot.desc1, haplotypes, mut);
      if (localRoot.desc1 == null) { // found leaf
        haplotypes[localRoot.id][mut] = ranPos;
      }
      makeHaplotypes(localRoot.desc2, haplotypes, mut);
    }
  }

  //-------------------------------------------------------------------------------------
  public int[] getLeafId() {
    int[] leafId = new int[sampleSize];
    pos = 0.0;
    computeLeafId(tree[2 * sampleSize - 2], leafId);
    return leafId;
  }

  //-------------------------------------------------------------------------------------
  private void computeLeafId(Node localRoot, int[] leafId) {
    if (localRoot != null) {
      computeLeafId(localRoot.desc1, leafId);
      if (localRoot.desc1 == null) { // found leaf
        leafId[(int) pos] = localRoot.id;
        pos++;
      }
      computeLeafId(localRoot.desc2, leafId);
    }
  }
}


class MutationObject {
  double[][] mutCoord;
  double[][] haplotypes;
}
