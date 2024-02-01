package user;


/**
 * Represent a User of the CAMs app
 * contains the attributes, mutator and accessor of a user
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public abstract class User {
    private String ID;
    private String name;
    private String password;
    private String faculty;

    
    /**
    * 
 	* the User Constructor 
 	* @param ID of the User, retrieved from their email before the @ symbol
 	* @param name of the User
 	* @param faculty the Faculty of the User
 	* @param password of the User
 	*/
    protected User(String ID, String name, String faculty, String password) {
        this.ID = ID;
        this.name = name;
        this.faculty = faculty;
        this.password = password;
    }

    
    /**
    * Retrieve the ID of the user
    * @return the ID of the user
    */
    public String getID() {
        return ID;
    }

    /**
    * Retrieve the name of the user
	* @return the name of the user
	*/
    public String getName() {
        return name;
    }

    
    /**
     * Retrieve the faculty of the user
     * @return the faculty of the user
     */
    public String getFaculty() {
        return faculty;
    }

    
    /**
     * Retrieve the password of the user
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    
    /**
     * Set the password of the user
     * @param password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

}