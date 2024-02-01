package user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import utils.*;

/**
 * Read and Write to Student.txt Database
 * Inherits from TextDB from the utility package
 * 
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class StaffTextDB extends TextDB<Staff> {

    /**
     * create the Student Object after reading each line from the student.txt file
     * 
     * @param line read from student.txt
     */
    @Override
    public Staff createObject(String line) {
        StringTokenizer star = new StringTokenizer(line, SEPARATOR);

        String ID = star.nextToken().trim();
        String name = star.nextToken().trim();
        String faculty = star.nextToken().trim();
        String password = star.nextToken().trim();

        Staff staff = new Staff(ID, name, faculty, password);

        return staff;
    }

    /**
     * convert each Staff object into a string eg
     * ID|NAME|FACULTY|PASSWORD|CAMPNAME|POINTS|TRUE
     * 
     * @param student object from the student arraylist
     * @return the string
     */
    @Override
    public String objectToString(Staff staff) {
        StringBuilder st = new StringBuilder();
        st.append(staff.getID().trim()).append(SEPARATOR);
        st.append(staff.getName().trim()).append(SEPARATOR);
        st.append(staff.getFaculty().trim()).append(SEPARATOR);
        st.append(staff.getPassword().trim()).append(SEPARATOR);
        return st.toString();
    }

    /**
     * Reads the txt file and convert the string into student object. add the
     * student object into students array list and returns he array list
     * 
     * @param filename , name of file "staff.txt"
     * @return students, the list of student in an array list
     * @throws IOException if an error occurs during the reading process
     */
    public static ArrayList<Staff> readStaffs(String filename) throws IOException {
        ArrayList<String> stringArray = (ArrayList<String>) read(filename);
        ArrayList<Staff> staffs = new ArrayList<>();
        StaffTextDB textDB = new StaffTextDB(); // Create an instance
        for (String st : stringArray) {
            staffs.add(textDB.createObject(st));
        }
        return staffs;
    }

    /**
     * write the data back into staff.txt
     * 
     * @param filename, name of file to be written
     * @param staffs,   the list of staff object
     * @throws IOException, if an error occurs while writing to Staff.txt
     */
    public static void saveStaffs(String filename, List<Staff> staffs) throws IOException {
        List<String> staffStrings = new ArrayList<>();
        StaffTextDB textDB = new StaffTextDB(); // Create an instance
        for (Staff staff : staffs) {
            staffStrings.add(textDB.objectToString(staff));
        }
        write(filename, staffStrings);
    }
}
