package tr.com.zerva.Gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Bagla {
	static Connection myConn;
	static Statement myState;
	static ResultSet myRs;
	static ResultSet yap() {
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3307/otomasyon","root","root");
			myState = myConn.createStatement();
			myRs = myState.executeQuery("SELECT * FROM accounts");
			return myRs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return myRs;
		
	}
	static void ekle(String sql_sorgu) throws SQLException {
		myState.executeUpdate(sql_sorgu);
	}
	static void update(String sql_sorgu) throws SQLException {
		myState.executeUpdate(sql_sorgu);
	}
	static ResultSet bul(String sql) throws SQLException {
		ResultSet myRs= null;
		myConn = DriverManager.getConnection("jdbc:mysql://localhost:3307/otomasyon","root","root");
		myState = myConn.createStatement();
		myRs = myState.executeQuery(sql);
		return myRs;
		
	}
	public static int getYetkiId(String kullaniciAdi) throws SQLException {
        String query = "SELECT yetkiId FROM accounts WHERE kullaniciAdi = '" + kullaniciAdi + "'";
        ResultSet resultSet = myState.executeQuery(query);

        if (resultSet.next()) {
            return resultSet.getInt("yetkiId");
        } else {
            return -1; 
        }
        }
	 public static String getJdbcUrl() {
	        return "jdbc:mysql://localhost:3307/otomasyon";
	    }

	    public static String getUsername() {
	        return "root";
	    }

	    public static String getPassword() {
	        return "root";
	    }
		public static Object getConnection() {
			// TODO Auto-generated method stub
			return null;
		}
}