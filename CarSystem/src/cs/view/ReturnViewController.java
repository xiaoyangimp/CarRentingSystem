package cs.view;

import cs.Calculate;
import cs.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import cs.constant.CommonC;
import cs.SQLConnect;
import cs.model.Return;
import java.time.LocalDate;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
*
* @author Zhenhong Dai
*/
public class ReturnViewController
{
        
    
            Statement stmt;
            ResultSet rs;
            String platenumber =new String();
            String phonenumber = new String();
            String creditnumber= new String();
            int bid=0;
            int newbid=0;
            String cname=new String();
            String expiredate=new String();
            String Startdate =new String();
            String Returndate= new String();
            String Actualreturndate=new String();
            String dateStart=new String();
            String dateStop=new String();
            String dateexpected=new String();
            String vtype=new String();
            String rent_id=new String();
            String equipment_type=new String();
            Set<String> eset = new HashSet<String>();
            private Return myreturn;
            //private Return myreturn1;
            private MainViewController mainvc;
            long diff;
            long diff_expire;
            int rate;
            int newpoint;
            int redeemday=0;
            Calculate cl;
            boolean Roadstar;
            int fulltank;
            int actualtank;
            int tankdifference;
            int expirehour;
            double calculateresult;

    	@FXML
	private DatePicker returndate;
        
        @FXML
	private ChoiceBox<String> returnhour;
        
       @FXML
	private ChoiceBox<String> pay_type;
                
        @FXML
        private TextField tankstatustextfield;
        
        @FXML
        private Label available_points; 
                        
        @FXML
        private TextField platenumbertextfield;
        
        @FXML
        private TextField branchid;
       
        @FXML
        private TextField odometertextfield;
       
        @FXML
        private TextField pointswantredeem;
        
        @FXML
        private TextField tankfulltextfield;
        
        @FXML
        private TextField cardnumber;
       
        @FXML
        private TableView<Return> return_info;
	
        @FXML
        private TableColumn<Return,String> PlateNum;
        
        @FXML
        private TableColumn<Return,Integer> branch_id;
        
        @FXML
        private TableColumn<Return,String> customer_name;
        
        @FXML
        private TableColumn<Return,String> customer_phone;
        
        @FXML
        private TableColumn<Return,String> Expiredate;
                
        @FXML
        private TableColumn<Return,String> cardnum;
        
        @FXML
        private Button calculationbutton;
        
        @FXML
        private Button platenumberconfirm;
        
        @FXML
        private Button redeembutton;
        
        @FXML
        private Button branchconfirm;
        
        @FXML
        private Button CheckOverdue;
        
        @FXML
        private Button Odometerconfirm;
        
        @FXML
        private Button tankstatusbutton;
        
        @FXML
        private Button paymentmethod;
        
        @FXML
        private Button paymentmethod1;
        
        @FXML
        private AnchorPane gridpane;

        @FXML
        public void initialize() {
            
        returnhour.setItems(CommonC.HourString);
        pay_type.setItems(CommonC.Paymethod);
        returndate.setValue(LocalDate.now());
        Roadstar=false;
        returnhour.getSelectionModel().selectFirst();
        pay_type.getSelectionModel().selectFirst();
        redeembutton.setDisable(true);
            
        PlateNum.setCellValueFactory(new PropertyValueFactory<Return, String>("PlateNum"));
        branch_id.setCellValueFactory(new PropertyValueFactory<Return, Integer>("branch_id"));
        customer_name.setCellValueFactory(new PropertyValueFactory<Return, String>("customer_name"));
        customer_phone.setCellValueFactory(new PropertyValueFactory<Return, String>("customer_phone"));
        Expiredate.setCellValueFactory(new PropertyValueFactory<Return, String>("Expiredate"));
        cardnum.setCellValueFactory(new PropertyValueFactory<Return, String>("cardnum")); 
            
	}
	
        public ReturnViewController(){
	}

