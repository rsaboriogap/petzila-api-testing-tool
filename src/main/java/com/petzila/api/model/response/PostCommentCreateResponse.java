package com.petzila.api.model.response;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 02/12/14.
 */
public final class PostCommentCreateResponse extends Response {
    public Data data;

    public static class Data {
        public String id;
        public User user;
        public String postId;
        public String description;
        public String createdAt;

        public static class User {
            public String id;
            public String firstName;
            public String lastName;
            public String username;
            public String profilePicture;
            public String updatedAt;

            @Override
            public String toString() {
                return MoreObjects.toStringHelper(this)
                        .add("id", id)
                        .add("firstName", firstName)
                        .add("lastName", lastName)
                        .add("username", username)
                        .add("profilePicture", profilePicture)
                        .add("updatedAt", updatedAt)
                        .toString();
            }
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("id", id)
                    .add("user", user)
                    .add("postId", postId)
                    .add("description", description)
                    .add("createdAt", createdAt)
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
