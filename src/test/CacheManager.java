package test;


import java.util.HashSet;

public class CacheManager {
    private int size;
	private CacheReplacementPolicy crp;
    private HashSet<String> words;

    public CacheManager(int size, CacheReplacementPolicy crp) {
        this.size = size;
        this.crp = crp;
        words = new HashSet<String>();
    }

    public boolean query(String s) {
        return words.contains(s);
    }


    public void add(String s) {
        if(this.words.size() == size) {
            String toBeRemoved = crp.remove();
            words.remove(toBeRemoved);
        }
        crp.add(s);
        words.add(s);
    }
}
