package haubold.birkdesktop;

/**
 * @author Bernhard Haubold
 * Date: Apr 29, 2003; time: 3:41:16 PM.
 *
 * Description:
 */
import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import haubold.resources.util.*;
import haubold.resources.demo.*;

public class BirkDesktop extends JFrame  {
  private static final long serialVersionUID = 1L;
  JDesktopPane desktop;
  InternalFrameListener internalFrameListener;
  BirkhaeuserGUIComponents bgc;
  private static JFrame frame;
  static BirkhaeuserSplashWindow splash;
  JToolBar taskBar;
  JInternalFrame testFrame1, testFrame2;
  JLabel imageLabel;
  DemoFrameListener dfl;
  int demoWidth = 550;
  int demoHeight = 400;
  Rectangle demoBounds;
  static int frameHeight = 750;
  static int frameWidth = 940;
  String version = "1.01";
  String helpPath = "haubold/resources/help/";
  ImageIcon frameIcon;
  ArrayList <DemoFrame>activeFrames;

  public BirkDesktop() {
    super("Bioinformer");
    JMenuItem menuItem;

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//		Don't use splash window as program starts so fast
//		splash =
//			new BirkhaeuserSplashWindow("haubold/resources/images/birk.jpg", this, 1000, version);
    bgc = new BirkhaeuserGUIComponents(version);
    activeFrames = new ArrayList<DemoFrame>();
    // Construct desktop
    desktop = new JDesktopPane();
    desktop.setToolTipText("Select program from menu");
    // Construct frame icon
    frameIcon = Icons.getTreeIcon();
    this.setIconImage(frameIcon.getImage());
    // Background image
    String imageName = "haubold/resources/images/bioinformer.gif";
    ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(imageName));
    imageLabel = new JLabel(icon);
    imageLabel.setBounds(0,0,frameWidth,frameHeight);
    desktop.add(imageLabel, Integer.valueOf(0));
    imageLabel.setVisible(true);

    desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
    internalFrameListener = new InternalFrameIconifyListener();
    demoBounds = new Rectangle(demoWidth,demoHeight);

    taskBar = new JToolBar();
    taskBar.setBackground(bgc.getColor2());
    taskBar.setToolTipText("Task Manager");
    // File menu
    JMenuBar menuBar = new JMenuBar();
    menuBar.setBackground(bgc.getColor2());
    JMenu fileMenu = new JMenu("File");
    fileMenu.setBackground(bgc.getColor2());
    fileMenu.setMnemonic('F');
    menuBar.add(fileMenu);
    // File->Exit, X - Memonic
    JMenuItem exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
    exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
    FileActionListener fileActionListener = new FileActionListener();
    exitMenuItem.addActionListener(fileActionListener);
    fileMenu.add(exitMenuItem);
    // Alignment Menu
    menuBar.add(new AlignmentMenu(this));
    // Match Menu
    menuBar.add(new MatchMenu(this));
    // Probability Menu
    menuBar.add(new ProbabilityMenu(this));
    // Evolution Menu
    menuBar.add(new EvolutionMenu(this));
    //About Menu
    JMenu aboutMenu = new JMenu("About");
    aboutMenu.setBackground(bgc.getColor2());
    aboutMenu.setMnemonic('b');
    menuBar.add(aboutMenu);
    // Construct menu items for about menu
    menuItem = bgc.getAboutMenuItem("About Bioinformer");
    aboutMenu.add(menuItem);
    menuItem.setMnemonic('b');
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,ActionEvent.CTRL_MASK));
    this.setJMenuBar(menuBar);
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(desktop, BorderLayout.CENTER);
    this.getContentPane().add(taskBar,BorderLayout.SOUTH);
  }


  public static void main(String[] arg) {
    frame = new BirkDesktop();
    frame.setSize(frameWidth, frameHeight);
    frame.setVisible(true);
  }

  public class InternalFrameIconifyListener extends InternalFrameAdapter {
    public void internalFrameIconified(InternalFrameEvent internalFrameEvent) {
      JInternalFrame source =
        (JInternalFrame) internalFrameEvent.getSource();
      System.out.println("Iconified: " + source.getTitle());
    }
    public void internalFrameDeiconified(InternalFrameEvent internalFrameEvent) {
      JInternalFrame source =
        (JInternalFrame) internalFrameEvent.getSource();
      System.out.println("Deiconified: " + source.getTitle());
    }
  }

  class FileActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (e.getActionCommand() == "Exit") {
        frame.dispose();
        System.exit(0);
      }
    }
  }

  public String getHelpPath() {
    return helpPath;
  }

  public Rectangle getDemoBounds() {
    return demoBounds;
  }

  public JToolBar getTaskBar() {
    return taskBar;
  }

  public JDesktopPane getDesktop() {
    return desktop;
  }


  public ImageIcon getFrameIcon() {
    return frameIcon;
  }


  public ArrayList<DemoFrame> getActiveFrames() {
    return activeFrames;
  }


  public void setActiveFrames(ArrayList<DemoFrame> activeFrames) {
    this.activeFrames = activeFrames;
  }
}
