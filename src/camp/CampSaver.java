package camp;

import java.io.*;
import java.util.TreeMap;

/**
 * CampSaver class is responsible for saving and loading camp data to/from a file.
 * It provides methods to serialize and deserialize a TreeMap of camps.
 * @author group1
 * @version 1.0
 * @since 24 nov 2023
 */
public class CampSaver {
    // The file path where camp data will be stored.
    private static final String FILE_PATH = "System/camps.txt";

    /**
     * Save a TreeMap of camps to a file.
     *
     * @param campTreeMap The TreeMap containing camp data to be saved.
     */
    public static void saveCamps(TreeMap<String, Camp> campTreeMap) {
        // Create a directory if it doesn't exist
        File directory = new File("System");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(campTreeMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load a TreeMap of camps from a file.
     *
     * @return A TreeMap containing the loaded camp data, or an empty TreeMap if loading fails.
     */
    public static TreeMap<String, Camp> loadCamps() {
        // Create a directory if it doesn't exist
        File directory = new File("System");
        if (!directory.exists()) {
            directory.mkdirs();
            return new TreeMap<>();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (TreeMap<String, Camp>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new TreeMap<>();
    }
}