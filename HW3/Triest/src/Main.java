import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        String[] edgeData = FileHandler.readLines("resource/jazz 2742 17899.txt");
        int sampleEdgeNum = 274;
        int streamEdgeNum = 2742;
        System.out.println("The streamEdgeNumber is:" + streamEdgeNum);
        System.out.println("The sampleEdgeNumber is:" + sampleEdgeNum);

        //base algorithm
        Sampling sampling = new Sampling(edgeData, streamEdgeNum);
        List<List<String>> sampleEdges = sampling.edgeSamplePairs(sampleEdgeNum);
        TriangleCount triangleCount = new TriangleCount(sampleEdges);
        Map<String, Integer> countSampleTriangles = triangleCount.countTriangle();
        TriestBase triestBase = new TriestBase();
        Map<String, Integer> trianglesBASE = triestBase.triangleEstimate(sampleEdgeNum, streamEdgeNum, countSampleTriangles);

        //impr algorithm
        TriestIMPR triestIMPR = new TriestIMPR(sampleEdgeNum, streamEdgeNum, edgeData);
        Map<String, Integer> trianglesIMPR = triestIMPR.edgeEstimatePairs();

        System.out.println("The BASE estimate of global and local triangle are: " + trianglesBASE);
        System.out.println("The IMPR estimate of global and local triangle are: " + trianglesIMPR);
    }
}
