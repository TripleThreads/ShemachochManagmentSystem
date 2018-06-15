package triplethreads.shemachoch.EntityClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Seller {
	public boolean AddCustomer(String credential[]) {
		Connection con = ConnectionHandler.INISTANCE.Connect();
		
		try {
			String sql = "insert into Customer_data " + "(customer_id, FirstName, LastName , Location, Family_number , Allowed_Amount)"
					+ "values(?,?,?,?,?,?,?)";
			
			PreparedStatement ps = con.prepareStatement(sql);
			for (int i = 1; i < 8; i++) {
				ps.setString(i, credential[i - 1]);
			}
			if(ps.execute()) {
				return true;
			}
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
public boolean RemoveCustomer(String Customer_id) {
		
		Connection con = ConnectionHandler.INISTANCE.Connect();
		try {
			String sql = "DELETE FROM Customer_data WHERE Item_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(0, Customer_id);
			if(ps.executeUpdate()!=-1) {
				return true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
