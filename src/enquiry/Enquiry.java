package enquiry;


/**
 * Enquiry raised by the students
 * contains the attributes of an enquiry
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class Enquiry {
    private String campName;
    private String content;
    private String reply;
    private String madeBy;

    
    /**
     * @param campName name of the camp
     * @param content the description of the Enquiry
     * @param reply of the enquiry by the student committee or staff
     * @param madeBy the author of the enquiry

     */

    public Enquiry(String campName, String content, String reply, String madeBy) {
        this.campName = campName;
        this.content = content;
        this.reply = reply;
        this.madeBy = madeBy;
    }
    
    
    /**
     * Retrieve the name of the camp the enquiry is for
     * @return the name of the camp
     */
    public String getCampName() {
        return campName;
    }

    /**
    * Retrieve the description of the Enquiry
    * @return the description of the Enquiry
    */
    public String getContent() {
        return content;
    }

    /**
    * Retrieve the reply of the Enquiry
    * @return the reply of the Enquiry
    */
    public String getReply() {
        return reply;
    }

    /**
    * Retrieve the owner of the Enquiry
    * @return the owner of the Enquiry
    */
    public String getMadeBy() {
        return madeBy;
    }

    /**
     * Set the reply of the enquiry
     * @param reply , the reply string of the enquiry
     */
    public void setReply(String reply) {
        this.reply = reply;
    }

}