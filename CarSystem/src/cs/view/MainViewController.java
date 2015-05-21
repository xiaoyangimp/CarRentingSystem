package cs.view;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import cs.MainController;
import cs.SQLConnect;

/**
*
* @author Jiyu Xiao
*/
public class MainViewController {
	
	@FXML
	private Group leftgroup;
	
	@FXML
	private Group rightgroup;
	
	@FXML
	private Label user = new Label("user");
	
	@FXML
	private Label pos = new Label("pos");
	
	@FXML
	private Button logout;
	
	@FXML
	private ImageView iv;
	
	private MainController mainc;
	
	public MainViewController(){
		
	}
	
	public void setParam(String uid, String position){
		user.setText(uid);
		pos.setText(position);
	}
	
	public MainController getMain(){
		return mainc;
	}
	
	public void setMain(MainController main){
		this.mainc = main;
	}
	
	public void setLeft(AnchorPane left) {
		leftgroup.getChildren().add(left);
	}
	
	public void setRight(AnchorPane right){
		rightgroup.getChildren().clear();
		rightgroup.getChildren().add(right);
	}
	
	@FXML
	public void close(){
		try{
			SQLConnect.getcon().close();
		} catch (SQLException ex) {
			
			MainController.showWarningDialog(ex.getMessage());
		}
		
		MainController.showWarningDialog("Good Bye!");
		mainc.getPrimaryStage().close();
		mainc.getStart().showLoginView();
	}
}
