package com.petzila.api.model.response;

import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * Created by rsaborio on 20/11/14.
 */
public final class VersionGetResponse extends Response {
    public Data data;

    public static class Data {
        public List<String> ios;
        public List<String> android;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("ios", ios)
                    .add("android", android)
                    .toString();
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("status", status)
                .add("data", data)
                .toString();
    }
}
