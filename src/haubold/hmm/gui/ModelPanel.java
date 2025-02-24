package haubold.hmm.gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;
import javax.swing.*;


import java.awt.geom.*;

import haubold.hmm.algorithm.*;
import haubold.resources.util.*;

/**
 * @author haubold
 * Date: Nov 1, 2003; time: 12:29:29 PM.
 *
 * Description: Grahpical depiction of a hidden Markov model
 * with two hidden states with two emitted states each.
 */
public class ModelPanel extends JPanel {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private HiddenMarkovModel hmm;
  private float boxWidth = 50.0f;
  private float boxHeight;
  private DecimalFormat format;
  private Arrow arrow;
  private BirkhaeuserGUIComponents bgc;
  private Color colorE1, colorE2, colorH1, colorH2;
  private Rectangle histogram1, histogram2;
  private Ellipse2D startingPoint;
  private double scale;
  private double translateX, translateY;
  private boolean pending;
  private RenderingHints qualityHints;
  private int progress;

  public ModelPanel(HiddenMarkovModel hmm) {
    this.hmm = hmm;
    qualityHints =
      new RenderingHints(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON);
    qualityHints.put(
      RenderingHints.KEY_RENDERING,
      RenderingHints.VALUE_RENDER_QUALITY);
    format = new DecimalFormat("0.##");
    arrow = new Arrow();
    bgc = new BirkhaeuserGUIComponents();
    colorE1 = bgc.getColor3();
    colorE2 = bgc.getColor5();
    colorH1 = bgc.getColor2();
    colorH2 = bgc.getColor4();
  }


  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D g2 = (Graphics2D)g;
    // Background color
    g2.setRenderingHints(qualityHints);
    g2.setColor(getBackground());
    g2.fillRect(0,0,getWidth(),getHeight());
    g2.setColor(Color.black);
    if(!pending) {
      drawModel(g2);
    } else {
      drawPending(g2);
    }


  }

  private void drawPending(Graphics2D g2) {
    int h = getHeight();
    int w = getWidth();
    double scaleY = (double)h/200.0;
    double scaleX = (double)w/200.0;
    scale = Math.min(scaleX,scaleY);
    double ty = (h-10*scale)/2.0;
    double tx = (w-170*scale)/2.0;
    int border = 10;
    g2.translate(tx,ty);
    g2.scale(scale,scale);
    g2.setColor(Color.BLACK);
    g2.drawString("Estimating Model",border,0);
    // Draw Progress Box
    g2.setColor(Color.BLACK);
    g2.drawRect(border-4,7,118,7);
    BasicStroke bs = new BasicStroke(7);
    // Draw Progress Bar
    g2.setStroke(bs);
    g2.setColor(Color.red);
    g2.drawLine(border+progress,10,border+progress+10,10);
  }

  private void drawModel(Graphics2D g2) {
    float y1 = boxWidth;
    float y2 = 4*boxWidth;
    float histX = y2;
    int startX = (int)(1.1*boxWidth);
    float middle;
    int width = getWidth();
    int height = getHeight();
    double xScale, yScale;

    // Work out scale
    AffineTransform transform = g2.getTransform();
    xScale = (double)width/300.0;
    yScale = (double)height/300.0;
    scale = Math.min(xScale,yScale);
    // Translate coordinate system
    translateX = (width - (histX + 4.0*boxWidth - startX)*scale)/2.0f;
    translateY = (height - (y2+boxHeight - y1)*scale)/2.0f;
    g2.translate(translateX,translateY);
    g2.scale(scale,scale);
    // Draw histograms.
    g2.setColor(colorH1);
    histogram1 = new Rectangle((int)histX,(int)y1,(int)boxWidth,(int)boxHeight);
    if(hmm == null)
      System.out.println("Model Panel: HMM is null");
    drawHistogram(g2,histogram1,(float)hmm.getObservationProbabilities()[0][0]);
    g2.setColor(colorH2);
    histogram2 = new Rectangle((int)histX,(int)y2,(int)boxWidth,(int)boxHeight);
    drawHistogram(g2,histogram2,(float)hmm.getObservationProbabilities()[1][0]);
    g2.setColor(Color.black);
    // Draw arrows pointing back to histograms
    drawSelfReferenceConcave(g2,histX+boxWidth/2,y1-boxHeight);
    g2.drawString(format.format(hmm.getTransitionProbabilities()[0][0]),
                  (int)(histX+1.25*boxWidth),(int)(y1-1.25*boxHeight+12));
    drawSelfReferenceConvex(g2,histX+boxWidth/2,y2);
    g2.drawString(format.format(hmm.getTransitionProbabilities()[1][1]),
                  (int)(histX+1.25*boxWidth),(int)(y2+boxHeight/4));
    // Draw arrows connecting histograms
    arrow = new Arrow();
    arrow.drawArrow(g2,histX+0.1f*boxWidth,y2-boxHeight,histX+0.1f*boxWidth,y1);
    middle = y1+(y2-y1-boxHeight)/2.0f;
    g2.drawString(format.format(hmm.getTransitionProbabilities()[1][0]),
                  (int)histX-25,
                  (int) middle);
    arrow.drawArrow(g2,histX+0.9f*boxWidth,y1,histX+0.9f*boxWidth,y2-boxHeight);
    g2.drawString(format.format(hmm.getTransitionProbabilities()[0][1]),
                  (int)(histX+boxWidth+5),
                  (int) middle);
    // Draw starting point & corresponding arrows
    startingPoint = new Ellipse2D.Float(startX-5,middle-5,10,10);
    g2.fill(startingPoint);
    arrow.drawArrow(g2,startX,middle,histX,y1-boxHeight/2.0f);
    arrow.drawArrow(g2,startX,middle,histX,y2-boxHeight/2.0f);
    g2.drawString(format.format(hmm.getInitialProbabilities()[0]),
                  startX + (histX-startX)/2.0f,
                  y1+(middle-y1)/2.0f);
    g2.drawString(format.format(hmm.getInitialProbabilities()[1]),
                  startX + (histX-startX)/2.0f,
                  y2-(middle-y1)/2.0f-5);
    g2.setTransform(transform);
  }

  private void drawSelfReferenceConcave(Graphics2D g2, float x, float y) {
    GeneralPath s = new GeneralPath();
    s.moveTo(x,y);
    s.curveTo(x+boxWidth/2,y-boxHeight/2,
              x+boxWidth,y+boxHeight/4,
              x+boxWidth/2,y+boxHeight/2);
    g2.draw(s);
    arrow.drawArrowHead(g2,
                        x+boxWidth/2,y+boxHeight/2,
                        x+1.15f*boxWidth,y);
  }

  private void drawSelfReferenceConvex(Graphics2D g2, float x, float y) {
    GeneralPath s = new GeneralPath();
    s.moveTo(x,y);
    s.curveTo(x+boxWidth/2,y+boxHeight/2,
              x+boxWidth,y-boxHeight/4,
              x+boxWidth/2,y-boxHeight/2);
    g2.draw(s);
    arrow.drawArrowHead(g2,
                        x+boxWidth/2,y-boxHeight/2,
                        x+1.15f*boxWidth,y);
  }

  private void drawHistogram(Graphics2D g, Rectangle rec, float p) {
    float boxX = (float)rec.getX();
    float boxY = (float)rec.getY();
    float letterHeight = 12;
    boxHeight = (float)boxWidth;
    float histogramWidth;
    float hYHeight,hRHeight, maxHistHeight;
    // Draw frame
    g.fillRect((int)boxX,(int)(boxY-boxHeight),(int)boxWidth,(int)boxHeight);
    // Draw base line
    g.setColor(Color.black);
    g.drawLine((int)boxX,(int)(boxY-letterHeight),(int)(boxX+boxWidth),(int)(boxY-letterHeight));
    // Draw letters
    g.setColor(colorE1);
    g.drawString("A/T",boxX+0.5f*boxWidth/5.0f,boxY);
    g.setColor(colorE2);
    g.drawString("G/C",boxX+2.5f*boxWidth/5.0f,boxY);
    // Draw histogram boxes
    histogramWidth = boxWidth/5.0f;
    maxHistHeight = boxHeight - 2.0f*letterHeight;
    hYHeight = p * maxHistHeight;
    hRHeight = (1.0f-p) * maxHistHeight;
    // Draw the two rectangles.
    g.setColor(Color.black);
    g.drawRect(
      (int)(boxX+1.0f*boxWidth/5.0f),
      (int)(boxY-letterHeight-hYHeight),
      (int)histogramWidth,
      (int)hYHeight);
    g.drawRect(
      (int)(boxX+3.0f*boxWidth/5.0f),
      (int)(boxY-letterHeight-hRHeight),
      (int)histogramWidth,
      (int)hRHeight);
    // Fill the two rectangles.
    g.setColor(colorE1);
    g.fillRect(
      (int)(boxX+1.0f*boxWidth/5.0f),
      (int)(boxY-letterHeight-hYHeight),
      (int)histogramWidth,
      (int)hYHeight);
    g.setColor(colorE2);
    g.fillRect(
      (int)(boxX+3.0f*boxWidth/5.0f),
      (int)(boxY-letterHeight-hRHeight),
      (int)histogramWidth,
      (int)hRHeight);
  }
  // The following code does not work due to scaling.
