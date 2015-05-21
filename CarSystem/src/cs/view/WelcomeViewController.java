package cs.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Calendar;

/**
*
* @author Jiyu Xiao
*/
public class WelcomeViewController {
	@FXML
	private Label greet = new Label("");;
	
	public WelcomeViewController(){
	}
	
	@FXML
	public void initiate(){
		Calendar date = Calendar.getInstance();
		
		int hour = date.get(Calendar.HOUR_OF_DAY);
		
		if( hour >=6 && hour < 12){
			greet.setText("  Good Morning!");
		} else if ( hour >= 12 && hour < 18) {
			greet.setText(" Good Afternoon!");
		} else if ( hour >= 18 && hour < 22) {
			greet.setText("  Good Evening!");
		} else {
			greet.setText("Enjoy Night Time!");
		}
	}
}
