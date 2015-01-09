package com.petzila.api;

import com.petzila.api.flow.Flow;
import com.petzila.api.util.Environments;
import org.apache.commons.cli.*;
import org.reflections.Reflections;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by rsaborio on 19/11/14.
 */
public class Main {
    private static final int DEFAULT_THREAD_COUNT = 10;
    private static final int DEFAULT_TEST_TIME_MS = 1000 * 60; // one minute
    private static final int DEFAULT_DELAY_TIME_MS = 1000; // one second


    private static Map<String, Flow> flows = new HashMap<>();
    static {
        Reflections reflections = new Reflections(Flow.class.getPackage().getName());
        Set<Class<? extends Flow>> flowClasses = reflections.getSubTypesOf(Flow.class);
        Flow flow;
        for (Class<? extends Flow> f: flowClasses) {
            try {
                flow = f.newInstance();
                flows.put(flow.getName(), flow);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    private static Options options = new Options();
    static {
        options.addOption(new Option("h", false, "Show this help."));
        options.addOption(new Option("u", true, "Number of concurrent users. Default = 10."));
        options.addOption(new Option("t", true, "Duration of test (s, m, h) eg. 1h = one hour test. Default = 1m."));
        options.addOption(new Option("d", true, "Time delay before making a call. Random delay between 1 sec and N secs. Default = 1 sec."));
        options.addOption(new Option("e", true, "Environment (local, dev, qa, qa2, qa3, qa4, pre, stg). Default = local."));
        options.addOption(new Option("l", false, "Show all registered flows."));
        options.addOption(new Option("f", true, "Flow to run."));
    }

    // For reporting
    private static AtomicInteger errorCount = new AtomicInteger();
    private static AtomicInteger hitsCount = new AtomicInteger();
    private static AtomicLong responseTime = new AtomicLong();
    private static AtomicLong shortestCall = new AtomicLong(Long.MAX_VALUE);
    private static AtomicLong longestCall = new AtomicLong();

    private static ExecutorService executor;
    private static Flow flow;
    private static int cycles;
    private static long start;
    private static int threadCount = DEFAULT_THREAD_COUNT;
    private static int testTimeMs = DEFAULT_TEST_TIME_MS;
    private static int delayTimeMs = DEFAULT_DELAY_TIME_MS;

    public static void main(String[] args) throws Exception {
        // Parse Command-Line Interface args
        parseCLIArgs(args);

        // Crtl+C hook
        Runtime.getRuntime().addShutdownHook(new Thread("shutdown-hook") {
            @Override
            public void run() {
                printReport();
            }
        });

        // Create N concurrent threads
        System.out.println("Preparing " + threadCount + " concurrent users...");
        executor = Executors.newFixedThreadPool(threadCount);

        // Initialize Flow to run
        System.out.println("Running flow '" + flow.getName() + "'...");
        flow.init();

        // Start test
        final Random random = new Random();
        System.out.println("Starting tests...");
        boolean running = true;
        start = System.currentTimeMillis();
        while (running) {
            try {
                executor.execute(() -> {
                    try {
                        Thread.sleep(random.nextInt(delayTimeMs));

                        long duration = flow.run();
                        hitsCount.incrementAndGet();
                        responseTime.addAndGet(duration);
                        if (duration < shortestCall.get())
                            shortestCall.set(duration);
                        if (duration > longestCall.get())
                            longestCall.set(duration);
                    } catch (InterruptedException e) {
                        // do nothing
                    } catch (Exception e) {
                        errorCount.incrementAndGet();
                    }
                });
                if (System.currentTimeMillis() - start > testTimeMs) {
                    running = false;
                } else {
                    Thread.sleep(10);
                }
                cycles++;
            } catch (RejectedExecutionException ree) {
                // Do nothing
            }
        }
        System.exit(0);
    }

    private static void parseCLIArgs(String... args) throws ParseException {
        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption('h')) {
            printHelpAndExit();
        }
        if (cmd.hasOption('u')) {
            threadCount = Integer.parseInt(cmd.getOptionValue('u'));
        }
        if (cmd.hasOption('d')) {
            delayTimeMs = Integer.parseInt(cmd.getOptionValue('d')) * 1000;
            if (delayTimeMs < DEFAULT_DELAY_TIME_MS)
                delayTimeMs = DEFAULT_DELAY_TIME_MS;
        }
        if (cmd.hasOption('t')) {
            String t = cmd.getOptionValue('t').toLowerCase();
            try {
                if (t.endsWith("s")) {
                    testTimeMs = Integer.parseInt(t.substring(0, t.indexOf('s'))) * 1000;
                } else if (t.endsWith("m")) {
                    testTimeMs = Integer.parseInt(t.substring(0, t.indexOf('m'))) * 1000 * 60;
                } else if (t.endsWith("h")) {
                    testTimeMs = Integer.parseInt(t.substring(0, t.indexOf('h'))) * 1000 * 60 * 60;
                }
            } catch (Exception e) {
                System.err.println("Invalid -t option: " + t);
                printHelpAndExit();
            }
        }
        if (cmd.hasOption('e')) {
            Environments.set(cmd.getOptionValue('e'));
        }
        if (cmd.hasOption('l')) {
            if (flows.isEmpty()) {
                System.out.println("There are no registered flows.");
            } else {
                System.out.println("Registered flows:");
                for (Flow flow : flows.values()) {
                    System.out.println(MessageFormat.format("\t{0}  ->  {1}", flow.getName(), flow.getDescription()));
                }
            }
            System.exit(0);
        }
        if (cmd.hasOption('f')) {
            flow = flows.get(cmd.getOptionValue('f'));
        }

        if (flow == null) {
            System.err.println("Invalid -f option - Flow wasn't specified");
            printHelpAndExit();
        }
    }

    private static void printReport() {
        float elapsedTime = (System.currentTimeMillis() - start) / 1000f;
        executor.shutdownNow();
        try {
            executor.awaitTermination(8, TimeUnit.SECONDS);
        } catch (InterruptedException ie) {
            // Do nothing
        }

        System.out.println();
        System.out.println();
        System.out.println(MessageFormat.format("Environment: \t\t {0}", Environments.get()));
        System.out.println(MessageFormat.format("Flow: \t\t\t {0}", flow.getName()));
        System.out.println(MessageFormat.format("Hits: \t\t\t {0}", hitsCount.get()));
        System.out.println(MessageFormat.format("Hits/sec: \t\t {0}", hitsCount.get() / elapsedTime));
        System.out.println(MessageFormat.format("Threads: \t\t {0}", threadCount));
        System.out.println(MessageFormat.format("Availability: \t\t {0}%", (1f - (float) errorCount.get() / (float) cycles) * 100f));
        System.out.println(MessageFormat.format("Elapsed Time: \t\t {0} secs", elapsedTime));
        System.out.println(MessageFormat.format("Average RT: \t\t {0} secs", (responseTime.get() / cycles) / 1000f));
        System.out.println(MessageFormat.format("Shortest RT: \t\t {0} secs", shortestCall.get() / 1000f));
        System.out.println(MessageFormat.format("Longest RT: \t\t {0} secs", longestCall.get() / 1000f));
        System.out.println(MessageFormat.format("Successful Calls: \t {0}", cycles - errorCount.get()));
        System.out.println(MessageFormat.format("Failed Calls: \t\t {0}", errorCount.get()));
    }

    private static void printHelpAndExit() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("patt", options);
        System.exit(0);
    }
}
