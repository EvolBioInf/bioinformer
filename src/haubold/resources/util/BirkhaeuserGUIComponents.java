package haubold.resources.util;

/**
 * @author Bernhard Haubold
 * Date: Apr 22, 2003; time: 6:04:38 PM.
 *
 * Description: This class collects GUI features that apply to all programs
 * distributed with <it>Introduction to Computational Biology</it> by Bernhard
 * Haubold and Thomas Wiehe.
 */

import java.awt.Color;
import javax.swing.*;
import javax.swing.border.*;

public class BirkhaeuserGUIComponents {

  private Color color1, lightGreen;
  private Color color2, lightYellow;
  private Color color3, darkRed;
  private Color color4, lightBlue;
  private Color color5, darkGreen;
  private JSlider slider;
  private String sliderTitle = "Slider Title";

  private Border sliderBorder;
  private String version;

  public BirkhaeuserGUIComponents() {

  }

  public BirkhaeuserGUIComponents(String version) {
    this.version = version;
    // Colors
    // Slider
  }

  /**
   * Returns the colorPresentationPanel.
   * @return Color
   */
  public Color getColor1() {
    if (lightGreen == null) {
      lightGreen = new Color(220, 255, 220);
    }
    color1 = lightGreen;
    return color1;
  }

  /**
   * Returns the color2.
   * @return Color
   */
  public Color getColor2() {
    if (lightYellow == null) {
      lightYellow = new Color(255, 255, 204);
      color2 = lightYellow;
    }
    return color2;
  }

  /**
   * Returns the slider.
   * @return JSlider
   */
  public JSlider getSlider(
    int minimumValue,
    int maximumValue,
    int initialValue,
    String sliderTitle) {
    this.sliderTitle = sliderTitle;
    slider = new JSlider(minimumValue, maximumValue, initialValue);
    slider.setMajorTickSpacing((maximumValue - minimumValue) / 5);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    slider.setSnapToTicks(false);
    sliderBorder = new TitledBorder(sliderTitle);
    slider.setBorder(sliderBorder);
    return slider;
  }

  /**
   * Returns the sliderTitle.
   * @return String
   */
  public String getSliderTitle() {
    return sliderTitle;
  }

  /**
   * Sets the slider.
   * @param slider The slider to set
   */
  public void setSlider(JSlider slider) {
    this.slider = slider;
  }

  /**
   * Sets the sliderTitle.
   * @param sliderTitle The sliderTitle to set
   */
  public void setSliderTitle(String sliderTitle) {
    sliderBorder = new TitledBorder(sliderTitle);
    slider.setBorder(sliderBorder);
    this.sliderTitle = sliderTitle;
  }

  /**
   * Returns the aboutButton.
   * @return JButton
   */
  public JButton getAboutButton(String title) {
    BirkhaeuserAboutButton aboutButton = new BirkhaeuserAboutButton();
    aboutButton.setTitleString(title);
    return aboutButton;
  }

  /**
   * Returns the aboutButton.
   * @return JButton
   */
  public JMenuItem getAboutMenuItem(String title) {
    BirkhaeuserAboutMenuItem aboutMenuItem = new BirkhaeuserAboutMenuItem(version);
    aboutMenuItem.setTitleString(title);
    return aboutMenuItem;
  }

  /**
   * Returns the color3.
   * @return Color
   */
  public Color getColor3() {
    if(darkRed == null) {
      darkRed = new Color(204,102,0);
      color3 = darkRed;
    }
    return color3;
  }

  /**
   * Returns the color4.
   * @return Color
   */
  public Color getColor4() {
    if(lightBlue == null) {
      lightBlue = new Color(204,204,255);
      color4 = lightBlue;
    }
    return color4;
  }
  /**
   * Returns the color5.
   * @return Color
   */
  public Color getColor5() {
    if(darkGreen == null) {
      darkGreen = new Color(51,153,0);
      color5 = darkGreen;
    }
    return color5;
  }

  /**
   * @return Returns the version.
   */
  public String getVersion() {
    return version;
  }
}
