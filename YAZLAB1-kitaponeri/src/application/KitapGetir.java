package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class KitapGetir {
	private Connection conn = null;
	private Statement stmt = null;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/KitapOneri?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";

	static final String USER = "root";
	static final String PASS = "";
 
	
	public KitapGetir() {


	
		try {
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public ArrayList<String> yuksekOrtalama(){
		ArrayList<String> kitapIsimleri =new ArrayList<>();
		String sql = "SELECT BookTitle FROM bxbooks AS bk"
				+ " inner join( "
				+ "select ISBN from bxbookratings "
				+ "GROUP BY ISBN "
				+ "having count(BookRating) > 10 "
				+ "order by avg(Bookrating) DESC "
				+ "LIMIT 10 "
				+ ") AS rt "
				+ "ON rt.ISBN = bk.ISBN";
		//10 DAN FAZLA OYLANAN KITAPLAR ICIN ORTALAMA HESAPLIYOR
		try {
			ResultSet rs =stmt.executeQuery(sql);
			
			while(rs.next()) {
				kitapIsimleri.add(rs.getString("BookTitle)"));
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return kitapIsimleri;
		
		
	}
	public ArrayList<String> cokOylanan(){
		ArrayList<String> kitapIsimleri = new ArrayList<>();
		String sql = "SELECT BookTitle FROM bxbooks AS bk "
				+ "inner join("
				+ "select ISBN  from bxbookratings"
				+ " GROUP BY ISBN "
				+ "ORDER by count(bookrating) desc"
				+ " LIMIT 10"
				+ ") as rt "
				+ "ON rt.ISBN = bk.ISBN ";
		try {
			ResultSet rs =stmt.executeQuery(sql);
			
			while(rs.next()) {
				kitapIsimleri.add(rs.getString("BookTitle"));
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return kitapIsimleri;
		
		
	}
	
	public ArrayList<String> sonEklenen(){
		return null;
	}

}
