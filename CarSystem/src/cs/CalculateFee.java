package cs;

import cs.model.NewRent;
import cs.model.Reservation;
import cs.model.Equipment;
import cs.model.VehicleType;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Calendar;


/**
*
* @author Jiyu Xiao
*/
public class CalculateFee {
	public static final long MSINWEEK = 1000 * 3600 * 24 * 7;
	public static final long MSINDAY = 1000 * 3600 * 24;
	public static final long MSINHOUR = 1000 * 3600;
	
	/**
	 * A static function that calculate the estimated fee for a reservation
	 * 
	 * @param r Reservation (Object)
	 * @return estimated fee for rent
	 */
	public static double estimateFee(Reservation r) {
		double result = 0.0;
		double generalfee = 0.0, equipmentfee = 0.0;
		VehicleType vt = new VehicleType();
		
		PreparedStatement ps;
		ResultSet rs;
		
		try{
			ps = SQLConnect.getcon().prepareStatement("Select * from VEHICLETYPE vt join VEHICLE v where vt.type = v.type AND v.vid = ?");
			ps.setString(1, r.getVid());
			rs = ps.executeQuery();

			
			if(rs.next()){
				vt.setWeekrate(rs.getDouble("w_rate"));
				vt.setDayrate(rs.getDouble("d_rate"));
				vt.setHourrate(rs.getDouble("h_rate"));
				vt.setKilorate(rs.getDouble("kilo_rate"));
				vt.setWeekinsurance(rs.getDouble("w_insu"));
				vt.setDayinsurance(rs.getDouble("d_insu"));
				vt.setHourinsurance(rs.getDouble("h_insu"));
			}
			
			ps.close();
			
			generalfee = calculateGeneralFee(r.getStartdate(), r.getReturndate(), vt, false);
			
			for( Equipment equip : r.getequip() ) {
				ps = SQLConnect.getcon().prepareStatement("Select * from EQUIPMENT where ename = ?");
				ps.setString(1, equip.getName());
				rs = ps.executeQuery();
				
				if(rs.next()) {
					equip.setHourrate(rs.getDouble("h_rate"));
					equip.setDayrate(rs.getDouble("d_rate"));
				}
				
				equipmentfee += calculateEquipmentFee(r.getStartdate(), r.getReturndate(), equip);
				ps.close();
			}
			
		} catch (SQLException ex) {
			MainController.showWarningDialog(ex.getMessage());
		}
		
		result = generalfee + equipmentfee;
		
		BigDecimal bd = new BigDecimal(result);
		double fee = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		return fee;
	}
	
	
	public static double estimateFee(NewRent r) {
		double result = 0.0;
		double generalfee = 0.0, equipmentfee = 0.0;
		VehicleType vt = new VehicleType();
		
		PreparedStatement ps;
		ResultSet rs;
		
		try{
			ps = SQLConnect.getcon().prepareStatement("Select * from VEHICLETYPE vt join VEHICLE v where vt.type = v.type AND v.vid = ?");
			ps.setString(1, r.getVid());
			rs = ps.executeQuery();

			
			if(rs.next()){
				vt.setWeekrate(rs.getDouble("w_rate"));
				vt.setDayrate(rs.getDouble("d_rate"));
				vt.setHourrate(rs.getDouble("h_rate"));
				vt.setKilorate(rs.getDouble("kilo_rate"));
				vt.setWeekinsurance(rs.getDouble("w_insu"));
				vt.setDayinsurance(rs.getDouble("d_insu"));
				vt.setHourinsurance(rs.getDouble("h_insu"));
			}
			
			ps.close();
			
			generalfee = calculateGeneralFee(r.getStartdate(), r.getReturndate(), vt, false);
			
			for( Equipment equip : r.getequip() ) {
				ps = SQLConnect.getcon().prepareStatement("Select * from EQUIPMENT where ename = ?");
				ps.setString(1, equip.getName());
				rs = ps.executeQuery();
				
				if(rs.next()) {
					equip.setHourrate(rs.getDouble("h_rate"));
					equip.setDayrate(rs.getDouble("d_rate"));
				}
				
				equipmentfee += calculateEquipmentFee(r.getStartdate(), r.getReturndate(), equip);
				ps.close();
			}
			
		} catch (SQLException ex) {
			MainController.showWarningDialog(ex.getMessage());
		}
		
		result = generalfee + equipmentfee;
		
		BigDecimal bd = new BigDecimal(result);
		double fee = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		return fee;
	}
	
	/**
	 * A static function that calculates the rental fee and insurance fee
	 * 
	 * @param startdate start date (Calendar)
	 * @param returndate return date (Calendar)
	 * @param vt VehicleType (Object)
	 * @param isroadstar a signal that indicates whether or not the customer is a road star
	 * @return a sum of rental fee and insurance fee
	 */
	private static double calculateGeneralFee(Calendar startdate, Calendar returndate, VehicleType vt, boolean isroadstar) {
		double temp;
		double generalfee, insurance;
		long total;
		int week, day, hour, remain;
		
		total = returndate.getTimeInMillis() - startdate.getTimeInMillis();
		week = (int) (total / MSINWEEK);
		day = (int) ( ( total - week * MSINWEEK ) / MSINDAY );
		hour = (int) ( ( total - week * MSINWEEK - day * MSINDAY ) / MSINHOUR );
		remain = (int) ( total - week * MSINWEEK - day * MSINDAY  - hour * MSINHOUR );
		
		if(remain != 0) hour += 1;
		
		generalfee = week * vt.getWeekrate() + day * vt.getDayrate() + hour * vt.getHourrate();
		insurance = week * vt.getWeekinsurance() + day * vt.getDayinsurance() + hour * vt.getHourinsurance();
		
		if(isroadstar) insurance = insurance * 0.5;
		
		temp = generalfee + insurance;

		return temp;
	}
	
	/**
	 * A static function that calculates the equipment fee for a rent
	 * 
	 * @param startdate start date (Calendar)
	 * @param returndate return date (Calendar)
	 * @param e Equipment (Object) 
	 * @return a sum of fee by renting equipments
	 */
	private static double calculateEquipmentFee(Calendar startdate, Calendar returndate, Equipment e){
		double temp = 0.0;
		long total;
		int day, hour, remain;
				
		total = returndate.getTimeInMillis() - startdate.getTimeInMillis();
		day = (int) ( total / MSINDAY );
		hour = (int) ( ( total - day * MSINDAY ) / MSINHOUR );
		remain = (int) ( total - day * MSINDAY - hour * MSINHOUR );
		
		if(remain != 0) hour += 1;
		
		temp = day * e.getDayrate() + hour * e.getHourrate();

		return temp;
	}
	
	
}
