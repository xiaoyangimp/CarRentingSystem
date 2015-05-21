package cs.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
*
* @author Jiyu Xiao
*/
public class WarningDialogController {
	
	@FXML
	private Label info;
	
	@FXML
	private Button ok;
	
	private Stage stage; 
	
	public WarningDialogController(){
	}
	
	public void setText(String toshow){
		info.setText(toshow);
	}
	
	public void setStage(Stage s){
		this.stage = s;
	}

	@FXML
	public void click(){
		stage.close();
	}
	
}
