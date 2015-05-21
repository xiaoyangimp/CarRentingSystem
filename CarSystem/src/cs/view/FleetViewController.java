/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.view;



import java.sql.*;
import cs.SQLConnect;
import cs.SQLSearch;
import cs.model.OnSaleVehicle;
import cs.model.RentalRate;
import cs.model.Vehicle;
import java.time.LocalDate;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import static cs.AlertDialog.AlertDialog;
/**
 * FXML Controller class
 *
 * @author whitney-pc
 */
public class FleetViewController{

    /**
     * Initializes the controller class.
     */
    //View tab
    @FXML
     private ComboBox car_type,type_class,branch,year;
    @FXML 
     private TableView<Vehicle> table;
    @FXML
    TableColumn vid,type,date,ordor,bid;
    //Add tab
    @FXML 
    private ComboBox add_type,add_class,add_loc;
    @FXML 
    private DatePicker add_date;
    @FXML 
    private TextField  add_id,add_ordor;
    
    
    // set rental rate tab
    @FXML private TableView<RentalRate> r_table;
    @FXML private TableColumn r_type,w_rate,d_rate,h_rate,k_rate;
    
    //sell vehicle tab
    @FXML private TableView<OnSaleVehicle> sale_table;
    @FXML private TableColumn sale_id,sale_price;
    //construct function
    
    public FleetViewController() {

                
    }
    
    @FXML
    private void initialize(){
        car_type.getItems().addAll(SQLSearch.getCategoryList());
        add_type.getItems().addAll(SQLSearch.getCategoryList());
        add_type.setValue(add_type.getItems().get(0));
        add_class.getItems().addAll(SQLSearch.getVehicleTypeList(1));
        add_class.setValue(add_class.getItems().get(0));
        type_class.getItems().addAll(SQLSearch.getVehicleTypeList(0));
        add_date.setValue(LocalDate.now());
        branch.getItems().addAll(SQLSearch.getBranchList());
        add_loc.getItems().addAll(SQLSearch.getBranchList());
        add_loc.setValue(add_loc.getItems().get(0));
        
    }
    
     
     @FXML//process the option changes for vehicle class comboBox
     protected void type_change(){    //chage the options of vehicle type classes in View tab
        type_class.getItems().clear();
        type_class.getItems().addAll("all");
        type_class.setValue("all");
        ClassInit(type_class,car_type.getValue().toString().trim());
        
    }
     private void ClassInit(ComboBox box,String vtype){//change the options for class combobox;
        if(vtype.equals("all")) box.getItems().addAll(SQLSearch.getVehicleTypeList(0)); 
                else {
                    box.getItems().addAll(SQLSearch.getVehicleTypeList(Character.getNumericValue(vtype.charAt(0))));
                    if(box.equals(add_class)) box.setValue(box.getItems().get(0)); 
                }
        
        
    }
//<editor-fold defaultstate="collapsed" desc="Search Vehicle operation">
     @FXML 
     protected void search() {//process search operation
         //process searching criteria
         String typeClass=type_class.getValue().toString().trim();
         String loc=branch.getValue().toString().trim();
         String date=year.getValue().toString().trim();
      
         String qcom="select vid,VEHICLE.type, acquiredate,odoread,bid from VEHICLE ";

         boolean flag=false;
         if(typeClass.equals("all")){
             String vtype=car_type.getValue().toString().trim();  
             if(!vtype.equals("all")){
                 qcom+="join VEHICLETYPE on VEHICLE.type=VEHICLETYPE.type where cid="+vtype.charAt(0);
                 flag=true;
             }
         }else{
             qcom+=" where type='"+typeClass+"'";
             flag=true;
         }
         if(!loc.equals("all")){
             if(flag)qcom+=" and bid="+loc.charAt(0);else qcom+="where bid="+loc.charAt(0);
             flag=true;
         }
         
         if(!date.equals("all")) {
             if(flag)qcom+=" and acquiredate<'"+date+"-00-00'";else qcom+="where acquiredate<'"+date+"-00-00'";
         }
         qcom+=" order by bid,type";
         //connect to database and execute sql command
             try { 
                 Statement s=SQLConnect.getcon().createStatement();
                 ResultSet rs=s.executeQuery(qcom);
                 FillVdata(rs);
                 rs.close();
                 s.close();
                 
             } catch (SQLException ex) {
                 AlertDialog("Query Fail!");
             }
         
         
     }
      
