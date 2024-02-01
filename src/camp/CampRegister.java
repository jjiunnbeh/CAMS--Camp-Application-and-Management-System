package camp;

import utils.InputValidator;
import utils.OperationResult;
import user.Student;
import user.StudentManager;
import enquiry.EnquiryView;
import suggestion.SuggestionView;
import login.*;

/**
 * Represents a register interface for students to interact with camps.
 * This class allows students to view and interact with camps, such as joining
 * and quitting camps, as well as changing filters.
 * 
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class CampRegister {
    private Student student;
    private CampManager campManager;
    private CampFilter campFilter;
    private InputValidator validator;

    private final Runnable[] instructList = {
            () -> {
                campManager.saveCamps();
                validator.closeScanner();
            },
            this::displayAvailableCamps,
            this::displayMyCamps,
            this::campDetailView,
            this::joinCamp,
            this::quitCamp,
            () -> campFilter.changeFilter(validator, student),
            () -> EnquiryView.displayStudentMenu(student),
            () -> SuggestionView.displayStudentMenu(student),
            () -> {
                PasswordManagerInterface passwordmanager = new PasswordManager(student);
                passwordmanager.changePassword(student);
            },
            this::generateCommitReport

    };

    /**
     * Initializes a new CampRegister with the given student.
     *
     * @param student The student using the CampRegister.
     */
    public CampRegister(Student student) {
        this.student = student;
        campManager = CampManager.getInstance();
        campFilter = new CampFilter();
        validator = new InputValidator();
    }

    /**
     * Displays the main menu of the CampRegister and handles user input for various
     * operations.
     * View Camps
     * Join and quit camp
     * Change Filter
     * Generate report
     * Link to other menus
     */
    public void displayMainMenu() {
        while (true) {
            System.out.println("=====================================================");
            System.out.println("      Welcome to NTU Camp Management System (CAMs)");
            System.out.println("               Student Interface - Main Menu");
            System.out.println("               Filter by " + campFilter.getFilterName());
            System.out.println("=====================================================");
            System.out.println("Please select an option:");
            System.out.println("0. Quit");
            System.out.println("1. View available camp");
            System.out.println("2. View My Camps");
            System.out.println("3. View Details of camp");
            System.out.println("4. Register for camp");
            System.out.println("5. Unregister for camp");
            System.out.println("6. Change Filter");
            System.out.println("7. Access Enquiry Menu");
            System.out.println("8. Access Suggestion Menu");
            System.out.println("9. Change Password");
            System.out.println("================ FOR COMMITTEE MEMBER =================");
            System.out.println("10. Generate Report");
            System.out.println("Enter option number:");
            int choice = validator.promptIntInRange(0, 10);
            if (choice == 10 && !student.getIsCommittee()) {
                System.out.println("You are not a committee member.");
                continue;
            }

            instructList[choice].run();
            if (choice == 0)
                return;

        }
    }

    private void displayAvailableCamps() {
        CampView.printCampList(campManager.getCampList(campFilter.availableToJoin(student)), student.getID());
    }

    private void displayMyCamps() {
        CampView.printCampList(campManager.getCampList(campFilter.availableToQuit(student)), student.getID());
    }

    private void campDetailView() {
        System.out.println("Please enter the camp you wish to view details");
        Camp camp = campManager.getCamp(validator.promptString("Please enter a valid name"),
                campFilter.availableToJoin(student).or(campFilter.availableToQuit(student)));
        if (camp == null) {
            System.out.println("No camp of given name");
        } else {
            CampView.printCampDetails(camp);
        }
    }

    private void joinCamp() {
        StudentManager studentmanager = StudentManager.getInstance();
        System.out.println("Please enter the camp to join");
        String campName = validator.promptString("Please enter a valid name");
        System.out.println("Please enter C for commit ot M for member");
        boolean asCommit = validator.promptBoolean("Please enter C for commit or M for member", "C", "M");
        OperationResult result = campManager.joinCamp(campName, student.getID(), asCommit, student.getIsCommittee(),
                campFilter.availableToJoin(student));
        if (!result.isSuccess() && !student.getCommitCamp().equals("NO_CAMP")) {
            System.out.println("Failed to enter " + campName + " !\n" + result.getMessage());
        } else {
            if (result.isSuccess()) {
                System.out.println("You had successfully entered " + campName);
            } else {
                System.out.println("Failed to enter " + campName + " !\n" + result.getMessage());
            }
            if (result.isSuccess() && asCommit && !student.getIsCommittee()
                    && student.getCommitCamp().equals("NO_CAMP")) {
                student.setCommitCamp(campManager.findCamp(camp -> camp.hasCommitMember(student.getID())).getName());
                studentmanager.save();
            }

        }

    }

    private void quitCamp() {
        System.out.println("Please enter the camp to quit");
        Camp camp = campManager.getCamp(validator.promptString("Please enter a valid name"),
                campFilter.availableToQuit(student));
        OperationResult result;
        if (camp == null) {
            result = OperationResult.failure("No camp of given name");
        } else {
            result = camp.removeStudent(student.getID());
        }
        if (result.isSuccess()) {
            System.out.println("You had successfully quit " + camp.getName());
        } else {
            System.out.println("Failed to quit " + camp.getName() + " !\n" + result.getMessage());
        }
    }

    private void generateCommitReport() {
        if (student.getIsCommittee()) {
            Camp camp = campManager.getCamp(student.getCommitCamp());
            if (camp == null)
                return;
            System.out.println("Please enter the filter you wish to apply");
            System.out.println("1. For All student");
            System.out.println("2. For Members only");
            System.out.println("3. For Committee only");
            int choice = validator.promptIntInRange(1, 3);
            switch (choice) {
                case 1:
                    CampReportGenerator.writeStudentListToFile(camp, CampReportGenerator.WriteOption.BOTH);
                    break;
                case 2:
                    CampReportGenerator.writeStudentListToFile(camp, CampReportGenerator.WriteOption.ONLY_ATTENDEE);
                    break;
                case 3:
                    CampReportGenerator.writeStudentListToFile(camp, CampReportGenerator.WriteOption.ONLY_COMMITTEE);
                    break;
                default:
                    System.out.println("UNEXPECTED OUTCOME!!!");
                    return;
            }
            System.out.println("Report successfully generated in "
                    + CampReportGenerator.FILE_PATH + "/" + camp.getName() + "_StudentList.txt");

        } else {
            System.out.println("You are not a commit member of any camp");
        }
    }
}
