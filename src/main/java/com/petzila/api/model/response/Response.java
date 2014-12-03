package com.petzila.api.model.response;

/**
 * Created by rsaborio on 02/12/14.
 */
public abstract class Response {
    public Report report = new Report();
    public String status;

    public static class Report {
        public long duration;
    }

    @Override
    public abstract String toString();
}
