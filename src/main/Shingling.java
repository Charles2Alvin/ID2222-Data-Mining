package main;

import java.io.FileNotFoundException;
import java.util.Set;
import java.util.TreeSet;

public class Shingling {
    private NovelReader novelReader;
    private int k;
    private Set<String> shingleSet;

    public Shingling(int k, NovelReader novelReader) {
        this.k = k;
        this.novelReader = novelReader;
        this.shingleSet = new TreeSet<String>();
    }

    public Set<String> getShingleSet() {
        return shingleSet;
    }

    public void generate(String file) throws FileNotFoundException {
        String novel = novelReader.read(file);
        String text = novel.toLowerCase().
                replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&;*（）——+|{}【】‘；：”“’。，、？|-]", "");

        for (int i = 0; i < text.length() - k; i++) {
            shingleSet.add(text.substring(i, i + k));
        }

    }
}
