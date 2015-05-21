package cs.model;

import java.util.Set;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
*
* @author Zhenhong Dai
*/
public class Return {
	
        private final SimpleIntegerProperty branch_id;
	private final SimpleStringProperty PlateNum;
	//private final SimpleIntegerProperty confirmnum;
	private final SimpleStringProperty customer_name;
	private final SimpleStringProperty customer_phone;
        private final SimpleStringProperty cardnum;
        private final SimpleStringProperty Expiredate;
        private final SimpleStringProperty Startdate;
        private final SimpleStringProperty Returndate;
        private Set<String> equipment_set;
        

    public Return(String PlateNum, int branch_id, String customer_name, String customer_phone, String cardnum,String Expiredate, String Startdate, String Returndate, Set<String> e) {
        this.branch_id = new SimpleIntegerProperty(branch_id);
        this.PlateNum = new SimpleStringProperty(PlateNum);
        this.customer_name = new SimpleStringProperty(customer_name);
        this.customer_phone = new SimpleStringProperty(customer_phone);
        this.cardnum = new SimpleStringProperty(cardnum);
        this.Expiredate=new SimpleStringProperty(Expiredate);
        this.Startdate=new SimpleStringProperty(Startdate);
        this.Returndate=new SimpleStringProperty(Returndate);
        this.equipment_set = e;
    }

    public int getBranch_id() {
        return branch_id.get();
    }

    public String getPlateNum() {
        return PlateNum.get();
    }

    public String getCustomer_name() {
        return customer_name.get();
    }

    public String getCustomer_phone() {
        return customer_phone.get();
    }

    public String getCardnum() {
        return cardnum.get();
    }
    
    public String getExpiredate(){
        return Expiredate.get();
    }
    
    public String getReturndate(){
        return Returndate.get();
    }
    
    public String getStartdate(){
        return Startdate.get();
    }

    public Set<String> getequipment_type(){
            return equipment_set;
    }

}