//		public boolean contains(int x, int y){
//			if(histogram1.contains(x,y)){
//				setToolTipText("Hidden State 1");
//			}else if(histogram2.contains(x,y)){
//				setToolTipText("Hidden State 2");
//			}else if(startingPoint.contains(x,y)){
//				setToolTipText("Starting Point");
//			}else{
//				setToolTipText(null);
//			}
//			return super.contains(x,y);
//		}
  /**
   * Get color of first emitted state.
   * @return
   */
  public Color getColorE1() {
    return colorE1;
  }

  /**
   * Get color of second emitted state.
   * @return
   */
  public Color getColorE2() {
    return colorE2;
  }

  /**
   * Get color of first hidden state.
   * @return
   */
  public Color getColorH1() {
    return colorH1;
  }

  /**
   * Get color of second hidden state.
   * @return
   */
  public Color getColorH2() {
    return colorH2;
  }

  /**
   * Set color of first emitted state.
   * @param color
   */
  public void setColorE1(Color color) {
    colorE1 = color;
    repaint();
  }

  /**
   * Set color of second emitted state.
   * @param color
   */
  public void setColorE2(Color color) {
    colorE2 = color;
    repaint();
  }

  /**
   * Set color of first hidden state.
   * @param color
   */
  public void setColorH1(Color color) {
    colorH1 = color;
    repaint();
  }

  /**
   * Set color of second hidden state.
   * @param color
   */
  public void setColorH2(Color color) {
    colorH2 = color;
    repaint();
  }

  /**
   * @param model
   */
  public void setHmm(HiddenMarkovModel model) {
    this.hmm = model;
    repaint();
  }

  public HiddenMarkovModel getHmm() {
    return hmm;
  }

  /**
   * @return
   */
  public boolean isPending() {
    return pending;
  }

  /**
   * @param b
   */
  public void setPending(boolean b) {
    pending = b;
  }

  public void setProgress(int progress) {
    this.progress = progress;
    repaint();
  }
  /**
   * Get the scale of the graphics object.
   * @return
   */
  public double getScale() {
    return scale;
  }

}
