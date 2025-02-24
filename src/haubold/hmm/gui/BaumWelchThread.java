package haubold.hmm.gui;
import haubold.hmm.algorithm.*;
import javax.swing.*;
/**
 * @author haubold
 * Date: Nov 26, 2003; time: 2:02:23 PM.
 *
 * Description: Packages estimation of a hidden Markov model into a thread.
 */
public class BaumWelchThread extends Thread {
  BaumWelch bw;
  ModelPanel modelPanel;
  JButton modelButton;

  public BaumWelchThread(BaumWelch bw, ModelPanel modelPanel, JButton modelButton) {
    this.bw = bw;
    this.modelPanel = modelPanel;
    this.modelButton = modelButton;
  }

  public void run() {
    modelButton.setEnabled(false);
    modelPanel.setPending(true);
    HiddenMarkovModel hmm = bw.newHmm();
    modelPanel.setPending(false);
    modelPanel.setHmm(hmm);
    modelButton.setEnabled(true);
  }

}
