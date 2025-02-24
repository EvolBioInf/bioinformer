package haubold.wrightfisher;

import java.awt.*;
import java.util.*;

/**
 * Description: A node in the Wright-Fisher graph.
 *
 * @author Bernhard Haubold, Fachhochschule Weihenstephan, Freising, Germany
 * Date: Oct 5, 2005; time: 12:15:59 PM.
 */
public class Node {
  private ArrayList<Node> children;
  private ArrayList<Node> descendants;
  private ArrayList<Node> offspring;
  private Node ancestor1;
  private Node ancestor2;
  private Shape shape, untangledShape;
  private int generation;
  private int tangledX, untangledX;
  private int id;

  private boolean geneMrca;
  private boolean indivMrca;
  private boolean ua;
  private boolean nonUa;
  private boolean visited;
  private boolean ancestral;
  private boolean clicked;
  private boolean mouseOver;
  private boolean markedForward, markedBackward;
  private Color colorForward, colorBackward;

  public Node() {
    this.id = -1;
    children = new ArrayList<Node>();
    descendants = new ArrayList<Node>();
    offspring = new ArrayList<Node>();
    this.setGeneMrca(false);
    this.setIndivMrca(false);
    this.setUa(false);
    this.setVisited(false);
    this.setAncestral(false);
    this.setMouseOver(false);
  }


  public boolean isClicked() {
    return clicked;
  }
  public void setClicked(boolean clicked) {
    this.clicked = clicked;
  }
  public boolean isMouseOver() {
    return mouseOver;
  }
  public void setMouseOver(boolean mouseOver) {
    this.mouseOver = mouseOver;
  }
  public int getGeneration() {
    return generation;
  }
  public void setGeneration(int generation) {
    this.generation = generation;
  }
  public int getTangledX() {
    return tangledX;
  }
  public void setTangledX(int tangledX) {
    this.tangledX = tangledX;
  }
  public int getUntangledX() {
    return untangledX;
  }
  public void setUntangledX(int untangledX) {
    this.untangledX = untangledX;
  }
  public Node getAncestor1() {
    return ancestor1;
  }
  public void setAncestor1(Node ancestor1) {
    this.ancestor1 = ancestor1;
  }
  public Node getAncestor2() {
    return ancestor2;
  }
  public void setAncestor2(Node ancestor2) {
    this.ancestor2 = ancestor2;
  }
  public ArrayList<Node> getChildren() {
    return children;
  }
  public void setChildren(ArrayList<Node> children) {
    this.children = children;
  }
  public ArrayList<Node> getDescendants() {
    return descendants;
  }
  public void setDescendants(ArrayList<Node> descendants) {
    this.descendants = descendants;
  }
  public boolean isGeneMrca() {
    return geneMrca;
  }
  public void setGeneMrca(boolean geneMrca) {
    this.geneMrca = geneMrca;
  }
  public boolean isIndivMrca() {
    return indivMrca;
  }
  public void setIndivMrca(boolean indivMrca) {
    this.indivMrca = indivMrca;
  }
  public boolean isNonUa() {
    return nonUa;
  }
  public void setNonUa(boolean nonUa) {
    this.nonUa = nonUa;
  }
  public Shape getShape() {
    return shape;
  }
  public void setShape(Shape shape) {
    this.shape = shape;
  }
  public boolean isUa() {
    return ua;
  }
  public void setUa(boolean ua) {
    this.ua = ua;
  }

  public boolean isVisited() {
    return visited;
  }
  public void setVisited(boolean visited) {
    this.visited = visited;
  }
  public boolean isAncestral() {
    return ancestral;
  }
  public void setAncestral(boolean ancestral) {
    this.ancestral = ancestral;
  }
  public ArrayList<Node> getOffspring() {
    return offspring;
  }
  public void setOffspring(ArrayList<Node> offspring) {
    this.offspring = offspring;
  }
  public Color getColorForward() {
    return colorForward;
  }
  public void setColorForward(Color color) {
    this.colorForward = color;
  }
  public Shape getUntangledShape() {
    return untangledShape;
  }
  public void setUntangledShape(Shape untangledShape) {
    this.untangledShape = untangledShape;
  }
  public Color getColorBackward() {
    return colorBackward;
  }
  public void setColorBackward(Color colorBackward) {
    this.colorBackward = colorBackward;
  }
  public boolean isMarkedBackward() {
    return markedBackward;
  }
  public void setMarkedBackward(boolean markedBackward) {
    this.markedBackward = markedBackward;
  }
  public boolean isMarkedForward() {
    return markedForward;
  }
  public void setMarkedForward(boolean markedForward) {
    this.markedForward = markedForward;
  }


  public int getId() {
    return id;
  }


  public void setId(int id) {
    this.id = id;
  }
}
