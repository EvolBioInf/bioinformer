package haubold.resources.util;

/**
 * Description: Methods for manipulating strings.
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Apr 11, 2004; time: 2:21:36 PM.
 */
public class StringUtils {
  StringBuffer stringBuffer;
  public StringUtils() {
    stringBuffer = new StringBuffer();
  }

  public String wrapString(String string, int lineLength) {
    int i=0;
    stringBuffer.delete(0,stringBuffer.length());
    while(i<string.length()) {
      stringBuffer.append(string.subSequence(i,Math.min(i+lineLength,string.length())));
      if(i+lineLength < string.length()) {
        stringBuffer.append("\n");
      }
      if(i+lineLength<string.length()) {
        i += lineLength;
      } else {
        i = string.length();
      }
    }

    return stringBuffer.toString();
  }
}
