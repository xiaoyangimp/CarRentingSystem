package cs.view;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import cs.MainController;
import cs.SQLConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
*
* @author Jiyu Xiao
*/
public class UserManageViewController {
	
	@FXML
	private TextField addid;
	
	@FXML
	private TextField addpwd;
	
	@FXML
	private TextField removeid;
	
	@FXML
	private TextField changeid;
	
	@FXML
	private TextField changepwd;
	
	@FXML
	private ChoiceBox<String> addtype;
	
	@FXML
	private Button add;
	
	@FXML
	private Button remove;
	
	@FXML
	private Button change;
	
	
	public UserManageViewController(){

	}
	
	@FXML
	public void initialize(){
		ObservableList<String> usertype = FXCollections.observableArrayList("clerk", "manage");
		addtype.setItems(usertype);
		addtype.getSelectionModel().selectFirst();
	}
	
	@FXML
	public void addUser(){
		PreparedStatement ps;
		
		String id = addid.getText();
		String pwd = addpwd.getText();
		String position = addtype.getSelectionModel().getSelectedItem().substring(0, 3);
		
		if(id == null || pwd == null || id.length() == 0 || pwd.length() == 0) {
			MainController.showWarningDialog("Please give an none empty id or password.");
			return;
		}
		
		try{
			/* add a new employee record*/
			ps = SQLConnect.getcon().prepareStatement("INSERT into EMPLOYEE values (?,?,?)");
			ps.setString(1, id);
			ps.setString(2, pwd);
			ps.setString(3, position);
			
			ps.executeUpdate();
			SQLConnect.getcon().commit();
			ps.close();
			
			MainController.showWarningDialog("Add user " + id + " successfully.");

		} catch (SQLException ex) {
			MainController.showWarningDialog(ex.getMessage());
		}
	}
	
	@FXML
	public void removeUser(){
		PreparedStatement ps;
		
		String id = removeid.getText();
		
		if(id == null || id.length() == 0) {
			MainController.showWarningDialog("Please give an none empty id");
			return;
		}
		
		try{
			/* delete a employee record*/
			ps = SQLConnect.getcon().prepareStatement("Delete from EMPLOYEE where empid = ?");
			ps.setString(1, id);
			
			ps.executeUpdate();
			SQLConnect.getcon().commit();
			ps.close();
			
			MainController.showWarningDialog("Delete user " + id + " successfully.");
			
		} catch (SQLException ex) {
			MainController.showWarningDialog(ex.getMessage());
		}
	}
	
	@FXML
	public void changePwd(){
		PreparedStatement ps;
		
		String id = changeid.getText();
		String pwd = changepwd.getText();
		
		if(id == null || pwd == null || id.length() == 0 || pwd.length() == 0) {
			MainController.showWarningDialog("Please give an none empty id or password.");
			return;
		}
		
		try{
			/* update a employee record*/
			ps = SQLConnect.getcon().prepareStatement("Update EMPLOYEE set password = ? where empid = ?");
			ps.setString(1, pwd);
			ps.setString(2, id);

			
			ps.executeUpdate();
			SQLConnect.getcon().commit();
			ps.close();

			MainController.showWarningDialog("Update user " + id + " password successfully.");
			
		} catch (SQLException ex) {
			MainController.showWarningDialog(ex.getMessage());
		}
	}
}
