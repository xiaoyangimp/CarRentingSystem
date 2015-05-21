package cs;

import java.sql.*;
import java.util.Calendar;

import cs.model.Equipment;
import cs.model.ReservationToCancel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
*
* @author Jiyu Xiao
*/
public class SQLSearch {
	
	/**
	 * A static function that get all the branch from database
	 * @return ObservableList<String> that contains all branch information
	 */
	public static ObservableList<String> getBranchList() {
		ObservableList<String> temp = FXCollections.observableArrayList();
		
		int bid;
		String bcity;
		String baddr;
		StringBuffer btemp = new StringBuffer();
		Statement stmt;
		ResultSet rs;
		
		try
		{
			stmt = SQLConnect.getcon().createStatement();
			
			rs = stmt.executeQuery("SELECT * FROM BRANCH");
			
			while(rs.next()) {
				bid = rs.getInt("bid");
				bcity = rs.getString("city");
				baddr = rs.getString("address");
				
				btemp.append(bid);
				btemp.append(". ");
				btemp.append(bcity);
				btemp.append("-");
				btemp.append(baddr);
				
				temp.add(btemp.toString());
				btemp.delete(0, btemp.length());
			}
			
		} 	catch (SQLException ex) {
			MainController.showWarningDialog(ex.getMessage());
		}
		
		return temp;
	}
	
	/**
	 * A static function that get all the category from database
	 * @return ObservableList<String> that contains all category information
	 */
	public static ObservableList<String> getCategoryList(){
		ObservableList<String> temp = FXCollections.observableArrayList();
		
		StringBuffer ctemp = new StringBuffer();
		Statement stmt;
		ResultSet rs;
		
		try
		{
			stmt = SQLConnect.getcon().createStatement();
			
			rs = stmt.executeQuery("SELECT * FROM CATEGORY");
			
			while(rs.next()) {
				ctemp.append(rs.getString("cid"));
				ctemp.append(". ");
				ctemp.append(rs.getString("name"));
				
				temp.add(ctemp.toString());
				ctemp.delete(0, ctemp.length());
			}
			
		} 	catch (SQLException ex) {
			MainController.showWarningDialog(ex.getMessage());
		}
		
		return temp;
	}
	
	/**
	 *
	 * A static function that get all the VehicleType from database
	 *
	 * @param i the id of the category
	 * @return ObservableList<String> that contains all VehicleType information
	 */
	public static ObservableList<String> getVehicleTypeList(int i){
		ObservableList<String> temp = FXCollections.observableArrayList();
		
		String vtype;
		PreparedStatement ps;
		Statement stmt;
		ResultSet rs;
		
		try
		{
			if( i == 0) {
				stmt = SQLConnect.getcon().createStatement();
				rs = stmt.executeQuery("SELECT * FROM VEHICLETYPE");

			
				while(rs.next()) {
					vtype = rs.getString("type");
				
					temp.add(vtype);
				}
			}
			else {
				ps = SQLConnect.getcon().prepareStatement("SELECT * FROM VEHICLETYPE where cid = ?");
				ps.setInt(1, i);
				rs = ps.executeQuery();
			
				while(rs.next()) {
					vtype = rs.getString("type");
				
					temp.add(vtype);
				}
				
				
				ps.close();
			}
			
		} 	catch (SQLException ex) {
			MainController.showWarningDialog(ex.getMessage());
		}
		
		return temp;
	}
	
	/**
	 * A static function that get all the Equipment from database
	 * @return ObservableList<String> that contains all equipment information
	 */
	public static ObservableList<String> getAllEquipmentName() {
		ObservableList<String> equip = FXCollections.observableArrayList();
		
		String ename;
		Statement stmt;
		ResultSet rs;
		
		try
		{
			stmt = SQLConnect.getcon().createStatement();
			rs = stmt.executeQuery("SELECT ename from EQUIPMENT"); 
			
			while(rs.next()) {
				ename = rs.getString("ename");
				equip.add(ename);
			}
			
		} 	catch (SQLException ex) {
			MainController.showWarningDialog(ex.getMessage());
		}
				
		return  equip;
	}
	
	/**
	 * A static function that get all the Equipment from database that belongs a particular vehicle
	 * @param vid the id of the vehicle
	 * @return ObservableList<Equipment> that contains Equipment object
	 */
	public static ObservableList<Equipment> getAdditionalEquipment(String vid) {
		ObservableList<Equipment> equip = FXCollections.observableArrayList();
		
		String ename;
		PreparedStatement ps;
		ResultSet rs;
		
		try
		{
			
			ps = SQLConnect.getcon().prepareStatement("SELECT e.ename from VEHICLE v join VEHICLETYPE vt"
					+ " join EQUIPMENT e where v.type = vt.type AND vt.cid = e.cid AND v.vid = ?"); 
			
			ps.setString(1, vid);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				ename = rs.getString("ename");
				equip.add(new Equipment(ename));
			}
			
			ps.close();
			
		} 	catch (SQLException ex) {
			MainController.showWarningDialog(ex.getMessage());
		}
				
