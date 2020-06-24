import java.util.*;

/*
* Can parse DNA string from different start points. Counts codons, shows common one.
 */

public class CodonCount {
    private static HashMap<String, Integer> codonMap;

    public CodonCount() {
        codonMap = new HashMap<String, Integer>();
    }

    public static void buildCodonMap(int start, String dna) {
        codonMap.clear();
        dna.toUpperCase();

        for(int i = start; dna.length() - i >= 3; i += 3) {
            String currentCodon = dna.substring(i, i + 3);
            if (!codonMap.containsKey(currentCodon)) {
                codonMap.put(currentCodon, 1);
            } else {
                codonMap.put(currentCodon, codonMap.get(currentCodon) + 1);
            }
        }
    }

    public static String getMostCommonCodon() {
        Map.Entry<String, Integer> maxEntry = null;

        for (Map.Entry<String, Integer> entry : codonMap.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();
    }

    public static void printCodonCounts(int start, int end) {
        for (Map.Entry<String, Integer> entry : codonMap.entrySet()) {
            int curr = entry.getValue();
            if (curr >= start && curr <= end ) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        }
    }

    public static void test() {

        String dna = "CAACCTTTAAAAGGAAGAAATCGCAGCAGCCCAGAACCAACTGCATAACATACAACCTTTAAAAGGAAGAAATCGCAGCAGCCCAGAACCAACTGCATAACATACAACCTTTAAAAGGAAGAAATCGCACCAGCCCAGAATCAACTGCATAACATACAAACTTTAAAAGGAAGAAATCTAACATACAACCTTTAAAAGGAAGAAATCGCACCAGCCCAGAATCAACTGCATAACATACAAACTTTAAAAGGAAGAAATCCAACCTTTAAAAGGAAGAAATCGCAGCAGCCCAGAACCAACTGCATAACATACAACCTTTAAAAGGAAGAAATCGCAGCAGCCCAGAACCAACTGCATAACATACAACCTTTAAAAGGAAGAAATCGCACCAGCCCAGAATCAACTGCATAACATACAAACTTTAAAAGGAAGAAATC";
        CodonCount cc = new CodonCount();
        cc.buildCodonMap(2, dna);
        printCodonCounts(1, 100);
        System.out.println("The most common is " + getMostCommonCodon());
        System.out.println(codonMap.size());
    }

    public static void main(String[] args) {
        test();
    }
}
