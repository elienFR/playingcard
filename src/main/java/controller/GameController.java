package controller;

import games.GameEvaluator;
import model.Deck;
import model.IPlayer;
import model.PlayingCard;
import view.GameViewable;

import java.util.ArrayList;
import java.util.List;


public class GameController {
  Deck deck;
  List<IPlayer> players;
  IPlayer winner;
  GameState gameState;
  GameEvaluator gameEvaluator;
  GameViewable view;

  public GameController(Deck deck, GameViewable gameViewable, GameEvaluator gameEvaluator) {
    super();
    this.deck = deck;
    this.view = gameViewable;
    this.players = new ArrayList<IPlayer>();
    this.gameState = GameState.AddingPlayer;
    this.gameEvaluator = gameEvaluator;
    view.setController(this);
  }

  public void run() {
    while (gameState == GameState.AddingPlayer) {
      view.promptForPlayerName();
    }

    switch (gameState) {
      case CardsDealt:
        view.promptForFlip();
        break;
      case WinnerRevealed:
        view.promptForNewGame();
        break;
    }
  }

  public void addPlayer(String playerName) {
    if (gameState == GameState.AddingPlayer) {
      players.add(new IPlayer(playerName));
      view.showPlayerName(players.size(), playerName);
    }
  }

  public void startGame() {
    if (gameState != GameState.CardsDealt) {
      deck.shuffle();
      int playerIndex = 1;
      for (IPlayer player : players) {
        player.addCardToHand(deck.removeTopCard());
        view.showFaceDownCardForEachPlayer(playerIndex++, player.getName());
      }
      gameState = GameState.CardsDealt;
    }
    this.run();
  }

  public void flipCards() {
    int playerIndex = 1;
    for (IPlayer player : players) {
      PlayingCard pc = player.getCard(0);
      pc.flip();
      view.showCardForPlayer(playerIndex++,player.getName(),
          pc.getRank().toString(), pc.getSuit().toString());
    }

    evaluateWinner();
    displayWinner();
    rebuildDeck();
    gameState = GameState.WinnerRevealed;
    this.run();
  }

  public void evaluateWinner(){
    winner = gameEvaluator.evaluateWinner(players);
  }

  public void displayWinner() {
    view.showWinner(winner.getName());
  }

  public void rebuildDeck() {
    for (IPlayer player : players) {
      deck.returnCardToDeck(player.removeCard());
    }
  }

  public void exitGame() {
    System.exit(0);
  }

  public void nextAction(String nextChoice) {
    if("+q".equals(nextChoice)){
      exitGame();
    } else {
      startGame();
    }
  }

  enum GameState {
    AddingPlayer, CardsDealt, WinnerRevealed;
  }


}
