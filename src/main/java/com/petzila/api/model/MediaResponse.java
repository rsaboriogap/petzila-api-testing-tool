package com.petzila.api.model;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 25/11/14.
 */
public class MediaResponse {
    public String status;
    public Data data;

    public static class Data {
        public String mediaId;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("mediaId", mediaId)
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
