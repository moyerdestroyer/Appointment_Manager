package DAO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * FileHandler class for Creating and appending the login_activity.txt file
 */
public class FileHandler {
    static final String filename = "login_activity.txt";
    /**
     * Attempts to create file, creates exception if one is already made
     */
    public static void createFile() {
        try {
            File activityLog = new File(filename);
            if (activityLog.createNewFile()) {
                System.out.println("File Created");
            } else {
                System.out.println("File Already Exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param textToWrite Input string is appended to the file and a newline is added, no other formatting
     */
    public static void appendFile(String textToWrite) {
        try {
            FileWriter fw = new FileWriter(filename, true);
            fw.write(textToWrite + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
