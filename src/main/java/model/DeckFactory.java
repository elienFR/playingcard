package model;

public class DeckFactory {
  public Deck makeDeck(DeckType type) {
    switch (type) {
      case Normal:
        return new NormalDeck();
      case Small:
        return new SmallDeck();
      case Test:
        return new TestDeck();
    }

    //Fallback

    return new NormalDeck();

  }

  public enum DeckType {
    Normal,
    Small,
    Test;
  }
}
