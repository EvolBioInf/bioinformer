package haubold.hmm.algorithm;

/**
 * @author Bernhard Haubold
 * Date: Oct 20, 2003; time: 6:29:33 AM.
 *
 * Description: Forward algorithm for calculating the probability of
 * observing a sequence given a hidden Markov model. Probabilities are
 * scaled so that the probabilities of observing the various symbols always
 * sums to one.
 */
public class Forward {
  private double[][] forwardProbabilities;
  private double[] scale;
  private double logP;

  public Forward() {

  }

  /**
   * Returns the logLikelihood of the data given the model
   */
  public void computeForward(HiddenMarkovModel hmm, int[] observedStates) {
    int states = hmm.getNumStates();
    int length = observedStates.length;
    forwardProbabilities = new double[states][length];
    scale = new double[length];
    double[][] observationProbabilities = hmm.getObservationProbabilities();
    double[][] transitionProbabilities = hmm.getTransitionProbabilities();
    int i,j,k;
    double s;

    // Initialize scale to zero
    for(i=0; i<length; i++) {
      scale[i] = 0.0;
    }

    // Initialize first column of forwardProbabilities
    for(i=0; i<states; i++) {
      forwardProbabilities[i][0] = hmm.getInitialProbabilities()[i]
                                   * transitionProbabilities[i][observedStates[0]];
      scale[0] += forwardProbabilities[i][0];
    }
    for(i=1; i<states; i++) {
      forwardProbabilities[i][0] /= scale[0];
    }

    // Fill in rest of forwardProbabilities
    for(i=1; i<length; i++) {
      for(j=0; j<states; j++) {
        s = 0.0;
        for(k=0; k<states; k++) {
          s += forwardProbabilities[k][i-1]
               * transitionProbabilities[k][j];
        }
        forwardProbabilities[j][i] = s
                                     * observationProbabilities[j][observedStates[i]];
        scale[i] += forwardProbabilities[j][i];
      }
      for(j=0; j<states; j++) {
        forwardProbabilities[j][i] /= scale[i];
      }
    }
    // Termination
    logP = 0.0;
    for(i=0; i<length; i++) {
      logP += Math.log(scale[i]);
    }
  }

  /**
   * Returns the dynamic programming matrix holding the forward probabilities.
   * @return double[][]
   */
  public double[][] getForwardProbabilities() {
    return forwardProbabilities;
  }

  /**
   * Returns the scale.
   * @return double[]
   */
  public double[] getScale() {
    ;
    return scale;
  }

  /**
   * @return
   */
  public double getLogP() {
    return logP;
  }

}
