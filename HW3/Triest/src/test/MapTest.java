package test;

import java.util.HashMap;
import java.util.LinkedList;

public class MapTest {
    public HashMap<Integer, LinkedList<Integer>> map;
    public LinkedList<Integer> value;

    public HashMap<Integer, LinkedList<Integer>> addMapItem(){
        value.add(2);
        map.put(1, value);
        map.get(1).add(3);
        return map;
    }

    public static void main(String[] args) {
//        MapTest mapTest = new MapTest();
//        System.out.println(mapTest.addMapItem());
        int sampleEdgeNum = 274;
        int totalEdgeNum = 2742;
        double total = totalEdgeNum*(totalEdgeNum-1)*(totalEdgeNum-2);
        long sample = sampleEdgeNum*(sampleEdgeNum-1)*(sampleEdgeNum-2);
//        float coefficient = total/sample;
        System.out.println(total);
    }
}
