import java.io.*;
import java.util.*;
import java.util.regex.*;

/*
* Counts all unique words and number of their occurrence in multiple files.
* Creates 2 HashMaps (filename, words) and (word, files with this word).
* Allows to find and count common words in a few files.
 */

public class WordsInFiles {
    private static HashMap<String, HashSet<String>> wordInFiles;
    private static HashMap<String, HashSet<String>> filesContent;
    private static ArrayList<String> allWords;

    public WordsInFiles(){
        wordInFiles = new HashMap<String, HashSet<String>>();
        filesContent = new HashMap<String, HashSet<String>>();
        allWords = new ArrayList<String>();
    }

    private void addWordsFromFile(File f) throws FileNotFoundException {
        Scanner scan = new Scanner(f);
        String filename = f.getName();
        HashSet<String> words = new HashSet<String>();
        while (scan.hasNextLine()) {
            String line = scan.nextLine().toLowerCase();
            for (String word : line.split("\\s+")) {
                word = word.replaceAll("[^\\w]", "");
                if (word != "") {
                    HashSet<String> fileNames = wordInFiles.containsKey(word)
                            ? wordInFiles.get(word)
                            : new HashSet<String>();

                    fileNames.add(filename);
                    wordInFiles.put(word, fileNames);
                    words.add(word.toLowerCase());
                }
                allWords.add(word);
            }
        }
        filesContent.put(filename, words);
    }

    public void whereIsWord(String word) {
        System.out.println("Word " + word.toUpperCase() + " appears in files: ");
        for (Map.Entry<String, HashSet<String>> entry : filesContent.entrySet()) {
            if (entry.getValue().contains(word)) {
                System.out.println(entry.getKey());
            }
        }
    }

    public static int wordsInNumFiles(int numberOfFiles) {
        int counter = 0;
        for (Map.Entry<String, HashSet<String>> entry : wordInFiles.entrySet()) {
            if (entry.getValue().size() == numberOfFiles) {
                counter++;
            }
        }
        return counter;
    }

    public static HashMap<String, Integer> countFrequencies(ArrayList<String> list) {
        HashMap<String, Integer> hm = new HashMap<String, Integer>();

        for (String i : list) {
            Pattern p = Pattern.compile(".*?[a-zA-Z].*?");
            Matcher m = p.matcher(i);
            
            if (m.find()) {
                Integer j = hm.get(i);
                hm.put(i, (j == null) ? 1 : j + 1);
            }
        }
        return hm;
    }

    private void showMaxOccurrence(HashMap<String, Integer> hm) {
        Map.Entry<String, Integer> maxEntry = null;

        for (Map.Entry<String, Integer> val : hm.entrySet()) {
            if ((maxEntry == null || val.getValue().compareTo(maxEntry.getValue()) > 0) && !val.getKey().equals("")) {
                maxEntry = val;
            }
            //System.out.println("Element " + val.getKey() + " " + "occurs" + ": " + val.getValue() + " times");
        }
        System.out.println("\nMax value is " + maxEntry.getValue() + " for word " + maxEntry.getKey().toUpperCase() +
                ". \nFound in files: ");

        for (Map.Entry<String, HashSet<String>> entry : filesContent.entrySet()) {
            if (entry.getValue().contains(maxEntry.getKey())) {
                System.out.println(entry.getKey());
            }
        }
    }


    private static void test() throws FileNotFoundException {
        WordsInFiles test = new WordsInFiles();

        File file1 = new File("src/main/java/files/hamlet.txt");
        File file2 = new File("src/main/java/files/likeit.txt");
        File file3 = new File("src/main/java/files/macbeth.txt");
        File file4 = new File("src/main/java/files/romeo.txt");
        File file5 = new File("src/main/java/files/caesar.txt");
        File file6 = new File("src/main/java/files/errors.txt");
        File file7 = new File("src/main/java/files/confucius.txt");

        test.addWordsFromFile(file1);
        test.addWordsFromFile(file2);
        test.addWordsFromFile(file3);
        test.addWordsFromFile(file4);
        test.addWordsFromFile(file5);
        test.addWordsFromFile(file6);
        test.addWordsFromFile(file7);

        HashMap<String, Integer> wordFrequency = countFrequencies(allWords);
        test.showMaxOccurrence(wordFrequency);
        test.whereIsWord("of");

        System.out.println("Number of common words in 4 files is " + wordsInNumFiles(4));
    }

    public static void main(String[] args) throws FileNotFoundException {
        test();
    }
}
