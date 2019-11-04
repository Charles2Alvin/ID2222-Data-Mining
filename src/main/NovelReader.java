package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class NovelReader {
    public String read(String file) throws FileNotFoundException {
        BufferedReader fromFile = new BufferedReader(new FileReader(file));
        StringBuilder content = new StringBuilder();
        fromFile.lines().forEach(content::append);
        return content.toString();
    }

}
