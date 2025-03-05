import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import javafx.geometry.Pos;

public class InfoC {
  private StackPane pane = new StackPane();
  private Label message;
  private Button startButton;
  
  public InfoC (){
    pane.setMinSize(UI.APP_WIDTH, UI.INFO_CENTER_HEIGHT);
    pane.setTranslateX(UI.APP_WIDTH /2);
    pane.setTranslateY(UI.INFO_CENTER_HEIGHT / 2);
      
    message = new Label("Tic-Tac-Toe");
    message.setMinSize(UI.APP_WIDTH, UI.INFO_CENTER_HEIGHT);
    message.setFont(Font.font(24));
    message.setAlignment(Pos.CENTER);
    message.setTranslateY(-20);
    pane.getChildren().add(message);

    startButton = new Button("New Game");
    startButton.setMinSize(135,30);
    startButton.setTranslateY(20);
    pane.getChildren().add(startButton);
  }
  
  public StackPane getStackPane(){
    return pane;
  }

  public void setStartButtonOnAction(EventHandler<ActionEvent> onAction){
    startButton.setOnAction(onAction);
  }
  public void updateMessage (String msgP) {
    message.setText(msgP);
  }
  public void updateButton (String msgP) {
    startButton.setText(msgP);
  }
  
}