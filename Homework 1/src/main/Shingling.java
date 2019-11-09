package main;

import java.io.FileNotFoundException;
import java.util.Set;
import java.util.TreeSet;

public class Shingling {
    private int k;  // Shingle size
    private Set<Integer> shingles;

    public Shingling(int k, String file) throws FileNotFoundException {
        this.k = k;
        this.shingles = new TreeSet<Integer>();
        generate(file);
    }

    public Set<Integer> getShingles() {
        return shingles;
    }

    public void generate(String file) throws FileNotFoundException {
        String novel = FileHandler.read(file);
        String text = novel.toLowerCase().
                replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&;*（）——+|{}【】‘；：”“’。，、？|-]", "");

        for (int i = 0; i < text.length() - k; i++) {
            int shingle = text.substring(i, i + k).hashCode();
            if (!shingles.contains(shingle))
                shingles.add(shingle);
        }
    }
}
