/*
 * The EmployeeViewController handles functionalities of the EmployeeView
 */
package hrms.Controller;

import hrms.HRMS;
import hrms.Model.Application;
import hrms.Model.Database;
import hrms.Model.ModelControl;
import hrms.Model.Room;
import java.io.IOException;
import java.net.URL;
import static java.nio.file.Files.list;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Collections.list;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author agonzalez26
 */
public class EmployeeViewController implements Initializable {
	Database db = new Database();
        ModelControl ViewMC = new ModelControl(db.getConnection());
        
	
	@FXML
	private Button logOutButton;
	@FXML
	private Button reservationButton;
	@FXML
	private Button managerButton;
	@FXML
	private Stage stage = null;
	@FXML
	private Parent root = null;
        
        @FXML private TableView<Room> roomTableInfo;
        @FXML private TableColumn<Room, Integer> roomNumberIDColumn;
        @FXML private TableColumn<Room, String> descriptionColumn;

        @FXML private TableColumn<Room, Boolean> availabilityColumn;
        @FXML private TableColumn<Room, Double> roomPriceColumn;
        @FXML private TableColumn<Room, Integer> amenityCountColumn;
        @FXML private TableColumn<Room, Integer> totalPriceColumn;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
            ViewMC.initialize();

            roomNumberIDColumn.setCellValueFactory(new PropertyValueFactory<Room, Integer>("id"));
            availabilityColumn.setCellValueFactory(new PropertyValueFactory<Room, Boolean>("availability"));
            roomPriceColumn.setCellValueFactory(new PropertyValueFactory<Room, Double>("roomPrice"));
            amenityCountColumn.setCellValueFactory(new PropertyValueFactory<Room, Integer>("amountOfAmenities"));
            totalPriceColumn.setCellValueFactory(new PropertyValueFactory<Room, Integer>("totalPrice"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("Description"));

            roomTableInfo.setItems(getRoomInfo());
            db.closeConnection();
          /*  ArrayList<Room> roomInfoViewTest = new ArrayList(ViewMC.getHotelRooms());
            for(Room r: roomInfoViewTest){
                System.out.println(r.getRoomDescription());
            }*/
	}
        
        @FXML
        public ObservableList<Room> getRoomInfo(){
            ArrayList<Room> roomListView = new ArrayList<Room>(ViewMC.getHotelRooms());
            ObservableList<Room> roomInfoView = FXCollections.observableArrayList(roomListView);
            return roomInfoView;
        }       

	/*
	 * Function that handles all button action events
	 */
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		// if log out button selected
		if (event.getSource() == logOutButton) {
			// get reference to the button's stage
			stage = (Stage) logOutButton.getScene().getWindow();
			// load up OTHER FXML document
                        db.closeConnection();

			root = FXMLLoader.load(HRMS.class.getResource("View/HomeView.fxml"));

		}
		// if reservation button selected
		else if (event.getSource() == reservationButton) {
			stage = (Stage) reservationButton.getScene().getWindow();
			// load up OTHER FXML document
                        db.closeConnection();
			root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeReservation.fxml"));

		}
		// if manager button selected
		else if (event.getSource() == managerButton) {
			stage = (Stage) managerButton.getScene().getWindow();
			// load up OTHER FXML document
                        db.closeConnection();
			root = FXMLLoader.load(HRMS.class.getResource("View/ManagerView.fxml"));
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

}


///*
// * The EmployeeViewController handles functionalities of the EmployeeView
// */
//package hrms.Controller;
//
//import hrms.HRMS;
//import java.io.IOException;
//import java.net.URL;
//import java.util.ResourceBundle;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
//
///**
// * FXML Controller class
// *
// * @author agonzalez26
// */
//public class EmployeeViewController implements Initializable {
//	// variables
//	@FXML
//	private AnchorPane employeeView;
//	@FXML
//	private Button logOutButton;
//	@FXML
//	private Button reservationButton;
//	@FXML
//	private Button managerButton;
//	@FXML
//	private Stage stage = null;
//	@FXML
//	private Parent root = null;
//
//        String id;
//	/**
//	 * Initializes the controller class.
//	 */
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
//		// if log out button selected
//		if (event.getSource() == logOutButton) {
//			// get reference to the button's stage
//			stage = (Stage) logOutButton.getScene().getWindow();
//			// load up OTHER FXML document
//			root = FXMLLoader.load(HRMS.class.getResource("View/HomeView.fxml"));
//
//		}
//		// if reservation button selected
//		else if (event.getSource() == reservationButton) {
//			stage = (Stage) reservationButton.getScene().getWindow();
//			// load up OTHER FXML document
//			root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeReservation.fxml"));
//
//		}
//		// if manager button selected
//		else if (event.getSource() == managerButton) {
//			stage = (Stage) managerButton.getScene().getWindow();
//			// load up OTHER FXML document
//			root = FXMLLoader.load(HRMS.class.getResource("View/ManagerView.fxml"));
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
//	}
//        
//        public void setEmployee(String id){
//            this.id = id;
//            if(id.equals("0000")){
//                managerButton.setDisable(true);
//            }
//        }
//
//}
