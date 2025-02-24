package haubold.wrightfisher;

import java.awt.Color;
import java.util.Random;

/**
 * Description:
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Oct 6, 2005; time: 2:33:39 PM.
 */
public class ColorUtil {

  Random ran;

  public ColorUtil() {
    ran = new Random();
  }

  public Color getRandomColor() {
    return new Color(ran.nextFloat(),ran.nextFloat(),ran.nextFloat());
  }

}
