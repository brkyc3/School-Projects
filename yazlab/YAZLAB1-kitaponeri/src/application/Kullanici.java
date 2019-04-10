package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Kullanici {
	private Connection conn = null;
	private Statement stmt = null;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/KitapOneri?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";

	static final String USER = "root";
	static final String PASS = "";

	public void oyVer(String isbn, int rating) {
		try {
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();
			String sql = "select count(*) as cnt from bxbookratings where userId =" + userId + " and ISBN = \"" + isbn
					+ "\"";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			if (rs.getInt("cnt") < 1)
				sql = "insert into  bxbookratings values(" + userId + " , \"" + isbn + "\" , " + rating + ")";
			else
				sql = "update   bxbookratings set BookRating = "+rating+" where userId =" + userId + " and ISBN = \"" + isbn + "\"";
			
			System.out.println(sql);
			stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean yeterinceOyVerdimi() {
		try {
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();

			String sql = "select count(*) as cnt  from bxbookratings where userId = " + userId;
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			System.out.println(" oy sayisi " + rs.getInt("cnt"));
			return !(rs.getInt("cnt") < 10);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean insertToDb() {
		//
		try {
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();

			String sql = "INSERT INTO bxusers values (" + userId + " , \"" + location + "\" , \"" + age + "\", \""
					+ password + "\", \"" + userName + "\")";
			stmt.execute(sql);

			return true;
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

	}

	public Kullanici(int userId, String location, int age, String password, String userName) {

		this.userId = userId;
		this.location = location;
		this.age = age;
		this.password = password;
		this.userName = userName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	private int userId;
	private String location;
	private int age;
	private String password;
	private String userName;
}
