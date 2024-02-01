package suggestion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import utils.TextDB;


/**
 * Read and Write to suggestions.txt Database
 * Inherits from TextDB from the utils package
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */ 
public class SuggestionTextDB extends TextDB<Suggestion> {

	
	/**
	 * create the Suggestion Object after reading each line from the suggestions.txt file
	 * @param line read from suggestions.txt
	 */
    @Override
    public Suggestion createObject(String line) {
        StringTokenizer star = new StringTokenizer(line, SEPARATOR);

        String campName = star.nextToken().trim();
        String content = star.nextToken().trim();
        
        String madeBy = star.nextToken().trim();

        return new Suggestion(campName, content, madeBy);
    }

    /**
     * convert each Suggestion object into a string eg CAMPNAME|DESCRIPTION|OWNER
     * @param suggestion object from the suggestion arraylist
     * @return the string
     */
    @Override
    public String objectToString(Suggestion suggestion) {
        StringBuilder st = new StringBuilder();
        st.append(suggestion.getCampName().trim()).append(SEPARATOR);
        st.append(suggestion.getMadeBy().trim()).append(SEPARATOR);
        st.append(suggestion.getContent().trim());
        
        return st.toString();
    }

    
    /**
     * Read suggestion from suggestions.txt
     * @param filename , name of file "suggestions.txt"
     * @return suggestions, the list of suggestions in an array list
     * @throws IOException if an error occurs during the reading process
     */
    public static ArrayList<Suggestion> readSuggestions(String filename) throws IOException {
        List<String> stringArray = read(filename);
        ArrayList<Suggestion> suggestions = new ArrayList<>();
        SuggestionTextDB textDB = new SuggestionTextDB(); // Create an instance
        for (String st : stringArray) {
            suggestions.add(textDB.createObject(st));
        }
        return suggestions;
    }

    
    /**
     * write the data back into suggestions.txt
     * @param filename, name of file to be written
     * @param suggestionss, the list of suggestions object
     * @throws IOException, if an error occurs while writing to suggestions.txt
     */
    public static void saveSuggestions(String filename, List<Suggestion> suggestions) throws IOException {
        List<String> suggestionStrings = new ArrayList<>();
        SuggestionTextDB textDB = new SuggestionTextDB(); // Create an instance
        for (Suggestion suggestion : suggestions) {
            suggestionStrings.add(textDB.objectToString(suggestion));
        }

        write(filename, suggestionStrings);
    }
}