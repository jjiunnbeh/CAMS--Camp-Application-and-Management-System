package enquiry;

import java.util.InputMismatchException;
import java.util.Scanner;

import user.Staff;
import user.Student;
import user.StudentManager;
import camp.CampManager;

/**
 * Display the enquiry menu for Student and Staff
 * Uses polymorphism where the the Student/Staff is passed into the user
 * parameter
 * based on the object passed decide if the user is a staff or student
 * call the corresponding menu for the different type of user
 * 
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class EnquiryView {

    /**
     * Uses polymorphism where the the Student/Staff is passed into the user
     * parameter
     * based on the object passed decide if the user is a staff or student
     * call the corresponding menu for the different type of user
     * 
     * @param user the user object passed in after logging in
     */

    public static void displayStudentMenu(Student student) {
        Scanner sc = new Scanner(System.in);
        int choice;
        int index;
        boolean run = true;
        String selectedCampName;
        Enquiry selectedEnquiry;
        String content = null;
        String reply;
        StudentManager studentMgr = StudentManager.getInstance();
        EnquiryManager enquiryMgr = EnquiryManager.getInstance();
        CampManager campMgr = CampManager.getInstance();

        do {
            System.out.println("\n============ENQUIRIES===============");
            System.out.println("(1) Create Enquiry");
            System.out.println("(2) View Enquiries for a Camp");
            System.out.println("(3) Edit your Enquiry");
            System.out.println("(4) Delete your Enquiry");
            System.out.println("(5) Submit your Enquiry");
            System.out.println("(6) Return");
            System.out.println("===========FOR COMMITTEE MEMBERS=========");
            System.out.println("(7) View Unanswered Enquiries for your Camp");
            System.out.println("(8) Reply to an Enquiry for your Camp");
            System.out.println("=======================================");
            System.out.print("Select option");
            try {
                choice = sc.nextInt();
                if (!(choice >= 1 && choice <= 8)) {
                    System.out.println("Input must be an integer from 1-8!");
                }
                switch (choice) {
                    case 1: // Create
                        System.out.println("Enter Enquiry:");
                        sc.nextLine();
                        content = sc.nextLine();
                        break;
                    case 2: // View
                        System.out.println("Enter name of Camp:");
                        sc.nextLine();
                        selectedCampName = sc.nextLine();
                        // include a check if camp exists:
                        if (!campMgr.hasCamp(selectedCampName)) {
                            System.out.println("no camp of this name exists");
                            break;
                        }
                        enquiryMgr.listAllCampEnquiriesByIndex(selectedCampName);
                        break;
                    case 3: // edit
                        if (content == null) {
                            System.out.println("Enquiry not created yet");
                            break;
                        }
                        System.out.println("Current Enquiry: " + content);
                        System.out.println("Enter new Enquiry:");
                        sc.nextLine();
                        content = sc.nextLine();
                        break;
                    case 4: // delete
                        if (content == null) {
                            System.out.println("Enquiry not created yet");
                            break;
                        }
                        System.out.println("Enquiry deleted");
                        sc.nextLine();
                        content = null;
                        break;

                    case 5:// submit
                        System.out.println("Enter name of Camp:");
                        sc.nextLine();
                        selectedCampName = sc.nextLine();
                        // include a check if camp exists:
                        if (!campMgr.hasCamp(selectedCampName)) {
                            System.out.println("no camp of this name exists");

                            break;
                        } else {
                            System.out.println("Enquiry sucessfully submitted.");

                        }
                        enquiryMgr.addEnquiry(selectedCampName, student, content, "NO_REPLY");
                        content = null;
                        break;
                    case 6: // Exit
                        System.out.println("Returning to main menu...");
                        sc.nextLine();
                        return;
                    case 7:
                        if (!student.getIsCommittee() || student.getCommitCamp().equals("NO_CAMP")) {
                            System.out.println("You are not a Commitee Member");
                            break;
                        }
                        System.out.println("Enter name of Camp:");
                        sc.nextLine();
                        selectedCampName = sc.nextLine();
                        // include a check if camp exists:
                        if (!campMgr.hasCamp(selectedCampName)) {
                            System.out.println("no camp of this name exists");
                            break;
                        }
                        enquiryMgr.listUnansweredCampEnquiriesByIndex(selectedCampName);
                        break;

                    case 8:
                        System.out.println(student.getCommitCamp());
                        if (!student.getIsCommittee()) {
                            System.out.println("You are not a Commitee Member");
                            break;
                        }
                        System.out.println("Enter index:");
                        sc.nextLine();
                        index = sc.nextInt();
                        selectedEnquiry = enquiryMgr.getEnquiryByIndex(index);
                        System.out.println("Enquiry: " + selectedEnquiry.getContent());
                        System.out.println("Enter reply:");
                        sc.nextLine();
                        reply = sc.nextLine();
                        enquiryMgr.replyEnquiry(index, reply);
                        studentMgr.addPoint(student);
                        break;
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println(
                        "Invalid index, please view the unanswered enquiries and reply to get the correct index.");

                continue;
            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer!");
                sc.nextLine();
                continue;
            }

        } while (run);

        sc.close();
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
        boolean run = true;
        String selectedCampName;
        Enquiry selectedEnquiry;
        String reply;
        EnquiryManager enquiryMgr = EnquiryManager.getInstance();
        CampManager campMgr = CampManager.getInstance();

        do {
            System.out.println("\n============ENQUIRIES===============");
            System.out.println("(1) View Unanswered Enquiries of a Camp");
            System.out.println("(2) Reply to an Enquiry for your Camp");
            System.out.println("(3) Return");
            System.out.println("=======================================");
            System.out.print("Select option");

            try {
                choice = sc.nextInt();
                if (!(choice >= 1 && choice <= 3)) {
                    System.out.println("Input must be an integer from 1-3!");
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
                        enquiryMgr.listAllCampEnquiriesByIndex(selectedCampName);
                        break;
                    case 2: // Reply

                        System.out.println("Enter index:");
                        sc.nextLine();
                        index = sc.nextInt();
                        selectedEnquiry = enquiryMgr.getEnquiryByIndex(index);
                        System.out.println("Enquiry: " + selectedEnquiry.getContent());
                        System.out.println("Enter reply:");
                        sc.nextLine();
                        reply = sc.nextLine();
                        enquiryMgr.replyEnquiry(index, reply);
                        break;
                    case 3: // Exit
                        System.out.println("Returning to main menu...");
                        sc.nextLine();
                        return;
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println(
                        "Invalid index, please view the unanswered enquiries and reply to get the correct index.");

                continue;
            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer!");
                sc.nextInt();
                continue;
            }

        } while (run);

        sc.close();

    }

}