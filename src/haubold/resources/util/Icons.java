package haubold.resources.util;

import java.net.*;
import javax.swing.*;

/**
 * Description: Provide standard icons.
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: May 31, 2004; time: 4:27:52 PM.
 */
public class Icons {
  private static String imageName;
  private static URL iconURL;
  @SuppressWarnings("unused")
  private static ImageIcon imageIcon;

  public static ImageIcon getTreeIcon() {
//		imageName = "haubold/resources/icons/firefox.png";
    imageName = "haubold/resources/icons/tree.gif";
    iconURL = ClassLoader.getSystemResource(imageName);
    return imageIcon = new ImageIcon(iconURL);
  }

  public static ImageIcon getHelpIcon() {
    imageName = "haubold/resources/icons/questionMark.gif";
    iconURL = ClassLoader.getSystemResource(imageName);
    return imageIcon = new ImageIcon(iconURL);
  }

  public static ImageIcon get1rightArrow() {
    imageName = "haubold/resources/icons/1rightarrow.png";
    iconURL = ClassLoader.getSystemResource(imageName);
    return imageIcon = new ImageIcon(iconURL);
  }

  public static ImageIcon get2rightArrow() {
    imageName = "haubold/resources/icons/2rightarrow.png";
    iconURL = ClassLoader.getSystemResource(imageName);
    return imageIcon = new ImageIcon(iconURL);
  }

  public static ImageIcon getWrightFisher() {
    imageName = "haubold/resources/icons/wrightFisherSmall.png";
    iconURL = ClassLoader.getSystemResource(imageName);
    return imageIcon = new ImageIcon(iconURL);
  }


  public static ImageIcon getUndo() {
    imageName = "haubold/resources/icons/undo.png";
    iconURL = ClassLoader.getSystemResource(imageName);
    return imageIcon = new ImageIcon(iconURL);
  }

  public static ImageIcon getStop() {
    imageName = "haubold/resources/icons/stop.png";
    iconURL = ClassLoader.getSystemResource(imageName);
    return imageIcon = new ImageIcon(iconURL);
  }

  public static ImageIcon getRun() {
    imageName = "haubold/resources/icons/run.png";
    iconURL = ClassLoader.getSystemResource(imageName);
    return imageIcon = new ImageIcon(iconURL);
  }

  public static ImageIcon getDie() {
    imageName = "haubold/resources/icons/gnome-die3.png";
    iconURL = ClassLoader.getSystemResource(imageName);
    return imageIcon = new ImageIcon(iconURL);
  }

  public static ImageIcon getQuestionMark() {
    imageName = "haubold/resources/icons/questionMark.png";
    iconURL = ClassLoader.getSystemResource(imageName);
    return imageIcon = new ImageIcon(iconURL);
  }

}
