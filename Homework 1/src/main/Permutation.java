package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

public class Permutation {
    int size;
    ArrayList<Integer> nums;
    public Permutation(int size) {
        this.size = size;
        this.nums = new ArrayList<>();
        permute();
    }
    /**
     * Generate a permutation of a ascending list
     */
    public void permute() {
        for (int i = 0; i < size; i++) {
            nums.add(i);
        }
        Collections.shuffle(nums);
    }

}
