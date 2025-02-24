package haubold.hmm.algorithm;

/**
 * @author haubold
 * Date: Nov 26, 2003; time: 9:43:21 AM.
 *
 * Description: Hidden Markov Model with all probabilities initially
 * set to zero.
 */
public class BlankHmm extends HiddenMarkovModel {

  private int numStates, numObservations;

  public BlankHmm(int numStates, int numObservations) {
    this.numStates = numStates;
    this.numObservations = numObservations;
    generateBlankModel();
  }

  private void generateBlankModel() {
    int i, j;
    // Allocate transition probabilities
    double[][] tp = new double[numStates][numStates];
    // Allocate observation proabilities
    double[][] op = new double[numStates][numObservations];
    // Allocate initial probabilities
    double[] ip = new double[numStates];
    // Generate initial probabilities
    for(i=0; i<numStates; i++) {
      ip[i] = 0.0;
    }
    // Generate transition probabilities
    for(i=0; i<numStates; i++) {
      for(j=0; j<numStates; j++) {
        tp[i][j] = 0.0;
      }
    }
    // Generate observation probabilities
    for(i=0; i<numStates; i++) {
      for(j=0; j<numObservations; j++) {
        op[i][j] = 0.0;
      }
    }
    this.setInitialProbabilities(ip);
    this.setTransitionProbabilities(tp);
    this.setObservationProbabilities(op);
  }
}
