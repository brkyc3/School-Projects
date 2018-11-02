package application.view;

import javafx.scene.control.TextField;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdfViewerFX.PDFViewer;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;

public class LoginScreenController {
/*
 * 
 * 
 * 
 * pdf okumaya yarýyo jar indirdim projeye ekledimjPDFViewerFX.jar nereye koyulacagini tam kesiremedim
 * 	PDFViewer m_PDFViewer = new PDFViewer();
		try {
			m_PDFViewer.loadPDF("C:\\Users\\asus\\Desktop\\asd.pdf");
		} catch (PDFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BorderPane borderPane = new BorderPane(m_PDFViewer);
		Scene scene = new Scene(borderPane);
		primaryStage.setTitle("JavaFX PDFViewer - Qoppa Software");
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
	@FXML
	private TextField IDText;
	@FXML
	private TextField sifreText;
	
	private Main main;
	
	public LoginScreenController() {		
	}
	
	private void initialize() {	
	}
	
	@FXML
	private void handleLogin() {
		if(isInputValid()) {
			boolean login = Main.Giris(IDText.getText(), sifreText.getText());		
			if(login) {
				//load main scene
				main.showMainPage();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
	            //alert.initOwner(dialogStage);
	            alert.setTitle("Hatali giris!");
	            alert.setHeaderText("Yanlis kullanici adi veya sifre!");
	            //alert.setContentText(errorMessage);
	            alert.showAndWait();
			}
		}
	}
	
	@FXML
	private void handleSignUp() {
		main.showSignUpScreen();
	}

	
	private boolean isInputValid() {
        String errorMessage = "";

        if (IDText.getText() == null || IDText.getText().length() == 0) {
            errorMessage += "No valid ID!\n"; 
        }
        if (sifreText.getText() == null || sifreText.getText().length() == 0) {
            errorMessage += "No valid pass!\n"; 
        }
        

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            //alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
        }
    }
	
	public void setMain(Main _main) {
		this.main = _main;
	}

}
