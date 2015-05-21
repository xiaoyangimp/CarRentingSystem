package cs.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import cs.MainController;

/**
*
* @author Jiyu Xiao
*/
public class AdminToolbarController extends ToolbarController {
	@FXML
	private Button reserveV;
	
	@FXML
	private Button rentV;
	
	@FXML
	private Button returnV;
	
	@FXML
	private Button report;
	
	@FXML 
	private Button cancelReservation;
	
	@FXML
	private Button usermanage;
	
	@FXML
	private Button clerkq;
	
	@FXML
	private Button managefleet;
	
	@FXML
	private Button equipmentmanage;
	
	@FXML
	private Button customer;
	
	private MainController mainc;
	
	public AdminToolbarController(){
		
	}
	
	public MainController getMain(){
		return mainc;
	}
	
	public void setMain(MainController main){
		this.mainc = main;
	}
	
	@FXML
	public void reserve(){
		mainc.reserve();
	}
	
	@FXML
	public void cancelReservation(){
		mainc.cancelReserve();
	}
	
	@FXML
	public void userManage(){
		mainc.userManage();
	}
	
	@FXML
	public void clerkQuery(){
		mainc.clerkquery();
	}
	
	@FXML
	public void equipmentManage(){
		mainc.equipmentManage();
	}
	
	@FXML
	public void rent(){
		mainc.rent();
	}
	
	@FXML
	public void manageFleet(){
		mainc.manageFleet();
	}
	
	@FXML
	public void returnv(){
		mainc.returnV();
	}
	
	@FXML
	public void report(){
		mainc.report();
	}
	
	@FXML
	public void customer(){
		mainc.editCustomer();
	}
}
