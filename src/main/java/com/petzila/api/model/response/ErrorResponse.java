package com.petzila.api.model.response;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 02/12/14.
 */
public final class ErrorResponse extends Response {
    public int code;
    public Data data;

    public static class Data {
        public String message;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("message", message)
                    .toString();
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("status", status)
                .add("code", code)
                .add("data", data)
                .toString();
    }
}
