package haubold.resources.util;
import javax.swing.*;
import java.awt.print.*;
import java.awt.*;
/**
 * Description: A split pane that can be printed.
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Apr 22, 2004; time: 3:07:43 PM.
 */
public class PrintableSplitPane extends JSplitPane implements Printable {
  private static final long serialVersionUID = 1L;

  public PrintableSplitPane(int orientation, boolean b) {
    super(orientation, b);
  }

  public PrintableSplitPane(int orientation, boolean b, Component c1, Component c2) {
    super(orientation, b, c1, c2);
  }

  public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
    if (pageIndex >= 1) {
      return Printable.NO_SUCH_PAGE;
    }
    g.translate((int)pageFormat.getImageableX(),(int)pageFormat.getImageableY());
    paint(g);
    return Printable.PAGE_EXISTS;
  }
}
