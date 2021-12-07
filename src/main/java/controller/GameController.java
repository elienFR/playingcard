package controller;

import model.Deck;
import model.Player;
import model.PlayingCard;

import java.util.ArrayList;
import java.util.List;

class View {
  public void something() {

  }

  public void setController(GameController gc) {

  }
}

public class GameController {
  Deck deck;
  List<Player> players;
  Player winner;
  View view;
  GameState gameState;

  public GameController(Deck deck, View view) {
    super();
    this.deck = deck;
    this.view = view;
    this.players = new ArrayList<Player>();
    this.gameState = GameState.AddingPlayer;
    view.setController(this);
  }

  public void run() {
    while (gameState == GameState.AddingPlayer) {
      view.something();
    }

    switch (gameState) {
      case CardsDealt:
        view.something();
        break;
      case WinnerRevealed:
        view.something();
        break;
    }
  }

  public void addPlayer(String playerName) {
    if (gameState == GameState.AddingPlayer) {
      players.add(new Player(playerName));
      view.something();
    }
  }

  public void startGame() {
    if (gameState != GameState.CardsDealt) {
      deck.shuffle();
      for (Player player : players) {
        player.addCardToHand(deck.removeTopCard());
        view.something();
      }
      gameState = GameState.CardsDealt;
    }
    this.run();
  }

  public void fliCards() {
    for (Player player : players) {
      PlayingCard pc = player.getCard(0);
      pc.flip();
      view.something();
    }

    evaluateWinner();
    displayWinner();
    rebuildDeck();
    gameState = GameState.WinnerRevealed;
    this.run();
  }

  public void evaluateWinner() {
    Player bestPlayer = null;
    int bestRank = -1;
    int bestSuit = -1;

    for (Player player : players){
      boolean newBestPlayer = false;

      if(bestPlayer == null) {
        newBestPlayer = true;
      } else {
        PlayingCard pc = player.getCard(0);
        int thisRank = pc.getRank().value();
        if(thisRank >= bestRank){
          if(thisRank > bestRank){
            newBestPlayer = true;
          } else {
            if (pc.getSuit().value() > bestSuit){
              newBestPlayer = true;
            }
          }
        }
      }
      if (newBestPlayer) {
        bestPlayer = player;
        PlayingCard pc = player.getCard(0);
        bestRank = pc.getRank().value();
        bestSuit = pc.getSuit().value();
      }
    }
  }

  public void displayWinner() {
    view.something();
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
