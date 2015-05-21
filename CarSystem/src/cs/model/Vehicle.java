package cs.model;

import java.util.Calendar;

/**
*
* @author Jiyu Xiao
*/
public class Vehicle {
	private String branch;
	private int bid;
	private String vid;
	private String type;
	private int odoread;
	private Calendar adate;
	private String acquiredate;
	private double price;
	private String date;

	
	/**
	 * Default constructor
	 */
	public Vehicle(){
		branch = "";
		vid = "";
		type = "";
		odoread = 0;
		adate = Calendar.getInstance();
		acquiredate = "";
		price = 0;
		date = "";
	}
	
	public Vehicle(String id,String t,String d,double ord,int b) {
		this.vid = id;
		this.type = t;
		this.date = d;
		this.odoread = (int) ord;
		this.bid = b;
	}
	
	public String getBranch(){
		return branch;
	}
	
	public void setBranch(String b) {
		this.branch = b;
	}
	
	public String getVid(){
		return vid;
	}
	
	public void setVid(String v){
		this.vid = v;
	}
	
	public String getType(){
		return type;
	}
	
	public void setType(String t){
		this.type = t;
	}
	
	public int getOdoread(){
		return odoread;
	}
	
	public void setOdoread(int o){
		this.odoread = o;
	}
	
	public String getAcquiredate(){
		return acquiredate;
	}
	
	public void setAcquiredate( Calendar a) {
		this.adate = a;
		acquiredate = adate.getTime().toString();
	}
	
	public double getPrice(){
		return price;
	}
	
	public void setPrice( double p){
		this.price = p;
	}
	
	public String getDate(){
        return date;
    }
	
	public void setDate(String d){
		this.date = d;
	}
	
	public int getBid(){
        return bid;
    }
    
	public void setBid(int b){
		this.bid = b;
	}
}
