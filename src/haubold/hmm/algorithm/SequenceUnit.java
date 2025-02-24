package haubold.hmm.algorithm;

/**
 * @author Bernhard Haubold
 * Date: Oct 21, 2003; time: 3:38:27 PM.
 *
 * Description: Class to transport the hidden & observed
 * states in a HMM sequence.
 */
public class SequenceUnit {
  private int hiddenState;
  private int observedState;
  private String nucleotide;

  public SequenceUnit() {
  }

  public SequenceUnit(int hiddenState) {
    this.hiddenState = hiddenState;
  }

  public SequenceUnit(int hiddenState, int observedState) {
    this.hiddenState = hiddenState;
    this.observedState = observedState;
  }

  /**
   * Returns the hiddenState.
   * @return int
   */
  public int getHiddenState() {
    return hiddenState;
  }

  /**
   * Returns the observedState.
   * @return int
   */
  public int getObservedState() {
    return observedState;
  }

  /**
   * Sets the hiddenState.
   * @param hiddenState The hiddenState to set
   */
  public void setHiddenState(int hiddenState) {
    this.hiddenState = hiddenState;
  }

  /**
   * Sets the observedState.
   * @param observedState The observedState to set
   */
  public void setObservedState(int observedState) {
    this.observedState = observedState;
  }

  /**
   * @return Returns the nucleotide.
   */
  public String getNucleotide() {
    return nucleotide;
  }
  /**
   * @param nucleotide The nucleotide to set.
   */
  public void setNucleotide(String nucleotide) {
    this.nucleotide = nucleotide;
  }
}
