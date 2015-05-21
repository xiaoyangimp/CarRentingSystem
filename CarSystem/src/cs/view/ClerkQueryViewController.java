package cs.view;

import java.sql.*;
import java.time.LocalDate;
import java.util.Calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import cs.MainController;
import cs.SQLConnect;
import cs.SQLSearch;
import cs.constant.Translate;
import cs.model.Vehicle;

/**
*
* @author Jiyu Xiao
*/
public class ClerkQueryViewController {
	@FXML
	private ChoiceBox<String> category;
	
	@FXML
	private ChoiceBox<String> loc;
	
	@FXML
	private DatePicker from;
	
	@FXML
	private DatePicker to;
	
	@FXML
	private Button checkavailable;
	
	@FXML
	private Button checkoverdue;
	
	@FXML
	private Button checkforsale;
	
	@FXML
	private TableView<Vehicle> tb;
	
	@FXML
	private TableColumn<Vehicle, String> vehicleid;
	
	@FXML
	private TableColumn<Vehicle, String> branch;
	
	@FXML
	private TableColumn<Vehicle, String> type;
	
	@FXML
	private TableColumn<Vehicle, Integer> odoread;
	
	@FXML
	private TableColumn<Vehicle, String> adate;
	
	@FXML
	private TableColumn<Vehicle, Double> saleprice;

	
	private ObservableList<Vehicle> result;
	private ObservableList<String> branchrecord;
	
	public ClerkQueryViewController(){
		result = FXCollections.observableArrayList();
	}
	
