package cli;

import algorithms.MaxHeap;
import metrics.PerformanceTracker;
import org.apache.commons.cli.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * CLI runner to benchmark MaxHeap on different input sizes and distributions.
 *
 * Usage examples:
 *  mvn -q exec:java -Dexec.mainClass="cli.BenchmarkRunner" -Dexec.args="--sizes 100,1000,10000 --reps 5 --out results.csv"
 */
public class    BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        Options options = new Options();
        options.addOption("s", "sizes", true, "Comma-separated sizes (e.g. 100,1000,10000)");
        options.addOption("r", "reps", true, "Repetitions per size (default 5)");
        options.addOption("o", "out", true, "Output CSV file (default results.csv)");
        options.addOption("d", "dist", true, "Distribution: random, sorted, reverse, nearly (default random)");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        String sizesArg = cmd.getOptionValue("sizes", "100,1000,10000");
        int reps = Integer.parseInt(cmd.getOptionValue("reps", "5"));
        String out = cmd.getOptionValue("out", "results.csv");
        String dist = cmd.getOptionValue("dist", "random");

        List<Integer> sizes = new ArrayList<>();
        for (String s : sizesArg.split(",")) sizes.add(Integer.parseInt(s.trim()));

        try (FileWriter writer = new FileWriter(out)) {
            writer.write("n,distribution,rep,timeMs,");
            writer.write(new PerformanceTracker().toCsvHeader() + "\n");

            Random rnd = new Random(42);

            for (int n : sizes) {
                for (int rep = 1; rep <= reps; rep++) {
                    int[] arr = generateArray(n, dist, rnd);
                    PerformanceTracker tracker = new PerformanceTracker();
                    MaxHeap heap = new MaxHeap(tracker);

                    long t0 = System.nanoTime();

                    for (int v : arr) heap.insert(v);
                    int extractCount = Math.max(1, n / 10);
                    for (int i = 0; i < extractCount; i++) heap.extractMax();


                    if (!heap.isEmpty()) {
                        int tries = Math.min(5, heap.size());
                        for (int k = 0; k < tries; k++) {
                            int idx = rnd.nextInt(heap.size());
                            int newVal = Integer.MAX_VALUE - rnd.nextInt(1000);
                            try { heap.increaseKey(idx, newVal); } catch (IllegalArgumentException ignore) {}
                        }
                    }
                    long t1 = System.nanoTime();
                    long timeMs = (t1 - t0) / 1_000_000;

                    writer.write(String.format("%d,%s,%d,%d,%s\n",
                            n, dist, rep, timeMs, tracker.toCsvLine()));
                    writer.flush();
                    System.out.printf("n=%d dist=%s rep=%d time=%dms %s%n", n, dist, rep, timeMs, tracker.toPrettyString());
                }
            }
        } catch (IOException e) {
            System.err.println("File write error: " + e.getMessage());
        }
    }

    private static int[] generateArray(int n, String dist, Random rnd) {
        int[] a = new int[n];
        switch (dist) {
            case "sorted":
                for (int i = 0; i < n; i++) a[i] = i;
                break;
            case "reverse":
                for (int i = 0; i < n; i++) a[i] = n - i;
                break;
            case "nearly":
                for (int i = 0; i < n; i++) a[i] = i;

                for (int k = 0; k < Math.max(1, n / 100); k++) {
                    int i = rnd.nextInt(n), j = rnd.nextInt(n);
                    int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
                }
                break;
            default:
                for (int i = 0; i < n; i++) a[i] = rnd.nextInt();
        }
        return a;
    }
}
