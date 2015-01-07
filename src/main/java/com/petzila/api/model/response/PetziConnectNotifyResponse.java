package com.petzila.api.model.response;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 19/11/14.
 */
public final class PetziConnectNotifyResponse extends Response {
    public String hbsUrl;
    public Data data;
    public boolean needProcess;

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
                .add("data", data)
                .add("hbsUrl", hbsUrl)
                .add("needProcess", needProcess)
                .toString();
    }
}
