package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class KullaniciDB {
	private  static int sayfadakiKullaniciAdedi=50;
	public int getSayfadakiKitapAdedi() {
		return sayfadakiKullaniciAdedi;
	}
	public void setSayfadakiKitapAdedi(int sayfadakiKitapAdedi) {
		this.sayfadakiKullaniciAdedi = sayfadakiKitapAdedi;
	}



	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/KitapOneri?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";

	static final String USER = "root";
	static final String PASS = "";
	public  static void deleteUser(int userId) {
		try {
			Class.forName(JDBC_DRIVER);

			 Connection conn  = DriverManager.getConnection(DB_URL, USER, PASS);

			 Statement stmt = conn.createStatement();
			// foreingkey oldugundan once verdigi oylari sil
			 String sql = "DELETE FROM  bxbookratings WHERE userId = "+userId; 
				stmt.execute(sql);
			sql = "DELETE FROM  bxusers WHERE userId = "+userId;
			stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  static ArrayList<Kullanici> sayfaIleGetir(int ninciSayfa) {
		ArrayList<Kullanici> kullanicilar =new ArrayList<>();
		
		try {
			Class.forName(JDBC_DRIVER);

			 Connection conn  = DriverManager.getConnection(DB_URL, USER, PASS);

			 Statement stmt = conn.createStatement();
			int start =278858+sayfadakiKullaniciAdedi*(ninciSayfa-1);
			int end = 278858+sayfadakiKullaniciAdedi*(ninciSayfa);

			 String sql = "SELECT * FROM bxusers "
						+ "LIMIT "+start +" , "+end;
			ResultSet rs =stmt.executeQuery(sql);
			while(rs.next()) {
				kullanicilar.add(
						new Kullanici(
								rs.getInt("UserId"), 
								rs.getString("location"),
								rs.getInt("Age"),
								"***", rs.getString("username")
								));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return kullanicilar;
	}

	public static Kullanici getKullanici(int userId) {
		try {
			Class.forName(JDBC_DRIVER);

			 Connection conn  = DriverManager.getConnection(DB_URL, USER, PASS);

			 Statement stmt = conn.createStatement();

			 String sql = "SELECT * FROM bxusers "
						+ "where userID = "+userId;
			ResultSet rs =stmt.executeQuery(sql);
			rs.next();
				
					return	new Kullanici(
								rs.getInt("UserId"), 
								rs.getString("location"),
								rs.getInt("Age"),
								"***", rs.getString("username")
								);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
