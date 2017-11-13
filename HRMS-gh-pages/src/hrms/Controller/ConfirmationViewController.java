/*
 * The ConfirmationViewController will handle functionalities of the ConfirmationView.
 */
package hrms.Controller;

import java.util.concurrent.TimeUnit;
import hrms.Model.Application;
import hrms.HRMS;
import hrms.Model.Database;
import hrms.Model.Guest;
import hrms.Model.ModelControl;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author agonzalez26
 */
public class ConfirmationViewController implements Initializable {
    // variables

    @FXML
    private AnchorPane confirmationView;
    @FXML
    private Button logInButton;
    @FXML
    private Button backButton;
    @FXML
    private Button cancelButton;
    private Stage stage = null;
    private Parent root = null;
    @FXML
    private Button confirmButton;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField phonenumber;
    @FXML
    private TextField emailaddress;
    @FXML
    private TextField address;

    Application app = new Application();
    @FXML
    private TextField reservationNumber;
    @FXML
    private TextField checkIn;
    @FXML
    private TextField checkOut;
    @FXML
    private TextField dayCount;
    @FXML
    private TextField guestCount;
    @FXML
    private TextField roomList;
    @FXML
    private TextField price;
    @FXML
    private TextField totPrice;
    @FXML
    private TextField amenityCount;
    private Database db = new Database();
    private ModelControl mc = new ModelControl(db.getConnection());

    /*
	 * Function will handle all button action events
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
            root = FXMLLoader.load(HRMS.class.getResource("View/AmenityView.fxml"));
        } // if next button selected
        else if (event.getSource() == confirmButton) {
            stage = (Stage) confirmButton.getScene().getWindow();
            root = FXMLLoader.load(HRMS.class.getResource("View/HomeView.fxml"));
            Guest guest = mc.getGuest(app.getReservationNumber());
            //System.out.println("Guest: "+guest);

            if (guest != null) {
                String rooms = app.getRoomString();
                String[] roomArray = rooms.split("\\s+");

                for (int i = 0; i < roomArray.length; i++) {
                        
                    System.out.println("date"+app.getStartDate() + "/" + app.getEndDate()+" ");
                    mc.addRoomToReservation(app.getReservationNumber(), Integer.parseInt(roomArray[i]), app.getStartDate() + "/" + app.getEndDate()+" ");
                    mc.setAmountOfGuestsToRoom(Integer.parseInt(app.getGuestCount()), Integer.parseInt(roomArray[i]));
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                        System.out.println("Error test: " + e.getMessage());
                    }

                }
                
                mc.guestCheckIn(app.getReservationNumber());

                HashMap<String, ArrayList<String>> map = app.getRoomAmens();

                for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
                    String key = entry.getKey();
                    ArrayList<String> value = entry.getValue();
                    for (String aString : value) {
                          System.out.println(key +" "+ aString);
                       mc.addAmenityToRoom(Integer.parseInt(key), Integer.parseInt(aString));
                    }
                }
            } else {

                Guest newguest = new Guest(db.getConnection(), app.getFirstName(), app.getLastName(), app.getAddress(), app.getEmailAddress(), app.getPhoneNumber(), 16);
                String rooms = app.getRoomString();
                String[] roomArray = rooms.split("\\s+");
                System.out.println("room 0" + roomArray[0]);
                mc.addGuest(newguest, Integer.parseInt(roomArray[0]), app.getStartDate() + "/" + app.getEndDate() + " ");  //originally have no rooms
                int resnum = newguest.getId();
                app.setReservationNumber(resnum);

                System.out.println(rooms);
                
                for (int i = 1; i <= roomArray.length; i++) {
                        
                    System.out.println("date"+app.getStartDate() + "/" + app.getEndDate()+" ");
                    System.out.println(Integer.parseInt(roomArray[i]));
                    System.out.println(app.getReservationNumber());
                    mc.addRoomToReservation(app.getReservationNumber(), Integer.parseInt(roomArray[i]), app.getStartDate() + "/" + app.getEndDate()+" ");
                   
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                        System.out.println("Error test: " + e.getMessage());
                    }

                }
                
                mc.guestCheckIn(app.getReservationNumber());

                HashMap<String, ArrayList<String>> map = app.getRoomAmens();

                for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
                    String key = entry.getKey();
                    ArrayList<String> value = entry.getValue();
                    for (String aString : value) {
                          System.out.println(key +" "+ aString);
                       mc.addAmenityToRoom(Integer.parseInt(key), Integer.parseInt(aString));
                    }
                }
             
                

            }
        } // if cancel button selected
        else if (event.getSource() == cancelButton) {
            stage = (Stage) cancelButton.getScene().getWindow();
            root = FXMLLoader.load(HRMS.class.getResource("View/HomeView.fxml"));
        } // exit application
        else {
            System.exit(0);
        }
        db.closeConnection();
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

        mc.initialize();
        firstname.setText(app.getFirstName());
        lastname.setText(app.getLastName());
        phonenumber.setText(app.getPhoneNumber());
        emailaddress.setText(app.getEmailAddress());
        address.setText(app.getAddress());
        reservationNumber.setText("" + app.getReservationNumber());
        checkIn.setText(app.getStartDate());
        checkOut.setText(app.getEndDate());
        roomList.setText(app.getRoomString());
        amenityCount.setText(app.getAmenityCount() + "");
        dayCount.setText(app.getDayCount() + "");
        guestCount.setText(app.getRoomCount());
        price.setText("$" + app.getBill());
        DecimalFormat format = new DecimalFormat("##.00");
        double finPrice = app.getBill() * 1.06;
        totPrice.setText("$" + format.format(finPrice));
        if (app.getReservationNumber() == 0) {//generate new if bookin
            int reserNum = db.generateReservationId();
            reservationNumber.setText(reserNum + "");
        }

    }

}
