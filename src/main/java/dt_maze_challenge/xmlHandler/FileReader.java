package dt_maze_challenge.xmlHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    private FileReader(){}
    public static String readFromFile(String fileName) {
        String filePath = "C:\\usefull\\java_project\\maze\\resources\\" + fileName;
        StringBuilder content = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
