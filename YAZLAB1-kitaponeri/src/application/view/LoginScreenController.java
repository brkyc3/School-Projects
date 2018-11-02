package application.view;

import javafx.scene.control.TextField;

import application.Kullanici;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LoginScreenController {

	@FXML
	private TextField IDText;
	@FXML
	private TextField sifreText;
	
	private Kullanici kullanici;
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
