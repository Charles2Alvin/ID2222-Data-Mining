package main;

import java.io.FileNotFoundException;
import java.util.*;

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

        //one::compare jaccardSimilarity of s1 and s2
        CompareSets compareSets = new CompareSets();
        double s1compares2 = compareSets.jaccardSimilarity(s1.getShingleSet(), s2.getShingleSet());
        System.out.println("The Jaccard similarity of two sets is:"+s1compares2);

        //two::minhashing calculate jaccardSimilarity
        LinkedList<Set<Integer>> documents = new LinkedList<>();
        documents.add(s1.getShingleSet());
        documents.add(s2.getShingleSet());
        documents.add(s3.getShingleSet());
        documents.add(s4.getShingleSet());
        MinHashing minHashing = new MinHashing(documents, 1000, true);
        int[][]signatureMatrix = minHashing.buildSignatureMatrix();

        //three::LSH compare signature
        int bands = 50;
        int rows = 20;
        double threshold = 0.8;
        LSH lsh = new LSH(signatureMatrix, bands, rows, threshold);
        List<List<Integer>>candidatePairs = lsh.findCandidatePairs();
        List<List<Integer>>similarPairs = new ArrayList<>();
        for(List pair:candidatePairs){
            int index1 = Integer.parseInt(pair.get(0).toString());
            int index2 = Integer.parseInt(pair.get(1).toString());
            int[] array1 = signatureMatrix[index1];
            int[] array2 = signatureMatrix[index2];
            Set doc1 = new HashSet<>(Arrays.asList(array1));
            Set doc2 = new HashSet<>(Arrays.asList(array2));
            double similarity = compareSets.jaccardSimilarity(doc1, doc2);
            if(similarity>=threshold){
                similarPairs.add(pair);
            }
        }
        System.out.println("SimilarPairs are:"+similarPairs);

    }
}
