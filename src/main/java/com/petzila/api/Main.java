package com.petzila.api;

import com.petzila.api.model.response.PostGetResponse;
import com.petzila.api.util.Environments;
import org.apache.commons.cli.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by rsaborio on 19/11/14.
 */
public class Main {
    private static final int DEFAULT_THREAD_COUNT = 10;
    private static final int DEFAULT_TEST_TIME_MS = 1000 * 60; // one minute
    private static final int DEFAULT_DELAY_TIME_MS = 1000; // one second

    private static Options options = new Options();
    static {
        options.addOption(new Option("h", false, "Show this help"));
        options.addOption(new Option("u", true, "Number of concurrent users"));
        options.addOption(new Option("t", true, "Duration of test (S, M, H) eg. 1H= one hour test"));
        options.addOption(new Option("d", true, "Time delay, random delay between 1 and N"));
        options.addOption(new Option("e", true, "Environment (local, dev, qa, qa2, qa3, qa4, pre, stg)"));
    }

    public static void main(String[] args) throws Exception {
        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(options, args);

        int threadCount = DEFAULT_THREAD_COUNT;
        int testTimeMs = DEFAULT_TEST_TIME_MS;
        int delayTimeMs = DEFAULT_DELAY_TIME_MS;
        if (cmd.hasOption('h')) {
            printHelp();
        }
        if (cmd.hasOption('u')) {
            threadCount = Integer.parseInt(cmd.getOptionValue('u'));
        }
        if (cmd.hasOption('d')) {
            delayTimeMs = Integer.parseInt(cmd.getOptionValue('d'));
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

        final AtomicInteger errorCount = new AtomicInteger();
        final AtomicInteger hitsCount = new AtomicInteger();
        final AtomicLong responseTime = new AtomicLong();
        final AtomicLong shortestCall = new AtomicLong(Long.MAX_VALUE);
        final AtomicLong longestCall = new AtomicLong();
        List<Runnable> runnables = new ArrayList<>();
        System.out.println("Preparing " + threadCount + " concurrent users...");
        for (int i = 0; i < threadCount; i++) {
            runnables.add(new Runnable() {
                @Override
                public void run() {
                    try {
                        PostGetResponse response = Petzila.PostAPI.get();
                        hitsCount.incrementAndGet();
                        responseTime.addAndGet(response.report.duration);
                        if (response.report.duration < shortestCall.get())
                            shortestCall.set(response.report.duration);
                        if (response.report.duration > longestCall.get())
                            longestCall.set(response.report.duration);
                    } catch (Exception e) {
                        errorCount.incrementAndGet();
                    }
                }
            });
        }

        ExecutorService executor = Executors.newCachedThreadPool();
        Random random = new Random();
        boolean running = true;
        System.out.println("Starting tests...");
        long start = System.currentTimeMillis();
        while (running) {
            executor.execute(runnables.get(random.nextInt(threadCount)));
            if (System.currentTimeMillis() - start > testTimeMs)
                running = false;
            Thread.sleep(random.nextInt(delayTimeMs));
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        long end = System.currentTimeMillis();

        Report report = new Report();
        report.environment = Environments.get();
        report.hits = hitsCount.get();
        report.availability = (1f - errorCount.get() / threadCount) * 100;
        report.elapsedTime = (end - start) / 1000f;
        report.averageRT = (responseTime.get() / threadCount) / 1000f;
        report.shortestRT = shortestCall.get() / 1000f;
        report.longestRT = longestCall.get() / 1000f;
        report.successfulCalls = threadCount - errorCount.get();
        report.failedCalls = errorCount.get();
        printReport(report);
    }

    private static void printReport(Report report) {
        System.out.println();
        System.out.println();
        System.out.println(MessageFormat.format("Environment: \t\t {0}", report.environment));
        System.out.println(MessageFormat.format("Hits: \t\t\t {0}", report.hits));
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
        int hits;
        float availability;
        float elapsedTime;
        float averageRT;
        float longestRT;
        float shortestRT;
        int successfulCalls;
        int failedCalls;
    }
}
