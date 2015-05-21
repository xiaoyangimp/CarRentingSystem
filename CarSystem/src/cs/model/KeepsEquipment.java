package cs.model;

/**
*
* @author Jiyu Xiao
*/
public class KeepsEquipment {
	private String branch;
	private String equipment;
	private int quantity;
	
	/**
	 * Default constructor
	 */
	public KeepsEquipment(){
		branch = "";
		equipment = "";
		quantity = 0;
	}
	
	public String getBranch(){
		return branch;
	}
	
	public void setBranch(String b) {
		this.branch = b;
	}
	
	public String getEquipment(){
		return equipment;
	}
	
	public void setEquipment(String eq) {
		this.equipment = eq;
	}
	
	public int getQuantity(){
		return quantity;
	}
	
	public void setQuantity(int q){
		this.quantity = q;
	}
}
