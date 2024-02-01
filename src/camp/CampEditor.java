package camp;

import utils.InputValidator;
import utils.OperationResult;
import enquiry.*;
import suggestion.SuggestionView;
import user.*;
import login.*;

/**
 * Represents an editor interface for managing camps.
 * This class allows staff members to perform various operations on camps,
 * including creating, editing, and removing camps, as well as generating
 * reports.
 * 
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class CampEditor {
    private CampManager campManager;
    private Staff staff;
    private CampFilter campFilter;
    private InputValidator validator;

    private final Runnable[] instructList = {
            () -> {
                campManager.saveCamps();
                validator.closeScanner();
            },
            () -> CampView.printCampList(campManager.getCampList(campFilter.getFilter())),
            this::campDetailView,
            this::createCampView,
            this::removeCampView,
            this::editCampView,
            this::setVisibilityOfCamp,
            () -> campFilter.changeFilter(validator, staff),
            this::generateReport,
            () -> CommitteeReportGenerator.writeCommitReport(StudentManager.getInstance().getStudents()),

            () -> EnquiryView.displayStaffMenu(staff),
            () -> SuggestionView.displayStaffMenu(staff),
            () -> {
                PasswordManagerInterface passwordmanager = new PasswordManager(staff);
                passwordmanager.changePassword(staff);
            }
    };

    /**
     * Initializes a new CampEditor with the given staff member.
     *
     * @param staff The staff member using the CampEditor.
     */
    public CampEditor(Staff staff) {
        this.campManager = CampManager.getInstance();
        this.staff = staff;
        campFilter = new CampFilter();
        validator = new InputValidator();
    }

    /**
     * Displays the main menu of the Staff
     * CRUD of camps
     * Change camp filter
     * Generate report
     * Link to other menus
     */
    public void displayMainMenu() {
        while (true) {
            System.out.println("=====================================================");
            System.out.println("      Welcome to NTU Camp Management System (CAMs)");
            System.out.println("                Staff Interface - Main Menu");
            System.out.println("                Filtered by - " + campFilter.getFilterName());
            System.out.println("=====================================================");
            System.out.println("Please select an option:");
            System.out.println("0. Save and Quit");
            System.out.println("1. View camps");
            System.out.println("2. View Detail of Camp");
            System.out.println("3. Create a New Camp");
            System.out.println("4. Remove Camp");
            System.out.println("5. Edit Camp");
            System.out.println("6. Set visibility of camp");
            System.out.println("7. Change Filter");
            System.out.println("8. Generate Student Reports");
            System.out.println("9. Generate Performance Reports");
            System.out.println("10. Access Enquiry Menu");
            System.out.println("11. Access Suggestion Menu");
            System.out.println("12. Change Password");
            System.out.print("Enter option number:");
            int choice = validator.promptIntInRange(0, 12);
            instructList[choice].run();
            if (choice == 0)
                return;

        }
    }

    private Camp selectCamp(String message) {
        System.out.println("Please enter a camp name you wish to " + message);
        Camp camp = campManager.getCamp(validator.promptString("Please enter a valid name"));
        if (camp == null) {
            System.out.println("No camp of given name");
            return null;
        } else {
            return camp;
        }
    }

    private void campDetailView() {
        Camp camp = selectCamp("view");
        if (camp != null) {
            CampView.printCampDetails(camp);
        }
    }

    private void createCampView() {
        Camp newCamp = CampView.createCamp(validator, staff.getID());
        OperationResult result = campManager.addCamp(newCamp);
        if (result.isSuccess()) {
            System.out.println("Camp created successfully");
        } else {
            System.out.println("Failed to create Camp\n" + result.getMessage());
        }
    }

    private void removeCampView() {
        System.out.println("Please enter the camp name you wish to remove");
        String campName = validator.promptString("Please enter a valid name");
        OperationResult result = campManager.removeCamp(campName);
        if (result.isSuccess()) {
            System.out.println("Camp removed successfully");
        } else {
            System.out.println("Failed to remove Camp\n" + result.getMessage());
        }
    }

    private void setVisibilityOfCamp() {
        Camp camp = selectCamp("edit");
        if (camp != null) {
            System.out.println("Please enter T for visible, F for invisible");
            boolean visibility = validator.promptBoolean("Please enter T or F", "T", "F");
            camp.setVisible(visibility);
        }

    }

    private void editCampView() {
        Camp camp = selectCamp("edit");
        if (camp != null) {
            CampView.updateCamp(validator, camp);
        }
    }

    private void generateReport() {
        Camp camp = selectCamp("generate report");
        if (camp != null) {
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
        }
    }
}
