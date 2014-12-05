package com.petzila.api.flow;

/**
 * Created by rsaborio on 03/12/14.
 */
public interface Flow {
    String getName();

    String getDescription();

    long run();
}
