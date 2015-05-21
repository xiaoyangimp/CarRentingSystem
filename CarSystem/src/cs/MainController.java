package cs;

import java.io.IOException;

import cs.model.Employee;
import cs.view.MainViewController;
import cs.view.RentViewController;
import cs.view.ReserveViewController;
import cs.view.ReturnViewController;
import cs.view.ToolbarController;
import cs.view.CancelReservationViewController;
import cs.view.WelcomeViewController;
import cs.view.WarningDialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
*
* @author Jiyu Xiao
*/
public class MainController {

	private Stage primaryStage;
	private Employee currentUser = new Employee();
	private AnchorPane mainView;
	private MainViewController mainvc;
	private SuperRentStart srs;
	
	
	public MainController(){
		
	}
	
	/**
	 * A function that create the primary stage
	 * 
	 * @param e Employee (Object)
	 */
	public void showGUI(Employee e) {
		this.primaryStage = new Stage();
		this.primaryStage.setTitle("SuperRent System");
		this.currentUser = e; 
		
		showMainGUI();
	}
	
	/**
	 *  A function that create the main panel and load all the other panels 
	 *  according to the current position of the user
	 */
	public void showMainGUI() {
		try {
			// Load main View
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainController.class.getResource("view/MainView.fxml"));
			mainView = (AnchorPane) loader.load();
			mainvc = loader.getController();
			
			
			ToolbarController toolcon;
			WelcomeViewController welcomecon;
			AnchorPane toolbar, welcome;
			
			mainvc.setMain(this);
			mainvc.setParam(currentUser.getuserid(), currentUser.getposition());
			
			// Load tool bar 
			if(currentUser.getposition().equals("adm")){
				FXMLLoader loader2 = new FXMLLoader();
				loader2.setLocation(MainController.class.getResource("view/AdminToolbar.fxml"));
				toolbar = (AnchorPane) loader2.load();
				
				toolcon = loader2.getController();
				toolcon.setMain(this);
				mainvc.setLeft(toolbar);
			} else if (currentUser.getposition().equals("man")) {
				FXMLLoader loader2 = new FXMLLoader();
				loader2.setLocation(MainController.class.getResource("view/ManagerToolbar.fxml"));
				toolbar = (AnchorPane) loader2.load();
				
				toolcon = loader2.getController();
				toolcon.setMain(this);
				mainvc.setLeft(toolbar);
			} else if (currentUser.getposition().equals("cle")) {
				FXMLLoader loader2 = new FXMLLoader();
				loader2.setLocation(MainController.class.getResource("view/ClerkToolbar.fxml"));
				toolbar = (AnchorPane) loader2.load();
				
				toolcon = loader2.getController();
				toolcon.setMain(this);
				mainvc.setLeft(toolbar);
			} else {
				
			}
			
			
			// Load welcome view
			FXMLLoader loader3 = new FXMLLoader();
			loader3.setLocation(MainController.class.getResource("view/WelcomeView.fxml"));
			welcome = (AnchorPane) loader3.load();
			
			welcomecon = loader3.getController();
			mainvc.setRight(welcome);
			welcomecon.initiate();
			
			Scene scene = new Scene(mainView);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public SuperRentStart getStart(){
		return srs;
	}
	
	public void setStart( SuperRentStart s) {
		this.srs = s;
	}
	
	public AnchorPane getMainView() {
		return mainView;
	}
	
	public MainViewController getMainViewController(){
		return mainvc;
	}
	
	/**
	 * A function that loads the Reserve view
	 */
	public void reserve(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainController.class.getResource("view/ReserveView.fxml"));
			AnchorPane reserve = (AnchorPane) loader.load();
		
			ReserveViewController con = loader.getController();
			mainvc.setRight(reserve);
			con.setMainVC(mainvc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * A function that load CancelReservationView
	 */
	public void cancelReserve(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainController.class.getResource("view/CancelReservationView.fxml"));
			AnchorPane cancelreserve = (AnchorPane) loader.load();
		
			CancelReservationViewController con = loader.getController();
			mainvc.setRight(cancelreserve);
			con.setMainVC(mainvc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * A function that load ClerkQueryView
	 */
	public void clerkquery(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainController.class.getResource("view/ClerkQueryView.fxml"));
			AnchorPane clerkquery = (AnchorPane) loader.load();			
			
			loader.getController();
			mainvc.setRight(clerkquery);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * A function that load UserManageView
	 */
	public void userManage(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainController.class.getResource("view/UserManageView.fxml"));
			AnchorPane usermanage = (AnchorPane) loader.load();
//		
//			UserManageViewController con = loader.getController();
			loader.getController();
			mainvc.setRight(usermanage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * A function that load EquipmentView
	 */
	public void equipmentManage(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainController.class.getResource("view/EquipmentView.fxml"));
			AnchorPane equip = (AnchorPane) loader.load();
//		
//			EquipmentViewController con = loader.getController();
			loader.getController();
			mainvc.setRight(equip);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * A function that load RentView
	 */
    public void rent(){
    	try {
             
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(MainController.class.getResource("view/RentView.fxml"));
    		AnchorPane rentv = (AnchorPane) loader.load();
	
    		RentViewController con = loader.getController();
    		mainvc.setRight(rentv);
    		con.setMainVC(mainvc);
                    
    	} catch (IOException e) {
		e.printStackTrace();
    	}
	}
	
	
	/**
	 * A function that load ReturnView
	 */
    public void returnV(){
    	try {
             
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(MainController.class.getResource("view/ReturnView.fxml"));
    		AnchorPane returnv = (AnchorPane) loader.load();
	
    		ReturnViewController con = loader.getController();
    		mainvc.setRight(returnv);
    		con.setMainVC(mainvc);
                    
    	} catch (IOException e) {
		e.printStackTrace();
    	}
	}
    
    /**
	 * A function that load ManageFleetView
	 */
    public void manageFleet(){
    	try {
             
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(MainController.class.getResource("view/FleetView.fxml"));
    		AnchorPane mf = (AnchorPane) loader.load();
	
    		loader.getController();
    		mainvc.setRight(mf);
                    
    	} catch (IOException e) {
		e.printStackTrace();
    	}
	}
    
    /**
   	 * A function that load ReportView
   	 */
       public void report(){
       	try {
                
       		FXMLLoader loader = new FXMLLoader();
       		loader.setLocation(MainController.class.getResource("view/ReportFXML.fxml"));
       		AnchorPane rp = (AnchorPane) loader.load();
   	
       		loader.getController();
       		mainvc.setRight(rp);
                       
       	} catch (IOException e) {
   		e.printStackTrace();
       	}
   	}
	
   	/**
   	 * A function that load EditCustomer View
   	 */
   	public void editCustomer(){
   		try {
   			FXMLLoader loader = new FXMLLoader();
   			loader.setLocation(MainController.class.getResource("view/EditCustomerView.fxml"));
   			AnchorPane ec = (AnchorPane) loader.load();			
   			
   			loader.getController();
   			mainvc.setRight(ec);
   		} catch (IOException e) {
   			e.printStackTrace();
   		}
   	}
       
	/**
	 * A static function that shows a warning dialog stage
	 * 
	 * @param toshow the string that will be showed in that dialog
	 */
	public static void showWarningDialog (String toshow) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainController.class.getResource("view/WarningDialog.fxml"));
			AnchorPane dialog = (AnchorPane) loader.load();
		
			WarningDialogController con = loader.getController();
			con.setText(toshow);
			
			Stage dialogstage = new Stage();
			Scene sc = new Scene(dialog);
			dialogstage.setScene(sc);
			
			con.setStage(dialogstage);
			dialogstage.showAndWait();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
