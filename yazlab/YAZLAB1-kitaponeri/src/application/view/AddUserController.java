package application.view;

import application.Kullanici;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class AddUserController {

	@FXML
	private TextField IDText;
	
	@FXML
	private TextField konumText;
	
	@FXML
	private TextField yasText;
	
	@FXML
	private TextField sifreText;
	@FXML
	private TextField kullaniciAdiText;
	
	private Main main;
	private Stage myStage;
	
	public AddUserController() {
		
	}
	
	public void setMain(Main _main) {
		this.main = _main;
	}
	
	public void setStage(Stage _myStage) {
		myStage = _myStage;
	}
	
	public AddUserController getController() {
		return this;
	}
	
	@FXML private void initialize() {}
	
	@FXML
	private void handleAddUser() {
		if(isInputValid()) {
			
				Kullanici kk = new Kullanici(Integer.parseInt(IDText.getText()),
						konumText.getText(), Integer.parseInt(yasText.getText()), 
						sifreText.getText(), kullaniciAdiText.getText());
				
				if(kk.insertToDb()) 
				{
					myStage.close();
				}
				else 
				{
					Alert alert = new Alert(AlertType.ERROR);
		            //alert.initOwner(dialogStage);
		            alert.setTitle("Hatali giris!");
		            alert.setHeaderText("Gecersiz giris!");
		            //alert.setContentText(errorMessage);
		            alert.showAndWait();
				}

				
			} else {
				Alert alert = new Alert(AlertType.ERROR);
	            //alert.initOwner(dialogStage);
	            alert.setTitle("Hatali giris!");
	            alert.setHeaderText("Gecersiz giris!");
	            //alert.setContentText(errorMessage);
	            alert.showAndWait();
			}
		
	}
	
	private boolean isInputValid() {
        String errorMessage = "";

        if (IDText.getText() == null || IDText.getText().length() == 0) {
            errorMessage += "No valid ID!\n"; 
        }
        if (konumText.getText() == null || konumText.getText().length() == 0) {
            errorMessage += "No valid konum!\n"; 
        }
        if (yasText.getText() == null || yasText.getText().length() == 0) {
            errorMessage += "No valid yas!\n"; 
        }
        if (sifreText.getText() == null || sifreText.getText().length() == 0 )
        {
            errorMessage += "No valid pass!\n"; 
        }
        if (kullaniciAdiText.getText() == null || kullaniciAdiText.getText().length() == 0 )
        { 	
            errorMessage += "No valid kullanici adi!\n"; 
        }
        /*
        String userName = kullaniciAdiText.getText();
        String cut = userName.substring(0,5);
        if(cut.equals("admin")) {
        	errorMessage += "admin ismini alamazsiniz!!\n";
        }
*/
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
	
	@FXML
	private void handleCancel() {
		myStage.close();
	}
	
}







