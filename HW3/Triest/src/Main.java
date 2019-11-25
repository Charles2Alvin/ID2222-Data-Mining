import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        String[] edgeData = FileHandler.readLines("resource/jazz 2742 198 17899.txt");
        Sampling sampling = new Sampling(edgeData);
//        System.out.println(sampling.getEdgePairs());
        int sampleEdgeNum = 914;
        int totalEdgeNum = edgeData.length;
        List<List<String>> sampleEdges = sampling.edgeSamplePairs(sampleEdgeNum);
        TriangleCount triangleCount = new TriangleCount(sampleEdges);
        Map<String, List<String>> neighborSet = triangleCount.generateNeighbor();
        Map<String, Integer> countSampleTriangles = triangleCount.countTriangle();
        TriestBase triestBase = new TriestBase();
        Map<String, Integer> countEstimateTriangles = triestBase.triangleEstimate(sampleEdgeNum, totalEdgeNum, countSampleTriangles);
//        System.out.println("The " + sampleEdgeNum + " edgeSamples are: " + "\n" + sampleEdges);
//        System.out.println("The "+ TriangleCount.vertexSet.size()+ " Vertexes' neighbors are: " + "\n" + neighborSet);
//        System.out.println("The triangles are: " +  "\n" + TriangleCount.triangleSet);
//        System.out.println(TriangleCount.vertexSet);
//        System.out.println(TriangleCount.generateNeighbor());
        System.out.println("The datasetEdgeNumber is:" + edgeData.length);
        System.out.println("The sampleEdgeNumber is:" + sampleEdgeNum);
//        System.out.println("The sampleVertexNumber is:" + TriangleCount.vertexSet.size());
//        System.out.println("The sample global and local triangle are: " + countSampleTriangles);
        System.out.println("The estimate global and local triangle are: " + countEstimateTriangles);
//        System.out.println(triestBase.coefficient);
//        int globalTriangle = 0;
//        Map localTriangle;
//        globalTriangle, localTriangle = TriangleCount.getEstimate();
    }
}
