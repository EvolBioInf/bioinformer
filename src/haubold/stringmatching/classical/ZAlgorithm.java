/*
 * ZAlgorithm.java
 *
 * Created on January 15, 2003, 10:56 AM
 */

package haubold.stringmatching.classical;
import java.util.*;

/**
 * The Z-algorithm is the simplest linear-time exact matching algorithm known.
 * For a description of the algorityhm see Gusfield (1997). Algorithms on Strings, Trees, and Sequences; Computer Science
 * and Computational Biology. Cambridge University Press; p. 7ff.
 * @author  haubold
 * @version
 */
public class ZAlgorithm implements MatchAlgorithm {
  private String pattern;
  private String text;
  private String sentinel = "\000";
  private int[] z;
  private int k,r,l,q;
  private int patternLength, completeLength;
  private char[] charComplete, charPattern;
  int charComp=0;
  ArrayList<Integer> occurrences;

  /** Creates new ZAlgorithm */
  public ZAlgorithm() {
    occurrences = new ArrayList<Integer>();
  }

  public ArrayList match() {
    ArrayList occurrences = match(pattern,text);
    return occurrences;
  }

  public void preprocess(String pattern, String text) {
    String string = pattern + sentinel + text;
    charComplete = string.toCharArray();
    charPattern = pattern.toCharArray();
    completeLength = charComplete.length;
    patternLength = charPattern.length;
    k=1;
    r=0;
    l=0;
    q=0;
    z = new int[patternLength];
    // initialize
    while(k+q < patternLength && charComplete[q] == charPattern[k+q]) {
      q++;
    }
    if(z.length > 1) {
      z[k]=q;
      if(z[k]>0) {
        l=k;
        r=k+z[k]-1;
      }
    }
    // calculate Z-values for pattern
    k++;
    while(k < patternLength) {
      z[k] = calculateZ(z,k,r,l,charComplete);
      k++;
    }
  }

  public  ArrayList match(String pattern, String text) {
    occurrences.clear();
    if(z==null) {
      preprocess(pattern, text);
    }
    // calculate Z-values for remaining string
    //System.out.println("Z-Algorithm - z.length: " + z.length);
    while(k < completeLength-patternLength+1) {
      if(calculateZ(z,k,r,l,charComplete) == patternLength) {
        occurrences.add(Integer.valueOf(k-patternLength-1));
      }
      k++;
    }
    z = null;
    return occurrences;
  }

  private int calculateZ(int[] z, int k, int r, int l, char[] charA) {
    int kDash=0;
    int q=0;
    int i=0;
    int beta=0;
    int currentZ=0;
    if(k>r) {
      q=0;
      if(k+q<charA.length) {
        charComp++;
      }
      while(k+q < charA.length && charA[q] == charA[k+q]) {
        q++;
        charComp++;
      }
      if(k+q == charA.length) {
        charComp--;
      }
      currentZ=q;
      if(currentZ>0) {
        l=k;
        r=k+currentZ-1;
      }
    } else {
      kDash = k-l;
      beta = r-k;
      if(z[kDash] < beta) {
        currentZ = z[kDash];
      } else {
        q = r+1;
        i = beta;
        if(q<charA.length) {
          charComp++;
        }
        while(q < charA.length && charA[q] == charA[i]) {
          i++;
          q++;
          charComp++;
        }
        if(q == charA.length) {
          charComp--;
        }
        l=k;
        r=q-1;
        currentZ=q-k-1;
      }
    }
    return currentZ;
  }

  /**
   * Sets the pattern string. The pattern is the (short) string that is matched against the (long) text string.
   */
  public void setPattern(String pattern) {
    this.pattern=pattern;
  }

  /**
   * Returns the pattern string. The pattern is the (short) string that is matched against the (long) text string.
   */
  public String getPattern() {
    return pattern;
  }

  /**
   * Sets the text string. The text is the (long) string scanned for matches with the (short) pattern string.
   */
  public void setText(String text) {
    this.text=text;
  }

  /**
   * Returns the text string. The text is the (long) string scanned for matches with the (short) pattern string.
   */
  public String getText() {
    return text;
  }

  /**
   * Sets the sentinel character. The sentinel character separates the pattern & text strings. It should not occur anywhere in the pattern. Its default
   * value is "$".
   */
  public void setSentinel(String sentinel) {
    this.sentinel = sentinel;
  }

  /**
   * Returns the sentinel character. The sentinel character separates the pattern & text strings. It should not occur anywhere in the pattern. Its default
   * value is "$".
   */
  public String getSentinel() {
    return sentinel;
  }

  /**
   * Returns the array of Z-values calculated for the pattern currently used. This is intended for demonstration- and
   * debugging purposes. No setter is provided, as the <code>match</code> method starts by computing the Z-values from
   * scratch.
   */
  public int[] getZ() {
    return z;
  }

  public int getCharComp() {
    return charComp;
  }

}
