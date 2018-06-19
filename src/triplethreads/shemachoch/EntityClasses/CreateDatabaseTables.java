package triplethreads.shemachoch.EntityClasses;

import java.sql.Connection;
import java.sql.Statement;


public class CreateDatabaseTables {
	public CreateDatabaseTables() {
		try{
			Connection con = ConnectionHandler.INSTANCE.getConnection();
			Statement st = con.createStatement();
			
			String sql1 = "create table if not exists customer_data("
					  +"customer_id int(6) auto_increment primary key,"
					  +"FirstName varchar(20) not null,"
					  +"LastName varchar(20) not null,"
					  +"Location varchar(255) not null,"
					  +"Family_number int(2) not null,"
					  +"Allowed_Amount int(2) not null,"
					  +"Photo blob);";
			st.execute(sql1);
			
			String sql2 = "create table if not exists items_data("
					  +"Item_id varchar(7) not null,"
					  +"Item_Name varchar(30) not null,"
					  +"Item_Brand varchar(30) not null,"
					  +"Item_amount int(20) not null,"
					  +"Item_price int(20) not null);";
					  
			st.execute(sql2);

			
			String sql3 = "create table if not exists removed_customers("
					  +"Fname varchar(20) not null,"
					  +"Lname varchar(20) not null,"
					  +"House_Number int(20) not null,"
					  +"Family_id int(10) not null,"
					  +"photp blob not null);";
			st.execute(sql3);

			
			String sql4 = "create table if not exists sold_Items("
					  +"Item_id int(6),"
					  +"Amount int(20) not null,"
					  +"Seller_Id int(20) not null,"
					  +"Buyer_Id int(30) not null,"
					  +"date Date not null);";
			
			st.execute(sql4);
			
			String sql5 = "create table if not exists user_data("
					  +"First_Name varchar(20) not null,"
					  +"Password varchar(20) not null,"
					  +"Location varchar(20) not null);";
			
			st.execute(sql5);
			
			}
			catch(Exception e){
				e.printStackTrace();
			}
	}
}