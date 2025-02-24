package haubold.dp;
import javax.swing.*;
import java.awt.*;
/**
 * The <code>GapModel</code> defines the the <code>gapOpening</code>
 * and the <code>gapExtension</code> scores, as well as a GUI.
 *
 * @version 0.0 July 12, 2002
 * @author Bernhard Haubold
 *
 */
public class GapModel {
  JPanel panel;
  JTextField gapOpeningField, gapExtensionField;
  JLabel gapCostLabel, plusLabel, gapLengthLabel;

  GapModel() {
    Font font12 = new Font("CourierNew",Font.PLAIN,12);
    panel=new JPanel(new FlowLayout());
    panel.setBackground(Color.blue);
    gapOpeningField=new JTextField("0.0",5);
    gapExtensionField=new JTextField("-1.0",5);
    gapOpeningField.setFont(font12);
    gapOpeningField.setEnabled(false);
    gapExtensionField.setFont(font12);
    gapOpeningField.setBackground(Color.white);
    gapOpeningField.setForeground(Color.blue);
    gapExtensionField.setBackground(Color.white);
    gapExtensionField.setForeground(Color.blue);
    gapCostLabel=new JLabel("Gap cost = ");
    plusLabel=new JLabel("+");
    gapLengthLabel=new JLabel("x gap length");
    gapCostLabel.setFont(font12);
    plusLabel.setFont(font12);
    gapCostLabel.setFont(font12);
    gapCostLabel.setOpaque(true);
    plusLabel.setOpaque(true);
    gapLengthLabel.setOpaque(true);
    gapCostLabel.setBackground(Color.blue);
    gapCostLabel.setForeground(Color.yellow);
    plusLabel.setBackground(Color.blue);
    plusLabel.setForeground(Color.yellow);
    gapLengthLabel.setBackground(Color.blue);
    gapLengthLabel.setForeground(Color.yellow);
    panel.add(gapCostLabel);
    panel.add(gapOpeningField);
    panel.add(plusLabel);
    panel.add(gapExtensionField);
    panel.add(gapLengthLabel);
  }

  public double getGapOpening() {
    double gapOpening=Double.parseDouble(gapOpeningField.getText());
    return gapOpening;
  }

  public double getGapExtension() {
    double gapExtension=Double.parseDouble(gapExtensionField.getText());
    return gapExtension;
  }

  public void setGapOpening(double go) {
    gapOpeningField.setText(String.valueOf(go));
  }

  public void setGapExtension(double ge) {
    gapExtensionField.setText(String.valueOf(ge));
  }

  public void enableEdit() {
    gapOpeningField.setEditable(true);
    gapExtensionField.setEditable(true);
  }

  public void disableEdit() {
    gapOpeningField.setEditable(false);
    gapExtensionField.setEditable(false);
  }
}
