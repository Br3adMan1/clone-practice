package test;


import java.util.HashMap;

public class DictionaryManager {
    private static DictionaryManager d;
    private HashMap<String,Dictionary> bookDictionaries;

    public DictionaryManager() {
        this.bookDictionaries = new HashMap<>();
    }

    public static DictionaryManager get() {
        if(d == null)
            d = new DictionaryManager();
        return d;
    }

    private Dictionary getOrCreateDictionary(String book) {
        return bookDictionaries.computeIfAbsent(book, k -> new Dictionary(book));
    }
    public boolean query(String... books) {
        boolean exists = false;
        for (int i = 0; i< books.length-1; i++){
            Dictionary dictionary = getOrCreateDictionary(books[i]);
            exists |= dictionary.query(books[books.length-1]);
        }
        return exists;
    }

    public boolean challenge(String... books) {
        boolean exists = false;
        for (int i = 0; i< books.length-1; i++){
            Dictionary dictionary = getOrCreateDictionary(books[i]);
            exists |= dictionary.challenge(books[books.length-1]);
        }
        return exists;
    }

    public int getSize() {
        return bookDictionaries.size();
    }
}
