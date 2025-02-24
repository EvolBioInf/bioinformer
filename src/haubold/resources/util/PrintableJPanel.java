package haubold.resources.util;
import javax.swing.*;
import java.awt.print.*;
import java.awt.*;
/**
 * Description: A JPanel that can be printed.
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Apr 22, 2004; time: 2:55:24 PM.
 */
public class PrintableJPanel extends JPanel implements Printable {
  private static final long serialVersionUID = 1L;

  public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
    if (pageIndex >= 1) {
      return Printable.NO_SUCH_PAGE;
    }
    g.translate((int)pageFormat.getImageableX(),(int)pageFormat.getImageableY());
    paint(g);
    return Printable.PAGE_EXISTS;
  }

}
