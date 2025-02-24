package haubold.hmm.algorithm;

/**
 * @author haubold
 * Date: Oct 30, 2003; time: 11:50:47 PM.
 *
 * Description: Reestimate hidden Markov model.
 * Reference: Rabiner (1989). A tutorial on hidden Markov models
 * and selected applications in speech recognition. Proceedings
 * of the IEEE, 77:257-286.
 */
public class Reestimation {

  private HiddenMarkovModel hmm;

  public Reestimation() {

  }

  /**
   * NB: Before passing xiGamma, the method computeXiGamma needs
   * to have been executed.
   * @param hmm
   * @param observedStates
   * @param xiGamma
   * @return
   */
  public HiddenMarkovModel reestimateHMM(HiddenMarkovModel hmm,
                                         int[] observedStates,
                                         XiGamma xiGamma) {
    this.hmm = hmm;
    double[][][] xi = xiGamma.getXi();
    double[][] gamma = xiGamma.getGamma();
    hmm = reestimateInitialProb(hmm,gamma);
    hmm = reestimateTransitionProb(hmm,xi,gamma);
    hmm = reestimateEmmissionProb(hmm,gamma,observedStates);
    return hmm;
  }

  private HiddenMarkovModel reestimateInitialProb(HiddenMarkovModel hmm, double[][] gamma) {
    int i;
    double[] ip = hmm.getInitialProbabilities();
    for(i=0; i<hmm.getNumStates(); i++) {
      ip[i]=gamma[i][0];
    }
    hmm.setInitialProbabilities(ip);
    return hmm;
  }

  private HiddenMarkovModel reestimateTransitionProb(HiddenMarkovModel hmm,
      double[][][] xi,
      double[][] gamma) {
    int i,j,k;
    int n = hmm.getNumStates();
    double[][] tp = hmm.getTransitionProbabilities();
    int len = xi[0][0].length;
    double denominator;
    double numerator;
    for(i=0; i<n; i++) {
      for(j=0; j<n; j++) {
        numerator = 0.0;
        denominator = 0.0;
        for(k=0; k<len-1; k++) {
          numerator += xi[i][j][k];
          denominator += gamma[i][k];
        }
        tp[i][j]=numerator/denominator;
      }
    }
    hmm.setTransitionProbabilities(tp);
    return hmm;
  }

  private HiddenMarkovModel reestimateEmmissionProb(HiddenMarkovModel hmm,
      double[][] gamma,
      int[] obsStat) {
    int i,j,k;
    int m = hmm.getNumObservationSymbols();
    int n = hmm.getNumStates();
    double[][] op = hmm.getObservationProbabilities();
    int len = gamma[0].length;
    double denominator;
    double numerator;
    for(i=0; i<m; i++) {
      for(j=0; j<n; j++) {
        numerator = 0.0;
        denominator = 0.0;
        for(k=0; k<len; k++) {
          if(obsStat[k] == i) {
            numerator += gamma[j][k];
          }
          denominator += gamma[j][k];
        }
        op[j][i] = numerator/denominator;
      }
    }
    hmm.setObservationProbabilities(op);
    return hmm;
  }
  /**
   * @return
   */
  public HiddenMarkovModel getHmm() {
    return hmm;
  }

}
