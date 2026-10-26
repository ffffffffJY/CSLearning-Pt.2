import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    int N;
    int[] grid;
    WeightedQuickUnionUF uf;
    int opened;
    boolean isPercolated;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        grid = new int[N * N];
        uf = new WeightedQuickUnionUF(N * N);
        opened = 0;
        for (int i = 1; i < N; i++) {
            uf.union(0, i);
        }
    }

    public void open(int row, int col) {
        if (stateOf(row, col) == 0) {
            grid[row * N + col] = 1;
            opened++;
            if (row > 0 && stateOf(row - 1, col) == 1) {
                uf.union(idx(row, col), idx(row - 1, col));
            }
            if (row < N - 1 && stateOf(row + 1, col) == 1) {
                uf.union(idx(row, col), idx(row + 1, col));
            }
            if (col > 0 && stateOf(row, col - 1) == 1) {
                uf.union(idx(row, col), idx(row, col - 1));
            }
            if (col < N - 1 && stateOf(row, col + 1) == 1) {
                uf.union(idx(row, col), idx(row, col + 1));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        return stateOf(row, col) == 1;
    }

    public boolean isFull(int row, int col) {
        if (stateOf(row, col) == 0) {
            return false;
        }
        if (row == N - 1 && uf.connected(0, idx(row, col))) {
            isPercolated = true;
        }
        return uf.connected(0, idx(row, col));
    }

    public int numberOfOpenSites() {
        return opened;
    }

    public boolean percolates() {
        return isPercolated;
    }


    public int stateOf(int row, int col) {
        return grid[idx(row, col)];
    }

    public int idx(int row, int col) {
        if (row < N && row >= 0 && col < N && col >= 0) {
            return row * N + col;
        }
        throw new java.lang.IndexOutOfBoundsException("Index: " + row + ", " + col);
    }

    public static void main(String[] args) {

    }

}
