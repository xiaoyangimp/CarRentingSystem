/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.view;

import cs.MainController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author IshmeetSingh
 */
public class RentViewController  {
    
    @FXML
    public Button newRent;
    
   @FXML
    public Button reserved;
   
   
   public void reserved()
   {
    
      try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainController.class.getResource("view/CheckReservationView.fxml"));
			AnchorPane rent = (AnchorPane) loader.load();
		
			CheckReservationViewController con = loader.getController();
			mainvc.setRight(rent);
			con.setMainVC(mainvc);
		} catch (IOException ex) {
                    ex.printStackTrace();
		}    
   }
   
   public void newRent()
   {
    
      try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainController.class.getResource("view/NewRentView.fxml"));
			AnchorPane rent = (AnchorPane) loader.load();
		
			NewRentViewController con = loader.getController();
			mainvc.setRight(rent);
			con.setMainVC(mainvc);
		} catch (IOException ex) {
                    ex.printStackTrace();
		}    
   }
   
   
   
   
   
   
   private MainViewController mainvc;
   
        public void setMainVC(MainViewController m) {
		this.mainvc = m;
	}
	
	public MainViewController getMainVC(){
		return mainvc;
	}
   
   
      
}
