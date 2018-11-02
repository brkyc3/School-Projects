package application;

	
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import application.view.LoginScreenController;
import application.view.MainPageController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

//ALTER TABLE bxbookratings ADD FOREIGN KEY (ISBN) REFERENCES bxbooks(ISBN);
//DELETE FROM bxbookratings WHERE ISBN NOT IN (SELECT BB.ISBN FROM bxbooks AS BB)
//CREATE TABLE bxbookratings ( UserID int(11) NOT NULL default '0', ISBN varchar(13)CHARACTER SET latin1 COLLATE latin1_bin NOT NULL default '', BookRating int(11) NOT NULL default '0', INDEX(ISBN), PRIMARY KEY (UserID,ISBN), FOREIGN KEY (UserID) REFERENCES bxusers(UserID), FOREIGN KEY (ISBN) REFERENCES bxbooks(ISBN) )
//ALTER TABLE bxbookratings ADD KEY ix1(ISBN, BookRating ); avg ve count lu queryleri hýzlandýrmak için
//ALTER TABLE bxbooks ADD COLUMN id INT AUTO_INCREMENT UNIQUE; // son eklenenleri id ye gore tersten sýralayarak bulmak icin
public class Main extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	private static void test(){
	Kullanici kk = new Kullanici(1231251252, "sakarya", 23, "qwer123", "burak123");
		kk.insertToDb();
		ArrayList<Kullanici> kullanicilar =KullaniciDB.sayfaIleGetir(1);// 1. sayfa 0 ile 50 arasýndaki kullanilari getir
		for(Kullanici k :kullanicilar){
		System.out.println("kullanici " +k.getUserName());
		}
		KullaniciDB.deleteUser(1231251252);


		Kitap kt = new Kitap("!!!!!!!","incog","dav" ,1995,"asd","burak","url","urls");
		kt.insertToDB();
		ArrayList<Kitap> kitaplar = KitapDB.sayfaIleGetir(2);// 1. sayfa 50 ile 100 arasýndaki kullanilari getir
		for(Kitap k :kitaplar){
		System.out.println(k.getBookTitle());
		}
		
		
		
		ArrayList<Kitap> yuksekOrt =KitapDB.yuksekOrtalama();
		System.out.println("Yuksek ortalamali kitaplar");
		System.out.println();
		for(Kitap k:yuksekOrt) {
			System.out.println(k.getBookTitle());
		}
		ArrayList<Kitap> cokOylanan =KitapDB.cokOylanan();
		System.out.println();
		System.out.println();
		
		
		
		
		System.out.println("Cok oylanan kitaplar");
		System.out.println();
		for(Kitap k:cokOylanan) {
			System.out.println(k.getBookTitle());
		}
		System.out.println();
		System.out.println();
		
		
		
		
		ArrayList<Kitap> sonEklenen =KitapDB.sonEklenen();
		System.out.println("Son eklenen kitaplar");
		System.out.println();
		for(Kitap k:sonEklenen) {
			System.out.println(k.getBookTitle() );
		}
		System.out.println();
		System.out.println();
		
		
		KitapDB.deleteBook("!!!!!!!");
		
		
		
		
		
		
		
		/*******************************TEST*************************************************/
	}
	
	@Override
	public void start(Stage primaryStage) {
	
	
	/*******************************TEST*************************************************/
	
	
	
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
		test();
		
		
		
		launch(args);

	}

	
	public static boolean Giris(String userName, String sifre) {
		try {
			Class.forName(JDBC_DRIVER);

			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

			Statement stmt = conn.createStatement();

			String sql = "SELECT count(UserId) as cnt,b.UserName,b.sifre from bxusers as b where b.UserName = \""+userName +"\" and b.sifre = \""+sifre+"\"";
			ResultSet st = stmt.executeQuery(sql);
			st.next();
			return st.getInt("cnt") == 1;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean kullaniciVarMi = false; 
		return kullaniciVarMi;
	}

	public static boolean Kayit(int userId, String location, int age, String password, String userName) {
		Kullanici k= new Kullanici(userId,location,age,password,userName);
		
		boolean kullaniciVarMi = k.insertToDb(); //false donerse eklerken hata
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

			String sql = "UPDATE bxusers\r\n" + "SET userName =  UserId";
			stmt.execute(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
