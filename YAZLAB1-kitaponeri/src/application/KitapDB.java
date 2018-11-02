package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class KitapDB {
	private Connection conn = null;
	private Statement stmt = null;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/KitapOneri?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";

	static final String USER = "root";
	static final String PASS = "";
 
	private int sayfadakiKitapAdedi=50; 
	
	public int getSayfadakiKitapAdedi() {
		return sayfadakiKitapAdedi;
	}
	public void setSayfadakiKitapAdedi(int sayfadakiKitapAdedi) {
		this.sayfadakiKitapAdedi = sayfadakiKitapAdedi;
	}
	public KitapDB() {


	
		try {
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public ArrayList<Kitap> yuksekOrtalama(){
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
		}
		return kitaplar;
		
		
	}
	public ArrayList<Kitap> cokOylanan(){
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
		}
		
		
		
		return kitaplar;
		
		
	}
	
	public ArrayList<Kitap> sonEklenen(){
		ArrayList<Kitap> kitaplar =new ArrayList<>();
		String sql = "SELECT * FROM bxbooks "
				+ "WHERE insertionDate > \"2018-11-02 18:04:44\" "
				+ "ORDER by insertionDate desc "
				+ "LIMIT 5";

		try {
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
		}
		return kitaplar;
	}
	
	public ArrayList<Kitap> sayfaIleGetir(int ninciSayfa){
		ArrayList<Kitap> kitaplar =new ArrayList<>();
		String sql = "SELECT * FROM bxbooks "
				+ "LIMIT "+sayfadakiKitapAdedi*(ninciSayfa-1) +" , "+sayfadakiKitapAdedi*(ninciSayfa);

		try {
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
		}
		return kitaplar;
		
	}
	public void deleteBook(String isbn) {
		try {
			Class.forName(JDBC_DRIVER);

			 Connection conn  = DriverManager.getConnection(DB_URL, USER, PASS);

			 Statement stmt = conn.createStatement();
			 
			// foreingkey oldugundan  oylari sil
			 String sql = "DELETE FROM  bxbookratings WHERE ISBN = "+isbn;
			stmt.execute(sql);
			sql = "DELETE FROM  bxbooks WHERE ISBN = "+isbn;
			stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
