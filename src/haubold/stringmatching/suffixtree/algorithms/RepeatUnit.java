package haubold.stringmatching.suffixtree.algorithms;

/**
 * @author Bernhard Haubold
 * Date: Apr 8, 2003; time: 7:55:18 AM.
 *
 * Description: Contains the start position of a repeat
 * as well as an identifier of its string of origin.
 */
public class RepeatUnit {
  private int position;
  private int stringId;

  public RepeatUnit() {}

  public RepeatUnit(int position, int stringId) {
    this.position = position;
    this.stringId = stringId;
  }

  /**
   * Returns the position.
   * @return int
   */
  public int getPosition() {
    return position;
  }

  /**
   * Returns the stringId.
   * @return int
   */
  public int getStringId() {
    return stringId;
  }

  /**
   * Sets the position.
   * @param position The position to set
   */
  public void setPosition(int position) {
    this.position = position;
  }

  /**
   * Sets the stringId.
   * @param stringId The stringId to set
   */
  public void setStringId(int stringId) {
    this.stringId = stringId;
  }

}