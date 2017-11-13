/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hrms.Model;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Ejiroghene
 */
public class Application {

    private static boolean emp = true;
    private static String guestCount = "0";
    private static String roomCount = "0";
    private static List<String> chosenRooms = new ArrayList<String>();
    private static String guestFirstName=null;
    private static String guestLastName=null;
    private static String guestPhoneNumber=null;
    private static String guestEmailAddress=null;
    private static String guestAddress=null;
    private static int reservationNumber=0;
    private static HashMap hm = new HashMap();
    private static String startDate=null;
    private static String endDate = null;
    private static Reservation reservation = null;
    private static int dayCount = 1;
    private static double suitePrice;
    private static double singlePrice;
    private static double doublePrice;
    private static double kingPrice;
    private static HashMap amenIds = new HashMap();
    private static ArrayList<Room> hotelRooms;

    public void setReservation(Reservation res){
        this.reservation = res;
    }
    
    public void setHotelRooms(ArrayList<Room> hotelRooms) {
        this.hotelRooms = hotelRooms;
    }

    public ArrayList getHotelRooms(){
        return hotelRooms;
    }
    public  void setAmenIds(HashMap amenIds) {
        this.amenIds = amenIds;
    }
    
    public double getSuitePrice() {
        return suitePrice;
    }

    public double getSinglePrice() {
        return singlePrice;
    }

    public double getKingPrice() {
        return kingPrice;
    }

    public double getDoublePrice() {
        return doublePrice;
    }

    
    public void setEmp(boolean t) {
        emp = t;
    }

    public boolean getEmp() {
        return emp;
    }

    public void setGuestCount(String guestCount) {
        this.guestCount = guestCount;
    }

    public String getGuestCount() {
        return guestCount;
    }

    public void setRoomCount(String roomCount) {
        this.roomCount = roomCount;
    }

    public String getRoomCount() {
        return roomCount;
    }

    public void setChosenRooms(List<String> chosenRooms) {
        this.chosenRooms = chosenRooms;
    }

    public List<String> getChosenRooms() {
        return chosenRooms;
    }
    
    public void setFirstName(String firstName){
        this.guestFirstName=firstName;
    }
    
    public String getFirstName(){
        return guestFirstName;
    }
    
    public void setLastName(String lastName){
        this.guestLastName=lastName;
    }
    
    public String getLastName(){
        return guestLastName;
    }
    
    public void setEmailAddress(String emailAddress){
        this.guestEmailAddress=emailAddress;
    }
    
    public String getEmailAddress(){
        return guestEmailAddress;
    }
    
    public void setPhoneNumber(String phoneNumber){
        this.guestPhoneNumber=phoneNumber;
    }
    
    public String getPhoneNumber(){
        return guestPhoneNumber;
    }
    
    public void setAddress(String address){
        this.guestAddress=address;
    }
    
    public String getAddress(){
        return guestAddress;
    }
    
    public void setReservationNumber(int reservationNumber){
        this.reservationNumber=reservationNumber;
    }
    
    public int getReservationNumber(){
        return reservationNumber;
    }
    
     public void setMap(HashMap hm){
        this.hm = hm;
    }
    
    public HashMap getMap(){
        return hm;
    }
    
    public void setStartDate(String startDate){
        this.startDate=startDate;
    }
    
    public String getStartDate(){
        return startDate;
    }
    
    public void setEndDate(String endDate){
        this.endDate=endDate;
    }
    
    public String getEndDate(){
        return endDate;
    }
    
    public String getRoomString(){
        String roomString = "";
        if(reservation!=null){
            roomString = reservation.getRoomIds();
        }
        for(String s: chosenRooms){
            int ind = s.indexOf("m")+1;
            roomString += s.substring(ind)+" ";
        }
            
//        for(int i=0; i<chosenRooms.size(); i++){
//            String s = chosenRooms.get(i);
//            if(s!=chosenRooms.get(0)){
//                roomString += ", ";
//            }
//            int end = chosenRooms.size()-1;
//            int ind = s.indexOf("m")+1;
//            if(s==chosenRooms.get(end)){
//                roomString += s.substring(ind);
//            }else{
//                roomString += s.substring(ind, ind+3);
//            }
//        }
        return roomString;
    }
    
   public int getAmenityCount(){
        int count = 0;
        for(String s: chosenRooms){
            List<String> selAmens;
            try{
                selAmens = (List<String>) hm.get(s);
                count += selAmens.size();
            }catch(NullPointerException e){
                        
           }
        }
        return count;
    }
    
   public void setDayCount(int dayCount){
       this.dayCount = dayCount;
   }
   
   public int getDayCount(){
       return dayCount;
   }
   
   public int roomCount(){
       int count = 0;
       if(reservation!=null){
            count += reservation.getAmountOfRooms();
       }
       count += chosenRooms.size();
       return count;
   }
   
   public void setPrice(){
       doublePrice = hotelRooms.get(0).getRoomPrice();
       singlePrice = hotelRooms.get(3).getRoomPrice();
       kingPrice = hotelRooms.get(11).getRoomPrice();
       suitePrice = hotelRooms.get(6).getRoomPrice();
   }

   public double totRoomPrice(){
       double price = 0.0;
       if(reservation!=null){//price for room in database
            ArrayList<Room> roomList = reservation.getRoomList();
            for(Room room: roomList){
                price+= room.getRoomPrice();
            }
       }
       
       
       for(String s: chosenRooms){
           int ind = s.indexOf("m")+1;
            s = s.substring(ind);
            int id = parseInt(s);
            for(Room room: hotelRooms){
                if(id==room.getId()){
                    price += room.getRoomPrice();
                    break;
                }
            }
       }
       return price;
   }
   
   public double totGuestPrice(){
       int guest = parseInt(guestCount);
       double price = guest * 5.0;
       return price;
   }
   
   public double totAmenityPrice(){
       double price = 0;
       for(String s: chosenRooms){
            List<String> selAmens;
            try{
                selAmens = (List<String>) hm.get(s);
                for(String amen: selAmens){
                    int ind = amen.indexOf("$")+1;
                    String strPrice = amen.substring(ind);
                    double inPrice = parseDouble(strPrice);
                    price += inPrice;
                }
            }catch(NullPointerException e){
                        
           }
        }
       return price;
   }
   
   public double getBill(){
       return dayCount*totRoomPrice() + totGuestPrice() + totAmenityPrice();
   }
  
   public HashMap getRoomAmens(){
       HashMap roomToAmen = new HashMap();
       
       for(String room: chosenRooms){
           int ind = room.indexOf("m")+1;
           String roomid = room.substring(ind);
           List<String> selAmens;
           List<String> selAmensId = new ArrayList<>();
            try{
                selAmens = (List<String>) hm.get(room);
                for(String amen: selAmens){
                   String amenid = ""+amenIds.get(amen);
                   selAmensId.add(amenid);
                }
            }catch(NullPointerException e){
                
                        
           }
           roomToAmen.put(roomid, selAmensId);
       }
       return roomToAmen;
   }
       
  
}
