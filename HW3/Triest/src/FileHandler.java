import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileHandler {
    public static final String TEXT_FILE_EXTENSION = ".txt";

    public static List<String> readTexts(String path) {
        ArrayList<String> fileNames = new ArrayList<>();
        File[] files = new File(path).listFiles();
        for (File file : files) {
            if (file.isFile() && file.toString().endsWith(TEXT_FILE_EXTENSION)) {
                fileNames.add(file.toString());
            }
        }
        return fileNames;
    }
    public static String readAll(String file) throws FileNotFoundException {
        BufferedReader fromFile = new BufferedReader(new FileReader(file));
        StringBuilder content = new StringBuilder();
        fromFile.lines().forEach(content::append);
        return content.toString();
    }

    public static String[] readLines(String file) throws FileNotFoundException {
        BufferedReader fromFile = new BufferedReader(new FileReader(file));
        LinkedList<String> content = new LinkedList<>();
        fromFile.lines().forEach(content::add);
        return content.stream().toArray(String[]::new);
    }

}
