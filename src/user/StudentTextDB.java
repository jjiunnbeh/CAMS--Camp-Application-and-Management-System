package user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import utils.TextDB;


/**
 * Read and Write to Student.txt Database
 * Inherits from TextDB from the utility package
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class StudentTextDB extends TextDB<Student> {

	
    /**
	 * create the Student Object after reading each line from the student.txt file
	 * @param line read from student.txt
	 */
    @Override
    public Student createObject(String line) {
        StringTokenizer star = new StringTokenizer(line, SEPARATOR);

        String ID = star.nextToken().trim();
        String name = star.nextToken().trim();
        String faculty = star.nextToken().trim();
        String password = star.nextToken().trim();
        String commitCamp = star.nextToken().trim();
        int points = Integer.parseInt(star.nextToken().trim());
        boolean isCommittee = Boolean.parseBoolean(star.nextToken().trim());

        Student student = new Student(ID, name, faculty, password, commitCamp, points);
        student.setIsCommittee(isCommittee);

        return student;
    }

    
    /**
     * convert each Staff object into a string eg ID|NAME|FACULTY|PASSWORD|CAMPNAME|POINTS|TRUE
     * @param student object from the student arraylist
     * @return the string 
     */
    @Override
    public String objectToString(Student student) {
        StringBuilder st = new StringBuilder();
        st.append(student.getID().trim()).append(SEPARATOR);
        st.append(student.getName().trim()).append(SEPARATOR);
        st.append(student.getFaculty().trim()).append(SEPARATOR);
        st.append(student.getPassword().trim()).append(SEPARATOR);
        st.append(student.getCommitCamp().trim()).append(SEPARATOR);
        st.append(student.getPoints()).append(SEPARATOR);
        st.append(student.getIsCommittee());
        return st.toString();
    }

    /**
     * Reads the txt file and convert the string into student object. add the student object into students array list and returns he array list
     * @param filename , name of file "staff.txt"
     * @return students, the list of student in an array list
     * @throws IOException if an error occurs during the reading process
     */
    public static ArrayList<Student> readStudents(String filename) throws IOException {
        ArrayList<String> stringArray = (ArrayList<String>) read(filename);
        ArrayList<Student> students = new ArrayList<>();
        StudentTextDB textDB = new StudentTextDB(); // Create an instance
        for (String st : stringArray) {
            students.add(textDB.createObject(st));
        }
        return students;
    }

    
    /**
     * write the data back into staff.txt
     * @param filename, name of file to be written
     * @param staffs, the list of staff object
     * @throws IOException, if an error occurs while writing to Staff.txt
     */
    public static void saveStudents(String filename, List<Student> students) throws IOException {
        List<String> studentStrings = new ArrayList<>();
        StudentTextDB textDB = new StudentTextDB(); // Create an instance
        for (Student student : students) {
            studentStrings.add(textDB.objectToString(student));
        }

        write(filename, studentStrings);
    }
}
