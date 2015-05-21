package cs.view;


import java.sql.*;
import java.util.Calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import cs.MainController;
import cs.SQLConnect;
import cs.SQLSearch;
import cs.constant.Translate;
import cs.model.KeepsEquipment;

/**
*
* @author Jiyu Xiao
*/
public class EquipmentViewController {

	@FXML
	private ChoiceBox<String> loc;
	
	@FXML
	private ChoiceBox<String> loc2;
	
	@FXML
	private ChoiceBox<String> eq;
	
	@FXML
	private TextField quant;
	
	@FXML
	private PasswordField pwd;

	@FXML
	private Button check;
	
	@FXML
	private Button checktomorrow;
	
	@FXML
	private Button update;
	
	@FXML
	private Button init;
	
	@FXML
	private TableView<KeepsEquipment> tb;
	
	@FXML
	private TableColumn<KeepsEquipment, String> branch;
	
	@FXML
	private TableColumn<KeepsEquipment, String> equipment;
	
	@FXML
	private TableColumn<KeepsEquipment, Integer> quantity;
	
	private ObservableList<KeepsEquipment> result;
	private ObservableList<String> branchrecord;
	private ObservableList<String> equiprecord;
	
	public EquipmentViewController(){
		result = FXCollections.observableArrayList();
	}
	
	@FXML
	public void initialize(){
		branchrecord = SQLSearch.getBranchList();
		equiprecord = SQLSearch.getAllEquipmentName();
		loc.setItems(branchrecord);
		loc2.setItems(branchrecord);
		eq.setItems(equiprecord);
		loc2.getSelectionModel().selectFirst();
		eq.getSelectionModel().selectFirst();
		branch.setCellValueFactory(new PropertyValueFactory<KeepsEquipment, String>("branch"));
		equipment.setCellValueFactory(new PropertyValueFactory<KeepsEquipment, String>("equipment"));
		quantity.setCellValueFactory(new PropertyValueFactory<KeepsEquipment, Integer>("quantity"));
	}
	
	@FXML
	public void check(){
		result.clear();
		
		ResultSet rs;
		
		try
		{
			if( loc.getSelectionModel().getSelectedIndex() >= 0 ){
				PreparedStatement ps;
				
				/* query that select the equipments that are kept for a particular branch*/
				ps = SQLConnect.getcon().prepareStatement("SELECT * from KEEPSEQUIPMENT where bid = ?");
				ps.setInt(1, Translate.SToI(loc.getSelectionModel().getSelectedItem()));
				
				rs = ps.executeQuery();

				
				while(rs.next()) {
					KeepsEquipment ke = new KeepsEquipment();
					ke.setBranch(branchrecord.get(rs.getInt("bid") - 1));
					ke.setEquipment(rs.getString("ename"));
					ke.setQuantity(rs.getInt("quantity"));
					
					result.add(ke);
				}
				
				ps.close();
			}
			else {
				Statement stmt;
				
				stmt = SQLConnect.getcon().createStatement();
				/* query that select the equipments that are kept for all branches*/
				rs = stmt.executeQuery("SELECT * from KEEPSEQUIPMENT ORDER BY bid"); 
				
				while(rs.next()) {
					KeepsEquipment ke = new KeepsEquipment();
					ke.setBranch(branchrecord.get(rs.getInt("bid") - 1));
					ke.setEquipment(rs.getString("ename"));
					ke.setQuantity(rs.getInt("quantity"));
					
					result.add(ke);
				}
			}

			
		} 	catch (SQLException ex) {
			MainController.showWarningDialog(ex.getMessage());
		}
		
		if(!result.isEmpty())
			tb.setItems(result);
	}
	
