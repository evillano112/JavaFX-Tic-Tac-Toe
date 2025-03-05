/*
  @Author Edward Villano
  @Version 1.0
  @Date 5/7/23
**/

/*
  This is a simple tic-tac-toe game
  @Class CSCI-2210
*/

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;


public class Main extends Application{

  private InfoC infoC;
  private TileBoard tileBoard;
  @Override
  public void start(Stage primaryStage){
    try{
    BorderPane bPane = new BorderPane();
    Scene scene = new Scene(bPane, UI.APP_WIDTH, UI.APP_HEIGHT);
    startLayout(bPane);
    primaryStage.setScene(scene);
      primaryStage.setTitle("Tic-Tac-Toe Game");
    primaryStage.show();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
  public static void main(String[] args){
    launch(args);
  }

  private void startLayout (BorderPane bPane){
    startInfoC(bPane);
    startGrid(bPane);
  }
  
  private void startInfoC (BorderPane bPane) {
    infoC = new InfoC();
    infoC.setStartButtonOnAction(startNewGame());
    bPane.getChildren().add(infoC.getStackPane());
  }

  private EventHandler<ActionEvent> startNewGame(){
    return new EventHandler<ActionEvent>(){
      @Override
      public void handle (ActionEvent e){
        infoC.updateMessage("X's Turn");
        tileBoard.startNewGame();
      }
    };
  }

  
  private void startGrid(BorderPane bPane){
    tileBoard = new TileBoard(infoC);

    bPane.getChildren().add(tileBoard.getStackPane());
    
  }
}
