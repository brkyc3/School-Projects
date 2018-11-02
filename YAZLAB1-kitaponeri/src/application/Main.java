package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

//ALTER TABLE bxbookratings ADD FOREIGN KEY (ISBN) REFERENCES bxbooks(ISBN);
//DELETE FROM bxbookratings WHERE ISBN NOT IN (SELECT BB.ISBN FROM bxbooks AS BB)
//CREATE TABLE bxbookratings ( UserID int(11) NOT NULL default '0', ISBN varchar(13)CHARACTER SET latin1 COLLATE latin1_bin NOT NULL default '', BookRating int(11) NOT NULL default '0', INDEX(ISBN), PRIMARY KEY (UserID,ISBN), FOREIGN KEY (UserID) REFERENCES bxusers(UserID), FOREIGN KEY (ISBN) REFERENCES bxbooks(ISBN) )
//ALTER TABLE bxbookratings ADD KEY ix1(ISBN, BookRating ); avg ve count lu queryleri hýzlandýrmak için
//ALTER TABLE bxbooks ADD COLUMN id INT AUTO_INCREMENT UNIQUE; // son eklenenleri id ye gore tersten sýralayarak bulmak icin
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		KitapDB kitapGetir = new KitapDB();
		
		
		ArrayList<Kitap> yuksekOrt =kitapGetir.yuksekOrtalama();
		System.out.println("Yuksek ortalamali kitaplar");
		System.out.println();
		for(Kitap k:yuksekOrt) {
			System.out.println(k.getBookTitle());
		}
		ArrayList<Kitap> cokOylanan =kitapGetir.cokOylanan();
		System.out.println();
		System.out.println();
		
		
		
		
		System.out.println("Cok oylanan kitaplar");
		System.out.println();
		for(Kitap k:cokOylanan) {
			System.out.println(k.getBookTitle());
		}
		System.out.println();
		System.out.println();
		
		
		
		
		ArrayList<Kitap> sonEklenen =kitapGetir.sonEklenen();
		System.out.println("Son eklenen kitaplar");
		System.out.println();
		for(Kitap k:sonEklenen) {
			System.out.println(k.getBookTitle() );
		}
		System.out.println();
		System.out.println();
		launch(args);

	}

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/KitapOneri?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";

	static final String USER = "root";
	static final String PASS = "";

	static void uniqueUsernameEkle() {
		try {
			Class.forName(JDBC_DRIVER);

			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

			Statement stmt = conn.createStatement();

			String sql = "UPDATE bxusers\r\n" + "SET userName =  UserId";
			stmt.execute(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
