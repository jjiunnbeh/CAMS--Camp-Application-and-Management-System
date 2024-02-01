package user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * This class serves as an abstraction that handles the reading of data from Staff.txt Database
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class StaffManager {

	//private Staff staff;
	public static ArrayList<Staff> staffs = new ArrayList<Staff>();
	//when login input here the staff
	private static StaffManager instance;
	
    /**
    * Reads from Staff.txt. Handles any exception caused when reading
    */	
    private StaffManager() {
        try {
            staffs = StaffTextDB.readStaffs("staffs.txt");
        } catch (IOException e) {
            System.out.println("IOException while reading staffs: " + e.getMessage());
            // continuing with an empty list.
            staffs = new ArrayList<>();
        }
    }
    
    
    /**
    * Allow other class to get the Instance of StaffManager
    * @return the instance of the StaffManager
    */
    public static StaffManager getInstance() {
        if (instance == null) {
            instance = new StaffManager();
        }
        return instance;
    }

    
    /**
    * Allow other class to retrieve the List of Staff
    * @return the list of Staff object
    */
    public List<Staff> getStaffs() {
        return staffs;
    }

    
    /**
     * Find if the staff exist by their name
     * @param staffName
     * @return the staff object if found
     */
    public Staff getStaffByName(String staffName) {
        for (Staff staff : staffs) {
            if (staff.getName().equals(staffName)) {
                return staff; // Found the staff, return it
            }
        }
        // If no staff with the given name is found, return null or handle it as
        // needed
        return null;
    }

    /**
    * Calls the StaffTextDB Class' method to save the staff to Txt file
    */
    public void saveStaffsTxt(){
    	try {
    		StaffTextDB.saveStaffs("staffs.txt", staffs);
    	}catch(IOException e) {
    		System.out.println("IOException while saving Staffs " + e.getMessage());
    	}
    }
    
	
}
