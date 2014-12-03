package com.petzila.api;

import org.apache.commons.cli.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by rsaborio on 19/11/14.
 */
public class Main {
    private static final int DEFAULT_THREAD_COUNT = 50;

    public static void main(String[] args) throws Exception {
        Options options = new Options();
        options.addOption(new Option("t", true, "Number of concurrent Threads"));

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse( options, args);

        int threadCount = DEFAULT_THREAD_COUNT;
        if (cmd.hasOption('t')) {
            threadCount = Integer.parseInt(cmd.getOptionValue('t'));
        }

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    Petzila.PostAPI.get();
                }
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
    }}
