package application.view;

import java.util.ArrayList;
import java.util.HashSet;

import application.Kitap;
import application.KitapDB;
import application.Kullanici;
import application.Main;
import application.Onerici;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainPageController {
	@FXML
	private Label showBookTitle;
	@FXML
	private Label showBookAuthor;
	@FXML
	private Label showBookYear;
	@FXML
	private Label showBookPublisher;
	@FXML
	private Label showBookISBN;
	@FXML
	private ImageView showBookCover;
	
	@FXML
	private Slider slider;
	
	@FXML 
	private ListView<String> kitapListBest;
	
	private ObservableList<String> kitapListeBest = FXCollections.observableArrayList();

	
	
	private ArrayList<Kitap> liste = new ArrayList<Kitap>();
	
	private Main main;
	private Kullanici user;
	private Kitap goster;
	
	public MainPageController() {
		
	}
	
	public void setMain(Main _main) {
		this.main = _main;
		
	} 
	public void setUser(Kullanici _user) {
		this.user = _user;
	}
    @FXML
    private void initialize() {
    	handleBest();
    	
    	kitapListBest.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
 
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    	        // Your action here
    	        System.out.println("Selected item: " + newValue);
    	        kitapGoster(liste.get(kitapListBest.getSelectionModel().getSelectedIndex()));
    	        
    	    }
    	});
    	/*
        slider.valueProperty().addListener(new ChangeListener() {

            @Override 
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                handleSlider(slider.getValue());

            }
        });*/
    }
	
	@FXML
	private void kitapGoster(Kitap kitap) {
		if(kitap!=null) {
		goster = kitap;
		showBookTitle.setText(kitap.getBookTitle());
		showBookAuthor.setText(kitap.getBookAuthor());
		showBookYear.setText(Integer.toString(kitap.getYearOfPublication()));
		showBookPublisher.setText(kitap.getPublisher());
		showBookISBN.setText(kitap.getIsbn());
		kapakGoster(kitap.getUrlMedium());

		} else {
			showBookTitle.setText("");
			showBookAuthor.setText(""); 
			showBookYear.setText("");
			showBookPublisher.setText("");
			showBookISBN.setText("");
		}
	}
	
	private void kapakGoster(String _url) {
		try {
			Image cover = new Image(_url, 100, 0, false, false);
			//showBookCover = new ImageView(cover);
			showBookCover.setImage(cover);
			showBookCover.setCache(true);
			
			System.out.println(_url);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@FXML
	private void readBook() {
		
	}
	
	public void yukle() {
		
		//liste = KitapDB.yuksekOrtalama();
		kitapListeBest.clear();
		for(Kitap k:liste) {
			kitapListeBest.add(k.getBookTitle());
		}
		kitapListBest.setItems(kitapListeBest);	
		 
	}
	
	@FXML
	private void handleBest() {
		liste.clear();
		liste = KitapDB.yuksekOrtalama();
		yukle();
	}
	 
	@FXML
	private void handlePop() {
		liste.clear();
		liste = KitapDB.cokOylanan();
		yukle();
	}
	
	@FXML
	private void handleNew() {
		liste.clear();
		liste = KitapDB.sonEklenen();
		yukle();
	}
	
	@FXML
	private void handleForYou() {
		liste.clear();
		boolean getir = user.yeterinceOyVerdimi();
		if(!getir) {
			Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Yeterince oy verilmedi!");
            alert.setHeaderText("En az 10 adet kitabi oylamalisiniz!");
            alert.showAndWait();
		}else {
			Onerici oner = new Onerici(user.getUserId());
			HashSet<Kitap> onerilenler = oner.kitapOner(10);
			for(Kitap k:onerilenler) {
				liste.add(k);
			}
			yukle();
		}
		
		yukle();
	}
	
	@FXML
	private void handleAll() {
		liste.clear();
		liste = KitapDB.sayfaIleGetir(1);
		yukle();
	}
	
	@FXML
	private void handleSlider() {
		main.oyla(goster.getIsbn(), (int)slider.getValue());
		System.out.println(slider.getValue());
	}
	
	@FXML
	private void handleRead() {
		main.readBook();
	}
	
	  
}
