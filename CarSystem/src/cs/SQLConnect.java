package cs;
import java.sql.*;

/**
*
* @author Jiyu Xiao
*/
public class SQLConnect {
	
	private static Connection con;
	
	/**
	 * A static function that create the connection to the database and save the connection
	 * 
	 * @return true if connection succeed, false if connection failed
	 */
	public static boolean connect() {
		
		String connectURL = "jdbc:mysql://dbserver.mss.icics.ubc.ca/team03";
		
		try
		{
			con = DriverManager.getConnection(connectURL, "team03", "frid@y!@");
			
			Statement s = con.createStatement();
			
			s.executeUpdate("use team03");
			
			return true;
		} catch (SQLException ex) {
			
			MainController.showWarningDialog(ex.getMessage());
			return false;
		}
		
	}
	
	public static Connection getcon() throws SQLException {
		con.setAutoCommit(false);
		return con;
	}
	
	
}
