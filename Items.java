package triplethreads.shemachoch.EntityClasses;

import javafx.scene.control.TextField;

import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Items {
	Connection con = ConnectionHandler.INISTANCE.Connect();
	String name = null;
	String itemId = null;
	String brand = null;
	int amount = 0;
	double price = 0;
	int x = 0;
	public double AvailableAmount(String Item_id) {
		double Amount = 0;
		
		Connection con = ConnectionHandler.INISTANCE.Connect();
		try {
		String sql = "select Item_price from items_data where item_id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(x, Item_id);
			ResultSet rs = ps.executeQuery();
				while (rs.next()) {
						Amount = Double.parseDouble(rs.getString(2));
				}
			}
		catch(Exception e) {
			e.printStackTrace();
		}
		return Amount;
	}
	
	public double getPrice(String Item_id) {
		double Price = 0;
	
		Connection con = ConnectionHandler.INISTANCE.Connect();
		try {
		String sql = "select Item_price from items_data where item_id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(x, Item_id);
			ResultSet rs = ps.executeQuery();
				while (rs.next()) {
						Price = Double.parseDouble(rs.getString(3));
				}
			}
		catch(Exception e) {
			e.printStackTrace();
		}
		return Price;
	}
	
	public boolean AddItems(String[] Data ) {
		Connection con = ConnectionHandler.INISTANCE.Connect();
		
		try {
			String sql = "insert into items_data " + "(Item_name, Item_Brand, Item_amount , Item_Price, ExpireDate)"
					+ "values(?,?,?,?,?)";
			
			PreparedStatement ps = con.prepareStatement(sql);
			for (int i = 1; i < 8; i++) {
				ps.setString(i, Data[i - 1]);
			}
			if(ps.execute()) {
				return true;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean RemoveItem(String Item_id) {
		
		Connection con = ConnectionHandler.INISTANCE.Connect();
		try {
			String sql = "DELETE FROM Items_data WHERE Item_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(x, Item_id);
			if(ps.executeUpdate()!=-1) {
				return true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public ArrayList<Items> getListOfAllItems() {
		Connection con = ConnectionHandler.INISTANCE.Connect();
		ArrayList<Items> arr = new ArrayList<>();
		try {
			String s = "select * from items_data";
			Statement st = con.createStatement();
			
			ResultSet rs = st.executeQuery(s);
			while(rs.next()) {
				Items item = new Items();
				item.amount = Integer.parseInt(rs.getString("item_amount"));
				item.price = Double.parseDouble(rs.getString("item_price"));
				item.name = rs.getString("name");
				item.brand = rs.getString("brand");
				arr.add(item);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return arr;
	}
	public ArrayList<String> getListOfAllowedItems(String Customer_id) {
		Connection con = ConnectionHandler.INISTANCE.Connect();
		int Family_number = 0;
		ArrayList<String> arr = new ArrayList<>();
		try {
			String s = "select family_number from customer_data where customer id = ?";
			PreparedStatement ps = con.prepareStatement(s);
			ps.setString(0, Customer_id);
			
			ResultSet rs = ps.executeQuery(s);
			while(rs.next()) {
				Family_number = Integer.parseInt(rs.getString(4));
			}
			if(Family_number < 6) {
				arr.add("Sugar:3Kg");
				arr.add("Oil:3L");
				arr.add("Duket:5kg");
			}
			else {
				arr.add("Sugar:5Kg");
				arr.add("Oil:5L");
				arr.add("Duket:5kg");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return arr;
	}
	public boolean SpecifyAmount(ArrayList<String> items_idWzAmount) {
		double totalAmount = 0;
		
			String str0 = "select * from items_data where item_id = ?";
			try {
				PreparedStatement ps1 = con.prepareStatement(str0);
				String[] idAndPrice = items_idWzAmount.get(0).split(":");
				ps1.setString(x, idAndPrice[0]);
				ResultSet rs = ps1.executeQuery();
				while(rs.next()) {
					totalAmount = Double.parseDouble(rs.getString("item_amount"));
				}
				double newAmount = totalAmount-Double.parseDouble(idAndPrice[1]);
			String str = "Update item_data set item_amount = ? where item_id = ?";
			
				PreparedStatement ps = con.prepareStatement(str);
				ps.setString(x, newAmount+"");
				ps.setString(1, idAndPrice[0]);
				if(ps.executeUpdate()!=-1) {
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return false;
	}
	public boolean validateItems(TextField name, TextField brand, TextField id, TextField amount, TextField price){

		if(String.valueOf(name).equals("") || String.valueOf(brand).equals("") || String.valueOf(id).equals("") || String.valueOf(amount).equals("") || String.valueOf(price).equals("")){
			return false;
		}
		else if(String.valueOf(amount).contains("[a-zA-Z]+")){
			return  false;
		}
		else if(String.valueOf(price).contains("[a-zA-Z]+")){
			return  false;
		}
		else if(String.valueOf(name).matches(".*\\d.*")){
			return  false;
		}
		else if(String.valueOf(id).contains("[a-zA-Z]+")){
			return  false;
		}
		else{
			String arr[] = {String.valueOf(name), String.valueOf(brand), String.valueOf(id), String.valueOf(amount), String.valueOf(price)};
			new CreateDatabaseTables();
			Items i = new Items();
			i.AddItems(arr);
			return true;
		}
	}
	public boolean validateRegistration(TextField Fname, TextField Lname, TextField Password, TextField location, TextField id){
		if(String.valueOf(Fname).equals(null) || String.valueOf(Lname).equals(null) || String.valueOf(Password).equals(null) || String.valueOf(location).equals(null) || String.valueOf(id).equals(null)){
			return false;
		}
		else if(String.valueOf(Fname).matches(".*\\d*.")){
			return false;
		}
		else if(String.valueOf(Lname).matches(".*\\d*.")){
			return false;
		}
		else if(String.valueOf(Password).length() < 6 ){

		}
		else{
			Pattern letter = Pattern.compile("[a-zA-z]]");
			Pattern digit = Pattern.compile("[0-9]");
			Pattern specialChars = Pattern.compile("[!@#$%^&*()_+=|<>?{}\\[\\]~-]");

			Matcher hasLetter = letter.matcher(String.valueOf(Password));
			Matcher hasDigit = digit.matcher(String.valueOf(Password));
			Matcher hasSpecialChar = specialChars.matcher(String.valueOf(Password));
			if( hasLetter.find()==false && hasDigit.find()==false || hasSpecialChar.find()==false){
				return false;
			}
			else{
				String s[] = {String.valueOf(Fname)+" "+String.valueOf(Lname), String.valueOf(Password), String.valueOf(location)};
				new Register().Register(s);
				return true;
			}
		}
		return false;
	}
}