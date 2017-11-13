/*The Room object is created when guest adds room to their reservation.*/
package hrms.Model;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.sql.Connection;
import java.util.Random;

public class Room {
	private int id; /*Room id of Room*/
	private String description;/*Room description of Room*/
//	private Guest primaryPerson; /*Guest that has Room on hold*/ this can probably go in the reservation side 
//	private List<Guest> secondaryPeople; questionable if we need to add the info of guest staying in room
	private ArrayList<Amenity> amenityList = new ArrayList<Amenity>();; /*List of amenities attached to the room*/
	private double roomPrice; /*room price*/
	private boolean availability; /*if the room is available or not*/
	private double totalPrice;
	private double totalAmenityPrice;
	private int amountOfGuests, amountOfAmenities, amountOfDays;
	private String startDate, endDate, currentReservedDatePeriod, passcode;		//date period room is reserved for, yyyy-mm-dd (yyyy-mm-dd/yyyy-mm-dd)
	private boolean hotelRoomFlag;
	private Database db;
	Connection c;
	
	public Room(Connection con, int i, double rP, double tAP, double tP,
			String d, boolean a, String AIDs, int aoa, int aog, int aod, String cdrp, String p, boolean hrf) {		//constructor for filling in information on startup from database
		c = con;
		db = new Database(c);
		hotelRoomFlag = hrf;
		id = i;
		roomPrice = rP;
		totalAmenityPrice = tAP;
		totalPrice = tP;
		description = d;
		availability = a;
		amountOfAmenities = aoa;
		amountOfGuests = aog;
		passcode = p;
		//amountOfDays = aod;
		currentReservedDatePeriod = cdrp;
		if(!cdrp.equals("-1")) {
			calculateStartEndDate();
			calculateAmountOfDays();	
		}
		if (AIDs.equals("-1")) {
			amenityList.clear();
		}
		else {
			int counter = 0;
	    	ArrayList<Integer> indexes = new ArrayList<Integer>();
	    	
	    	for(int count = 0;count<AIDs.length();count++) {
	    		if(Character.isWhitespace(AIDs.charAt(count))) {
	    			indexes.add(count);
	    	        counter++;
	    	    } 
	    	}
	    	int[] IDs = new int[counter];
	    	int lastIndex = 0;
	    	for(int count=0;count<indexes.size();count++) {
	    		IDs[count] = Integer.parseInt(AIDs.substring(lastIndex, indexes.get(count)));
	    		lastIndex = indexes.get(count) + 1;
	    	}
	    	ResultSet rs = db.getAmenitiesList();
	    	try {
	    		for(int x=0;x<IDs.length;x++) {
	    			while(rs.next()) {
		    			int amenityId = rs.getInt("id");
		        		if(amenityId == IDs[x]) {
		        			amenityList.add(new Amenity(c, amenityId, rs.getString("name"),
		        					rs.getString("description"), rs.getDouble("price")));
		        		}
		        	}
	    			rs.close();
	    			rs = db.getRoomsList();
	    		}
	    	}catch(Exception e) {
	    		System.out.println("\nError Rom 1:\n" + e.getMessage());
	    	}
		}
	}
	/*@return room id*/
	public int getId(){
		return id;
	}
	
	/*@param room description is set (String)*/
	public void setDescription(String desc){
		description = desc;
		if(hotelRoomFlag) {
			db.updateRoom(this);
		}
	}
	
	/*@return room description*/
	public String getDescription(){
		return description;
	}
	
	/*@param room price is set (double)*/
	public void setRoomPrice(double price){
		roomPrice = price;
		totalPrice = getAmenityPrice() + (roomPrice*amountOfDays);
		if(hotelRoomFlag) {
			db.updateRoom(this);
		}
	}
	
	/*@return room price*/
	public double getRoomPrice(){
		return roomPrice;
	}
	public int getAmountOfDays() {
		return amountOfDays;
	}
	public double getAmenityPrice() {
		totalAmenityPrice = 0;
		for(int count = 0;count<amenityList.size();count++) {
			totalAmenityPrice += amenityList.get(count).getPrice();
		}
		return totalAmenityPrice;
	}
	public double getTotalPrice() {
		updateRoom();
		return totalPrice;
	}
	public void updateRoom() {			//updates the total price and amount of amenities to current values
		calculateStartEndDate();
		calculateAmountOfDays();
		totalPrice = getAmenityPrice() + (roomPrice*amountOfDays);
		amountOfAmenities = amenityList.size();
	}
	
	/*Add amenity to roomAmenitiesList
	 * @pre: amenity exists in database
	 * @post: amenity added to roomAmenitiesList
	 * @post: roomAmenitiesList.size() > 0*/
	public void addAmenity(Amenity a){
		amenityList.add(a);
		updateRoom();
		if(hotelRoomFlag) {
			db.updateRoom(this);
		}
	}
	
