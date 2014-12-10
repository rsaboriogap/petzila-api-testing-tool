package com.petzila.api.model.response;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 19/11/14.
 */
public final class PetziConnectGetResponse extends Response {
    public Data data;

    public static class Data {
        public boolean needProcess;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("needProcess", needProcess)
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
