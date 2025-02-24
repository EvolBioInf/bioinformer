package haubold.hmm.algorithm;
import java.util.*;

/**
 * @author Bernhard Haubold
 * Date: Oct 21, 2003; time: 3:43:39 PM.
 *
 * Description: Generate a sequence of hidden and observed states
 * given a hidden Markov model.
 */
public class HmmSequence {

  private HiddenMarkovModel hmm;
  private Random ran;

  public HmmSequence() {
    ran = new Random();
  }

  public HmmSequence(HiddenMarkovModel hiddenMarkovModel) {
    this.hmm = hiddenMarkovModel;
    ran = new Random();
  }
  /**
   * Generate a hidden Markov sequence of a given length.
   * @return SequenceUnit[] The array of <code>SequenceUnit</code>s where each
   * element consists of a hidden and an observed state.
   * @param length The length of the hidden Markov series.
   */
  public SequenceUnit[] generateHmmSequence(int length) {
    int i;
    SequenceUnit s[] = new SequenceUnit[length];
    s[0] = getInitialState();
    s[0] = generateSymbol(s[0]);
    s[0] = generateNucleotide(s[0]);
    for(i=1; i<length; i++) {
      s[i] = generateState(s[i-1]);
      s[i] = generateSymbol(s[i]);
      s[i] = generateNucleotide(s[i]);
    }
    return s;
  }
  /**
   * Generate the initial state of a hidden Markov sequence
   */
  private SequenceUnit getInitialState() {
    double accum = 0.0;
    int i;
    for(i=0; i<hmm.getNumStates(); i++) {
      accum += hmm.getInitialProbabilities()[i];
      if(ran.nextDouble() < accum) {
        return new SequenceUnit(i);
      }
    }
    return new SequenceUnit(i);
  }
  /**
   * Given a sequenceUnit with an assigned hidden State,
   * generate its observed state.
   */
  private SequenceUnit generateSymbol(SequenceUnit s) {
    double accum = 0.0;
    int i;
    for(i=0; i<hmm.getNumObservationSymbols(); i++) {
      accum += hmm.getObservationProbabilities()[s.getHiddenState()][i];
      if(ran.nextDouble() < accum) {
        s.setObservedState(i);
        return s;
      }
    }
    return s;
  }
  /**
   * Given a sequenceUnit with an assigned observed State,
   * generate its nucleotide state.
   */
  private SequenceUnit generateNucleotide(SequenceUnit s) {
    double accum = 0.0;
    int i;
    for(i=0; i<hmm.getNumObservationSymbols(); i++) {
      accum += hmm.getObservationProbabilities()[s.getHiddenState()][i];
      if(s.getObservedState() == 0) {
        if(ran.nextDouble() < 0.5) {
          s.setNucleotide("A");
        } else {
          s.setNucleotide("T");
        }
      } else {
        if(ran.nextDouble() < 0.5) {
          s.setNucleotide("C");
        } else {
          s.setNucleotide("G");
        }
      }
    }
    return s;
  }
  /**
   * Given a sequenceUnit with an assigned hidden State,
   * generate the next sequenceUnit with its assigned State.
   */
  private SequenceUnit generateState(SequenceUnit s) {
    double accum = 0.0;
    int i;
    for(i=0; i<hmm.getNumStates(); i++) {
      accum += hmm.getTransitionProbabilities()[s.getHiddenState()][i];
      if(ran.nextDouble() < accum) {
        return new SequenceUnit(i);
      }
    }
    return new SequenceUnit(i);
  }
  /**
   * @param model
   */
  public void setHmm(HiddenMarkovModel model) {
    hmm = model;
  }

}
