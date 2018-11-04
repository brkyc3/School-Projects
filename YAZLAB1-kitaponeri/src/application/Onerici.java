package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashSet;

import java.util.List;

import java.util.Map.Entry;



public class Onerici {
	private long userId;
	private Connection conn = null;
	private Statement stmt = null;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/KitapOneri?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";

	static final String USER = "root";
	static final String PASS = "";
	private ArrayList<Entry<Long, Double>> benzerler; 
	
	public Onerici(long userId) {
		benzerler = new ArrayList<>();

		this.userId = userId;
		try {
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}



	public void benzerleriBul() {

		try {
			String sql;
			sql = "SELECT * FROM bxbookratings where userId = " + userId;
			ResultSet rs = stmt.executeQuery(sql);
			HashMap<String, Integer> user = new HashMap<>();
			while (rs.next()) {
				user.put(rs.getString("ISBN"), rs.getInt("BookRating"));
				
			}
			sql = "SELECT * FROM bxbookratings as b1 ,bxbookratings as b2 WHERE b1.UserID <> \""+userId+"\" and b2.UserID =\""+userId+"\" and b1.ISBN =b2.ISBN";
			rs = stmt.executeQuery(sql);
		
	
	HashMap<Long, Double> userDistance=new HashMap<>();;
			
			
			while (rs.next()) {
			int left =rs.getInt("b1.BookRating");
			int right =rs.getInt("b2.BookRating");
			
			userDistance.put(rs.getLong("b1.userid"),userDistance.getOrDefault(rs.getLong("b1.userid"), 0.0) +Math.pow(left- right,2));
				
			
				
				
			}
			
			
			benzerler = new ArrayList<>(userDistance.entrySet());
	        benzerler.sort(Entry.comparingByValue());

	       
			
			
	
		} catch (SQLException se) {

			se.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}

	}



	public HashSet<Kitap> kitapOner(int adet) {
		this.benzerleriBul();
		List<Entry<Long, Double>> sub = benzerler;

		HashSet<Kitap> kitaplar = new HashSet<Kitap>();
		int count =0;
		
		HashSet<String> kullaniciOkuduklari = new HashSet<String>();
		try {
			String sql;
			sql = "SELECT ISBN FROM bxbookratings where userId = \"" + userId + "\"";
			ResultSet rs0 = stmt.executeQuery(sql);
			
			while (rs0.next()) {
				kullaniciOkuduklari.add(rs0.getString("ISBN"));
			}
			for (Entry<Long, Double>  p : sub) {
				
				sql = "SELECT * FROM bxbookratings where UserId = \"" + p.getKey()+"\"";

				ResultSet rs = stmt.executeQuery(sql);
				

				while (rs.next()) {
					if ((!kullaniciOkuduklari.contains(rs.getString("ISBN"))) && rs.getInt("BookRating")>7 ) {
						sql = "SELECT * FROM bxbooks where ISBN = \"" + rs.getString("ISBN") + "\"";
					
						Statement stmt2 = conn.createStatement();
						ResultSet rs1 = stmt2.executeQuery(sql);
						while (rs1.next()) {
							if(count<adet) {
							kitaplar.add(new Kitap(
									rs1.getString("ISBN"),
									rs1.getString("BookTitle"),
									rs1.getString("BookAuthor"),
									rs1.getInt("YearOfPublication"),
									rs1.getString("Publisher"),
									rs1.getString("ImageURLS"),
									rs1.getString("ImageURLM"),
									rs1.getString("ImageURLL")
								    )
									);
							count++;
							
							}
							else
								return kitaplar;
						}
					}

				}

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return kitaplar;

	}
}
