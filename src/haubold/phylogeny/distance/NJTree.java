package haubold.phylogeny.distance;

/**
 * @author Bernhard Haubold
 * Date: Jun 18, 2003; time: 10:30:15 AM.
 *
 * Description: Compute a phylogenetic tree using the
 * neighbor-joining algorithm.
 * Reference: Weir, B. S. (1996). Genetic Data Analysis II.
 *            Sinauer, ISBN: 0-87893-902-4; p. 353ff.
 */
import java.text.*;
import java.util.*;
import haubold.phylogeny.util.*;

public class NJTree extends DistanceTree {

  Node root = null;
  Node[] tree;
  ArrayList<Integer> indexList1 = new ArrayList<Integer>();
  ArrayList<Integer> indexList2 = new ArrayList<Integer>();
  DecimalFormat decimalFormat = new DecimalFormat("0.000");
  int nodeCounter;
  boolean debug;
  TreePanel treePanel = new TreePanel();

  public NJTree(double[][] distanceMatrix, String[] taxa) {
    this.distanceMatrix = distanceMatrix;
    this.taxa = taxa;
    computeTree();
  }

  private void computeTree() {
    tree = initializeTree(distanceMatrix, taxa);
    tree = constructTreeTopology(distanceMatrix,tree);
    tree = computeXPositions(tree);
    treePanel.setTree(tree);
    if(debug) {
      monitorTree(tree[tree.length-1]);
    }
  }

  private Node[] initializeTree(double[][] dm, String[] taxa) {
    int n = dm.length;
    Node[] tree = new Node[2 * n - 2];
    int i;
    // Generate leaf nodes
    for (i = 0; i < n; i++) {
      tree[i] = new LeafNode();
      tree[i].setYPosition(0.0);
      tree[i].setIndex(i);
      if (taxa[i] != null) {
        ((LeafNode) tree[i]).setLabel(taxa[i]);
      } else {
        ((LeafNode) tree[i]).setLabel("");
      }
    }

    // Generate internal nodes
    for(i=n; i<2*n-2; i++) {
      tree[i] = new InternalNode();
      tree[i].setIndex(i);
    }
    return tree;
  }


  private Node[] constructTreeTopology(double[][] dm, Node[] tree) {
    double dm2[][] = new double[dm.length][dm.length];
    double correctedDist[][] = new double[dm.length][dm.length];
    double totalDist[] = new double[dm.length];
    int[] indices = new int[dm.length];
    int[] minIndices;
    int i,j;
    int n = dm.length-2;
    // Initialize index array
    for(i=0; i<indices.length; i++) {
      indices[i] = i;
    }
    // Initialize a second copy of the distance matrix, dm2
    // TODO: check that this is really necessary.
    for(i=0; i<dm.length; i++) {
      for(j=0; j<dm.length; j++) {
        dm2[i][j] = dm[i][j];
      }
    }

    // Initialize the array of total distances
    for(i=0; i<dm.length; i++) {
      totalDist[i] = 0.0;
      for(j=0; j<dm.length; j++) {
        totalDist[i] += dm[i][j];
      }
    }

    // Initialize matrix containing corrected distances, correctedDist
    for(i=0; i<dm.length; i++) {
      for(j=0; j<dm.length; j++) {
        correctedDist[i][j] = dm[i][j] -
                              ((totalDist[i]+totalDist[j])/(double)(dm.length-2.0));
      }
    }

    for(i = dm.length; i < tree.length - 3; i++) {
//			printDistanceMatrix(dm2,indices);
      minIndices = findMinIndices(correctedDist);
      tree[i].setLeftChild(tree[indices[minIndices[0]]]);
      tree[i].setRightChild(tree[indices[minIndices[1]]]);
      tree[i].getLeftChild().setParent(tree[i]);
      tree[i].getRightChild().setParent(tree[i]);

      // Compute branch lengths
      double dLeft = 1.0/(2.0*((double)n-2.0))*(((double)n-2.0)*dm2[indices[minIndices[0]]][indices[minIndices[1]]]
                     + totalDist[indices[minIndices[0]]]
                     - totalDist[indices[minIndices[1]]]);
      double dRight = 1.0/(2.0*((double)n-2.0))*(((double)n-2.0)*dm2[indices[minIndices[0]]][indices[minIndices[1]]]
                      - totalDist[indices[minIndices[0]]]
                      + totalDist[indices[minIndices[1]]]);
      tree[i].getLeftChild().setBranchLength(dLeft);
      tree[i].getRightChild().setBranchLength(dRight);
      n--;
      // Remove the indices of the clustered nodes and add index of the new node (i)
      indices[Math.max(minIndices[0],minIndices[1])] = indices[n];
      indices[Math.min(minIndices[1],minIndices[0])] = i;
      // CONTINUE HERE
      // Adjust distance matrix
      double[][] dm3 = new double[n][n];
      for(int k=0; k<n; k++) {
        for(int l=k+1; l<0; l++) {
          if(indices[k] != i && indices[l] != i) {
            dm3[k][l] = dm2[k][l];
            dm3[l][k] = dm3[k][l];
          } else {
          }
        }
      }

      dm2 = reconstructDistanceMatrix(dm2,indices,n, tree);
    }

    return tree;
  }


