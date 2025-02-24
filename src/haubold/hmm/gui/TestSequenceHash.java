package haubold.hmm.gui;

/**
 * @author haubold
 * Date: Mar 7, 2003; time: 3:31:59 PM.
 *
 * Description:
 */

import haubold.hashtable.SequenceHash;
public class TestSequenceHash {

  public static void main(String[] arg) {
    String sequence = "AGAGAACCGATAG";
    SequenceHash hash = new SequenceHash();
    hash.hashSequence(sequence.toCharArray(),3);
  }

}