	@FXML
	public void initialize(){
		category.setItems(SQLSearch.getVehicleTypeList(0));
		branchrecord = SQLSearch.getBranchList();
		loc.setItems(branchrecord);
		from.setValue(LocalDate.now());
		to.setValue(LocalDate.now());
		vehicleid.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vid"));
		branch.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("branch"));
		type.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("type"));
		odoread.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("odoread"));
		adate.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("acquiredate"));
		saleprice.setCellValueFactory(new PropertyValueFactory<Vehicle, Double>("price"));
	}
		
	
	@FXML
	public void checkavail(){
		result.clear();
		
		adate.setText("Acquire Date");
		odoread.setText("Odormeter Read");
		
		
		int test = checkcondition();
		
		PreparedStatement ps;
		ResultSet rs;
			
		LocalDate sd = from.getValue();
		LocalDate rd = to.getValue();
			
		Calendar scal = Calendar.getInstance();
		scal.clear();
		scal.set(Calendar.YEAR, sd.getYear());
		scal.set(Calendar.MONTH, sd.getMonthValue() - 1);
		scal.set(Calendar.DAY_OF_MONTH, sd.getDayOfMonth());

			
		Calendar rcal = Calendar.getInstance();
		rcal.clear();
		rcal.set(Calendar.YEAR, rd.getYear());
		rcal.set(Calendar.MONTH, rd.getMonthValue() - 1);
		rcal.set(Calendar.DAY_OF_MONTH, rd.getDayOfMonth());
			
		Timestamp st = new Timestamp(scal.getTimeInMillis());
		Timestamp rt = new Timestamp(rcal.getTimeInMillis());

		
		try{
			if(test == 0) {
				/* query that search the available vehicle from database during a particular time period*/
				ps = SQLConnect.getcon().prepareStatement("SELECT * FROM VEHICLE V where ("
						+ " V.status = 1 AND V.vid not in "
						+ "(select R.vid from RESERVATION R where (R.startdate <= ? AND R.returndate >= ?) OR "
						+ "(R.startdate <= ? AND R.returndate >= ?) OR (R.startdate >= ? AND R.returndate <= ?)) "
						+ "AND V.vid not in (select RT.vid from RENTAL RT where RT.expectedreturn >= ?)) Order by V.bid, V.type");
					ps.setTimestamp(1, st);
					ps.setTimestamp(2, st);
					ps.setTimestamp(3, rt);
					ps.setTimestamp(4, rt);
					ps.setTimestamp(5, st);
					ps.setTimestamp(6, rt);
					ps.setTimestamp(7, st);
			}
			else if(test == 1) {
				/* query that search the available vehicle from database during a particular time period order by branch id for a particular type*/
				ps = SQLConnect.getcon().prepareStatement("SELECT * FROM VEHICLE V where (V.type = ? "
						+ "AND V.status = 1 AND V.vid not in "
						+ "(select R.vid from RESERVATION R where (R.startdate <= ? AND R.returndate >= ?) OR "
						+ "(R.startdate <= ? AND R.returndate >= ?) OR (R.startdate >= ? AND R.returndate <= ?)) "
						+ "AND V.vid not in (select RT.vid from RENTAL RT where RT.expectedreturn >= ?)) Order by V.bid");
					ps.setString(1, category.getSelectionModel().getSelectedItem());
					ps.setTimestamp(2, st);
					ps.setTimestamp(3, st);
					ps.setTimestamp(4, rt);
					ps.setTimestamp(5, rt);
					ps.setTimestamp(6, st);
					ps.setTimestamp(7, rt);
					ps.setTimestamp(8, st);
			}
			else if (test == 2){
				/* query that search the available vehicle from database during a particular time period order by type for a particular branch*/
				ps = SQLConnect.getcon().prepareStatement("SELECT * FROM VEHICLE V where ("
						+ "V.bid = ? AND V.status = 1 AND V.vid not in "
						+ "(select R.vid from RESERVATION R where (R.startdate <= ? AND R.returndate >= ?) OR "
						+ "(R.startdate <= ? AND R.returndate >= ?) OR (R.startdate >= ? AND R.returndate <= ?)) "
						+ "AND V.vid not in (select RT.vid from RENTAL RT where RT.expectedreturn >= ?)) Order by V.type");
					ps.setInt(1, Translate.SToI(loc.getSelectionModel().getSelectedItem()));
					ps.setTimestamp(2, st);
					ps.setTimestamp(3, st);
					ps.setTimestamp(4, rt);
					ps.setTimestamp(5, rt);
					ps.setTimestamp(6, st);
					ps.setTimestamp(7, rt);
					ps.setTimestamp(8, st);
			}
			else {
				/* query that search the available vehicle from database during a particular time period for a particular type and branch*/
				ps = SQLConnect.getcon().prepareStatement("SELECT * FROM VEHICLE V where (V.type = ? AND "
						+ "V.bid = ? AND V.status = 1 AND V.vid not in "
						+ "(select R.vid from RESERVATION R where (R.startdate <= ? AND R.returndate >= ?) OR "
						+ "(R.startdate <= ? AND R.returndate >= ?) OR (R.startdate >= ? AND R.returndate <= ?)) "
						+ "AND V.vid not in (select RT.vid from RENTAL RT where RT.expectedreturn >= ?))");
					ps.setString(1, category.getSelectionModel().getSelectedItem());
					ps.setInt(2, Translate.SToI(loc.getSelectionModel().getSelectedItem()));
					ps.setTimestamp(3, st);
					ps.setTimestamp(4, st);
					ps.setTimestamp(5, rt);
					ps.setTimestamp(6, rt);
					ps.setTimestamp(7, st);
					ps.setTimestamp(8, rt);
					ps.setTimestamp(9, st);
			}
			
			rs = ps.executeQuery();
			
			while( rs.next() ) {
				Vehicle v = new Vehicle();
				Calendar temp = Calendar.getInstance();
				temp.clear();
				
				temp.setTimeInMillis( rs.getDate("acquiredate").getTime() );
				v.setVid(rs.getString("vid"));
				v.setBranch(branchrecord.get(rs.getInt("bid") - 1));
				v.setType(rs.getString("type"));
				v.setOdoread((int) rs.getDouble("odoread"));
				v.setAcquiredate(temp);
				
				result.add(v);
			}
				
			if(!result.isEmpty())
				tb.setItems(result);


		} catch (SQLException ex) {
			MainController.showWarningDialog(ex.getMessage());
		}
	}
	
	
	
	@FXML
	public void checkod(){
		result.clear();
		
		adate.setText("Expected Return Date");
		odoread.setText("RentID");
		
		int test = checkcondition();
		
		PreparedStatement ps;
		ResultSet rs;
			
		Calendar cal = Calendar.getInstance();
		Timestamp ts = new Timestamp(cal.getTime().getTime());
			
		try{
			if(test == 0) {
				/* query that search the over due vehicle*/
				ps = SQLConnect.getcon().prepareStatement("SELECT V.bid, V.vid, V.type, R.rentid, R.expectedreturn FROM VEHICLE V "
						+ "JOIN RENTAL R WHERE (V.vid = R.vid AND R.expectedreturn < ? AND R.rentid not in (select rt.rentid from RETURNRECORD rt)) order by V.bid, V.type");
				ps.setTimestamp(1, ts);
			}
			else if(test == 1) {
				/* query that search the over due vehicle ordered by branch id for a particular type*/
				ps = SQLConnect.getcon().prepareStatement("SELECT V.bid, V.vid, V.type, R.rentid, R.expectedreturn FROM VEHICLE V "
						+ "JOIN RENTAL R WHERE (V.type = ? AND V.vid = R.vid AND R.expectedreturn < ? AND R.rentid not in (select rt.rentid from RETURNRECORD rt)) order by V.bid");
				ps.setString(1, category.getSelectionModel().getSelectedItem());
				ps.setTimestamp(2, ts);
			}
			else if (test == 2){
				/* query that search the over due vehicle ordered by type for a particular branch*/
				ps = SQLConnect.getcon().prepareStatement("SELECT V.bid, V.vid, V.type, R.rentid, R.expectedreturn FROM VEHICLE V "
						+ "JOIN RENTAL R WHERE (V.bid = ? AND V.vid = R.vid AND R.expectedreturn < ? AND R.rentid not in (select rt.rentid from RETURNRECORD rt)) order by V.type");
				ps.setInt(1, Translate.SToI(loc.getSelectionModel().getSelectedItem()));
				ps.setTimestamp(2, ts);
			}
			else {
				/* query that search the over due vehicle for a particular branch and type*/
				ps = SQLConnect.getcon().prepareStatement("SELECT V.bid, V.vid, V.type, R.rentid, R.expectedreturn FROM VEHICLE V "
						+ "JOIN RENTAL R WHERE (V.type = ? AND V.bid = ? AND V.vid = R.vid AND R.expectedreturn < ? AND R.rentid not in (select rt.rentid from RETURNRECORD rt))");
				ps.setString(1, category.getSelectionModel().getSelectedItem());
				ps.setInt(2, Translate.SToI(loc.getSelectionModel().getSelectedItem()));
				ps.setTimestamp(3, ts);
			}
			
			rs = ps.executeQuery();
			
			while( rs.next() ) {
				Vehicle v = new Vehicle();
				Calendar temp = Calendar.getInstance();
				temp.clear();
				
				temp.setTimeInMillis( rs.getDate("expectedreturn").getTime() );
				v.setVid(rs.getString("vid"));
				v.setBranch(branchrecord.get(rs.getInt("bid") - 1));
				v.setType(rs.getString("type"));
				v.setOdoread(rs.getInt("rentid"));
				v.setAcquiredate(temp);
				
				result.add(v);
			}
				
			if(!result.isEmpty())
				tb.setItems(result);


		} catch (SQLException ex) {
			MainController.showWarningDialog(ex.getMessage());
		}
	}
	
	
	
	@FXML
	public void checkfs(){
		result.clear();
		
		adate.setText("Acquire Date");
		odoread.setText("Odormeter Read");
		
		int test = checkcondition();
		
		PreparedStatement ps;
		Statement stmt;
		ResultSet rs;

			
		try{
			if(test == 0) {
				/* query that search the vehicles for sale*/
				stmt = SQLConnect.getcon().createStatement();
				rs = stmt.executeQuery("SELECT * FROM FORSALE s JOIN VEHICLE v where s.vid = v.vid Order by v.bid, v.type");
				
			}
			else if(test == 1) {
				/* query that search the vehicles for sale ordered by branch id for a particular type*/
				ps = SQLConnect.getcon().prepareStatement("SELECT * FROM FORSALE s JOIN VEHICLE v where s.vid = v.vid AND v.type = ? Order by v.bid");
				ps.setString(1, category.getSelectionModel().getSelectedItem());
				rs = ps.executeQuery();
					
			}
			else if (test == 2){
				/* query that search the vehicles for sale ordered by type for a particular branch*/
				ps = SQLConnect.getcon().prepareStatement("SELECT * FROM FORSALE s JOIN VEHICLE v where s.vid = v.vid AND v.bid = ? Order by v.type");
				ps.setInt(1, Translate.SToI(loc.getSelectionModel().getSelectedItem()));
				rs = ps.executeQuery();
			}
			else {
				/* query that search the vehicles for sale for a particular branch and type*/
				ps = SQLConnect.getcon().prepareStatement("SELECT * FROM FORSALE s JOIN VEHICLE v where s.vid = v.vid AND v.type = ? AND v.bid = ?");
				ps.setString(1, category.getSelectionModel().getSelectedItem());
				ps.setInt(2, Translate.SToI(loc.getSelectionModel().getSelectedItem()));
				rs = ps.executeQuery();
			}
			
			while( rs.next() ) {
				Vehicle v = new Vehicle();
				Calendar temp = Calendar.getInstance();
				temp.clear();
				
				temp.setTimeInMillis( rs.getDate("acquiredate").getTime() );
				v.setVid(rs.getString("vid"));
				v.setBranch(branchrecord.get(rs.getInt("bid") - 1));
				v.setType(rs.getString("type"));
				v.setOdoread((int)rs.getDouble("odoread"));
				v.setPrice(rs.getDouble("price"));
				v.setAcquiredate(temp);
				
				result.add(v);
			}
				
				tb.setItems(result);


		} catch (SQLException ex) {
			MainController.showWarningDialog(ex.getMessage());
		}
	}
	
	/**
	 * check the condition of choice box (selected or not selected)
	 * @return int used for condition check
	 */
	private int checkcondition(){
		int temp = 0;
		
		if( category.getSelectionModel().getSelectedIndex() >= 0 ) temp += 1;
		
		if( loc.getSelectionModel().getSelectedIndex() >= 0 ) temp += 2;
		
		return temp;
	}
	
}
