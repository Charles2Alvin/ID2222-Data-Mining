package main;

import java.util.*;

public class MinHashing {
    private int numPermutes;
    private LinkedList<Set<Integer>> documents;
    private int[][] characteristic;
    private int[][] signatureMatrix;
    private boolean printInfo;
    private float[][] similarity;
    private int numDocs;    // the number of documents
    private int sLength;    // the length of the whole shingle set


    public MinHashing(LinkedList<Set<Integer>> documents, int numPermutes, boolean printInfo) {
        this.documents = documents;
        this.numPermutes = numPermutes;
        this.printInfo = printInfo;
        this.numDocs = documents.size();

        buildCharaMatrix();
        signatureMatrix = new int[numPermutes][];
        for (int i = 0; i < numPermutes; i++) {
            signatureMatrix[i] = new int[documents.size()];
        }
    }

    /**
     * Transform a collection of shingle sets to a characteristic matrix
     * @return void
     */
    public void buildCharaMatrix() {
        if (printInfo) System.out.println("> generating characteristic matrix...");
        // Collect all the shingles
        Set<Integer> shingles = new TreeSet<>();
        documents.forEach(shingles::addAll);
        this.sLength = shingles.size();

        LinkedList<Integer> universal = new LinkedList<>(shingles);
        characteristic = new int[sLength][numDocs];
        for (int i = 0; i < numDocs; i++) {
            Set doc = documents.get(i);
            for (int j = 0; j < shingles.size(); j++) {
                characteristic[j][i] = doc.contains(universal.get(j)) ? 1 : 0;
            }
        }
    }
    /**
     *  Compute the signature matrix with permutation-based hashing
     * @return
     */
    public int[][] getSignByPermute() {
        if (printInfo) System.out.println("> generating signature matrix...");
        double startTime = System.currentTimeMillis();
        // Fill the signature matrix with minHash value
        for (int p = 0; p < numPermutes; p++) {
            ArrayList<Integer> permutes = new Permutation(sLength).nums;
            for (int i = 0; i < documents.size(); i++) {
                // Compute the signature for the i-th document
                for (int row = 0; row < sLength; row++) {
                    // Traverse the rows in the permutation
                    int index = permutes.get(row);
                    if (characteristic[index][i] == 1) {
                        signatureMatrix[p][i] = row + 1;
                        break;
                    }
                }
            }
        }
        if (printInfo) {
            double interval = (System.currentTimeMillis() - startTime)/1000;
            System.out.println("Time consumed: " + interval + " sec");
        }
        fillSimilarity();
        return signatureMatrix;
    }
    public float colSimilarity(int p, int q) {
        if (p == q) return 1f;
        int sameValues = 0;
        for (int i = 0; i < numPermutes; i++) {
            if (signatureMatrix[i][p] == signatureMatrix[i][q]) {
                sameValues += 1;
            }
        }
        return (float) sameValues / numPermutes;
    }
    public void fillSimilarity() {
        int n = documents.size();
        similarity = new float[n][];
        for (int i = 0; i < n; i++) {
            similarity[i] = new float[n];
            for (int j = 0; j < n; j++) {
                similarity[i][j] = colSimilarity(i, j);
            }
        }
        if (printInfo) {
            System.out.println("> Computing document-wise similarities:");
            printMatrix(similarity);
        }
    }

    public void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    public void printMatrix(float[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
