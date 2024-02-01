package suggestion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//import java.util.Iterator;
import user.Student;


/**
 * This class serves as an abstraction that handles the reading of data from suggestions.txt Database
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class SuggestionManager {
    private List<Suggestion> suggestions = new ArrayList<>();
    private static SuggestionManager instance;

    
	/**
	* Reads from suggestions.txt. Handles any exception caused when reading
 	*/
    private SuggestionManager() {
        try {
            this.suggestions = SuggestionTextDB.readSuggestions("suggestions.txt");
        } catch (IOException e) {
            System.out.println("IOException while reading suggestions: " + e.getMessage());
            // continuing with an empty list.
            this.suggestions = new ArrayList<>();
        }
    }

    
    /**
    * Allow other class to get the Instance of SuggestionManager
 	* @return the instance of the SuggestionManager
 	*/
    public static SuggestionManager getInstance() {
        if (instance == null) {
            instance = new SuggestionManager();
        }
        return instance;
    }

    /**
    * Allow other class to retrieve the List of Suggestion
 	* @return the list of Suggestion object
 	*/
    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    
    /**
     * create the new suggestion and add it to the suggestion list
 	* @param campName name of the camp the suggestion is for
 	* @param student the owner of the the suggestion
 	* @param content the description of the suggestion
 	*/
    public void addSuggestion(String campName, Student student, String content) {
        Suggestion suggestion = new Suggestion(campName, student.getName(), content);
        suggestions.add(suggestion);
        this.save();
    }

    
    /**
    * Method used to remove a suggestion
 	* @param suggestion the suggestion object to remove
 	*/
    public void removeSuggestion(Suggestion suggestion) {
        // searches through suggestions to remove the passed in suggestion from the list
        int indexToRemove = suggestions.indexOf(suggestion);

        if (indexToRemove != -1) {
            suggestions.remove(indexToRemove);
            this.save();
            System.out.println("Suggestion removed successfully.");
        } else {
            System.out.println("Suggestion not found in the list.");
        }
    }

    
    /**
    * Retrieves all the suggestions for a specific camp
  	* @param campName the name of the camp
  	*/
    public void listCampSuggestionsByIndex(String campName) {
        System.out.println("Suggestions for " + campName + ":");
        for (Suggestion suggestion : suggestions) {
            if (suggestion.getCampName().equals(campName)) {
                System.out.println(suggestions.indexOf(suggestion) + ". " + "By " + suggestion.getMadeBy() + ": "
                        + suggestion.getContent());
            }
        }
    }

    /**
     * Retrieve the suggestion based on its index in database
     * @param index the index of of suggetion object
     * @return the suggestion that match the index
     */
    public Suggestion getSuggestionByIndex(int index) {
        return suggestions.get(index);
    }

    
    /**
    * Saves all the suggestion into suggestions.txt
    * handles the Exception when writing to suggestions.txt
    */
    public void save() {
        try {
            SuggestionTextDB.saveSuggestions("suggestions.txt", suggestions);
        } catch (IOException e) {
            System.out.println("IOException while saving suggestions: " + e.getMessage());
        }
    }

}
