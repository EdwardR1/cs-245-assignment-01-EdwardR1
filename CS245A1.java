import java.util.*;
import java.io.*;
import java.net.*;

public class CS245A1 {
    public static Trie trie = new Trie();
    public static BST bst = new BST();
    public static Suggestions suggestions = null;

    public static void main(String[] args) {
        Properties properties = new Properties();
        InputStream fis;
        try {
            String filePath = new File("").getAbsolutePath();
            filePath = filePath.concat("/a1properties.txt");
            fis = new FileInputStream(filePath);
            properties.load(fis);
        } catch (IOException e) {
            properties.setProperty("storage", "trie");
        }

        if (properties.getProperty("storage").equalsIgnoreCase("trie")) {
            create(trie);
        } else if (properties.getProperty("storage").equalsIgnoreCase("bst")) {
            create(bst);
        } else {
            create(trie);
        }

        String inputFileName = "";
        String outputFileName = "";
        if (args.length < 2) {
            System.out.println("Missing at least one parameter, defaulting to input.txt and output.txt");
            inputFileName = "input.txt";
            outputFileName = "output.txt";
        } else {
            inputFileName = args[0];
            outputFileName = args[1];
        }

        try {
            if (!checkFileExists(inputFileName)) {
                clearWrite(inputFileName);
            }
            if (!checkFileExists(outputFileName)) {
                clearWrite(outputFileName);
            }
        } catch (Exception e) {
        }

        clearWrite(outputFileName);
        readInput(inputFileName, outputFileName);
        System.out.println("Input file read!");
        System.out.println("Output file written to!");

    }

    public static void create(Tree tree) {
        Scanner sc = null;
        try {
            URL url = new URL("https://raw.githubusercontent.com/magsilva/jazzy/master/resource/dict/english.0");
            sc = new Scanner(url.openStream());
            while (sc.hasNext()) {
                if (sc.nextLine().length() == 0) {
                    String nextLine = sc.nextLine();
                    tree.insert(nextLine.toLowerCase());
                }
            }
            sc.close();
        } catch (IOException e) {
            try {
                String filePath = new File("").getAbsolutePath();
                filePath = filePath.concat("/english.0");
                File file = new File(filePath);
                sc = new Scanner(file);
                while (sc.hasNext()) {
                    if (sc.nextLine().length() == 0) {
                        String nextLine = sc.nextLine();
                        tree.insert(nextLine.toLowerCase());
                    }
                }
                sc.close();
                System.out.println("File used");
            } catch (FileNotFoundException ec) {
                System.out.println("File not found! english.0 being created!");
                clearWrite("/english.0");
                System.out.println("File english.0 created!");
            }
        }
        suggestions = new Suggestions(tree);
    }

    public static boolean checkFileExists(String filename) {
        String directory = System.getProperty("user.dir");
        String path = directory + File.separator + filename;
        File file = new File(path);
        if (file.exists()) {
            return true;
        } else {
            System.out.println("File " + filename + " does not exist!");
            System.out.println("File " + filename + " created!");
            return false;
        }
    }

    public static void readInput(String filename, String outputFileName) {
        String directory = System.getProperty("user.dir");
        String path = directory + File.separator + filename;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            while (line != null) {
                String temp = line.toLowerCase().trim();
                suggestions.writeOutput(temp, outputFileName);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearWrite(String filename) {
        String directory = System.getProperty("user.dir");
        String path = directory + File.separator + filename;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, false))) {
            bw.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}