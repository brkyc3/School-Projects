package application;
	
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import application.view.LoginScreenController;
import application.view.MainPageController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Books");

        initRootLayout();

        showLoginScreen();
        
	}
	
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showLoginScreen() {
    	try {		
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();           
            loader.setLocation(Main.class.getResource("view/LoginScreen.fxml"));           
            AnchorPane loginScreen = (AnchorPane) loader.load();
            // Set person overview into the center of root layout.
            rootLayout.setCenter(loginScreen);          
            // Give the controller access to the main app.
            LoginScreenController controller = loader.getController();
            controller.setMain(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
    
    public void showMainPage() {
    	try {		
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();           
            loader.setLocation(Main.class.getResource("view/MainPage.fxml"));           
            AnchorPane mainPage = (AnchorPane) loader.load();
            // Set person overview into the center of root layout.
            rootLayout.setCenter(mainPage);          
            // Give the controller access to the main app.
            MainPageController controller = loader.getController();
            //controller.setMain(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
    
   
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static boolean Giris(String userName, String sifre) {
		//kullanici var mi kontrol et
		boolean kullaniciVarMi = true; 
		return kullaniciVarMi;
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
