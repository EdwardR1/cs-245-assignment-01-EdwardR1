import java.io.*;
import java.util.*;

public class BST implements Tree {
    public BSTNode root;
    public ArrayList<String> stringSuggestions = new ArrayList<>();

    public BST() {
        root = null;
    }

    public void insert(String key) {
        root = insert(key, root);
    }

    public BSTNode insert(String key, BSTNode node) {
        if (node == null) {
            return new BSTNode(key);
        } else if (node.data.compareTo(key) < 0) {
            node.right = insert(key, node.right);
        } else {
            node.left = insert(key, node.left);
        }
        return node;
    }

    public boolean search(String key) {
        return search(key, root);
    }

    public boolean search(String key, BSTNode node) {
        if (node == null) {
            return false;
        }
        if (node.data.compareTo(key) == 0) {
            return true;
        } else if (node.data.compareTo(key) < 0) {
            return search(key, node.right);
        } else {
            return search(key, node.left);
        }
    }

}