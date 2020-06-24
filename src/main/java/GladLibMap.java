import java.util.*;
import edu.duke.FileResource;
import edu.duke.URLResource;

/*
* Creates random story using template text file.
* Template contains <tags> with category names inside. Class method recognises tags and replaces them
* with random words from the appropriate category.
* Custom data structure RandomisedQueue is used because it return and remove its element
* and this behaviour allows to avoid using the same word from the category twice.
* Some additional analytical methods allows to count replaces words,
* used categories and words in them, etc.
*/

public class GladLibMap {
	private HashMap<String, RandomizedQueue<String>> map;
	private HashMap<String, Integer> wordCounters;
	private HashSet<String> usedCategories;
	private Random myRandom;

	private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
	private static String dataSourceDirectory = "data";

	public GladLibMap() {
		map = new HashMap<String, RandomizedQueue<String>>();
		usedCategories = new HashSet<String>();
		wordCounters = new HashMap<String, Integer>();
		initializeFromSource(dataSourceDirectory);
		myRandom = new Random();
	}

	public GladLibMap(String source){
		map = new HashMap<String, RandomizedQueue<String>>();
		usedCategories = new HashSet<String>();
		wordCounters = new HashMap<String, Integer>();
		initializeFromSource(source);
		myRandom = new Random();
	}

	private void initializeFromSource(String source) {
		String[] categories = {"adjective", "noun", "color", "country", "name", "animal", "timeframe", "verb", "fruit"};

		for (String s: categories) {
			RandomizedQueue<String> tmp = readIt(source + "/" + s + ".txt");
			map.put(s, tmp);
			wordCounters.put(s, tmp.size());
		}
	}

	public int totalWordsInMap() {
		int total = 0;

		for (Map.Entry<String, Integer> entry : wordCounters.entrySet()) {
			total += entry.getValue();
		}

		return total;
	}

	public int totalWordsConsidered() {
		int res = 0;

		for (String s: usedCategories) {
			res += wordCounters.get(s);
		}

		return res;
	}

	private String getSubstitute(String label) {
		if (label.equals("number")) {
			return "" + myRandom.nextInt(50) + 5;
		}

		if (map.containsKey(label)) {
			usedCategories.add(label);
			return map.get(label).dequeue();
		}

		return "**UNKNOWN**";
	}

	private String processWord(String w){
		int first = w.indexOf("<");
		int last = w.indexOf(">", first);
		if (first == -1 || last == -1){
			return w;
		}
		String prefix = w.substring(0, first);
		String suffix = w.substring(last + 1);
		String sub = getSubstitute(w.substring(first + 1, last));
		return prefix + sub + suffix;
	}

	private void printOut(String s, int lineWidth) {
		int charsWritten = 0;
		for(String w : s.split("\\s+")) {
			if (charsWritten + w.length() > lineWidth) {
				System.out.println();
				charsWritten = 0;
			}
			System.out.print(w + " ");
			charsWritten += w.length() + 1;
		}
	}

	private String fromTemplate(String source) {
		String story = "";
		int count = 0;
		if (source.startsWith("http")) {
			URLResource resource = new URLResource(source);
			for(String word : resource.words()) {
				story = story + processWord(word) + " ";
			}
		}
		else {
			FileResource resource = new FileResource(source);
			for(String word : resource.words()) {
				String nextWord = processWord(word);
				story = story + nextWord + " ";
				if (!word.equals(nextWord)) {
					count++;
				}
			}
		}
		System.out.println("Changed words amount: " + count + "\n");
		return story;
	}

	private RandomizedQueue<String> readIt(String source) {
		RandomizedQueue<String> list = new RandomizedQueue<String>();
		if (source.startsWith("http")) {
			URLResource resource = new URLResource(source);
			for(String line : resource.lines()) {
				list.enqueue(line);
			}
		} else {
			FileResource resource = new FileResource(source);
			for(String line : resource.lines()) {
				list.enqueue(line);
			}
		}
		return list;
	}

	public void makeStory() {
	    System.out.println("\n");
		String story = fromTemplate("src/main/java/data/madtemplate2.txt");
		printOut(story, 60);
		System.out.println("\n\nTotal words: " + totalWordsInMap());
		//System.out.println("\n\nTotal number of words in categories used: " + totalWordsConsidered());
		//System.out.println("Used categories: " + usedCategories.toString());
	}

	public static void main(String[] args) {
		GladLibMap test = new GladLibMap("src/main/java/data");
		test.makeStory();
	}
}

