package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int capacity = 16;
    private double loadFactor = 0.75;
    private int elementNum;

    /** Constructors */
    public MyHashMap() {
        buckets = new Collection[capacity];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
        elementNum = 0;
    }

    public MyHashMap(int initialCapacity) {
        capacity = initialCapacity;
        buckets = new Collection[capacity];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
        elementNum = 0;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        capacity = initialCapacity;
        this.loadFactor = loadFactor;
        buckets = new Collection[capacity];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
        elementNum = 0;
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    public int hash(K key, int mod) {
        return Math.floorMod(key.hashCode(), mod);
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     */
    @Override
    public void put(K key, V value) {
        Node n = new Node(key, value);
        if (!this.containsKey(key)) {
            buckets[hash(key, capacity)].add(n);
            elementNum++;
            if (elementNum > capacity * loadFactor) {
                resize();
            }
        } else {
            this.getNode(key).value = value;
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     */
    @Override
    public V get(K key) {
        Node n = this.getNode(key);
        if (n != null) {
            return n.value;
        }
        return null;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     */
    @Override
    public boolean containsKey(K key) {
        return getNode(key) != null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return elementNum;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        elementNum = 0;
        buckets = new Collection[capacity];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for this lab.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (K key : this) {
            set.add(key);
        }
        return set;
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        if (!this.containsKey(key)) {
            return null;
        }
        V val = getNode(key).value;
        buckets[hash(key, capacity)].remove(getNode(key));
        return val;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return new HashIterator();
    }

    private class HashIterator implements Iterator<K> {

        private int index;
        private Iterator<Node>[] idxInBuckets;

        private HashIterator() {
            index = 0;
            idxInBuckets = new Iterator[capacity];
            for (int i = 0; i < capacity; i++) {
                idxInBuckets[i] = buckets[i].iterator();
            }
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            if (idxInBuckets[index].hasNext()) {
                return true;
            } else {
                while (++index < capacity) {
                    if (idxInBuckets[index].hasNext()) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public K next() {
            if (hasNext()) {
                if (idxInBuckets[index].hasNext()) {
                    return idxInBuckets[index].next().key;
                } else {
                    while (index < capacity) {
                        if (idxInBuckets[index].hasNext()) {
                            return idxInBuckets[index].next().key;
                        } else {
                            index++;
                        }
                    }
                }
            }
            return null;
        }
    }

    private void resize() {
        int newCapacity = capacity * 2;
        Collection<Node>[] newBuckets = new Collection[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            newBuckets[i] = createBucket();
        }
        for (Collection<Node> bucket : buckets) {
            for (Node n : bucket) {
                K key = n.key;
                newBuckets[hash(key, newCapacity)].add(new Node(key, n.value));
            }
        }
        capacity = newCapacity;
        buckets = newBuckets;
    }

    private Node getNode(K key) {
        for (Node n : buckets[hash(key, capacity)]) {
            if (n.key.equals(key)) {
                return n;
            }
        }
        return null;
    }


}
