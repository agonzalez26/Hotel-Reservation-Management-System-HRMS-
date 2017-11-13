package hrms;

/*
 * The Hotel Reservation Management System (HRMS) is an application for simple Check in and Booking Reservations
 * at a hotel as well as employee functionalities to manage the hotel responsibilities.
 * */

 /*Alma, Vib, Chris, Ejiro, Asad*/
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author agonzalez26
 */

/*
 * Main file that will run the HRMS application
 */
public class HRMS extends Application {

    /*
	 * Variables for the screen and the layout of the application
     */
    private Stage primarystage;
    private AnchorPane rootLayout;
    
    @Override
    public void start(Stage stage) throws Exception {

        /*
		 * Loads the HRMS application
         */
        Parent root = FXMLLoader.load(getClass().getResource("View/HomeView.fxml"));      
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Hotel Reservation Management System");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
