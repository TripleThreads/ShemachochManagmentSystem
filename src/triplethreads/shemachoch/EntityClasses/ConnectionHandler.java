package triplethreads.shemachoch.EntityClasses;

		import java.sql.Connection;
		import java.sql.DriverManager;
		import java.sql.SQLException;


public enum ConnectionHandler {
	INISTANCE;
	public Connection Connect() {
		Connection connect = null;
		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/shemt?autoReconnect=true&useSSL=false","root", "classified");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connect;
	}
}