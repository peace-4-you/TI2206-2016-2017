/*
 * File: BubbleView.java
 *
 * Class: BubbleView
 *
 * Version: 0.0.1
 *
 * Date: September 8th, 2016
 *
 */


package nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observer;

/**
 * The BubbleView class is responsible for rendering a bubble represented
 * by a BubbleModel
 *
 * @author Calvin Nhieu
 *
 */
public class BubbleView implements Observer {
  private double x;
  private double y;
  private Color color;

  /**
   * Creates BubbleView instance
   */
  public BubbleView() {
    this.x = 0;
    this.y = 0;
    this.color = null;
  }

  /**
   * draws the Bubble
   *
   * @param g  Java Graphics instance
   */
  public void draw(Graphics g) {
    g.setColor(this.color);
    g.fillOval((int) this.x, (int) this.y, (int) BubbleModel.DIAMETER);
  }

  /**
   * @override Observer
   * invoked on Observable changes
   *
   * @param o  changed Observable
   */
  public void update(Observable o) {
    this.x = o.x;
    this.y = o.y;
    this.color = o.drawColor;
  }

  /**
   * returns x value
   */
  public double getX() {
    return this.x;
  }
  /**
   * sets x value
   *
   * @param x  double value to set x to
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * returns y value
   */
  public double getY() {
    return this.y;
  }
  /**
   * sets y value
   *
   * @param y  double value to set y to
   */
  public void setY(double y) {
    this.y = y;
  }

  /**
   * returns color value
   */
  public Color getColor() {
    return this.color;
  }
  /**
   * sets color value
   *
   * @param color  double value to set color to
   */
  public void setColor(Color color) {
    this.color = color;
  }
}
