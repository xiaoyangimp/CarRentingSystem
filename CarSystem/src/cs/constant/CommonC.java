package cs.constant;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
*
* @author Jiyu Xiao
*/
public class CommonC {
	public static ObservableList<Integer> Hour
	= FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
	15, 16, 17, 18, 19, 20, 21, 22, 23);
	
	public static ObservableList<String> HourString
	= FXCollections.observableArrayList("select","00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
	"15", "16", "17", "18", "19", "20", "21", "22", "23");
	
    public static ObservableList<String> Paymethod
    = FXCollections.observableArrayList("Type","VISA", "MasterCard", "DebitCard","Cash");
    
	public static ObservableList<String> CarType
	= FXCollections.observableArrayList("Economy", "Compact", 
	"Mid-size", "Standard", "Full-size", "Premium",	"Luxury", "SUV", "Van");
	
	public static ObservableList<String> TruckType
	= FXCollections.observableArrayList("24-foot", "15-foot", "12-foot"
	, "Box-Truck", "Cargo-Van");
	
	public static ObservableList<String> VehicleType
	= FXCollections.observableArrayList("1-car", "2-truck");
	
	public static ObservableList<String> CarEquip
	= FXCollections.observableArrayList("NONE", "Ski rack", "Child safety seat");
	
	public static ObservableList<String> TruckEquip
	= FXCollections.observableArrayList("NONE", "Lift gate", "Car-towing equipment");
}
