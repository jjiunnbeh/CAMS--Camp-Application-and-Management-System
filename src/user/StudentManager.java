package user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * This class serves as an abstraction that handles the reading of data from Student.txt Database
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class StudentManager {
    private List<Student> students = new ArrayList<>();
    private static StudentManager instance;

    
    /**
	 * Reads from students.txt. Handles any exception caused when reading
	 */
    public StudentManager() {
        try {
            this.students = StudentTextDB.readStudents("students.txt");
        } catch (IOException e) {
            System.out.println("IOException while reading students: " + e.getMessage());
            // continuing with an empty list.
            this.students = new ArrayList<>();
        }
    }

 	/**
 	 * Allow other class to get the Instance of StudentManager
 	 * @return the instance of the StudentManager
 	 */
    public static StudentManager getInstance() {
        if (instance == null) {
            instance = new StudentManager();
        }
        return instance;
    }

    
    /**
     * Allow other class to retrieve the List of Students
     * @return the list of Student object
     */
    public List<Student> getStudents() {
        return students;
    }

    
    /**
     * Find if the student exist by their name
     * @param studentName
     * @return the student object if found
     */
    public Student getStudentByName(String studentName) {
        for (Student student : students) {
            if (student.getName().equals(studentName)) {
                return student; // Found the student, return it
            }
        }
        // If no student with the given name is found, return null or handle it as
        // needed
        return null;
    }

    
    /**
    * Add points for the student after they have replied to an enquiry. Increment the point by 1
    * @param student the student object that needs to be updated
    */
    public void addPoint(Student student) {
    	
        // searches through students
        int studentIndex = students.indexOf(student);
        if (studentIndex == -1)
        {
        	System.out.println("Error. Student doesn't exist");
        	return;
        }
        
        int points = students.get(studentIndex).getPoints();
        
       
        points++;
        students.get(studentIndex).setPoints(points);
        this.save();
    }


    /**
    * Saves the student array list into students.txt
    * Handles any exception when writing to students.txt file
    */
    public void save() {
        try {
            StudentTextDB.saveStudents("students.txt", students);
        } catch (IOException e) {
            System.out.println("IOException while saving students: " + e.getMessage());
        }
    }
}
