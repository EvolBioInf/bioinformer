package haubold.resources.util;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import haubold.sequencetools.*;

/**
 * Description: JMenuItem for opening sequence files.
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Apr 21, 2004; time: 9:49:26 PM.
 */
public class OpenSequenceMenuItem extends JMenuItem {
  private static final long serialVersionUID = 1L;
  JTextPane textPane;
  JFileChooser fileChooser;
  StringUtils stringUtils;
  StringBuffer stringBuffer;

  public OpenSequenceMenuItem() {
    fileChooser = new JFileChooser();
    this.addActionListener(new OpenActionListener());
    stringUtils = new StringUtils();
    stringBuffer = new StringBuffer();
  }

  public OpenSequenceMenuItem(String title, JTextPane textPane) {
    this.setText(title);
    this.textPane = textPane;
    // Create file chooser
    fileChooser = new JFileChooser();
    ExtensionFileFilter fileFilter = new ExtensionFileFilter(
      "Sequence Files (*.fasta, *.fas, *.embl, *.em, *.gbk, *.gb)", new String[] {"fasta","fas","embl","em","gbk","gb"});
    fileChooser.addChoosableFileFilter(fileFilter);
    this.addActionListener(new OpenActionListener());
    stringUtils = new StringUtils();
    stringBuffer = new StringBuffer();
  }

  public OpenSequenceMenuItem(String title, int mnemonic, JTextPane textPane) {
    this.setText(title);
    this.setMnemonic(mnemonic);
    this.textPane = textPane;
    // Create file chooser
    fileChooser = new JFileChooser();
    ExtensionFileFilter fileFilter = new ExtensionFileFilter(
      "Sequence Files (*.fasta, *.fas, *.embl, *.em, *.gbk, *.gb)", new String[] {"fasta","fas","embl","em","gbk","gb"});
    fileChooser.addChoosableFileFilter(fileFilter);
    this.addActionListener(new OpenActionListener());
    stringUtils = new StringUtils();
    stringBuffer = new StringBuffer();
  }

  private void open() {
    SequenceReader sequenceReader;
    String seq="";
    String name;
    BioSequence bioSequence;
    int returnVal = fileChooser.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      sequenceReader = new SequenceReader(file.getPath());
      stringBuffer.delete(0,stringBuffer.length());
      while(!sequenceReader.isEmpty()) {
        bioSequence = sequenceReader.getBioSequence();
        seq = bioSequence.getSequence();
        name = bioSequence.getName();
        stringBuffer.append(">" + name + "\n" + seq + "\n");
      }
      if(stringBuffer.length() < 499001) {
        setSequence(stringBuffer.toString());
        textPane.setCaretPosition(0);
      } else {
        JOptionPane.showMessageDialog(this,"I am sorry, but I cannot display sequence data totalling more than 499,000 residues.");
      }
    }
  }


  private void setSequence(String seq) throws Error {
    textPane.setText(seq);
  }

  class OpenActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      open();
    }

  }

  /**
   * @return
   */
  public JTextPane getTextPane() {
    return textPane;
  }

  /**
   * @param pane
   */
  public void setTextPane(JTextPane pane) {
    textPane = pane;
  }

}
