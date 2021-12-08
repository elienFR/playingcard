package controller;

import games.GameEvaluator;
import games.HighCardGameEvaluator;
import model.Deck;
import model.Player;
import model.PlayingCard;
import view.GameViewable;
import view.View;

import java.util.ArrayList;
import java.util.List;


public class GameController {
  Deck deck;
  List<Player> players;
  Player winner;
  View view;
  GameState gameState;
  GameEvaluator gameEvaluator;
  GameViewable gameViewable;

  public GameController(Deck deck, GameViewable gameViewable, GameEvaluator gameEvaluator) {
    super();
    this.deck = deck;
    this.gameViewable = gameViewable;
    this.players = new ArrayList<Player>();
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
      players.add(new Player(playerName));
      view.showPlayerName(players.size(), playerName);
    }
  }

  public void startGame() {
    if (gameState != GameState.CardsDealt) {
      deck.shuffle();
      int playerIndex = 1;
      for (Player player : players) {
        player.addCardToHand(deck.removeTopCard());
        view.showFaceDownCardForEachPlayer(playerIndex++, player.getName());
      }
      gameState = GameState.CardsDealt;
    }
    this.run();
  }

  public void flipCards() {
    int playerIndex = 1;
    for (Player player : players) {
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
    for (Player player : players) {
      deck.returnCardToDeck(player.removeCard());
    }
  }

  enum GameState {
    AddingPlayer, CardsDealt, WinnerRevealed;
  }


}
