package cs.model;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
*
* @author Jiyu Xiao
*/
public class Employee {
	
	private final StringProperty userid;
	private final StringProperty password;
	private final StringProperty position;
	
	/**
	 * Default constructor
	 */
	public Employee() {
		this(null, null);
	}
	
	/**
	 * constructor
	 * @param uid user's id
	 * @param pwd user's password
	 */
	public Employee(String uid, String pwd) {
		this.userid = new SimpleStringProperty (uid);
		this.password = new SimpleStringProperty(pwd);
		this.position = new SimpleStringProperty();
	}
	
	public String getuserid() {
		return userid.get();
	}
	
	public void setuserid(String uid){
		this.userid.set(uid);
	}
	
	public StringProperty useridProperty() {
		return userid;
	}
	
	public String getpassword() {
		return password.get();
	}
	
	public void setpassword(String pwd){
		this.password.set(pwd);
	}
	
	public StringProperty passwordProperty() {
		return password;
	}
	
	public String getposition(){
		return position.get();
	}
	
	public void setposition(String pos){
		this.position.set(pos);
	}
	
	public StringProperty positionProperty(){
		return position;
	}
	
}
