/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.model;

import java.util.Calendar;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author IshmeetSingh
 */
public class CheckReservation {
    
        private final IntegerProperty BID;
        private final IntegerProperty RESID;
	private final StringProperty VID;
	private final StringProperty CNAME;
	private final StringProperty CPHONENUM;
        //private final StringProperty STARTDATE;
        //private final StringProperty RETURNDATE;
        private final StringProperty ADDRESS;
        private final StringProperty CITY; 
        private Calendar CARDEXPIRE;
        private Calendar STARTDATE;
        private Calendar RETURNDATE;
        private final StringProperty CARDNUM; 
        private final StringProperty DLICENSE; 
         
         
        public CheckReservation()
                {
                this.RESID = new SimpleIntegerProperty(-1);    
                this.BID = new SimpleIntegerProperty (-1);
		this.VID = new SimpleStringProperty (null);
		this.CNAME = new SimpleStringProperty(null);
                this.ADDRESS = new SimpleStringProperty(null);
                this.CITY = new SimpleStringProperty(null);
		this.CPHONENUM = new SimpleStringProperty(null);
                //this.STARTDATE = new SimpleStringProperty(null);
                //this.RETURNDATE = new SimpleStringProperty(null);
                this.DLICENSE = new SimpleStringProperty(null);
                this.CARDNUM = new SimpleStringProperty(null);
                this.CARDEXPIRE = Calendar.getInstance();
                this.STARTDATE = Calendar.getInstance();
                this.RETURNDATE = Calendar.getInstance();
        }
        
        public int getResID() {
		return RESID.get();
	}
        
        public int getBid() {
		return BID.get();
	}
        
        public void setBid(int t){
		this.BID.set(t);
	}
	

	
	public void setVid(String t){
		this.VID.set(t);
	}
        
        public String getVid() {
		return VID.get();
	}
        
        public String getCname() {
		return CNAME.get();
	}
        
        public String getCphonenum() {
		return CPHONENUM.get();
	}
        
       // public String getStartDate() {
//		return STARTDATE.get();
//	}
        
  //      public String getReturnDate() {
//		return RETURNDATE.get();
//	}
        public void setCname(String t){
		this.CNAME.set(t);
	}
        
        public void setCphonenum(String t){
		this.CPHONENUM.set(t);
	}
        
  //      public void setStartDate(String t){
//		this.STARTDATE.set(t);
//	}
  //      public void setReturnDate(String t){
//		this.RETURNDATE.set(t);
//	}
        
        public Calendar getExpDate() {
		return CARDEXPIRE;
	}
	
	public void setExpDate(Calendar exp){
		this.CARDEXPIRE = exp;
	}
        
         public Calendar getStartDate() {
		return STARTDATE;
	}
	
	public void setStartDate(Calendar exp){
		this.STARTDATE = exp;
	}
        
         public Calendar getReturnDate() {
		return RETURNDATE;
	}
	
	public void setReturnDate(Calendar exp){
		this.RETURNDATE = exp;
	}
        
        
        
        
         public String getAddress() {
		return ADDRESS.get();
	}
         
          public String getCity() {
		return CITY.get();
	}
        
          public void setAddress(String t){
		this.ADDRESS.set(t);
	}
          
          public void setCity(String t){
		this.CITY.set(t);
	}
           public void setCardNum(String t){
		this.CARDNUM.set(t);
	}
          
          public String getCardNum(){
		return CARDNUM.get();
	}
          
          public String getLicense() {
		return DLICENSE.get();
	}
          public void setLicense(String t){
		this.DLICENSE.set(t);
	}
}
