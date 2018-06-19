package triplethreads.shemachoch.EntityClasses;

import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Customer {
	static Connection con = ConnectionHandler.INSTANCE.getConnection();
	String firstName = null;
	String lastName = null;
	String IDnumber = null;
	String HouseNumber = null;
	BufferedImage Photo = null;
	ArrayList<String> getCustomer(String customer_id) {
		ArrayList<String> info = new ArrayList<>();
		int Count = 0;
		try {
			String s = "select * from Customer_data where customer_id= ?";
			PreparedStatement p = con.prepareStatement(s);
			p.setString(1, customer_id);
			ResultSet r = p.executeQuery();
			Customer c = new Customer();
			while (r.next()) {
				info.add(r.getString("FirstName"));
				info.add(r.getString("LastName"));
				info.add(r.getString("Location"));
				info.add(r.getString("Family_id"));
				info.add(r.getString("House_Number"));
				info.add(r.getString("Allowed_Amount"));
			}
			String s1 = "select count(*) from Customer_data where Family_id = ?";
			PreparedStatement ps = con.prepareStatement(s1);
			ps.setString(1, info.get(3));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Count = rs.getInt(1);
			}
			System.out.println("Family members: "+Count);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(info.size()!=0) {
			return info;
		}
		return null;
	}
	ArrayList<String> getCustomerList(){
			ArrayList<String> info = new ArrayList<>();
			try {
				String s = "select * from Customer_data";
				PreparedStatement p = con.prepareStatement(s);
				ResultSet r = p.executeQuery();
				while (r.next()) {
					info.add(r.getString("FirstName"));
					info.add(r.getString("LastName"));
					info.add(r.getString("Location"));
					info.add(r.getString("Family_id"));
					info.add(r.getString("House_Number"));
					info.add(r.getString("Allowed_Amount"));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(info.size()!=0) {
				return info;
			}
			return null;
		}
}
