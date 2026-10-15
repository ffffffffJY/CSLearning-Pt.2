import org.apache.commons.collections.iterators.ArrayListIterator;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T>{

    int size;
    int start;
    int end;
    T[] array;

    public ArrayDeque61B() {
        this.size = 0;
        this.start = 0;
        this.end = 0;
        this.array = (T[]) new Object[8];
    }

    private int circ(int x) {
        if (x < 0) {
            return x + array.length;
        } else {
            return x % array.length;
        }
    }

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        size++;
        if (size >= array.length) {
            this.resizeUp();
        }
        start = circ(start - 1);
        array[start] = x;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        size++;
        if (size >= array.length) {
            this.resizeUp();
        }
        array[end] = x;
        end = circ(end + 1);
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            returnList.add(array[circ(i + start)]);
        }
        return returnList;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Return the element at the front of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getFirst() {
        if (size == 0) {
            return null;
        }
        return array[start];
    }

    /**
     * Return the element at the back of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getLast() {
        if (size == 0) {
            return null;
        }
        return array[circ(end - 1)];
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size--;
        if (size < array.length / 2 && array.length >= 16) {
            this.resizeDown();
        }
        T ret = array[start];
        start = circ(start + 1);
        return ret;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size--;
        if (size < array.length / 2 && array.length >= 16) {
            this.resizeDown();
        }
        end = circ(end - 1);
        return array[end];
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return array[circ(index + start)];
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for ArrayDeque61B.");
    }

    public void resizeUp() {
        T[] copy = (T[]) new Object[2 * array.length];
        for (int i = 0; i < size; i++) {
            copy[i] = array[circ(i + start)];
        }
        start = 0;
        end = size;
        array = copy;
    }

    public void resizeDown() {
        T[] copy = (T[]) new Object[array.length / 2];
        for (int i = 0; i < size; i++) {
            copy[i] = array[circ(i + start)];
        }
        start = 0;
        end = size;
        array = copy;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public @NonNull Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int pos;

        public ArrayDequeIterator() {
            pos = 0;
        }

        @Override
        public boolean hasNext() {
            return pos < size;
        }

        @Override
        public T next() {
            T ret = array[circ(pos + start)];
            pos = pos + 1;
            return ret;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ArrayDeque61B) {
            if (size != ((ArrayDeque61B) obj).size()) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                int ss = ((ArrayDeque61B<?>) obj).start;
                if (((ArrayDeque61B<?>) obj).array[circ(ss + i)] != array[circ(i + start)]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (T s : this) {
            sb.append(s.toString());
            sb.append(", ");
        }
        if (sb.length() > 1) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("]");
        return sb.toString();
    }


}
