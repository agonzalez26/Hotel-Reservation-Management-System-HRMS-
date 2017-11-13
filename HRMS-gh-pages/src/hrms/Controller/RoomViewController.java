/*
 * The RoomViewController handles functionalities for RoomView.
 */
package hrms.Controller;

import hrms.HRMS;
import hrms.Model.Application;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author agonzalez26
 */
public class RoomViewController implements Initializable {
	// Variables
	ObservableList<String> roomCountList = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7",
			"8", "9", "10+");

	@FXML
	private AnchorPane roomView;
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
	private ChoiceBox roomCountBox;
	@FXML
	private Stage stage = null;
	@FXML
	private Parent root = null;
        
        Application app = new Application();
        

	/*
	 * Function that handles all button action events
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
			// get reference to the button's stage
			stage = (Stage) backButton.getScene().getWindow();
			// load up OTHER FXML document
			root = FXMLLoader.load(HRMS.class.getResource("View/GuestView.fxml"));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();

		}
		// if next button selected
		else if (event.getSource() == nextButton) {
                        app.setRoomCount(roomCountBox.getValue().toString());
			stage = (Stage) nextButton.getScene().getWindow();
                        FXMLLoader fxLoader = new FXMLLoader(HRMS.class.getResource("View/RoomSelectionView.fxml"));
                        root = (Parent)fxLoader.load();  
                        RoomSelectionViewController controller = fxLoader.<RoomSelectionViewController>getController();
                        String option = roomCountBox.getValue().toString();
                        int optionSelected = parseInt(option);  
                        controller.setNumRooms(optionSelected);
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        controller.initRooms();
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
		// exit application
		else {
			System.exit(0);
		}
		// create a new scene with root and set the stage
//		Scene scene = new Scene(root);
//		stage.setScene(scene);
//		stage.show();
	}

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		roomCountInitialize();
                billAmountText.setText("$"+app.getBill());
                roomCountBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String value, String newValue) { 
                    app.setRoomCount(newValue);
                    billAmountText.setText("$"+app.getBill());
                }       
            });  
                
                    
	}

	/*
	 * Setting up the choice box for room Count
	 * 
	 * @pre: roomCount >=0 , RoomView
	 * 
	 * @post: roomCount >0 0, RoomSelectionView
	 */
	private void roomCountInitialize() {
		// setting the values into the choice box
		roomCountBox.setItems(roomCountList);
		// setting the default value to be zero
		roomCountBox.setValue(app.getRoomCount());

	}
}
