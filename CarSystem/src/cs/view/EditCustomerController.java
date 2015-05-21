/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.view;


import java.sql.*;

import cs.MainController;
import cs.SQLConnect;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Jiyu Xiao
 */
public class EditCustomerController {

   @FXML
   private TextField phonenum;
   
   @FXML
   private TextField name;
   
   @FXML
   private TextField address;
   
   @FXML
   private TextField city;
   
   @FXML
   private CheckBox sm;
   
   @FXML
   private Label info;
   
   @FXML
   private Button check;
   
   @FXML
   private Button add;
   
   @FXML
   private Button update;
   
   public EditCustomerController(){

   }
   
   @FXML
   public void initialize(){
	   add.setDisable(true);
	   update.setDisable(true);
   }
   
   
   
   @FXML
   public void check(){
	   String phnum = phonenum.getText();
	   
	   if(phnum.length() == 0) {
		   MainController.showWarningDialog("Please provide an non empty phone number");
		   return;
	   }
	   
	   Statement stmt, stm; 
	   ResultSet rs, rs1;
	   
	   try{
		   stmt = SQLConnect.getcon().createStatement();
		   /* query that read the customer info*/
		   rs = stmt.executeQuery("SELECT * FROM CUSTOMER where phonenum = " + phnum);
		   phonenum.setDisable(true);
		   
		   if(rs.next()){
			   name.setText(rs.getString("name"));
			   address.setText(rs.getString("address"));
			   city.setText(rs.getString("city"));
			   
			   stm = SQLConnect.getcon().createStatement();
			   /* query that read the super member info*/
			   rs1 = stm.executeQuery("SELECT * FROM SUPERMEMBER where phonenum = " + phnum);
			   
			   if(rs1.next()){
				   sm.setSelected(true);
				   sm.setDisable(true);
				   info.setText("The current customer's point is " + rs1.getInt("points") + ".");
			   }
			   
			   update.setDisable(false);
		   } else {
			   info.setText("This is a new customer!");
			   add.setDisable(false);
		   }
	   } catch (SQLException ex){
		   MainController.showWarningDialog(ex.getMessage());
	   }

		
		
   }
   
   @FXML
   public void add(){
	   String phnum = phonenum.getText();
	   String n = name.getText();
	   String addr = address.getText();
	   String c = city.getText();
	   
	   if(phnum.length() == 0) {
		   MainController.showWarningDialog("Please provide an non empty phone number");
		   return;
	   }
	   
	   if(n.length() == 0) {
		   MainController.showWarningDialog("Please provide an non empty name");
		   return;
	   }
	   
	   if(addr.length() == 0) {
		   MainController.showWarningDialog("Please provide an non empty address");
		   return;
	   }
	   
	   if(c.length() == 0) {
		   MainController.showWarningDialog("Please provide an non empty city");
		   return;
	   }
	   
	   PreparedStatement ps;
	   
	   try{
		   /* query that add a new customer*/
		   ps = SQLConnect.getcon().prepareStatement("Insert into CUSTOMER values (?,?,?,?)");
		   ps.setString(1, phnum);
		   ps.setString(2, n);
		   ps.setString(3, addr);
		   ps.setString(4, c);
		   ps.executeUpdate();
		   SQLConnect.getcon().commit();
		   ps.close();
		   
		   if(sm.isSelected()){
			   /* query that add a new super member*/
			  ps = SQLConnect.getcon().prepareStatement("Insert into SUPERMEMBER values (?,?)");
			  ps.setString(1, phnum);
			  ps.setInt(2, 500);
			  ps.executeUpdate();
			  SQLConnect.getcon().commit();
			  ps.close();
			  
		   }
		   
		   info.setText("The customer record is added successfully");
		   add.setDisable(true);
	   } catch (SQLException ex){
		   MainController.showWarningDialog(ex.getMessage());
	   }
   }
   
   @FXML
   public void update(){
	   String phnum = phonenum.getText();
	   String n = name.getText();
	   String addr = address.getText();
	   String c = city.getText();
	   
	   if(phnum.length() == 0) {
		   MainController.showWarningDialog("Please provide an non empty phone number");
		   return;
	   }
	   
	   if(n.length() == 0) {
		   MainController.showWarningDialog("Please provide an non empty name");
		   return;
	   }
	   
	   if(addr.length() == 0) {
		   MainController.showWarningDialog("Please provide an non empty address");
		   return;
	   }
	   
	   if(c.length() == 0) {
		   MainController.showWarningDialog("Please provide an non empty city");
		   return;
	   }
	   
	   PreparedStatement ps;
	   
	   try{
		   /* query that update a customer's info*/
		   ps = SQLConnect.getcon().prepareStatement("Update CUSTOMER set name = ?, address = ?, city = ? where phonenum = ?");
		   ps.setString(1, n);
		   ps.setString(2, addr);
		   ps.setString(3, c);
		   ps.setString(4, phnum);
		   ps.executeUpdate();
		   SQLConnect.getcon().commit();
		   ps.close();
		   
		   if(! sm.isDisabled() && sm.isSelected()){
			   /* query that update a customer as super member*/
			   ps = SQLConnect.getcon().prepareStatement("Insert into SUPERMEMBER values (?,?)");
			   ps.setString(1, phnum);
			   ps.setInt(2, 500);
			   ps.executeUpdate();
			   SQLConnect.getcon().commit();
			   ps.close();
			  
			   info.setText("The customer is successfully registered as super member.");
		   } else {
			   info.setText("The customer information is updated");
		   }
		   
		   update.setDisable(true);
	   } catch (SQLException ex){
		   MainController.showWarningDialog(ex.getMessage());
	   }
   }
}
