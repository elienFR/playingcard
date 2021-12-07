package controller;

import model.Deck;
import model.Player;

import java.util.ArrayList;
import java.util.List;

class View {
  public void something(){

  };

  public void setController(GameController gc){

  };
}

public class GameController {
  enum GameState {
    AddingPlayer, CardDealt, WinnerRevealed;
  }
  Deck deck;
  List<Player> players;
  Player winner;
  View view;

  GameState gameState;

  public GameController(Deck deck, View view){
    super();
    this.deck = deck;
    this.view = view;
    this.players = new ArrayList<Player>();
    this.gameState = GameState.AddingPlayer;
    view.setController(this);
  }

  public void run(){
    while (gameState == GameState.AddingPlayer) {
      view.something();
    }

    switch (gameState) {
      case CardDealt:
        view.something();
        break;
      case WinnerRevealed:
        view.something();
        break;
    }
  }
}
