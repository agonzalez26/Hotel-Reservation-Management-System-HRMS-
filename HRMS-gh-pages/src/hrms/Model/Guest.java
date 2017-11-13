/*A Guest is a customer at the hotel. A Guest is
an extension of the Operator class that can create and update a Reservation.
Each guests will have information that is stored in the system.*/

package hrms.Model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Connection;

public class Guest extends Operator{
	private String firstName;	/*The first name of the guest at the hotel*/
	private String lastName;	 /*The last name of the guest staying at the hotel.*/
	private String address;	 /*The street address of the guest staying at the hotel*/
	private String email;	 /*The email address of the guest staying at the hotel.*/
	private String phoneNumber; /*The phone number of the guest staying at the hotel*/
	private int age;		//age of the guest
	private int id;	 /*The guest ID of the guest staying at the hotel.*/
	//private int confirmationNumber;	 /*The confirmation number of the guest staying at the hotel*/
	//The idea of a confirmation number isnt needed if we have reservation IDs
	//In the instance of having multiple reservations for one guest, we can have multiple reservation IDs 
	//assigned to a guest instead of one confirmation number which has the different reservation IDs
	//assigned to it anyways (inefficient)
	private int amountOfReservations;	//the number of reservations that the person has
	private ArrayList<Reservation> reservationList = new ArrayList<Reservation>();	//The reservations assigned to that guest
	private Database db;
	Connection c;
	
	public Guest(Connection con, String fn, String ln, String adr, String eml, String pn, int a) {
		c = con;
		db = new Database(c);
		firstName = fn;												//constructor for new guest
		lastName = ln;
		address = adr;
		email = eml;
		phoneNumber = pn;
		age = a;
		id = db.generateGuestId();
		updateAOR();
	}
	public Guest(Connection con, int i, String fn, String ln, String adr, String eml, String pn, int a, int aor, String reservationIds) {						//initialization constructor
		c = con;
		db = new Database(c);
		id = i;																					
		firstName = fn;												
		lastName = ln;
		address = adr;
		email = eml;
		phoneNumber = pn;
		age = a;
		amountOfReservations = aor;
		int counter = 0;
    	ArrayList<Integer> indexes = new ArrayList<Integer>();
    	for(int count = 0;count<reservationIds.length();count++) {
    		if(Character.isWhitespace(reservationIds.charAt(count))) {
    			indexes.add(count);
    	        counter++;
    	    } 
    	}
    	int[] IDs = new int[counter];
    	int lastIndex = 0;
    	for(int count=0;count<indexes.size();count++) {
    		IDs[count] = Integer.parseInt(reservationIds.substring(lastIndex, indexes.get(count)));
    		lastIndex = indexes.get(count) + 1;
    	}
    	ResultSet rs = db.getReservationsList();
    	try {
    		for(int x=0;x<IDs.length;x++) {
    			while(rs.next()) {
        			int resId = rs.getInt("id");
            		if(resId == IDs[x]) {
            			reservationList.add(new Reservation(c, resId, rs.getInt("amountOfRooms"),
            					rs.getString("roomIDs"),rs.getString("reservedDates"), 
            					rs.getBoolean("checkedIn"), rs.getDouble("totalPrice")));
            		}
            	}
    			rs.close();
    			rs = db.getReservationsList();
    		}
    	}catch(Exception e) {
    		System.out.println("\nError Gus 1:\n" + e.getMessage());
    	}
	}
	/*@param guest first name is set (String)*/
	public void setFirstName(String fName){
		firstName = fName;
		db.updateGuest(this);
	}
	
	/*@return guest first name*/
	public String getFirstName() {
		return firstName;
	}
	
	/*@param guest last name is set (String)*/
	public void setLastName(String lName){
		lastName = lName;
		db.updateGuest(this);
	}
	
	/*@return guest last name*/
	public String getLastName(){
		return lastName;
	}
	
