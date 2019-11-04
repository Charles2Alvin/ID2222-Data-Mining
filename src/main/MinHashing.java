package main;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

public class MinHashing {
    private LinkedList<Set<Integer>> documents;
    private int[][] characteristic;
    private int[][] signatureMatrix;

    public MinHashing(LinkedList<Set<Integer>> documents) {
        this.documents = documents;
    }
    /*
        Transform a collection of shingle sets to a characteristic matrix
     */
    public void buildCharaMatrix() {
        Set<Integer> shingles = new TreeSet<>();
        documents.forEach(shingles::addAll);
        LinkedList<Integer> universal = new LinkedList<>(shingles);
        characteristic = new int[shingles.size()][documents.size()];
        for (int i = 0; i < documents.size(); i++) {
            Set doc = documents.get(i);
            for (int j = 0; j < shingles.size(); j++) {
                characteristic[j][i] = doc.contains(universal.get(j)) ? 1 : 0;
            }
        }
    }
    public void buildSignatureMatrix() {
        if (characteristic == null) buildCharaMatrix();
        
    }
    public void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
