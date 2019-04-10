package application;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Kitap {


	private Connection conn = null;
	private Statement stmt = null;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/KitapOneri?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";

	static final String USER = "root";
	static final String PASS = "";

	
	public void insertToDB() {
		try {
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();
			
			String sql = "INSERT INTO bxbooks( ISBN ,BookTitle,BookAuthor ,YearOfPublication ,Publisher ,ImageUrlS,ImageUrlM,ImageUrlL) values (\""+isbn+"\", \""+bookTitle+"\", \""+bookAuthor+"\","+yearOfPublication+",\""+publisher+"\", \""+urlSmall+"\", \""+urlMedium+"\" , \""+urlLong+"\")";
			//System.out.println(sql);
			stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
public Kitap(String isbn, String bookTitle, String bookAuthor, int yearOfPublication, String publisher,
			String urlSmall, String urlMedium, String urlLong) {

		this.isbn = isbn;
		this.bookTitle = bookTitle;
		this.bookAuthor = bookAuthor;
		this.yearOfPublication = yearOfPublication;
		this.publisher = publisher;
		this.urlSmall = urlSmall;
		this.urlMedium = urlMedium;
		this.urlLong = urlLong;
	}
public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getBookTitle() {
		return bookTitle;
	}
	public StringProperty getTitleProperty() {
		StringProperty s = new SimpleStringProperty((String) bookTitle);
		return s;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	
	public String getBookAuthor() {
		return bookAuthor;
	}
	public StringProperty getAuthorProperty() {
		StringProperty s = new SimpleStringProperty((String) bookAuthor);
		return s;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	public int getYearOfPublication() {
		return yearOfPublication;
	}
	public void setYearOfPublication(int yearOfPublication) {
		this.yearOfPublication = yearOfPublication;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getUrlSmall() {
		return urlSmall;
	}
	public void setUrlSmall(String urlSmall) {
		this.urlSmall = urlSmall;
	}
	public String getUrlMedium() {
		return urlMedium;
	}
	public void setUrlMedium(String urlMedium) {
		this.urlMedium = urlMedium;
	}
	public String getUrlLong() {
		return urlLong;
	}
	public void setUrlLong(String urlLong) {
		this.urlLong = urlLong;
	}
	
private String isbn;
private String bookTitle;
private String bookAuthor;
private int yearOfPublication;
private String publisher;
private String urlSmall;
private String urlMedium;
private String urlLong;

@Override
public boolean equals(Object arg0) {
	Kitap r=(Kitap)arg0;
	return super.equals(arg0) && this.isbn.equals(r.isbn);
}
@Override
public int hashCode() {
	// TODO Auto-generated method stub
	return this.isbn.hashCode();
}
//ayni kitabýn tekrar eklenip eklenmediði kontrol edilirken
//HashSet tarafýndan kullanýldýklarý için override edildiler.

}
