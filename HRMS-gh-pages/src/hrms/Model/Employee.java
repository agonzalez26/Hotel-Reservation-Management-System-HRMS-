package hrms.Model;

import java.util.List;
import java.sql.Connection;

/*Employee object has additional functionalities for the HRMS system.*/

public class Employee extends Operator{
	private int id; /*employee id*/
	private String password, firstName, lastName;
	private boolean managerFlag; /*determines if a general employee or manager*/
	//private List<Room> roomsList; /*list of rooms at the hotel*/
	//private List<Amenity> amenitiesList; /*list of amenities at the hotel*/
	//private List<Reservation> reservationsList; /*list of reservations at hotel*/
	private Database db;
	Connection c;
	
	public Employee(Connection con, String fn, String ln,
				String pw, boolean mf) {		//constructor for new employee
		c = con;
		db = new Database(c);
		firstName = fn;
		lastName = ln;
		password = pw;
		managerFlag = mf;
		id = db.generateEmployeeId();
	}
	public Employee(Connection con, int i, String fn, String ln,
				String p, boolean mf) {		//constructor for employee initialization
		c = con;
		db = new Database(c);
		id=i;
		firstName = fn;
		lastName = ln;
		password = p;
		managerFlag = mf;
	}
	public boolean matchPassword(String pw) {
		if(pw.equals(password)) {
			return true;
		}
		return false;
	}
	public String getPassword() {
		return password;
	}
	public int getId(){
		return id;
	}
	public boolean getManagerFlag() {
		return managerFlag;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setFirstName(String s) {
		firstName = s;
		db.updateEmployee(this);
	}
	public void setLastName(String s) {
		lastName = s;
		db.updateEmployee(this);
	}
	public void setPassword(String s) {
		password = s;
		db.updateEmployee(this);
	}
	public void setManagerFlag(Boolean b) {
		managerFlag = b;
		db.updateEmployee(this);
	}
}
