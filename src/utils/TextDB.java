package utils;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;


/**
 * Abstract class TextDB, inherited by other TextDB classes to read and write to database
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public abstract class TextDB<T> {
    public static final String SEPARATOR = "|";

    
    /**
     * Abstract class createObject used to create object after reading from database
     * @param line input read from database
     * @return the object type
     */
    // implement both with specific object struture in subclasses
    public abstract T createObject(String line);

    
    /**
     * Abstract class objectToString  used to convert object to a database string
     * @param object the type of object
     * @return the object in string format
     */
    public abstract String objectToString(T object);

    
    
    /**
     * Write to database 
     * @param fileName in txt format
     * @param data the string to be written
     * @throws IOException if an error occurs when writing to database 
     */
    protected static void write(String fileName, List<String> data) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(fileName));

        try {
            for (String line : data) {
                out.println(line);
            }
        } finally {
            out.close();
        }
    }

    
    /**
     * Read from database
     * @param fileName to be read
     * @return the list of strings read from database
     * @throws IOException if an error occurs when writing to database 
     */
    protected static List<String> read(String fileName) throws IOException {
        List<String> data = new ArrayList<>();
        Scanner scanner = new Scanner(new FileInputStream(fileName));

        try {
            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
        } finally {
            scanner.close();
        }

        return data;
    }

}
