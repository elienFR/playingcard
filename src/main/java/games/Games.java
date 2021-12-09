package games;

import controller.GameController;
import model.Deck;
import model.DeckFactory;
import view.CommandLineView;
import view.GameSwingView;

public class Games {
  public static void main(String[] args) {
    GameSwingView gsv = new GameSwingView();
    gsv.createAndShowGUI();
    GameController gc = new GameController(DeckFactory.makeDeck(DeckFactory.DeckType.Normal), gsv, new LowCardGameEvaluator());
    gc.run();

  }
}
