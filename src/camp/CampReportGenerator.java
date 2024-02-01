package camp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

/**
 * CampReportGenerator class is responsible for generating and writing camp
 * reports to files.
 * It provides methods to write lists of camp attendees and committee members to
 * separate files.
 * 
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class CampReportGenerator {

    /**
     * Enumeration representing the write options for generating camp reports.
     */
    public enum WriteOption {
        ONLY_ATTENDEE, // Write only attendee list
        ONLY_COMMITTEE, // Write only committee member list
        BOTH // Write both attendee and committee member lists
    }

    // The directory path where camp reports will be saved.
    public static final String FILE_PATH = "CampReport";

    /**
     * Write the list of students attending a camp to a file, based on the specified
     * write option.
     *
     * @param camp   The camp for which the list is generated.
     * @param option The write option to determine which list to generate.
     */
    public static void writeStudentListToFile(Camp camp, WriteOption option) {
        // Create a directory if it doesn't exist
        File directory = new File(FILE_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String fileName = FILE_PATH + "/" + camp.getName() + "_StudentList_generatedbyStaff.txt";
        HashSet<String> studentList = camp.getStudentList();
        File file = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String studentID : studentList) {
                boolean isCommittee = camp.hasCommitMember(studentID);
                switch (option) {
                    case ONLY_ATTENDEE:
                        if (isCommittee)
                            continue;
                        break;
                    case ONLY_COMMITTEE:
                        if (!isCommittee)
                            continue;
                        break;
                    case BOTH:
                        break;
                }
                writer.write(studentID);
                writer.write("|");
                if (camp.hasCommitMember(studentID)) {
                    writer.write("committee");
                } else {
                    writer.write("attendee");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}