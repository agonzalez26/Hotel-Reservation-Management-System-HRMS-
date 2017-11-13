/*
 * The GuestViewController handles functionalities for GuestView.
 */
package hrms.Controller;

import hrms.HRMS;
import hrms.Model.Application;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
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
public class GuestViewController implements Initializable {
    // variables

    ObservableList<String> guestCountList = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "10+");

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
    private AnchorPane guestView;
    @FXML
    private ChoiceBox guestCountBox;
    @FXML
    private Stage stage = null;
    @FXML
    private Parent root = null;

    Application app = new Application();

    /*
	 * Function that handles all button events
     */
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        // if login button selected
        if (event.getSource() == logInButton) {
            // get reference to the button's stage
            stage = (Stage) logInButton.getScene().getWindow();
            // load up OTHER FXML document
            root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeLoginView.fxml"));
        } // if back button selected
        else if (event.getSource() == backButton) {
            // get reference to the button's stage
            stage = (Stage) backButton.getScene().getWindow();
            // load up OTHER FXML document
            root = FXMLLoader.load(HRMS.class.getResource("View/DayView.fxml"));
        } // if next button selected
        else if (event.getSource() == nextButton) {
            app.setGuestCount(guestCountBox.getValue().toString());
            stage = (Stage) nextButton.getScene().getWindow();
            root = FXMLLoader.load(HRMS.class.getResource("View/RoomView.fxml"));

        } // if cancel button selected
        else if (event.getSource() == cancelButton) {
            stage = (Stage) cancelButton.getScene().getWindow();
            root = FXMLLoader.load(HRMS.class.getResource("View/HomeView.fxml"));
        } // if exit application selected
        else {
            System.exit(0);
        }

        // create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        guestCountInitialize();
        billAmountText.setText("$"+app.getBill());
        guestCountBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String value, String newValue) { 
                    app.setGuestCount(newValue);
                    billAmountText.setText("$"+app.getBill());
                }       
            });    
                    
    }

    /*
	 * Setting up the choice box for guest Count
	 * 
	 * @pre: guestCount >=0 , GuestView
	 * 
	 * @post: guestCount >0 0, RoomView
     */
    private void guestCountInitialize() {
        // setting the values into the choice box
        guestCountBox.setItems(guestCountList);
        // setting the default value to be zero
        guestCountBox.setValue(app.getGuestCount());

    }

}
