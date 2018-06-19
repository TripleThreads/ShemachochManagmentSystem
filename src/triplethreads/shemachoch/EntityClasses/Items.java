package triplethreads.shemachoch.EntityClasses;

import java.sql.*;
import java.util.ArrayList;

public class Items {
    private String name = null;
    private String itemId = null;
    private String brand = null;
    private int amount = 0;
    private double price = 0;
    int x = 0;

    //getters of the variables
    public String getName(){
        return name;
    }
    public String getItemId(){
        return itemId;
    }
    public String getBrand(){
        return brand;
    }
    public int getAmount(){
        return amount;
    }
    public double getPrice(){
        return price;
    }

    public double getAvailableItemAmount(String Item_id) {
        double Amount = 0;

        Connection con = ConnectionHandler.INSTANCE.getConnection();
        try {
            String sql = "select Item_price from items_data where item_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(x, Item_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Amount = Double.parseDouble(rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Amount;
    }

    public double getPrice(String Item_id) {
        double Price = 0;

        Connection con = ConnectionHandler.INSTANCE.getConnection();
        try {
            String sql = "select Item_price from items_data where item_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(x, Item_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Price = Double.parseDouble(rs.getString(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Price;
    }

    public boolean addItems(String[] Data) {
        Connection con = ConnectionHandler.INSTANCE.getConnection();
        int num_part_of_last_id = 1;
        String num_of_zero_added = "000";
        try {
            String get_last_id = "SELECT Item_id FROM items_data ORDER BY item_id DESC LIMIT 1";
            PreparedStatement preparedStatement = con.prepareStatement(get_last_id);
            ResultSet resultset = preparedStatement.executeQuery(get_last_id);
            while(resultset.next()){
                String last_id = resultset.getString("item_id").substring(3);
                if (!last_id.isEmpty()){
                    num_part_of_last_id = Integer.parseInt(last_id) + 1;
                    num_of_zero_added = "";
                    for (int i = 0; i < 4 - Integer.parseInt(last_id)+"".length(); i++){
                        num_of_zero_added += "0";
                    }
                }
            }
            itemId = Data[0].substring(0,3) + num_of_zero_added + "" +num_part_of_last_id;
            String sql = "insert into items_data " + "(Item_name, Item_Brand, Item_amount , Item_Price, item_id)"
                    + "values(?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);
            for (int i = 1; i < 5; i++) {
                ps.setString(i, Data[i - 1]);
            }
            ps.setString(5, itemId);
            if (ps.execute()) {
                return true;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeItem(String Item_id) {

        Connection con = ConnectionHandler.INSTANCE.getConnection();
        try {
            String sql = "DELETE FROM Items_data WHERE Item_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(x, Item_id);
            if (ps.executeUpdate() != -1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Items> getListOfAllItems() {
        Connection con = ConnectionHandler.INSTANCE.getConnection();
        ArrayList<Items> arr = new ArrayList<>();
        try {
            String s = "select * from items_data";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(s);
            while (rs.next()) {
                Items item = new Items();
                item.amount = Integer.parseInt(rs.getString("item_amount"));
                item.price = Double.parseDouble(rs.getString("item_price"));
                item.name = rs.getString("item_name");
                item.brand = rs.getString("item_brand");
                item.itemId = rs.getString("item_id");
                arr.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public ArrayList<String> getListOfAllowedItems(String Customer_id) {
        Connection con = ConnectionHandler.INSTANCE.getConnection();
        int Family_number = 0;
        ArrayList<String> arr = new ArrayList<>();
        try {
            String s = "select family_number from customer_data where customer id = ?";
            PreparedStatement ps = con.prepareStatement(s);
            ps.setString(0, Customer_id);

            ResultSet rs = ps.executeQuery(s);
            while (rs.next()) {
                Family_number = Integer.parseInt(rs.getString(4));
            }
            if (Family_number < 6) {
                arr.add("Sugar:3Kg");
                arr.add("Oil:3L");
                arr.add("Duket:5kg");
            } else {
                arr.add("Sugar:5Kg");
                arr.add("Oil:5L");
                arr.add("Duket:5kg");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public boolean SpecifyAmount(ArrayList<String> items_idWzAmount) {
        double totalAmount = 0;
        Connection con = ConnectionHandler.INSTANCE.getConnection();
        String str0 = "select * from items_data where item_id = ?";
        try {
            PreparedStatement ps1 = con.prepareStatement(str0);
            String[] idAndPrice = items_idWzAmount.get(0).split(":");
            ps1.setString(x, idAndPrice[0]);
            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                totalAmount = Double.parseDouble(rs.getString("item_amount"));
            }
            double newAmount = totalAmount - Double.parseDouble(idAndPrice[1]);
            String str = "Update item_data set item_amount = ? where item_id = ?";

            PreparedStatement ps = con.prepareStatement(str);
            ps.setString(x, newAmount + "");
            ps.setString(1, idAndPrice[0]);
            if (ps.executeUpdate() != -1) {
                return true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }
}