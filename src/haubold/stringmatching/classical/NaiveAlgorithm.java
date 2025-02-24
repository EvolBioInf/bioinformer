/*
 * NaiveAlgorithm.java
 *
 * Created on January 15, 2003, 8:29 PM
 */

package haubold.stringmatching.classical;
import java.util.*;

/**
 * The naive algorithm is also known as the brute-force algorithm. Basically, it is the least efficient way of carrying out
 * exact string matching. For more details c.f. Gusfield (1997). Algorithms on Strings, Trees, and Sequences; Computer Science
 * and Computational Biology. Cambridge University Press; p. 5f.
 * @author  haubold
 * @version
 */
public class NaiveAlgorithm implements MatchAlgorithm {
  private String pattern;
  private String text;
  private int charComp = 0;
  ArrayList<Integer> occurrences;

  /** Creates new NaiveAlgorithm */
  public NaiveAlgorithm() {
    occurrences = new ArrayList<Integer>();
  }


  /** Returns a list of indices that point to the first character of the pattern string in the text string wherever a match
   * has been found.
   */
  public ArrayList match() {
    occurrences = match(pattern,text);
    return occurrences;
  }
  /** Returns a list of indices that point to the first character of the pattern string in the text string wherever a match
   * has been found.
   */
  public ArrayList match(String pattern,String text) {
    int textPosition=0;
    int patternPosition=0;
    char[] charPattern = pattern.toCharArray();
    char[] charText = text.toCharArray();
    int textLength = charText.length;
    int patternLength = charPattern.length;
    occurrences.clear();

    while(textPosition < textLength - patternLength + 1) {
      patternPosition=0;
      charComp++;
      while(patternPosition < patternLength
            && charText[textPosition+patternPosition]
            == charPattern[patternPosition]) {
        patternPosition++;
        charComp++;
      }
      if(patternPosition == patternLength) {
        charComp--;
      }
      if(patternPosition ==  patternLength) {
        occurrences.add(Integer.valueOf(textPosition));
      }
      textPosition++;
    }
    return occurrences;
  }
  /** Sets the pattern string. The pattern is the (short) string that is matched against the (long) text string.
   */
  public void setPattern(String pattern) {
    this.pattern = pattern;
  }
  /** Returns the pattern string. The pattern is the (short) string that is matched against the (long) text string.
   */
  public String getPattern() {
    return pattern;
  }
  /** Sets the text string. The text is the (long) string scanned for matches with the (short) pattern string.
   */
  public void setText(String text) {
    this.text = text;
  }
  /** Returns the text string. The text is the (long) string scanned for matches with the (short) pattern string.
   */
  public String getText() {
    return text;
  }

  public int getCharComp() {
    return charComp;
  }
}
