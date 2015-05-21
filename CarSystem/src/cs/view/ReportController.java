/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.view;

import static cs.AlertDialog.AlertDialog;
import cs.SQLConnect;
import cs.SQLSearch;
import cs.model.Rent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
/**
 *
 * @author whitney-pc
 */
public class ReportController {
    @FXML ComboBox report_type,branch;
    private final Stage stage = new Stage();
    
    public ReportController() {
                             

    }
    @FXML private void initialize(){
                branch.getItems().addAll("all");
                branch.setValue("all");
                branch.getItems().addAll(SQLSearch.getBranchList());
    }
    @FXML
    protected void report(){
        ObservableList<Rent> data = FXCollections.observableArrayList();
        String table,count_type,count_sum,count_grand;
        String date=LocalDate.now().toString();
        count_grand="";
        if(report_type.getValue().toString().equals("Rent")){
            table="RentReport";
        if(branch.getValue().toString().equals("all")){
         count_type="select type,count(type),RENTAL.bid,city,address from BRANCH,RENTAL join "
                 + "VEHICLE on RENTAL.vid=VEHICLE.vid where RENTAL.bid=BRANCH.bid and startdate>='"+date+"' group by bid,type";
         count_sum="select count(RENTAL.bid) as NUM,RENTAL.bid,city,address from BRANCH,RENTAL join "
                 + "VEHICLE on RENTAL.vid =VEHICLE.vid where RENTAL.bid=BRANCH.bid and startdate>='"+date+"'group by RENTAL.bid";
         count_grand="select count(RENTAL.vid) as NUM from BRANCH,RENTAL where RENTAL.bid=BRANCH.bid and startdate>='"+date+"'";
        }
        else {
            count_type="select type,count(type),RENTAL.bid,city,address from BRANCH,RENTAL join VEHICLE "
                    + "on RENTAL.vid=VEHICLE.vid where RENTAL.bid=BRANCH.bid and RENTAL.bid="+branch.getValue().toString().charAt(0)+" and startdate>='"+date+"' group by type";
            count_sum="select count(RENTAL.bid) as NUM,RENTAL.bid,city,address from BRANCH,RENTAL join "
                    +"VEHICLE on RENTAL.vid=VEHICLE.vid where RENTAL.bid=BRANCH.bid and RENTAL.bid="+branch.getValue().toString().charAt(0)+" and startdate>='"+date+"'";
           
        }
        } else{
            table="ReturnReport";
            if(branch.getValue().toString().equals("all")){
         count_type="select type,count(type) as num,sum(fee) as amount,returnbid,city,address from BRANCH,RETURNRECORD join "
                 + "RENTAL on RENTAL.rentid=RETURNRECORD.rentid join VEHICLE on RENTAL.vid=VEHICLE.vid where returnbid=BRANCH.bid and startdate>='"+date+"' group by returnbid,type";
         count_sum="select count(returnbid) as num,sum(fee) as amount,returnbid,city,address from BRANCH,RETURNRECORD join "
                 + "RENTAL on RENTAL.rentid =RETURNRECORD.rentid join VEHICLE on RENTAL.vid=VEHICLE.vid where returnbid=BRANCH.bid and startdate>='"+date+"' group by returnbid";
         count_grand="select count(RETURNRECORD.rentid) as num, sum(fee)as amount from RETURNRECORD where returndate>='"+date+"'";
        }
        else {
            count_type="select type,count(type),sum(fee),returnbid,city,address from BRANCH,RETURNRECORD join "
                 + "RENTAL on RENTAL.rentid=RETURNRECORD.rentid join VEHICLE on RENTAL.vid=VEHICLE.vid where returnbid=BRANCH.bid and BRANCH.bid="+branch.getValue().toString().charAt(0)+" and returndate>='"+date+"' group by type";
            count_sum="select count(returnbid),sum(fee),returnbid,city,address from BRANCH,RETURNRECORD join "
                 + "RENTAL on RENTAL.rentid =RETURNRECORD.rentid join VEHICLE on RENTAL.vid=VEHICLE.vid where returnbid=BRANCH.bid and BRANCH.bid="+branch.getValue().toString().charAt(0)+" and returndate>='"+date+"'";
            
            }
        }
        
             try { 
                 Statement s=SQLConnect.getcon().createStatement();//statement for each branch
                 ResultSet rs=s.executeQuery(count_type); 
                 //caculate subtotal and grandtotal for each branch
                 Statement s2=SQLConnect.getcon().createStatement();//statement for subtotal 
                 Statement s3=SQLConnect.getcon().createStatement();//statement for grandtotal
                 ResultSet rss=s2.executeQuery(count_sum);
                 ResultSet grand;
                 if(report_type.getValue().toString().equals("Rent")){
                 while(rs.next())
                     data.add(new Rent(rs.getString(1),rs.getInt(2),0,rs.getString(3)+"-"+rs.getString(4)+"-"+rs.getString(5)));       
                 while(rss.next())    
                     data.add(new Rent("SUBTOTAL",rss.getInt(1),0,rss.getString(2)+"-"+rss.getString(3)+"-"+rss.getString(4)));
                 
                 if(!count_grand.equals("")){
                      grand=s3.executeQuery(count_grand);
                      if(grand.next())
                      data.add(new Rent("GrandTotal",grand.getInt(1),0,""));
                 }
                 } else {
                     while(rs.next())
                         data.add(new Rent(rs.getString(1),rs.getInt(2),rs.getDouble(3),rs.getString(4)+"-"+rs.getString(5)+"-"+rs.getString(6)));
                     while(rss.next())
                         data.add(new Rent("SUBTOTAL",rss.getInt(1),rss.getDouble(2),rss.getString(3)+"-"+rss.getString(4)+"-"+rss.getString(5)));
                     if(!count_grand.equals("")){
                      grand=s3.executeQuery(count_grand);
                      if(grand.next())
                      data.add(new Rent("GrandTotal",grand.getInt(1),grand.getDouble(2),""));
                     }
                 }
                 export(data,table);
                 rs.close();
                 s.close();
                 s2.close();
             } catch (Exception ex) {
                 //AlertDialog("Export Failed");
                 AlertDialog("report error");
             }
        
    }
    private void export(ObservableList<Rent> data,String fname)throws Exception {
     
            Writer writer;
      
            //File file = new File("/Users/whitney-pc/Dropbox/a.csv");
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Export Report");
            chooser.setInitialDirectory(new File("/"));
            File f=chooser.showDialog(stage);
            if(f!=null)
            try{    
            File file = new File(f.getPath()+"/"+fname+".csv");
            writer = new BufferedWriter(new FileWriter(file));
            if(fname.equals("RentReport")) writer.write("Category,NUM,Branch\n");else writer.write("Category,NUM,AMOUNT,Branch\n");
            if(fname.equals("RentReport")) for(Rent r:data) {
                String text = r.getCategory() + "," + Integer.toString(r.getCount())+ "," + r.getBranch() + "\n";
                writer.write(text);
            }
            else for(Rent r:data) {
                String text = r.getCategory() + "," + Integer.toString(r.getCount())+ "," + r.getFee()+","+r.getBranch() + "\n";
                writer.write(text);
            }
                
            writer.flush();
            writer.close();
            AlertDialog("Generate Report Successfully!");
          }
        catch (Exception ex) {
            
        }
        
    }
    
}
