package camp;

import utils.InputValidator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * CampView class is responsible for displaying camp-related information and
 * user prompts.
 * It provides methods for printing camp lists, camp details, and
 * creating/updating camps.
 * 
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class CampView {
    private static final int MAX_COMMIT_SLOT = 10;
    private static final int NAME_WIDTH = 20;
    private static final int FACULTY_WIDTH = 10;
    private static final int DATE_WIDTH = 15;
    private static final int SLOTS_WIDTH = 5;
    private static final int STATUS_WIDTH = 10;

    private static final String CAMP_INFO_FORMAT = String.format(
            "%%-%ds %%-%ds %%-%ds %%%ds %%-%ds",
            NAME_WIDTH, FACULTY_WIDTH, DATE_WIDTH, SLOTS_WIDTH, STATUS_WIDTH);

    /**
     * Private constructor to prevent instantiation of CampView.
     */
    private CampView() {
    }

    /**
     * Print the header for the camp list.
     */
    private static void printCampHeader() {
        System.out.format(
                CAMP_INFO_FORMAT + "\n",
                "Name", "Faculty", "Date", "Slots", "Status");
    }

    /**
     * Print brief camp information for a given camp.
     *
     * @param camp The camp to display information for.
     */
    public static void printBriefCampInfo(Camp camp) {
        String visibilityMark = camp.isVisible() ? "Visible" : "Invisible";
        System.out.format(
                CAMP_INFO_FORMAT + "\n",
                camp.getName(),
                camp.getFaculty(),
                camp.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                camp.getRemainingSlots(),
                visibilityMark);
    }

    /**
     * Print brief camp information for a given camp and status of given student in
     * camp.
     *
     * @param camp      The camp to display information for.
     * @param studentID The student ID to check attendance for.
     */
    public static void printBriefCampInfo(Camp camp, String studentID) {
        if (!camp.isVisible()) {
            return;
        }
        String status = camp.hasCommitMember(studentID) ? "is Committee"
                : camp.hasStudent(studentID) ? "is Member" : "not Member";
        System.out.format(
                CAMP_INFO_FORMAT + "\n",
                camp.getName(),
                camp.getFaculty(),
                camp.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                camp.getRemainingSlots(),
                status);
    }

    /**
     * Print a list of camps.
     *
     * @param camps The list of camps to print.
     */
    public static void printCampList(List<Camp> camps) {
        if (camps.isEmpty()) {
            System.out.println("No Camps");
            return;
        }
        printCampHeader();
        for (Camp camp : camps) {
            printBriefCampInfo(camp);
        }
    }

    /**
     * Print a list of camps and check attendance for a specific student.
     *
     * @param camps     The list of camps to print.
     * @param studentID The student ID to check attendance for.
     */
    public static void printCampList(List<Camp> camps, String studentID) {
        if (camps.isEmpty()) {
            System.out.println("No Camps");
            return;
        }
        printCampHeader();
        for (Camp camp : camps) {
            printBriefCampInfo(camp, studentID);
        }
    }

    /**
     * Print detailed information for a given camp.
     *
     * @param camp The camp to display detailed information for.
     */
    public static void printCampDetails(Camp camp) {
        System.out.println("=====================================================");
        System.out.println("         " + camp.getName() + " Details");
        System.out.println("=====================================================");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("Date: " +
                (camp.getDate() != null ? camp.getDate().format(formatter) : "N/A"));
        System.out.println("Registration Deadline: " +
                (camp.getClosureDate() != null ? camp.getClosureDate().format(formatter) : "N/A"));
        System.out.println("Faculty: " + (camp.getFaculty().equals("NTU") ? "All NTU students" : camp.getFaculty()));
        System.out.println("Location: " + camp.getLocation());
        System.out.println(
                "Remaining slots: " + camp.getRemainingSlots() + (camp.getRemainingSlots() <= 0 ? " (Full)" : ""));
        System.out.println("Remaining committee slots: " + camp.getRemainingCommitSlots()
                + (camp.getRemainingCommitSlots() <= 0 ? " (Full)" : ""));
        System.out.println("Description: " + camp.getDescription());
        System.out.println("Camp Coordinator: " + camp.getCreator());
    }

    /**
     * Create a new camp with user input.
     *
     * @param validator The input validator to use for user prompts.
     * @param staffID   The ID of the staff creating the camp.
     * @return A new Camp object created based on user input.
     */
    public static Camp createCamp(InputValidator validator, String staffID) {
        System.out.println("Creating a new Camp...");

        System.out.println("Please enter the Camp name:");
        String name = validator.promptString("Please enter a valid name");

        System.out.println("Please enter the camp date (YYYY-MM-DD):");
        LocalDate campDate = validator.promptDate("Please enter a valid date", "yyyy-MM-dd");

        System.out.println("Please enter the registration closing date (YYYY-MM-DD):");
        LocalDate closureDate = validator.promptDate("Please enter a valid date", "yyyy-MM-dd");

        while (!closureDate.isBefore(campDate)) {
            System.out.println("Closure date must be before the camp date");
            System.out.println("Please choose one of them to change, T for camp date, F for closure date");
            boolean choice = validator.promptBoolean("Please enter T/F", "T", "F");
            if (choice) {
                System.out.println("Please enter the camp date (YYYY-MM-DD):");
                campDate = validator.promptDate("Please enter a valid date", "yyyy-MM-dd");
            } else {
                System.out.println("Please enter the registration closing date (YYYY-MM-DD):");
                closureDate = validator.promptDate("Please enter a valid date", "yyyy-MM-dd");
            }
        }

        System.out.println("Please enter the faculty:");
        String faculty = validator.promptString("Please enter a valid faculty").toUpperCase();

        System.out.println("Please enter the location:");
        String location = validator.promptString("Please enter a valid location");

        System.out.println("Please enter the total number of slots:");
        int totalSlots = validator.promptInt();

        System.out.println("Please enter the number of committee slots:");
        int commitSlots = validator.promptIntInRange(1, MAX_COMMIT_SLOT);

        System.out.println("Please enter the description:");
        String description = validator.promptString("Please enter a valid description");

        return new Camp(
                name,
                campDate,
                closureDate,
                faculty,
                location,
                totalSlots,
                commitSlots,
                description,
                staffID);
    }

    /**
     * Update a camp's details with user input.
     *
     * @param validator The input validator to use for user prompts.
     * @param camp      The camp to update.
     */
    public static void updateCamp(InputValidator validator, Camp camp) {
        printCampDetails(camp);
        int optionLimit = camp.hasNoStudent() ? 8 : 5;
        while (true) {
            System.out.println("Please enter what you wish to change:");
            System.out.println("0. Quit");
            System.out.println("1. Camp name");
            System.out.println("2. Camp date");
            System.out.println("3. Registration closing date");
            System.out.println("4. Camp location");
            System.out.println("5. Camp description");
            System.out.println("6. Camp faculty");
            System.out.println("7. Number of slots");
            System.out.println("8. Number of committee slots");
            int choice = validator.promptIntInRange(0, optionLimit);
            switch (choice) {
                case 0:
                    return;
                case 1:
                    System.out.println("Please enter the new camp name:");
                    String newName = validator.promptString("Enter a valid name");
                    camp.setName(newName);
                    break;
                case 2:
                    System.out.println("Please enter the new camp date (YYYY-MM-DD):");
                    LocalDate newCampDate = validator.promptDate("Please enter a valid date", "yyyy-MM-dd");
                    camp.setDate(newCampDate);
                    break;
                case 3:
                    System.out.println("Please enter the new registration closing date (YYYY-MM-DD):");
                    LocalDate newClosureDate = validator.promptDate("Please enter a valid date", "yyyy-MM-dd");
                    camp.setClosureDate(newClosureDate);
                    break;
                case 4:
                    System.out.println("Please enter a new location:");
                    String newLocation = validator.promptString("Enter a valid location");
                    camp.setLocation(newLocation);
                    break;
                case 5:
                    System.out.println("Please enter a new description:");
                    String newDescription = validator.promptString("Enter a valid description");
                    camp.setDescription(newDescription);
                    break;
                case 6:
                    System.out.println("Please enter a new faculty:");
                    String newFaculty = validator.promptString("Enter a valid faculty");
                    camp.setFaculty(newFaculty);
                    break;
                case 7:
                    System.out.println("Please enter the new number of camp slots:");
                    int newNoSlot = validator.promptInt();
                    camp.setRemainingSlots(newNoSlot);
                    break;
                case 8:
                    System.out.println("Please enter the new number of committee slots:");
                    int newNoComSlot = validator.promptInt();
                    camp.setRemainingCommitSlots(newNoComSlot);
                    break;
                default:
                    System.out.println("UNEXPECTED OUTPUT!!!");
                    break;
            }
            while (!camp.getClosureDate().isBefore(camp.getDate())) {
                System.out.println("Registration closing date must be before the camp date");
                System.out.println(
                        "Please choose one of them to change, T for camp date, F for  registration closing date");
                boolean changeChoice = validator.promptBoolean("Please enter T/F", "T", "F");
                if (changeChoice) {
                    System.out.println("Please enter the camp date (YYYY-MM-DD):");
                    camp.setDate(validator.promptDate("Please enter a valid date", "yyyy-MM-dd"));
                } else {
                    System.out.println("Please enter the registration closing date (YYYY-MM-DD):");
                    camp.setClosureDate(validator.promptDate("Please enter a valid date", "yyyy-MM-dd"));
                }
            }
        }
    }
}