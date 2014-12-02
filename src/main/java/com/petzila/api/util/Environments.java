package com.petzila.api.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rsaborio on 02/12/14.
 */
public final class Environments {
    private static Map<String, String> envs = new HashMap<>();
    static {
        envs.put("local", "http://localhost:3000");
        envs.put("dev", "https://dev.petzila.com");
        envs.put("qa", "https://qa.petzila.com");
        envs.put("qa2", "https://qa2.petzila.com");
        envs.put("qa3", "https://qa3.petzila.com");
        envs.put("qa4", "https://qa4.petzila.com");
        envs.put("pre", "https://preview.petzila.com");
        envs.put("stg", "https://stg.petzila.com");
        envs.put("pro", "https://api.petzila.com");
    }

    public static String get() {
        return envs.get(Utils.getProperty("api.environment"));
    }

    public static String get(String environment) {
        return envs.get(environment);
    }

    private Environments() {
    }
}
