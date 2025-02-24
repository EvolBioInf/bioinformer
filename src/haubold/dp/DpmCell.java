package haubold.dp;
/**
 * A <code>DpmCell</code> object provides the smalles unit of
 * a dynamic programming matrix.
 *
 * @version 0.0 July 12, 2002
 * @author Bernhard Haubold
 */
public class DpmCell {
  double horizontal;
  double score;
  double vertical;
  double numCooptimal;
  boolean backHorizontal;
  boolean backVertical;
  boolean backDiagonal;
  boolean optimal;
  DpmCell back;
  int i,j;

  DpmCell() {
    backHorizontal = false;
    backVertical = false;
    backDiagonal = false;
    optimal = false;
    numCooptimal = 0.0;
  }

  DpmCell(int i, int j) {
    this.i = i;
    this.j = j;
  }
  /**
   * Returns the back.
   * @return DpmCell
   */
  public DpmCell getBack() {
    return back;
  }

  /**
   * Returns the i.
   * @return int
   */
  public int getI() {
    return i;
  }

  /**
   * Returns the j.
   * @return int
   */
  public int getJ() {
    return j;
  }

  /**
   * Sets the back.
   * @param back The back to set
   */
  public void setBack(DpmCell back) {
    this.back = back;
  }

  /**
   * Sets the i.
   * @param i The i to set
   */
  public void setI(int i) {
    this.i = i;
  }

  /**
   * Sets the j.
   * @param j The j to set
   */
  public void setJ(int j) {
    this.j = j;
  }

}
