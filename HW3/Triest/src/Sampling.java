import java.util.*;

public class Sampling {
    public List<List<String>> edgePairs;
    public List<List<String>> edgeSamples;

    public Sampling(String[] edges){
        edgePairs = new LinkedList<>();
        for(String edge:edges){
            List<String> pair = new LinkedList<>();
            String[] items = edge.split(" ");
            if(items.length >= 2){
                pair.add(items[0]);
                pair.add(items[1]);
            }
            edgePairs.add(pair);
        }
    }

    public List<List<String>> getEdgePairs() {
        return edgePairs;
    }

    //generate sample edges
    public List<List<String>> edgeSamplePairs(int sampleNum){
        edgeSamples = new LinkedList<>();
        int sum = 0;
        int randomNum;
        int replaceIndex;
        Random rand = new Random();
        for(List edge:edgePairs){
            if(sum < sampleNum){
                edgeSamples.add(edge);
            }
            else {
                randomNum = rand.nextInt(sum);
                if(randomNum < sampleNum){
                    replaceIndex = rand.nextInt(sampleNum);
                    edgeSamples.set(replaceIndex, edge);
                }
            }
            sum ++;
        }
        return edgeSamples;
    }


}
