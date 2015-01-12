package com.petzila.api.model.response;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 19/11/14.
 */
public final class AdminLoginResponse extends Response {
    public Data data;

    public static class Data {
        public String id;
        public String email;
        public String username;
        public String token;
        public String renewToken;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("id", id)
                    .add("email", email)
                    .add("username", username)
                    .add("token", token)
                    .add("renewToken", renewToken)
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
