package com.petzila.api.model.response;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 20/11/14.
 */
public final class PetFollowersResponse extends Response {
    public Data[] data;

    public static class Data {
        public String id;
        public String email;
        public String firstName;
        public String lastName;
        public String username;
        public String profilePicture;
        public String updatedAt;
        public String timestamp;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("id", id)
                    .add("email", email)
                    .add("firstName", firstName)
                    .add("lastName", lastName)
                    .add("username", username)
                    .add("profilePicture", profilePicture)
                    .add("updatedAt", updatedAt)
                    .add("timestamp", timestamp)
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