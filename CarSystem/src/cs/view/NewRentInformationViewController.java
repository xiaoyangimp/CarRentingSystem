package cs.view;


import java.sql.*;

import cs.CalculateFee;
import cs.SQLConnect;
import cs.model.Equipment;
import cs.model.NewRent;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Jiyu Xiao, IshmeetSingh
 */
public class NewRentInformationViewController {
	
	@FXML
	private TextField cname;
	
	@FXML
	private TextField cphonenum;
        
        @FXML
        private TextField address;
	
        @FXML
        private TextField city;
        
        @FXML
        private TextField license;
        
        @FXML
        private TextField card;
        
        @FXML
        private DatePicker expiry;
        
	@FXML
	private Label info;
	
        @FXML
	private Label errorName;
        
        @FXML
	private Label errorPhone;
        
        @FXML
	private Label errorAddress;
        
        @FXML
	private Label errorCity;
        
        @FXML
	private Label errorLicense;
        
        @FXML
	private Label errorCard;
        
        @FXML
	private Label errorExp;
        
        
        
        
	@FXML
	private Button reserve;
	
	private NewRent res = new NewRent();
	
	@FXML
	public void makeRent(){
		String name = cname.getText();
		String phonenum = cphonenum.getText();
                
                
		
		
		if(name.length() == 0 ) {
			errorName.setText("Name cannot be left empty");
			
		}
                
                else if (phonenum.length() ==0 || Pattern.matches("[a-zA-Z]+", phonenum) == true){
                    errorName.setText("");
                    errorPhone.setText("Please enter valid phone number");
                    
                }
                
                else if(address.getText().length() == 0 ) {
                        errorPhone.setText("");
			errorAddress.setText("Address cannot be left empty");
			
		}
                
                else if(city.getText().length() == 0 ) {
                    errorAddress.setText("");
			errorCity.setText("City cannot be left empty");
			
		}
                                
                else if(license.getText().length() != 10 )
                    
                {   errorCity.setText("");
                    errorLicense.setText("Please enter valid License Number");
                    
                }
                
                else if(card.getText().length() != 16 || Pattern.matches("[a-zA-Z]+", card.getText()) == true)
                    
                {   errorLicense.setText("");
                    errorCard.setText("Please enter valid Card Number");
                    
                }
                
                               
                else if(expiry.getValue().isBefore(LocalDate.now()) ) {
                        errorCard.setText("");
			errorExp.setText("Card is Expired");
			
		}
                
                
                else{
                
                errorExp.setText("");
		
		res.setCname(name);
		res.setCphonenum(phonenum);
                res.setAddress(address.getText());
                res.setCity(city.getText());
                res.setLicense(license.getText());
                res.setCreditCard(card.getText());
                
                LocalDate Edate = expiry.getValue();
         
                Calendar expcal = Calendar.getInstance();
		expcal.clear();
		expcal.set(Calendar.YEAR, Edate.getYear());
		expcal.set(Calendar.MONTH, Edate.getMonthValue() - 1);
		expcal.set(Calendar.DAY_OF_MONTH, Edate.getDayOfMonth());
                
		
                res.setExpDate(expcal);
                
		
		PreparedStatement ps, ps2;
		Statement stmt;
		ResultSet rs;
		
		try{
                    //Adding Customer to CUSTOMER table if not present
                                
                                
                                stmt = SQLConnect.getcon().createStatement();    
                                rs = stmt.executeQuery("SELECT * FROM CUSTOMER WHERE PHONENUM ='"+cphonenum.getText()+"'");
                                
                                if(!rs.next()){
                    
                               
                                ps = SQLConnect.getcon().prepareStatement("INSERT INTO CUSTOMER VALUES (?,?,?,?)"); 
				
				ps.setString(1, res.getCphonenum());
				ps.setString(2, res.getCname());
				ps.setString(3, res.getAddress());
				ps.setString(4, res.getCity());
				
				
				ps.executeUpdate();
				SQLConnect.getcon().commit();
				ps.close(); 
                                
                                    }
                                
                                //}                                                              
                                
                    
                    
			ps = SQLConnect.getcon().prepareStatement("INSERT INTO RENTAL (bid, phonenum, vid, startdate, expectedreturn, dlicense, cardnum, cardexpire ) VALUES (?,?,?,?,?,?,?,?)");
			ps.setInt(1, res.getBid());
                        ps.setString(2, res.getCphonenum());
			ps.setString(3, res.getVid());
			ps.setTimestamp(4, new java.sql.Timestamp(res.getStartdate().getTimeInMillis()));
			ps.setTimestamp(5, new java.sql.Timestamp(res.getReturndate().getTimeInMillis()));
			ps.setString(6, res.getLicense());
                        ps.setString(7, res.getCreditCard());
			ps.setTimestamp(8, new java.sql.Timestamp(res.getExpDate().getTimeInMillis()));
			
			ps.executeUpdate();
			SQLConnect.getcon().commit();
			ps.close();
			
			stmt = SQLConnect.getcon().createStatement();
			
			rs = stmt.executeQuery("SELECT RENTID FROM RENTAL where RENTID = LAST_INSERT_ID()");
			
			if( ! rs.next() ) {
				info.setText("DataBase error!");
			}
			else {
				res.setRentid(rs.getInt("RENTID"));
				stmt.close();
				
				for (Equipment i : res.getequip()) {
					ps = SQLConnect.getcon().prepareStatement("INSERT INTO RENTEDEQUIPMENT VALUES (?,?)"); 
					ps.setInt(1, res.getRentid());
					ps.setString(2, i.getName());
					ps.executeUpdate();
					SQLConnect.getcon().commit();
					ps.close();
					
					ps2 = SQLConnect.getcon().prepareStatement("Update KEEPSEQUIPMENT set quantity = quantity - 1 where bid = ? AND ename = ?");
					ps2.setInt(1, res.getBid());
					ps2.setString(2, i.getName());
					ps2.executeUpdate();
					SQLConnect.getcon().commit();
					ps2.close();
				}
				ps.close();
                                
                                
                                
	
                        // Removing entries from RESERVATION and RESERVED EQUIPMENT
				
				info.setText("Thanks for renting, your confirm number is " + res.getRentid() + ".");
				
				cname.setDisable(true);;
				cphonenum.setDisable(true);
				reserve.setDisable(true);
			}
		} catch (SQLException ex) {
                    info.setText("Message:" + ex.getMessage());
		}
	}
        }
	@FXML
	public void initialize(){
		cname.setDisable(true);
		cphonenum.setDisable(true);
		reserve.setDisable(true);
	}
	
	public void setNewRent(NewRent r, String type) {
		this.res = r;
		info.setText("The estimated fee for " + type + " " + res.getVid() + " is " + CalculateFee.estimateFee(r) +  " CAD$.");
		
		cname.setDisable(false);
		cphonenum.setDisable(false);
		reserve.setDisable(false);
	}
	
	
}
