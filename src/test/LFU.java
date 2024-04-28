package test;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LFU implements CacheReplacementPolicy {
    private Map<String, Integer> frequencies;
    private Map<String, Integer> order;
    private int minFrequency;

    public LFU() {
        frequencies = new HashMap<>();
        order = new LinkedHashMap<>();
        minFrequency = 0;
    }

    @Override
    public void add(String word) {
        frequencies.put(word, frequencies.getOrDefault(word, 0) + 1);
        order.put(word, order.size());

        int wordFrequency = frequencies.get(word);
        if (wordFrequency < minFrequency) {
            minFrequency = wordFrequency;
        }
    }

    @Override
    public String remove() {
        if (order.isEmpty()) {
            return null;
        }
        String leastFrequentWord = null;
        int minFreq = Integer.MAX_VALUE;

        for (Map.Entry<String, Integer> entry : order.entrySet()) {
            String word = entry.getKey();
            int freq = frequencies.get(word);
            if (freq <= minFreq) {
                minFreq = freq;
                leastFrequentWord = word;
            }
        }
        frequencies.remove(leastFrequentWord);
        order.remove(leastFrequentWord);
        return leastFrequentWord;
    }
}