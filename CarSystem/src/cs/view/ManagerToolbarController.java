package cs.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import cs.MainController;

/**
*
* @author Jiyu Xiao
*/
public class ManagerToolbarController extends ToolbarController {

	
	@FXML
	private Button report;
	
	@FXML
	private Button managefleet;
	
	private MainController mainc;
	
	public ManagerToolbarController(){
		
	}
	
	public MainController getMain(){
		return mainc;
	}
	
	public void setMain(MainController main){
		this.mainc = main;
	}

	
	@FXML
	public void manageFleet(){
		mainc.manageFleet();
	}

	
	@FXML
	public void report(){
		mainc.report();
	}
}
