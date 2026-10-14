import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class WordFinder {
    /**
     *  Returns the maximum string according to the provider comparator.
     *  If multiple strings are considered equal by c, return the first in
     *  the array.
     *  Use loops. Don't use Collections.max or similar.
     */
    public static String findMax(String[] strings, Comparator<String> c) {
        // TODO: Implement this.
        String ret = strings[0];
        for (String s  : strings) {
            if (c.compare(s, ret) > 0) {
                ret = s;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        In in = new In("data/mobydick.txt");
        String[] words = in.readAllStrings();

        // TODO: Print only the word with the most lower case vowels.
        //       Use your findMax method!
        //
        //       Start by creating a Comparator that compares based on lower case vowels.
        Comparator<String> vowelComparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int x1 = 0;
                int x2 = 0;
                for (int i = 0; i < o1.length(); i++) {
                    if (List.of('a', 'e', 'i', 'o', 'u').contains(o1.charAt(i))) {
                        x1++;
                    }
                }
                for (int i = 0; i < o2.length(); i++) {
                    if (List.of('a', 'e', 'i', 'o', 'u').contains(o2.charAt(i))) {
                        x2++;
                    }
                }
                return x1 - x2;
            }
        };

        IO.println(findMax(words, vowelComparator));

        // Optional task: Play around with lists of words from Wikipedia articles.
        // String[] zebraWords = ParseUtils.fetchWords("https://en.wikipedia.org/wiki/zebra");
        // System.out.println(findMax(zebraWords, vowelComparator));
    }
}
