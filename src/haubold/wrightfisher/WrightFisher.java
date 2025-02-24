package haubold.wrightfisher;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * Description: Computation of Wright-Fisher graph.
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Oct 5, 2005; time: 12:30:17 PM.
 */
public class WrightFisher {
  private Random ran;
  private Node[][] pop;
  private int posTable[];
  boolean mrca;

  public WrightFisher() {
    ran = new Random();
  }

  public Node[][] constructPopulation(int numGen, int popSize) {
    int i, j, r, m;
    posTable = new int[numGen];
    // generate population
    pop = new Node[numGen][popSize];
    for(i=0; i<numGen; i++) {
      for(j=0; j<popSize; j++) {
        pop[i][j] = new Node();
        pop[i][j].setId(j+1);
        pop[i][j].setGeneration(i);
        pop[i][j].setTangledX(j);
        pop[i][j].setShape(new Ellipse2D.Double((j+4)*10-2,(i+1)*12-2,4,4));
        pop[i][j].setColorForward(Color.black);
        pop[i][j].setColorBackward(Color.black);
      }
    }
    // set descendants of terminal nodes
    m = numGen-1;
    for(i=0; i<popSize; i++)
      pop[m][i].getDescendants().add(pop[numGen-1][i]);
    // establish genealogy
    for(i=numGen-1; i>0; i--) {
      for(j=0; j<popSize; j++) {
        // pick first ancestor
        r = ran.nextInt(popSize);
        pop[i][j].setAncestor1(pop[i-1][r]);
        pop[i-1][r].getChildren().add(pop[i][j]);
        pop[i-1][r].getOffspring().add(pop[i][j]);
        uniteDescendants(pop[i][j],pop[i-1][r]);
        // pick second ancestor
        r = ran.nextInt(popSize);
        pop[i][j].setAncestor2(pop[i-1][r]);
        pop[i-1][r].getChildren().add(pop[i][j]);
        uniteDescendants(pop[i][j],pop[i-1][r]);
      }
    }
    untangle();
    return pop;
  }
  private void untangle() {
    int i, j, numGen, popSize;

    numGen = pop.length;
    popSize = pop[0].length;

    for(i=0; i<numGen; i++) {
      for(j=0; j<popSize; j++) {
        if(!pop[i][j].isVisited()) {
          traverseSubgraph(pop[i][j]);
        }
      }
    }
  }

  private void traverseSubgraph(Node node) {
    ArrayList offspring;
    int i;

    if(node != null) {
      node.setUntangledShape((new Ellipse2D.Double((posTable[node.getGeneration()]+4)*10-2,
                              (node.getGeneration()+1)*12-2,4,4)));
      posTable[node.getGeneration()]++;
      node.setVisited(true);
      offspring = node.getOffspring();
      for(i=0; i<offspring.size(); i++)
        traverseSubgraph((Node)offspring.get(i));
    }
  }

  /**
   * Parent inherits all descendants of child.
   * @param child
   * @param parent
   */
  private void uniteDescendants(Node child, Node parent) {
    int i;
    for(i=0; i<child.getDescendants().size(); i++) {
      if(!parent.getDescendants().contains(child.getDescendants().get(i)))
        parent.getDescendants().add(child.getDescendants().get(i));

    }
  }

  public boolean isMrca() {
    return mrca;
  }
  public void setMrca(boolean mrca) {
    this.mrca = mrca;
  }
}
