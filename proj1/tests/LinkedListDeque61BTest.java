import edu.princeton.cs.algs4.In;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

    @Test
    /** In this test, we add three elements to verify that addFirst works correctly. */
    public void addFirstTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst("back"); // after this call we expect: ["back"]
        lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]

        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    public void addLastTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addLast("back");
        lld1.addLast("middle");
        lld1.addLast("front");

        assertThat(lld1.toList()).containsExactly("back", "middle", "front").inOrder();
    }

    @Test
    public void toListTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst("back");
        lld1.addFirst("middle");
        lld1.addLast("front");

        List<String> lld = lld1.toList();
        assertThat(lld).containsExactly("middle", "back", "front").inOrder();
    }

    @Test
    public void toListTestEmpty() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        List<String> lld = lld1.toList();
        assertThat(lld).isEqualTo(new ArrayList<String>());
    }

    @Test
    public void removeFirstTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst("back");
        lld1.addFirst("middle");
        lld1.addFirst("front");
        String s = lld1.removeFirst();

        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();
        assertThat(s).isEqualTo("front");

        s = lld1.removeFirst();
        s = lld1.removeFirst();
        s = lld1.removeFirst();

        assertThat(s).isEqualTo(null);

    }

    @Test
    public void isEmptyTest() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();

        lld1.addFirst("back");
        assertThat(lld1.isEmpty()).isFalse();

        lld1.removeFirst();
        assertThat(lld1.isEmpty()).isTrue();

    }

    @Test
    public void removeLastTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst("back");
        lld1.addFirst("middle");
        lld1.addFirst("front");
        String s = lld1.removeLast();

        assertThat(lld1.toList()).containsExactly("front", "middle").inOrder();
        assertThat(s).isEqualTo("back");

        s = lld1.removeLast();
        s = lld1.removeLast();
        s = lld1.removeLast();

        assertThat(s).isEqualTo(null);

    }

    @Test
    public void sizeTest() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst("back");
        lld1.addFirst("middle");
        lld1.addFirst("front");
        assertThat(lld1.size()).isEqualTo(3);

        String s = lld1.removeLast();
        assertThat(lld1.size()).isEqualTo(2);

        s = lld1.removeFirst();
        assertThat(lld1.size()).isEqualTo(1);

        s = lld1.removeLast();
        s = lld1.removeLast();
        assertThat(lld1.size()).isEqualTo(0);
    }

    @Test
    public void getTest() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst("back");
        lld1.addFirst("middle");
        lld1.addFirst("front");

        assertThat(lld1.get(1)).isEqualTo("middle");
        assertThat(lld1.get(0)).isEqualTo("front");
        assertThat(lld1.get(3)).isNull();
        assertThat(lld1.get(-10086)).isNull();

    }

    @Test
    public void getRecursiveTest() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst("back");
        lld1.addFirst("middle");
        lld1.addFirst("front");

        assertThat(lld1.getRecursive(1)).isEqualTo("middle");
        assertThat(lld1.getRecursive(0)).isEqualTo("front");
        assertThat(lld1.getRecursive(3)).isNull();
        assertThat(lld1.getRecursive(-10086)).isNull();
    }

    @Test
    public void getFirstTest() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.getFirst()).isNull();

        lld1.addFirst("back");
        lld1.addLast("middle");
        assertThat(lld1.getFirst()).isEqualTo("back");
    }

    @Test
    public void getLastTest() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.getLast()).isNull();

        lld1.addFirst("back");
        lld1.addLast("middle");
        assertThat(lld1.getLast()).isEqualTo("middle");
    }

    @Test
    public void addAfterRemoveTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst(1);
        int x = lld1.removeLast();
        lld1.addLast(4);
        assertThat(lld1.toList()).containsExactly(4).inOrder();

        x = lld1.removeFirst();
        lld1.addFirst(x);
        assertThat(lld1.toList()).containsExactly(4).inOrder();
    }
}