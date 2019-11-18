public class TrieNode {
    
    public TrieNode[] children;
    // a-z and '
    private final int ALPHABET_SIZE = 27;
    public boolean isEndOfWord;

    public TrieNode() {
        isEndOfWord = false;
        children = new TrieNode[ALPHABET_SIZE];
        for(int i=0; i<ALPHABET_SIZE; i++){
            children[i] = null;
        }
    }

}