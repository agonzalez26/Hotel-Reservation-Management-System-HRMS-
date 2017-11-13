/*Amenities are services and products that are provided by the hotel that are provided by the hotel. */
package hrms.Model;

import java.sql.Connection;

public class Amenity {
	private int id;/*The Amenity identifier for a particular amenity.*/
	private String name;	/*Amenity string of the name of the Amenity*/
	private String description;	/*Amenity string that contains the description of the Amenity*/
	private double price;	/*Amenity string that contains the price of the Amenity*/
	private Database db;
	Connection c;
	
	public Amenity(Connection con, String n, String desc, double p) {			//constructor for new amenity
		c = con;
		db = new Database(c);
		name = n;
		description = desc;
		price = p;
		id = db.generateAmenityId();
	}
	public Amenity(Connection con, int i, String n, String d, double p) {								//constructor for filling in information on startup from database
		c = con;
		db = new Database(c);
		id = i;
		name = n;
		description = d;
		price = p;
	}
	/*@return amenity id*/
	public int getId(){
		return id;
	}
	
	/*@param amenity name is set(String)*/
	public void setName(String n){
		name = n;
		db.updateAmenity(this);
	}
	
	/*@return amenity name*/
	public String getName(){
		return name;
	}
	
	/*@param amenity description is set(String)*/
	public void setDescription(String desc){
		description = desc;
		db.updateAmenity(this);
	}
	
	/*@return amenity description*/
	public String getDescription(){
		return description;
	}
	
	/*@param amenity price is set(double)*/
	public void setPrice(double p){
		price = p;
		db.updateAmenity(this);
	}
	
	/*@return amenity price*/
	public double getPrice(){
		return price;
	}
}
