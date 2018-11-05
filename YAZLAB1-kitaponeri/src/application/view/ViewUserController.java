package application.view;

import java.util.ArrayList;

import application.KitapDB;
import application.Kullanici;
import application.KullaniciDB;
import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ViewUserController {
	@FXML
	private Label idLabel;
	@FXML
	private Label konumLabel;
	@FXML
	private Label yasLabel;
	@FXML
	private Label userNameLabel;
	
	@FXML
	private TextField pageText;
	
	@FXML
	private ListView<String> userListView;
	
	private ObservableList<String> userList = FXCollections.observableArrayList();
	
	private ArrayList<Kullanici> liste = new ArrayList<Kullanici>();
	
	private Main main;
	private Kullanici goster;
	
	public ViewUserController() {
		
	}
	
	public void setMain(Main _main) {
		this.main = _main;
	}
	
	public ViewUserController getController() {
		return this;
	}
	
	@FXML
	private void initialize() {
		
		userListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			 
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    	        // Your action here
    	        System.out.println("Selected item: " + newValue);
    	        kullaniciGoster(liste.get(userListView.getSelectionModel().getSelectedIndex()));
    	        
    	    }
    	});
	}
	
	private void kullaniciGoster(Kullanici _goster) {
		if(_goster!=null) {
			goster = _goster;
			idLabel.setText(Integer.toString(goster.getUserId()));
			konumLabel.setText(goster.getLocation());
			yasLabel.setText(Integer.toString(goster.getAge()));
			userNameLabel.setText(goster.getUserName());
		}else {
			idLabel.setText("");
			konumLabel.setText("");
			yasLabel.setText("");
			userNameLabel.setText("");
		}
	}
	
	@FXML
	private void handleNextPage() {
		int pageNum = Integer.parseInt(pageText.getText());
		pageNum++;
		pageText.setText(Integer.toString(pageNum));
		handlePageInput();
	}
	
	@FXML
	private void handlePreviosPage() {
		int pageNum = Integer.parseInt(pageText.getText());
		if(pageNum>0) pageNum--;
		pageText.setText(Integer.toString(pageNum));
		handlePageInput();
	}
	
	@FXML
	private void handlePageInput() {
		liste.clear();
		liste = KullaniciDB.sayfaIleGetir(Integer.parseInt(pageText.getText()));
		yukle();
	}
	
	private void yukle() {
		userList.clear();
		for(Kullanici k:liste) {
			userList.add(k.getUserName());
		}
		
		userListView.setItems(userList);
	}
	
	@FXML
	private void handleDelete() {
		KullaniciDB.deleteUser(goster.getUserId());
	}
	
	@FXML
	private void handleAdd() {
		main.showAddUser();
	}
	
}