    private void FillVdata(ResultSet rs){//fill vehicle view table
       ObservableList<Vehicle> data = FXCollections.observableArrayList();
        try {
            while(rs.next()){
                data.add(new Vehicle(rs.getString(1),rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getInt(5)));
            }
        } catch (SQLException ex) {
            System.out.println("Query Failed");
        }
        vid.setCellValueFactory(new PropertyValueFactory<>("vid"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        bid.setCellValueFactory(new PropertyValueFactory<>("bid"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        table.setItems(data);
    }
   //</editor-fold>
     
    
//<editor-fold defaultstate="collapsed" desc="Add Vehicle operation">

     @FXML
     protected void addtype_change(){    //chage the options of vehicle type classes in Add tab
         add_class.getItems().clear();
         ClassInit(add_class,add_type.getValue().toString().trim());
      
    }
     @FXML 
     protected void add_vehicle(){
        String id=add_id.getText().trim();
        String odoread=add_ordor.getText().trim();
        char bid=add_loc.getValue().toString().charAt(0);
        if(id.length()==6&&!odoread.equals("")){
             try { 
                 String insertSql="INSERT INTO VEHICLE(bid,vid,type,odoread,acquiredate,status) VALUES(?,?,?,?,?,1)";
                 PreparedStatement s=SQLConnect.getcon().prepareStatement(insertSql);
                 s.setInt(1,Character.getNumericValue(bid));
                 s.setString(2, id);
                 s.setString(3, add_class.getValue().toString());
                 s.setDouble(4, Double.parseDouble(odoread));
                 LocalDate d=add_date.getValue();
                 s.setString(5,d.toString().trim());
                 s.executeUpdate();
                 SQLConnect.getcon().commit(); 
                 AlertDialog("Add Successfully!");
                  s.close(); 
             } catch (SQLException ex) {
                 AlertDialog("Add Failed");
             }
         
        } else AlertDialog("Vehicle Information Is Not Correct");
     }
   //</editor-fold>
 //<editor-fold defaultstate="collapsed" desc="put Vehicle for sale">   
@FXML
     protected void sell() throws SQLException{
        
            Vehicle v=table.getSelectionModel().getSelectedItem();
            
            if(v !=null) {
                //check vehicle status
                String id=v.getVid();
                int status=checkStatus(id);
                if(status==1){
               //Create price setting dialog
               TextInputDialog dialog = new TextInputDialog();
               dialog.setTitle("Set Sell Price");  
               dialog.setContentText("Sell Price:");
               dialog.setHeaderText(null);
               Optional<String> result=dialog.showAndWait();
               if(result.isPresent()&& !result.get().trim().equals("")){
                 double price=Double.parseDouble(result.get());
                 if(price>0)
                 try {
                  Statement s=SQLConnect.getcon().createStatement();
                  s.executeUpdate("update VEHICLE set status=0 where vid='"+id.trim()+"'");
                  String sql="insert into FORSALE(vid,price) values(?,?)";
                  PreparedStatement p=SQLConnect.getcon().prepareStatement(sql);
                  p.setString(1, id.trim());
                  p.setDouble(2,price);
                  p.executeUpdate();
                  SQLConnect.getcon().commit();
                  p.close();
                  AlertDialog("Put On Sale Successfully!");
                  search();
                 } catch (Exception ex) {
                //Logger.getLogger(FleetViewController.class.getName()).log(Level.SEVERE, null, ex);
                 AlertDialog("Put Onsale Failed");
                   }           
               }else AlertDialog("Input is invalid");
               }else AlertDialog("This Vehicle is already on sale list");
            }
     }
    int checkStatus(String id) throws SQLException{//check the vehicle status
         ResultSet rs;
         PreparedStatement s=SQLConnect.getcon().prepareStatement("select status from VEHICLE where vid=?");
         s.setString(1, id);  
         rs=s.executeQuery();
         while(rs.next()) return rs.getInt(1);
        return(-1);
    }
 //</editor-fold>  
     
//<editor-fold defaultstate="collapsed" desc="Set rental rate"> 
     @FXML 
     protected void View_rate(){
         
             try { 
                 Statement s=SQLConnect.getcon().createStatement();                 
                 ResultSet rs;         
                 rs=s.executeQuery("select type,w_rate,d_rate,h_rate,kilo_rate from VEHICLETYPE");
                 
                 ObservableList<RentalRate> data = FXCollections.observableArrayList();
                 while(rs.next()){
                  data.add(new RentalRate(rs.getString(1),rs.getDouble(2),rs.getDouble(3),rs.getDouble(4),rs.getDouble(5))); 
                  r_type.setCellValueFactory(new PropertyValueFactory<>("r_type"));
                  w_rate.setCellValueFactory(new PropertyValueFactory<>("w_rate"));
                  d_rate.setCellValueFactory(new PropertyValueFactory<>("d_rate"));
                  h_rate.setCellValueFactory(new PropertyValueFactory<>("h_rate"));
                  k_rate.setCellValueFactory(new PropertyValueFactory<>("k_rate"));
                  r_table.setItems(data);
                  
            }
                 s.close();
             } catch (SQLException ex) {
                 System.out.println("Query Failed");
             }
         
     }
     @FXML protected void rate_update(){//update rental rate
         RentalRate rate=r_table.getSelectionModel().getSelectedItem();
         if(rate!=null){
             //construct the update window form.
             Dialog<String> dialog=new Dialog<>();
             dialog.setTitle("Change Rental Rate");
             dialog.setHeaderText(rate.getR_type()+" Rate:");
             GridPane grid = new GridPane();
             TextField wrate = new TextField(Double.toString(rate.getW_rate()));
             TextField drate = new TextField(Double.toString(rate.getD_rate()));
             TextField hrate = new TextField(Double.toString(rate.getH_rate()));
             TextField krate = new TextField(Double.toString(rate.getK_rate()));
             grid.add(new Label("Weekly Rate"),0,0);
             grid.add(wrate, 1, 0);
             grid.add(new Label("Daily Rate"),0,1);
             grid.add(drate, 1, 1);
             grid.add(new Label("Hourly Rate"),0,2);
             grid.add(hrate, 1, 2);
             grid.add(new Label("Kilometer Rate"),0,3);
             grid.add(krate, 1, 3);
             dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
             dialog.getDialogPane().setContent(grid);
             Optional<String> result=dialog.showAndWait();
             if(result.isPresent())
                 
                 try{
                     String com="update VEHICLETYPE set w_rate="+wrate.getText().trim()+",d_rate="+
                               drate.getText().trim()+",h_rate="+hrate.getText().trim()+",kilo_rate="+krate.getText().trim()+
                             "where type='"+rate.getR_type()+"'";
                     Statement s=SQLConnect.getcon().createStatement();
                     s.executeUpdate(com);
                     SQLConnect.getcon().commit();
                     AlertDialog("Change Successfully");
                     s.close();
                     View_rate();
                 } catch(Exception ex){
                     AlertDialog("Error!");
                 }
             
             
         }
         
     }
//</editor-fold>
     
//<editor-fold defaultstate="collapsed" desc="Sell Car"> 
     @FXML protected void ViewSale(){
         ObservableList<OnSaleVehicle> data=FXCollections.observableArrayList();
         if(SQLConnect.connect()){
             try { 
                 ResultSet rs;                 
                 Statement s = SQLConnect.getcon().createStatement();
                     rs=s.executeQuery("select * from FORSALE");
                 while(rs.next()){
                  data.add(new OnSaleVehicle(rs.getString(1),rs.getDouble(2))); 
                  
            }
                 sale_id.setCellValueFactory(new PropertyValueFactory<>("vid"));
                  sale_price.setCellValueFactory(new PropertyValueFactory<>("price"));
                  sale_table.setItems(data);
             } catch (SQLException ex) {
                 System.out.println("Query Failed");
             }
         }
     }
     @FXML protected void sold(){
         
             try { 
                 Statement s=SQLConnect.getcon().createStatement();
                 String sql="insert into SOLD values(?,?,?)";         
                 PreparedStatement p=SQLConnect.getcon().prepareStatement(sql);
                 OnSaleVehicle v=sale_table.getSelectionModel().getSelectedItem();
                 if(v!=null){
                  String id=v.getVid();
                 //Create purchaser setting dialog
                 TextInputDialog dialog = new TextInputDialog();
                 dialog.setTitle("Enter Buyer Name");  
                 dialog.setContentText("Buyer:");
                 dialog.setHeaderText(null);
                 Optional<String> result=dialog.showAndWait();
                 if(result.isPresent()&&!result.get().trim().equals("")){
                   s.executeUpdate("delete from FORSALE where vid='"+id+"'");
                   p.setString(1,v.getVid());
                   p.setString(2,result.get().trim());
                   p.setString(3,LocalDate.now().toString());
                   p.executeUpdate();
                   SQLConnect.getcon().commit();
                   p.close();
                   s.close();
                   AlertDialog("Sell Vehicle Successfully!");
                   ViewSale();
                 }
               } 
             }catch (SQLException ex) {
                 AlertDialog("Sell Failed");
             }
             
         
    }
     //</editor-fold>
}
