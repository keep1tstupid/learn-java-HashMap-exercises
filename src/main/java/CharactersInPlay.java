import edu.duke.FileResource;
import java.util.ArrayList;
import java.util.regex.*;

/*
* Counts Characters in Shakespeare plays and quantity of their lines.
* Characters by number of their lines can be shown.
* In given files names of characters are always in UpperCase and followed by dot.
*/

public class CharactersInPlay {
    private static ArrayList<String> names;
    private static ArrayList<Integer> counts;

    public CharactersInPlay() {
        names = new ArrayList<String>();
        counts = new ArrayList<Integer>();
    }

    public static void update(String person) {
        int index = names.indexOf(person);

        if (index == -1) {
            names.add(person);
            counts.add(1);
        } else {
            int count = counts.get(index);
            counts.set(index, count + 1);
        }
    }

    public static String findName(String s) {
        String res = "";
        s.trim();

        Pattern p = Pattern.compile("[A-Z]{4,}\\.");
        //Pattern p = Pattern.compile("[A-Z]{4,}"); << for course test dot should be included into name
        Matcher m = p.matcher(s);
        if (m.find()) {
            res = m.group();
        }
        return res;
    }

    public static void findAllCharacters() {
        FileResource text = new FileResource("src/main/java/files/errors.txt");

        for(String line: text.lines()) {
            if (line.length() != 0) {
                String res = findName(line);
                if (res != "") {
                    update(res);
                }
            }
        }
    }

    // prints characters who have lines amount between num1 and num2 (inclusive)
    public static void charactersWithNumParts(int num1, int num2) {
        for (String s: names) {
            int index = names.indexOf(s);
            int freq = counts.get(index);
            if (freq >= num1 && freq <= num2) {
                System.out.println(freq + " " + s);
            }
        }
    }

    public static void test() {
        for (String s: names) {
            int index = names.indexOf(s);
            System.out.println(counts.get(index) + " " + s);
        }
    }

    public static void main(String[] args) {
        CharactersInPlay kekes = new CharactersInPlay();
        findAllCharacters();
        test();
        System.out.println(" - - - - - ");
        charactersWithNumParts(9, 16);
    }
}
