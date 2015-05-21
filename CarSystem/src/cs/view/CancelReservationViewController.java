package cs.view;

import java.sql.*;
import java.time.LocalDate;
import java.util.Calendar;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import cs.MainController;
import cs.SQLConnect;
import cs.SQLSearch;
import cs.constant.Translate;
import cs.model.ReservationToCancel;

/**
*
* @author Jiyu Xiao
*/
public class CancelReservationViewController {
	@FXML
	private RadioButton cn;
	
	@FXML
	private RadioButton pnsd;
	
	@FXML
	private TextField cntf;
	
	@FXML
	private TextField pntf;
	
	@FXML
	private DatePicker st;
	
	@FXML
	private Button check;
	
	@FXML
	private Button checkall;
	
	@FXML
	private Button cancel;
	
	@FXML
	private TableView<ReservationToCancel> recordtable;
	
	@FXML
	private TableColumn<ReservationToCancel, Integer> vehicleid;
	
	@FXML
	private TableColumn<ReservationToCancel, String> sdate;
	
	@FXML
	private TableColumn<ReservationToCancel, String> rdate;
	
	@FXML
	private TableColumn<ReservationToCancel, Integer> cnumber;
	
	@FXML
	private TableColumn<ReservationToCancel, String> vtype;
	
	private final ToggleGroup group = new ToggleGroup();
	private MainViewController mainvc;
	int status = -1;
	
	public CancelReservationViewController(){
		
	}
	
	@FXML
	public void initialize(){
		cn.setToggleGroup(group);
		cn.setSelected(true);
		pnsd.setToggleGroup(group);
		st.setValue(LocalDate.now());
		vehicleid.setCellValueFactory(new PropertyValueFactory<ReservationToCancel, Integer>("vid"));
		sdate.setCellValueFactory(new PropertyValueFactory<ReservationToCancel, String>("startdateinstring"));
		rdate.setCellValueFactory(new PropertyValueFactory<ReservationToCancel, String>("returndateinstring"));
		cnumber.setCellValueFactory(new PropertyValueFactory<ReservationToCancel, Integer>("resid"));
		vtype.setCellValueFactory(new PropertyValueFactory<ReservationToCancel, String>("vehicletype"));
	}
	
	@FXML
	protected void check(){
		if(cn.isSelected()) {
			recordtable.setItems(SQLSearch.cancelByConfirmNumber(Translate.SToI(cntf.getText())));
		}
		else {
			LocalDate readdate = st.getValue();
			Calendar temp = Calendar.getInstance();
			temp.clear();
			temp.set(Calendar.YEAR, readdate.getYear());
			temp.set(Calendar.MONTH, readdate.getMonthValue() - 1);
			temp.set(Calendar.DAY_OF_MONTH, readdate.getDayOfMonth());
			recordtable.setItems(SQLSearch.cancelByphonenumanddate(pntf.getText(), temp));
			recordtable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		}
		
		status = 0;
	}
	
	@FXML
	protected void checkall(){
		recordtable.setItems(SQLSearch.checkall());
		status = 1;
	}
	
	
	@FXML
	protected void cancel(){
		if( recordtable.getSelectionModel().getSelectedIndex() < 0	) return;
			
		ReservationToCancel temp = recordtable.getSelectionModel().getSelectedItem();
		
//		String ename;
		PreparedStatement ps;
//		PreparedStatement ps2;
//		ResultSet rs;
		
		try{
//			ps = SQLConnect.getcon().prepareStatement("Select * from RESERVEDEQUIPMENT where resid = ?");
//			ps.setInt(1, temp.getResid());
//			rs = ps.executeQuery();
//
//			
//			while(rs.next()){
//				ename = rs.getString("ename");
//				
//				ps2 = SQLConnect.getcon().prepareStatement("Update KEEPSEQUIPMENT set quantity = quantity + 1 where bid = ? AND ename = ?");
//				ps2.setInt(1, temp.getBid());
//				ps2.setString(2, ename);
//				ps2.executeUpdate();
//				SQLConnect.getcon().commit();
//				ps2.close();
//			}
//			
//			ps.close();
			
			
			ps = SQLConnect.getcon().prepareStatement("Delete from RESERVEDEQUIPMENT where resid = ?"); // delete the reserved equipment record according to resid
			ps.setInt(1, temp.getResid());
			
			ps.executeUpdate();
			SQLConnect.getcon().commit();
			ps.close();
			
			ps = SQLConnect.getcon().prepareStatement("Delete from RESERVATION where resid = ?"); // delete the reservation record according to resid
			ps.setInt(1, temp.getResid());
			
			ps.executeUpdate();
			SQLConnect.getcon().commit();
			ps.close();

			cntf.clear();
			pntf.clear();
			
			if(status == 0) check();
			else checkall();
				
		} catch (SQLException ex) {
			MainController.showWarningDialog(ex.getMessage());
		}
		
	}
	
	public void setMainVC(MainViewController m) {
		this.mainvc = m;
	}
	
	public MainViewController getMainVC(){
		return mainvc;
	}
}
