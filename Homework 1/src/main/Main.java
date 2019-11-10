package main;

import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static final int SHINGLE_SIZE = 9;
    public static void main(String[] args) throws FileNotFoundException {
        List<String> files = FileHandler.readTexts("Homework 1/src/resources");
        List<Shingling> sets = new LinkedList<>();
        for (String file : files) {
            sets.add(new Shingling(SHINGLE_SIZE, file));
        }

        // 1st::compare Jaccard Similarity of s1 and s2
        CompareSets compareSets = new CompareSets();
        double s1compares2 = compareSets.jaccardSimilarity(sets.get(0).getShingles(), sets.get(1).getShingles());
        System.out.println("The Jaccard similarity of two sets is:" + s1compares2);

        // 2nd::minhashing calculate jaccardSimilarity
        LinkedList<Set<Integer>> documents = new LinkedList<>();
        sets.forEach(s -> documents.add(s.getShingles()));
        int numPermutes = 1000;
        MinHashing minHashing = new MinHashing(documents, numPermutes, true);
        float startTime = System.currentTimeMillis();
        int[][]signatureMatrix = minHashing.getSignByPermute();

//        // 3nd::LSH compare signature
        int bands = 50;
        int rows = 20;
        double threshold = 0.8;
        LSH lsh = new LSH(signatureMatrix, bands, rows, threshold);
        List<List<Integer>>candidatePairs = lsh.findCandidatePairs();
        List<List<Integer>>similarPairs = new ArrayList<>();
        for(List pair:candidatePairs){
            int index1 = Integer.parseInt(pair.get(0).toString());
            int index2 = Integer.parseInt(pair.get(1).toString());
//            int[] array1 = signatureMatrix[index1];
//            int[] array2 = signatureMatrix[index2];
//            Set doc1 = new HashSet<>(Arrays.asList(array1));
//            Set doc2 = new HashSet<>(Arrays.asList(array2));
            Set doc1 = sets.get(index1).getShingles();
            Set doc2 = sets.get(index2).getShingles();

            double similarity = compareSets.jaccardSimilarity(doc1, doc2);
            System.out.println(similarity);
            if(similarity>=threshold){
                similarPairs.add(pair);
            }
        }
        System.out.println("SimilarPairs are:"+similarPairs);
    }
}
