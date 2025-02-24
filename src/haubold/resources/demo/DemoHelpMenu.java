package haubold.resources.demo;
import haubold.resources.util.*;

import java.awt.Dimension;
import java.awt.event.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.*;
/**
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Nov 28, 2003; time: 9:07:22 PM.
 *
 * Description: Generic help menu.
 */
public class DemoHelpMenu extends JMenu {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private JTextPane textPane;
  private JMenuItem helpMenuItem;
  private HelpActionListener hal;
  JEditorPane html;
  JDesktopPane desktop;
  Dimension dimension;
  String titleString;
  JToolBar toolBar;
  JButton toolButton;

  public DemoHelpMenu(String helpPath, JDesktopPane desktop, String titleString) {
    this.setText("Help");
    this.setMnemonic(KeyEvent.VK_H);
    helpMenuItem = new JMenuItem("Help");
    helpMenuItem.setMnemonic('h');
    dimension = new Dimension(300,300);
    helpMenuItem.setAccelerator(
      KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
    try {
      URL url = ClassLoader.getSystemResource(helpPath);
      if (url != null) {
        html = new JEditorPane(url);
        html.setEditable(false);
        html.addHyperlinkListener(createHyperLinkListener());

        JScrollPane scroller = new JScrollPane();
        JViewport vp = scroller.getViewport();
        vp.add(html);
        hal = new HelpActionListener(desktop, scroller, dimension, titleString);
        helpMenuItem.addActionListener(hal);
        this.add(helpMenuItem);
      }
    } catch (MalformedURLException e) {
      System.out.println("Malformed URL: " + e);
    } catch (IOException e) {
      System.out.println("IOException: " + e);
    }
  }

  public void setHelpText(String helpText) {
    textPane.setText(helpText);
  }

  public HyperlinkListener createHyperLinkListener() {
    return new HyperlinkListener() {
      public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
          if (e instanceof HTMLFrameHyperlinkEvent) {
            (
              (HTMLDocument) html
              .getDocument())
            .processHTMLFrameHyperlinkEvent(
              (HTMLFrameHyperlinkEvent) e);
          } else {
            try {
              html.setPage(e.getURL());
            } catch (IOException ioe) {
              System.out.println("IOE: " + ioe);
            }
          }
        }
      }
    };
  }

  /**
   * @return
   */
  public JToolBar getToolBar() {
    return toolBar;
  }

  /**
   * @param bar
   */
  public void setToolBar(JToolBar bar) {
    toolBar = bar;
    hal.setToolBar(toolBar);
  }
}
