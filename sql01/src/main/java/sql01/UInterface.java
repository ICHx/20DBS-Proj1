package sql01;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class UInterface extends Application{
public void start(Stage primaryStage) {
    		//Layout
		GridPane grid = new GridPane(); //Grid排版
		grid.setAlignment(Pos.CENTER); //Layout
		grid.setHgap(10); //水平距離
		grid.setVgap(10); //垂直距離
	 
		grid.setPadding(new Insets(25, 25, 25, 25)); //填充邊界。Insets定義上、右、下、左四個方向的長度。
	 
		//用Layout作為Scene的root node
		Scene scene = new Scene(grid, 300, 250);
		primaryStage.setScene(scene);
	 
		//GUI元件
		Text scenetitle = new Text("歡迎");
		scenetitle.setFont(Font.font("Serif", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1); //2,1是指這個元件要佔用2格的column和1格的row
	 
		Label userName = new Label("帳號：");
		grid.add(userName, 0, 1);
	 
		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);
	
	 
		Button btn = new Button("登入");
		HBox hbBtn = new HBox(btn); //以新的HBox Layout來對Button進行排版，目的是為了讓Button在GridPane 1,4的位置中可以靠到最右邊。
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		grid.add(hbBtn, 1, 4);
	 
		//登入事件
		final Text actiontarget = new Text(); //顯示結果的Text
		grid.add(actiontarget, 1, 6);
		btn.setOnAction(e -> { //用lambda語法省略實作EventHandler介面
		    actiontarget.setText("登入成功！");
		});
	 
		//讀取與設定CSS
		scene.getStylesheets().add(UInterface.class.getResource("UInterface.css").toExternalForm());
		scenetitle.setId("welcome-text");
		actiontarget.setId("actiontarget");
		
		//顯示Stage
		primaryStage.setTitle("登入");
		primaryStage.show();
}    
}
    

