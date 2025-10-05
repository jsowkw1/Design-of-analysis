package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class MaxHeapTest {

    @Test
    public void testInsertAndExtract() {
        MaxHeap h = new MaxHeap(new PerformanceTracker());
        h.insert(5);
        h.insert(10);
        h.insert(3);
        assertEquals(10, h.peek());
        assertEquals(10, h.extractMax());
        assertEquals(5, h.extractMax());
        assertEquals(3, h.extractMax());
        assertTrue(h.isEmpty());
    }

    @Test
    public void testIncreaseKey() {
        MaxHeap h = new MaxHeap();
        h.insert(1);
        h.insert(2);
        h.insert(3);
        // underlying order may vary; find index of 1 and increase it
        int idx = h.toList().indexOf(1);
        assertTrue(idx >= 0);
        h.increaseKey(idx, 100);
        assertEquals(100, h.peek());

    }

    @Test
    public void testEdgeCasesEmpty() {
        MaxHeap h = new MaxHeap();
        assertThrows(NoSuchElementException.class, h::peek);
        assertThrows(NoSuchElementException.class, h::extractMax);
    }

    @Test
    public void testInvalidIncrease() {
        MaxHeap h = new MaxHeap();
        h.insert(10);
        assertThrows(IndexOutOfBoundsException.class, () -> h.increaseKey(5, 20));
        assertThrows(IllegalArgumentException.class, () -> h.increaseKey(0, 5)); // smaller value
    }
}
