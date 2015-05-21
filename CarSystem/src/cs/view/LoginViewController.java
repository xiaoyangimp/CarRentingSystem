package cs.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import cs.SuperRentStart;

/**
*
* @author Jiyu Xiao
*/
public class LoginViewController {
	@FXML
	private TextField uid;
	
	@FXML
	private PasswordField pwd;
	
	@FXML
	private Button Login;
	
	@FXML
	private ImageView iv;
	
	private SuperRentStart mainApp;
	
	public LoginViewController() {
	}
	
	public void setStart(SuperRentStart main) {
		this.mainApp = main;
	}
	

	
	@FXML
	protected void submit(){
		String temp1 = uid.getText();
		String temp2 = pwd.getText();
		mainApp.login(temp1, temp2);
	}
}
