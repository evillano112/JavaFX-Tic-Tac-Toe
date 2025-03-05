import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.shape.Line;
import javafx.event.EventHandler;
public class TileBoard{
  private StackPane pane;
  private InfoC infoC;

  Image winImage = new Image("crown.png");
  ImageView iv = new ImageView();
  private Tile[][] tiles = new Tile[3][3];
  private char playerTurn = 'X';
  private boolean isEndOfGame = false;
  private Line line;
  public TileBoard(InfoC infoCP){
    line = new Line();
    iv.setImage(winImage);
    iv.setFitHeight(300);
    iv.setFitWidth(300);
    iv.setVisible(false);
    this.infoC = infoCP;
    pane = new StackPane();
    pane.setMinSize(UI.APP_WIDTH, UI.TILE_BOARD_HEIGHT);
    pane.setTranslateX(UI.APP_WIDTH / 2);
    pane.setTranslateY((UI.TILE_BOARD_HEIGHT /2) + UI.INFO_CENTER_HEIGHT);
    pane.getChildren().add(iv);
    pane.getChildren().add(line);
    addAllTiles();
  }

  private void addAllTiles() {
    for (int row = 0; row < 3; row++){
      for (int col = 0; col < 3; col++){
        Tile tile = new Tile();
        tile.getStackPane().setTranslateX((col*100)-100);
        tile.getStackPane().setTranslateY((row*100)-100);
        pane.getChildren().add(tile.getStackPane());
        tiles[row][col] = tile;
      }
    }
  }

  public void startNewGame(){
    isEndOfGame = false;
    iv.setVisible(false);
    line.setVisible(false);
    infoC.updateButton("Reset");
    playerTurn = 'X';
    for(int row = 0; row < 3; row++){
      for (int col = 0; col < 3; col++){
        tiles[row][col].setValue("");
      }
    }
  }
  public void changePlayerTurn(){
    if (playerTurn == 'X') {
      playerTurn = 'O';
    } else {
      playerTurn = 'X';
    }
    infoC.updateMessage("Player "+ playerTurn + "'s Turn'");
  }

  public String getPlayerTurn(){
    return String.valueOf(playerTurn);
  }
  public StackPane getStackPane(){
    return pane;
  }

  public void checkForWinner(){
    checkRowsForWinner();
    checkColsForWinner();
    checkTLeftBRightForWinner();
    checkTRightBLeftForWinner();
    checkForStalemate();
  }

  private void checkRowsForWinner(){
    if (!isEndOfGame){
      for (int row = 0; row < 3; row++){
        if ((tiles[row][0].getValue().equals(tiles[row][1].getValue())) && (tiles[row][0].getValue().equals(tiles[row][2].getValue())) && !(tiles[row][0].getValue().isEmpty())){
          String winner = tiles[row][0].getValue();
          endGame(winner, new WinningTiles(tiles[row][0],tiles[row][1],tiles[row][2]));
          return;
        }
      }
    }
    
  }
  private void checkColsForWinner(){
    if(!isEndOfGame){
      for (int col = 0; col < 3; col++){
        if ((tiles[0][col].getValue().equals(tiles[1][col].getValue())) && (tiles[0][col].getValue().equals(tiles[2][col].getValue())) && !(tiles[0][col].getValue().isEmpty())){
          String winner = tiles[0][col].getValue();
          endGame(winner, new WinningTiles(tiles[0][col],tiles[1][col],tiles[2][col]));
          return;
      }
    }

      
    }

  }
  private void checkTLeftBRightForWinner(){
    if(!isEndOfGame){
      if(tiles [0][0].getValue().equals(tiles[1][1].getValue()) && tiles[0][0].getValue().equals(tiles[2][2].getValue()) && !tiles[0][0].getValue().isEmpty()){
        String winner = tiles[0][0].getValue();
        endGame(winner, new WinningTiles(tiles[0][0],tiles[1][1],tiles[2][2]));
        return;
      }
    }
  }
  private void checkTRightBLeftForWinner(){
    if(!isEndOfGame){
      if(tiles [0][2].getValue().equals(tiles[1][1].getValue()) && tiles[0][2].getValue().equals(tiles[2][0].getValue()) && !tiles[0][2].getValue().isEmpty()){
        String winner = tiles[0][2].getValue();
        endGame(winner, new WinningTiles(tiles[0][2],tiles[1][1],tiles[2][0]));
        return;
      }
    }
      
    }
  
  private void checkForStalemate(){
    if(!isEndOfGame){
      for(int row = 0; row<3; row++){
        for(int col = 0; col<3; col++){
          if (tiles[row][col].getValue().isEmpty()){
            return;
          }
        }
      }
      isEndOfGame = true;
      infoC.updateMessage("Stalemate!");
      infoC.updateButton("Start New Game");
    }
  }

  private void endGame(String winner, WinningTiles winningTiles){
    drawWinningLine(winningTiles);
    isEndOfGame = true;
    infoC.updateButton("Start New Game");
    iv.setVisible(true);
    infoC.updateMessage("Player " + winner + " wins!");
  }
  private void drawWinningLine(WinningTiles winningTiles){
    line.setStartX(winningTiles.start.getStackPane().getTranslateX());
    line.setStartY(winningTiles.start.getStackPane().getTranslateY());
    line.setEndX(winningTiles.end.getStackPane().getTranslateX());
    line.setEndY(winningTiles.end.getStackPane().getTranslateY());
    line.setTranslateX(winningTiles.middle.getStackPane().getTranslateX());
    line.setTranslateY(winningTiles.middle.getStackPane().getTranslateY());
    line.setVisible(true);
  }

  private class WinningTiles {
    Tile start;
    Tile middle;
    Tile end;
    public WinningTiles (Tile start,Tile middle, Tile end){
      this.start = start;
      this.middle = middle;
      this.end = end;
    }
  }
  private class Tile {
    private Label label;
    private StackPane pane;
    
    public Tile(){
      pane = new StackPane();
      pane.setMinSize(100,100);
      
      Rectangle border = new Rectangle();
      border.setHeight(100);
      border.setWidth(100);
      border.setFill(Color.TRANSPARENT);
      border.setStroke(Color.BLACK);
      pane.getChildren().add(border);
      label = new Label("");
      label.setAlignment(Pos.CENTER);
      label.setFont(Font.font(24));
      pane.getChildren().add(label);

      pane.setOnMouseClicked(event -> {if (label.getText().isEmpty() && !isEndOfGame){
        label.setText(getPlayerTurn());
        changePlayerTurn();
        checkForWinner();
      }});
         
    }

    public StackPane getStackPane(){
      return pane;
    }

    public String getValue(){
      return label.getText();
    }

    public void setValue(String value){
      label.setText(value);
    }
  }
}