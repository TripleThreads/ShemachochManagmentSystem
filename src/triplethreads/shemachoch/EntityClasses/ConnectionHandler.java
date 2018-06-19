package triplethreads.shemachoch.EntityClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public enum ConnectionHandler {
	INSTANCE;
	public Connection getConnection() {
		Connection connect = null;
		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/shemt?autoReconnect=true&useSSL=false","root", "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connect;
	}
}