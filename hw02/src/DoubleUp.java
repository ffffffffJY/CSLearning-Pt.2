public class DoubleUp {
    /**
     * Returns a new string where each character of the given string is repeated twice.
     * Example: doubleUp("hello") -> "hheelllloo"
     */
    public static String doubleUp(String s) {
        // TODO: Fill in this function
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            result += s.charAt(i);
            result += s.charAt(i);
        }
        return result;
    }

    void main() {
        String s = doubleUp("hello");
        IO.println(s);

        IO.println(doubleUp("cat"));
    }
}