	@FXML
	public void update(){
		if( ! Translate.SValid(quant.getText()) || quant.getText().length() == 0 ) MainController.showWarningDialog("Please give an non-negative integer in Quantity field!");
		else {
			int newquantity = Translate.SToI(quant.getText());

			PreparedStatement ps, ps2;
			ResultSet rs;
			
			try
			{
				/* query that select a particular equipment that are kept for a particular branch*/
				ps = SQLConnect.getcon().prepareStatement("SELECT * from KEEPSEQUIPMENT where bid = ? AND ename = ?");
				ps.setInt(1, Translate.SToI(loc2.getSelectionModel().getSelectedItem()));
				ps.setString(2, eq.getSelectionModel().getSelectedItem());
					
				rs = ps.executeQuery();

				if(rs.next()) {	
					/* query that update the quantity info for a particular equipment and a particular branch*/
					ps2 = SQLConnect.getcon().prepareStatement("Update KEEPSEQUIPMENT set quantity = ? where bid = ? AND ename = ?");
					ps2.setInt(1, newquantity);
					ps2.setInt(2, Translate.SToI(loc2.getSelectionModel().getSelectedItem()));
					ps2.setString(3, eq.getSelectionModel().getSelectedItem());
					ps2.executeUpdate();
					SQLConnect.getcon().commit();
				}
				else {
					/* query that insert a new record for a particular equipment and a particular branch*/
					ps2 = SQLConnect.getcon().prepareStatement("Insert into KEEPSEQUIPMENT values (?,?,?)");
					ps2.setString(1, eq.getSelectionModel().getSelectedItem());
					ps2.setInt(2, Translate.SToI(loc2.getSelectionModel().getSelectedItem()));
					ps2.setInt(3, newquantity);
					ps2.executeUpdate();
					SQLConnect.getcon().commit();
				}
					
					ps.close();
					ps2.close();
				
			} 	catch (SQLException ex) {
				MainController.showWarningDialog(ex.getMessage());
			}
			
			check();
		}
		
	}
	
	@FXML
	public void init(){
		String txt = pwd.getText();
		if(txt.equals("jyxiao123456")){
			
			PreparedStatement ps;
			
			try {
			
				for(String eq : equiprecord){
					for (String b : branchrecord) {
						ps = SQLConnect.getcon().prepareStatement("INSERT INTO KEEPSEQUIPMENT VALUES (?,?,?)"); 
						ps.setString(1, eq);
						ps.setInt(2, Translate.SToI(b));
						ps.setInt(3, 20);
						ps.executeUpdate();
						SQLConnect.getcon().commit();
						ps.close();
					}
				}
			} catch (SQLException ex){
				MainController.showWarningDialog(ex.getMessage());
			}
			
			check();
			
		}
		else {
			MainController.showWarningDialog("Invalid password!");
		}
	}
	
	@FXML
	public void checkTomorrow(){
		result.clear();
		
		java.sql.Date dt = new java.sql.Date(Calendar.getInstance().getTimeInMillis() + 24*60*60*1000);
		
		PreparedStatement ps;
		ResultSet rs;
		
		try
		{
			if( loc.getSelectionModel().getSelectedIndex() >= 0 ){
				
				/* query that shows equipment requests for all branch in next day*/
				ps = SQLConnect.getcon().prepareStatement("SELECT R.bid, RE.ename, count(*) as quantity from RESERVATION R"
						+ " join RESERVEDEQUIPMENT RE where RE.resid = R.resid AND Date(R.startdate) = ? AND R.bid = ? group by R.bid, RE.ename");
				ps.setDate(1, dt);
				ps.setInt(2, Translate.SToI(loc.getSelectionModel().getSelectedItem()));

			}
			else {
				/* query that shows equipment requests for a particular branch in next day*/
				ps = SQLConnect.getcon().prepareStatement("SELECT R.bid, RE.ename, count(*) as quantity from RESERVATION R"
						+ " join RESERVEDEQUIPMENT RE where RE.resid = R.resid AND Date(R.startdate) = ? group by R.bid, RE.ename");
				ps.setDate(1, dt);

			}
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				KeepsEquipment ke = new KeepsEquipment();
				ke.setBranch(branchrecord.get(rs.getInt("bid") - 1));
				ke.setEquipment(rs.getString("ename"));
				ke.setQuantity(rs.getInt("quantity"));
				
				result.add(ke);
			}

			ps.close();
			
		} 	catch (SQLException ex) {
			MainController.showWarningDialog(ex.getMessage());
		}
		
		if(!result.isEmpty())
			tb.setItems(result);
	}
}
