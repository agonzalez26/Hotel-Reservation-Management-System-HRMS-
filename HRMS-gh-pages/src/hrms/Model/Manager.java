package hrms.Model;

import java.util.List;

/*A Manager is an hotel employee with extra privileges. The manager has access to all functionalities an employee has and more. */

public class Manager /*extends Employee*/{
	private double hotelIncome; /*income of hotel*/
	private List<Employee> employeeList; /*list of employees*/

    /* Changes the price of a paticular hotel room.
    * @pre Room exists in the database
    * @post Room price changed
    */
    public void UpdateRoomPrice(Room r){}

    /* Creates a new hotel amenity in the database.
    * @pre amenity currently doesn't exist in the database
    * @post amenity is added to the database
    */
    public void createAmenity(){}

    /* Deletes an hotel amenity from the database.
    * @pre amenity exists in the database
    * @post amenity is deleted from the database
    */
    public void deleteAmenity(Amenity a){}

    /* Updates the price of an hotel amenity
    * @pre amenity exists in the database
    * @post amenity's price and/or description is updated
    */
    public void updateAmenity(Amenity a){}

    /* Creates a new hotel employee in  the database
    * @pre employee currently does not exist in the database
    * @post employee is added to the database
    */
    public void createEmployee(){}

    /* Deletes an hotel employee from the database
    * @pre employee exists in the database
    * @post employee is removed from the database
    */
    public void deleteEmployee(Employee e){}

    /* Updates employee's name and/or password
    * @pre employee exists in the database
    * @post employee's information is changed
    */
    public void updateEmployee(Employee e){
    
    }

    /*@return list of employees*/
    public List<Employee> getEmployeeList(){
        return employeeList;
    }
    
    /*@param hotel income is set (double)*/
    public void setHotelIncome(double income){}
    
    /*@return hotel income*/
    public double getHotelIncome(){
        return hotelIncome;
    } 

}