	/*Remove amenity from roomAmenitiesList
	 * @pre: amenity exists in roomAmenitiesList
	 * @pre: roomAmenitiesList.size() > 0
	 * @post: decrease size of roomAmenitiesList*/
	public void removeAmenity(Amenity a){
		amenityList.remove(a);
		updateRoom();
		if(hotelRoomFlag) {
			db.updateRoom(this);
		}
	}
	
	/*Returns list of amenities for Room*/
	public ArrayList<Amenity> getAmenityList(){
		return amenityList;
	}
	
	/*Clears all existing data attached to room
	 * @pre: roomId exists in database
	 * @post: room data cleared from database
	 * */
	public void clearRoom(){
		amenityList.clear();
		totalPrice = 0.0;
		totalAmenityPrice = 0.0;
		amountOfGuests = 0;
		amountOfAmenities = 0;
		amountOfDays = 0;
		startDate = "-1";
		endDate = "-1";
		currentReservedDatePeriod = "-1";
		generatePasscode();
		availability = true;
		if(hotelRoomFlag) {
			db.updateRoom(this);
		}
	}
	
	/*Sets the Room to be available or unavailable
	 * @pre: room must exist
	 * @post: flag is set*/
	public void setAvailability(boolean b){
		availability = b;
		if(availability == false) {
			amountOfGuests = 1;
		}
		if(hotelRoomFlag) {
			db.updateRoom(this);
		}
	}
	
	public boolean getAvailability() {
		return availability;
	}
	public String getAmenityIds() {
		String output = "";
		if(amenityList.isEmpty()) {
			output = "-1";
		}
		else {
			for(int count=0;count<amenityList.size();count++) {
				output += amenityList.get(count).getId() + " ";
			}
		}
		return output;
	}
	public void calculateAmountOfDays() {
		calculateStartEndDate();
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
		    Date date1 = myFormat.parse(startDate);
		    Date date2 = myFormat.parse(endDate);
		    long diff = date2.getTime() - date1.getTime();
		    amountOfDays = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		    amountOfDays++;
		} catch (Exception e) {
			System.out.println("\nError Rom 2:\n" + e.getMessage());
		}
	}
	public void calculateStartEndDate() {
		startDate = currentReservedDatePeriod.substring(0,10);
		endDate = currentReservedDatePeriod.substring(11, currentReservedDatePeriod.length());
	}
	public int getAmountOfAmenities() {
		return amountOfAmenities;
	}
	public int getAmountOfGuests() {
		return amountOfGuests;
	}
	public void setAmountOfGuests(int x) {
		amountOfGuests = x;
		updateRoom();
		if(hotelRoomFlag) {
			db.updateRoom(this);
		}
	}
	public void newPasscode() {
		generatePasscode();
	}
	public String getPasscode() {
		return passcode;
	}
	public void setCurrentReservedDatePeriod(String s) {
		currentReservedDatePeriod = s;
		calculateAmountOfDays();
		updateRoom();
		if(hotelRoomFlag) {
			db.updateRoom(this);
		}
	}
	public void setHotelRoomFlag(boolean b) {
		hotelRoomFlag = b;
	}
	public void setEndDate(String s) {
		endDate = s;
		currentReservedDatePeriod = currentReservedDatePeriod.substring(0,11)+endDate;
		updateRoom();
		if(hotelRoomFlag) {
			db.updateRoom(this);
		}
	}
	public boolean getHotelRoomFlag() {
		return hotelRoomFlag;
	}
	public void checkIn(String date) {
		setAvailability(false);
		generatePasscode();
		setCurrentReservedDatePeriod(date);
		if(hotelRoomFlag) {
			db.updateRoom(this);
		}
	}
	public String getStartDate() {
		return startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public String getCurrentReservedDatePeriod() {
		return currentReservedDatePeriod;
	}
	public String toString() {
		return "\nRoom " + id;
	}
	public String getBillOverview() {
		String bill = "";
		bill += "Room:" + id + "\n" + "Rate: $" + roomPrice +
				"\nDays Stayed: " + amountOfDays +"\nRated Price: $" +
				(amountOfDays*roomPrice) + "\n\n";
		for(int count=0;count<amenityList.size();count++) {
			bill += "Amenity " + amenityList.get(count).getName() + "\n" +
					"Price: $" + amenityList.get(count).getPrice() + "\n\n";
		}
		bill += "Total Room Cost: $" + getTotalPrice() + "\n\n";
		return bill;
	}
	private void generatePasscode() {
		Random random = new Random();
		String p = String.format("%04d", random.nextInt(10000));
		passcode = p;
	}
}
