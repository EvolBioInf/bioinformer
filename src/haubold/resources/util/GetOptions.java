package haubold.resources.util;
import java.util.*;
/**
 * Description: Class for parsing the commandline for options.
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Dec 14, 2003; time: 7:51:29 PM.
 *
 */
public class GetOptions extends HashMap<String,String> {
  private static final long serialVersionUID = 1L;

  public GetOptions(String[] args) {
    this.clear();
    int i=0;
    while (i < args.length) {
      if (args[i].startsWith("-")) {
        if(i+1 > args.length-1) {
          this.put(args[i].substring(1),null);
          i++;
        } else if(args[i+1].startsWith("-")) {
          this.put(args[i].substring(1),null);
          i++;
        } else {
          this.put(args[i].substring(1),args[i+1]);
          i += 2;
        }
      }
    }
  }

}
