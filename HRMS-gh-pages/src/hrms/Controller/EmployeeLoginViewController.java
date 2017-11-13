/*
 * The EmployeeLoginViewController handles functionalities of the EmployeeLoginView.
 */
package hrms.Controller;

import static hrms.Controller.HomeViewController.isNumeric;
import hrms.HRMS;
import hrms.Model.Amenity;
import hrms.Model.Application;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author agonzalez26
 */
public class EmployeeLoginViewController implements Initializable {
    // variables

    @FXML
    private AnchorPane employeeLoginView;
    @FXML
    private Button backButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button logInButton;
    @FXML
    private TextField employeeId;
    @FXML
    private TextField employeePassword;
    @FXML
    private Stage stage = null;
    @FXML
    private Parent root = null;

    Application app = new Application();

    /*
	 * Function handles all button action events
     */
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        // if back button selected
        if (event.getSource() == backButton) {
            // get reference to the button's stage
            stage = (Stage) backButton.getScene().getWindow();
            // load up OTHER FXML document
            root = FXMLLoader.load(HRMS.class.getResource("View/HomeView.fxml"));
        } // if next button selected
        else if (event.getSource() == nextButton) {
            // retrieve the employeeId and password TextField inputs
            String id = employeeId.getText();
            String password = employeePassword.getText();

            // checks if the employeeId field is empty
            if (id.isEmpty()) {
                // calls error method
                EmptyError();

                // get reference to the button's stage
                stage = (Stage) nextButton.getScene().getWindow();
                // load up OTHER FXML document
                root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeLoginView.fxml"));
            } // checks if id is not empty but password is empty
            else if (!id.isEmpty() && password.isEmpty()) {
                // call error message
                EmptyError();

                // get reference to the button's stage
                stage = (Stage) nextButton.getScene().getWindow();
                // load up OTHER FXML document
                root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeLoginView.fxml"));
            } // if id and password are not empty inputs
            else {
                // checks if id is a number and password is not empty
                if (isNumeric(id) && !password.isEmpty()) {
                    // get reference to the button's stage
                    if (id.equals("1111")) {
                        app.setEmp(false);
                    }

                    stage = (Stage) nextButton.getScene().getWindow();
                    // load up OTHER FXML document
                    root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeView.fxml"));

                } else {
                    // employee id does not exist
                    AccessDeniedError();

                    // get reference to the button's stage
                    stage = (Stage) nextButton.getScene().getWindow();
                    // load up OTHER FXML document
                    root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeLoginView.fxml"));

                }
            }
        } // exit application
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
        // TODO
    }

    /*
	 * Method called when employee id does not exist in the database
	 * 
	 * @pre: database is accessible, EmployeeloginView
	 * 
	 * @post: employee is logged into system, EmployeeView
     */
    private void AccessDeniedError() {
        // creates alert that the employee does not exist in the database
        Alert a = new Alert(Alert.AlertType.ERROR, "Employee Id does not exist", ButtonType.OK);
        // display the alert and must click ok before returning to main window
        Optional<ButtonType> result = a.showAndWait();

    }

    /*
	 * Method called when textfields, id or password, are empty
	 * 
	 * @pre: database is accessible, EmployeeLoginView
	 * 
	 * @post: filled textfields, EmployeeLoginView
     */
    private void EmptyError() {
        // creates alert that there is missing information in the textfields
        Alert a = new Alert(Alert.AlertType.ERROR, "Missing information (Id or Password)", ButtonType.OK);
        // display the alert and must click ok before returning to main window
        Optional<ButtonType> result = a.showAndWait();
    }

    /*
	 * Method returns if the employee id is a integer
	 * 
	 * @pre: integer value in textfield
	 * 
	 * @post: true or false if integer number in textfield
     */
    public static boolean isNumeric(String str) {
        // try catch to check if the input is an integer
        try {
            // parse the string into an integer
            int d = Integer.parseInt(str);
        } // catches if the string is not an integer
        catch (NumberFormatException nfe) {

            return false;
        }
        // the string is an integer
        return true;
    }

}
