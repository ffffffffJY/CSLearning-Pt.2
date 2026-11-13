package lab9algorithms;

import lab9lib.Lab9Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/** Breadth first search on a Lab9Graph from a single start node.
 *
 *  All of the work happens in the constructor, which runs the search and
 *  records what it finds. The query methods then just read off the answers.
 *  Unlike Lab9DFS, no instance variables are provided: decide what you need
 *  to record and add it yourself. Add the neighbors of each node to the
 *  queue in the order given by g.neighbors(). */
public class Lab9BFS {
    private final Lab9Graph g;
    private final List<String> order = new ArrayList<>();
    private final TreeMap<String, Integer> dists = new TreeMap<>();

    /** Runs breadth first search on g starting from start.
     *  Throws IllegalArgumentException if start is not a node of g. */
    public Lab9BFS(Lab9Graph g, String start) {
        if (!g.nodes().contains(start)) {
            throw new IllegalArgumentException("no such node: " + start);
        }
        this.g = g;
        List<String> queue = new ArrayList<>();
        int dist = 0;
        queue.add(start);
        dists.put(start, dist);
        while (!queue.isEmpty()) {
            String current = queue.removeFirst();
            order.add(current);
            List<String> neighbours = g.neighbors(current);
            neighbours.removeIf(order::contains);
            neighbours.removeIf(queue::contains);
            for (String neighbour : neighbours) {
                dists.put(neighbour, dists.get(current) + 1);
            }
            queue.addAll(neighbours);
        }

    }

    /** Returns true if s was reached by the search, i.e. there is a path
     *  from start to s. Throws IllegalArgumentException if s is not a node
     *  of the graph. */
    public boolean reachable(String s) {
        if (!g.nodes().contains(s)) {
            throw new IllegalArgumentException("no such node: " + s);
        }
        return order.contains(s);
    }

    /** Returns the distance from start to s, i.e. the number of edges on a
     *  shortest path from start to s, or -1 if s is not reachable from
     *  start. The distance to start itself is 0. Throws
     *  IllegalArgumentException if s is not a node of the graph. */
    public int distTo(String s) {
        if (!g.nodes().contains(s)) {
            throw new IllegalArgumentException("no such node: " + s);
        }
        if (!reachable(s)) {
            return -1;
        }
        return dists.get(s);
    }

    /** Returns the BFS order of the nodes reachable from start: the order
     *  in which nodes were removed from the queue. The first node in the
     *  list is always start. */
    public List<String> order() {
        // TODO: Replace this with your implementation.
        return this.order;
    }
}
