package cs.model;


/**
*
* @author Jiyu Xiao
*/
public class ReservationToCancel extends Reservation {
	

	private String startdateinstring;
	private String returndateinstring;
	private String vehicletype;

	
	/**
	 * Default constructor
	 */
	public ReservationToCancel() {
		this("", "");
	}
	
	/**
	 * constructor
	 * @param sd start date (String)
	 * @param rd return date (String)
	 */
	public ReservationToCancel(String sd, String rd){
		super();
		this.startdateinstring = sd;
		this.returndateinstring = rd;
	}
	
	public String getStartdateinstring(){
		return startdateinstring;
	}
	
	public void setStartdateinstring( String sd ){
		this.startdateinstring = sd;
	}
	
	public String getReturndateinstring(){
		return returndateinstring;
	}
	
	public void setReturndateinstring( String rd ){
		this.returndateinstring = rd;
	}
	
	public String getVehicletype(){
		return vehicletype;
	}

	public void setVehicletype(String vt) {
		this.vehicletype = vt;
	}
	
}
