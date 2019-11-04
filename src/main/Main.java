package main;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Shingling shingling = new Shingling(9, new NovelReader());
        shingling.generate("src/resources/A Scandal in Bohemia ch1.txt");
    }
}
