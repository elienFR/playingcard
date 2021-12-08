package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;


public class GameSwingView implements GameViewable {
  GameController gameController;
  JTextArea textArea;
  JFrame frame;

  public void createAndShowGUI() {
    //Create main frame
    frame = new JFrame("MVC-SOLID-Game");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 500);

    //display vertically
    Container contentPane = frame.getContentPane();
    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

    addControllerCommandTracker(contentPane);

    frame.setVisible(true);
  }

  //a simple place to display what the controller is telling us
  //very similar to our command line version
  private void addControllerCommandTracker(Container contentPane) {
    textArea = new JTextArea("Game Status\n", 100, 1);
    JScrollPane scrollPane = new JScrollPane(textArea);
    addCenteredComponent(scrollPane, contentPane);
    textArea.setSize(500, 500);
  }

  private void addCenteredComponent(JComponent component, Container contentPane) {
    component.setAlignmentX(Component.CENTER_ALIGNMENT);
    contentPane.add(component);
  }

  private void appendText(String text) {
    textArea.append(text + "\n");
    textArea.setCaretPosition(textArea.getDocument().getLength());
  }

  @Override
  public void setController(GameController controller) {

    this.gameController = controller;

  }

  @Override
  public void showPlayerName(int playerIndex, String playerName) {
    appendText("[" + playerIndex + "][" + playerName + "]");
  }

  @Override
  public void showCardForPlayer(int playerIndex, String playerName, String cardRank, String cardSuit) {
    appendText("[" + playerName + "][" + cardRank + ":" + cardSuit + "]");
  }

  @Override
  public void showWinner(String winnerName) {
    appendText("Winner!\n" + winnerName);
  }

  @Override
  public void showFaceDownCardForEachPlayer(int playerIndex, String name) {
    appendText("["+playerIndex+"][" + name + "][x][x]");
  }

  @Override
  public void promptForPlayerName() {

    String result = (String) JOptionPane.showInputDialog(frame, "Add a player", "Player",
        JOptionPane.PLAIN_MESSAGE, null, null, "");

    if(result == null || result.isEmpty()) {
      gameController.nextAction("+q");
    }

    gameController.addPlayer(result);

    int addMore = JOptionPane.showConfirmDialog(frame, "Add more players ?", "More players", JOptionPane.YES_NO_OPTION);

    if( addMore == JOptionPane.NO_OPTION) {
      gameController.startGame();
    }
  }

  @Override
  public void promptForFlip() {
    gameController.flipCards();
  }

  @Override
  public void promptForNewGame() {
    int newGame = JOptionPane.showConfirmDialog(frame, "Play again ?", "Play again", JOptionPane.YES_NO_OPTION);
    gameController.nextAction(newGame == JOptionPane.NO_OPTION ? "+q" : "");
  }
}