  private double[][] reconstructDistanceMatrix(double[][] dm,int[] indices,int n, Node[] tree) {
    double[][] dm2 = new double[n][n];
    int i,j;

    for(i=0; i<n-1; i++) {
      for(j=i+1; j<n; j++) {
        dm2[i][j] =
          dm2[i][j] = distance(tree[indices[i]],tree[indices[j]],dm);
        dm2[j][i] = dm2[i][j];
      }
    }


    return dm2;

  }

  private int[] findMinIndices(double[][] dm) {
    int[] indices = new int[2];
    double min = +Double.MAX_VALUE;
    int i,j;

    for(i=0; i<dm.length-1; i++) {
      for(j=i+1; j<dm.length; j++) {
        if(min > dm[i][j]) {
          min = dm[i][j];
          indices[0] = i;
          indices[1] = j;
        }
      }
    }
    return indices;
  }

  private double distance(Node node1, Node node2, double[][] dm) {
    double d = 0.0;
    int i,j,n1,n2,x,y;
    // Construct list of relevant leaves
    indexList1.clear();
    indexList2.clear();
    findLeafIndices(node1, indexList1);
    findLeafIndices(node2, indexList2);
    // Calculate distances
    n1 = indexList1.size();
    n2 = indexList2.size();
    for(i=0; i<n1; i++) {
      for(j=0; j<n2; j++) {
        x = Integer.parseInt(indexList1.get(i).toString());
        y = Integer.parseInt(indexList2.get(j).toString());
        d += dm[x][y];
      }
    }
    d /= (double)n1*n2;
    return d;
  }

  private void findLeafIndices(Node node, ArrayList<Integer> indexList) {
    if(!node.isLeaf()) {
      findLeafIndices(node.getLeftChild(), indexList);
    }

    if(!node.isLeaf()) {
      findLeafIndices(node.getRightChild(), indexList);
    } else {
      indexList.add(Integer.valueOf(node.getIndex()));
    }
  }

  public void setDistanceMatrix(double[][] distanceMatrix) {
    this.distanceMatrix = distanceMatrix;
    computeTree();
  }

  /**
   * @see haubold.phylogeny.util.Tree#drawTree()
   */

  private Node[] computeXPositions(Node[] tree) {
    double h = tree[tree.length-1].getYPosition();
    double w = h/1.618;
    double xStep = w/(double)(tree.length-1);
    nodeCounter = 0;
    traverseXPositions(tree[tree.length-1], xStep);
    return tree;
  }

  public Node[] getTree() {
    return tree;
  }

//	private void printDistanceMatrix(double[][] dm, int[] indices){
//		System.out.print("\n\n");
//		for(int i=0; i<dm.length; i++){
//			System.out.print(indices[i] + "\t");
//			for(int j=0; j<dm.length; j++){
//				System.out.print(decimalFormat.format(dm[i][j]) + "\t");
//			}
//			System.out.print("\n");
//		}
//	}

  private void monitorTree(Node node) {
    if(!node.isLeaf()) {
      monitorTree(node.getLeftChild());
    }

    System.out.println("id: " + node.getId() + " leaf: " + node.isLeaf()
                       + " xPosition: " + node.getXPosition());

    if(!node.isLeaf()) {
      monitorTree(node = node.getRightChild());
    }
  }

  private void traverseXPositions(Node node, double xStep) {
    if(!node.isLeaf()) {
      traverseXPositions(node.getLeftChild(),xStep);
    }

    node.setXPosition(nodeCounter*xStep);
    nodeCounter++;

    if(!node.isLeaf()) {
      traverseXPositions(node.getRightChild(),xStep);
    }

  }

  /**
   * Returns the debug.
   * @return boolean
   */
  public boolean isDebug() {
    return debug;
  }

  /**
   * Sets the debug.
   * @param debug The debug to set
   */
  public void setDebug(boolean debug) {
    this.debug = debug;
  }

}
