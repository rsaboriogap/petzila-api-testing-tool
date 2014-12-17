package com.petzila.api.model.response;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 02/12/14.
 */
public final class PetUpdateResponse extends Response {
    public Data data;

    public static class Data {
        public String id;
        public String name;
        public String description;
        public String age;
        public String species;
        public String other;
        public String breed;
        public String size;
        public String gender;
        public String website;
        public String food;
        public String profilePicture;
        public String updatedAt;
        public String owner;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("id", id)
                    .add("name", name)
                    .add("description", description)
                    .add("age", age)
                    .add("species", species)
                    .add("other", other)
                    .add("breed", breed)
                    .add("size", size)
                    .add("gender", gender)
                    .add("website", website)
                    .add("food", food)
                    .add("profilePicture", profilePicture)
                    .add("updatedAt", updatedAt)
                    .add("owner", owner)
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
