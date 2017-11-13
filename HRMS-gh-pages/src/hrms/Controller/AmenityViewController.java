/*
 * The amenityViewController will manage the functionalities of the AmenityView.
 */
package hrms.Controller;

import hrms.HRMS;
import hrms.Model.Amenity;
import hrms.Model.Application;
import hrms.Model.Database;
import hrms.Model.ModelControl;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author agonzalez26
 */

public class AmenityViewController implements Initializable {
	// variables
	@FXML
	private Button logInButton;
	@FXML
	private Button backButton;
	@FXML
	private Button nextButton;
	@FXML
	private Button cancelButton;
	@FXML
	private TextField billAmountText;
	@FXML
	private TextArea amenityDescription;
	@FXML
	private Stage stage = null;
	@FXML
	private Parent root = null;
	@FXML
	private AnchorPane employeeLoginView;
	@FXML
	private TextField guestFirstName;
	@FXML
	private TextField guestLastName;
        @FXML
        private ChoiceBox roomChoiceBox; 

        @FXML        
        private ListView<CheckBox> listView;
        ArrayList<Amenity> hotelAmenities;
                
        private ObservableList<CheckBox> cBoxes = 
        FXCollections.observableArrayList();      
        
	private Application app = new Application();
        private ModelControl mc;
        private String selRoom;
        
        HashMap hm;
        HashMap amenIds = new HashMap();

	/*
	 * Function handles all the button Action Events
	 */
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		// if login button selected
		if (event.getSource() == logInButton) {
			// get reference to the button's stage
			stage = (Stage) logInButton.getScene().getWindow();
			// load up OTHER FXML document
			root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeLoginView.fxml"));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
		}
		// if back button selected
		else if (event.getSource() == backButton) {
                        app.setMap(hm);
                    
			// get reference to the button's stage
			stage = (Stage) nextButton.getScene().getWindow();
                        FXMLLoader fxLoader = new FXMLLoader(HRMS.class.getResource("View/RoomSelectionView.fxml"));
                        root = (Parent)fxLoader.load();  
                        RoomSelectionViewController controller = fxLoader.<RoomSelectionViewController>getController();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        controller.initRooms();
                        stage.show();
		}
		// if next button selected
		else if (event.getSource() == nextButton) {
                        app.setMap(hm);
                    
			stage = (Stage) nextButton.getScene().getWindow();
			root = FXMLLoader.load(HRMS.class.getResource("View/ConfirmationView.fxml"));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
		}
		// if cancel button selected
		else if (event.getSource() == cancelButton) {
			stage = (Stage) cancelButton.getScene().getWindow();
			root = FXMLLoader.load(HRMS.class.getResource("View/HomeView.fxml"));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
		}
		// exit the application
		else {
			System.exit(0);
		}

		// create a new scene with root and set the stage
//		Scene scene = new Scene(root);
//		stage.setScene(scene);
//		stage.show();
	}

        @FXML
        public void handleAddAmenities(){
            List<String> selAmens = new ArrayList<String>();
            for(CheckBox cb: cBoxes){
                if(cb.isSelected()){
                    selAmens.add(cb.getText());
                }
            }
            hm.put(selRoom, selAmens);
            app.setMap(hm);
            billAmountText.setText("$"+app.getBill());
        }
        
	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
            hm = app.getMap();
            Database db = new Database();        
            mc = new ModelControl(db.getConnection());
            mc.initialize();		
            hotelAmenities = mc.getHotelAmenities();

            for(Amenity a: hotelAmenities){
                String txt = a.getName()+": "+ a.getDescription()+" $"+a.getPrice();
                CheckBox cb = new CheckBox(txt);
                cBoxes.add(cb);
                amenIds.put(txt, a.getId());
            }
            listView.setItems(cBoxes);

                ObservableList<String> rooms = FXCollections.observableArrayList(app.getChosenRooms());

                if(!rooms.isEmpty()){
                    roomChoiceBox.setItems(rooms);
                    roomChoiceBox.setValue(rooms.get(0));
                    selRoom = rooms.get(0);
                }

          
                onchangeChoiceBox();
          
        
 
                try{
                        List<String> selAmens = (List<String>) hm.get(selRoom);
                        for(CheckBox cb: cBoxes){
                            if(selAmens.contains(cb.getText())){
                                cb.setSelected(true);
                            }
                    }
                    }catch(NullPointerException e){
                        System.out.println("list not made yet");
                    }
                
                billAmountText.setText("$"+app.getBill());
                app.setAmenIds(amenIds);
                
                db.closeConnection();
    }
        
        public void onchangeChoiceBox(){
            roomChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String value, String newValue) {
       
                    selRoom = newValue;
                    for(CheckBox cb: cBoxes){
                        cb.setSelected(false);             
                    }
                    try{
                        List<String> selAmens = (List<String>) hm.get(selRoom);
                        for(CheckBox cb: cBoxes){
                            if(selAmens.contains(cb.getText())){
                                cb.setSelected(true);
                            }
                    }
                    }catch(NullPointerException e){
                        System.out.println("list not made yet");
                    }
                    
       
                }
            });
        }
        
        
       
}
