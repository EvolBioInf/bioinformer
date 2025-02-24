package haubold.hmm.algorithm;

/**
 * @author haubold
 * Date: Oct 31, 2003; time: 10:53:01 PM.
 *
 * Description: Optimize parameters of hidden Markov model given an
 * initial model and a sequence of observations.
 * Reference: Rabiner (1998). A tutorial on hidden Markov models and
 * selected applications in speech recognition. Proceedings of the IEEE,
 * 77:257-286.
 */
public class BaumWelch {

  private double tol = 0.00001;
  private HiddenMarkovModel hmm;
  private int[] obsSeq;
  private int maxIt;
  private double logP;
  private Forward forward;
  private Backward backward;
  private XiGamma xiGamma;
  private Reestimation reestimation;

  /**
   * No args constructor. Need to set hmm, obsSeq, and maxIt.
   */
  public BaumWelch() {
  }

  /**
   * Constructor
   * @param hmm Hidden Markov Model
   * @param obsSeq Sequence of observed states
   * @param maxIt Maximum number of interations
   */
  public BaumWelch(HiddenMarkovModel initialHmm, int[] obsSeq, int maxIt) {
    this.obsSeq = obsSeq;
    this.maxIt = maxIt;
    initializeHmm(initialHmm);
  }

  private HiddenMarkovModel initializeHmm(HiddenMarkovModel initialHmm) {
    hmm = new HiddenMarkovModel();
    hmm.setInitialProbabilities(initialHmm.getInitialProbabilities());
    hmm.setTransitionProbabilities(initialHmm.getTransitionProbabilities());
    hmm.setObservationProbabilities(
      initialHmm.getObservationProbabilities());
    forward = new Forward();
    backward = new Backward();
    xiGamma = new XiGamma();
    reestimation = new Reestimation();
    return hmm;
  }

  public HiddenMarkovModel newHmm() {
    int it = 0;
    forward.computeForward(hmm, obsSeq);
    logP = forward.getLogP();
    backward.computeBackward(hmm, obsSeq, forward.getScale());
    xiGamma.computeXiGamma(
      forward.getForwardProbabilities(),
      backward.getBackwardProbabilities(),
      hmm,
      obsSeq);

    while (it < maxIt) {
      hmm = reestimation.reestimateHMM(hmm, obsSeq, xiGamma);
      forward.computeForward(hmm, obsSeq);
      if (Math.abs(logP - forward.getLogP()) < tol) {
        break;
      }
      it++;
      logP = forward.getLogP();
      backward.computeBackward(
        reestimation.getHmm(),
        obsSeq,
        forward.getScale());
      xiGamma.computeXiGamma(
        forward.getForwardProbabilities(),
        backward.getBackwardProbabilities(),
        reestimation.getHmm(),
        obsSeq);
    }
    return reestimation.getHmm();
  }

  /**
   * Returns the "tolerance", i.e. the difference between old and
   * new log-likelihood tolerated for the algorithm to finish.
   * @return
   */
  public double getTol() {
    return tol;
  }

  /**
   * Sets the "tolerance", i.e. the difference between old and
   * new log-likelihood tolerated for the algorithm to finish.
   * @param d
   */
  public void setTol(double d) {
    tol = d;
  }

  /**
   * @return
   */
  public HiddenMarkovModel getHmm() {
    return hmm;
  }

  /**
   * @param model
   */
  public void setHmm(HiddenMarkovModel initialModel) {
    hmm = initializeHmm(initialModel);
  }

  /**
   * @param is
   */
  public void setObsSeq(int[] is) {
    this.obsSeq = is;
  }

  /**
   * @param i
   */
  public void setMaxIt(int i) {
    this.maxIt = i;
  }

  /**
   * @return
   */
  public double getLogP() {
    return logP;
  }

}
