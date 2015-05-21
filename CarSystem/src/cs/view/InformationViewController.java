package cs.view;


import java.sql.*;

import cs.CalculateFee;
import cs.SQLConnect;
import cs.model.Reservation;
import cs.model.Equipment;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

/**
*
* @author Jiyu Xiao
*/
public class InformationViewController {
	
	@FXML
	private TextField cname;
	
	@FXML
	private TextField cphonenum;
		
	@FXML
	private Label info;
	
	@FXML
	private Button reserve;
	
	private Reservation res = new Reservation();
	
	@FXML
	public void makeReservation(){
		String name = cname.getText();
		String phonenum = cphonenum.getText();
		
		
		if(name.length() == 0 || phonenum.length() == 0) {
			info.setText("Please give an valid name or phonenum");
			return ;
		}
		
		res.setCname(name);
		res.setCphonenum(phonenum);
		
		PreparedStatement ps;
//		PreparedStatement ps2;
		Statement stmt;
		ResultSet rs;
		
		try{
			/* query that insert the new reservation record*/
			ps = SQLConnect.getcon().prepareStatement("INSERT INTO RESERVATION (bid, vid, startdate, returndate, cname, cphonenum) VALUES (?,?,?,?,?,?)");
			ps.setInt(1, res.getBid());
			ps.setString(2, res.getVid());
			ps.setTimestamp(3, new java.sql.Timestamp(res.getStartdate().getTimeInMillis()));
			ps.setTimestamp(4, new java.sql.Timestamp(res.getReturndate().getTimeInMillis()));
			ps.setString(5, res.getCname());
			ps.setString(6, res.getCphonenum());
			
			ps.executeUpdate();
			SQLConnect.getcon().commit();
			ps.close();
			
			stmt = SQLConnect.getcon().createStatement();
			
			/* query that get the most recent reservation id*/
			rs = stmt.executeQuery("SELECT RESID FROM RESERVATION where resid = LAST_INSERT_ID()");
			
			if( ! rs.next() ) {
				info.setText("DataBase error!");
			}
			else {
				res.setResid(rs.getInt("Resid"));
				stmt.close();
				
				for (Equipment i : res.getequip()) {
					/* query that insert the new reservated equipment record*/
					ps = SQLConnect.getcon().prepareStatement("INSERT INTO RESERVEDEQUIPMENT VALUES (?,?)"); 
					ps.setInt(1, res.getResid());
					ps.setString(2, i.getName());
					ps.executeUpdate();
					SQLConnect.getcon().commit();
					ps.close();
					
//					ps2 = SQLConnect.getcon().prepareStatement("Update KEEPSEQUIPMENT set quantity = quantity - 1 where bid = ? AND ename = ?");
//					ps2.setInt(1, res.getBid());
//					ps2.setString(2, i.getName());
//					ps2.executeUpdate();
//					SQLConnect.getcon().commit();
//					ps2.close();
				}
				
				
				info.setText("Thanks for the reservation, your confirm number is " + res.getResid() + ".");
				
				cname.setDisable(true);;
				cphonenum.setDisable(true);
				reserve.setDisable(true);
			}
		} catch (SQLException ex) {
			info.setText("Message:" + ex.getMessage());
		}
	}
	
	@FXML
	public void initialize(){
		cname.setDisable(true);
		cphonenum.setDisable(true);
		reserve.setDisable(true);
	}
	
	public void setReservation(Reservation r, String type) {
		this.res = r;
		info.setText("The estimated fee for " + type + " " + res.getVid() + " is " + CalculateFee.estimateFee(r) +  " CAD$.");
		
		cname.setDisable(false);
		cphonenum.setDisable(false);
		reserve.setDisable(false);
	}
	
	
}
