package main;

import java.io.FileNotFoundException;
import java.util.Set;
import java.util.TreeSet;

public class Shingling {
    private NovelReader novelReader;
    private int k;
    private Set<Integer> shingleSet;

    public Shingling(int k, NovelReader novelReader) {
        this.k = k;
        this.novelReader = novelReader;
        this.shingleSet = new TreeSet<Integer>();
    }

    public Set<Integer> getShingleSet() {
        return shingleSet;
    }

    public void generate(String file) throws FileNotFoundException {
        String novel = novelReader.read(file);
        String text = novel.toLowerCase().
                replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&;*（）——+|{}【】‘；：”“’。，、？|-]", "");

        for (int i = 0; i < text.length() - k; i++) {
            int shingle = text.substring(i, i + k).hashCode();
            if (!shingleSet.contains(shingle))
                shingleSet.add(shingle);
        }
    }
}
