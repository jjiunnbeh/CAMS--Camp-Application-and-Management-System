package camp;

import utils.OperationResult;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;

/**
 * Camp class represents a camp event.
 * It stores information about the camp, such as its name, date, location, and available slots.
 * Camps can have student participants and committee members.
 * Serializable to enable object serialization.
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class Camp implements Serializable {
    private static final long serialVersionUID = 1L;

    // Camp properties
    private String name;
    private LocalDate date;
    private LocalDate closureDate;
    private boolean visible;
    private String faculty;
    private String location;
    private int remainingSlots;
    private int remainingCommitSlots;
    private final String creator;
    private HashSet<String> studentList;
    private HashSet<String> commitList;
    private HashSet<String> exitStudentList;
    private String description;

    /**
     * Constructor to initialize a Camp object.
     *
     * @param name          Name of the camp.
     * @param date          Date of the camp.
     * @param closureDate   Closure date of the camp.
     * @param faculty       Faculty the camp open to.
     * @param location      Location of the camp.
     * @param totalSlots    Total slots for students.
     * @param commitSlot    Slots for committee members.
     * @param description   Description of the camp.
     * @param staffID       Staff ID of the camp creator.
     */
    public Camp(String name, LocalDate date, LocalDate closureDate, String faculty, String location, int totalSlots,
                int commitSlot, String description, String staffID) {
        this.name = name;
        this.date = date;
        this.closureDate = closureDate;
        this.visible = true;
        this.faculty = faculty;
        this.location = location;
        this.remainingSlots = totalSlots;
        this.remainingCommitSlots = commitSlot;
        this.description = description;
        this.creator = staffID;
        this.studentList = new HashSet<>();
        this.commitList = new HashSet<>();
        this.exitStudentList = new HashSet<>();
    }

    // Getter methods

    /**
     * Get the name of the camp.
     *
     * @return The name of the camp.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the date of the camp.
     *
     * @return The date of the camp.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Get the registration closure date of the camp.
     *
     * @return The registration closure date of the camp.
     */
    public LocalDate getClosureDate() {
        return closureDate;
    }

    /**
     * Check if the camp is visible to student.
     *
     * @return True if the camp is visible to student; otherwise, false.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Get the faculty the camp open to, NTU if it opens to all faculty.
     *
     * @return The faculty the camp open to.
     */
    public String getFaculty() {
        return faculty;
    }

    /**
     * Get the location of the camp.
     *
     * @return The location of the camp.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Get the remaining available slots for students.
     *
     * @return The remaining student slots.
     */
    public int getRemainingSlots() {
        return remainingSlots;
    }

    /**
     * Get the remaining available slots for committee members.
     *
     * @return The remaining committee slots.
     */
    public int getRemainingCommitSlots() {
        return remainingCommitSlots;
    }

    /**
     * Get the description of the camp.
     *
     * @return The description of the camp.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the creator (staff ID) of the camp.
     *
     * @return The staff ID of the camp creator.
     */
    public String getCreator() {
        return this.creator;
    }

    // Setter methods

    /**
     * Set the name of the camp.
     *
     * @param name The new name of the camp.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the date of the camp.
     *
     * @param date The new date of the camp.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Set the registration closure date of the camp.
     *
     * @param closureDate The new registration closure date of the camp.
     */
    public void setClosureDate(LocalDate closureDate) {
        this.closureDate = closureDate;
    }

    /**
     * Set the visibility of the camp.
     *
     * @param visible True to make the camp visible to student; otherwise, false.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Set the faculty of the camp open to, NTU for open to all faculty.
     *
     * @param faculty The new faculty of the camp.
     */
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    /**
     * Set the location of the camp.
     *
     * @param location The new location of the camp.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Set the remaining available slots for students.
     *
     * @param remainingSlots The new remaining student slots.
     */
    public void setRemainingSlots(int remainingSlots) {
        this.remainingSlots = remainingSlots;
    }

    /**
     * Set the remaining available slots for committee members.
     *
     * @param remainingCommitSlots The new remaining committee slots.
     */
    public void setRemainingCommitSlots(int remainingCommitSlots) {
        this.remainingCommitSlots = remainingCommitSlots;
    }

    /**
     * Set the description of the camp.
     *
     * @param description The new description of the camp.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    // Camp operations

    /**
     * Add a student to the camp and minus 1 slot.
     * if camp already has this student, fail
     * if camp have no more slot, fail
     * if student had exited camp before, fail
     *
     * @param studentID The ID of the student to add.
     * @return An OperationResult indicating the success or failure of the operation.
     */
    public OperationResult addStudent(String studentID) {
        if (this.hasStudent(studentID)) {
            return OperationResult.failure("Student is already registered for the camp");
        }
        if (remainingSlots <= 0) {
            return OperationResult.failure("Not enough available slots for students");
        }
        if (exitStudentList.contains(studentID)) {
            return OperationResult.failure("Cannot rejoin the camp after exiting");
        }
        studentList.add(studentID);
        remainingSlots--;
        return OperationResult.success();
    }

    /**
     * Add a student as a committee member to the camp.
     * Both add into commit list and member list
     *
     * @param studentID The ID of the student to add as a committee member.
     * @return An OperationResult indicating the success or failure of the operation.
     */
    public OperationResult addCommitMember(String studentID) {
        if (this.hasStudent(studentID)) {
            return OperationResult.failure("Student is already registered for the camp");
        }
        if (remainingSlots <= 0) {
            return OperationResult.failure("Not enough available slots for students");
        }
        if (remainingCommitSlots <= 0) {
            return OperationResult.failure("Not enough available slots for committee members");
        }
        if (exitStudentList.contains(studentID)) {
            return OperationResult.failure("Cannot rejoin the camp after exiting");
        }
        studentList.add(studentID);
        commitList.add(studentID);
        remainingSlots--;
        remainingCommitSlots--;
        return OperationResult.success();
    }

    /**
     * Check if a student is registered for the camp.
     *
     * @param studentID The ID of the student to check.
     * @return True if the student is registered; otherwise, false.
     */
    public boolean hasStudent(String studentID) {
        return studentList.contains(studentID);
    }

    /**
     * Check if a student is a committee member of the camp.
     *
     * @param studentID The ID of the student to check.
     * @return True if the student is a committee member; otherwise, false.
     */
    public boolean hasCommitMember(String studentID) {
        return commitList.contains(studentID);
    }

    /**
     * Remove a student from the camp.
     * Cannot remove commit member
     *
     * @param studentID The ID of the student to remove.
     * @return An OperationResult indicating the success or failure of the operation.
     */
    public OperationResult removeStudent(String studentID) {
        if (!this.hasStudent(studentID))
            return OperationResult.failure("Student is not registered for the camp");
        if (hasCommitMember(studentID)) {
            return OperationResult.failure("Cannot quit the camp as a committee member");
        }
        studentList.remove(studentID);
        exitStudentList.add(studentID);
        remainingSlots++;
        return OperationResult.success();
    }

    /**
     * Check if there are no students registered for the camp.
     *
     * @return True if there are no students registered; otherwise, false.
     */
    public boolean hasNoStudent() {
        return studentList.isEmpty();
    }

    /**
     * Get the list of students registered for the camp.
     *
     * @return A HashSet containing the IDs of registered students.
     */
    public HashSet<String> getStudentList() {
        return studentList;
    }
}