package cs.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import cs.constant.CommonC;
import cs.MainController;
import cs.SQLConnect;
import cs.SQLSearch;
import cs.model.Equipment;
import cs.constant.Translate;
import cs.model.NewRent;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;
import java.io.IOException;
import java.sql.*;

/**
 * FXML Controller class
 *
 * @author Jiyu Xiao, IshmeetSingh
 */
public class NewRentViewController {
	@FXML 
	private ChoiceBox<String> address = new ChoiceBox<String>();
	
	@FXML
	private ChoiceBox<String> type = new ChoiceBox<String>();
	
	@FXML
	private ChoiceBox<String> category = new ChoiceBox<String>();
	
	@FXML
	private Button gettype;
	
	@FXML
	private DatePicker pickupdate;
	
	@FXML
	private DatePicker returndate;
	
	@FXML
	private ChoiceBox<Integer> pickuphour;
	
	@FXML
	private ChoiceBox<Integer> returnhour;
	
	@FXML
	private Button check;
        
        	
	@FXML
	private Label DBresult;
	
	@FXML
	private TableView<Equipment> option;
	
	@FXML
	private TableColumn<Equipment, String> ename;
	
	@FXML
	private Button confirm;
	
	private NewRent res = new NewRent();
	private MainViewController mainvc;

	
	public NewRentViewController(){
	}
	
	@FXML
	public void initialize() {
		address.setItems(SQLSearch.getBranchList());
		category.setItems(SQLSearch.getCategoryList());
		pickuphour.setItems(CommonC.Hour);
		returnhour.setItems(CommonC.Hour);
		confirm.setDisable(true); // can not confirm if transaction is not checked
		check.setDisable(true);
		
		/* set all choicebox select first column*/
		address.getSelectionModel().selectFirst();
		category.getSelectionModel().selectFirst();
		pickupdate.setValue(LocalDate.now());
		returndate.setValue(LocalDate.now());	
		pickuphour.getSelectionModel().selectFirst();
		returnhour.getSelectionModel().selectFirst();
		ename.setCellValueFactory(new PropertyValueFactory<Equipment, String>("name"));
	}
	
	@FXML
	protected void gettype(){
		String cat = category.getSelectionModel().getSelectedItem();
		System.out.println(cat);
		type.setItems(SQLSearch.getVehicleTypeList(Translate.SToI(cat)));
		type.getSelectionModel().selectFirst();
		check.setDisable(false);
	}
	
	@FXML
	protected void check(){
		
		int bid;
		String bids;
		String vtype;
		PreparedStatement ps;
		ResultSet rs;
		
		bids = address.getSelectionModel().getSelectedItem().toString();
		bid = Translate.SToI(bids);
		vtype = type.getSelectionModel().getSelectedItem().toString();
		
		LocalDate pdate = pickupdate.getValue();
		LocalDate rdate = returndate.getValue();
		int phour = pickuphour.getValue();
		int rhour = returnhour.getValue();
		
		if(pdate.isBefore(LocalDate.now()) || rdate.isBefore(LocalDate.now())) {
			DBresult.setText("Invalid time period");
			return ;
		}
		
		if( pdate.isAfter(rdate) || ( pdate.isEqual(rdate) && phour >= rhour ) ) {
			DBresult.setText("Invalid time period");
			return ;
		}
		
		Calendar pickcal = Calendar.getInstance();
		pickcal.clear();
		pickcal.set(Calendar.YEAR, pdate.getYear());
		pickcal.set(Calendar.MONTH, pdate.getMonthValue() - 1);
		pickcal.set(Calendar.DAY_OF_MONTH, pdate.getDayOfMonth());
		pickcal.set(Calendar.HOUR_OF_DAY, phour);
//		pickcal.set(Calendar.MINUTE, 0);
//		pickcal.set(Calendar.SECOND, 0);
//		pickcal.set(Calendar.MILLISECOND, 0);
		
		Calendar returncal = Calendar.getInstance();
		returncal.clear();
		returncal.set(Calendar.YEAR, rdate.getYear());
		returncal.set(Calendar.MONTH, rdate.getMonthValue() - 1);
		returncal.set(Calendar.DAY_OF_MONTH, rdate.getDayOfMonth());
		returncal.set(Calendar.HOUR_OF_DAY, rhour);
//		returncal.set(Calendar.MINUTE, 0);
//		returncal.set(Calendar.SECOND, 0);
//		returncal.set(Calendar.MILLISECOND, 0);
		
		res.setStartdate(pickcal);
		res.setReturndate(returncal);
		
		Timestamp st = new Timestamp(pickcal.getTimeInMillis());
		Timestamp rt = new Timestamp(returncal.getTimeInMillis());
		
		try{
			ps = SQLConnect.getcon().prepareStatement("SELECT V.vid FROM VEHICLE V where (V.type = ? AND "
					+ "V.bid = ? AND V.status = 1 AND V.vid not in "
					+ "(select R.vid from RESERVATION R where (R.startdate <= ? AND R.returndate >= ?) OR "
					+ "(R.startdate <= ? AND R.returndate >= ?) OR (R.startdate >= ? AND R.returndate <= ?)) "
					+ "AND V.vid not in (select RT.vid from RENTAL RT where RT.expectedreturn >= ?))");
			ps.setString(1, vtype);
			ps.setInt(2, bid);
			ps.setTimestamp(3, st);
			ps.setTimestamp(4, st);
			ps.setTimestamp(5, rt);
			ps.setTimestamp(6, rt);
			ps.setTimestamp(7, st);
			ps.setTimestamp(8, rt);
			ps.setTimestamp(9, st);
			
			rs = ps.executeQuery();
			
			if( ! rs.next() ) {
				DBresult.setText("No vehicle is available now on that location.");
			}
			else {
				res.setVid(rs.getString("vid"));
				res.setBid(bid);
				
				DBresult.setText("Vehicle " + res.getVid() + " is available at location " + bid + ".");
				confirm.setDisable(false); // now can confirm
				address.setDisable(true); // can not change parameters
				type.setDisable(true);
				gettype.setDisable(true);
				category.setDisable(true);
				
				option.setItems(SQLSearch.getAdditionalEquipment(res.getVid()));
				
				option.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
		
	}
	
	@FXML
	protected void confirm(){
		
		Set<Equipment> opt = new HashSet<Equipment>(option.getSelectionModel().getSelectedItems());
		res.setequip(opt);
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainController.class.getResource("view/NewRentInformationView.fxml"));
			AnchorPane info = (AnchorPane) loader.load();
		
			NewRentInformationViewController con = loader.getController();
                        
			con.setNewRent(this.res, type.getSelectionModel().getSelectedItem().toString());
			mainvc.setRight(info);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void setMainVC(MainViewController m) {
		this.mainvc = m;
	}
}
