import java.util.*;
import java.util.Map.Entry;

public class TriangleCount {
    public List<String> vertexSet;
    public List<List<String>> edgeSet;
    public HashMap<String, List<String>> neighborSet;
    public List<Set<String>> triangleSet;

    public TriangleCount(List<List<String>> sampleEdges){
        edgeSet = sampleEdges;
        vertexSet = new LinkedList<>();
        for(List<String> edge:sampleEdges){
            for(String vertex:edge){
                if(!vertexSet.contains(vertex)){
                    vertexSet.add(vertex);
                }
            }
        }
    }

    //generate neighbor set of every vertex
    public HashMap<String, List<String>> generateNeighbor(){
        neighborSet = new HashMap<>();
        for(List<String> edge:edgeSet){
            String a = edge.get(0);
            String b = edge.get(1);
            if(!neighborSet.containsKey(a)){
                LinkedList<String> addItem = new LinkedList<>();
                addItem.add(b);
                neighborSet.put(a, addItem);
            }
            else {
                neighborSet.get(a).add(b);
            }
            if(!neighborSet.containsKey(b)){
                LinkedList<String> addItem = new LinkedList<>();
                addItem.add(a);
                neighborSet.put(b, addItem);
            }
            else{
                neighborSet.get(b).add(a);
            }
        }
        return neighborSet;
    }

    //count the triangle number
    public Map<String, Integer> countTriangle(){
        Map<String, Integer> trianglesNum = new TreeMap<>();
        Map<String, List<String>> neighborSet = generateNeighbor();
        List<String> vertexes = new LinkedList<>(neighborSet.keySet());
        triangleSet = new LinkedList<>();

        //search intersection(the third vertex of a triangle) from i and its neighbor
        for(int i=0; i<vertexSet.size(); i++){
            List<Set<String>> vertexTriangle = new LinkedList<>();
            for(String secondVertex:neighborSet.get(vertexes.get(i))){
                    LinkedList<String> intersection = new LinkedList<>();
                    intersection.addAll(neighborSet.get(vertexes.get(i)));
                    intersection.retainAll(neighborSet.get(secondVertex));
                    if(!(intersection==null)){
                        for(String thirdVertex:intersection){
                            Set<String> triangle = new HashSet<>();
                            triangle.add(vertexes.get(i));
                            triangle.add(secondVertex);
                            triangle.add(thirdVertex);
                            if(!vertexTriangle.contains(triangle)){
                                vertexTriangle.add(triangle);
                            }
                            if(!triangleSet.contains(triangle)){
                                triangleSet.add(triangle);
                            }
                    }
                }
            }
            trianglesNum.put("Vertex"+vertexes.get(i), vertexTriangle.size());
        }
        trianglesNum.put("globalTriangle", triangleSet.size());

        //sort the local triangle number
        //transform trianglesNumList.entrySet() into trianglesNumList
        List<Map.Entry<String,Integer>> trianglesList = new ArrayList<Map.Entry<String,Integer>>(trianglesNum.entrySet());
        //sort by comparator
        Collections.sort(trianglesList,new Comparator<Map.Entry<String,Integer>>() {
            public int compare(Entry<String, Integer> o1,
                               Entry<String, Integer> o2) {
                return -o1.getValue().compareTo(o2.getValue());
            }});

        Map<String, Integer> triangleResult = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : trianglesList) {
            triangleResult.put(entry.getKey(), entry.getValue());
        }

        return triangleResult;
    }

}

