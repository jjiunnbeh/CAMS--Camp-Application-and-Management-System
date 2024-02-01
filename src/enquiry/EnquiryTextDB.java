package enquiry;

import utils.TextDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/**
 * Read and Write to enquiries.txt Database
 * Inherits from TextDB from the utils package
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */ 
public class EnquiryTextDB extends TextDB<Enquiry> {
	
	/**
	 * create the Enquiry Object after reading each line from the suggestions.txt file
	 * @param line read from enquires.txt
	 */
    @Override
    public Enquiry createObject(String line) {
        StringTokenizer star = new StringTokenizer(line, SEPARATOR);

        String campName = star.nextToken().trim();
        String content = star.nextToken().trim();
        String reply = star.nextToken().trim();
        String madeBy = star.nextToken().trim();
        Enquiry enquiry = new Enquiry(campName, content, reply, madeBy);
        return enquiry;
    }

    
    /**
     * convert each Enquiry object into a string eg CAMPNAME|DESCRIPTION|REPLY|OWNER
     * @param enquiry object from the enquiries arraylist
     * @return the string
     */
    @Override
    public String objectToString(Enquiry enquiry) {
        StringBuilder st = new StringBuilder();
        st.append(enquiry.getCampName().trim()).append(SEPARATOR);
        st.append(enquiry.getContent().trim()).append(SEPARATOR);
        st.append(enquiry.getReply().trim()).append(SEPARATOR);
        st.append(enquiry.getMadeBy().trim());
        return st.toString();
    }

    
    /**
     * Read data from enquires.txt
     * @param filename , name of file "enquiries.txt"
     * @return enquiries, the list of enquires in an array list
     * @throws IOException if an error occurs during the reading process
     */
    public static ArrayList<Enquiry> readEnquiries(String filename) throws IOException {
        List<String> stringArray = read(filename);
        ArrayList<Enquiry> enquiries = new ArrayList<>();
        EnquiryTextDB textDB = new EnquiryTextDB(); // Create an instance

        for (String st : stringArray) {
            enquiries.add(textDB.createObject(st));
        }
        return enquiries;
    }

    /**
     * Write the data back into enquires.txt
     * @param filename, name of file to be written
     * @param enquiries, the list of enquiry object
     * @throws IOException, if an error occurs while writing to suggestions.txt
     */
    public static void saveEnquiries(String filename, List<Enquiry> enquiries) throws IOException {
        List<String> enquiryStrings = new ArrayList<>();
        EnquiryTextDB textDB = new EnquiryTextDB(); // Create an instance

        for (Enquiry enquiry : enquiries) {
            enquiryStrings.add(textDB.objectToString(enquiry));
        }

        write(filename, enquiryStrings);
    }
}