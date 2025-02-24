package haubold.hmm.algorithm;

/**
 * @author Bernhard Haubold
 * Date: Oct 21, 2003; time: 4:28:31 PM.
 *
 * Description: Find ML sequence of states given an array of observed
 * states and a hidden Markov model.
 */
public class Viterbi {
  private SequenceUnit[] sequence;
  private HiddenMarkovModel hmm;
  private double logP;

  public Viterbi() {

  }

  public Viterbi(SequenceUnit[] sequence, HiddenMarkovModel hmm) {
    this.sequence = sequence;
    this.hmm = hmm;
  }

  /**
   * Generate ML sequence of hidden states.
   */
  public SequenceUnit[] getViterbiSequence() {
    int length = sequence.length;
    int[] s = new int[length];
    double[][] dpm = new double[hmm.getNumStates()][length];
    hmm = generateLogModel(hmm);
    int i,j,k;
    double max;
    double v;

    // Initialize first column of dynamic programming matrix.
    for(i=0; i< hmm.getNumStates(); i++) {
      dpm[i][0] = hmm.getInitialProbabilities()[i]
                  + hmm.getObservationProbabilities()[i][sequence[0].getObservedState()];
    }
    // Compute first hidden state
    v = dpm[0][0];
    s[0] = 0;
    for(i=1; i<hmm.getNumStates(); i++) {
      if(dpm[i][0] > v) {
        s[0] = i;
        v = dpm[i][0];
      }
    }
    // Fill in rest of dmp.
    for(i=1; i<length; i++) {
      max = -Double.MAX_VALUE;
      for(j=0; j<hmm.getNumStates(); j++) {
        dpm[j][i] = -Double.MAX_VALUE;
        for(k=0; k<hmm.getNumStates(); k++) {
          v = dpm[k][i-1] + hmm.getTransitionProbabilities()[k][j];
          if(v > dpm[j][i]) {
            dpm[j][i] = v;
          }
        }
        dpm[j][i] += hmm.getObservationProbabilities()[j][sequence[i].getObservedState()];
        if(dpm[j][i] > max) {
          max = dpm[j][i];
          s[i] = j;
        }
      }
    }
    // Termination.
    max = -Double.MAX_VALUE;
    for(i=0; i<hmm.getNumStates(); i++) {
      if(dpm[i][length-1] > max) {
        max = dpm[i][length-1];
        logP = max;
        s[length-1] = i;
      }
    }
    // Package the result into array of SequenceUnits
    for(i=0; i<s.length; i++) {
      sequence[i].setHiddenState(s[i]);
    }
    return sequence;
  }

  /**
   * Generate log-version of hidden Markov model.
   */
  private HiddenMarkovModel generateLogModel(HiddenMarkovModel hmm) {
    HiddenMarkovModel hmm2 = new HiddenMarkovModel();
    double t[][] = new double[hmm.getNumStates()][hmm.getNumObservationSymbols()];
    double o[][] = new double[hmm.getNumStates()][hmm.getNumObservationSymbols()];
    double s[] = new double[hmm.getNumStates()];
    int i,j;

    for(i=0; i<hmm.getNumStates(); i++) {
      for(j=0; j<hmm.getNumStates(); j++) {
        t[i][j] = Math.log(hmm.getTransitionProbabilities()[i][j]);
      }
    }
    hmm2.setTransitionProbabilities(t);
    for(i=0; i<hmm.getNumStates(); i++) {
      for(j=0; j<hmm.getNumObservationSymbols(); j++) {
        o[i][j] = Math.log(hmm.getObservationProbabilities()[i][j]);
      }
    }
    hmm2.setObservationProbabilities(o);
    for(i=0; i<hmm.getNumStates(); i++) {
      s[i] = Math.log(hmm.getInitialProbabilities()[i]);
    }
    hmm2.setInitialProbabilities(s);
    return hmm2;
  }
  /**
   * Returns the hmm.
   * @return HiddenMarkovModel
   */
  public HiddenMarkovModel getHmm() {
    return hmm;
  }

  /**
   * Sets the hmm.
   * @param hmm The hmm to set
   */
  public void setHmm(HiddenMarkovModel hmm) {
    this.hmm = hmm;
  }

  /**
   * Returns the log probability of the inferred sequence of hidden states.
   * @return double
   */
  public double getLogP() {
    return logP;
  }

  /**
   * @param units
   */
  public void setSequence(SequenceUnit[] units) {
    sequence = units;
  }

}
