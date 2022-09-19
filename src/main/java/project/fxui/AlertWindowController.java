package project.fxui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AlertWindowController {
	
	public Button yes;
	public Button no;
	public Button ok;
	public Button go;
	public Button cancel;
	
	public Stage window = new Stage();
	public Label label;
	public VBox layout = new VBox(10);	
	public Scene scene;
	
	public AlertWindowController(String type, String labeltext) {
		label = new Label(labeltext);
		if (type == "ok") {
			scene = new Scene(layout);
			ok = new Button("OK");
			setMessageProperties();
		}
		else if (type == "yesno") {
			scene = new Scene(layout);
			yes = new Button("Yes");
			no = new Button("No");
			setYesNoProperties();
		}
	}
	
	public void setMessageProperties() {
		window.setTitle("Alert");
		window.setMinWidth(600);
		window.setMinHeight(150);
		layout.setAlignment(Pos.CENTER);
		window.setScene(scene);
	}
	
	public void setYesNoProperties() {
		window.setTitle("Are you sure?");
		window.setMinWidth(400);
		window.setMinHeight(150);
		layout.setAlignment(Pos.CENTER);
		window.setScene(scene);
		no.setOnAction(e -> window.close());
	}
}
