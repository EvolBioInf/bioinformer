package haubold.hashtable;

/**
 * @author Bernhard Haubold
 * Date: Mar 7, 2003; time: 12:11:55 PM.
 *
 * Description:
 */
import java.util.*;

public class SequenceHash extends HashMap<String,ArrayList<Integer>> {
  private static final long serialVersionUID = 1L;
  char[] sequence;
  StringBuffer key;
  int wordLength;
  ArrayList<Integer> list;

  public void hashSequence(char[] sequence, int wordLength) {
    this.clear();
    key = new StringBuffer();
    int i, j;
    for(i=0; i<sequence.length-wordLength+1; i++) {
      key.delete(0,key.length());
      for(j=0; j<wordLength; j++) {
        key.append(sequence[i+j]);
      }
      if(this.containsKey(key.toString())) {
        list = this.get(key.toString());
        list.add(Integer.valueOf(i));
      } else {
        list = new ArrayList<Integer>();
        list.add(Integer.valueOf(i));
        this.put(key.toString(),list);
      }
    }

  }
}
