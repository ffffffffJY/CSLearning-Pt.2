import java.util.Comparator;
import java.util.List;

public class WordComparators {

    /** Returns a comparator that orders strings by the number of lowercase 'x' characters (ascending). */
    public static Comparator<String> getXComparator() {
        // TODO: Implement this.
        Comparator<String> com = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int x1 = 0;
                int x2 = 0;
                for (int i = 0; i < o1.length(); i++) {
                    if (o1.charAt(i) == 'x') {
                        x1++;
                    }
                }
                for (int i = 0; i < o2.length(); i++) {
                    if (o2.charAt(i) == 'x') {
                        x2++;
                    }
                }
                return x1 - x2;
            }
        };
        return com;
    }

    /** Returns a comparator that orders strings by the count of the given character (ascending). */
    public static Comparator<String> getCharComparator(char c) {
        // TODO: Implement this.
        Comparator<String> com = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int x1 = 0;
                int x2 = 0;
                for (int i = 0; i < o1.length(); i++) {
                    if (o1.charAt(i) == c) {
                        x1++;
                    }
                }
                for (int i = 0; i < o2.length(); i++) {
                    if (o2.charAt(i) == c) {
                        x2++;
                    }
                }
                return x1 - x2;
            }
        };
        return com;
    }

    /** Returns a comparator that orders strings by the total count of the given characters (ascending). */
    public static Comparator<String> getCharListComparator(List<Character> chars) {
        // TODO: Implement this.
        Comparator<String> com = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int x1 = 0;
                int x2 = 0;
                for (int i = 0; i < o1.length(); i++) {
                    if (chars.contains(o1.charAt(i))) {
                        x1++;
                    }
                }
                for (int i = 0; i < o2.length(); i++) {
                    if (chars.contains(o2.charAt(i))) {
                        x2++;
                    }
                }
                return x1 - x2;
            }
        };
        return com;
    }
}
