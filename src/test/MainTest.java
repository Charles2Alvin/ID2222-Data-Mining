package test;

import test.LshTest;
import test.CompareSetsTest;

import java.util.*;

public class MainTest {
    public static void main(String[] args) {

        int[][] signatureMatrix = new int[1000][4];
        for(int i=0; i<1000; i++){
            for(int j=0; j<4; j++){
                final long l = System.currentTimeMillis();
                final int random = (int)( l % 1000 );
                signatureMatrix[i][j] = random;
            }
        }

        int bands = 50;
        int rows = 20;
        double threshold = 0.8;
        LshTest lsh = new LshTest(signatureMatrix, bands, rows, threshold);
        List<List<Integer>>candidatePairs = lsh.findCandidatePairs();
        List<List<Integer>>similarPairs = new ArrayList<>();
        for(List pair:candidatePairs){
            int index1 = Integer.parseInt(pair.get(0).toString());
            int index2 = Integer.parseInt(pair.get(1).toString());
            int[] array1 = signatureMatrix[index1];
            int[] array2 = signatureMatrix[index2];
            Set doc1 = new HashSet<>(Arrays.asList(array1));
            Set doc2 = new HashSet<>(Arrays.asList(array2));
            double similarity = CompareSetsTest.jaccardSimilarity(doc1, doc2);
            if(similarity>=threshold){
                similarPairs.add(pair);
            }
        }
        System.out.println("SimilarPairs are:"+similarPairs);
    }
}
