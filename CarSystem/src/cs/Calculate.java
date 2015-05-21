package cs;

import cs.model.VehicleType;
import cs.model.Return;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;

/**
*
* @author Zhenhong Dai
*/
public class Calculate {
	public static final long MSINWEEK = 1000 * 3600 * 24 * 7;
	public static final long MSINDAY = 1000 * 3600 * 24;
	public static final long MSINHOUR = 1000 * 3600;
        long diff;
        private int redeemday;
        private int tankdifference;
        private int expirehour;
        private boolean isroadstar;
        private static Set<String> equipment_type;
        private String rent_id;
        private static double rgeneralfee;
        private static double rinsurancefee;
        private static double radditionequipmentfee;
        private static double rexpiredreturnfee;
        private static double radditionaltankfee;
        private static double rroadstarreduction = 0;
        private static double rredeemreduction;
        private static double radditionalexpirefee;

        
        public Calculate(int redeemday, boolean roadstar, int tankdifference, Set<String> equipment_type, String rent_id,int expirehour){
            this.redeemday = redeemday;
            this.isroadstar= roadstar;
            this.tankdifference=tankdifference;
            this.rent_id=rent_id;
            this.expirehour=expirehour;
        }
        
	public double estimateFee(Return r) throws ParseException {
		double result = 0.0;
		double generalfee = 0.0, equipmentfee = 0.0;
		VehicleType vt = new VehicleType();
            
		PreparedStatement ps;
		ResultSet rs;

		try{
			ps = SQLConnect.getcon().prepareStatement("Select * from VEHICLETYPE vt join VEHICLE v where vt.TYPE = v.TYPE AND v.VID = ?");
			ps.setString(1, r.getPlateNum());
			rs = ps.executeQuery();
  
			
			if(rs.next()){
				vt.setWeekrate(rs.getDouble("W_RATE"));
				vt.setDayrate(rs.getDouble("D_RATE"));
				vt.setHourrate(rs.getDouble("H_RATE"));
				vt.setKilorate(rs.getDouble("KILO_RATE"));
				vt.setWeekinsurance(rs.getDouble("W_INSU"));
				vt.setDayinsurance(rs.getDouble("D_INSU"));
				vt.setHourinsurance(rs.getDouble("H_INSU"));
			}
			
			ps.close();

                        
			generalfee = calculateGeneralFee(r.getStartdate(), r.getReturndate(), vt, isroadstar,redeemday,tankdifference,expirehour);			
                        equipmentfee=calculateEquipmentFee(r.getStartdate(), r.getReturndate(), r.getequipment_type(),rent_id);

			
		} catch (SQLException ex) {
			System.out.println("Message:" + ex.getMessage());
		}		
		result = generalfee + equipmentfee;
		
		return result;
	}
	       

	private static double calculateGeneralFee(String startdate, String returndate, VehicleType vt, boolean isroadstar, int redeemday,int tankdifference, int expirehour) throws ParseException {
		double temp = 0.0;
		double generalfee, insurance;
		long week, day, hour, remain;
  
                String dateStart=startdate.substring(0,13);
                String dateStop=returndate.substring(0,13);
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
                       
                java.util.Date d1 = null;
                java.util.Date d2 = null;

                d1 = format.parse(dateStart);
                d2 = format.parse(dateStop);
                
                System.out.println("Date in yyy-MM-dd HH format is: "+format.format(d1));
                System.out.println("Date in yyy-MM-dd HH format is: "+format.format(d2));

                //in milliseconds
                long diff = d2.getTime() - d1.getTime();

		week = diff / MSINWEEK;
		day = ( diff - week * MSINWEEK ) / MSINDAY;
		hour = ( diff - week * MSINWEEK - day * MSINDAY ) / MSINHOUR;
		remain =  diff - week * MSINWEEK - day * MSINDAY  - hour * MSINHOUR;		
		
                if(remain != 0) hour += 1;
		
		generalfee = week * vt.getWeekrate() + day * vt.getDayrate() + hour * vt.getHourrate();
		insurance = week * vt.getWeekinsurance() + day * vt.getDayinsurance() + hour * vt.getHourinsurance();
                rinsurancefee = insurance;
                if(isroadstar){
                    insurance = insurance * 0.5;
                    rroadstarreduction = insurance;
                }
           
		temp = generalfee + insurance-redeemday*vt.getDayrate()+2*tankdifference+2*vt.getHourrate()*expirehour;
                rgeneralfee = generalfee;
                rredeemreduction = redeemday*vt.getDayrate();
                radditionaltankfee = 2*tankdifference;
                radditionalexpirefee= 2*vt.getHourrate()*expirehour;                
                
		return temp;
	}
	
	private static double calculateEquipmentFee(String startdate, String returndate, Set<String> equipment, String rent_id) throws ParseException{
		double temp = 0.0;
                double hourly_rate=0.0;
                double dayly_rate=0.0;
                
                long day, hour, remain;

                String dateStart=startdate.substring(0,13);
                String dateStop=returndate.substring(0,13);
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
                       
                java.util.Date d1 = null;
                java.util.Date d2 = null;

                d1 = format.parse(dateStart);
                d2 = format.parse(dateStop);

                //in milliseconds
                long diff = d2.getTime() - d1.getTime();

		day = diff / MSINDAY;
		hour = ( diff - day * MSINDAY ) / MSINHOUR;               
		remain =  diff - day * MSINDAY - hour * MSINHOUR;
              
		if(remain != 0) hour += 1;
                
                Statement stmt,stmt1;
                ResultSet rs,rs1;
 
                try{
                String sql="select ENAME from RENTEDEQUIPMENT WHERE RENTID= '"+rent_id+"'";
                stmt = SQLConnect.getcon().createStatement();
                rs = stmt.executeQuery(sql);

                    for(String equip : equipment){
                        String sql1="select H_RATE, D_RATE from EQUIPMENT WHERE ENAME= '"+equip+"'";
                        stmt1 = SQLConnect.getcon().createStatement();
                        rs1 = stmt1.executeQuery(sql1);
       
                        if(rs1.next()){
                            hourly_rate=rs1.getDouble(1);
                            System.out.println("       "+hourly_rate);
                            dayly_rate=rs1.getDouble(2);
                        } else {
                            hourly_rate=0;
                            dayly_rate=0;
                        }
                        
                        temp += day * dayly_rate + hour * hourly_rate;                      
                    }
                }
                
                catch (SQLException ex) {
			System.out.println("Database error.");
		}                          
                radditionequipmentfee=temp;		
		return temp;
	}

    public static double getRgeneralfee() {
        return rgeneralfee;
    }
 
    public static double getRinsurancefee() {
        return rinsurancefee;
    }

    public static double getRadditionequipmentfee() {
        return radditionequipmentfee;
    }

    public static double getRexpiredreturnfee() {
        return rexpiredreturnfee;
    }

    public static double getRadditionaltankfee() {
        return radditionaltankfee;
    }

    public static double getRroadstarreduction() {
        return rroadstarreduction;
    }

    public static double getRredeemreduction() {
        return rredeemreduction;
    }
    
    
    public static double getRadditionalexpirefee(){
        return radditionalexpirefee;
    }
	
}

