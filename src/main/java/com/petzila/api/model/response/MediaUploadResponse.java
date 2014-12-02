package com.petzila.api.model.response;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 25/11/14.
 */
public class MediaUploadResponse extends Response {
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
