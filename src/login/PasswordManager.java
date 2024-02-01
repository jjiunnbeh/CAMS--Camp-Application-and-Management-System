package login;

import java.io.IOException;
import java.util.*;

import user.Staff;
import user.StaffTextDB;
import user.Student;
import user.StudentTextDB;
import user.User;


/**
 * Handles the changing of password.
 * Displays the interface for user to change password
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class PasswordManager implements PasswordManagerInterface{
	private String currPassword;

	

	/**
	 * PasswordManager constructor
	 * @param user the user object 
	 */
	public PasswordManager(User user) {
		this.currPassword = user.getPassword();
	}

	
	/**
	 * used to check if the old password of the user matches
	 * @param input the old password entered by the user
	 * @return boolean of the password validation outcome
	 */
	public boolean checkPassword(String input) {
		return currPassword.equals(input);
	}

	
	/**
	 * Display the interface that ask user for old and new password
	 * Saves the new password to database if password successfully changed
	 * @param user the user that wants to change password
	 */
	public void changePassword(User user) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter your current password: ");
		String currInput = sc.nextLine();
		System.out.println("Please enter the new password: ");
		String newPassword = sc.nextLine();
		if (checkPassword(currInput) == true) {
			user.setPassword(newPassword);

			try {
				List<? extends User> userList;

				if (user instanceof Student) {
					userList = StudentTextDB.readStudents("students.txt");
				} else if (user instanceof Staff) {
					userList = StaffTextDB.readStaffs("staffs.txt");
				} else {
					throw new IllegalArgumentException("Unsupported user type");
				}

				for (User u : userList) {
					if (u.getID().equals(user.getID())) {
						u.setPassword(newPassword);
						System.out.println("Password sucessfully changed.");
						System.out.println("");
						break;
					}

				}

				// Update databases
				if (user instanceof Student) {
					StudentTextDB.saveStudents("students.txt", (List<Student>) userList);
				} else {
					StaffTextDB.saveStaffs("staffs.txt", (List<Staff>) userList);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("Incorrect password, please try again.......");
			System.out.println("");
		}
	}

}
