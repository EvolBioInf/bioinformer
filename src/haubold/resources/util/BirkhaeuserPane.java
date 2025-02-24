package haubold.resources.util;

/**
 * @author Bernhard Haubold
 * Date: May 3, 2003; time: 10:59:35 AM.
 *
 * Description:
 */
import java.net.URL;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

public class BirkhaeuserPane extends JTextPane {
  private static final long serialVersionUID = 1L;
  private String version = null;
  /*
   * Construct the text pane.
   */
  BirkhaeuserGUIComponents bgc;
  public BirkhaeuserPane() {
    constructBirkhaeuserPane();
  }
  public BirkhaeuserPane(String version) {
    this.version = version;
    constructBirkhaeuserPane();
  }

  void constructBirkhaeuserPane() {
    bgc = new BirkhaeuserGUIComponents();
    this.setBackground(bgc.getColor1());
    Document doc = this.getDocument();
    String imageName = "haubold/resources/images/bioinformerSmall.gif";
    URL iconURL = ClassLoader.getSystemResource(imageName);
    Icon icon = new ImageIcon(iconURL);
    //Initialize some styles.
    Style def =
      StyleContext.getDefaultStyleContext().getStyle(
        StyleContext.DEFAULT_STYLE);
    Style regular = this.addStyle("regular", def);
    StyleConstants.setFontFamily(regular, "SansSerif");
    StyleConstants.setAlignment(regular, StyleConstants.ALIGN_CENTER);
    StyleConstants.setForeground(regular,Color.BLACK);
    Style s = this.addStyle("email", regular);
    StyleConstants.setFontFamily(s, "Courier");
    StyleConstants.setAlignment(s, StyleConstants.ALIGN_CENTER);
    StyleConstants.setFontSize(s, 14);

    s = this.addStyle("italic", regular);
    StyleConstants.setItalic(s, true);

    s = this.addStyle("red",regular);
    StyleConstants.setForeground(s,Color.red);

    s = this.addStyle("center", regular);

    s = this.addStyle("boldEmail", regular);
    StyleConstants.setFontFamily(s, "monospaced");
    StyleConstants.setAlignment(s,StyleConstants.ALIGN_CENTER);
    StyleConstants.setBold(s, true);
    StyleConstants.setFontSize(s,28);

    s = this.addStyle("super",regular);
    StyleConstants.setSuperscript(s,true);

    s = this.addStyle("icon", regular);
    StyleConstants.setAlignment(s, StyleConstants.ALIGN_CENTER);
    StyleConstants.setIcon(s, icon);
    try {
      doc.insertString(doc.getLength(), " ", this.getStyle("icon"));
      doc.insertString(
        doc.getLength(),
        "\nbioinformer " + version,
        this.getStyle("boldEmail"));
      doc.insertString(
        doc.getLength(),
        "\nThis software is destributed as part of the book ",
        this.getStyle("regular"));
      doc.insertString(
        doc.getLength(),
        "Introduction to Computational Biology - an Evolutionary Approach ",
        this.getStyle("italic"));
      doc.insertString(
        doc.getLength(),
        "by Bernhard Haubold",
        this.getStyle("regular"));
      doc.insertString(
        doc.getLength(),
        "1",
        this.getStyle("super"));
      doc.insertString(
        doc.getLength(),
        " and Thomas Wiehe",
        this.getStyle("regular"));
      doc.insertString(
        doc.getLength(),
        "2",
        this.getStyle("super"));
      doc.insertString(
        doc.getLength(),
        ", Birkh\u00E4user Verlag, 2006. \n"
        + "Please refer to the book for",
        this.getStyle("regular"));
      doc.insertString(
        doc.getLength(),
        " tutorials ",
        this.getStyle("red"));
      doc.insertString(
        doc.getLength(),
        "on all modules of this software."
        + "\n\nSoftware developement by Bernhard Haubold, "
        + "all rights reserved.\n\nThis software incorporates JFreeChart, " +
        "(C)opyright 2000-2004 by Object Refinery Limited " +
        "and Contributors. JFreeChart is distributed under the " +
        "GNU Lesser General Public License (LGPL), a copy of which " +
        "is supplied with this sofware\n\n",
        this.getStyle("regular"));
      doc.insertString(
        doc.getLength(),
        "1",
        this.getStyle("super"));
      doc.insertString(
        doc.getLength(),
        "Research Group Bioinformatics, Max-Planck-Institute for Evolutionary Biology, Plön, Germany\n",
        this.getStyle("regular"));
      doc.insertString(
        doc.getLength(),
        "2",
        this.getStyle("super"));
      doc.insertString(
        doc.getLength(),
        "Institute for Genetics, University of Cologne, Germany",
        this.getStyle("regular"));
    } catch (Exception e) {
    }
    this.setEditable(false);
  }
}
