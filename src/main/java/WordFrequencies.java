import edu.duke.*;
import java.util.ArrayList;

public class WordFrequencies {
    private static ArrayList<String> myWords;
    private static ArrayList<Integer> myFreqs;

    public WordFrequencies() {
        myWords = new ArrayList<String>();
        myFreqs = new ArrayList<Integer>();
    }

    public static void findUnique() {
        FileResource resource = new FileResource("test.txt");

        for(String s : resource.words()) {
            s = s.toLowerCase();
            int index = myWords.indexOf(s);
            if (index == -1) {
                myWords.add(s);
                myFreqs.add(1);
            }
            else {
                int freq = myFreqs.get(index);
                myFreqs.set(index, freq + 1);
            }
        }
    }

    public static int findMax() {
        int max = myFreqs.get(0);
        int maxIndex = 0;
        for(int i = 0; i < myFreqs.size(); i++) {
            if (myFreqs.get(i) > max) {
                max = myFreqs.get(i);
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static void tester() {
        findUnique();
        System.out.println("Number of unique words: " + myWords.size());
        int index = findMax();
        System.out.println("Max word/freq: " + myWords.get(index) + " " + myFreqs.get(index));

        for (String w: myWords) {
            int wordFr = myWords.indexOf(w);
            System.out.println(w + " occurs " + myFreqs.get(wordFr) + " times");
        }
    }

    public static void main(String[] args) {
        WordFrequencies kek = new WordFrequencies();
        kek.tester();
    }
}
