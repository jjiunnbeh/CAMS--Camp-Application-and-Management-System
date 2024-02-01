package login;

import user.User;


/**
 * The PasswordManager Interface used as an abstraction to passwordManager
 * Implemented by PasswordManager.
 * To cater to future upgrades such as new login method via pin number or other forms of password
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public interface PasswordManagerInterface {
	
		/**
	 	* abstract method for checking password
	 	* @param input to be verified
	 	* @return if successful or failed
	 	*/
	    public boolean checkPassword(String input);
	    
	    /**
	     * abstract method for changing password
	     * @param user the user that wants to change password
	     */
	    public void changePassword(User user);
	

}
