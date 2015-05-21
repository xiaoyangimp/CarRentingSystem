/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.view;

import cs.SQLConnect;
import cs.model.CheckReservation;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author IshmeetSingh
 */
public class CheckReservationViewController {
    
    @FXML
    public TextField license;
    @FXML
    public TextField address;
    
    @FXML
    public TextField city;
    
    @FXML
    private ChoiceBox<Integer> phour;
	
    @FXML
    private ChoiceBox<Integer> rhour;
    
    @FXML
    public TextField cardNum;
    
    @FXML
    public DatePicker expDate;
    
    @FXML
    public TextField resID;
    
    @FXML
    public TextField rescname;
    
    @FXML
    public TextField respnumber;
    
    @FXML
    public DatePicker PickDate;
    
    @FXML
    public DatePicker RetDate;
    
    @FXML
    public Button resconfirm;
    
     @FXML
    public Button MakeRent;
     
     @FXML
     public Label errorMsg;
     
     @FXML
     public Label info;
     
     
     
     private CheckReservation chk = new CheckReservation();
	private MainViewController mainvc;
     
        @FXML
	public void initialize() {
            MakeRent.setDisable(true);
            expDate.setDisable(true);
            license.setDisable(true);
            cardNum.setDisable(true);
            address.setDisable(true);
            city.setDisable(true);
            expDate.setValue(LocalDate.now());
        }
        
        
     public void CheckReservation()
     {
         String reservationID = resID.getText();
         String customerName = rescname.getText();
         String phoneNum = respnumber.getText();
         String Caddress = address.getText();
         String Ccity = city.getText();
         
         
         
         
         
         
                PreparedStatement ps;
		ResultSet rs;
                Statement stmt;
                
                
                
                
                
                try{
                
                    
                    
                    
                    	stmt = SQLConnect.getcon().createStatement();
			
                    	rs = stmt.executeQuery("SELECT * FROM RESERVATION WHERE RESID ="+reservationID+"");
                        
                        
                        
                        
                       
                        
                        if (rs.next()){
                        
                            
                        MakeRent.setDisable(false);
                        expDate.setDisable(false);
                        license.setDisable(false);
                        cardNum.setDisable(false);
                        address.setDisable(false);
                        city.setDisable(false);
                        errorMsg.setText("");
                        
                        
                        chk.setCname(rs.getString("CNAME"));
			rescname.setText(chk.getCname());                        
                        
                        chk.setCphonenum(rs.getString("CPHONENUM"));
			respnumber.setText(chk.getCphonenum());
                                               
                        //chk.setStartDate(rs.getString("STARTDATE"));
			//PickDate.setText(chk.getStartDate());
                        
                       // chk.setReturnDate(rs.getString("RETURNDATE"));    
			//RetDate.setText(chk.getReturnDate());
                        
                        
                        LocalDate PDATE = rs.getTimestamp("STARTDATE").toLocalDateTime().toLocalDate();
                        LocalDate RDATE = rs.getTimestamp("RETURNDATE").toLocalDateTime().toLocalDate();
                        
                        int PHOUR = rs.getTimestamp("STARTDATE").toLocalDateTime().getHour();
                         int RHOUR = rs.getTimestamp("RETURNDATE").toLocalDateTime().getHour();
                        
                         
                         
                         
                        PickDate.setValue(PDATE);
                        RetDate.setValue(RDATE);
                        
                       // phour.setValue(phour);
                        //rhour.setValue(rhour);
                        
                        
                       
                        }
                        
                        else if (!rs.next()){
                            MakeRent.setDisable(true);
                            expDate.setDisable(true);
                            license.setDisable(true);
                            cardNum.setDisable(true);
                            address.setDisable(true);
                            city.setDisable(true);
                            errorMsg.setText("No Reservation Under This ID");
                        }
                        
                        rs = stmt.executeQuery("SELECT * FROM CUSTOMER WHERE phonenum ="+chk.getCphonenum()+""); 
                        
                        if(rs.next()) {
                        	address.setText(rs.getString("address"));
                        }
                      
                }catch(SQLException ex) {
			errorMsg.setText("Database error.");
         
    
      }
     
     
     
}
     
     
     public void MakeRent(){
         
                      
         
                String reservationID = resID.getText();               
                String phoneNum = respnumber.getText();
                String cardNumber = cardNum.getText();
                PreparedStatement ps, ps2;
		ResultSet rs;
                Statement stm,stmt;
                
                
                
                
                
                
                
                try{
                    if((address.getLength() == 0) )
                        { 
                            address.setText("Address cannot be empty!!");
                            return;
                        }
                        
                        if(city.getLength() == 0 )
                        { 
                            city.setText("City cannot be empty!!");
                            return;
                        }
                        
                        if(license.getText().length() != 10 )
                    
                        {
                            //license.set
                            license.setText("Invalid License Number");
                            return;
                    
                        }  
                        
                        if(cardNum.getText().length() != 16 || Pattern.matches("[a-zA-Z]+", cardNum.getText()) == true)
                        {
                            cardNum.setText("Invalid Card Number");
                            return;
                        }
                        
                        if(expDate.getValue().isBefore(LocalDate.now()) )
                        {
                        	info.setText("Card is Expired");
                        	return;
                        }
                        
                        else{
                
                stm = SQLConnect.getcon().createStatement();    
                rs = stm.executeQuery("SELECT * FROM CUSTOMER WHERE PHONENUM ='"+phoneNum+"'");
                
                if(rs.next()){
                    
                String temp = (rs.getString("PHONENUM"));
                
                              
                
                     if(phoneNum.equals(rs.getString("PHONENUM")))
                    {
                        //  errorMsg.setText("Customer Alerady Exists");
                     }
                }
                
                else{
                 
                        
                        
                        chk.setCphonenum(respnumber.getText());
                        chk.setAddress(address.getText());
                        chk.setCity(city.getText());
                        chk.setCname(rescname.getText());
                        
                        
                        
                        
                        
                        errorMsg.setText("New Customer Added");
                        
                        ps = SQLConnect.getcon().prepareStatement("INSERT INTO CUSTOMER VALUES (?,?,?,?)"); 
				
				ps.setString(1, chk.getCphonenum());
				ps.setString(2, chk.getCname());
				ps.setString(3, chk.getAddress());
				ps.setString(4, chk.getCity());
				
				
				ps.executeUpdate();
				SQLConnect.getcon().commit();
				ps.close();
                    
                
                
                            }
                
                    
                
                
                //Add to rent table
                
               
                
                
                LocalDate Edate = expDate.getValue();
         
                Calendar expcal = Calendar.getInstance();
                expcal.clear();
                expcal.set(Calendar.YEAR, Edate.getYear());
                expcal.set(Calendar.MONTH, Edate.getMonthValue() - 1);
                expcal.set(Calendar.DAY_OF_MONTH, Edate.getDayOfMonth());
		
                chk.setExpDate(expcal);
                chk.setCardNum(cardNum.getText());
                chk.setLicense(license.getText());
                
                
                              
                
               if(cardNumber.length() != 16 || Pattern.matches("[a-zA-Z]+", cardNumber) == true)
                    
                {
                    errorMsg.setText("Invalid Credit Card Number");
                    return ;
                }
                
               
               
                        stm = SQLConnect.getcon().createStatement();
			
			rs = stm.executeQuery("SELECT * FROM RESERVATION WHERE RESID ="+reservationID+"");
                        
                       
                        
                        while (rs.next()){
                            
                        
                        
                        LocalDate PDATE = rs.getTimestamp("STARTDATE").toLocalDateTime().toLocalDate();
                        int PHOUR = rs.getTimestamp("STARTDATE").toLocalDateTime().getHour();
                        
                        Calendar pdate = Calendar.getInstance();
                        pdate.clear();
                        pdate.set(Calendar.YEAR, PDATE.getYear());
                        pdate.set(Calendar.MONTH, PDATE.getMonthValue() - 1);
                        pdate.set(Calendar.DAY_OF_MONTH, PDATE.getDayOfMonth());
                        pdate.set(Calendar.HOUR_OF_DAY, PHOUR);
		
                        chk.setStartDate(pdate);
                        
                        
                        LocalDate RDATE = rs.getTimestamp("RETURNDATE").toLocalDateTime().toLocalDate();
                        int RHOUR = rs.getTimestamp("RETURNDATE").toLocalDateTime().getHour();
                        Calendar rdate = Calendar.getInstance();
                        rdate.clear();
                        rdate.set(Calendar.YEAR, RDATE.getYear());
                        rdate.set(Calendar.MONTH, RDATE.getMonthValue() - 1);
                        rdate.set(Calendar.DAY_OF_MONTH, RDATE.getDayOfMonth());
                        rdate.set(Calendar.HOUR_OF_DAY, RHOUR);
                        
                        chk.setReturnDate(rdate);
                        
                        chk.setBid(rs.getInt("BID"));
                        chk.setVid(rs.getString("VID"));
                        }
                
                        
			ps = SQLConnect.getcon().prepareStatement("INSERT INTO RENTAL (BID,PHONENUM,VID,STARTDATE,EXPECTEDRETURN,DLICENSE,CARDNUM,CARDEXPIRE) VALUES  (?,?,?,?,?,?,?,?)");
			ps.setInt(1, chk.getBid());
                        ps.setString(2, chk.getCphonenum());
			ps.setString(3, chk.getVid());
			ps.setTimestamp(4,new java.sql.Timestamp(chk.getStartDate().getTimeInMillis()) );
			ps.setTimestamp(5,new java.sql.Timestamp(chk.getReturnDate().getTimeInMillis()));
			ps.setString(6, chk.getLicense());
			ps.setString(7, chk.getCardNum());
                        ps.setTimestamp(8, new java.sql.Timestamp(chk.getExpDate().getTimeInMillis()));
			
			ps.executeUpdate();
			SQLConnect.getcon().commit();
			ps.close();
            
			int rentid = 0;
			
			rs = stm.executeQuery("SELECT rentid FROM RENTAL where rentid = LAST_INSERT_ID()");
			
			if( ! rs.next() ) {
				info.setText("DataBase error!");
				return;
			}
			else {
				rentid = rs.getInt("rentid"); 
			}
                        
            rs = stm.executeQuery("SELECT * FROM RESERVEDEQUIPMENT where RESID = '"+reservationID+"'");
                        
                        
             while(rs.next())
             {
            	 	ps = SQLConnect.getcon().prepareStatement("INSERT INTO RENTEDEQUIPMENT VALUES (?,?)"); 
					ps.setInt(1, rentid);
					ps.setString(2, rs.getString("ename"));
					ps.executeUpdate();
					SQLConnect.getcon().commit();
					ps.close();
					
					ps2 = SQLConnect.getcon().prepareStatement("Update KEEPSEQUIPMENT set quantity = quantity - 1 where bid = ? AND ename = ?");
					ps2.setInt(1, chk.getBid());
					ps2.setString(2, rs.getString("ename"));
					ps2.executeUpdate();
					SQLConnect.getcon().commit();
					ps2.close();
                            
             }

                        
                        
   
                info.setText("Thanks for the rent, your transaction ID is " + rentid + ".");

                
                
                        
                ps = SQLConnect.getcon().prepareStatement("DELETE FROM RESERVEDEQUIPMENT WHERE RESID = '"+reservationID+"'");        
                ps.executeUpdate();
                SQLConnect.getcon().commit();
                ps.close();
                    
                ps = SQLConnect.getcon().prepareStatement("DELETE FROM RESERVATION WHERE RESID = '"+reservationID+"'");        
                ps.executeUpdate();
                SQLConnect.getcon().commit();
                ps.close();        
                        
                        }       
                
                
                }catch(SQLException ex) {
			errorMsg.setText(ex.getMessage());
                }
                
                
     
     }     
     public void setMainVC(MainViewController m) {
		this.mainvc = m;
	}
	
	public MainViewController getMainVC(){
		return mainvc;
	}

    
}












