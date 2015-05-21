package cs.model;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
*
* @author IshmeetSingh
*/
public class NewRent {

	private final IntegerProperty rentid;
	private Calendar startdate;
	private Calendar returndate;
	private Set<Equipment> equip;
	private final IntegerProperty bid;
	private final StringProperty vid;
	private final StringProperty cname;
	private final StringProperty cphonenum;
        private final StringProperty address;
        private final StringProperty city;
        private final StringProperty license;
        private final StringProperty creditCard;
        private Calendar expDate;
        	
	public NewRent() {
		this.rentid = new SimpleIntegerProperty (-1);
		this.startdate = Calendar.getInstance();
		this.returndate = Calendar.getInstance();
		this.equip = new HashSet<Equipment>();
		this.bid = new SimpleIntegerProperty (-1);
		this.vid = new SimpleStringProperty (null);
		this.cname = new SimpleStringProperty(null);
		this.cphonenum = new SimpleStringProperty(null);
                this.address = new SimpleStringProperty(null);
                this.city = new SimpleStringProperty(null);
                this.license = new SimpleStringProperty(null);
                this.creditCard = new SimpleStringProperty(null);
                this.expDate = Calendar.getInstance();
	}
	
	public int getRentid() {
		return rentid.get();
	}
	
	public void setRentid(int t){
		this.rentid.set(t);
	}
	
	public Calendar getStartdate() {
		return startdate;
	}
	
	public void setStartdate(Calendar startd){
		this.startdate = startd;
	}
	
	
	public Calendar getReturndate(){
		return returndate;
	}
	
	public void setReturndate(Calendar returnd){
		this.returndate = returnd;
	}
	
	public Set<Equipment> getequip(){
		return equip;
	}
	
	public void setequip(Set<Equipment> e){
		this.equip = e;
	}
	
	public int getBid() {
		return bid.get();
	}
	
	public void setBid(int t){
		this.bid.set(t);
	}
	
	public String getVid() {
		return vid.get();
	}
	
	public void setVid(String t){
		this.vid.set(t);
	}
	
	public String getCname() {
		return cname.get();
	}
	
	public void setCname(String n){
		this.cname.set(n);
	}
	
	public String getCphonenum() {
		return cphonenum.get();
	}
	
	public void setCphonenum(String n){
		this.cphonenum.set(n);
	}
        
        public Calendar getExpDate() {
		return expDate;
	}
	
	public void setExpDate(Calendar exp){
		this.expDate = exp;
	}
        
        public String getAddress() {
		return address.get();
	}
	
	public void setAddress(String n){
		this.address.set(n);
	}
        
        public String getCity() {
		return city.get();
	}
	
	public void setCity(String n){
		this.city.set(n);
	}
        
        public String getLicense() {
		return license.get();
	}
	
	public void setLicense(String n){
		this.license.set(n);
	}
        
        public String getCreditCard() {
		return creditCard.get();
	}
	
	public void setCreditCard(String n){
		this.creditCard.set(n);
	}
        
        
}
