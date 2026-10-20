import java.util.Arrays;

public class UnionFind {
    // TODO: Instance variables
    private int[] sets;
    private int size;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
        size = N;
        sets = new int[N];
        Arrays.fill(sets, -1);
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE
        return -parent(find(v));
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // TODO: YOUR CODE HERE
        if (v > size) {
            throw new IllegalArgumentException("Input out of bound.");
        }
        return sets[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        // TODO: YOUR CODE HERE
        if (parent(v) < 0) {
            return v;
        } else {
            int temp = v;
            while (parent(temp) >= 0) {
                temp = parent(temp);
            }
            while (parent(v) >= 0) {
                int t = sets[v];
                sets[v] = temp;
                v = t;
            }
            return temp;
        }
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        int p1 = find(v1);
        int p2 = find(v2);
        if (parent(p1) > parent(p2)) {
            sets[p2] += sets[p1];
            sets[p1] = p2;
        } else if (parent(p2) > parent(p1)) {
            sets[p1] += sets[p2];
            sets[p2] = p1;
        } else {
            if (p1 == p2) {
                return;
            } else {
                sets[p2] += sets[p1];
                sets[p1] = p2;
            }
        }
    }

}
