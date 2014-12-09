package com.petzila.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 19/11/14.
 */
public final class PetziConnectUpdateResponse extends Response {
    public Data data;

    public static class Data {
        public String pzcIp;
        public String pzcId;
        public String pzcName;
        public String userKey;
        @JsonProperty("default")
        public boolean isDefault;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("pzcIp", pzcIp)
                    .add("pzcId", pzcId)
                    .add("pzcName", pzcName)
                    .add("userKey", userKey)
                    .add("default", isDefault)
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
