package cs;

import java.io.IOException;

import cs.model.Employee;
import cs.view.LoginViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.*;

/**
*
* @author Jiyu Xiao
*/
public class SuperRentStart extends Application {
	
	private Stage primaryStage;
	private Employee tempRecord = new Employee();
	
	
	public SuperRentStart(){

	}
	
	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("SuperRent Login");
		
		showLoginView();
	}
	
	/**
	 * Function show the login user interface
	 */
	public void showLoginView() {
		try {
			// Load Login View
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(SuperRentStart.class.getResource("view/LoginView.fxml"));
			GridPane loginView = (GridPane) loader.load();

			Scene scene = new Scene(loginView);
			LoginViewController control = loader.getController();
			control.setStart(this);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
		
	/**
	 * Login function that connect to the database and search the 
	 * user table for validation
	 * 
	 * @param uid user's id
	 * @param pwd user's password
	 */
	public void login(String uid, String pwd) {
		if(SQLConnect.connect()){
			System.out.println("Connect OK!");
		} 
		else {
			MainController.showWarningDialog("Server error!");
		}
		
		tempRecord.setuserid(uid);
		tempRecord.setpassword(pwd);
		PreparedStatement ps;
		ResultSet rs;

		try {
				ps = SQLConnect.getcon().prepareStatement("SELECT * FROM EMPLOYEE WHERE empid = ? AND password = ?");
				ps.setString(1, uid);
				ps.setString(2, pwd);
				
				rs = ps.executeQuery();
				
				if( ! rs.next() ) {
					MainController.showWarningDialog("Invalid userid or password!");
				} else {
					tempRecord.setposition(rs.getString("position")); // get the user's position
		
					primaryStage.close();
					MainController m = new MainController();
					m.showGUI(this.tempRecord);
					m.setStart(this);
				}
				
				ps.close();
				
				
		} catch(SQLException ex) {
				MainController.showWarningDialog(ex.getMessage());
		}

	}
	
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
