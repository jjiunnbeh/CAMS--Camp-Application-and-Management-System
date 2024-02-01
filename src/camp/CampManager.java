package camp;

import utils.OperationResult;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * CampManager class is responsible for managing camps and their operations.
 * It maintains a TreeMap of camps and provides methods for adding, removing,
 * and accessing camps.
 * 
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class CampManager {
    private TreeMap<String, Camp> campTreeMap;

    private static CampManager instance;

    /**
     * Private constructor to initialize a CampManager instance.
     * Loads existing camps from storage using CampSaver.
     */
    private CampManager() {
        campTreeMap = CampSaver.loadCamps();
    }

    /**
     * Get the singleton instance of CampManager.
     *
     * @return The CampManager instance.
     */
    public static CampManager getInstance() {
        if (instance == null) {
            instance = new CampManager();
        }
        return instance;
    }

    /**
     * Save the current state of camps to storage using CampSaver.
     */
    public void saveCamps() {
        CampSaver.saveCamps(campTreeMap);
    }

    /**
     * Check if a camp with the given name exists.
     *
     * @param name The name of the camp to check.
     * @return True if a camp with the given name exists; otherwise, false.
     */
    public boolean hasCamp(String name) {
        return campTreeMap.containsKey(name);
    }

    /**
     * Add a new camp to the manager.
     *
     * @param camp The camp to add.
     * @return An OperationResult indicating the success or failure of the
     *         operation.
     */
    public OperationResult addCamp(Camp camp) {
        if (hasCamp(camp.getName())) {
            return OperationResult.failure("There is an existing camp with the same name");
        }
        campTreeMap.put(camp.getName(), camp);
        return OperationResult.success();
    }

    /**
     * Remove a camp with the given name from the manager.
     *
     * @param name The name of the camp to remove.
     * @return An OperationResult indicating the success or failure of the
     *         operation.
     */
    public OperationResult removeCamp(String name) {
        if (!hasCamp(name)) {
            return OperationResult.failure("No camp with the given name exists");
        }
        if (!campTreeMap.get(name).hasNoStudent()) {
            return OperationResult.failure("Cannot remove camp with student registered");
        }
        campTreeMap.remove(name);
        return OperationResult.success();
    }

    /**
     * Get a camp by name.
     *
     * @param name The name of the camp to retrieve.
     * @return The Camp object if found; otherwise, null.
     */
    public Camp getCamp(String name) {
        return getCamp(name, camp -> true);
    }

    /**
     * Get a camp by name that satisfies a specific condition.
     *
     * @param name      The name of the camp to retrieve.
     * @param condition The condition to filter camps.
     * @return The Camp object if found and the condition is satisfied; otherwise,
     *         null.
     */
    public Camp getCamp(String name, Predicate<Camp> condition) {
        Camp camp = campTreeMap.get(name);
        if (camp != null && condition.test(camp)) {
            return camp;
        }
        return null;
    }

    /**
     * Get a list of camps that satisfy a specific condition.
     *
     * @param condition The condition to filter camps.
     * @return A List of Camp objects that satisfy the condition.
     */
    public List<Camp> getCampList(Predicate<Camp> condition) {
        return campTreeMap.values().stream().filter(condition).collect(Collectors.toList());
    }

    /**
     * Find the first camp that satisfies a specific condition.
     *
     * @param condition The condition to search for a camp.
     * @return The first Camp object that satisfies the condition; otherwise, null.
     */
    public Camp findCamp(Predicate<Camp> condition) {
        for (Camp camp : campTreeMap.values()) {
            if (condition.test(camp))
                return camp;
        }
        return null;
    }

    /**
     * Apply an action to a camp with the given name.
     *
     * @param name   The name of the camp to apply the action to.
     * @param action The action to perform on the camp.
     * @return An OperationResult indicating the success or failure of the action.
     */
    public OperationResult applyToCamp(String name, Function<Camp, OperationResult> action) {
        if (!hasCamp(name)) {
            return OperationResult.failure("No camp with the given name exists");
        }
        return action.apply(getCamp(name));
    }

    /**
     * Check if a student is already registered for a camp on the same date.
     *
     * @param studentID The ID of the student.
     * @param date      The date to check for clashes.
     * @return The name of the conflicting camp if a clash is found; otherwise,
     *         null.
     */
    private String clashDate(String studentID, LocalDate date) {
        for (Camp camp : campTreeMap.values()) {
            if (camp.hasStudent(studentID) && camp.getDate().isEqual(date))
                return camp.getName();
        }
        return null;
    }

    /**
     * Join a camp with the given name, student ID, and role (student or committee
     * member).
     *
     * @param name      The name of the camp to join.
     * @param studentID The ID of the student joining the camp.
     * @param asCommit  True if joining as a committee member; False if joining as a
     *                  student.
     * @param condition The condition to filter camps for joining.
     * @return An OperationResult indicating the success or failure of joining the
     *         camp.
     */
    public OperationResult joinCamp(String name, String studentID, boolean asCommit, boolean isCommittee,
            Predicate<Camp> condition) {
        Camp camp = getCamp(name, condition);
        if (camp == null) {
            return OperationResult.failure("No camp with the given name exists");
        }
        String clashCamp = clashDate(studentID, camp.getDate());
        if (clashCamp != null)
            return OperationResult.failure("You have already joined camp " + clashCamp + " on the same date");
        if (asCommit && !isCommittee) {
            return applyToCamp(name, camp1 -> camp1.addCommitMember(studentID));
        }
        if (asCommit && isCommittee) {
            return OperationResult.failure("You are a committee member of another camp.");
        }

        else {
            return applyToCamp(name, camp1 -> camp1.addStudent(studentID));
        }
    }
}