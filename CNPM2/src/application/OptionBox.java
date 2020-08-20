package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

public class OptionBox extends Popup{
	private VBox layout;
	private int count;
	private Button[] boxes;
		public OptionBox() {
			layout=new VBox();
			layout.setPrefWidth(300);
			layout.setMaxWidth(300);
		
			
			this.setAutoHide(true);
			this.getContent().add(layout);
			layout.setPadding(new Insets(1,1,2,1));
			layout.setStyle("-fx-background-color:#9c9c9c");
			layout.getStylesheets().add(this.getClass().getResource("option.css").toExternalForm());
		}
		
	public void create(int box_count) {
		layout.getChildren().clear();
		
		this.count=box_count;
		boxes=new Button[box_count];
		
		for(int i=0;i<box_count;i++) {
			Button button=new Button();
			button.setAlignment(Pos.CENTER_LEFT);
			button.setPrefWidth(300);
			layout.getChildren().add(button);
			boxes[i]=button;
		}
	}
	public Button getBox(int index) {
		if(index<0||index>=count) return null;
		else return boxes[index];
	}
}
