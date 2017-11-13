/*
 * The ManagerViewPriceController handles functionalities for ManagerViewPrice.
 */
package hrms.Controller;

import hrms.HRMS;
import hrms.Model.Database;
import hrms.Model.ModelControl;
import hrms.Model.Room;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.DoubleStringConverter;

public class ManagerViewPriceController implements Initializable {
	// variables
    
    
        Database db =new Database();
        ModelControl PriceMC = new ModelControl(db.getConnection());
        
        
       
        
	@FXML
	private Button roomPriceButton;
	@FXML
	private Button employeeButton;
	@FXML
	private Button amenityButton;
	@FXML
	private Button searchButton;
	@FXML
	private Button roomButton;
	@FXML
	private Button reservationsButton;
	@FXML
	private Button updatePriceButton;
	@FXML
	private Button managerButton;
	@FXML
	private Button logOutButton;
	@FXML
	private TextField confirmationNumberText;
	@FXML
	private Stage stage = null;
	@FXML
	private Parent root = null;

        
        @FXML private TableView<Room> roomTable;
        @FXML private TableColumn<Room, Integer> roomNumberColumn;
        @FXML private TableColumn<Room, Double> roomPriceColumn;
        @FXML private TableColumn<Room, Boolean> roomAvailability;
        
        ArrayList roomList = new ArrayList(PriceMC.getHotelRooms());
        ObservableList<Room> rooms = FXCollections.observableArrayList(roomList);
        FilteredList<Room> filteredData = new FilteredList<>(rooms, e -> true);
             

            
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
                PriceMC.initialize();
                roomNumberColumn.setCellValueFactory(new PropertyValueFactory<Room, Integer>("id"));
                roomPriceColumn.setCellValueFactory(new PropertyValueFactory<Room, Double>("roomPrice"));
                roomAvailability.setCellValueFactory(new PropertyValueFactory<Room, Boolean>("availability"));
                roomTable.setItems(getRoom());
                
