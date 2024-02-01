package user;

/**
 * Represent a staff in NTU
 * The Staff class inherited from User Class
 * contains the attributes of the staff
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class Staff extends User{
	
	 /**
	 * the Staff Constructor 
	 * @param ID of the staff, retrieved from their email before the @ symbol
	 * @param name of the Staff
	 * @param faculty the Faculty of the Staff
	 * @param password of the Staff
	 */
	public Staff(String ID,String name ,String faculty, String password){
	    super(ID,name,faculty, password);
}
}
