package haubold.resources.util;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.print.*;

import javax.swing.JTextPane;

/**
 * Description: Learn how to use the javax printing framework.
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Apr 22, 2004; time: 9:00:39 PM.
 */
public class PrintableTextPane extends JTextPane implements Printable {
  private static final long serialVersionUID = 1L;
  TextLayout textLayout;

  public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
    if (pageIndex >= 1) {
      return Printable.NO_SUCH_PAGE;
    }
    Graphics2D g2 = (Graphics2D)g;
    textLayout = new TextLayout(this.getText(), new Font("monospaced", Font.PLAIN, 12), new FontRenderContext(null, false, false));
    textLayout.draw(g2,(float)pageFormat.getImageableX()+10,(float)pageFormat.getImageableY()+20);
    return Printable.PAGE_EXISTS;
  }
}
