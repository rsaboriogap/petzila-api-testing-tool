package com.petzila.api.model.response;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 19/11/14.
 */
public final class PetziConnectSessionStatusGetResponse extends Response {
    public Data data;

    public static class Data {
        public boolean ready;
        public PublishPoint publishPoints;
        public String audioPort;
        public String msNonce;
        public String ssIp;
        public String ssNonce;
        public String ssPassword;

        public static class PublishPoint {
            public String web;
            public String android;
            public String iOS;

            @Override
            public String toString() {
                return MoreObjects.toStringHelper(this)
                        .add("web", web)
                        .add("android", android)
                        .add("iOS", iOS)
                        .toString();
            }
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("ready", ready)
                    .add("publishPoints", publishPoints)
                    .add("audioPort", audioPort)
                    .add("msNonce", msNonce)
                    .add("ssIp", ssIp)
                    .add("ssNonce", ssNonce)
                    .add("ssPassword", ssPassword)
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
