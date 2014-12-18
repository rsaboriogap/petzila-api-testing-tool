package com.petzila.api.model.response;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 19/11/14.
 */
public final class UserNotificationGetResponse extends Response {
    public Data[] data;

    public static class Data {
        public String id;
        public String type;
        public String description;
        public String pending;
        public User user;
        public Pet pet;
        public Post post;
        public String createdAt;

        public static class User {
            public String userId;
            public String username;

            @Override
            public String toString() {
                return MoreObjects.toStringHelper(this)
                        .add("userId", userId)
                        .add("username", username)
                        .toString();
            }
        }

        public static class Pet {
            public String id;
            public String name;
            public String profilePicture;

            @Override
            public String toString() {
                return MoreObjects.toStringHelper(this)
                        .add("id", id)
                        .add("name", name)
                        .add("profilePicture", profilePicture)
                        .toString();
            }
        }

        public static class Post {
            public String id;
            public String postPicture;

            @Override
            public String toString() {
                return MoreObjects.toStringHelper(this)
                        .add("id", id)
                        .add("postPicture", postPicture)
                        .toString();
            }
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("id", id)
                    .add("type", type)
                    .add("description", description)
                    .add("pending", pending)
                    .add("user", user)
                    .add("pet", pet)
                    .add("post", post)
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
