package main;

import java.util.HashSet;
import java.util.Set;

public class CompareSets {
    public static double jaccardSimilarity(Set<Integer> set1, Set<Integer> set2){
        Integer intersection = intersectionCount(set1, set2);
        Double intersection1 = new Double(intersection);
        Integer unionset = unionsetCount(set1, set2);
        Double unionset1 = new Double(unionset);
        double jaccardSimilarity = intersection1/unionset1;
        return jaccardSimilarity;
    }
    public static Integer intersectionCount(Set<Integer> set1, Set<Integer> set2){
        Set set1Copy=new HashSet(set1);
        set1Copy.retainAll(set2);
        return set1Copy.size();
    }
    public static Integer unionsetCount(Set<Integer> set1, Set<Integer> set2){
        Set set1Copy=new HashSet(set1);
        set1Copy.addAll(set2);
        return set1Copy.size();
    }



}
