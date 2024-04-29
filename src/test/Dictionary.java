package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Dictionary {
    private CacheManager existingWordsCache;
    private CacheManager nonExistingWordsCache;
    private BloomFilter bloomFilter;
    private String[] fileNames;

    public Dictionary(String... fileNames) {
        existingWordsCache = new CacheManager(400, new LRU());
        nonExistingWordsCache = new CacheManager(100, new LFU());
        bloomFilter = new BloomFilter(256, "MD5", "SHA1");
        this.fileNames = fileNames.clone();

        for (String fileName : fileNames) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] words = line.split("\\s+");
                    for (String word : words) {
                        existingWordsCache.add(word);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean query(String word) {
        boolean existsInExistingCache = existingWordsCache.query(word);
        if (existsInExistingCache) {
            return true;
        }

        boolean existsInNonExistingCache = nonExistingWordsCache.query(word);
        if (existsInNonExistingCache) {
            return false;
        }

        boolean existsInBloomFilter = bloomFilter.contains(word);
        if (existsInBloomFilter) {
            existingWordsCache.add(word);
        } else {
            nonExistingWordsCache.add(word);
        }
        return existsInBloomFilter;
    }

    public boolean challenge(String word) {
        try {
            boolean wordExists = IOSearcher.search(word, fileNames);
            if (wordExists) {
                existingWordsCache.add(word);
            } else {
                nonExistingWordsCache.add(word);
            }
            return wordExists;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}