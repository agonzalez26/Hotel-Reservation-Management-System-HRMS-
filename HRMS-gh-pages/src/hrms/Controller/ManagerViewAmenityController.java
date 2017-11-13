/* 
 * ManagerViewAmenityController handles functionalities for ManagerAmenityView.
 */
package hrms.Controller;

import hrms.HRMS;
import hrms.Model.Amenity;
import hrms.Model.Database;
import hrms.Model.ModelControl;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class ManagerViewAmenityController implements Initializable {
	// variables
    
        Database db = new Database();
        ModelControl AmenityMC = new ModelControl(db.getConnection());
	@FXML
	private Button logOutButton;
	@FXML
	private Button roomButton;
	@FXML
	private Button reservationsButton;
	@FXML
	private Button managerButton;
	@FXML
	private Button roomPriceButton;
	@FXML
	private Button employeeButton;
	@FXML
	private Button amenityButton;
	@FXML
	private Button addButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button updateButton;
	@FXML
	private TextField amenityName;
	@FXML
	private TextField amenityPrice;
	@FXML
	private TextArea amenityDescription;
	@FXML
	private Stage stage = null;
	@FXML
	private Parent root = null;
        
        //Table Labels
        @FXML private TableView<Amenity> amenityTable;
        @FXML private TableColumn<Amenity, String> amenityNameColumn;
        @FXML private TableColumn<Amenity, String> descriptionColumn;
        @FXML private TableColumn<Amenity, Double> priceColumn;
        @FXML private TableColumn<Amenity, String> idColumn;
        

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
                AmenityMC.initialize();
                amenityNameColumn.setCellValueFactory(new PropertyValueFactory<Amenity, String>("name"));
                descriptionColumn.setCellValueFactory(new PropertyValueFactory<Amenity, String>("description"));
                priceColumn.setCellValueFactory(new PropertyValueFactory<Amenity, Double>("price"));
                idColumn.setCellValueFactory(new PropertyValueFactory<Amenity, String>("id"));
                amenityTable.setItems(getAmenity());
                
                //update the table to allow for changes to fields
                amenityTable.setEditable(true);
                amenityNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                

               
	}
        
        public ObservableList<Amenity> getAmenity(){
            ArrayList amenityList = new ArrayList(AmenityMC.getHotelAmenities());
            ObservableList<Amenity> amenity = FXCollections.observableArrayList(amenityList);
            return amenity;    
        }

	/*
	 * Function that handles all button action events
	 */
        public void changeAmenityNameEvent(CellEditEvent edittedCell){
            Amenity amenitySelected = amenityTable.getSelectionModel().getSelectedItem();
            amenitySelected.setName(edittedCell.getNewValue().toString()); 
        }
        
        public void changeAmenityDescriptionEvent(CellEditEvent edittedCell){
            Amenity amenitySelected = amenityTable.getSelectionModel().getSelectedItem();
            amenitySelected.setDescription(edittedCell.getNewValue().toString()); 
        }
        
        public void changeAmenityPriceEvent(CellEditEvent edittedCell){
            Amenity amenitySelected = amenityTable.getSelectionModel().getSelectedItem();
            amenitySelected.setPrice((double) edittedCell.getNewValue());   
        }
        
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		// if logout button selected
		if (event.getSource() == logOutButton) {
			// get reference to the button's stage
			stage = (Stage) logOutButton.getScene().getWindow();
			root = FXMLLoader.load(HRMS.class.getResource("View/HomeView.fxml"));

		}
		// if room button selected
		else if (event.getSource() == roomButton) {
			stage = (Stage) roomButton.getScene().getWindow();
			// load up OTHER FXML document
			root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeView.fxml"));
			
		} else if (event.getSource() == reservationsButton) {
			stage = (Stage) reservationsButton.getScene().getWindow();
			// load up OTHER FXML document
			root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeReservation.fxml"));

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
                        db.closeConnection();
			// load up OTHER FXML document
			root = FXMLLoader.load(HRMS.class.getResource("View/ManagerViewEmployee.fxml"));

		}
		// if amenity button selected
		else if (event.getSource() == amenityButton) {
			stage = (Stage) amenityButton.getScene().getWindow();
                        db.closeConnection();
			// load up OTHER FXML document
			root = FXMLLoader.load(HRMS.class.getResource("View/ManagerViewAmenity.fxml"));

		}
		// if add button selected
		else if (event.getSource() == addButton) {
			String newAmenityName = amenityName.getText();
			double newAmenityPrice = Double.parseDouble(amenityPrice.getText());
			String newAmenityDescription = amenityDescription.getText();
                        AmenityMC.addAmenityToHotel(newAmenityName, newAmenityDescription,newAmenityPrice);
                        System.out.println("Amenity created.");
                        db.closeConnection();
			/*
			if(name.isEmpty() || price.isEmpty() || description.isEmpty()){
				
					EmptyError();
					stage = (Stage) addButton.getScene().getWindow();
					// load up OTHER FXML document
					root = FXMLLoader.load(HRMS.class.getResource("View/ManagerViewAmenity.fxml"));	
		
			}else if(!name.isEmpty() && !price.isEmpty() && !description.isEmpty()){
				stage = (Stage) addButton.getScene().getWindow();
				// load up OTHER FXML document
				root = FXMLLoader.load(HRMS.class.getResource("View/ManagerViewAmenity.fxml"));
				System.out.println("Amenity added.");
			}
                        */
			stage = (Stage) addButton.getScene().getWindow();
                        
			// load up OTHER FXML document
			root = FXMLLoader.load(HRMS.class.getResource("View/ManagerViewAmenity.fxml"));
		}
		// if delete button selected
		else if (event.getSource() == deleteButton) {
                        ObservableList<Amenity> amenitySelected;
                        amenitySelected = amenityTable.getSelectionModel().getSelectedItems();
                        int selectedID = amenitySelected.get(0).getId();
                        AmenityMC.deleteAmenity(selectedID);
                        System.out.println("Amenity with ID" +selectedID+ "deleted.");
                        db.closeConnection();
                        
			/*
			if(name.isEmpty() || price.isEmpty() || description.isEmpty()){
				
					EmptyError();
					stage = (Stage) deleteButton.getScene().getWindow();
					// load up OTHER FXML document
					root = FXMLLoader.load(HRMS.class.getResource("View/ManagerViewAmenity.fxml"));	
		
			}else if(!name.isEmpty() && !price.isEmpty() && !description.isEmpty()){
				stage = (Stage) deleteButton.getScene().getWindow();
				// load up OTHER FXML document
				root = FXMLLoader.load(HRMS.class.getResource("View/ManagerViewAmenity.fxml"));
				System.out.println("Amenity deleted.");
			}*/

			stage = (Stage) deleteButton.getScene().getWindow();
			// load up OTHER FXML document
			root = FXMLLoader.load(HRMS.class.getResource("View/ManagerViewAmenity.fxml"));
//			stage = (Stage) deleteButton.getScene().getWindow();
//			// load up OTHER FXML document
//			root = FXMLLoader.load(HRMS.class.getResource("View/ManagerViewAmenity.fxml"));
//			System.out.println("Amenity deleted..");
		}
		// if update button selected
		else if (event.getSource() == updateButton) {
                    ObservableList<Amenity> selectedAmenity;
                    selectedAmenity = amenityTable.getSelectionModel().getSelectedItems();
                    int selectedID = selectedAmenity.get(0).getId();
                    double newEdittedPrice = selectedAmenity.get(0).getPrice();
                    String newEdittedName = selectedAmenity.get(0).getName();
                    String newEdittedDescription = selectedAmenity.get(0).getDescription();
                    AmenityMC.deleteAmenity(selectedID);
                    AmenityMC.addAmenityToHotel(newEdittedName, newEdittedDescription, newEdittedPrice);
                    System.out.println("New amenity editted");
                    db.closeConnection();
                        
                        
                        
			/*
			if(name.isEmpty() || price.isEmpty() || description.isEmpty()){
				
					EmptyError();
					stage = (Stage) updateButton.getScene().getWindow();
					// load up OTHER FXML document
					root = FXMLLoader.load(HRMS.class.getResource("View/ManagerViewAmenity.fxml"));	
		
			}else if(!name.isEmpty() && !price.isEmpty() && !description.isEmpty()){
				stage = (Stage) updateButton.getScene().getWindow();
				// load up OTHER FXML document
				root = FXMLLoader.load(HRMS.class.getResource("View/ManagerViewAmenity.fxml"));
				System.out.println("Amenity Updated.");
			}
                        */
			stage = (Stage) updateButton.getScene().getWindow();
			// load up OTHER FXML document
			root = FXMLLoader.load(HRMS.class.getResource("View/ManagerViewAmenity.fxml"));

		}
		// if manager button selected
		else if (event.getSource() == managerButton) {
			stage = (Stage) managerButton.getScene().getWindow();
			// load up OTHER FXML document
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
	
	/*
	 * Method called when the confirmation number field is empty
	 * 
	 * @pre: HomeView
	 * 
	 * @post: EmptyError Alert display, HomeView
	 */
	private void EmptyError() {
					Alert a = new Alert(Alert.AlertType.ERROR, "Missing Information", ButtonType.OK);
			Optional<ButtonType> result = a.showAndWait();
		
		

	}
}

