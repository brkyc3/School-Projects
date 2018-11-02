package application;
	
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
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
				int id=0;
			
				String sql= "UPDATE bxusers\r\n" + 
						"SET userName =  UserId";
				stmt.execute(sql);
				
				
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
}
