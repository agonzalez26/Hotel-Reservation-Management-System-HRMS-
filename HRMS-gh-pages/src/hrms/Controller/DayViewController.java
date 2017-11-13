/*
 * The DayViewController will handle functionalities for the DayView.
 */
//package hrms.Controller;
//
//import hrms.Model.Reservation;
//import hrms.Model.Guest;
//import hrms.Model.Application;
//import hrms.Model.ModelControl;
//import hrms.HRMS;
//import hrms.Model.Database;
//import java.io.IOException;
//import java.net.URL;
//import java.time.LocalDate;
//import java.time.temporal.ChronoUnit;
//import java.util.Locale;
//import java.util.ResourceBundle;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.DateCell;
//import javafx.scene.control.DatePicker;
//import javafx.scene.control.TextField;
//import javafx.scene.control.Tooltip;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
//import javafx.util.Callback;
//
//
//
//
///**
// * FXML Controller class
// *
// * @author agonzalez26
// */
//public class DayViewController implements Initializable {
//    // variables
//
//    @FXML
//    private AnchorPane dayView;
//    @FXML
//    private Button logInButton;
//    @FXML
//    private Button backButton;
//    @FXML
//    private Button nextButton;
//    @FXML
//    private Button cancelButton;
//    @FXML
//    private TextField billAmountText;
//    @FXML
//    private DatePicker startDate;
//    @FXML
//    private DatePicker endDate;
//
//    private long dayCount;
//
//    @FXML
//    private Stage stage = null;
//    @FXML
//    private Parent root = null;
//    
//     Database db = new Database();
//     ModelControl mod = new ModelControl(db.getConnection());
//     Application app = new Application();
//     
//	
//
//    /*
//	 * Function will handle all button action events
//     */
//    @FXML
//    private void handleButtonAction(ActionEvent event) throws IOException {
//        // if login button selected
//        if (event.getSource() == logInButton) {
//            // get reference to the button's stage
//            stage = (Stage) logInButton.getScene().getWindow();
//            // load up OTHER FXML document
//            root = FXMLLoader.load(HRMS.class.getResource("View/EmployeeLoginView.fxml"));
//        } // if back button selected
//        else if (event.getSource() == backButton) {
//            // get reference to the button's stage
//            stage = (Stage) backButton.getScene().getWindow();
//            // load up OTHER FXML document
//            root = FXMLLoader.load(HRMS.class.getResource("View/ContactInfoView.fxml"));
//        } // if next button selected
//        else if (event.getSource() == nextButton) {
//             stage = (Stage) nextButton.getScene().getWindow();
//             root = FXMLLoader.load(HRMS.class.getResource("View/GuestView.fxml"));
//             mod.initialize();
//             Guest guest = mod.getGuest(app.getReservationNumber());
//             if(guest!=null){
//             Reservation res = mod.getReservation(app.getReservationNumber());
//             String dates = res.getReservedDates();
//             String[] date = dates.split("/");
//             app.setStartDate(date[0]);
//             app.setEndDate(date[1]);
//             
//
//             }else{
//             app.setStartDate(startDate.getValue().toString());
//             app.setEndDate(endDate.getValue().toString());
//             System.out.println(app.getStartDate());
//             System.out.println(app.getEndDate());
//             }
//          
//            
//        } // if cancel button selected
//        else if (event.getSource() == cancelButton) {
//            stage = (Stage) cancelButton.getScene().getWindow();
//            root = FXMLLoader.load(HRMS.class.getResource("View/HomeView.fxml"));
//        } // exit application
//        else {
//            System.exit(0);
//        }
//        // create a new scene with root and set the stage
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    /**
//     * Initializes the controller class.
//     */
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        /*
//		 * Initialize the calendars to the correct dates.
//         */
//        calendarsInitialize();
//        billAmountText.setText("$"+app.getBill());
//        endDate.valueProperty().addListener((ov, oldValue, newValue) -> {
//            long date1 = startDate.getValue().toEpochDay();
//            long date2 = endDate.getValue().toEpochDay();
//            int days = (int) Math.abs(date1-date2);
//            app.setDayCount(days);
//            billAmountText.setText("$"+app.getBill());
//            
//            
//        });
//
//    }
//
//    private void calendarsInitialize() {
//
//        Locale.setDefault(Locale.US);
//        // this is setting the first calendar to the current date
//        startDate.setValue(LocalDate.now());
//
//        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
//            @Override
//            public DateCell call(final DatePicker datePicker) {
//                return new DateCell() {
//                    @Override
//                    public void updateItem(LocalDate item, boolean empty) {
//                        super.updateItem(item, empty);
//
//                        if (item.isBefore(LocalDate.now()) || item.isAfter(LocalDate.now())) {
//
//                            setDisable(true);
//                            setStyle("-fx-background-color: #ffc0cb;");
//                        }
//                    }
//                };
//            }
//        };
//        startDate.setDayCellFactory(dayCellFactory);
//        startDate.setEditable(false);
//        
//        final Callback<DatePicker, DateCell> dayCellFactory2 = new Callback<DatePicker, DateCell>() {
//            @Override
//            public DateCell call(final DatePicker datePicker) {
//                return new DateCell() {
//                    @Override
//                    public void updateItem(LocalDate item, boolean empty) {
//                        super.updateItem(item, empty);
//                        // if the dayCell is before the current day plus one
//                        if (item.isBefore(startDate.getValue().plusDays(1))) {
//                            // disable the cell and style
//                            setDisable(true);
//                            setStyle("-fx-background-color: #ffc0cb;");
//                        }
//
//                        dayCount = ChronoUnit.DAYS.between(startDate.getValue(), item);
//                        setTooltip(new Tooltip("You're about to stay for " + dayCount + " days"));
//
//                    }
//                };
//            }
//        };
//
//        endDate.setDayCellFactory(dayCellFactory2);
//        endDate.setValue(startDate.getValue().plusDays(1));
//
//    }
//
//}
/*
 * The DayViewController will handle functionalities for the DayView.
 */
