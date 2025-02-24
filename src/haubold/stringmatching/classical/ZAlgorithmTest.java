/*
 * ZAlgorithmTest.java
 *
 * Created on January 15, 2003, 12:05 PM
 */

package haubold.stringmatching.classical;

import haubold.resources.util.DataContainer;
import java.util.*;

/**
 *
 * @author  haubold
 * @version
 */
public class ZAlgorithmTest extends Object {
  static String text = "";
  DataContainer data;
  /** Creates new ZAlgorithmTest */
  public ZAlgorithmTest() {
//        InputStream in = getClass().getResourceAsStream("../../resources/humanADH.seq");
//        InputStreamReader isr = new InputStreamReader(in);
//        BufferedReader br = new BufferedReader(isr);
//        StringBuffer textBuffer = new StringBuffer();
//        try{
//            while(br.ready()){
//                textBuffer.append(br.readLine());
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        text=textBuffer.toString();
    data = new DataContainer();
    text = data.getAdh();
  }

  public static void main(String arg[]) {
    ZAlgorithm zAlgorithm = new ZAlgorithm();
    NaiveAlgorithm naiveAlgorithm = new NaiveAlgorithm();
    StringBuffer textB = new StringBuffer();
    for(int i=0; i<1200000; i++) {
      textB.append('A');
    }
    StringBuffer patternB = new StringBuffer();
    for(int i=0; i<1000; i++) {
      patternB.append('A');
    }
    System.gc();
    long t1 = System.currentTimeMillis();
    ArrayList occurrences = new ArrayList();
    occurrences = zAlgorithm.match(patternB.toString(),textB.toString());
    long t2 = System.currentTimeMillis();
    System.out.println("Matches, Z-Algorithm: " + occurrences.size());
    for(int i=0; i<occurrences.size(); i++) {
//            System.out.println("match: " + occurrences.get(i));
    }
    System.out.println("CPU time Z: " + (t2-t1));
    System.out.println("CharComp Z: " + zAlgorithm.getCharComp());
    System.gc();
    t1 = System.currentTimeMillis();
    occurrences = naiveAlgorithm.match(patternB.toString(),textB.toString());
    t2 = System.currentTimeMillis();
    System.out.println("Matches, Naive Algorithm: " + occurrences.size());
    for(int i=0; i<occurrences.size(); i++) {
//            System.out.println("match: " + occurrences.get(i));
    }
    System.out.println("CPU time Naive: " + (t2-t1));
    System.out.println("CharComp naive: " + naiveAlgorithm.getCharComp());
  }
}