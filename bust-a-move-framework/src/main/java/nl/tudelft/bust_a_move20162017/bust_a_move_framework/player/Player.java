package nl.tudelft.bust_a_move20162017.bust_a_move_framework.player;

public class Player {
  private String name;
  private int score;
  private int combo;

  public Player(String name) {
    this.name = name;
    this.score = 0;
    this.combo = 1;
  }

  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public int getScore() {
    return this.score;
  }
  public void setScore(int score) {
    this.score = score;
  }

  public int getCombo() {
    return this.combo;
  }
  public void setCombo(int combo) {
    this.combo = combo;
  }

  public void reset() {
    this.score = 0;
    this.combo = 1;
  }
}
