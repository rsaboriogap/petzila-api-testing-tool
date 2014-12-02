package com.petzila.api.model;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 20/11/14.
 */
public class PetCreateResponse {
    public String status;
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
        public String createdAt;
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
                    .add("createdAt", createdAt)
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
