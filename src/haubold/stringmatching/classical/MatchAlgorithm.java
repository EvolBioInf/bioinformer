/*
 * MatchAlgorithm.java
 *
 * Created on January 15, 2003, 8:21 PM
 */

package haubold.stringmatching.classical;
import java.util.*;

/**
 * Interface for match algorithms.
 * @author  haubold
 * @version
 */
public interface MatchAlgorithm {
  /**
  * Returns a list of indices that point to the first character of the pattern string in the text string wherever a match
  * has been found.
  */
  public ArrayList match();

  /**
  * Returns a list of indices that point to the first character of the pattern string in the text string wherever a match
  * has been found.
  */
  public ArrayList match(String pattern, String text);

  /**
  * Sets the pattern string. The pattern is the (short) string that is matched against the (long) text string.
  */
  public void setPattern(String pattern);

  /**
  * Returns the pattern string. The pattern is the (short) string that is matched against the (long) text string.
  */
  public String getPattern();

  /**
  * Sets the text string. The text is the (long) string scanned for matches with the (short) pattern string.
  */
  public void setText(String text);

  /**
  * Returns the text string. The text is the (long) string scanned for matches with the (short) pattern string.
  */
  public String getText();
}
