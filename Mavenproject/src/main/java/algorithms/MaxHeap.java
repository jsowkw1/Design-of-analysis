package algorithms;

import metrics.PerformanceTracker;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * MaxHeap implementation using ArrayList<Integer>.
 * Tracks performance via an optional PerformanceTracker (can be null).
 *
 * Provided operations:
 * - insert(val)
 * - extractMax()
 * - increaseKey(index, newValue)
 * - peek()
 * - size(), isEmpty()
 *
 * Indexing: heap uses 0-based indexing internally.
 */
public class MaxHeap {
    private final ArrayList<Integer> heap;
    private final PerformanceTracker tracker;

    public MaxHeap() {
        this(null);
    }

    public MaxHeap(PerformanceTracker tracker) {
        this.heap = new ArrayList<>();
        this.tracker = tracker;
        if (tracker != null) tracker.incAllocations(1); // one list allocation approximated
    }

    // Insert value and sift up
    public void insert(int val) {
        heap.add(val);
        if (tracker != null) {
            tracker.incAllocations(1); // approximate per element allocation
            tracker.incAccesses(1);
        }
        siftUp(heap.size() - 1);
    }

    // Extract max (root) and restore heap property
    public int extractMax() {
        if (heap.isEmpty()) throw new NoSuchElementException("Heap is empty");
        int max = heap.get(0);
        int last = heap.remove(heap.size() - 1);
        if (tracker != null) {
            tracker.incAccesses(2); // read root and last
        }
        if (!heap.isEmpty()) {
            heap.set(0, last);
            if (tracker != null) tracker.incAccesses(1);
            heapifyDown(0);
        }
        return max;
    }

    // Increase key at index to newValue (newValue must be >= current)
    public void increaseKey(int index, int newValue) {
        Objects.checkIndex(index, heap.size());
        if (newValue < heap.get(index)) {
            throw new IllegalArgumentException("newValue must be >= current value");
        }
        if (tracker != null) {
            tracker.incComparisons(1);
            tracker.incAccesses(1);
        }
        heap.set(index, newValue);
        if (tracker != null) tracker.incAccesses(1);
        siftUp(index);
    }

    public int peek() {
        if (heap.isEmpty()) throw new NoSuchElementException("Heap is empty");
        if (tracker != null) tracker.incAccesses(1);
        return heap.get(0);
    }

    public int size() { return heap.size(); }
    public boolean isEmpty() { return heap.isEmpty(); }

    // INTERNALS

    private void siftUp(int i) {
        while (i > 0) {
            int parent = (i - 1) >>> 1;
            if (tracker != null) {
                tracker.incComparisons(1);
                tracker.incAccesses(2);
            }
            if (heap.get(parent) < heap.get(i)) {
                swap(parent, i);
                i = parent;
            } else {
                break;
            }
        }
    }

    private void heapifyDown(int i) {
        final int n = heap.size();
        while (true) {
            int left = (i << 1) + 1;
            int right = left + 1;
            int largest = i;

            if (left < n) {
                if (tracker != null) { tracker.incComparisons(1); tracker.incAccesses(1); }
                if (heap.get(left) > heap.get(largest)) largest = left;
            }
            if (right < n) {
                if (tracker != null) { tracker.incComparisons(1); tracker.incAccesses(1); }
                if (heap.get(right) > heap.get(largest)) largest = right;
            }

            if (largest != i) {
                swap(i, largest);
                i = largest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        if (tracker != null) tracker.incSwaps(1);
        int tmp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tmp);
        if (tracker != null) tracker.incAccesses(4); // get + set + set
    }

    // For tests / debug
    public List<Integer> toList() {
        return new ArrayList<>(heap);
    }
}
