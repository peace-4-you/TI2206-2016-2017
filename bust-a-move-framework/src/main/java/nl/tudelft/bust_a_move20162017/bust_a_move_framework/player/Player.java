/*
 * File: Player.java
 * Class: Player
 *
 * Version: 0.0.1
 * Date: September 7th, 2016
 */

package nl.tudelft.bust_a_move20162017.bust_a_move_framework.player;

/**
 * The Player class represents a player entity.
 *
 * @author Calvin Nhieu
 */
public class Player {
  private String name;
  private int score;
  private int combo;

  public Player(String name) {
    this.name = name;
    this.score = 0;
    this.combo = 1;
  }

  /**
   * returns name
   */
  public String getName() {
    return this.name;
  }

  /**
   * sets the player's name
   *
   * @param name  String value to set name to
   *
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * returns score
   */
  public int getScore() {
    return this.score;
  }
  /**
   * sets the player's score
   *
   * @param name  integer value to set score to
   *
   */
  public void setScore(int score) {
    this.score = score;
  }

  /**
   * returns combo
   */
  public int getCombo() {
    return this.combo;
  }
  /**
   * sets the player's combo
   *
   * @param name  integer value to set combo to
   *
   */
  public void setCombo(int combo) {
    this.combo = combo;
  }

  /**
   * Sets the player's members to default values
   */
  public void reset() {
    this.score = 0;
    this.combo = 1;
  }
}
