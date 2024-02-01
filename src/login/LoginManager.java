package login;

import java.util.*;

import user.Staff;
import user.Student;
import user.User;

/**
 * Handles the login feature of a user.
 * Displays the menu for user to login as a staff ot student
 * 
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class LoginManager {
	private List<Student> studentList;
	private List<Staff> staffList;
	private int loginchoice = 0;// login portal
	private boolean userFound;// User id and password matched
	private String userId;// user id that user input
	private User user;

	/**
	 * Constructor for LoginManager
	 * 
	 * @param studentList the list of student objects
	 * @param staffList   the list of staff objects
	 */
	public LoginManager(List<Student> studentList, List<Staff> staffList) {
		this.studentList = studentList;
		this.staffList = staffList;
	}

	/**
	 * Display the login menu and verify if the user's menu choice is valid
	 * 
	 * @return the user object after successful login
	 */
	// login will return the user(Student user or Staff user)
	public User login() {
		Scanner sc = new Scanner(System.in);

		do {

			System.out.println("Please select a log in option : ");
			System.out.println("============ Login Options ===============");
			System.out.println("(1) Student");
			System.out.println("(2) Staff");
			System.out.println("Key in (1) to log in as student, (2) to log in as staff...........");

			if (sc.hasNextInt()) {
				loginchoice = sc.nextInt();
				if (loginchoice != 1 && loginchoice != 2) {
					System.out.println(
							"Invalid input. Please key in (1) to log in as student, (2) to log in as staff..........");
					sc.nextLine();
				}

			} else {
				System.out.println(
						"Invalid input. Please key in (1) to log in as student, (2) to log in as staff..........");
				sc.nextLine();
			}

		} while (loginchoice != 1 && loginchoice != 2);

		// Student login portal
		if (loginchoice == 1) {
			boolean userFound = false;
			do {
				System.out.println("============ Student Login Portal ===============");
				userFound = searchUserList(studentList);
			} while (!userFound);
			return (Student) this.user;

		}
		// Staff login portal
		else {
			boolean userFound = false;
			do {
				System.out.println("============ Staff Login Portal ===============");
				userFound = searchUserList(staffList);
			} while (!userFound);
			return (Staff) this.user;

		}

	}

	/**
	 * Search for the user based on the id entered
	 * verify if the password matches for the user ID
	 * 
	 * @param userList the type of user to verify, a staff or student
	 * @return output of login. if successful or not
	 */
	public boolean searchUserList(List<? extends User> userList) {
		Scanner sc1 = new Scanner(System.in);

		System.out.println("Enter your User ID: ");
		userId = sc1.nextLine().toUpperCase();
		System.out.println("Enter your password: ");
		String password = sc1.nextLine();

		for (User user : userList) {
			if (user.getID().equals(userId)) {
				PasswordManagerInterface passwordmanager = new PasswordManager(user);
				boolean valid = passwordmanager.checkPassword(password);// Check password
				if (!valid)// Wrong password
				{
					break;
				}
				System.out.printf("Login sucessful\n");
				System.out.printf("Welcome to CAMs, %s\n", user.getName());
				System.out.println("");

				this.user = user;
				if (user instanceof Student && !((Student) user).getIsCommittee()
						&& !((Student) user).getCommitCamp().equals("NO_CAMP")) {
					System.out.println("You are a camp attendee.");
				} else if (user instanceof Student && ((Student) user).getIsCommittee()) {
					System.out.println("You are a camp committee.");
				}
				return true;

			}
		}
		System.out.println("Incorrect User ID or password, please try again..........");
		System.out.println("");

		return false;

	}

}