                roomTable.setEditable(true);
                roomPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                roomAvailability.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
      
            
        }
        @FXML
        public ObservableList<Room> getRoom(){
            ArrayList roomList = new ArrayList(PriceMC.getHotelRooms());
            ObservableList<Room> room = FXCollections.observableArrayList(roomList);
            return room;
        }
        @FXML
        public void changeRoomPriceEvent(CellEditEvent edittedCell){
            Room roomSelected = roomTable.getSelectionModel().getSelectedItem();
            String typeofRoom = roomSelected.getDescription();
            double newPriceofRoom = (double) edittedCell.getNewValue();
            PriceMC.setRoomPrice(typeofRoom, newPriceofRoom);
            
            
          //  System.out.println(PriceMC.setRoomPrice(typeofRoom, newPriceofRoom));
          
        /*     ArrayList<Room> rooms = PriceMC.getHotelRooms();
             for(int count = 0;count < rooms.size();count++){
                 System.out.println(rooms.get(count).getHotelRoomFlag());
             }*/
            
        } 
        @FXML
        public void changeRoomAvailabilityEvent(CellEditEvent edittedCell){
            Room roomSelected = roomTable.getSelectionModel().getSelectedItem();
            
            roomSelected.setAvailability((boolean) edittedCell.getNewValue());
            
        }
        
    /*    public void searchUser(){
            confirmationNumberText.setOnKeyReleased(e ->{
                  confirmationNumberText.textProperty().addListener((observableValue,oldValue,newValue) ->{
                      filteredData.setPredicate((Predicate<? super Room>) room ->{
                          if(newValue == null || newValue.isEmpty()){
                              return true;
                          }
                          if(Double.toString(room.getRoomPrice()).contains(newValue)){

                              return true;
                          }
                          return false;
                      });
                  });
                  
               SortedList<Room> sortedData = new SortedList<>(filteredData);   
               sortedData.comparatorProperty().bind(roomTable.comparatorProperty());
               roomTable.setItems(sortedData); 
            });
        }
	/*
	 * Function that handles all button action events
	 */
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		// if logout button selected
		if (event.getSource() == logOutButton) {
			// get reference to the button's stage
			stage = (Stage) logOutButton.getScene().getWindow();
			// load up OTHER FXML document
			root = FXMLLoader.load(HRMS.class.getResource("View/HomeView.fxml"));

		}
		// if room price button selected
		else if (event.getSource() == roomPriceButton) {
			stage = (Stage) roomPriceButton.getScene().getWindow();
			// load up OTHER FXML document
			root = FXMLLoader.load(HRMS.class.getResource("View/ManagerViewPrice.fxml"));

		}
		// if employee button selected
		else if (event.getSource() == employeeButton) {
			stage = (Stage) employeeButton.getScene().getWindow();
			// load up OTHER FXML document
			root = FXMLLoader.load(HRMS.class.getResource("View/ManagerViewEmployee.fxml"));

		}
		// if amenity button selected
		else if (event.getSource() == amenityButton) {
			stage = (Stage) amenityButton.getScene().getWindow();
			// load up OTHER FXML document
			root = FXMLLoader.load(HRMS.class.getResource("View/ManagerViewAmenity.fxml"));

		}
		// if update price button selected
		else if (event.getSource() == updatePriceButton) {
			stage = (Stage) updatePriceButton.getScene().getWindow();
			System.out.println("Updated rooms price.");
			// load up OTHER FXML document
			root = FXMLLoader.load(HRMS.class.getResource("View/ManagerViewPrice.fxml"));
                    
		}
		// if search button selected
		else if (event.getSource() == searchButton) {
                    
                   
			String conNum = confirmationNumberText.getText();
                        int roomID = Integer.parseInt(conNum);
                        ObservableList<Room> searchedRoom;
                         ArrayList<Room> searchedList = new ArrayList<Room>();
                         searchedList.add(PriceMC.getHotelRoom(roomID));
                         ObservableList<Room> room = FXCollections.observableArrayList(searchedList);
                        roomTable.setItems(room);

                      
		/*	if(conNum.equals("")){
				EmptyError();
				
				stage = (Stage) searchButton.getScene().getWindow();
				root = FXMLLoader.load(HRMS.class.getResource("View/ManagerViewPrice.fxml"));
				
			}else if(!isNumeric(conNum)){
				// calls confirmation error method
				ConfirmationError();
//				System.out.println("Showing matched rooms");
				stage = (Stage) searchButton.getScene().getWindow();
				root = FXMLLoader.load(HRMS.class.getResource("View/ManagerViewPrice.fxml"));
			
			}else{
				System.out.println("Showing matched rooms");
				stage = (Stage) searchButton.getScene().getWindow();
				root = FXMLLoader.load(HRMS.class.getResource("View/ManagerViewPrice.fxml"));
			}
			*/

		}
		// if manager button selected
		else if (event.getSource() == managerButton) {
			stage = (Stage) managerButton.getScene().getWindow();

			// load up OTHER FXML document
			root = FXMLLoader.load(HRMS.class.getResource("View/ManagerView.fxml"));
		}
		// exit application
		else if(event.getSource() == roomButton){
			stage = (Stage) roomButton.getScene().getWindow();

			// load up OTHER FXML document
			root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeView.fxml"));
		}else if(event.getSource() == reservationsButton){
			stage = (Stage) reservationsButton.getScene().getWindow();

			// load up OTHER FXML document
			root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeReservation.fxml"));
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
		Alert a = new Alert(Alert.AlertType.ERROR, "Missing confirmation number", ButtonType.OK);
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
		Alert a = new Alert(Alert.AlertType.ERROR, "Incorrect Confirmation Number", ButtonType.OK);
		Optional<ButtonType> result = a.showAndWait();

	}
}
