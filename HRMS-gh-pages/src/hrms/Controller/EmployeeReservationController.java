/*
 * EmployeeReservationController handles functionalities of the EmployeeReservationView.
 */
package hrms.Controller;

import hrms.HRMS;
import hrms.Model.Application;
import hrms.Model.Database;
import hrms.Model.ModelControl;
import hrms.Model.Reservation;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EmployeeReservationController implements Initializable {
	// variables
    
        Database db = new Database();
        ModelControl ReservationMC = new ModelControl(db.getConnection());
        
	@FXML
	private Button searchButton;
	@FXML
	private Button managerButton;
	@FXML
	private Button roomButton;
	@FXML
	private Button updateButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button logOutButton;
	@FXML
	private TextField reservationNumberText;
	@FXML
	private Stage stage = null;
	@FXML
	private Parent root = null;

      @FXML private TableView<Reservation> reservationTable;
      @FXML private TableColumn<Reservation, Integer> idColumn;
      @FXML private TableColumn<Reservation, Double> totalPriceColumn;
      @FXML private TableColumn<Reservation, String> datesColumn;
      
        
        
	@Override
	public void initialize(URL url, ResourceBundle rb) {
            
            ReservationMC.initialize();
            idColumn.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("id"));
            totalPriceColumn.setCellValueFactory(new PropertyValueFactory<Reservation, Double>("FinalPrice"));
            datesColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("reservedDates"));
            reservationTable.setItems(getReservation());
            
            
	}
        
        
        public ObservableList<Reservation> getReservation(){
            ArrayList reservationList = new ArrayList(ReservationMC.getAllReservations());
            ObservableList<Reservation> reservation = FXCollections.observableArrayList(reservationList);
            return reservation;
        }

	/*
	 * Function that handles all button action events
	 */
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		// if room button selected
		if (event.getSource() == roomButton) {
			// get reference to the button's stage
			stage = (Stage) roomButton.getScene().getWindow();
			// load up OTHER FXML document
			root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeView.fxml"));

		}
		// if update button selected
		else if (event.getSource() == updateButton) {
			System.out.println("Updated Reservation");
			stage = (Stage) roomButton.getScene().getWindow();
			root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeReservation.fxml"));

		}
		// if delete button selected
		else if (event.getSource() == deleteButton) {
			System.out.println("Deleted Reservation");
			stage = (Stage) roomButton.getScene().getWindow();
			root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeReservation.fxml"));

		}
		// if search button selected
		else if (event.getSource() == searchButton) {
			String conNum = reservationNumberText.getText();
			if(conNum.equals("")){
				EmptyError();
				
				stage = (Stage) roomButton.getScene().getWindow();
				root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeReservation.fxml"));
				
			}else if(!isNumeric(conNum)){
				
				ConfirmationError();
				stage = (Stage) roomButton.getScene().getWindow();
				root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeReservation.fxml"));
			
			}else{
				System.out.println("Showing matched reservations");
				stage = (Stage) roomButton.getScene().getWindow();
				root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeReservation.fxml"));
				
			}
		}
		// if manager button selected
		else if (event.getSource() == managerButton) {
			stage = (Stage) managerButton.getScene().getWindow();
			root = FXMLLoader.load(HRMS.class.getResource("View/ManagerView.fxml"));

		}
		// if logout button selected
		else if (event.getSource() == logOutButton) {
			stage = (Stage) roomButton.getScene().getWindow();
			root = FXMLLoader.load(HRMS.class.getResource("View/HomeView.fxml"));

		}
		// exit application
		else {
			System.exit(0);
		}

		// create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
	/*
	 * Method called when textfield, confirmation number is empty
	 * 
	 * @pre: database is accessible, EmployeeReservationView
	 * 
	 * @post: filled textfield, EmployeeReservationView
	 */
	private void EmptyError() {
		// creates alert that there is missing information in the textfield
		Alert a = new Alert(Alert.AlertType.ERROR, "Missing reservation number", ButtonType.OK);
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
		}
		// catches if the string is not an integer
		catch (NumberFormatException nfe) {

			return false;
		}
		// the string is an integer
		return true;
	}
	
	/*
	 * Method called when the confirmation number does not exist in the database
	 * 
	 * @pre: HomeView
	 * 
	 * @post: ConfirmationError Alert display, HomeView
	 */
	private void ConfirmationError() {
		Alert a = new Alert(Alert.AlertType.ERROR, "Incorrect Reservation Number", ButtonType.OK);
		Optional<ButtonType> result = a.showAndWait();

	}
}


