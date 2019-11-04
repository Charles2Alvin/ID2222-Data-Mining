package main;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Shingling s1 = new Shingling(9, new NovelReader());
        s1.generate("src/resources/A Scandal in Bohemia ch1.txt");
        Shingling s2 = new Shingling(9, new NovelReader());
        s2.generate("src/resources/A Scandal in Bohemia ch2.txt");
        Shingling s3 = new Shingling(9, new NovelReader());
        s3.generate("src/resources/A Scandal in Bohemia ch3.txt");
        Shingling s4 = new Shingling(9, new NovelReader());
        s4.generate("src/resources/Moon and sixpence 1.txt");
        LinkedList<Set<Integer>> documents = new LinkedList<>();
        documents.add(s1.getShingleSet());
        documents.add(s2.getShingleSet());
        documents.add(s3.getShingleSet());
        documents.add(s4.getShingleSet());
        MinHashing minHashing = new MinHashing(documents, 1000, true);
        minHashing.buildSignatureMatrix();
    }
}
