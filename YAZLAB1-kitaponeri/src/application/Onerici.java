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


//ALTER TABLE bxbookratings ADD FOREIGN KEY (ISBN) REFERENCES bxbooks(ISBN);
//DELETE FROM bxbookratings WHERE ISBN NOT IN (SELECT BB.ISBN FROM bxbooks AS BB)
//CREATE TABLE bxbookratings ( UserID int(11) NOT NULL default '0', ISBN varchar(13)CHARACTER SET latin1 COLLATE latin1_bin NOT NULL default '', BookRating int(11) NOT NULL default '0', INDEX(ISBN), PRIMARY KEY (UserID,ISBN), FOREIGN KEY (UserID) REFERENCES bxusers(UserID), FOREIGN KEY (ISBN) REFERENCES bxbooks(ISBN) )
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
		System.out.println("const 1");
		// TODO Auto-generated constructor stub
		this.userId = userId;
		try {
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("const 2");
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
			System.out.println("!!" +user.size());
			sql = "SELECT * FROM bxbookratings as b1 ,bxbookratings as b2 WHERE b1.UserID <> \""+userId+"\" and b2.UserID =\""+userId+"\" and b1.ISBN =b2.ISBN";
			rs = stmt.executeQuery(sql);
		
	
	HashMap<Long, Double> userDistance=new HashMap<>();;
			
			
			while (rs.next()) {
			int left =rs.getInt("b1.BookRating");
			int right =rs.getInt("b2.BookRating");
			System.out.println(left +" "+ right +"---");
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



	public HashSet<String> kitapOner(int adet) {
		
		List<Entry<Long, Double>> sub = benzerler;
		
		for(Entry<Long, Double> p : sub) {
			System.out.println(p.getKey() +" " +p.getValue());
		}

		System.out.println("benzer "+benzerler.size());
		System.out.println("sub "+sub.size());
		HashSet<String> kitaplar = new HashSet<String>();
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
				System.out.println("id "+p.getKey() );
				ResultSet rs = stmt.executeQuery(sql);
				

				while (rs.next()) {
					if (!kullaniciOkuduklari.contains(rs.getString("ISBN")) && rs.getInt("BookRating")>7 ) {
						sql = "SELECT BookTitle FROM bxbooks where ISBN = \"" + rs.getString("ISBN") + "\"";
					
						Statement stmt2 = conn.createStatement();
						ResultSet rs1 = stmt2.executeQuery(sql);
						while (rs1.next()) {
							if(count<adet) {
							kitaplar.add(rs1.getString("BookTitle"));
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
