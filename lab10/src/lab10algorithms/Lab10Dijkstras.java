package lab10algorithms;

import lab10lib.ExtrinsicMinPQ;
import lab10lib.Lab10Graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/** Computes shortest paths from a single source node of a Lab10Graph using
 *  Dijkstra's algorithm, treating each edge weight as a distance.
 *
 *  All of the work happens in the constructor, which builds the shortest
 *  paths tree and records it in the instance variables below. The three
 *  query methods then just read off the answers. ExtrinsicMinPQ (provided)
 *  is the priority queue you'll want for the fringe: it supports
 *  changePriority, which Dijkstra's relies on. */
public class Lab10Dijkstras {
    private final String source;
    /** The best known distance from source to each node. Integer.MAX_VALUE
     *  means "not reached (yet)". */
    private final Map<String, Integer> distTo = new HashMap<>();
    /** The node we came from on the best known path to each node. */
    private final Map<String, String> edgeTo = new HashMap<>();

    /** Runs Dijkstra's algorithm on g starting from source.
     *  Throws IllegalArgumentException if source is not a node of g. */
    public Lab10Dijkstras(Lab10Graph g, String source) {
        if (!g.nodes().contains(source)) {
            throw new IllegalArgumentException("no such node: " + source);
        }
        this.source = source;

        /* 
           To get you started: every node begins in the fringe. The source
           has distance 0 and every other node has "infinite" distance,
           which we represent with Integer.MAX_VALUE. Dijkstra's then
           repeatedly removes the closest node and relaxes its edges.
           */
        ExtrinsicMinPQ fringe = new ExtrinsicMinPQ();
        for (String node : g.nodes()) {
            distTo.put(node, Integer.MAX_VALUE);
            fringe.add(node, Integer.MAX_VALUE);
        }
        distTo.put(source, 0);
        fringe.changePriority(source, 0);

        String cur;
        while (fringe.size() != 0) {
            cur = fringe.removeSmallest();
            List<String> neighbors = g.neighbors(cur);
            for (String neighbor : neighbors) {
                int newDist = g.edgeWeight(cur, neighbor) + distTo.get(cur);
                if (distTo.get(neighbor) > newDist) {
                    distTo.put(neighbor, newDist);
                    edgeTo.put(neighbor, cur);
                    fringe.changePriority(neighbor, newDist);
                }
            }
        }
    }

    /** Returns true if there is a path from the source to v. The source
     *  always has a path to itself. */
    public boolean hasPathTo(String v) {
        return distTo.containsKey(v) && distTo.get(v) != Integer.MAX_VALUE;
    }

    /** Returns the total weight of the shortest path from the source to v.
     *  Throws IllegalArgumentException if there is no path to v. */
    public int distTo(String v) {
        if (!hasPathTo(v)) {
            throw new IllegalArgumentException("no such path: " + v);
        }
        return distTo.get(v);
    }

    /** Returns the shortest path from the source to v as a list of nodes,
     *  starting with the source and ending with v. The path to the source
     *  itself is the one-element list [source].
     *  Throws IllegalArgumentException if there is no path to v. */
    public List<String> shortestPathTo(String v) {
        if (!hasPathTo(v)) {
            throw new IllegalArgumentException("no such path: " + v);
        }
        LinkedList<String> path = new LinkedList<>();
        path.addFirst(v);
        String current = v;
        while (!current.equals(source)) {
            current = edgeTo.get(current);
            path.addFirst(current);
        }
        return path;
    }
}
