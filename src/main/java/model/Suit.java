package model;

public enum Suit {
  DIAMONDS(1),
  HEART(2),
  SPADES(3),
  CLUBS(4);

  int suit;

  private Suit(int value) {
    suit = value;
  }

  public int value() {
    return suit;
  }
}