        public GridPane addGridPane(double result) {
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10, 10, 10, 10));
            ColumnConstraints column1 = new ColumnConstraints(150);
            ColumnConstraints column2 = new ColumnConstraints(150);
            ColumnConstraints column3 = new ColumnConstraints(150);
            ColumnConstraints column4 = new ColumnConstraints(150);

            grid.getColumnConstraints().add(column1);
            grid.getColumnConstraints().add(column2);
            grid.getColumnConstraints().add(column3);
            grid.getColumnConstraints().add(column4);
         
            Text category = new Text("Customer Name");
            category.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            grid.add(category, 0, 0); 
          
            Text chartTitle = new Text("Phone Number");
            chartTitle.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            grid.add(chartTitle, 1, 0);

            Text chartTitle1 = new Text("Plate Number");
            chartTitle1.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            grid.add(chartTitle1, 2, 0);
            
            Text chartTitle2 = new Text("Branch ID");
            chartTitle2.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            grid.add(chartTitle2, 3, 0);
            
            Text chartTitle3 = new Text(cname);
            chartTitle3.setFont(Font.font("Arial",  15));
            grid.add(chartTitle3, 0, 1);
            Text chartTitle4 = new Text(phonenumber);
            chartTitle4.setFont(Font.font("Arial", 15));
            grid.add(chartTitle4, 1, 1);
            Text chartTitle5 = new Text(platenumbertextfield.getText());
            chartTitle5.setFont(Font.font("Arial",  15));
            grid.add(chartTitle5, 2, 1);
            Text chartTitle6 = new Text(Integer.toString(bid));
            chartTitle6.setFont(Font.font("Arial",  15));
            grid.add(chartTitle6, 3, 1);
            
            Text chartTitle0 = new Text("  ");
            chartTitle0.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            grid.add(chartTitle0, 0, 2);
            
            Text chartTitle7 = new Text("General Fee");
            chartTitle7.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            grid.add(chartTitle7, 0, 3);
            Text chartTitle8 = new Text("Insurance Fee");
            chartTitle8.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            grid.add(chartTitle8, 0, 4);
            Text chartTitle9 = new Text("Additional Equipment Fee");
            chartTitle9.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            grid.add(chartTitle9, 0, 5);
            Text chartTitle10 = new Text("Expired return Fee");
            chartTitle10.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            grid.add(chartTitle10, 0, 6);
            Text chartTitle11 = new Text("Additional Tank Fee");
            chartTitle11.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            grid.add(chartTitle11, 0, 7);
            Text chartTitle12 = new Text("RoadStar Reduction ");
            chartTitle12.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            grid.add(chartTitle12, 0, 8);
            Text chartTitle13 = new Text("Redeem Reduction");
            chartTitle13.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            grid.add(chartTitle13, 0, 9);

            Text chartTitle14 = new Text(String.valueOf(Calculate.getRgeneralfee()));
            chartTitle14.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            grid.add(chartTitle14, 2, 3);
            
            Text chartTitle15 = new Text(String.valueOf(Calculate.getRinsurancefee()));
            chartTitle15.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            grid.add(chartTitle15, 2, 4);
            
            Text chartTitle16 = new Text(String.valueOf(Calculate.getRadditionequipmentfee()));
            chartTitle16.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            grid.add(chartTitle16, 2, 5);
            
            Text chartTitle17 = new Text(String.valueOf(Calculate.getRadditionalexpirefee()));
            chartTitle17.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            grid.add(chartTitle17, 2, 6);
            
            Text chartTitle18 = new Text(String.valueOf(Calculate.getRadditionaltankfee()));
            chartTitle18.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            grid.add(chartTitle18, 2, 7);
            
            Text chartTitle19 = new Text(String.valueOf("-"+Calculate.getRroadstarreduction()));
            chartTitle19.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            grid.add(chartTitle19, 2, 8);
            
            Text chartTitle20 = new Text(String.valueOf("-"+Calculate.getRredeemreduction()));
            chartTitle20.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            grid.add(chartTitle20, 2, 9);
            
            Text chartTitle21 = new Text("Total");
            chartTitle21.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            grid.add(chartTitle21, 0, 10);
             
             
            Text chartTitle22 = new Text(String.valueOf(result));
            chartTitle22.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            grid.add(chartTitle22, 2, 10);           

            return grid;
}
               
        
        @FXML
	public void getCarInfo() throws ParseException, SQLException{
            try{              
                if(platenumbertextfield.getText().isEmpty()==true)
                {
			MainController.showWarningDialog("Please input the plate number");
		}
                else{               
                String sql="select RENTID from RENTAL WHERE RENTID  NOT IN(SELECT RENTID FROM RETURNRECORD) and VID = '"+platenumbertextfield.getText()+"' ";
                stmt = SQLConnect.getcon().createStatement();
                rs = stmt.executeQuery(sql);
                if(rs.next()){
                    rent_id=rs.getString(1);
                }
                String Sql = "select * from RENTAL R JOIN CUSTOMER C JOIN RENTEDEQUIPMENT E where R.RENTID = '"+rent_id+"' and R.PHONENUM=C.PHONENUM and E.RENTID='"+rent_id+"'";
                rs = stmt.executeQuery(Sql);
                while(rs.next()){
                    phonenumber = rs.getString("PHONENUM");
                    creditnumber=rs.getString("CARDNUM");
                    bid=rs.getInt("BID");
                    cname=rs.getString("NAME");
                    expiredate=rs.getString("EXPECTEDRETURN");
                    Startdate=rs.getString("STARTDATE");
                    Returndate=rs.getString("EXPECTEDRETURN");
                    rent_id=rs.getString("RENTID");
                    equipment_type=rs.getString("ENAME");
                    eset.add(equipment_type);                
                }
               
                available_points.setText(Integer.toString(this.getpoints()));
                if(this.getpoints()>=1000){
                    redeembutton.setDisable(false);
                }
                tankfulltextfield.setText(Integer.toString(this.getFullTank()));
                }} catch (SQLException ex) {
			System.out.println("Database error.");
		}  
     
        myreturn = new Return(platenumbertextfield.getText(), bid, cname, phonenumber, creditnumber,expiredate,Startdate,Returndate,eset);
        ObservableList<Return> data = FXCollections.observableArrayList(myreturn);             
        return_info.setItems(data);      

       }
        
        @FXML
        public void checkdatehour() throws ParseException{
        Actualreturndate=LocalDate.now().toString()+" "+returnhour.getValue();
        System.out.println("nenenen"+LocalDate.now());
        System.out.println("actualreturndate:"+Actualreturndate);
        
        if("select".equals(returnhour.getValue())){
             MainController.showWarningDialog("Input can not be NULL");  
             return;
        }
        
        if(returndate.getValue().isBefore(LocalDate.now())||returndate.getValue().isAfter(LocalDate.now())){
            MainController.showWarningDialog("Invalid date");
        }
        else {

        dateStart=myreturn.getStartdate().substring(0,13);
        dateStop=Actualreturndate.substring(0,13);
        dateexpected=Returndate.substring(0, 13);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        
        System.out.println("newStartdate:"+dateStart);
        System.out.println("newReturndate:"+dateStop);
        System.out.println("expectedReturndate:"+dateexpected);
        
        java.util.Date d1 = format.parse(dateStart);
        java.util.Date d2 = format.parse(dateStop);
        java.util.Date d3 = format.parse(dateexpected);

        diff = d2.getTime() - d1.getTime(); 
        diff_expire=d2.getTime()-d3.getTime();
        expirehour=(int)diff_expire/(1000*60*60);
        if(diff_expire <= 0) {
        	expirehour = 0;
        }
        }
        }
        
        @FXML
        public void Odometerconfirm(){
            if(odometertextfield.getText().isEmpty()==true)
            {
                MainController.showWarningDialog("Please input the Odometer");
            }    
        }
       
        @FXML
        public void tankstatusconfirm(){
            if(tankstatustextfield.getText().isEmpty()==true)
            {
                MainController.showWarningDialog("Please input the Tank status");
            } 
        }

        @FXML
        public boolean CheckRoadStar(){
            Roadstar=true;
         
            return Roadstar;
        }
        
        @FXML
        public int redeem() throws SQLException{
            String wantedpoints;
            wantedpoints = pointswantredeem.getText();
            
            vtype=getVehicleType();
            if(diff>=24*60*60*1000){
                if(getpoints()>=1000 && !"Luxury".equals(vtype) && !"SUV".equals(vtype) && !"Van".equals(vtype) && !"24-foot".equals(vtype)&& !"15-foot".equals(vtype)&& !"12-foot".equals(vtype)&& !"Box-Truck".equals(vtype) && !"Cargo-Van".equals(vtype)){
                    redeembutton.setDisable(false);
                    rate = 1000;
                    System.out.println("10000000000"+vtype);

                     }
                else if(getpoints()>=1500 && ("Luxury".equals(vtype) || "SUV".equals(vtype) || "Van".equals(vtype)||"24-foot".equals(vtype)||"15-foot".equals(vtype)||"12-foot".equals(vtype)||"Box-Truck".equals(vtype)||"Cargo-Van".equals(vtype))){                         
                    redeembutton.setDisable(false);
                    rate = 1500;
                    System.out.println("15000000000");
                     }  
            }
            else{
                MainController.showWarningDialog("Only rent one day can redeem points");
                return 0;
            }
            myreturn = new Return(platenumbertextfield.getText(), bid, cname, phonenumber, creditnumber,expiredate,dateStart, dateStop,eset);

            int wantredeemday = 0;
            wantredeemday = (int)(Integer.valueOf(wantedpoints)/rate);
            int redeemavailableday = (int)(getpoints()/rate);
            
            if (wantredeemday>redeemavailableday){
                MainController.showWarningDialog("Can not redeem ");                
            }
            
            else if(Integer.valueOf(wantedpoints)%1000!=0 && Integer.valueOf(wantedpoints)%1600!=0)
            {
                MainController.showWarningDialog("Invalid redeem points");
            } 
            else{

            newpoint = getpoints()-wantredeemday*rate;
            available_points.setText(Integer.toString(newpoint));
            redeemday=wantredeemday;
            System.out.println("rrrrr1:"+redeemday);
    
        }     
            return redeemday;
        }
        
        @FXML
        public void paymethod(){
            if("Type".equals(pay_type.getValue())){
             MainController.showWarningDialog("Please choose pay method"); 
            }
            
            if("Cash".equals(pay_type.getValue())){
             cardnumber.setDisable(true);
             paymentmethod.setDisable(true);
            }    
        }
        
        @FXML 
        public void paymentmethod(){

            if(cardnumber.getText().length() != 16 || Pattern.matches("[a-zA-Z]+", cardnumber.getText()) == true)
            {
                MainController.showWarningDialog("Invalid Credit Card Number");
            }
            creditnumber=cardnumber.getText();
        }

            
        @FXML
	public void calculatefee() throws ParseException, SQLException{
            if(getpoints()<1000){
                 vtype=getVehicleType();
                 myreturn = new Return(platenumbertextfield.getText(), bid, cname, phonenumber, creditnumber,expiredate,dateStart, dateStop,eset);         
            }
            actualtank=Integer.valueOf(tankstatustextfield.getText());
            tankdifference=fulltank-actualtank;
            System.out.println("tankdifference:"+tankdifference);
            System.out.println("redeemday"+redeemday);
            cl = new Calculate(redeemday, Roadstar, tankdifference,eset,rent_id,expirehour);
            calculateresult=cl.estimateFee(myreturn);
            DecimalFormat df = new DecimalFormat("#.##");    
            String s = df.format(calculateresult);
            calculateresult = Double.parseDouble(s);       
            gridpane.getChildren().add(this.addGridPane(calculateresult));
        }
        
        
        @FXML 
        public void updatedb() throws SQLException{
            Statement stmt3;
            Statement stmt4;
            Statement stmt5;
            Statement stmt6;
            Statement stmt8;
            Statement stmt9;
            
            int new_point;
            new_point=newpoint+(int)calculateresult/5;
            String sql = "update SUPERMEMBER set POINTS = '"+ new_point +"' where PHONENUM = '"+phonenumber+"';";
            stmt3 = SQLConnect.getcon().createStatement();
            stmt3.executeUpdate(sql);
            
            double odometer;
            String s = odometertextfield.getText();
            odometer = Double.parseDouble(s);
            String sql1="update VEHICLE set ODOREAD='"+odometer+"' where VID='"+platenumbertextfield.getText()+"'";
            stmt4=SQLConnect.getcon().createStatement();
            stmt4.executeUpdate(sql1);
           
            bid=this.BranchConfirm();
            String sql2="Insert into RETURNRECORD values ("+rent_id+", '" +bid+"','"+calculateresult+"','"+actualtank+"','"+Actualreturndate+"')";
            stmt5=SQLConnect.getcon().createStatement();
            stmt5.executeUpdate(sql2);
            
            String sql3="update VEHICLE set BID='"+bid+"' where VID='"+platenumbertextfield.getText()+"'";
            stmt6=SQLConnect.getcon().createStatement();
            stmt6.executeUpdate(sql3);
            
            for(String equip: eset){
                String sql5="update KEEPSEQUIPMENT set QUANTITY=QUANTITY+1 where BID='"+newbid+"' and ENAME='"+equip+"'";
                stmt8=SQLConnect.getcon().createStatement();
                stmt8.executeUpdate(sql5);
            }
            
            String sql6="update RENTAL set CARDNUM='"+creditnumber+"' where RENTID='"+rent_id+"'";
            stmt9=SQLConnect.getcon().createStatement();
            stmt9.executeUpdate(sql6);

            SQLConnect.getcon().commit();
            MainController.showWarningDialog("return Successful");        
        }
                        
        public MainViewController getMainVC(){
        	return mainvc;
        }
        
        public void setMainVC(MainViewController m) {
        	this.mainvc = m;
        }
        
        public String getVehicleType() throws SQLException{
            Statement stmt2;
            ResultSet rs2;   

            String sql="select * from VEHICLE V JOIN VEHICLETYPE VT where V.VID = '"+platenumbertextfield.getText()+"' and V.TYPE=VT.TYPE";
            stmt2=SQLConnect.getcon().createStatement();
            rs2=stmt2.executeQuery(sql);
            while(rs2.next()){
                vtype=rs2.getString("TYPE");
                fulltank=rs2.getInt("TANKCAPACITY");
            }
              return vtype;
            
        }
        
        public int getFullTank() throws SQLException{
        Statement stmt3;
        ResultSet rs3;          
        String sql="select TANKCAPACITY from VEHICLE V JOIN VEHICLETYPE VT where V.VID = '"+platenumbertextfield.getText()+"' and V.TYPE=VT.TYPE";
        stmt3=SQLConnect.getcon().createStatement();
        rs3=stmt3.executeQuery(sql);
        while(rs3.next()){
            fulltank=rs3.getInt("TANKCAPACITY");
        }
        System.out.println("FULLTANK:"+fulltank);
          return fulltank;

    }
        
        public int BranchConfirm(){     
            if(branchid.getText().isEmpty()==true)
                {
                    MainController.showWarningDialog("Please input the Branch ID");
		}
            newbid=Integer.valueOf(branchid.getText());
            System.out.println("textttttt"+newbid);
            return newbid;
        }

        public int getpoints(){
            Statement stmt1;
            ResultSet rs1;
            int point = 0;
            String sql = "select POINTS from SUPERMEMBER where PHONENUM = '" + phonenumber +"'";
                try {                  
                    stmt1 = SQLConnect.getcon().createStatement();
                    rs1 = stmt1.executeQuery(sql); 
                    while(rs1.next()){
                        point = rs1.getInt("POINTS");
                    }
                    stmt1.close();
                     
                } catch (SQLException ex) {
                    Logger.getLogger(ReturnViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            return point;
        }
}



