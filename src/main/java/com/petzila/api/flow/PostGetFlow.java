package com.petzila.api.flow;

import com.petzila.api.Petzila;

/**
 * Created by rsaborio on 03/12/14.
 */
public final class PostGetFlow implements Flow {
    @Override
    public String getName() {
        return "post-get-flow";
    }

    @Override
    public String getDescription() {
        return "Calls the /post/get endpoint";
    }

    @Override
    public long run() {
        return Petzila.PostAPI.get().report.duration;
    }
}
