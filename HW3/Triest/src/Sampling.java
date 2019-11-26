import java.util.*;

public class Sampling {
    public List<List<String>> edgePairs;
    public List<List<String>> edgeSamples;

    public Sampling(String[] edges, int streamNum){
        int count = 0;
        edgePairs = new LinkedList<>();
        if(count<streamNum){
            for(String edge:edges){
                List<String> pair = new LinkedList<>();
                String[] items = edge.split(" ");
                if(items.length >= 2){
                    pair.add(items[0]);
                    pair.add(items[1]);
                }
                edgePairs.add(pair);
                count++;
            }
        }
    }

    //generate sample edges
    public List<List<String>> edgeSamplePairs(int sampleNum){
        edgeSamples = new LinkedList<>();
        int sum = 0;
        int randomNum;
        int replaceIndex;
        Random rand = new Random();
        for(List edge:edgePairs){
            // if t<m
            if(sum < sampleNum-1){
                edgeSamples.add(edge);
            }
            // if t>m
            else if(sum == sampleNum-1){
                edgeSamples.add(edge);
                System.out.println("EdgeSamples are: "+edgeSamples);
            }
            else {
                randomNum = rand.nextInt(sum);
                // the probobility of m/t to replace one edge
                if(randomNum < sampleNum){
                    // random replace one edge
                    replaceIndex = rand.nextInt(sampleNum);
                    System.out.println(edge+"replace"+edgeSamples.get(replaceIndex));
                    edgeSamples.set(replaceIndex, edge);
                }
            }
            sum ++;
        }
        System.out.println("Final EdgeSamples are: "+edgeSamples);
        return edgeSamples;
    }


}
