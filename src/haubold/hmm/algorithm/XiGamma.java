package haubold.hmm.algorithm;

/**
 * @author haubold
 * Date: Oct 30, 2003; time: 10:54:48 PM.
 *
 * Description: Calculate Xi- and gamma-values accoring to Rabiner (1989).
 * Reference: L. R. Rabiner (1989). A tutorial on hidden Markov models and
 * selected applications in speech recognition. Proceedings of the IEEE,
 * 77: 257-286.
 */
public class XiGamma {
  private double[][][] xi;
  private double[][] gamma;

  /**
   * No args constructor. Need to set forwardProbabilites, backwardProbabilities,
   * hmm, and observedStates.
   *
   */
  public XiGamma() {
  }

  public void computeXiGamma(double[][] forwardProbabilities,
                             double[][] backwardProbabilities,
                             HiddenMarkovModel hmm,
                             int[] observedStates) {
    xi = computeXi(forwardProbabilities,backwardProbabilities,hmm,observedStates);
    gamma = computeGamma(forwardProbabilities,backwardProbabilities,hmm,observedStates);
  }


  /**
   * Compute xi[i][j][k], the probability of being in state i
   * at time k and state j at time k+1 given the hidden Markov model
   * and the observation sequence (c.f. Rabiner (1989) p. 264).
   * @return xi
   */
  private double[][][] computeXi(double[][] forwardProbabilities,
                                 double[][] backwardProbabilities,
                                 HiddenMarkovModel hmm,
                                 int[] observedStates) {

    int length = observedStates.length;
    int n = hmm.getNumStates();
    int i,j,k;
    double denominator;
    double[][][] xi = new double[n][n][length];

    for(i=0; i<length-1; i++) {
      denominator = 0.0;
      for(j=0; j<n; j++) {
        for(k=0; k<n; k++) {
//					System.out.println("XiGamma - forwardProbabilities[j][i]: " +
//						forwardProbabilities[j][i]);
//					System.out.println("hmm.getTransitionProbabilities()[j][k]" +
//						hmm.getTransitionProbabilities()[j][k]);
//					System.out.println("hmm.getObservationProbabilities()[k][observedStates[i+1]]" +
//						hmm.getObservationProbabilities()[k][observedStates[i+1]]);
//					System.out.println("backwardProbabilities[k][i+1]" +
//						backwardProbabilities[k][i+1]);
          xi[j][k][i] = forwardProbabilities[j][i]
                        * hmm.getTransitionProbabilities()[j][k]
                        * hmm.getObservationProbabilities()[k][observedStates[i+1]]
                        * backwardProbabilities[k][i+1];
          denominator += xi[j][k][i];
        }
      }
      for(j=0; j<n; j++) {
        for(k=0; k<n; k++) {
          xi[j][k][i] /= denominator;
//					System.out.println("XiGamma - xi[j][k][i]: " + xi[j][k][i]);
        }
      }
    }
    return xi;
  }

  private double[][] computeGamma(double[][] forwardProbabilities,
                                  double[][] backwardProbabilities,
                                  HiddenMarkovModel hmm,
                                  int[] observedStates) {

    int length = observedStates.length;
//		System.out.println("XiGamma - length: " + length);
    int n = hmm.getNumStates();
    int i,j;
    double[][] gamma = new double[n][length];
    double denominator;
    for(i=0; i<length; i++) {
      denominator=0.0;
      for(j=0; j<n; j++) {
        gamma[j][i] = forwardProbabilities[j][i] * backwardProbabilities[j][i];
        denominator += gamma[j][i];
      }
      for(j=0; j<n; j++) {
        gamma[j][i] /= denominator;
//				System.out.println("XiGamma - gamma[j][i]: " + gamma[j][i]);
      }
    }
    return gamma;
  }
  /**
   * @return
   */
  public double[][] getGamma() {
    return gamma;
  }

  /**
   * @return
   */
  public double[][][] getXi() {
    return xi;
  }

}
