package com.petzila.api.model.response;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 20/11/14.
 */
public final class PostCreateResponse extends Response {
    public Data data;

    public static class Data {
        public String id;
        public String userId;
        public String petId;
        public String description;
        public String createdAt;
        public String postPicture;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("id", id)
                    .add("userId", userId)
                    .add("petId", petId)
                    .add("description", description)
                    .add("createdAt", createdAt)
                    .add("postPicture", postPicture)
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
