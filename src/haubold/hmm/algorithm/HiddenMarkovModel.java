package haubold.hmm.algorithm;

/**
 * @author Bernhard Haubold
 * Date: Oct 20, 2003; time: 6:06:38 AM.
 *
 * Description:
 */
public class HiddenMarkovModel {
  private double[][] transitionProbabilities;
  private double[][] observationProbabilities;
  private double[] initialProbabilities;

  public HiddenMarkovModel() {
  }

  public HiddenMarkovModel(double[][] transitionProbabilities,
                           double[][] observationProbabilities,
                           double[] initialProbabilities) {
    this.transitionProbabilities = transitionProbabilities;
    this.observationProbabilities = observationProbabilities;
    this.initialProbabilities = initialProbabilities;
  }

  public HiddenMarkovModel copy() {
    HiddenMarkovModel newHmm = new HiddenMarkovModel();
    double[] ip = new double[this.getNumStates()];
    double[][] op = new double[this.getNumStates()][this.getNumObservationSymbols()];
    double[][] tp = new double[this.getNumStates()][this.getNumStates()];
    int i,j;
    // Copy initial probabilities
    for(i=0; i<this.getNumStates(); i++) {
      ip[i] = this.getInitialProbabilities()[i];
    }
    // Copy transition probabilities
    for(i=0; i<this.getNumStates(); i++) {
      for(j=0; j<this.getNumStates(); j++) {
        tp[i][j] = this.getTransitionProbabilities()[i][j];
      }
    }
    // Copy obervation probabilities
    for(i=0; i<this.getNumStates(); i++) {
      for(j=0; j<this.getNumObservationSymbols(); j++) {
        op[i][j] = this.getObservationProbabilities()[i][j];
      }
    }
    // Set the copied values
    newHmm.setInitialProbabilities(ip);
    newHmm.setObservationProbabilities(op);
    newHmm.setTransitionProbabilities(tp);
    return newHmm;
  }

  /**
   * Returns the initial probabilities i, where i[j] is the initial
   * probability of state j.
   * @return double[]
   */
  public double[] getInitialProbabilities() {
    return initialProbabilities;
  }

  /**
   * Returns the number of observation symbols.
   * @return int
   */
  public int getNumObservationSymbols() {
    return observationProbabilities[0].length;
  }

  /**
   * Returns the number of states.
   * @return int
   */
  public int getNumStates() {
    return transitionProbabilities.length;
  }

  /**
   * Returns the observation probabilities o, where o[i][j]
   * is the probability of observing symbol j in state i.
   * @return double[][]
   */
  public double[][] getObservationProbabilities() {
    return observationProbabilities;
  }

  /**
   * Returns the transition probabilities t, where t[i][j] is the probability
   * of going from state i at time t to state j at time t+1.
   * @return double[][]
   */
  public double[][] getTransitionProbabilities() {
    return transitionProbabilities;
  }

  /**
   * Sets the initial probabilities i, where i[j] is the initial
   * probability of state j.
   * @param initialProbabilities The initialProbabilities to set
   */
  public void setInitialProbabilities(double[] initialProbabilities) {
    this.initialProbabilities = initialProbabilities;
  }

  /**
   * Sets the observation probabilities o, where o[i][j] is the probability
   * of observing symbol j in state i.
   * @param observationProbabilities The observation probabilities to set
   */
  public void setObservationProbabilities(double[][] observationProbabilities) {
    this.observationProbabilities = observationProbabilities;
  }

  /**
   * Sets the transition probabilities t, where t[i][j] is the probability
   * of going from state i at time t to state j at time t+1
   * @param transitionProbabilities The transitionProbabilities to set
   */
  public void setTransitionProbabilities(double[][] transitionProbabilities) {
    this.transitionProbabilities = transitionProbabilities;
  }

}
