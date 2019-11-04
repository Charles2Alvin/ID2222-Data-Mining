package main;

import java.util.*;

public class MinHashing {
    private int numPermutes;
    private LinkedList<Set<Integer>> documents;
    private int[][] characteristic;
    private int[][] signatureMatrix;
    private int[][] permutation;
    private boolean printInfo;
    private float[][] similarity;

    public MinHashing(LinkedList<Set<Integer>> documents, int numPermutes, boolean printInfo) {
        this.documents = documents;
        this.numPermutes = numPermutes;
        this.printInfo = printInfo;
    }
    public MinHashing(LinkedList<Set<Integer>> documents, int numPermutes, boolean printInfo, int[][] characteristic) {
        this.documents = documents;
        this.numPermutes = numPermutes;
        this.printInfo = printInfo;
        this.characteristic = characteristic;
    }
    /*
        Transform a collection of shingle sets to a characteristic matrix
     */
    public void buildCharaMatrix() {
        if (printInfo) System.out.println("> generating characteristic matrix...");
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
        if (printInfo) System.out.println("> generating signature matrix...");
        signatureMatrix = new int[numPermutes][];
        for (int i = 0; i < numPermutes; i++) {
            signatureMatrix[i] = new int[documents.size()];
        }

        // Generate the permutation matrix which works as a bunch of hash functions
        permute();

        // Fill the signature matrix with minHash value
        for (int p = 0; p < numPermutes; p++) {
            for (int i = 0; i < documents.size(); i++) {
                // Compute the signature for the i-th document
                for (int row = 0; row < permutation.length; row++) {
                    // Traverse the rows in the permutation
                    int index = permutation[row][p];
                    if (characteristic[index][i] == 1) {
                        signatureMatrix[p][i] = row + 1;
                        break;
                    }
                }
            }
        }
        if (printInfo) {
            System.out.println("> Signature matrix:");
            printMatrix(signatureMatrix);
        }
        fillSimilarity();

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
    public void permute() {
        if (printInfo) System.out.println("> generating permutations...");
        int size = characteristic.length;
        LinkedList<Integer> nums = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            nums.add(i);
        }
        List<List<Integer>> matrix = new LinkedList<>();
        for (int i = 0; i < numPermutes; i++) {
            matrix.add(new LinkedList<>(nums));
        }
        matrix.forEach(Collections::shuffle);
        permutation = new int[size][];
        for (int i = 0; i < size; i++) {
            permutation[i] = new int[numPermutes];
        }
        for (int i = 0; i < numPermutes; i++) {
            for (int j = 0; j < size; j++) {
                permutation[j][i] = matrix.get(i).get(j);
            }
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
