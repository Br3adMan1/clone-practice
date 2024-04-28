package test;
import java.util.ArrayDeque;
import java.util.Deque;

public class LRU implements CacheReplacementPolicy {
    private Deque<String> queue;

    public LRU() {
        queue = new ArrayDeque<>();
    }
    @Override
    public void add(String word) {
        if (queue.contains(word)) {
            queue.remove(word);
        }
        queue.offerFirst(word);
    }

    @Override
    public String remove() {
        return queue.pollLast();
    }
}
