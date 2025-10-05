package metrics;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Tracks basic operation counts for empirical analysis:
 * - comparisons
 * - swaps
 * - array accesses
 * - allocations (approx, increment manually if needed)
 *
 * Designed to be lightweight and thread-unsafe (single-threaded experiments).
 */
public class PerformanceTracker {
    private final AtomicLong comparisons = new AtomicLong(0);
    private final AtomicLong swaps = new AtomicLong(0);
    private final AtomicLong accesses = new AtomicLong(0);
    private final AtomicLong allocations = new AtomicLong(0);

    public void incComparisons(long n) { comparisons.addAndGet(n); }
    public void incSwaps(long n) { swaps.addAndGet(n); }
    public void incAccesses(long n) { accesses.addAndGet(n); }
    public void incAllocations(long n) { allocations.addAndGet(n); }

    public long getComparisons() { return comparisons.get(); }
    public long getSwaps() { return swaps.get(); }
    public long getAccesses() { return accesses.get(); }
    public long getAllocations() { return allocations.get(); }

    public void reset() {
        comparisons.set(0);
        swaps.set(0);
        accesses.set(0);
        allocations.set(0);
    }

    public String toCsvHeader() {
        return "comparisons,swaps,accesses,allocations";
    }

    public String toCsvLine() {
        return String.format("%d,%d,%d,%d",
                getComparisons(), getSwaps(), getAccesses(), getAllocations());
    }

    @Override
    public String toString() {
        return String.format("comparisons=%d, swaps=%d, accesses=%d, allocations=%d",
                getComparisons(), getSwaps(), getAccesses(), getAllocations());
    }

    public String toPrettyString() {
        return toString();
    }

    // Helper for debugging: stack trace
    public static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
