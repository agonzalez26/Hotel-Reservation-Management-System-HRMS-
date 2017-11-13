package hrms;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import hrms.Model.Database;
import hrms.Model.Guest;
import hrms.Model.Room;
import hrms.Model.Reservation;
import hrms.Model.Amenity;
import hrms.Model.ModelControl;

import java.util.Random;

public class DBTest {
	//static Database db = new Database();

	static ResultSet rs;
	static ArrayList<Guest> guests = new ArrayList<Guest>();
	static ArrayList<Room> rooms = new ArrayList<Room>();
	static ArrayList<Amenity> amenities = new ArrayList<Amenity>();

	public static void main(String[] args) {
		Database db = new Database();
		ModelControl mod = new ModelControl(db.getConnection());
		mod.initialize();
                //every time you want to create a mod object, must do these 3 steps
                
//               boolean val= mod.guestCheckIn(936);
//               System.out.println(val);
//               

    Guest newguest = new Guest(db.getConnection(), "Bob", "Marley", "123 Beautiful Lane", "kingofreggae@peace.com", "10000000000", 16);
               mod.addGuest(newguest, 102,  "2017-11-12/2017-11-13 ");
               
               mod.setAmountOfGuestsToRoom(2, 102);
               
               db.closeConnection();
	}
}
