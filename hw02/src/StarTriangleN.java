public class StarTriangleN {
    /**
     * Prints a right-aligned triangle of stars ('*') with N lines.
     * The first row contains 1 star, the second 2 stars, and so on.
     */
    public static void starTriangle(int N) {
        // TODO: Fill in this function
        for (int i = 1; i <= N; i++) {
            print_n(N - i, ' ');
            print_n(i, '*');
            IO.println("");
        }
    }

    public static void print_n(int n, char c) {
        for (int i = 0; i < n; i++) {
            IO.print(c);
        }
    }

    void main() {
        starTriangle(8);
    }
}
