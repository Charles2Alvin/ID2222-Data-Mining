package test;
import java.util.*;

public class LshTest {
    private final int[][]signatureMatrix;
    private final int bands;
    private final int rows;
    private final double threshold;

    public LshTest(int[][]signatureMatrix, int bands, int rows, double threshold){
        int[][] transposition = new int[signatureMatrix[0].length][signatureMatrix.length];
        for(int i=0; i<signatureMatrix.length; i++){
            for(int j=0; j<signatureMatrix[0].length; j++){
                transposition[j][i]=signatureMatrix[i][j];
            }
        }
        this.signatureMatrix = transposition;
        this.bands = bands;
        this.rows = rows;
//        this.buckets = buckets;
        this.threshold = threshold;
    }


    private List<Integer> hashingBands(int[]signature){
        List<Integer> hashedSignature = new ArrayList<>();
        for(int a=0; a<bands; a++){
            String oneBand = null;
            for(int b=a*rows; b<(a+1)*rows; b++){
                String str=String.valueOf(signature[b]);
                oneBand = oneBand+str;
            }
            if(oneBand != null){
                int oneHash = oneBand.hashCode();
                hashedSignature.add(oneHash);
            }
        }
        return hashedSignature;
    }

    public List<List<Integer>> findCandidatePairs(){
        List<List<Integer>> hashingBandMatrix = new ArrayList<>();
        //for each document
        for(int[] signature:signatureMatrix){
            hashingBandMatrix.add(hashingBands(signature));
        }
        List<List<Integer>> candidatePairs = new ArrayList<>();
        for(int r=0; r<bands; r++){
            for(int i=0; i<signatureMatrix.length; i++){
                for(int j=i+1; j<signatureMatrix.length; j++){
                    int hash1 = hashingBandMatrix.get(i).get(r);
                    int hash2 = hashingBandMatrix.get(j).get(r);
                    if(hash1 == hash2){
                        List<Integer> onePair = new ArrayList<Integer>();
                        onePair.add(i);
                        onePair.add(j);
                        if(!candidatePairs.contains(onePair)){
                            candidatePairs.add(onePair);
                        }
                    }
                }
            }
        }
        System.out.println("CandidatePairs are:"+candidatePairs);
        return candidatePairs;
    }

}
