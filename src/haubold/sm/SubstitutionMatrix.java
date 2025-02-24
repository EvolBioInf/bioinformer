package haubold.sm;

/**
 * Description: Data transport object for protein substitution
 * matrices.
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Apr 24, 2004; time: 4:03:58 PM.
 */
public class SubstitutionMatrix {
  int[][] values;
  String[] aminoAcids;
  /**
   * @return
   */
  public String[] getAminoAcids() {
    return aminoAcids;
  }

  /**
   * @return
   */
  public int[][] getValues() {
    return values;
  }

  /**
   * @param cs
   */
  public void setAminoAcids(String[] as) {
    aminoAcids = as;
  }

  /**
   * @param is
   */
  public void setValues(int[][] is) {
    values = is;
  }

}
