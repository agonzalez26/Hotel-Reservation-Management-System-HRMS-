package hrms.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

//import javax.sql.DataSource;
//import com.mchange.v2.c3p0.ComboPooledDataSource;

public class Database {
	Connection c = null;
	Statement stmt = null;
	//ComboPooledDataSource cpds;
	//DataSource ds;
	
	public Database(){
		//attempt to connect to DB
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
			System.out.println("Database connection active");
		}catch(Exception e) {
			System.out.println("\nError 1:\n" + e.getMessage());
		}	
		/*try {
			cpds = new ComboPooledDataSource();
			cpds.setDriverClass("org.sqlite.JDBC"); //loads the jdbc driver            
			cpds.setJdbcUrl("jdbc:sqlite:Database.sqlite");
			cpds.setMaxStatements(180);
			ds = cpds;
			c = ds.getConnection();
		}catch(Exception e) {
			System.out.println("\nError 1:\n" + e.getMessage());
		}*/
	}
	public Database(String s) {
		//attempt to connect to DB
		if(s.equals("model")) {
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
			}catch(Exception e) {
				System.out.println("\nError 1a:\n" + e.getMessage());
			}
		}
		else {
			System.out.println("Database construct command error");
		}	
		/*if(s.equals("model")) {
			try {
				cpds = new ComboPooledDataSource();
				cpds.setDriverClass("org.sqlite.JDBC"); //loads the jdbc driver            
				cpds.setJdbcUrl("jdbc:sqlite:Database.sqlite");
				cpds.setMaxStatements(180);
				ds=cpds;
				c = ds.getConnection();
			}catch(Exception e) {
				System.out.println("\nError 1a:\n" + e.getMessage());
			}			
		}
		else {
			System.out.println("Database construct command error");
		}*/
	}
	public Database(Connection con) {
		c = con;
	}
	public Connection getConnection() {
		return c;
	}
	public void closeConnection() {
		try {
			c.close();
			System.out.println("Database connection terminated");
		}catch(Exception e) {
			System.out.println("\nError 1b:\n" + e.getMessage());
		}
	}
	public ResultSet initializeGuests() {			//loads all guest information into models, on startup
		return getGuestsList();
	}
	public ResultSet initializeRooms() {
		return getRoomsList();
	}
	public ResultSet initializeAmenities() {
		return getAmenitiesList();
	}
	public ResultSet initializeEmployees() {
		return getEmployeesList();
	}
	public ResultSet initializeProcessedPayments() {
		return getProcessedPaymentsList();
	}
	public void close() {
		try {
			stmt.close();
		}catch(Exception e) {
			System.out.println("\nError attempting to close database");
		}
	}
	public int generateGuestId() {
		boolean valid = true;
		Random gen = new Random();
		int id = -1;
		try {
			this.stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Guests");
			do {
				id = gen.nextInt(1000);
				while(rs.next()) {
					if(rs.getInt("id") == id) {
						valid = false;
					}
				}
			}while(valid == false);
			rs.close();
		}catch(Exception e) {
			System.out.println("\nError DBgen1:\n" + e.getMessage());
		}
		return id;
	}
	public int generateReservationId() {
		boolean valid = true;
		Random gen = new Random();
		int id = -1;
		try {
			this.stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Reservations");
			do {
				id = gen.nextInt(1000);
				while(rs.next()) {
					if(rs.getInt("id") == id) {
						valid = false;
					}
				}
			}while(valid == false);
			rs.close();
		}catch(Exception e) {
			System.out.println("\nError DBgen2:\n" + e.getMessage());
		}
		return id;
	}
	public int generateAmenityId() {
		boolean valid = true;
		Random gen = new Random();
		int id = -1; 
		try {
			this.stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Amenities");
			do {
				id = gen.nextInt(1000);
				while(rs.next()) {
					if(rs.getInt("id") == id) {
						valid = false;
					}
				}
			}while(valid == false);
			rs.close();
		}catch(Exception e) {
			System.out.println("\nError DBgen3:\n" + e.getMessage());
		}
		return id;
	}
	public int generateEmployeeId() {
		boolean valid = true;
		Random gen = new Random();
		int id = -1; 
		try {
			this.stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Employees");
			do {
				id = gen.nextInt(1000);
				while(rs.next()) {
					if(rs.getInt("id") == id) {
						valid = false;
					}
				}
			}while(valid == false);
			rs.close();
		}catch(Exception e) {
			System.out.println("\nError DBgen4:\n" + e.getMessage());
		}
		return id;
	}
	public void addGuest(Guest g) {							//add guest to database
		PreparedStatement prep = null;
		try {
			prep = c.prepareStatement("INSERT INTO Guests VALUES (?,?,?,?,?,?,?,?,?)");
			prep.setInt(1, g.getId());
			prep.setString(2, g.getFirstName());
			prep.setString(3, g.getLastName());
			prep.setString(4, g.getAddress());
			prep.setString(5, g.getEmail());
			prep.setString(6, g.getPhoneNum());
			prep.setString(7, g.getReservationIds());
			prep.setInt(8, g.getAmountOfReservations());
			prep.setInt(9, g.getAge());
			prep.execute();
			prep.close();
		}catch(Exception e) {
			System.out.println("\nError DB1:\n" + e.getMessage());
		}
	}
	public void addReservation(Reservation r) {									//add reservation to database
		PreparedStatement prep = null;
		try {
			prep = c.prepareStatement("INSERT INTO Reservations VALUES (?,?,?,?,?,?)");		//should only add reservation through guest object
			prep.setInt(1, r.getId());
			prep.setInt(2, r.getAmountOfRooms());
			prep.setString(3, r.getRoomIds());
			prep.setString(4, r.getReservedDates());
			prep.setBoolean(5, r.getCheckedIn());
			prep.setDouble(6, r.getFinalPrice());
			prep.execute();
			prep.close();
		}catch(Exception e) {
			System.out.println("\nError DB2:\n" + e.getMessage());
		}
	}
	public void addAmenity(Amenity a) {			//add amenity to database
		PreparedStatement prep = null;
		try {
			prep = c.prepareStatement("INSERT INTO Amenities VALUES (?,?,?,?)");
			prep.setInt(1, a.getId());
			prep.setString(2, a.getName());
			prep.setString(3, a.getDescription());
			prep.setDouble(4, a.getPrice());
			prep.execute();
			prep.close();
		}catch(Exception e) {
			System.out.println("\nError DB3:\n" + e.getMessage());
		}
	}
	public void addEmployee(Employee em) {
		PreparedStatement prep = null;
		try {
			prep = c.prepareStatement("INSERT INTO Employees VALUES (?,?,?,?,?)");
			prep.setInt(1, em.getId());
			prep.setString(2, em.getFirstName());
			prep.setString(3, em.getLastName());
			prep.setString(4, em.getPassword());
			prep.setBoolean(5, em.getManagerFlag());
			prep.execute();
			prep.close();
		}catch(Exception e) {
			System.out.println("\nError DB4:\n" + e.getMessage());
		}
	}
	public void addProcessedPayment(ProcessedPayment pp) {						//log new processed payment
		PreparedStatement prep = null;
		try {
			prep = c.prepareStatement("INSERT INTO ProcessedPayments VALUES (?,?,?,?,?)");
			prep.setInt(1, pp.getId());
			prep.setString(2, pp.getGuestFirstName());
			prep.setString(3, pp.getGuestLastName());
			prep.setString(4, pp.getReservedDatePeriod());
			prep.setDouble(5, pp.getTotalPayment());
			prep.execute();
			prep.close();
		}catch(Exception e) {
			System.out.println("\nError DB4:\n" + e.getMessage());
		}
	}
	public void deleteGuest(Guest g) {											//delete guest and any reservations from database only
		PreparedStatement prep = null;
		try {
			prep = c.prepareStatement("DELETE FROM Guests WHERE id = ?;");
			prep.setInt(1, g.getId());
			prep.execute();
			for(int count=0;count<g.getReservationList().size();count++) {
				deleteReservation(g.getReservationList().get(count));
			}
			prep.close();
		}catch(Exception e) {
			System.out.println("\nError DB5:\n" + e.getMessage());
		}
	}
	public void deleteReservation(Reservation r) {										//delete reservation and any related data from database only
		PreparedStatement prep = null;
		try {
			prep = c.prepareStatement("DELETE FROM Reservations WHERE id = ?;");
			prep.setInt(1, r.getId());
			prep.execute();
			for(int count=0;count<r.getRoomList().size();count++) {
				clearRoom(r.getRoomList().get(count));
			}
			prep.close();
		}catch(Exception e) {
			System.out.println("\nError DB6:\n" + e.getMessage());
		}
	}
	public void clearRoom(Room r) {														//clear data from a room and make the room available
		PreparedStatement prep = null;
		try {																			//probably wont be used, will just use clearRoom method in room class
			prep = c.prepareStatement("UPDATE Rooms SET totalAmenities = ?,"
					+ " totalPrice = ?, availability = ?, amenityIDs = ?, amountOfAmenities = ?,"
					+ " amountOfDays = ?, currentReservedDatePeriod = ?, passcode = ? WHERE id = ?");
			prep.setDouble(1, 0);
			prep.setDouble(2, 0);
			prep.setBoolean(3, true);
			prep.setString(4, "");
			prep.setInt(5, 0);
			prep.setInt(6, 0);
			prep.setString(7, "-1");
			prep.setInt(8, r.getId());
			prep.setString(9, r.getPasscode());
			prep.execute();
			prep.close();
		}catch(Exception e) {
			System.out.println("\nError DB7:\n" + e.getMessage());
		}
	}
	public void deleteAmenity(Amenity a) {											//delete amenity and any related data from database only
		PreparedStatement prep = null;
		try {
			prep = c.prepareStatement("DELETE FROM Amenities WHERE id = ?;");
			prep.setInt(1, a.getId());
			prep.execute();
			prep.close();
		}catch(Exception e) {
			System.out.println("\nError DB8:\n" + e.getMessage());
		}
	}
	public void deleteEmployee(Employee em) {
		PreparedStatement prep = null;
		try {
			prep = c.prepareStatement("DELETE FROM Employees WHERE id = ?;");
			prep.setInt(1, em.getId());
			prep.execute();
			prep.close();
		}catch(Exception e) {
			System.out.println("\nError DB9:\n" + e.getMessage());
		}
	}
	/* ***Whenever we change data regarding anything, the changes first get pushed through to the class,
	 * then we use the class to push the data through to the database.
	 */
	public void updateGuest(Guest g) {												//update a guest's information in the database only
		PreparedStatement prep = null;
		try {
			prep = c.prepareStatement("UPDATE Guests SET firstName = ?, lastName = ?,"
					+ " address = ?, email = ?, phoneNumber = ?, reservationIDs = ?,"
					+ " amountOfReservations = ?, age = ? WHERE id = ?");
			prep.setString(1, g.getFirstName());
			prep.setString(2, g.getLastName());
			prep.setString(3, g.getAddress());
			prep.setString(4, g.getEmail());
			prep.setString(5, g.getPhoneNum());
			prep.setString(6, g.getReservationIds());
			prep.setInt(7, g.getAmountOfReservations());
			prep.setInt(8, g.getAge());
			prep.setInt(9, g.getId());
			prep.execute();
			prep.close();
		}catch(Exception e) {
			System.out.println("\nError DB10:\n" + e.getMessage());
		}
	}
	public void updateReservation(Reservation r) {									//update reservation information in the database only
		PreparedStatement prep = null;
		try {
			prep = c.prepareStatement("UPDATE Reservations SET amountOfRooms = ?,"
					+ " roomIds = ?, reservedDates = ?, checkedIn = ?, totalPrice = ? WHERE id = ?");
			prep.setInt(1, r.getAmountOfRooms());
			prep.setString(2, r.getRoomIds());
			prep.setString(3, r.getReservedDates());
			prep.setBoolean(4, r.getCheckedIn());
			prep.setDouble(5, r.getFinalPrice());
			prep.setInt(6, r.getId());
			prep.execute();
			prep.close();
		}catch(Exception e) {
			System.out.println("\nError DB11:\n" + e.getMessage());
		}
	}
	public void updateRoom(Room r) {												//update room information in the database only
		PreparedStatement prep = null;
		try {
			prep = c.prepareStatement("UPDATE Rooms SET roomPrice = ?,"
					+ " totalAmenityPrice = ?, totalPrice = ?, description = ?, availability = ?,"
					+ " amenityIDs = ?, amountOfAmenities = ?, amountOfGuests = ?, amountOfDays = ?,"
					+ " currentReservedDatePeriod = ?, passcode = ? WHERE id = ?");
			prep.setDouble(1, r.getRoomPrice());
			prep.setDouble(2, r.getAmenityPrice());
			prep.setDouble(3, r.getTotalPrice());
			prep.setString(4, r.getDescription());
			prep.setBoolean(5, r.getAvailability());
			prep.setString(6, r.getAmenityIds());
			prep.setInt(7, r.getAmountOfAmenities());
			prep.setInt(8, r.getAmountOfGuests());
			prep.setInt(9, r.getAmountOfDays());
			prep.setString(10, r.getCurrentReservedDatePeriod());
			prep.setString(11, r.getPasscode());
			prep.setInt(12, r.getId());
			prep.execute();
			prep.close();
		}catch(Exception e) {
			System.out.println("\nError DB12:\n" + e.getMessage());
		}
	}
	public void updateAmenity(Amenity a) {											//update amenity information in the database only0
		PreparedStatement prep = null;
		try {
			prep = c.prepareStatement("UPDATE Amenities SET name = ?, description = ?,"
					+ " price = ? WHERE id = ?");
			prep.setString(1, a.getName());
			prep.setString(2, a.getDescription());
			prep.setDouble(3, a.getPrice());
			prep.setInt(4, a.getId());
			prep.execute();
			prep.close();
		}catch(Exception e) {
			System.out.println("\nError DB13:\n" + e.getMessage());
		}
	}
	public void updateEmployee(Employee em) {
		PreparedStatement prep = null;
		try {
			prep = c.prepareStatement("UPDATE Employees SET firstName = ?, lastName = ?,"
					+ " password = ?, managerFlag = ? WHERE id = ?");
			prep.setString(1, em.getFirstName());
			prep.setString(2, em.getLastName());
			prep.setString(3, em.getPassword());
			prep.setBoolean(4, em.getManagerFlag());
			prep.setInt(5, em.getId());
			prep.execute();
			prep.close();
		}catch(Exception e) {
			System.out.println("\nError DB14:\n" + e.getMessage());
		}
	}
	//No need to add(or delete) rooms because we have a set number of rooms
		/*We add rooms to reservations by assigning the IDs to the reservation in the database
		 * then we pull that room information from the database and add it to the list in the reservation
		 * and fill it with any necessary information
		 */
		/*public void addRoom(String firstName, String lastName, String address,
				String email, String phoneNumber, int age) {
			
			try {
				PreparedStatement prep = c.prepareStatement("INSERT INTO Rooms VALUES (?,?,?,?,?,?,?,?,?)");
				prep.setString(2, firstName);
				prep.setString(3, lastName);
				prep.setString(4, address);
				prep.setString(5, email);
				prep.setString(6, phoneNumber);
				prep.setInt(9, age);
				prep.execute();
			}catch(Exception e) {
				System.out.println("\nError 9:\n" + e.getMessage());
			}
			System.out.println("Room successfully added");
		}*/
	public ResultSet getGuestsList() {								//return guests to gui, output format depends on how information in gui is setup
		try {
			this.stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Guests");
			return rs;
		}catch(Exception e) {
			System.out.println("\nError DB15:\n" + e.getMessage());
		}
		return null;
	}
	public ResultSet getReservationsList() {								//return reservation to gui, output format depends on how information in gui is setup
		try {
			this.stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Reservations");
			return rs;
		}catch(Exception e) {
			System.out.println("\nError DB16:\n" + e.getMessage());
		}
		return null;
	}
	public ResultSet getRoomsList() {								//return rooms to gui, output format depends on how information in gui is setup
		try {
			this.stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Rooms");
			return rs;
		}catch(Exception e) {
			System.out.println("\nError DB17:\n" + e.getMessage());
		}
		return null;
	}
	public ResultSet getEmployeesList() {
		try {
			this.stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Employees");
			return rs;
		}catch(Exception e) {
			System.out.println("\nError DB18:\n" + e.getMessage());
		}
		return null;
	}
	public ResultSet getAmenitiesList() {								//return available amenities to gui, output format depends on how information in gui is setup
		try {
			this.stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Amenities");
			return rs;
		}catch(Exception e) {
			System.out.println("\nError DB19:\n" + e.getMessage());
		}
		return null;
	}
	public ResultSet getProcessedPaymentsList() {
		try {
			this.stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ProcessedPayments");
			return rs;
		}catch(Exception e) {
			System.out.println("\nError DB19b:\n" + e.getMessage());
		}
		return null;
	}
	public int getLastProcessedPaymentId() {
		try {
			this.stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ProcessedPayments WHERE id=(SELECT max(id) FROM ProcessedPayments)");
			return rs.getInt("id");
		}catch(Exception e) {
			System.out.println("\nError DB19b:\n" + e.getMessage());
		}
		return -1;
	}
	public boolean checkAvailability(Room r, String reserveDate) {
		try {
			this.stmt = c.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM Reservations");
			while(res.next()) {
				String roomIds = res.getString("roomIDs");
				int rIds[] = divideParseInt(roomIds);
		    	for(int count = 0;count<rIds.length;count++) {
		    		if(rIds[count] == r.getId()) {
		    			String dates = res.getString("reservedDates");
		    			String[] dateList = divideString(dates);
		    			if(isOverlap(reserveDate, dateList[count])) {
		    				return false;
		    			}
		    		}
		    	}
			}
			res.close();
		}catch(Exception e) {
			System.out.println("\nError DB20:\n" + e.getMessage());
		}
		return true;
	}
	public boolean checkAvailability(int roomId, String reserveDate) {
		try {
			this.stmt = c.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM Reservations");
			while(res.next()) {
				String roomIds = res.getString("roomIDs");
				int rIds[] = divideParseInt(roomIds);
		    	for(int count = 0;count<rIds.length;count++) {
		    		if(rIds[count] == roomId) {
		    			String dates = res.getString("reservedDates");
		    			String[] dateList = divideString(dates);
		    			if(isOverlap(reserveDate.substring(0,reserveDate.length()-1), dateList[count])) {
		    				return false;
		    			}
		    		}
		    	}
			}
			res.close();
		}catch(Exception e) {
			System.out.println("\nError DB21:\n" + e.getMessage());
		}
		return true;
	}
	private boolean isOverlap(String o, String t) {
		LocalDate s1 = LocalDate.parse(o.substring(0,10));
		LocalDate e1 = LocalDate.parse(o.substring(11));
		LocalDate s2 = LocalDate.parse(t.substring(0,10));
		LocalDate e2 = LocalDate.parse(t.substring(11));
		java.util.Date start1 = java.sql.Date.valueOf(s1);
		java.util.Date end1 = java.sql.Date.valueOf(e1);
		java.util.Date start2 = java.sql.Date.valueOf(s2);
		java.util.Date end2 = java.sql.Date.valueOf(e2);
		
		return start1.getTime() <= end2.getTime() && start2.getTime() <= end1.getTime();
	}
	private int[] divideParseInt(String ids) {
		int counter = 0;
    	ArrayList<Integer> indexes = new ArrayList<Integer>();
    	for(int count = 0;count<ids.length();count++) {
    		if(Character.isWhitespace(ids.charAt(count))) {
    			indexes.add(count);
    	        counter++;
    	    }
    	}
    	int lastIndex = 0;
    	int[] rIds = new int[counter];
    	for(int count=0;count<rIds.length;count++) {
    		rIds[count] = Integer.parseInt(ids.substring(lastIndex, indexes.get(count)));
    		lastIndex = indexes.get(count) + 1;
    	}
    	return rIds;
	}
	private String[] divideString(String s) {
		int counter = 0;
    	ArrayList<Integer> indexes = new ArrayList<Integer>();
    	for(int count = 0;count<s.length();count++) {
    		if(Character.isWhitespace(s.charAt(count))) {
    			indexes.add(count);
    	        counter++;
    	    }
    	}
    	int lastIndex = 0;
    	String[] rIds = new String[counter];
    	for(int count=0;count<rIds.length;count++) {
    		rIds[count] = s.substring(lastIndex, indexes.get(count));
    		lastIndex = indexes.get(count) + 1;
    	}
    	return rIds;
	}
	/*public Guest getGuest(int guestID) {					//get guest object, will work on this later, more complex than it seems on surface
		try {												//probably not even necessary
			this.stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT FROM Guests WHERE id = '" + guestID + "';");
			return new Guest(rs.getString("firstName"), rs.getString("lastName"),
					rs.getString("address"), rs.getString("email"),
					rs.getString("phoneNumber"), rs.getInt("guestId"));
		}catch(Exception e) {
			System.out.println("\nError 6:\n" + e.getMessage());
		}
		return null;
	}*/
	
}
