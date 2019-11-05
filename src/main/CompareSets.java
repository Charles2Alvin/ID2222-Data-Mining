package main;

import java.util.HashSet;
import java.util.Set;

public class CompareSets {
    public static double jaccardSimilarity(Set<Integer> set1, Set<Integer> set2){
        Integer intersection = intersectionCount(set1, set2);
        Double intersection1 = new Double(intersection);
//        System.out.println(intersection);
        Integer unionset = unionsetCount(set1, set2);
        Double unionset1 = new Double(unionset);
//        System.out.println(unionset);
//        System.out.println(set1);
//        System.out.println(set2);
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

//    public static void main(String[] args) {
//        main.CompareSets compareSets=new main.CompareSets();
//        Set set1 = new HashSet();
//        Set set2 = new HashSet();
//        int a = 1;
//        int b = 2;
//        int c = 3;
//        int d = 4;
//        int e = 5;
//        int f = 6;
//        set1.add(a);
//        set1.add(b);
//        set1.add(c);
//        set1.add(d);
//        set2.add(c);
//        set2.add(d);
//        set2.add(e);
//        set2.add(f);
//
//        System.out.println(compareSets.jaccardSimilarity(set1, set2));
//
//    }

}
