package com.petzila.api.model.response;

import com.google.common.base.MoreObjects;

/**
 * Created by vicente on 20/01/15.
 */
public class UserGetResponse extends Response {
    public Data data;

    public static class Data {

        public String id;
        public String email;
        public String firstName;
        public String lastName;
        public String description;
        public String profilePicture;
        public String updatedAt;
        public String username;
        public String followingCount;
        public String petsCount;
        public String token;
        public String postsCount;
        public String pendingNotifications;
        public Object[] pets;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("id", id)
                    .add("email", email)
                    .add("firstName", firstName)
                    .add("lastName", lastName)
                    .add("description", description)
                    .add("profilePicture", profilePicture)
                    .add("updatedAt", updatedAt)
                    .add("followingCount", followingCount)
                    .add("petsCount", petsCount)
                    .add("postsCount", postsCount)
                    .add("pendingNotifications", pendingNotifications)
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
