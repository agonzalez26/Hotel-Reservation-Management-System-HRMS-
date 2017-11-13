package hrms.Model;

/* An Operator is anyone using the Hotel Reservation Management System(HRMS).An Operator can be a guest, employee or manager.*/

public class Operator {
	  private int guestCount; /*number of guests for reservation*/
	  private int dayCount; /*number of days guest are staying for reservation*/
	  private int roomCount; /*number of rooms for reservation*/

    /* Checkin to the system using the confirmation number which was
    * given after the reservation.
    * @pre Confirmation number exists in database
    * @post Confirmation number is verified
    */
    private void checkInReservation(int ConfirmationNumber){}

    /*  removes a completed reservation from the database
    * @pre confirmation number exists in the database
    * @post reservation is removed from the database
    */
    private void checkOutReservation(int ConfirmationNumber){}

    /* creats and books a new reservation in the database
    * @pre Guest doesn't have a current reservation
    * @post a new reservation is added to the database
    */
    private void booksReservation(){}

    /* Returns the current bill in the database for specific reservation.
    @pre existing reservations
    @post returns bill for reservation
    */

    /* Updates first name address in the database
    * @pre currently checked in to the reservation
    * @post guest first name is updated in the database
    */
    public void updateGuestFirstName(String firstName){}

    /* Updates Operator last name in the database
    * @pre currently checked in to the reservation
    * @post guest last name is updated in the database
    */
    public void updateGuestLastName(String lastName){}

    /* Updates Operator address in the database
    * @pre currently checked in to the reservation
    * @post  guest address is updated in the database
    */
    public void updateAddress(String address){}

    /* Updates Operator email in the database
    * @pre currently checked in to the reservation
    * @post guest email is updated in the database
    */
    public void updateEmail(String email){}

    /* Updates Operator phone number in the database
    * @pre currently checked in to the reservation
    * @post phone number is updated in the database
    */
    public void updatePhoneNumber(int number){}

    /*Cancels session of check in process
     * @pre: in session
     * @post: session terminated*/
    private void cancelCheckinSession(int ConfirmationNumber){}
    
    //Where do I place these?
    /*@param guest count is set (int)*/
    public void setGuestCount(Reservation r, int gcount){}
  
    /* @return guest count*/
    public int getGuestCount(){
        return guestCount;
    }
  
    /*@param day count is set(int)
     * @pre: dayCount > 1*/
    public void setDayCount(Reservation r, int dcount){}
  
    /*@return day count*/
    public int getDayCount(){
        return dayCount;
    }
  
    /*@param room count is set (int)
     * @pre: roomCount > 1*/
    public void setRoomCount(Reservation r, int rcount){}
  
    /*@return room count*/
    public int getRoomCount(){
        return roomCount;
    }
}
