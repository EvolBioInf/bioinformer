package haubold.hmm.algorithm;

/**
 * @author Bernhard Haubold
 * Date: Oct 21, 2003; time: 5:50:29 PM.
 *
 * Description: Backward algorithm for calculating the probability of observing
 * a sequence of emitted states given a hidden Markov model.
 */
public class Backward {

  private double[][] backwardProbabilities;

  public Backward() {

  }


  public double[][] computeBackward(HiddenMarkovModel hmm, int[] observedStates, double[] scale) {
    int states = hmm.getNumStates();
    int length = observedStates.length;
    backwardProbabilities = new double[states][length];
    double[][] observationProbabilities = hmm.getObservationProbabilities();
    double[][] transitionProbabilities = hmm.getTransitionProbabilities();
    int i,j,k;
    double s;

    // Initialization
    for(i=0; i<states; i++) {
//			System.out.println("Backward: " +
//				"scale[states-1]: " +
//				scale[length-1]);
      backwardProbabilities[i][length-1]=1.0/scale[length-1];
    }

    // Fill in rest of observationProbabilities
    for(i=length-2; i>=0; i--) {
      for(j=0; j<states; j++) {
        s = 0.0;
        for(k=0; k<states; k++) {
          s += backwardProbabilities[k][i+1]
               * transitionProbabilities[j][k]
               * observationProbabilities[k][observedStates[i+1]];
        }
        backwardProbabilities[j][i] = s;
      }
      for(k=0; k<states; k++) {
        backwardProbabilities[k][i] /= scale[i];
      }
    }

    return backwardProbabilities;
  }
  /**
   * @return
   */
  public double[][] getBackwardProbabilities() {
    return backwardProbabilities;
  }

}
