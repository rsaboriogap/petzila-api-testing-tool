package com.petzila.api;

import com.petzila.api.model.response.PostGetResponse;
import com.petzila.api.util.Environments;
import org.apache.commons.cli.*;

import java.text.MessageFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by rsaborio on 19/11/14.
 */
public class Main {
    private static final int DEFAULT_THREAD_COUNT = 10;
    private static Options options = new Options();
    static {
        options.addOption(new Option("h", false, "Show this help"));
        options.addOption(new Option("t", true, "Number of concurrent Threads"));
        options.addOption(new Option("e", true, "Environment (local, dev, qa, qa2, ..., qa4, pre, stg)"));
    }

    public static void main(String[] args) throws Exception {
        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(options, args);

        int threadCount = DEFAULT_THREAD_COUNT;
        if (cmd.hasOption('h')) {
            printHelp();
        }
        if (cmd.hasOption('t')) {
            threadCount = Integer.parseInt(cmd.getOptionValue('t'));
        }
        if (cmd.hasOption('e')) {
            Environments.set(cmd.getOptionValue('e'));
        }

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        final AtomicInteger errorCount = new AtomicInteger();
        final AtomicLong responseTime = new AtomicLong();
        final AtomicLong shortestCall = new AtomicLong(Long.MAX_VALUE);
        final AtomicLong longestCall = new AtomicLong();
        long start = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        PostGetResponse response = Petzila.PostAPI.get();
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
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        long end = System.currentTimeMillis();

        Report report = new Report();
        report.environment = Environments.get();
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
        float availability;
        float elapsedTime;
        float averageRT;
        float longestRT;
        float shortestRT;
        int successfulCalls;
        int failedCalls;
    }
}
