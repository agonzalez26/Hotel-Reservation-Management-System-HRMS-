/*1. Always run initialize method on startup (after creating object of this class)
 *2. All methods handle their own data cleanup, updates, and cross checks with the database
 *automatically so dont worry about that.  Just use the methods the way they are intended and you
 *dont have to do any further thinking as far as they are concerned.*/
package hrms.Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
//import com.mchange.v2.c3p0.*;

public class ModelControl {
	ArrayList<Guest> allGuests = new ArrayList<Guest>();			//all guests with reservations, each guest object contains reservation, room, and amenity info(objects)
	ArrayList<Room> hotelRooms = new ArrayList<Room>();			//standing rooms in hotel
	ArrayList<Amenity> hotelAmenities = new ArrayList<Amenity>();		//standing amenities in hotel
	ArrayList<Employee> employees = new ArrayList<Employee>();
	ArrayList<ProcessedPayment> processedPaymentsList = new ArrayList<ProcessedPayment>();
	Database db;
	Connection c;
	
	public ModelControl(Connection con) {
		c = con;
		db = new Database(c);
	}
	public void initialize() {
		ResultSet rs;			//load in values to models on startup
		try {			//initialization, initializing when there are null values will cause errors
			rs = db.initializeGuests();
			while(rs.next()) {
				allGuests.add(new Guest(c, rs.getInt("id"), rs.getString("firstName"), rs.getString("lastName"),
						rs.getString("address"), rs.getString("email"), rs.getString("phoneNumber"),
						rs.getInt("age"),rs.getInt("amountOfReservations"), rs.getString("reservationIds")));
			}
		}catch(Exception e) {
			System.out.println("\nError Code: Summit\n" + e.getMessage());
		}
		try {
			rs = db.initializeRooms();
			while(rs.next()) {
				hotelRooms.add(new Room(c, rs.getInt("id"), rs.getDouble("roomPrice"), rs.getDouble("totalAmenityPrice"),
						rs.getDouble("totalPrice"), rs.getString("description"), rs.getBoolean("availability"),
						rs.getString("amenityIDs"), rs.getInt("amountOfAmenities"), rs.getInt("amountOfGuests"),
						rs.getInt("amountOfDays"), rs.getString("currentReservedDatePeriod"), rs.getString("passcode"), true));
			}
		}catch(Exception e) {
			System.out.println("\nError Code: Solar\n" + e.getMessage());
		}
		try {
			rs = db.initializeAmenities();
			while(rs.next()) {
				hotelAmenities.add(new Amenity(c, rs.getInt("id"), rs.getString("name"), rs.getString("description"),
						rs.getDouble("price")));
			}
		}catch(Exception e) {
			System.out.println("\nError Code: Rigid\n" + e.getMessage());
		}
		try {
			rs = db.initializeEmployees();
			while(rs.next()) {
				employees.add(new Employee(c, rs.getInt("id"), rs.getString("firstName"), rs.getString("lastName"),
						rs.getString("password"), rs.getBoolean("managerFlag")));
			}
		}catch(Exception e) {
			System.out.println("\nError Code: Pop\n" + e.getMessage());
		}
		try {
			rs = db.initializeProcessedPayments();
			while(rs.next()) {
				processedPaymentsList.add(new ProcessedPayment(rs.getInt("id"), rs.getString("guestFirstName"),
						rs.getString("guestLastName"), rs.getString("reservedDatePeriod"), rs.getDouble("totalPayment")));
			}
		}catch(Exception e) {
			System.out.println("\nError Code: Cotton\n" + e.getMessage());
		}
	}
	/*add guest and their reservation, never add guest unless
	 *reservation is a definite, reservedDate format: start (yyyy-mm-dd/yyyy-mm-dd) end */
	public void addGuest(Guest g, int roomId, String reserveDate) {
		if(db.checkAvailability(roomId, reserveDate)) {
			Reservation res;
			for(int count = 0;count<hotelRooms.size();count++) {
				if ((roomId == hotelRooms.get(count).getId())) {
					Room rom = hotelRooms.get(count);
					rom.setHotelRoomFlag(false);
					rom.clearRoom();
					rom.setAvailability(false);
					rom.setCurrentReservedDatePeriod(reserveDate);
					res = new Reservation(c, reserveDate,rom);
					db.addGuest(g);
					g.addReservation(res);
					allGuests.add(g);
					db.updateGuest(g);
					db.addReservation(res);
					break;
				}
			}
		}else {
			System.out.println("Error mc1; room booked");
		}
	}
	/*add extra reservation to guest*/
	public void addReservationToGuest(int guestId, int roomId, String reserveDate) {
		if(db.checkAvailability(roomId, reserveDate)) {
			Room r = null;
			for(int count = 0;count<hotelRooms.size();count++) {
				if(hotelRooms.get(count).getId() == roomId) {
					r = hotelRooms.get(count);
					r.setHotelRoomFlag(false);
					r.setAvailability(false);
					r.setCurrentReservedDatePeriod(reserveDate);
					break;
				}
			}
			if(r==null) {
				System.out.println("Error finding room: mc");
			}
			else {
				Reservation res = new Reservation(c, reserveDate, r);
				for(int count = 0;count<allGuests.size();count++) {
					if(allGuests.get(count).getId() == guestId) {
						Guest g = allGuests.get(count);
						g.addReservation(res);
						db.addReservation(res);
						allGuests.set(count, g);
						break;
					}
				}
			}
		}else {
			System.out.println("Error mc2: room booked");
		}
	}
	/*add extra room to reservation*/
	public void addRoomToReservation(int resId, int roomId, String reserveDate) {
		if(db.checkAvailability(roomId, reserveDate)) {
			Room rom = null;
			for(int count = 0;count<hotelRooms.size();count++) {
				if(hotelRooms.get(count).getId() == roomId) {
					rom = hotelRooms.get(count);
					rom.setHotelRoomFlag(false);
				}
			}
			if(rom == null) {
				System.out.println("Room not found: mc");
			}
			else {
				for(int x=0;x<allGuests.size();x++) {
					Guest g = allGuests.get(x);
					ArrayList<Reservation> reservations = g.getReservationList();
					for(int y=0;y<reservations.size();y++) {
						if(reservations.get(y).getId() == resId) {
							rom.setAvailability(false);
							rom.setCurrentReservedDatePeriod(reserveDate);
							g.addRoom(resId, rom, reserveDate);
							allGuests.set(x, g);
							break;
						}
					}
				}
			}
		}else {
			System.out.println("Error mc3: room booked");
		}
	}
	/*add amenity to room that is occupied
	 * pre: room must be occupied, guest must be checked in*/
	// Here be dragons. Thou art forewarned
	public void addAmenityToRoom(int roomId, int amenId) {
		Room rom = null;
		Amenity am = null;
		boolean test = false;
		for(int count=0;count<hotelAmenities.size();count++) {
			if(hotelAmenities.get(count).getId() == amenId) {
				am = hotelAmenities.get(count);
				break;
			}
		}
		for(int count = 0;count<hotelRooms.size();count++) {
			if(hotelRooms.get(count).getId() == roomId) {
				if(hotelRooms.get(count).getAvailability()) {
					System.out.println("Room Unoccupied: mc");
					break;
				}
				else {
					rom = hotelRooms.get(count);
					//rom.addAmenity(am);			//somehow adds amenity to room object assigned to guest as well????????!!!!
					hotelRooms.set(count, rom);		//idk bro
					break;
				}
			}
		}
		if(rom == null || am == null) {
			System.out.println("Error mc4: amenity/room");
		}
		else {
			for(int count=0;count<allGuests.size();count++) {
				Guest g = allGuests.get(count);
				ArrayList<Reservation> reservations = g.getReservationList();
				for(int x=0;x<reservations.size();x++) {
					Reservation r = reservations.get(x);
					ArrayList<Room> rooms = r.getRoomList();
					for(int y=0;y<rooms.size();y++) {
						if(roomId == rooms.get(y).getId()) {
							g.addAmenity(r.getId(), roomId, am);//
							allGuests.set(count, g);
							test = true;
							break;
						}
					}
					if(test == true) {
						break;
					}
				}
				if(test == true) {
					break;
				}
			}
		}
		if(test == false) {
			System.out.println("Error mc5: Star");
		}
	}
	/*add new amenity to hotel choices*/
	public void addAmenityToHotel(String name, String description, double price) {
		Amenity a = new Amenity(c, name, description, price);
		hotelAmenities.add(a);
		db.addAmenity(a);
	}
	/*add new employee to hotel*/
	public void addEmployee(String firstName, String lastName, String password, boolean managerFlag) {
		Employee e = new Employee(c, firstName, lastName, password, managerFlag);
		employees.add(e);
		db.addEmployee(e);
	}
	/*set amount of guests to room
	 *should only be used after checkin method is used*/
	public void setAmountOfGuestsToRoom(int num, int roomId) {
		for(int count = 0;count<hotelRooms.size();count++) {
			if(hotelRooms.get(count).getId() == roomId) {
				Room r = hotelRooms.get(count);
				r.setAmountOfGuests(num);
				hotelRooms.set(count, r);
				break;
			}
		}
	}
	/*delete guest and their reservations*/
	public void deleteGuest(int guestId) {
		boolean test = false;
		for(int count = 0;count<allGuests.size();count++) {
			if(allGuests.get(count).getId() == guestId) {
				db.deleteGuest(allGuests.get(count));
				allGuests.remove(allGuests.get(count));
				test = true;
				break;
			}
		}
		if(test == false) {
			System.out.println("Guest not found: mc");
		}
	}
	/*delete reservation if guest has >=2 reservations
	 *if guest has only one reservation, then use the deleteGuest method*/
	public void deleteReservation(int resId) {
		boolean test = false;
		for(int count = 0;count<allGuests.size();count++) {
			Reservation r = allGuests.get(count).getReservation(resId);
			if(r!=null) {
				test = true;
				Guest g = allGuests.get(count);
				g.deleteReservation(r);
				db.deleteReservation(r);
				allGuests.set(count, g);
				if(g.getReservationList().size()==0) {
					deleteGuest(g.getId());
				}
				test = true;
				break;
			}
		}
		if(test == false) {
			System.out.println("Invalid reservation id: mc");
		}
	}
	/*deletes amenity from hotel options*/
	public void deleteAmenity(int id) {
		boolean test = false;
		for(int count = 0;count<hotelAmenities.size();count++) {
			if(hotelAmenities.get(count).getId() == id){
				db.deleteAmenity(hotelAmenities.get(count));
				hotelAmenities.remove(count);
				test = true;
				break;
			}
		}
		if(test == false) {
			System.out.println("Amenity not found: mc");
		}
	}
	/*deletes employee and their data*/
	public void deleteEmployee(int id, int loggedInId) {
		boolean test = false;
		for(int count = 0;count<employees.size();count++) {
			if(employees.get(count).getId() == id) {
				if(employees.get(count).getId() != 1) {
					if(employees.get(count).getId() != loggedInId) {
						db.deleteEmployee(employees.get(count));
						employees.remove(count);
						test = true;
						break;
					}
				}
			}
		}
		if(test == false) {
			System.out.println("Employee not found: mc");
		}
	}
	/*check in guest
	 *Here be dragons. Thou art forewarned*/
	public boolean guestCheckIn(int reservationId) {
		boolean test = false;
		for(int count=0;count<allGuests.size();count++) {
			Guest g = allGuests.get(count);
			ArrayList<Reservation> reservations = g.getReservationList();
			for(int x =0;x<reservations.size();x++) {
				if(reservations.get(x).getId() == reservationId) {
					Reservation res = reservations.get(x);
					res.checkIn();
					reservations.set(x, res);
					ArrayList<Room> rooms = reservations.get(x).getRoomList();
					for(int y=0;y<rooms.size();y++) {
						for(int z=0;z<hotelRooms.size();z++) {
							if(rooms.get(y).getId() == hotelRooms.get(z).getId()) {
								Room r = rooms.get(y);
								r.setHotelRoomFlag(true);
								r.setAvailability(false);
								r.setRoomPrice(hotelRooms.get(z).getRoomPrice());
								hotelRooms.set(z,r);
								test = true;
								break;
							}
						}
						if(test) {
							break;
						}
					}
					if(test) {
						break;
					}
				}
			}
			if(test) {
				break;
			}
		}
		return test;
	}
	/*must use getBill method before
	 *use this method after bill payment is confirmed
	 *check out guest*/
	public boolean guestCheckOut(int reservationId) {
		boolean test = false;
		Guest g = null;
		Reservation r = null;
		for(int count=0;count<allGuests.size();count++) {
			g = allGuests.get(count);
			ArrayList<Reservation> reservations = g.getReservationList();
			for(int y =0;y<reservations.size();y++) {
				if(reservations.get(y).getId() == reservationId) {
					r = reservations.get(y);
					clearRooms(reservations.get(y).getRoomIds());
					test = true;
					break;
				}
			}
		}
		addProcessedPayment(g, r);
		deleteReservation(reservationId);
		return test;
	}
	/*get bill for guest right before checkout method is used*/
	public String getBill(int reservationId) {
		String bill = "";
		for(int count=0;count<allGuests.size();count++) {
			Guest g = allGuests.get(count);
			ArrayList<Reservation> reservations = g.getReservationList();
			for(int y =0;y<reservations.size();y++) {
				if(reservations.get(count).getId() == reservationId) {
					bill += reservations.get(y).getBillOverview() + "\n";
					break;
				}
			}
		}
		if(bill.length() == 0) {
			return "Error returning bill";
		}
		return bill;
	}
	/*tests to see whether operator attempting to login
	 *has the correct credentials*/
	public boolean employeeLoginCheck(int id, String password) {
		for(int count = 0;count<employees.size();count++) {
			if(employees.get(count).getId() == id) {
				if(employees.get(count).matchPassword(password)) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		return false;
	}
	/*tests to see whether employee is manager or not*/
	public boolean isManager(int id) {
		for(int count = 0;count<employees.size();count++) {
			if(employees.get(count).getId() == id) {
				if(employees.get(count).getManagerFlag() == true) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		return false;
	}
	/*tests to see whether employee is manager or not*/
	public boolean isManager(Employee e) {
		for(int count = 0;count<employees.size();count++) {
			if(employees.get(count).getId() == e.getId()) {
				if(employees.get(count).getManagerFlag() == true) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		return false;
	}
	/*All edit methods are for general information only
	 *It is not for information concerning reservations and rooms
	 *Do not create new objects when passing in the parameters (editGuestInfo(new Guest()))
	 *Don't do this because it will generate a new id and change the value of the object
	 *This goes for all edit methods
	 *
	 *All edit methods - parameter objects must have updated
	 *information already*/
	public void editGuestInfo(Guest g) {
		for(int count=0;count<allGuests.size();count++) {
			if(g.getId() == allGuests.get(count).getId()) {
				Guest g2 = allGuests.get(count);
				g2.setAddress(g.getAddress());
				g2.setEmail(g.getEmail());
				g2.setFirstName(g.getFirstName());
				g2.setLastName(g.getLastName());
				g2.setPhoneNum(g.getPhoneNum());
				allGuests.set(count, g2);
				break;
			}
		}
	}
	public void editEmployeeInfo(Employee e) {
		for(int count = 0;count<employees.size();count++) {
			if(e.getId() == employees.get(count).getId()) {
				Employee e2 = employees.get(count);
				e2.setFirstName(e.getFirstName());
				e2.setLastName(e.getLastName());
				e2.setManagerFlag(e.getManagerFlag());
				e2.setPassword(e.getPassword());
				employees.set(count, e2);
				break;
			}
		}
	}
	public void editAmenityInfo(Amenity a) {
		for(int count = 0;count<hotelAmenities.size();count++) {
			if(a.getId() == hotelAmenities.get(count).getId()) {
				Amenity a2 = hotelAmenities.get(count);
				a2.setDescription(a.getDescription());
				a2.setName(a.getName());
				a2.setPrice(a.getPrice());
				hotelAmenities.set(count, a2);
				break;
			}
		}
	}
	public void editRoomInfo(Room r) {
		for(int count = 0;count<hotelRooms.size();count++) {
			if(r.getId() == hotelRooms.get(count).getId()) {
				Room r2 = hotelRooms.get(count);
				r2.setAvailability(r.getAvailability());
				r2.setDescription(r.getDescription());
				r2.setRoomPrice(r.getRoomPrice());
				hotelRooms.set(count, r2);
				break;
			}
		}
	}
	/*updates the dates for the reservation and all its rooms
	 *returns true if was successful
	 *returns false if unsuccessful*/
	public boolean updateDates(String startDate, String endDate, int resId) {
		boolean test = false;
		ArrayList<Room> rooms = null;
		for(int count = 0;count<allGuests.size();count++) {
			Guest g = allGuests.get(count);
			ArrayList<Reservation> reservations = g.getReservationList();
			for(int x = 0;x<reservations.size();x++) {
				if(reservations.get(x).getId() == resId) {
					rooms = reservations.get(x).getRoomList();
					test = true;
					break;
				}
			}
			if(test) {
				break;
			}
		}
		if(!test) {
			return false;
		}
		for(int count = 0;count<rooms.size();count++) {
			if(!db.checkAvailability(rooms.get(count).getId(), startDate + "/" + endDate)) {
				return false;
			}
		}
		for(int count = 0;count<allGuests.size();count++) {
			Guest g = allGuests.get(count);
			ArrayList<Reservation> reservations = g.getReservationList();
			for(int x = 0;x<reservations.size();x++) {
				if(reservations.get(x).getId() == resId) {
					Reservation r = reservations.get(x);
					r.setReservedDates(startDate, endDate);
					reservations.set(x, r);
					return true;
				}
			}
		}
		return false;
	}
	public Room getHotelRoom(int roomId) {
		Room r = null;
		for(int count = 0;count < hotelRooms.size();count++) {
			if(hotelRooms.get(count).getId() == roomId) {
				r = hotelRooms.get(count);
				break;
			}
		}
		return r;
	}
	/*returns the passcode for room
	 *manager function only*/
	public String getPasscode(int roomId) {
		for(int count = 0;count<hotelRooms.size();count++) {
			if(hotelRooms.get(count).getId() == roomId) {
				return hotelRooms.get(count).getPasscode();
			}
		}
		return "Room does not exist";
	}
	/*returns employee based on id
	 *returns null if employee id does not exist*/
	public Employee getEmployee(int id) {
		for(int count=0;count<employees.size();count++) {
			if(employees.get(count).getId() == id) {
				return employees.get(count);
			}
		}
		return null;
	}
	/*return guest based on reservation ID
	 *returns null if reservation id does not exist*/
	public Guest getGuest(int resId) {
		for(int count=0;count<allGuests.size();count++) {
			Guest g = allGuests.get(count);
			ArrayList<Reservation> reservations = g.getReservationList();
			for(int x=0;x<reservations.size();x++) {
				if(reservations.get(x).getId() == resId) {
					return g;
				}
			}
		}
		return null;
	}
	/*returns specific reservation
	 *returns null if reservation id does not exist*/
	public Reservation getReservation(int resId) {
		boolean test = false;
		Reservation r = null;
		for(int count = 0;count<allGuests.size();count++) {
			ArrayList<Reservation> reservations = allGuests.get(count).getReservationList();
			for(int x = 0;x<reservations.size();x++) {
				if(reservations.get(x).getId() == resId) {
					r = reservations.get(x);
					test = true;
					break;
				}
			}
			if(test) {
				break;
			}
		}
		return r;
	}
	public double getProcessedPaymentSum() {
		double sum = 0;
		for(int count = 0;count<processedPaymentsList.size();count++) {
			sum += processedPaymentsList.get(count).getTotalPayment();
		}
		return sum;
	}
	public ArrayList<Guest> getAllGuests(){
		return allGuests;
	}
	public ArrayList<Reservation> getAllReservations(){
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		for(int count = 0;count<allGuests.size();count++) {
			ArrayList<Reservation> res = allGuests.get(count).getReservationList();
			for(int x = 0;x<res.size();x++) {
				reservations.add(res.get(x));
			}
		}
		return reservations;
	}
	public ArrayList<Room> getHotelRooms(){
		return hotelRooms;
	}
	public ArrayList<Amenity> getHotelAmenities(){
		return hotelAmenities;
	}
	public ArrayList<Employee> getEmployees(){
		return employees;
	}
	public boolean setRoomPrice(String roomType, double price) {
		boolean test = false;
		for(int count = 0;count<hotelRooms.size();count++) {
			if(hotelRooms.get(count).getDescription().equalsIgnoreCase(roomType)) {
				Room r = hotelRooms.get(count);
				r.setRoomPrice(price);
				hotelRooms.set(count, r);
				test = true;
			}
		}
		return test;
	}
	private void clearRoom(int roomId) {
		for(int count=0;count<hotelRooms.size();count++) {
			if(hotelRooms.get(count).getId() == roomId) {
				Room r = hotelRooms.get(count);
				r.clearRoom();
				hotelRooms.set(count, r);
			}
		}
	}
	private void clearRooms(String roomIds) {
		int counter = 0;
    	ArrayList<Integer> indexes = new ArrayList<Integer>();
    	for(int count = 0;count<roomIds.length();count++) {
    		if(Character.isWhitespace(roomIds.charAt(count))) {
    			indexes.add(count);
    	        counter++;
    	    } 
    	}
    	int[] IDs = new int[counter];
    	int lastIndex = 0;
    	for(int count=0;count<indexes.size();count++) {
    		IDs[count] = Integer.parseInt(roomIds.substring(lastIndex, indexes.get(count)));
    		lastIndex = indexes.get(count) + 1;
    	}
		for(int count=0;count<IDs.length;count++) {
			for(int x=0;x<hotelRooms.size();x++) {
				if(hotelRooms.get(x).getId() == IDs[count]){
					Room r = hotelRooms.get(x);
					r.clearRoom();
					hotelRooms.set(x, r);
				}
			}
		}
	}
	private void addProcessedPayment(Guest g, Reservation r) {
		ProcessedPayment pp = new ProcessedPayment(g.getFirstName(), g.getLastName(),
				r.getReservedDatesList().get(0), r.getFinalPrice());
		db.addProcessedPayment(pp);
		pp.setId(db.getLastProcessedPaymentId());
		processedPaymentsList.add(pp);
	}
	private void printGuestIDs() {
		for(int count = 0;count<allGuests.size();count++) {
			ArrayList<Reservation> reservations = allGuests.get(count).getReservationList();
			for(int x = 0;x<reservations.size();x++) {
				ArrayList<Room> rooms = reservations.get(x).getRoomList();
				for(int y = 0;y<rooms.size();y++) {
					System.out.println(rooms.get(y)+rooms.get(y).getAmenityIds());
				}
			}
		}
	}
}
