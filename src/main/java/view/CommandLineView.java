package view;

import controller.GameController;

import java.util.Scanner;

public class CommandLineView implements GameViewable {
  GameController controller;
  Scanner scanner = new Scanner(System.in);

  public void setController(GameController gc) {
    this.controller = gc;
  }

  public void promptForPlayerName() {
    System.out.println("Enter IPlayer Name :");
    String name = scanner.nextLine();
    if (name.isEmpty()) {
      controller.startGame();
    } else {
      controller.addPlayer(name);
    }
  }

  public void promptForFlip() {
    System.out.println("Press enter to reveal cards.");
    scanner.nextLine();
    controller.flipCards();
  }

  public void promptForNewGame() {
    System.out.println("Press enter to deal again or +q to exit.");
    controller.nextAction(scanner.nextLine());
  }

  public void showPlayerName(int playerIndex, String playerName) {
    System.out.println("[" + playerIndex + "][" + playerName + "]");
  }

  public void showFaceDownCardForPlayer(int playerIndex, String playerName) {
    System.out.println("[" + playerIndex + "][" + playerName + "[x][x]");
  }

  public void showCardForPlayer(int i, String playerName, String rank, String suit) {
    System.out.println("[" + i + "][" + playerName + "][" + rank + "][" + suit + "]");
  }

  public void showWinner(String name) {
    System.out.println("Winner is " + name + " !");
  }
}
