package haubold.resources.demo;
import haubold.resources.util.*;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.print.PrinterJob;
import javax.swing.*;
/**
 * Description:
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Apr 22, 2004; time: 10:29:01 PM.
 */
public class TestPrinting extends JFrame {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  PrintableTextPane textPane;

  public TestPrinting() {
    getContentPane().setLayout(new BorderLayout());
    JButton printButton = new JButton("Print");
    printButton.addActionListener(new PrintActionListener());
    getContentPane().add(printButton,BorderLayout.NORTH);
    textPane = new PrintableTextPane();
    getContentPane().add(textPane,BorderLayout.CENTER);

  }

  public static void main(String[] args) {
    JFrame frame = new TestPrinting();
    frame.setTitle("Test Printing");
    frame.setSize(400,400);
    frame.setVisible(true);
  }

  class PrintActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      PrinterJob pj = PrinterJob.getPrinterJob();
      pj.setPrintable(textPane);
      if(pj.printDialog()) {
        try {
          pj.print();
        } catch(Exception ex) {
          ex.printStackTrace();
        }
      }
    }
  }

}
