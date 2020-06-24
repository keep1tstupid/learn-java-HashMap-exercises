import edu.duke.*;
import java.util.*;

public class GladLib {

	private RandomizedQueue<String> adjectiveList;
	private RandomizedQueue<String> nounList;
	private RandomizedQueue<String> colorList;
	private RandomizedQueue<String> countryList;
	private RandomizedQueue<String> nameList;
	private RandomizedQueue<String> animalList;
	private RandomizedQueue<String> timeList;
	private RandomizedQueue<String> verbList;
	private RandomizedQueue<String> fruitList;

	private Random myRandom;

	private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
	private static String dataSourceDirectory = "data";

	public GladLib() {
		initializeFromSource(dataSourceDirectory);
		myRandom = new Random();
	}

	public GladLib(String source){
		initializeFromSource(source);
		myRandom = new Random();
	}

	private void initializeFromSource(String source) {
		adjectiveList = readIt(source + "/adjective.txt");
		nounList = readIt(source + "/noun.txt");
		colorList = readIt(source + "/color.txt");
		countryList = readIt(source + "/country.txt");
		nameList = readIt(source + "/name.txt");
		animalList = readIt(source + "/animal.txt");
		timeList = readIt(source + "/timeframe.txt");
		verbList = readIt(source + "/verb.txt");
		fruitList = readIt(source + "/fruit.txt");
	}

	private String getSubstitute(String label) {
		if (label.equals("country")) {
			return countryList.dequeue();
		}
		if (label.equals("color")) {
			return colorList.dequeue();
		}
		if (label.equals("noun")) {
			return nounList.dequeue();
		}
		if (label.equals("name")) {
			return nameList.dequeue();
		}
		if (label.equals("adjective")) {
			return adjectiveList.dequeue();
		}
		if (label.equals("animal")) {
			return animalList.dequeue();
		}
		if (label.equals("timeframe")) {
			return timeList.dequeue();
		}
		if (label.equals("number")) {
			return "" + myRandom.nextInt(50) + 5;
		}
		if (label.equals("verb")) {
			return verbList.dequeue();
		}
		if (label.equals("fruit")) {
			return fruitList.dequeue();
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
	}

	public static void main(String[] args) {
		GladLib lol = new GladLib("src/main/java/data");
		lol.makeStory();
	}
}

