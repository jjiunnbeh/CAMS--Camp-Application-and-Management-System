package suggestion;


/**
 * Suggestion provided by the camp comittee member
 * contains the attributes of a suggestion
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class Suggestion {
    private String campName;
    private String content;
    private String madeBy;

    /**
     * @param campName name of the camp
     * @param content the description of the suggestio
     * @param madeBy the author of the suggestion
    */
    public Suggestion(String campName, String content, String madeBy) {
        this.campName = campName;
        this.content = content;
        this.madeBy = madeBy;
    }
    

    /**
    * Retrieve the suggestion creator
    * @return the id of the suggestion creator
    */
    public String getMadeBy() {
        return madeBy;
    }

    
    /**
    * Retrieve the name of the camp that the suggestion is given
    * @return the name of the camp
    */
    public String getCampName() {
        return campName;
    }

    
    /**
    * Retrieve the description of the suggestion
    * @return the description of the suggestion
    */
    public String getContent() {
        return content;
    }
}