	/*@param guest address is set (String)*/
	public void setAddress(String adr){
		address = adr;
		db.updateGuest(this);
	}
	
	/*@return guest address*/
	public String getAddress(){
		return address;
	}
	
	/*@param guest email is set (String)*/
	public void setEmail(String em){
		email = em;
		db.updateGuest(this);
	}
	
	/*@return guest email*/
	public String getEmail(){
		return email;
	}
	
	/*@param guest phone number is set (String)*/
	public void setPhoneNum(String pnum){
		phoneNumber = pnum;
		db.updateGuest(this);
	}
	
	/*@return guest phone number*/
	public String getPhoneNum(){
		return phoneNumber;
	}
	
	/*@param guest id is set (int)*/
	public void setId(int i){
		id = i;
		db.updateGuest(this);
	}

	/*@return guest id*/
	public int getId(){
		return id;
	}
	public void deleteReservation(Reservation r) {
		reservationList.remove(r);
		updateAOR();
		db.updateGuest(this);
	}
	public void deleteReservation(int rid) {
		for(int count=0;count<reservationList.size();count++) {
			if(rid == reservationList.get(count).getId()) {
				reservationList.remove(count);
				updateAOR();
				db.updateGuest(this);
				break;
			}
		}
	}
	/*updates reservation
	 *returns true if successful
	 *returns false if unsuccessful(Reservation does not exist)*/
	public boolean setReservation(Reservation r) {
		for(int count=0;count<reservationList.size();count++) {
			if(reservationList.get(count).getId() == r.getId()) {
				reservationList.set(count, r);
				return true;
			}
		}
		return false;
	}
	public void addReservation(Reservation r) {
		reservationList.add(r);
		boolean test = true;
		try {
			ResultSet rs = db.getGuestsList();				//test to see whether reservation is being added before
			while(rs.next() && test == true) {				//or after the guest is being uploaded into the database
				if (rs.getInt("id") == id) {
					test = false;
					updateAOR();
					db.updateGuest(this);
				}
			}
		}catch(Exception e) {
			System.out.println("\nError Gus 2:\n" + e.getMessage());
		}
	}
	public void addRoom(int resId, Room r, String reserveDate) {
		boolean test = false;;
		for(int count = 0;count<reservationList.size();count++) {
			if(reservationList.get(count).getId() == resId) {
				Reservation res = reservationList.get(count);
				res.addRoom(r, reserveDate);
				reservationList.set(count, res);
				db.updateReservation(reservationList.get(count));
				test = true;
				break;
			}
		}
		if(test == false) {
			System.out.println("Reservation not found: Gus");
		}
	}
	public void addAmenity(int resId, int roomId, Amenity a) {
		for(int x = 0;x<reservationList.size();x++) {
			if(reservationList.get(x).getId() == resId) {
				Reservation r = reservationList.get(x);
				r.addAmenity(roomId, a);
				reservationList.set(x, r);
				db.updateReservation(r);
				break;
			}
		}
	}
	public Reservation getReservation(int reservationId) {
		for(int count=0;count<reservationList.size();count++) {
			if(reservationList.get(count).getId() == reservationId) {
				return reservationList.get(count);
			}
		}
		return null;
	}
	public ArrayList<Reservation> getReservationList(){
		return reservationList;
	}
	public int getAmountOfReservations() {
		updateAOR();
		return amountOfReservations;
	}
	/*Get all reservations Ids in one string, separated by spaces */
	public String getReservationIds() {
		String output = "";
		for(int count=0;count<reservationList.size();count++) {
			output += reservationList.get(count).getId() + " ";
		}
		return output;
	}
	public int getAge() {
		return age;
	}
//	public void updateGuest() {
//		amountOfReservations = reservationList.size();
//	}
	public String toString() {
		return "Name: " + firstName + " " + lastName + "\tID: " + id + "\nReservation Ids: "+getReservationIds();
	}
	private void updateAOR() {
		amountOfReservations = reservationList.size();
	}
}
