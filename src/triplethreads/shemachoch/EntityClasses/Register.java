package triplethreads.shemachoch.EntityClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Register {
    public boolean Register(String credential[]) {
        Connection con = ConnectionHandler.INISTANCE.Connect();

        try {
            String sql = "insert into users_data "
                    + "(Name, Password , Location)"
                    + "values(?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);
            for (int i = 1; i < 4; i++) {
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
}
