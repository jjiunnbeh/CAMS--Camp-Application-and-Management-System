package camp;

import utils.InputValidator;
import user.Staff;
import user.Student;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

/**
 * Represents a filter for camps based on various criteria.
 * This class allows filtering camps by name, creator, date, location, and faculty.
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class CampFilter {
    private Predicate<Camp> filter;
    private String filterName;
    private String filterCreator;
    private LocalDate filterDate;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String filterLocation;
    private String filterFaculty;

    /**
     * Initializes a new CampFilter with default values.
     * By default, it shows all camps.
     */
    public CampFilter() {
        filter = camp -> true;
        filterName = "All Camps";
        filterCreator = null;
        filterDate = null;
        filterLocation = null;
        filterFaculty = null;
    }

    /**
     * Returns a filter predicate for camps that a student can join.
     * @param student The student for whom the filter is applied.
     * @return A predicate for filtering camps.
     */
    public Predicate<Camp> availableToJoin(Student student) {
        return getFilter().and(
                camp -> camp.isVisible() &&
                        (camp.getFaculty().equals(student.getFaculty()) || camp.getFaculty().equals("NTU")) &&
                        (!camp.hasStudent(student.getID())));
    }

    /**
     * Returns a filter predicate for camps that a student can quit.
     * @param student The student for whom the filter is applied.
     * @return A predicate for filtering camps.
     */
    public Predicate<Camp> availableToQuit(Student student) {
        return getFilter().and(camp -> camp.hasStudent(student.getID()) && camp.isVisible());
    }

    /**
     * Gets the current filter predicate.
     * @return The filter predicate.
     */
    public Predicate<Camp> getFilter() {
        return filter;
    }

    /**
     * Gets the name of the current filter.
     * @return The name of the filter.
     */
    public String getFilterName() {
        return filterName;
    }

    private void setFilterAll() {
        filter = camp -> true;
        filterName = "All camps";
    }

    private void setFilterCreator(String creatorID) {
        this.filterCreator = creatorID;
        filter = camp -> camp.getCreator().equals(filterCreator);
        filterName = "Camps created by: " + filterCreator;
    }

    private void setFilterDate(LocalDate date) {
        this.filterDate = date;
        filter = camp -> camp.getDate().isEqual(filterDate);
        filterName = "Camps of date: " + filterDate.format(formatter);
    }

    private void setFilterLocation(String location) {
        this.filterLocation = location;
        filter = camp -> camp.getLocation().equals(filterLocation);
        filterName = "Camps in location: " + filterLocation;
    }

    private void setFilterFaculty(String faculty) {
        this.filterFaculty = faculty;
        filter = camp -> camp.getFaculty().equals(filterFaculty);
        filterName = "Camps in faculty: " + filterFaculty;
    }

    /**
     * Changes the filter based on user input (staff version).
     * @param validator The input validator.
     * @param staff The staff member for whom the filter is applied.
     */
    public void changeFilter(InputValidator validator, Staff staff) {
        System.out.println("Please choose one of the following options");
        System.out.println("1. View all camps");
        System.out.println("2. View camps you created");
        System.out.println("3. View camps of date");
        System.out.println("4. View camps of location");
        System.out.println("5. View camps of faculty");
        System.out.println("6. Quit");

        int choice = validator.promptIntInRange(1, 6);
        switch (choice) {
            case 1:
                setFilterAll();
                break;
            case 2:
                setFilterCreator(staff.getID());
                break;
            case 3:
                System.out.println("Please enter the camp date (YYYY-MM-DD):");
                LocalDate date = validator.promptDate("Please enter a valid date", "yyyy-MM-dd");
                setFilterDate(date);
                break;
            case 4:
                System.out.println("Please enter the location:");
                String location = validator.promptString("Please enter a valid location");
                setFilterLocation(location);
                break;
            case 5:
                System.out.println("Please enter the faculty:");
                String faculty = validator.promptString("Please enter a valid faculty");
                setFilterFaculty(faculty);
                break;
            case 6:
                return;
            default:
                System.out.println("UNEXPECTED OUTCOME!!!");
                return;
        }
    }

    /**
     * Changes the filter based on user input (student version).
     * @param validator The input validator.
     * @param student The student for whom the filter is applied.
     */
    public void changeFilter(InputValidator validator, Student student) {
        System.out.println("Please choose one of the following options");
        System.out.println("1. View all camps");
        System.out.println("2. View camps of date");
        System.out.println("3. View camps of location");
        System.out.println("4. View camps of faculty");
        System.out.println("5. Quit");

        int choice = validator.promptIntInRange(1, 5);
        switch (choice) {
            case 1:
                setFilterAll();
                break;
            case 2:
                System.out.println("Please enter the camp date (YYYY-MM-DD):");
                LocalDate date = validator.promptDate("Please enter a valid date", "yyyy-MM-dd");
                setFilterDate(date);
                break;
            case 3:
                System.out.println("Please enter the location:");
                String location = validator.promptString("Please enter a valid location");
                setFilterLocation(location);
                break;
            case 4:
                System.out.println("Please enter the faculty:");
                String faculty = validator.promptString("Please enter a valid faculty");
                setFilterFaculty(faculty);
                break;
            case 5:
                return;
            default:
                System.out.println("UNEXPECTED OUTCOME!!!");
                return;
        }
    }
}
