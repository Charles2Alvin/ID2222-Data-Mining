import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TriestBase {
    public double coefficient;

    public Map<String, Integer> triangleEstimate(Integer sampleEdgeNum, Integer totalEdgeNum, Map<String, Integer> sampleTriangleSet){
        Map<String, Integer> estimateTriangleSet = new LinkedHashMap<>();
        Double totalDouble = (double)totalEdgeNum;
        Double sampleDouble = (double)sampleEdgeNum;
        coefficient = totalDouble*(totalDouble-1)*(totalDouble-2)/(sampleDouble*(sampleDouble-1)*(sampleDouble-2));
        if(coefficient < 1){
            estimateTriangleSet = sampleTriangleSet;
        }
        else {
            for (Map.Entry<String, Integer> entry : sampleTriangleSet.entrySet()){
                estimateTriangleSet.put("Vertex("+entry.getKey()+")", (int)(entry.getValue()*coefficient));
            }
        }
        return estimateTriangleSet;
    }
}
