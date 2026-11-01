package com.example.bff.lazythreads;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class LazyStress {

    static final int THREADS = 64;
    static final int ROUNDS = 200;

    // Broken: check-then-act without synchronisation
    static final class NaiveLazy<T> implements Supplier<T> {
        private final Supplier<T> init;
        private T value;

        NaiveLazy(Supplier<T> init) { this.init = init; }

        @Override public T get() {
            if (value == null) value = init.get();
            return value;
        }
    }

    // Correct: volatile flag + double-checked locking
    static final class Lazy<T> implements Supplier<T> {
        private volatile Supplier<? extends T> init;   // null once computed
        private T value;

        Lazy(Supplier<? extends T> init) { this.init = Objects.requireNonNull(init); }

        @Override public T get() {
            Supplier<? extends T> s = init;
            if (s == null) return value;
            synchronized (this) {
                s = init;
                if (s == null) return value;
                T v = s.get();
                value = v;
                init = null;
                return v;
            }
        }
    }

    /** Runs ROUNDS rounds; in each, THREADS threads call lazy.get() at the same instant. Returns the worst init count. */
    static int worstInitCount(String name, java.util.function.Function<Supplier<String>, Supplier<String>> factory)
            throws Exception {
        int worst = 0;
        for (int round = 0; round < ROUNDS; round++) {
            AtomicInteger initCount = new AtomicInteger();

            // the 1 ms initializer: slow enough to widen the race window
            Supplier<String> initializer = () -> {
                initCount.incrementAndGet();
                try { Thread.sleep(1); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                return "connection";
            };
            Supplier<String> lazy = factory.apply(initializer);

            CountDownLatch ready = new CountDownLatch(THREADS);   // every thread is parked and waiting
            CountDownLatch go = new CountDownLatch(1);            // the starting gun
            ExecutorService pool = Executors.newFixedThreadPool(THREADS);
            List<Future<String>> results = new ArrayList<>();

            for (int t = 0; t < THREADS; t++) {
                results.add(pool.submit(() -> {
                    ready.countDown();
                    go.await();                                   // all threads block here...
                    return lazy.get();
                }));
            }
            ready.await();                                        // wait until all 64 are at the gun
            go.countDown();                                       // ...and are released together

            for (Future<String> f : results) f.get();
            pool.shutdown();
            worst = Math.max(worst, initCount.get());
        }
        System.out.printf("%-10s worst case over %d rounds: initialised %d time(s)%n", name, ROUNDS, worst);
        return worst;
    }

    public static void main(String[] args) throws Exception {
        worstInitCount("NaiveLazy", NaiveLazy::new);
        worstInitCount("Lazy", Lazy::new);
    }
}
