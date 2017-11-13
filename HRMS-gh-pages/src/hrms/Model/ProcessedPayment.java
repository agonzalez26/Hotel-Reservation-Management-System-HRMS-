package hrms.Model;

public class ProcessedPayment {
	private String guestFirstName, guestLastName, reservedDatePeriod;
	private double totalPayment;
	private int id;
	
	public ProcessedPayment(String gfn, String gln, String rdp, double tp) {				//new payment
		guestFirstName = gfn;
		guestLastName = gln;
		reservedDatePeriod = rdp;
		totalPayment = tp;
	}
	public ProcessedPayment(int i, String gfn, String gln, String rdp, double tp) {			//initialization method
		id = i;
		guestFirstName = gfn;
		guestLastName = gln;
		reservedDatePeriod = rdp;
		totalPayment = tp;
	}
	public void setId(int i) {
		id = i;
	}
	public int getId() {
		return id;
	}
	public String getGuestFirstName() {
		return guestFirstName;
	}
	public String getGuestLastName() {
		return guestLastName;
	}
	public String getReservedDatePeriod() {
		return reservedDatePeriod;
	}
	public double getTotalPayment() {
		return totalPayment;
	}
	public String toString() {
		return "id: " + id + "\tFirst Name: " + guestFirstName + "\tLast Name: " +
				guestLastName + "\tDate Stayed: " + reservedDatePeriod +
				"\tAmount Paid: " + totalPayment + "\n";
	}
}