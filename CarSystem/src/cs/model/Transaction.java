package cs.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.Calendar;

public class Transaction {
	
	private final IntegerProperty tid;
	private Calendar startdate;
	private Calendar returndate;
	private final StringProperty equip;
	
	/**
	 * Default constructor
	 */
	public Transaction() {
		this.tid = new SimpleIntegerProperty (-1);
		this.startdate = Calendar.getInstance();
		this.returndate = Calendar.getInstance();
		this.equip = new SimpleStringProperty(null);
	}

	
	public int gettid() {
		return tid.get();
	}
	
	public void settid(int t){
		this.tid.set(t);
	}
	
	public Calendar getstartdate() {
		return startdate;
	}
	
	public void setstartdate(Calendar startd){
		this.startdate = startd;
	}
	
	
	public Calendar getreturndate(){
		return returndate;
	}
	
	public void setreturndate(Calendar returnd){
		this.returndate = returnd;
	}
	
	public String getequip(){
		return equip.get();
	}
	
	public void setequip(String returnd){
		this.equip.set(returnd);
	}
}
