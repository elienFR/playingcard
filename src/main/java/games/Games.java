package games;

import controller.GameController;
import model.DeckFactory;
import view.GameSwingPassiveView;
import view.GameSwingView;
import view.GameViewables;

import java.util.concurrent.ExecutionException;

public class Games {
  public static void main(String[] args) {

    GameViewables views = new GameViewables();

    GameSwingView gsv = new GameSwingView();
    gsv.createAndShowGUI();

    views.addViewable(gsv);

    for(int i=0 ; i<3; i++){
      GameSwingPassiveView passiveView = new GameSwingPassiveView();
      passiveView.createAndShowGUI();

      views.addViewable(passiveView);

      //sleep to move new Swing frame on windows
      try{
        Thread.sleep(2500);
      } catch (InterruptedException e){
        e.printStackTrace();
      }
    }

    GameController gc = new GameController(DeckFactory.makeDeck(DeckFactory.DeckType.Normal), views, new LowCardGameEvaluator());
    gc.run();

  }
}