///*
// * EmployeeReservationController handles functionalities of the EmployeeReservationView.
// */
//package hrms.Controller;
//
//import hrms.HRMS;
//import java.io.IOException;
//import java.net.URL;
//import java.util.Optional;
//import java.util.ResourceBundle;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.ButtonType;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
//
//public class EmployeeReservationController implements Initializable {
//	// variables
//	@FXML
//	private Button searchButton;
//	@FXML
//	private Button managerButton;
//	@FXML
//	private Button roomButton;
//	@FXML
//	private Button updateButton;
//	@FXML
//	private Button deleteButton;
//	@FXML
//	private Button logOutButton;
//	@FXML
//	private TextField confirmationNumberText;
//	@FXML
//	private Stage stage = null;
//	@FXML
//	private Parent root = null;
//
//	@Override
//	public void initialize(URL url, ResourceBundle rb) {
//
//	}
//
//	/*
//	 * Function that handles all button action events
//	 */
//	@FXML
//	private void handleButtonAction(ActionEvent event) throws IOException {
//		// if room button selected
//		if (event.getSource() == roomButton) {
//			// get reference to the button's stage
//			stage = (Stage) roomButton.getScene().getWindow();
//			// load up OTHER FXML document
//			root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeView.fxml"));
//
//		}
//		// if update button selected
//		else if (event.getSource() == updateButton) {
//			System.out.println("Updated Reservation");
//			stage = (Stage) roomButton.getScene().getWindow();
//			root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeReservation.fxml"));
//
//		}
//		// if delete button selected
//		else if (event.getSource() == deleteButton) {
//			System.out.println("Deleted Reservation");
//			stage = (Stage) roomButton.getScene().getWindow();
//			root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeReservation.fxml"));
//
//		}
//		// if search button selected
//		else if (event.getSource() == searchButton) {
//			String conNum = confirmationNumberText.getText();
//			if(conNum.equals("")){
//				EmptyError();
//				
//				stage = (Stage) roomButton.getScene().getWindow();
//				root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeReservation.fxml"));
//				
//			}else if(!isNumeric(conNum)){
//				
//				ConfirmationError();
//				stage = (Stage) roomButton.getScene().getWindow();
//				root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeReservation.fxml"));
//			
//			}else{
//				System.out.println("Showing matched reservations");
//				stage = (Stage) roomButton.getScene().getWindow();
//				root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeReservation.fxml"));
//				
//			}
//		}
//		// if manager button selected
//		else if (event.getSource() == managerButton) {
//			stage = (Stage) managerButton.getScene().getWindow();
//			root = FXMLLoader.load(HRMS.class.getResource("View/ManagerView.fxml"));
//
//		}
//		// if logout button selected
//		else if (event.getSource() == logOutButton) {
//			stage = (Stage) roomButton.getScene().getWindow();
//			root = FXMLLoader.load(HRMS.class.getResource("View/HomeView.fxml"));
//
//		}
//		// exit application
//		else {
//			System.exit(0);
//		}
//
//		// create a new scene with root and set the stage
//		Scene scene = new Scene(root);
//		stage.setScene(scene);
//		stage.show();
//
//	}
//	/*
//	 * Method called when textfield, confirmation number is empty
//	 * 
//	 * @pre: database is accessible, EmployeeReservationView
//	 * 
//	 * @post: filled textfield, EmployeeReservationView
//	 */
//	private void EmptyError() {
//		// creates alert that there is missing information in the textfield
//		Alert a = new Alert(Alert.AlertType.ERROR, "Missing confirmation number", ButtonType.OK);
//		// display the alert and must click ok before returning to main window
//		Optional<ButtonType> result = a.showAndWait();
//	}
//	
//	/*
//	 * Method returns if the employee id is a integer
//	 * 
//	 * @pre: integer value in textfield
//	 * 
//	 * @post: true or false if integer number in textfield
//	 */
//	public static boolean isNumeric(String str) {
//		// try catch to check if the input is an integer
//		try {
//			// parse the string into an integer
//			int d = Integer.parseInt(str);
//		}
//		// catches if the string is not an integer
//		catch (NumberFormatException nfe) {
//
//			return false;
//		}
//		// the string is an integer
//		return true;
//	}
//	
//	/*
//	 * Method called when the confirmation number does not exist in the database
//	 * 
//	 * @pre: HomeView
//	 * 
//	 * @post: ConfirmationError Alert display, HomeView
//	 */
//	private void ConfirmationError() {
//		Alert a = new Alert(Alert.AlertType.ERROR, "Incorrect Confirmation Number", ButtonType.OK);
//		Optional<ButtonType> result = a.showAndWait();
//
//	}
//}
