package cs.model;

/**
*
* @author Jiyu Xiao
*/
public class Equipment {
	private String name;
	private double hourrate;
	private double dayrate;
	
	/**
	 * Default constructor
	 */
	public Equipment () {
		this("");
	}
	
	/**
	 * constructor
	 * @param e equipment name
	 */
	public Equipment (String e) {
		this.name = e;
		hourrate = 0.0;
		dayrate = 0.0;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String e) {
		this.name = e;
	}
	
	public double getHourrate(){
		return hourrate;
	}
	
	public void setHourrate(double hr){
		this.hourrate = hr;
	}
	
	public double getDayrate(){
		return dayrate;
	}
	
	public void setDayrate(double dr){
		this.dayrate = dr;
	}
}
