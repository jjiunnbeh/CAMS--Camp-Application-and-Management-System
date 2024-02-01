package user;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Used to generate the performance report for the camp committee by the staff
 * Used to trace how much points the committee member have achieved
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class CommitteeReportGenerator {
    public static final String FILE_PATH="CommitReport/CommitReport.txt";
    
    /**
     * Check if the student is a committee member 
     * and write it to the commitReport file.
     * the ID and points of the student will be written
     * @param students the list of student objects
     */
    public static void writeCommitReport(List<Student> students){
        File directory = new File("CommitReport");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File file=new File(FILE_PATH);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Student student:students){
                if (student.getIsCommittee()){
                    writer.write(student.getID());
                    writer.write(":");
                    writer.write(String.valueOf(student.getPoints()));
                    writer.newLine();
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}