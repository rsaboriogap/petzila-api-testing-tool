package com.petzila.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * Created by rsaborio on 20/11/14.
 */
public final class PostGetStreamResponse extends Response {
    public Data[] data;

    public static class Data {
        public String id;
        public Pet pet;
        public String postPicture;
        public String updatedAt;
        public String timestamp;
        public String description;
        public boolean liked;
        public int likesCount;
        public int commentsCount;
        public List<Comment> comments;

        public static class Pet {
            public String id;
            public String name;
            public String profilePicture;
            public String updatedAt;
            public String owner;

            @Override
            public String toString() {
                return MoreObjects.toStringHelper(this)
                        .add("id", id)
                        .add("name", name)
                        .add("profilePicture", profilePicture)
                        .add("updatedAt", updatedAt)
                        .add("owner", owner)
                        .toString();
            }
        }

        public static class Comment {
            public String id;
            public String timestamp;
            public String description;
            public User user;

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
                        .add("timestamp", timestamp)
                        .add("description", description)
                        .add("user", user)
                        .toString();
            }
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("id", id)
                    .add("pet", pet)
                    .add("postPicture", postPicture)
                    .add("updatedAt", updatedAt)
                    .add("timestamp", timestamp)
                    .add("description", description)
                    .add("liked", liked)
                    .add("likesCount", likesCount)
                    .add("commentsCount", commentsCount)
                    .add("comments", comments)
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
