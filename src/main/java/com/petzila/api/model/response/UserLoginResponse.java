package com.petzila.api.model.response;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 19/11/14.
 */
public final class UserLoginResponse extends Response {
    public Data data;

    public static class Data {
        public String temporalPassword;
        public String id;
        public String email;
        public String firstName;
        public String lastName;
        public String description;
        public String username;
        public String petsCount;
        public String token;
        public String renewToken;
        public String notificationCount;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("temporalPassword", temporalPassword)
                    .add("id", id)
                    .add("email", email)
                    .add("firstName", firstName)
                    .add("lastName", lastName)
                    .add("description", description)
                    .add("username", username)
                    .add("petsCount", petsCount)
                    .add("token", token)
                    .add("renewToken", renewToken)
                    .add("notificationCount", notificationCount)
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
