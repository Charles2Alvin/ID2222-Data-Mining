import java.util.*;

public class TriestIMPR {
    public List<List<String>> edgePairs;
    public List<List<String>> edgeSamples;
    public int sampleNum;

    public TriestIMPR(int sampleNumber, int streamNum, String[] edges){
        sampleNum = sampleNumber;
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
    public Map<String, Integer> edgeEstimatePairs(){
        edgeSamples = new LinkedList<>();
        Map<String, Integer> countSampleTriangles;
        Map<String, Integer> triangleResult;
        Map<String, Double> triangleResultDouble = new LinkedHashMap<>();
        int sum = 0;
        int randomNum;
        int replaceIndex;
        Random rand = new Random();
        for(List<String> edge:edgePairs){

            // if t<m
            if(sum < sampleNum-1){
                edgeSamples.add(edge);
            }
            else if (sum == sampleNum-1){
                edgeSamples.add(edge);
                System.out.println("EdgeSamples are: "+edgeSamples);
                TriangleCount triangleCount = new TriangleCount(edgeSamples);
                triangleResult = triangleCount.countTriangle();
                for(Map.Entry<String, Integer> entry: triangleResult.entrySet()){
                    triangleResultDouble.put(entry.getKey(), (double)entry.getValue());
                }
            }
            // if t>m
            else {
                TriangleCount triangleCount = new TriangleCount(edgeSamples);
                double rate = (sum -1)*(sum-2)/((sampleNum-1)*(sampleNum-2));

                // the probobility of m/t to replace one edge
                randomNum = rand.nextInt(sum);
                if(randomNum < sampleNum){
                    triangleCount.countTriangle();
                    List<Set<String>> triangleSet = triangleCount.triangleSet;

                    // randomly replace one edge
                    replaceIndex = rand.nextInt(sampleNum);
                    System.out.println(edge+"replace"+edgeSamples.get(replaceIndex));
                    edgeSamples.set(replaceIndex, edge);

                    TriangleCount triangleNewCount = new TriangleCount(edgeSamples);
                    triangleNewCount.countTriangle();
                    List<Set<String>> triangleNewSet = triangleNewCount.triangleSet;
                    triangleSet.retainAll(triangleNewSet);
                    triangleNewSet.removeAll(triangleSet);
                    for(Set<String> triangle:triangleNewSet){
                        triangleResultDouble.put("globalTriangle", triangleResultDouble.get("globalTriangle")+rate);
                        for(String vertex:triangle){
                            if(!triangleResultDouble.containsKey(vertex)){
                                triangleResultDouble.put(vertex, rate);
                            }
                            else {
                                triangleResultDouble.put(vertex, triangleResultDouble.get(vertex)+rate);
                            }
                        }
                    }
                }
                else {
                    //UpdateCounters is called unconditionally for each element
                    //on the stream, before the algorithm decides whether
                    //or not to insert the edge into S.
                    HashMap<String, List<String>> neighborSet = triangleCount.generateNeighbor();
                    for(Map.Entry<String, List<String>> entry : neighborSet.entrySet()){
                        if(entry.getValue().containsAll(edge)){
                            triangleResultDouble.put("globalTriangle", triangleResultDouble.get("globalTriangle")+rate);
                            if(!triangleResultDouble.containsKey(entry.getKey())){ triangleResultDouble.put(entry.getKey(), rate); }
                            else { triangleResultDouble.put(entry.getKey(), triangleResultDouble.get(entry.getKey())+rate); }
                            if(!triangleResultDouble.containsKey(edge.get(0))){ triangleResultDouble.put(edge.get(0), rate); }
                            else {triangleResultDouble.put(edge.get(0), triangleResultDouble.get(edge.get(0))+rate); }
                            if(!triangleResultDouble.containsKey(edge.get(1))){ triangleResultDouble.put(edge.get(1), rate); }
                            else { triangleResultDouble.put(edge.get(1), triangleResultDouble.get(edge.get(1))+rate); }
                        }
                    }
                }
            }
            sum ++;
        }

        if(sum <= sampleNum){
            TriangleCount triangleCount = new TriangleCount(edgeSamples);
            countSampleTriangles = triangleCount.countTriangle();
            Map<String, Integer> countTriangles = new LinkedHashMap<>();
            for(Map.Entry<String, Integer> entry: countSampleTriangles.entrySet()){
                countTriangles.put("Vertex("+entry.getKey()+")", entry.getValue());
            }
            return countTriangles;
        }
        System.out.println("Final EdgeSamples are: "+edgeSamples);

        //sort the triangle number
        //transform trianglesNumList.entrySet() into trianglesNumList
        List<Map.Entry<String,Double>> trianglesList = new ArrayList<Map.Entry<String,Double>>(triangleResultDouble.entrySet());
        //sort by comparator
        Collections.sort(trianglesList,new Comparator<Map.Entry<String,Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                return -o1.getValue().compareTo(o2.getValue());
            }});
        Map<String, Integer> triangleFinalResult = new LinkedHashMap<>();
        for(Map.Entry<String, Double> entry: trianglesList){
            triangleFinalResult.put("Vertex("+entry.getKey()+")", (int)(double)entry.getValue());
        }
        return triangleFinalResult;
    }
}

