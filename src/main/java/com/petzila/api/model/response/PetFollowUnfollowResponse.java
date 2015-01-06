package com.petzila.api.model.response;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 20/11/14.
 */
public final class PetFollowUnfollowResponse extends Response {
    public Data data;

    public static class Data {
        public int followersCounter;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("followersCounter", followersCounter)
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