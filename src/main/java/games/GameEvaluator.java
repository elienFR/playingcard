package games;

import model.IPlayer;

import java.util.List;

public interface GameEvaluator {
  public IPlayer evaluateWinner(List<IPlayer> players);

}

