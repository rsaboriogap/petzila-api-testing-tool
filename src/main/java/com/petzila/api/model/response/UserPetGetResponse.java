package com.petzila.api.model.response;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 19/11/14.
 */
public final class UserPetGetResponse extends Response {
    public Data[] data;

    public static class Data {
        public String id;
        public String name;
        public String description;
        public String profilePicture;
        public String updatedAt;
        public String createdAt;
        public String followed;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("id", id)
                    .add("Name", name)
                    .add("Description", description)
                    .add("updatedAt", updatedAt)
                    .add("createdAt", createdAt)
                    .add("followed", followed)
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
