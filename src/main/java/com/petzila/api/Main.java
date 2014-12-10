package com.petzila.api;

import com.petzila.api.flow.Flow;
import com.petzila.api.util.Environments;
import org.apache.commons.cli.*;
import org.reflections.Reflections;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    public static void main(String[] args) throws Exception {
        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(options, args);

        int threadCount = DEFAULT_THREAD_COUNT;
        int testTimeMs = DEFAULT_TEST_TIME_MS;
        int delayTimeMs = DEFAULT_DELAY_TIME_MS;
        Flow flowToRun = null;
        if (cmd.hasOption('h')) {
            printHelp();
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
                printHelp();
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
            flowToRun = flows.get(cmd.getOptionValue('f'));
        }

        if (flowToRun == null) {
            System.err.println("Invalid -f option - Flow wasn't specified");
            printHelp();
        }

        final AtomicInteger errorCount = new AtomicInteger();
        final AtomicInteger hitsCount = new AtomicInteger();
        final AtomicLong responseTime = new AtomicLong();
        final AtomicLong shortestCall = new AtomicLong(Long.MAX_VALUE);
        final AtomicLong longestCall = new AtomicLong();

        System.out.println("Preparing " + threadCount + " concurrent users...");
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        System.out.println("Running flow '" + flowToRun.getName() + "'...");
        flowToRun.init();

        final Random random = new Random();
        final Flow flow = flowToRun;
        System.out.println("Starting tests...");
        int cycles = 0;
        boolean running = true;
        long start = System.currentTimeMillis();
        while (running) {
            final int delay = delayTimeMs;
            executor.execute(() -> {
                try {
                    Thread.sleep(random.nextInt(delay));

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
            }
            Thread.sleep(10);
            cycles++;
        }
        long end = System.currentTimeMillis();
        executor.shutdownNow();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        Report report = new Report();
        report.environment = Environments.get();
        report.flow = flowToRun;
        report.hits = hitsCount.get();
        report.threads = threadCount;
        report.availability = (1f - (float) errorCount.get() / (float) cycles) * 100f;
        report.elapsedTime = (end - start) / 1000f;
        report.averageRT = (responseTime.get() / cycles) / 1000f;
        report.shortestRT = shortestCall.get() / 1000f;
        report.longestRT = longestCall.get() / 1000f;
        report.successfulCalls = cycles - errorCount.get();
        report.failedCalls = errorCount.get();
        report.hitsPerSeconds = report.hits /report.elapsedTime;
        printReport(report);

        System.exit(0);
    }

    private static void printReport(Report report) {
        System.out.println();
        System.out.println();
        System.out.println(MessageFormat.format("Environment: \t\t {0}", report.environment));
        System.out.println(MessageFormat.format("Flow: \t\t\t {0}", report.flow.getName()));
        System.out.println(MessageFormat.format("Hits: \t\t\t {0}", report.hits));
        System.out.println(MessageFormat.format("Hits/sec: \t\t {0}", report.hitsPerSeconds));
        System.out.println(MessageFormat.format("Threads: \t\t {0}", report.threads));
        System.out.println(MessageFormat.format("Availability: \t\t {0}%", report.availability));
        System.out.println(MessageFormat.format("Elapsed Time: \t\t {0} secs", report.elapsedTime));
        System.out.println(MessageFormat.format("Average RT: \t\t {0} secs", report.averageRT));
        System.out.println(MessageFormat.format("Shortest RT: \t\t {0} secs", report.shortestRT));
        System.out.println(MessageFormat.format("Longest RT: \t\t {0} secs", report.longestRT));
        System.out.println(MessageFormat.format("Successful Calls: \t {0}", report.successfulCalls));
        System.out.println(MessageFormat.format("Failed Calls: \t\t {0}", report.failedCalls));
    }

    private static void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("patt", options);
        System.exit(0);
    }

    static class Report {
        String environment;
        Flow flow;
        int hits;
        float hitsPerSeconds;
        int threads;
        float availability;
        float elapsedTime;
        float averageRT;
        float longestRT;
        float shortestRT;
        int successfulCalls;
        int failedCalls;
    }
}
