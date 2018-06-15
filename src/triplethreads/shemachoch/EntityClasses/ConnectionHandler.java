package triplethreads.shemachoch.EntityClasses;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum ConnectionHandler {
    INISTANCE;
    public Connection getConnection() {
        Connection con = null;
        try {
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/shemt","root","");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
