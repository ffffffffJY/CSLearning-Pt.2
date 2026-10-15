import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BEnhancementTest {
    @Test
    public void testIterator() {
        ArrayDeque61B<Integer> ad1 = new ArrayDeque61B<>();
        for (int i = 0; i < 8; i++) {
            ad1.addFirst(i);
        }
        List<Integer> list = new ArrayList<>();
        for (Integer i : ad1) {
            list.add(i);
        }
        assertThat(list).containsExactly(7, 6, 5, 4, 3, 2, 1, 0);
    }

    @Test
    public void testEquals() {
        ArrayDeque61B<Integer> ad1 = new ArrayDeque61B<>();
        ArrayDeque61B<Integer> ad2 = new ArrayDeque61B<>();
        for (int i = 0; i < 8; i++) {
            ad1.addFirst(i);
            ad2.addFirst(i);
        }
        assertThat(ad1.equals(ad2)).isTrue();

        ad1.removeFirst();
        assertThat(ad1.equals(ad2)).isFalse();
    }

    @Test
    public void testToString() {
        ArrayDeque61B<Integer> ad1 = new ArrayDeque61B<>();
        assertThat(ad1.toString()).isEqualTo("[]");

        for (int i = 0; i < 8; i++) {
            ad1.addFirst(i);
        }
        assertThat(ad1.toString()).isEqualTo("[7, 6, 5, 4, 3, 2, 1, 0]");
    }
}