		return  equip;
	}
	
	
	/**
	 * A static function that get all the reservation that has a particular resid
	 * @param cn a particular resid
	 * @return ObservableList<ReservationToCancel> that contains Reservation object that might be cancelled
	 */
	public static ObservableList<ReservationToCancel> cancelByConfirmNumber(int cn) {
		ObservableList<ReservationToCancel> recordlist = FXCollections.observableArrayList();
		
		PreparedStatement ps;
		ResultSet rs;
		
		try
		{
			ps = SQLConnect.getcon().prepareStatement("SELECT r.vid, r.startdate, r.returndate, "
					+ "v.type, r.bid, r.resid from RESERVATION r, VEHICLE v "
					+ "where r.vid = v.vid AND r.resid = ?"); 
			
			ps.setInt(1, cn);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				ReservationToCancel rtc = new ReservationToCancel();
				rtc.setVid(rs.getString("vid"));
				String std = rs.getTimestamp("startdate").toString();
				rtc.setStartdateinstring(std);
				String rtd = rs.getTimestamp("returndate").toString();
				rtc.setReturndateinstring(rtd);
				rtc.setVehicletype(rs.getString("type"));
				rtc.setBid(rs.getInt("bid"));
				rtc.setResid(rs.getInt("resid"));
				recordlist.add(rtc);
			}
			
			ps.close();
			
		} 	catch (SQLException ex) {
			MainController.showWarningDialog(ex.getMessage());
		}
				
		return  recordlist;
	}
	
	
	/**
	 * A static function that get all the current reservation
	 * @return ObservableList<ReservationToCancel> that contains Reservation object that might be cancelled
	 */
	public static ObservableList<ReservationToCancel> checkall() {
		ObservableList<ReservationToCancel> recordlist = FXCollections.observableArrayList();
		
		Statement stmt;
		ResultSet rs;
		
		try
		{
			stmt = SQLConnect.getcon().createStatement();
			rs = stmt.executeQuery("SELECT r.vid, r.startdate, r.returndate, "
					+ "v.type, r.bid, r.resid from RESERVATION r, VEHICLE v "
					+ "where r.vid = v.vid"); 
			
			while(rs.next()) {
				ReservationToCancel rtc = new ReservationToCancel();
				rtc.setVid(rs.getString("vid"));
				String std = rs.getTimestamp("startdate").toString();
				rtc.setStartdateinstring(std);
				String rtd = rs.getTimestamp("returndate").toString();
				rtc.setReturndateinstring(rtd);
				rtc.setVehicletype(rs.getString("type"));
				rtc.setBid(rs.getInt("bid"));
				rtc.setResid(rs.getInt("resid"));
				recordlist.add(rtc);
			}
			
		} 	catch (SQLException ex) {
			MainController.showWarningDialog(ex.getMessage());
		}
				
		return  recordlist;
	}
	
	
	/**
	 * A static function that get all the reservation that contains a particular phone number and start from a particular date
	 * @param phnum customer's phone number
	 * @param startd start date (Calendar)
	 * @return ObservableList<ReservationToCancel> that contains Reservation object that might be cancelled
	 */
	public static ObservableList<ReservationToCancel> cancelByphonenumanddate(String phnum, Calendar startd) {
		ObservableList<ReservationToCancel> recordlist = FXCollections.observableArrayList();
		
		PreparedStatement ps;
		ResultSet rs;
		
		try
		{
			ps = SQLConnect.getcon().prepareStatement("SELECT r.vid, r.startdate, r.returndate, "
					+ "v.type, r.bid, r.resid from RESERVATION r, VEHICLE v "
					+ "where r.vid = v.vid AND r.cphonenum = ? AND Date(r.startdate) = ?"); 
			
			ps.setString(1, phnum);
			ps.setDate(2, new java.sql.Date(startd.getTime().getTime()));
			rs = ps.executeQuery();
			
			while(rs.next()) {
				ReservationToCancel rtc = new ReservationToCancel();
				rtc.setVid(rs.getString("vid"));
				String std = rs.getTimestamp("startdate").toString();
				rtc.setStartdateinstring(std);
				String rtd = rs.getTimestamp("returndate").toString();
				rtc.setReturndateinstring(rtd);
				rtc.setVehicletype(rs.getString("type"));
				rtc.setBid(rs.getInt("bid"));
				rtc.setResid(rs.getInt("resid"));
				recordlist.add(rtc);
			}
			
			ps.close();
			
		} 	catch (SQLException ex) {
			MainController.showWarningDialog(ex.getMessage());
		}
				
		return  recordlist;
	}
}

