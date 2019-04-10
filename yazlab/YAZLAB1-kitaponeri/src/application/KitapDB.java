package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class KitapDB {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/KitapOneri?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";

	static final String USER = "root";
	static final String PASS = "";
 
	private static int sayfadakiKitapAdedi=50; 
	
	public int getSayfadakiKitapAdedi() {
		return sayfadakiKitapAdedi;
	}
	public void setSayfadakiKitapAdedi(int sayfadakiKitapAdedi) {
		this.sayfadakiKitapAdedi = sayfadakiKitapAdedi;
	}

	public static ArrayList<Kitap> yuksekOrtalama(){
		ArrayList<Kitap> kitaplar =new ArrayList<>();
		String sql = "SELECT * FROM bxbooks AS bk"
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
			Class.forName(JDBC_DRIVER);

			 Connection conn  = DriverManager.getConnection(DB_URL, USER, PASS);

			 Statement stmt = conn.createStatement();
			ResultSet rs =stmt.executeQuery(sql);
			
			while(rs.next()) {
				kitaplar.add(new Kitap(
						rs.getString("ISBN"),
						rs.getString("BookTitle"),
						rs.getString("BookAuthor"),
						rs.getInt("YearOfPublication"),
						rs.getString("Publisher"),
						rs.getString("ImageURLS"),
						rs.getString("ImageURLM"),
						rs.getString("ImageURLL")
					    )
						);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return kitaplar;
		
		
	}
	public static ArrayList<Kitap> cokOylanan(){
		ArrayList<Kitap> kitaplar = new ArrayList<>();
		String sql = "SELECT * FROM bxbooks AS bk "
				+ "inner join("
				+ "select ISBN  from bxbookratings"
				+ " GROUP BY ISBN "
				+ "ORDER by count(bookrating) desc"
				+ " LIMIT 10"
				+ ") as rt "
				+ "ON rt.ISBN = bk.ISBN ";
		try {
			Class.forName(JDBC_DRIVER);

			 Connection conn  = DriverManager.getConnection(DB_URL, USER, PASS);

			 Statement stmt = conn.createStatement();
			ResultSet rs =stmt.executeQuery(sql);
			
			while(rs.next()) {
				kitaplar.add(new Kitap(
						rs.getString("ISBN"),
						rs.getString("BookTitle"),
						rs.getString("BookAuthor"),
						rs.getInt("YearOfPublication"),
						rs.getString("Publisher"),
						rs.getString("ImageURLS"),
						rs.getString("ImageURLM"),
						rs.getString("ImageURLL")
					    )
						);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return kitaplar;
		
		
	}
	
	public static ArrayList<Kitap> sonEklenen(){
		ArrayList<Kitap> kitaplar =new ArrayList<>();
		String sql = "SELECT * FROM bxbooks "
				+ "WHERE insertionDate > \"2018-11-02 18:04:44\" "
				+ "ORDER by insertionDate desc "
				+ "LIMIT 5";

		try {
			Class.forName(JDBC_DRIVER);

			 Connection conn  = DriverManager.getConnection(DB_URL, USER, PASS);

			 Statement stmt = conn.createStatement();
			ResultSet rs =stmt.executeQuery(sql);
			
			while(rs.next()) {
				kitaplar.add(new Kitap(
						rs.getString("ISBN"),
						rs.getString("BookTitle"),
						rs.getString("BookAuthor"),
						rs.getInt("YearOfPublication"),
						rs.getString("Publisher"),
						rs.getString("ImageURLS"),
						rs.getString("ImageURLM"),
						rs.getString("ImageURLL")
					    )
						);
				
			
			}
			
		} catch (SQLException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		return kitaplar;
	}
	
	public static ArrayList<Kitap> sayfaIleGetir(int ninciSayfa){
		ArrayList<Kitap> kitaplar =new ArrayList<>();
		String sql = "SELECT * FROM bxbooks "
				+ "LIMIT "+sayfadakiKitapAdedi*(ninciSayfa-1) +" , "+sayfadakiKitapAdedi*(ninciSayfa);

		try {
			Class.forName(JDBC_DRIVER);

			 Connection conn  = DriverManager.getConnection(DB_URL, USER, PASS);

			 Statement stmt = conn.createStatement();
			
			ResultSet rs =stmt.executeQuery(sql);
			
			while(rs.next()) {
				kitaplar.add(new Kitap(
						rs.getString("ISBN"),
						rs.getString("BookTitle"),
						rs.getString("BookAuthor"),
						rs.getInt("YearOfPublication"),
						rs.getString("Publisher"),
						rs.getString("ImageURLS"),
						rs.getString("ImageURLM"),
						rs.getString("ImageURLL")
					    )
						);
				
			
			}
			
		} catch (SQLException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		return kitaplar;
		
	}
	public static void  deleteBook(String isbn) {
		try {
			Class.forName(JDBC_DRIVER);

			 Connection conn  = DriverManager.getConnection(DB_URL, USER, PASS);

			 Statement stmt = conn.createStatement();
			 String sql = "DELETE FROM  bxbookratings WHERE ISBN = \""+isbn+"\"";
			stmt.execute(sql);
			sql = "DELETE FROM  bxbooks WHERE ISBN = \""+isbn+"\"";
			stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
