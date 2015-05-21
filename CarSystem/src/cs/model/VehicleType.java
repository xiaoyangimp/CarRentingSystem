package cs.model;


/**
*
* @author Jiyu Xiao
*/
public class VehicleType {
	private double weekrate;
	private double dayrate;
	private double hourrate;
	private double kilorate;
	private double weekinsurance;
	private double dayinsurance;
	private double hourinsurance;
	
	/**
	 * Default constructor
	 */
	public VehicleType(){
		weekrate = 0.0;
		dayrate = 0.0;
		hourrate = 0.0;
		kilorate = 0.0;
		weekinsurance = 0.0;
		dayinsurance = 0.0;
		hourinsurance = 0.0;
	}
	
	public double getWeekrate(){
		return weekrate;
	}
	
	public void setWeekrate( double wr){
		this.weekrate = wr;
	}
	
	public double getDayrate(){
		return dayrate;
	}
	
	public void setDayrate( double dr){
		this.dayrate = dr;
	}
	
	public double getHourrate(){
		return hourrate;
	}
	
	public void setHourrate( double hr){
		this.hourrate = hr;
	}
	
	public double getKilorate(){
		return kilorate;
	}
	
	public void setKilorate( double kr){
		this.kilorate = kr;
	}
	
	public double getWeekinsurance(){
		return weekinsurance;
	}
	
	public void setWeekinsurance( double wi){
		this.weekinsurance = wi;
	}
	
	public double getDayinsurance(){
		return dayinsurance;
	}
	
	public void setDayinsurance( double di){
		this.dayinsurance = di;
	}
	
	public double getHourinsurance(){
		return hourinsurance;
	}
	
	public void setHourinsurance( double hi){
		this.hourinsurance = hi;
	}
}
