package com.petzila.api.model.response;

import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * Created by rsaborio on 19/11/14.
 */
public final class UserSignUpResponse extends Response {
    public Data data;

    public static class Data {
        public boolean temporal;
        public String id;
        public String email;
        public String firstName;
        public String lastName;
        public String description;
        public String profilePicture;
        public String username;
        public String status;
        public String createdAt;
        public String updatedAt;
        public long followingCount;
        public List<Long> following;
        public List<Long> petsCount;
        public List<Long> pets;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("temporal", temporal)
                    .add("id", id)
                    .add("email", email)
                    .add("firstName", firstName)
                    .add("lastName", lastName)
                    .add("description", description)
                    .add("profilePicture", profilePicture)
                    .add("username", username)
                    .add("status", status)
                    .add("createdAt", createdAt)
                    .add("updatedAt", updatedAt)
                    .add("followingCount", followingCount)
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