package hrms.Controller;

import hrms.Model.Reservation;
import hrms.Model.Guest;
import hrms.Model.Application;
import hrms.Model.ModelControl;
import hrms.HRMS;
import hrms.Model.Database;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author agonzalez26
 */
public class DayViewController implements Initializable {
    // variables

    @FXML
    private AnchorPane dayView;
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
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;

    private long dayCount;

    @FXML
    private Stage stage = null;
    @FXML
    private Parent root = null;
    private final String datePattern = "yyyy-MM-dd";

    Database db = new Database();
    ModelControl mod = new ModelControl(db.getConnection());
    Application app = new Application();
//    String assignedDate = app.getEndDate().substring(0, 10);

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
            root = FXMLLoader.load(HRMS.class.getResource("View/ContactInfoView.fxml"));
        } // if next button selected
        else if (event.getSource() == nextButton) {
            stage = (Stage) nextButton.getScene().getWindow();
            root = FXMLLoader.load(HRMS.class.getResource("View/GuestView.fxml"));
            mod.initialize();
            Guest guest = mod.getGuest(app.getReservationNumber());
            if (guest != null) {
                Reservation res = mod.getReservation(app.getReservationNumber());
                String dates = res.getReservedDates();
                String[] date = dates.split("/");
                app.setStartDate(date[0]);
                app.setEndDate(endDate.getValue().toString());

            } else {
                app.setStartDate(startDate.getValue().toString());
                app.setEndDate(endDate.getValue().toString());
                System.out.println(app.getStartDate());
                System.out.println(app.getEndDate());
            }

        } // if cancel button selected
        else if (event.getSource() == cancelButton) {
            stage = (Stage) cancelButton.getScene().getWindow();
            root = FXMLLoader.load(HRMS.class.getResource("View/HomeView.fxml"));
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
        mod.initialize();
        /*
		 * Initialize the calendars to the correct dates.
         */
        Guest guest = mod.getGuest(app.getReservationNumber());
        System.out.println(app.getEndDate());
        billAmountText.setText("$"+app.getBill());

//        if(guest!=null){
//        try {
//        endDate.setValue(LOCAL_DATE(assignedDate));
//        System.out.println(endDate.getValue());
//        } catch (NullPointerException e) {
//        }
//        }
        calendarsInitialize();

        endDate.valueProperty().addListener((ov, oldValue, newValue) -> {
            long date1 = startDate.getValue().toEpochDay();
            long date2 = endDate.getValue().toEpochDay();
            int days = (int) Math.abs(date1 - date2);
//            System.out.println(endDate.getValue());
            app.setDayCount(days);
            billAmountText.setText("$"+app.getBill());
//            app.setE

        });

        db.closeConnection();
    }

    private void calendarsInitialize() {

        Locale.setDefault(Locale.US);
        StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern(datePattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateformatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateformatter);
                } else {
                    return null;
                }
            }

        };

        startDate.setConverter(converter);
        endDate.setConverter(converter);
        // this is setting the first calendar to the current date
        startDate.setValue(LocalDate.now());

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isBefore(LocalDate.now()) || item.isAfter(LocalDate.now())) {

                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        startDate.setDayCellFactory(dayCellFactory);
        startDate.setEditable(false);

        final Callback<DatePicker, DateCell> dayCellFactory2 = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        // if the dayCell is before the current day plus one
                        if (item.isBefore(startDate.getValue().plusDays(1))) {
                            // disable the cell and style
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }

                        dayCount = ChronoUnit.DAYS.between(startDate.getValue(), item);
                        setTooltip(new Tooltip("You're about to stay for " + dayCount + " days"));

                    }
                };
            }
        };
            
        Guest guest = mod.getGuest(app.getReservationNumber());
        System.out.println(guest);
            if(guest!=null){
            try {
                endDate.setValue(LOCAL_DATE(app.getEndDate().substring(0,10)));
                System.out.println(endDate.getValue());

            } catch (NullPointerException e) {
            }
            endDate.setDayCellFactory(dayCellFactory2);
        }else{
                  
            endDate.setValue(startDate.getValue().plusDays(1));
            endDate.setDayCellFactory(dayCellFactory2);
        
            }

//        Guest guest = mod.getGuest(app.getReservationNumber());
//        if(guest==null){
//        endDate.setValue(startDate.getValue().plusDays(1));
//        }
    }

    public static final LocalDate LOCAL_DATE(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

}