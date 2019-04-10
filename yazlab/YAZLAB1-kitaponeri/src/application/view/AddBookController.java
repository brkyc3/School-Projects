package application.view;
import application.Kitap;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class AddBookController {

	@FXML
	private TextField ISBN;
	@FXML
	private TextField kitapAdi;
	@FXML
	private TextField yazar;
	@FXML
	private TextField basimYili;
	@FXML
	private TextField yayinevi;
	@FXML
	private TextField imgs;
	@FXML
	private TextField imgm;
	@FXML
	private TextField imgl;
	
	private Stage myStage;
	
	public AddBookController() {
		
	}
	
	@FXML
	private void handleAdd() {
		if(isInputValid()) {
			Kitap yeni = new Kitap(ISBN.getText(), kitapAdi.getText(), yazar.getText(),
					Integer.parseInt(basimYili.getText()), yayinevi.getText(), imgs.getText(),
					imgm.getText(),imgl.getText());
			yeni.insertToDB();
			myStage.close();
		}
	}
	
	@FXML
	private void handleCancel() {
		myStage.close();
	}
	
	public void setStage(Stage _myStage) {
		this.myStage = _myStage;
	}
	
	public AddBookController getController() {
		return this;
	}
	
	private boolean isInputValid() {
		String errorMessage = "";

        if (ISBN.getText() == null || ISBN.getText().length() == 0) {
            errorMessage += "ISBN Hatali!\n"; 
        }
        if (kitapAdi.getText() == null || kitapAdi.getText().length() == 0) {
            errorMessage += "Kitap Adi Hatali!\n"; 
        }
        if (yazar.getText() == null || yazar.getText().length() == 0) {
            errorMessage += "Yazar Adi Hatali!\n"; 
        }
        if (basimYili.getText() == null || basimYili.getText().length() == 0) {
            errorMessage += "Basim Yili Hatali!\n"; 
        }
        if (yayinevi.getText() == null || yayinevi.getText().length() == 0) {
            errorMessage += "Yayin Evi Hatali!\n"; 
        }
        if (imgs.getText() == null || imgs.getText().length() == 0) {
            errorMessage += "IMG-S Hatali!\n"; 
        }
        if (imgm.getText() == null || imgm.getText().length() == 0) {
            errorMessage += "IMG-M Hatali!\n"; 
        }
        if (imgl.getText() == null || imgl.getText().length() == 0) {
            errorMessage += "IMG-L Hatali!\n"; 
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
	
}
