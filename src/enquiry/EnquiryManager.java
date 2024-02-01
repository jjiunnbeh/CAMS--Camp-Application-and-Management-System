package enquiry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import user.Student;

/**
 * This class serves as an abstraction that handles the reading of data from
 * enquiries.txt Database
 * 
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class EnquiryManager {
    private List<Enquiry> enquiries = new ArrayList<>();
    private static EnquiryManager instance;

    /**
     * Reads from enquires.txt. Handles any exception caused when reading
     */
    private EnquiryManager() {
        try {
            this.enquiries = EnquiryTextDB.readEnquiries("enquiries.txt");
        } catch (IOException e) {
            System.out.println("IOException while reading enquiries: " + e.getMessage());
            // continuing with an empty list.
            this.enquiries = new ArrayList<>();
        }
    }

    /**
     * Allow other class to get the Instance of EnquiryManager
     * 
     * @return the instance of the EnquiryManager
     */
    public static EnquiryManager getInstance() {
        if (instance == null) {
            instance = new EnquiryManager();
        }
        return instance;
    }

    /**
     * Allow other class to retrieve the List of Enquiry
     * 
     * @return the list of Enquiry object
     */
    public List<Enquiry> getEnquiries() {
        return enquiries;
    }

    /**
     * Retrieves all the enquiries for a specific camp
     * 
     * @param campName the name of the camp
     */
    public void listAllCampEnquiriesByIndex(String campName) {
        System.out.println("Enquires for " + campName + ":");
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getCampName().equals(campName)) {
                System.out.println(enquiries.indexOf(enquiry) + ". " + "By " + enquiry.getMadeBy() + ": "
                        + enquiry.getContent() + " Reply: " + enquiry.getReply());
            }
        }
    }

    /**
     * Retrieves all the unanswered enquiries for a specific camp
     * 
     * @param campName the name of the camp
     */
    public void listUnansweredCampEnquiriesByIndex(String campName) {
        System.out.println("Enquires for " + campName + ":");
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getCampName().equals(campName) && enquiry.getReply().equals("NO_REPLY")) {
                System.out.println(enquiries.indexOf(enquiry) + ". " + "By " + enquiry.getMadeBy() + ": "
                        + enquiry.getContent() + " Reply: " + enquiry.getReply());
            }
        }
    }

    /**
     * Retrieve the Enquiry based on its index in database
     * 
     * @param index the index of of Enquiry object
     * @return the enquiry that matched the index
     */
    public Enquiry getEnquiryByIndex(int index) {
        return enquiries.get(index);
    }

    /**
     * create the new enquiry and add it to the enquiry list
     * 
     * @param campName name of the camp the enquiry is for
     * @param student  the owner of the the enquiry
     * @param content  the description of the enquiry
     * @param reply    the reply of the enquiry
     */
    public void addEnquiry(String campName, Student student, String content, String reply) {
        String madeBy = student.getName();
        Enquiry enquiry = new Enquiry(campName, content, reply, madeBy);
        enquiries.add(enquiry);
        this.save();
    }

    /**
     * Set the reply of a specific enquiry
     * 
     * @param index of the enquiry that got replied
     * @param reply the description of the reply
     */
    public void replyEnquiry(int index, String reply) {
        enquiries.get(index).setReply(reply);
        this.save();
    }

    /**
     * Saves all the enquiry into enquires.txt
     * handles the Exception when writing to enquiries.txt
     */
    public void save() {
        try {
            EnquiryTextDB.saveEnquiries("enquiries.txt", enquiries);
        } catch (IOException e) {
            System.out.println("IOException while saving enquiries: " + e.getMessage());
        }
    }

}