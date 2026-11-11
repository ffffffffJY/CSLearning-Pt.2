import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class BSTNode {
        K key;
        V value;
        BSTNode left, right;
        public BSTNode(K k, V val) {
            key = k;
            value = val;
            left = null;
            right = null;
        }
    }

    private BSTNode root;
    private int size;

    public BSTMap() {
        root = null;
        size = 0;
    }
    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        BSTNode newNode = new BSTNode(key, value);
        BSTNode current = root;
        if (root == null) {
            root = newNode;
            size++;
            return;
        }
        while (current != null) {
            if (current.key.compareTo(key) < 0) {
                if (current.right == null) {
                    current.right = newNode;
                    size++;
                    break;
                }
                current = current.right;
            }
            if (current.key.compareTo(key) > 0) {
                if (current.left == null) {
                    current.left = newNode;
                    size++;
                    break;
                }
                current = current.left;
            }
            if (current.key.compareTo(key) == 0) {
                current.value = value;
                break;
            }
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        BSTNode node = getNode(key);
        if (!containsKey(key)) {
            return null;
        }
        return node.value;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        if (getNode(key) == null) {
            return false;
        }
        return true;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        return Set.of();
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        BSTNode node = getNode(key);
        if (node == null) {
            return null;
        }
        V value = node.value;
        BSTNode replacement = node;
        while (replacement != null) {
            if (replacement.left == null) {
                if (replacement.right == null) {
                    replacement = null;
                } else {
                    replacement = replacement.right;
                    while (replacement.left != null) {
                        replacement = replacement.left;
                    }
                    node.key = replacement.key;
                    node.value = replacement.value;
                    replacement = null;
                }
            } else {
                replacement = replacement.left;
                while (replacement.right != null) {
                    replacement = replacement.right;
                }
                node.key = replacement.key;
                node.value = replacement.value;
                replacement = null;
            }
        }
        size --;
        return value;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    private BSTNode getNode(K key) {
        BSTNode node = root;
        while (node != null) {
            if (node.key.equals(key)) {
                return node;
            } else if (node.key.compareTo(key) < 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    private class BSTMapIterator implements Iterator<K> {
        BSTNode current;
        int size;

        public BSTMapIterator() {
            current = root;
            while (current.left != null) {
                current = current.left;
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
            if (current.right == null) {
                return false;
            }
            return true;
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

            }
            throw new NoSuchElementException();
        }
    }
}
