package games;

import model.Player;
import model.PlayingCard;

import java.util.List;

public interface GameEvaluator {
  public Player evaluateWinner(List<Player> players);

}

