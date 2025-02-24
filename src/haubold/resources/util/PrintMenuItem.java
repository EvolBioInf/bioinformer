package haubold.resources.util;
import javax.swing.*;
import javax.swing.text.*;
import javax.print.*;
import javax.print.attribute.*;

import java.awt.GraphicsEnvironment;
import java.awt.event.*;
import java.awt.print.*;
/**
 * Description: Menu item for printing.
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Apr 22, 2004; time: 2:34:14 PM.
 */
public class PrintMenuItem extends JMenuItem {
  private static final long serialVersionUID = 1L;
  private Printable printable;
  private JMenuItem menuItem;

  public PrintMenuItem(String text, int mnemonic, Printable printable) {
    this.setText(text);
    this.setMnemonic(mnemonic);
    this.printable = printable;
    this.addActionListener(new PrintActionListener());
    menuItem = this;
  }

  public PrintMenuItem(String text, int mnemonic, Document document) {
    DocFlavor docFlavor = DocFlavor.STRING.TEXT_HTML;
    PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
    PrintService[] services = PrintServiceLookup.lookupPrintServices(docFlavor, aset);
    ServiceUI.printDialog(GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getConfigurations()[0],
                          100,100,services,services[0],docFlavor,aset);
  }

  class PrintActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      PrinterJob pj = PrinterJob.getPrinterJob();
      pj.setPrintable(printable);

      if (pj.printDialog()) {
        try {
          pj.print();
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(menuItem, "Unfortunately, " +
                                        "the current printer setup leads to an error. " +
                                        "Please choose a different printer setup");
          ex.printStackTrace();
        }
      }
    }
  }


  /**
   * @return
   */
  public Printable getPrintable() {
    return printable;
  }

  /**
   * @param printable
   */
  public void setPrintable(Printable printable) {
    this.printable = printable;
  }

}
