import java.util.*;
import java.io.*;

public class Suggestions {
    Tree tree = null;
    ArrayList<String> stringSuggestions = new ArrayList<String>();

    public Suggestions(Tree tree) {
        this.tree = tree;
    }

    public ArrayList<String> createSuggestions(String key) {
        int size = 0;
        int LIMIT = 3;
        while (stringSuggestions.size() < LIMIT) {
            if ((!adjacent(key) && !substitute(key) && !add(key) && !remove(key)) || size == LIMIT) {
                return stringSuggestions;
            }
        }
        return stringSuggestions;
    }

    private char[] stringToCharArray(String key) {
        char[] characters = new char[key.length()];
        for (int i = 0; i < key.length(); i++) {
            characters[i] = key.charAt(i);
        }
        return characters;
    }

    private String charArrayToString(char[] chars) {
        StringBuilder sb = new StringBuilder();
        for (char chr : chars) {
            sb.append(chr);
        }
        return sb.toString();
    }

    // Switch adjacent characters
    public boolean adjacent(String key) {
        int i = 0;
        char[] characters = stringToCharArray(key);
        for (i = 0; i < key.length() - 1; i++) {
            characters = stringToCharArray(key);
            int j = i + 1;
            char temp = characters[i];
            characters[i] = characters[j];
            characters[j] = temp;
            String str = charArrayToString(characters);
            if (tree.search(str) && !stringSuggestions.contains(str)) {
                stringSuggestions.add(str);
                return true;
            }
        }
        if (i == key.length() - 1) {
            int j = 0;
            char temp = characters[i];
            characters[i] = characters[j];
            characters[j] = temp;
            String str = charArrayToString(characters);
            if (tree.search(str) && !stringSuggestions.contains(str)) {
                stringSuggestions.add(str);
                return true;
            }
        }
        return false;

    }

    public boolean substitute(String key) {
        char[] characters = stringToCharArray(key);
        for (int i = 0; i < characters.length; i++) {
            characters = stringToCharArray(key);
            for (int j = 0; j < 26; j++) {
                characters[i] = (char) (j + 97);
                String str = charArrayToString(characters);
                if (tree.search(str) && !stringSuggestions.contains(str)) {
                    stringSuggestions.add(str);
                    return true;
                }
            }
        }
        return false;
    }

    private char[] grow1(char[] chars) {
        char[] chars2 = new char[chars.length + 1];
        for (int i = 0; i < chars.length; i++) {
            chars2[i] = chars[i];
        }
        return chars2;
    }

    private char[] addLetter(char[] newWord, int pos){
        char[] temp = newWord;
        for(int i=0; i<26; i++){
            temp[pos] = (char)(i + 97);
            if(tree.search(charArrayToString(temp))){
                return temp;
            }
        }
        newWord[pos] = newWord[pos - 1];
        return newWord;
    }

    public boolean add(String key) {
        char[] original = stringToCharArray(key);
        char[] newWord = grow1(stringToCharArray(key));
        reset(newWord, original);
        for(int j = newWord.length - 1; j > 1; j--){
            newWord = addLetter(newWord, j);
            if(tree.search(charArrayToString(newWord)) && !stringSuggestions.contains(charArrayToString(newWord))){
                stringSuggestions.add(charArrayToString(newWord));
                return true;
            }
        }
        return false;
    }

    private void reset(char[] newWord, char[] original){
        for(int i=0; i<original.length; i++){
            newWord[i] = original[i];
        }
    }

    public boolean remove(String key) {
        char[] characters = stringToCharArray(key);
        for (int i = 0; i < characters.length; i++) {
            characters = stringToCharArray(key);
            for (int j = i; j < characters.length - 1; j++) {
                characters[j] = characters[j + 1];
            }
            String str = charArrayToString(characters);
            str = str.substring(0, str.length() - 1);
            if (tree.search(str) && !stringSuggestions.contains(str)) {
                stringSuggestions.add(str);
                return true;
            }
        }
        return false;
    }

    public void writeOutput(String word, String filename) {
        ArrayList<String> suggestions = new ArrayList<String>();
        String directory = System.getProperty("user.dir");
        String path = directory + File.separator + filename;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            if (tree.search(word)) {
                bw.write(word + "\n");
            } else {
                suggestions = createSuggestions(word);
                int counter = 0;
                for (String suggestion : suggestions) {
                    if (counter < 3) {
                        bw.write(suggestion + " ");
                        counter++;
                    }
                }
                if (!suggestions.isEmpty()) {
                    bw.write("\n");
                }
                if (suggestions.size() == 0) {
                    bw.write("No suggestions to provide!\n");
                }
            }
            suggestions.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}