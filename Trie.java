import java.io.*;
import java.util.*;

public class Trie implements Tree {
    private TrieNode root = new TrieNode();
    private ArrayList<String> stringSuggestions = new ArrayList<String>();

    public void insert(String key) {
        String lowerKey = key.toLowerCase();
        int level, length = lowerKey.length(), index;
        TrieNode temp = root;
        for (level = 0; level < length; level++) {
            index = lowerKey.charAt(level) - 'a';
            if (index == -58) {
                index = 26;
            }
            if (temp.children[index] == null) {
                temp.children[index] = new TrieNode();
            }
            temp = temp.children[index];
        }
        temp.isEndOfWord = true;
    }

    public boolean search(String key) {
        String lowerKey = key.toLowerCase();
        int level, length = key.length(), index;
        TrieNode temp = root;
        for (level = 0; level < length; level++) {
            index = lowerKey.charAt(level) - 'a';
            if (index == -58) {
                index = 26;
            }
            if (temp.children[index] == null) {
                return false;
            }
            temp = temp.children[index];
        }
        return (temp != null && temp.isEndOfWord);
    }

    public boolean search(char[] key) {
        return search(key.toString());
    }

}