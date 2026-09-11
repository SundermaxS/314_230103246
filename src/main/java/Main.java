//import java.util.concurrent.*;
//
//public class Main {
//
//    static final int WORK_SIZE = 100_000_000;
//
//    public static void main(String[] args) throws Exception {
//
//        int[] threadCounts = {1, 2, 4, 8, 16, 32};
//
//        for (int threads : threadCounts) {
//
//            System.out.println("Threads: " + threads);
//
//            for (int run = 1; run <= 3; run++) {
//
//                long start = System.nanoTime();
//
//                runBenchmark(threads);
//
//                long end = System.nanoTime();
//
//                double seconds = (end - start) / 1_000_000_000.0;
//
//                System.out.printf(
//                        "Run %d: %.4f seconds%n",
//                        run,
//                        seconds
//                );
//            }
//
//            System.out.println();
//        }
//    }
//
//    static void runBenchmark(int threadCount) throws Exception {
//
//        ExecutorService executor =
//                Executors.newFixedThreadPool(threadCount);
//
//        int chunkSize = WORK_SIZE / threadCount;
//
//        Future<Long>[] futures = new Future[threadCount];
//
//        for (int i = 0; i < threadCount; i++) {
//
//            int start = i * chunkSize;
//            int end = (i == threadCount - 1)
//                    ? WORK_SIZE
//                    : start + chunkSize;
//
//            futures[i] = executor.submit(() -> {
//
//                long result = 0;
//
//                for (int j = start; j < end; j++) {
//                    result += (long) j * j;
//                }
//
//                return result;
//            });
//        }
//
//        long total = 0;
//
//        for (Future<Long> future : futures) {
//            total += future.get();
//        }
//
//        executor.shutdown();
//
//        // Prevent compiler from eliminating the calculation
//        if (total == 0) {
//            System.out.println("Result: " + total);
//        }
//    }
//}
public class Main {

    static int counter = 0;

    static final int THREADS = 10;
    static final int INCREMENTS = 1_000_000;

    public static void main(String[] args) throws Exception {

        for (int run = 1; run <= 10; run++) {

            counter = 0;

            Thread[] threads = new Thread[THREADS];

            long start = System.nanoTime();

            for (int i = 0; i < THREADS; i++) {

                threads[i] = new Thread(() -> {

                    for (int j = 0; j < INCREMENTS; j++) {
                        counter++;
                    }

                });

                threads[i].start();
            }

            for (Thread thread : threads) {
                thread.join();
            }

            long end = System.nanoTime();

            long error = 10_000_000L - counter;

            System.out.printf(
                    "Run #%d: Actual = %,d | Error = %,d | Time = %.3f ms%n",
                    run,
                    counter,
                    error,
                    (end - start) / 1_000_000.0
            );
        }
    }
}