package haubold.hmm.algorithm;
import java.util.*;
/**
 * @author haubold
 * Date: Nov 26, 2003; time: 9:55:10 AM.
 *
 * Description: Hidden Markov Model with all probabilities
 * initially set to random values. Probabilities attached to
 * the same event sum to unity.
 */
public class RandomHmm extends HiddenMarkovModel {
  private int numStates, numObservations;
  private Random ran;

  public RandomHmm(int numStates, int numObservations) {
    this.numStates = numStates;
    this.numObservations = numObservations;
    ran = new Random();
    generateRandomModel();
  }

  private void generateRandomModel() {
    double[][] tp = new double[numStates][numStates]; // Transition probabilities
    double[][] op = new double[numStates][numObservations]; // Observation proabilities
    double[] ip = new double[numStates]; // Initial probabilities
    double r,s;
    int i, j;
    // Generate initial probabilities
    s = 0.0;
    for(i=0; i<numStates-1; i++) {
      r = ran.nextDouble();
      ip[i] = r;
      s += r;
    }
    ip[numStates-1] = 1.0 - s;
    // Generate transition probabilities
    s = 0.0;
    for(i=0; i<numStates; i++) {
      s = 0.0;
      for(j=0; j<numStates-1; j++) {
        r = ran.nextDouble();
        tp[i][j] = r;
        s += r;
      }
      tp[i][numStates-1] = 1.0 - s;
    }
    // Generate observation probabilities
    s = 0.0;
    for(i=0; i<numStates; i++) {
      s = 0.0;
      for(j=0; j<numObservations-1; j++) {
        r = ran.nextDouble();
        op[i][j] = r;
        s += r;
      }
      op[i][numObservations-1] = 1.0 - s;
    }
    this.setInitialProbabilities(ip);
    this.setTransitionProbabilities(tp);
    this.setObservationProbabilities(op);
  }
}
