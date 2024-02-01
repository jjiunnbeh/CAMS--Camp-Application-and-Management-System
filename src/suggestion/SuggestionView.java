package suggestion;

import java.util.InputMismatchException;
import java.util.Scanner;
import camp.*;

import user.Staff;
import user.Student;
import user.StudentManager;

/**
 * Display the suggestion menu for Student and Staff
 * Uses polymorphism where the the Student/Staff is passed into the user
 * parameter
 * based on the object passed decide if the user is a staff or student
 * call the corresponding menu for the different type of user
 * 
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class SuggestionView {

	/**
	 * Display the menu for student
	 * 
	 * @param student the student object that logged in
	 */
	public static void displayStudentMenu(Student student) {
		if (student.getIsCommittee() && !student.getCommitCamp().equals("NO_CAMP")) {
			Scanner sc = new Scanner(System.in);
			int choice;
			boolean run = true;
			String content = null;
			StudentManager studentMgr = StudentManager.getInstance();
			SuggestionManager suggestionMgr = SuggestionManager.getInstance();

			do {

				System.out.println("\n============SUGGESTIONS===============");
				System.out.println("(1) Create Suggestion");
				System.out.println("(2) View Suggestions for your Camp");
				System.out.println("(3) Edit your Suggestion");
				System.out.println("(4) Delete your Suggestion");
				System.out.println("(5) Submit your Suggestion");
				System.out.println("(6) Return");
				System.out.println("=======================================");
				System.out.print("Select option");

				try {
					choice = sc.nextInt();
					if (!(choice >= 1 && choice <= 6)) {
						System.out.println("Input must be an integer from 1-6!");
					}
					switch (choice) {
						case 1: // Create
							System.out.println("Enter Suggestion:");
							sc.nextLine();
							content = sc.nextLine();
							break;
						case 2: // View
							suggestionMgr.listCampSuggestionsByIndex(student.getCommitCamp());

							break;
						case 3: // edit
							if (content == null) {
								System.out.println("Suggestion not created yet");
								break;
							}
							System.out.println("Current Suggestion: " + content);
							System.out.println("Enter new Suggestion:");
							sc.nextLine();
							content = sc.nextLine();
							break;
						case 4: // delete
							if (content == null) {
								System.out.println("Suggestion not created yet");
								break;
							}
							System.out.println("Suggestion deleted");
							content = null;
							break;

						case 5:// submit
							suggestionMgr.addSuggestion(student.getCommitCamp(), student, content);
							studentMgr.addPoint(student);
							System.out.println("Suggestion submitted");
							break;
						case 6: // Exit
							System.out.println("Returning to main menu...");
							sc.nextLine();
							return;
					}
				} catch (InputMismatchException e) {
					System.out.println("Input must be an integer!");
					sc.nextLine();
					continue;
				}

			} while (run);

			sc.close();

		} else
			System.out.println(
					"You are not part of the Committee for any camp, thus you are unable to submit Suggestions");

	}

	/**
	 * Display the menu for staff
	 * 
	 * @param staff ,the staff object that logged in
	 */
	public static void displayStaffMenu(Staff staff) {
		Scanner sc = new Scanner(System.in);
		int choice;
		int index;
		Suggestion selectedSuggestion;
		String selectedCampName;
		Student student;
		boolean run = true;
		StudentManager studentMgr = StudentManager.getInstance();
		SuggestionManager suggestionMgr = SuggestionManager.getInstance();
		CampManager campMgr = CampManager.getInstance();
		do {

			System.out.println("\n============SUGGESTIONS===============");
			System.out.println("(1) View Suggestions for a Camp");
			System.out.println("(2) Approve Suggestion");
			System.out.println("(3) Reject Suggestion");
			System.out.println("(4) Return");
			System.out.println("=======================================");
			System.out.print("Select option");

			try {
				choice = sc.nextInt();
				if (!(choice >= 1 && choice <= 4)) {
					System.out.println("Input must be an integer from 1-4!");
				}
				switch (choice) {
					case 1: // View
						System.out.println("Enter name of Camp:");
						sc.nextLine();
						selectedCampName = sc.nextLine();

						// include a check if camp exists:
						if (!campMgr.hasCamp(selectedCampName)) {
							System.out.println("no camp of this name exists");
							break;
						}
						suggestionMgr.listCampSuggestionsByIndex(selectedCampName);

						break;
					case 2: // Approve = add point + remove
						System.out.println("Enter index of Suggestion for approval: ");
						sc.nextLine();
						index = sc.nextInt();
						selectedSuggestion = suggestionMgr.getSuggestionByIndex(index);
						System.out.println(selectedSuggestion.getMadeBy());
						student = studentMgr.getStudentByName(selectedSuggestion.getMadeBy());
						studentMgr.addPoint(student);
						suggestionMgr.removeSuggestion(selectedSuggestion);
						break;
					case 3: // Reject = remove
						System.out.println("Enter index of Suggestion for rejection: ");
						sc.nextLine();
						index = sc.nextInt();
						selectedSuggestion = suggestionMgr.getSuggestionByIndex(index);
						suggestionMgr.removeSuggestion(selectedSuggestion);
						break;
					case 4: // Exit
						System.out.println("Returning to main menu...");
						sc.nextLine();
						return;

				}
			} catch (IndexOutOfBoundsException e) {
				System.out.println(
						"Invalid index, please view the suggestions and get the correct index.");

				continue;
			} catch (InputMismatchException e) {
				System.out.println("Input must be an integer!");
				sc.nextLine();
				continue;
			}

		} while (run);

		sc.close();

	}

}
